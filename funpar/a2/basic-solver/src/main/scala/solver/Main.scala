package solver

object Solver {
  // solves expString == 0 for the variable in varName with an initial guess
  // specified. We'll assume that the given expression has a root.

  def solve(expString: String, varName: String, guess: Double): Double = {
    val ex = Parser(expString).getOrElse(throw new IllegalArgumentException("unparseable string"))
    val dex = Process.differentiate(ex, varName)

    val f: Double => Double = x => Process.eval(ex, Map(varName -> x))
    val df: Double => Double = x => Process.eval(dex, Map(varName -> x))

    // TODO: complete the implementation. This will construct the 
    // appropriate functions and call Newton.solve
    Newton.solve(f, df, guess).get
  }
}
