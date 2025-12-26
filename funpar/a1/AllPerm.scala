object AllPerm extends App {
  def allPerm(n: Int): List[List[Int]] = {
    def helper(x: Int, xs: List[Int]): List[List[Int]] = {
      def loop(pos: Int): List[List[Int]] = {
        if (pos > xs.length) Nil
        else {
          val (h, t) = xs.splitAt(pos)
          val inserted = h ::: (x :: t)
          inserted :: loop(pos + 1)
        }
      }

      loop(0)
    }

    if (n == 1) List(List(1))
    else {
      val previous = allPerm(n - 1)
      def combine(perms: List[List[Int]]): List[List[Int]] = perms match {
        case Nil => Nil
        case h :: t => helper(n, h) ::: combine(t)
      }

      combine(previous)
    }
  }
}
