import scala.annotation.tailrec

object Happy extends App {
  // TODO: write these functions!
  def sumOfDigitsSquared(n: Int): Int =
    if (n == 0) 0
    else {
      val d = n % 10
      (d*d) + sumOfDigitsSquared(n / 10)
    }

  @tailrec
  def isHappy(n: Int): Boolean =
    if (n == 1) true
    else if (n == 4) false
    else isHappy(sumOfDigitsSquared(n))

  def kThHappy(k: Int): Int = {
    @tailrec
    def findKth(n: Int, current: Int): Int =
      if (n == k) current - 1
      else if (isHappy(current)) findKth(n + 1, current + 1)
      else findKth(n, current + 1)
    findKth(0, 1)
  }

 }
