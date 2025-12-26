import scala.annotation.tailrec

object Aloud extends App {
  // TODO: Implement me
  def readAloud(xs: List[Int]): List[Int] = xs match {
    case Nil => Nil
    case x :: rest =>
      // n being number of occurrences of a number x in rest of list
      @tailrec
      def countOccurences(l: List[Int], n: Int): (Int, List[Int]) = l match {
        case num :: l1 if num == x => countOccurences(l1, n + 1)
        case _ => (n, l)
      }

      val (count, xs1) = countOccurences(rest, 1)
      count :: x :: readAloud(xs1)
  }
}
