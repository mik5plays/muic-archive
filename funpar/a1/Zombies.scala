object Zombies extends App {
  def countBad(hs: List[Int]): Int = {
    def mergeAndCount(left: List[Int], right: List[Int]): (List[Int], Int) = (left, right) match {
      case (Nil, _) => (right, 0)
      case (_, Nil) => (left, 0)
      case (l :: lRest, r :: rRest) =>
        if (l < r) { // bad pair since left is "shorter" than right
          val (merged, count) = mergeAndCount(lRest, right)
          (l :: merged, count + right.length)
        } else {
          val (merged, count) = mergeAndCount(left, rRest)
          (r :: merged, count)
        }
    }

    def sortAndCount(arr: List[Int]): (List[Int], Int) = arr match {
      case Nil => (Nil, 0)
      case _ :: Nil => (arr, 0)
      case _ =>
        val (left, right) = arr.splitAt(arr.length / 2)
        val (leftSort, leftCount) = sortAndCount(left)
        val (rightSort, rightCount) = sortAndCount(right)
        val (merged, mergedCount) = mergeAndCount(leftSort, rightSort)
        (merged, leftCount + rightCount + mergedCount)
    }

    val (_, count) = sortAndCount(hs)
    count
  }
}
