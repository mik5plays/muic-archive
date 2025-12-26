import org.jsoup.*

import java.io.IOException
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.jdk.CollectionConverters.*
import scala.collection.concurrent
import scala.collection.mutable
import java.net.{MalformedURLException, URL}
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.{ConcurrentHashMap, atomic}
import scala.concurrent.duration._

object Crawler {
  implicit val ec: ExecutionContext = ExecutionContext.global

  sealed case class WebStats(
    // the total number of (unique) files found
    numFiles: Int,
    // the total number of (unique) file extensions (.jpg is different from .jpeg)
    numExts: Int,
    // a map storing the total number of files for each extension.
    extCounts: Map[String, Int],
    // the total number of words in all html files combined, excluding
    // all html tags, attributes and html comments.
    totalWordCount: Long
  )

  // remove fragments from URL
  def normalize(url: URL): URL = {
    val protocol = url.getProtocol
    val host = url.getHost
    val port = url.getPort
    val path = Option(url.getPath).getOrElse("")

    try {
      if (port == -1) then new URL(protocol, host, path)
      else new URL(protocol, host, port, path)
    } catch {
      case _: Throwable => new URL(protocol, host, path) // fallback
    }
  }

  def getExtension(path: String): Option[String] = {
    if (path == null || path.isEmpty) return None
    val last = {
      if (path.endsWith("/")) ""
      else {
        val ext = path.lastIndexOf("/")
        if (ext >= 0) path.substring(ext + 1) else path
      }
    }
    if (last == null || last.isEmpty) return None
    val idx = last.lastIndexOf(".") // the extension
    if (idx >= 0 && idx < last.length - 1) Some(last.substring(idx + 1).toLowerCase) else None
  }

  def isWord(word: String): Boolean =
    word.nonEmpty && word.head.isLetter && word.forall(_.isLetterOrDigit)

  def isWorthCrawling(ext: Option[String], path: String): Boolean = {
    val crawlableExts = Set("html", "htm", "php", "asp", "aspx", "jsp", "")
    ext match {
      case Some(e) => crawlableExts.contains(e)
      case None =>
        val p = Option(path).getOrElse("")
        p.endsWith("/") || p.isEmpty
    }
  }

  def crawlForStats(basePath: String): WebStats = {

    // new normalized base URL
    val base = try {
      normalize(new URL(basePath))
    } catch {
      case _: MalformedURLException => throw new Exception("Invalid Base URL")
    }

    val visited: java.util.Set[String] = ConcurrentHashMap.newKeySet[String]()
    val extCounts: concurrent.TrieMap[String, Int] = concurrent.TrieMap.empty
    val files: java.util.Set[String] = ConcurrentHashMap.newKeySet[String]()
    val wc = new AtomicLong(0L)

    visited.add(base.toString)
    files.add(base.toString)

    var frontier: Seq[URL] = Seq(base)

    // BFS
    while (frontier.nonEmpty) {
      val result: Seq[Future[Seq[URL]]] = frontier.map { url =>
        Future {
          val path = Option(url.getPath).getOrElse("")
          val ext = getExtension(path)

          // count extension
          ext.foreach { e =>
            extCounts.updateWith(e) {
              case Some(c) => Some(c + 1)
              case None => Some(1)
            }
          }

          if (isWorthCrawling(ext, path)) {
            try {
              val doc = Jsoup.connect(url.toString).get

              val text = doc.text().toLowerCase
              val count = text.split("\\s+").count(token => isWord(token))
              wc.addAndGet(count.toLong)

              val allLinks = doc.select("a[href]").asScala.
                toSeq.map(_.attr("abs:href"))

              // resolve relative links
              val resolved: Seq[URL] = allLinks.flatMap { href =>
                try {
                  val normalizedUrl = normalize(new URL(url, href))
                  if (normalizedUrl.toString.startsWith(base.toString)) {
                    files.add(normalizedUrl.toString)
                    Some(normalizedUrl)
                  } else {
                    None
                  }
                } catch {
                  case _: MalformedURLException => None
                }
              }
              resolved
            } catch {
              case _: IOException => Seq.empty
            }
          } else {
            Seq.empty
          }
        }
      }

      val aggregate: Seq[Seq[URL]] = Await.result(Future.sequence(result), 30.seconds)

      // do not visit duplicates, only unique links
      val discovered = aggregate.flatten
      val toVisit = mutable.ArrayBuffer.empty[URL]
      discovered.foreach { url =>
        val added = visited.add(url.toString)
        if (added) {
          toVisit += url
        }
      }

      frontier = toVisit.toSeq.filter { u =>
        isWorthCrawling(getExtension(Option(u.getPath).getOrElse("")), u.getPath)
      }

    }

    WebStats(files.size(), extCounts.size, extCounts.toMap, wc.get())

  }

  def main(args: Array[String]) = {
    val sbp = "https://cs.muic.mahidol.ac.th/courses/ooc/api/"
    val sbp2 = "https://en.wikipedia.org"

    val ws = crawlForStats(sbp)

    println(ws)
  }
}
