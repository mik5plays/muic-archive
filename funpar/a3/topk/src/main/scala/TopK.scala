import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.*
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object TopK {

  def topKWords(k: Int)(fileSpec: String)(implicit ec: ExecutionContext): Vector[(String, Int)] = {
    val lines = io.Source.fromFile(fileSpec).getLines().toSeq

    // a-z only, up to two dashes.
    def isWord(word: String): Boolean =
      val isValidWord = word.forall(c => c.isLetter || c.equals('-'))
      isValidWord && word.count(_ == '-') <= 2

    // read in parallel each line
    val tokenize: Seq[Future[Map[String, Int]]] = lines.map {
      line => Future {
        line.split("\\s+").filter(token => token.nonEmpty && isWord(token))
          .map(_.toLowerCase)
          .groupBy(identity)
          .view.mapValues(_.length)
          .toMap
      }
    }

    val res: Map[String, Int] = Await.result(
      Future.sequence(tokenize).map {
        maps =>
          maps.foldLeft(Map.empty[String, Int]) {
            (acc, m) => {
              m.foldLeft(acc) {
                case (acc2, (w, f)) =>
                  acc2.updated(w, acc2.getOrElse(w, 0) + f)
              }
            }
          }
      },
      10.seconds // arbitrary timeout
    )

    res.toSeq
      .sortBy { case (w, f) => (-f, w) }
      .take(k)
      .toVector
  }

  def main(args: Array[String]) = {
    val k = topKWords(2)("testFile")
    println(k)
  }
}
