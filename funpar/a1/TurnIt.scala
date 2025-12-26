import scala.annotation.tailrec

object TurnIt extends App {
  def transpose(A: List[List[Int]]): List[List[Int]] = {
    @tailrec
    def helper(remaining: List[List[Int]], acc: List[List[Int]]): List[List[Int]] = {
      if (remaining.isEmpty || remaining.head.isEmpty) acc.reverse
      else {
        @tailrec
        def split(rows: List[List[Int]], heads: List[Int], tails: List[List[Int]]): (List[Int], List[List[Int]]) = rows match {
          case Nil => (heads.reverse, tails.reverse)
          case (h :: t) :: rest => split(rest, h :: heads, t :: tails)
        }
        val (heads, tails) = split(remaining, Nil, Nil)
        helper(tails, heads :: acc)
      }
    }

    helper(A, Nil)
  }
}
