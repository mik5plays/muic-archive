object Roman extends App {
  // TODO: implement this
  def toRoman(n: Int): String = {
    val numToRoman = List(
      (1000, "M"),
      (900, "CM"),
      (500, "D"),
      (400, "CD"),
      (100, "C"),
      (90, "XC"),
      (50, "L"),
      (40, "XL"),
      (10, "X"),
      (9, "IX"),
      (5, "V"),
      (4, "IV"),
      (1, "I")
    )

    def convert(n: Int, symbols: List[(Int, String)]): String = symbols match {
      case Nil => ""
      case (num, roman) :: rest =>
        if (n >= num) roman + convert(n - num, symbols)
        else convert(n, rest)
    }

    convert(n, numToRoman)
  }
}
