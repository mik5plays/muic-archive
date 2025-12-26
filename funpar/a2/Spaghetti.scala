import scala.math._

object Spaghetti extends App {
  def spaghetti: LazyList[String] = {
    def next(s: String): String = {
      def loop(chars: List[Char]): String = chars match {
        case Nil => ""
        case h :: t => {
          val (same, rest) = chars.span(_ == h)
          same.length.toString + h + loop(rest)
        }
      }
      loop(s.toList)
    }
    LazyList.iterate("1")(next)
  }

  /*
  pattern for ham is
  -- H(n) has 2^n strings
  -- Prepend "0" to every code in H(n-1)
  -- Prepend "1" to every code in H(n-1) but in reverse
   */
  def ham: LazyList[String] = {
    def H(n: Int): List[String] = n match {
      case 1 => List("0", "1")
      case _ => {
        val prev = H(n-1)
        prev.map("0" + _) ++ prev.reverse.map("1" + _)
      }
    }
    LazyList.from(1).flatMap(H)
  }
}
