import scala.io.Source

object Thesaurus {

  val defaultEncoding = "ISO8859-1"

  def load(filename: String): Map[String, Set[String]] = {
    val src = Source.fromFile(filename, defaultEncoding)
    val lines = src.getLines().toList
    src.close()

    val content = lines.tail // skip first encoding line

    def parse(rest: List[String], acc: Map[String, Set[String]]): Map[String, Set[String]] = {
      rest match {
        case Nil => acc
        case stem :: tail if stem.trim.isEmpty => parse(tail, acc)
        case stem :: tail =>
          val parts = stem.split("\\|")
          val stemWord = parts(0).trim
          val count = parts(1).toInt

          val (groups, rest_) = tail.splitAt(count)
          val synonyms: Set[String] = groups.flatMap { line =>
            val fields = line.split("\\|").map(_.trim)
            fields.drop(1) // drop the (noun) part or (adj) or whatever
          }.toSet

          parse(rest_, acc + (stemWord -> synonyms))
      }
    }
    parse(content, Map.empty)

  }

  // helper to reconstruct path from src to dst
  private def reconstruct[V](parent: Map[V, V], src: V, dst: V): Option[List[V]] = {
    if (!parent.contains(dst)) then None
    else {
      def loop(u: V, acc: List[V]): List[V] =
        if (u == src) src :: acc
        else loop(parent(u), u :: acc)
      Some(loop(dst, Nil))
    }
  }
  
  def linkage(thesaurusFile: String): String => String => Option[List[String]] = {
    val graph = load(thesaurusFile)

    (wordA: String) => {
      val (parent, _) = GraphBFS.bfs(graph.getOrElse(_, Set.empty), wordA)

      (wordB: String) => reconstruct(parent, wordA, wordB)
    }
  }
}
