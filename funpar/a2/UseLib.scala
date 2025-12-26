object UseLib extends App {
  def onlyBeginsWithLower(xs: Vector[String]): Vector[String] =
    xs.filter(word => word.head.isLower)
  def longestString(xs: Vector[String]): Option[String] =
    if (xs.isEmpty) {
      None
    } else {
      Some(xs.maxBy(_.length))
    }
  def longestLowercase(xs: Vector[String]): Option[String] =
    longestString(onlyBeginsWithLower(xs))
}
