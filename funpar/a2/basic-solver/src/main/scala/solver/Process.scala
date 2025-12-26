package solver

object Process {
  // gives a "pretty-print" string form of the expression
  def stringify(e: Expression): String = e match {
    case Constant(c) => c.toString
    case Var(name) => name
    case Sum(l, r) => stringify(l) + " + " + stringify(r)
    case Prod(l @ Sum(_, _), r @ Sum(_, _)) => "(" + stringify(l) + ") * (" + stringify(r) + ")"
    case Prod(l @ Sum(_, _), r) => "(" + stringify(l) + ") * " + stringify(r)
    case Prod(l, r @ Sum(_, _)) => stringify(l) + " * (" + stringify(r) + ")"
    case Prod(l, r) => stringify(l) + " * " + stringify(r)
    case Power(b, e) => stringify(b) + "^" + stringify(e)
  }

  // evaluates a given expression e: Expression using
  // the variable settings in varAssn: Map[String, Double],
  // returning the evaluation result as a Double.

  // Example: eval(e, Map("x" -> 4.0)) evaluates the expression 
  // with the variable "x" set to 4.0.
  def eval(e: Expression, varAssn: Map[String, Double]): Double = e match {
    case Constant(n) => n
    case Var(n) => varAssn.getOrElse(n, 0)
    case Sum(e1, e2) => eval(e1, varAssn) + eval(e2, varAssn)
    case Prod(e1, e2) => eval(e1, varAssn) * eval(e2, varAssn)
    case Power(e1, e2) => Math.pow(eval(e1, varAssn), eval(e2, varAssn))
  }

  // symbolically differentiates an expression e: Expression with 
  // respect to the variable varName: String
  def differentiate(e: Expression, varName: String): Expression = {
    // some helper functions for match cases:
    def exprNoVariables(expr: Expression): Boolean = expr match {
      case Constant(_) => true;
      case Var(_) => false;
      case Sum(e1, e2) => exprNoVariables(e1) && exprNoVariables(e2)
      case Prod(e1, e2) => exprNoVariables(e1) && exprNoVariables(e2)
      case Power(e1, e2) => exprNoVariables(e1) && exprNoVariables(e2)
    }

    def nonZero(expr: Expression): Boolean = expr match {
      case Constant(n) => n != 0
      case _ => false
    }

    e match {

      case Constant(_) => Constant(0)

      case Var(x) => if (x == varName) then Constant(1) else Constant(0)

      case Sum(e1, e2) => Sum(
        differentiate(e1, varName),
        differentiate(e2, varName)
      )

      case Prod(e1, e2) => Sum(
        Prod(differentiate(e1, varName), e2),
        Prod(e1, differentiate(e2, varName))
      )

      case Power(e1, h) if exprNoVariables(h) => {
        Prod(
          Power(
            Prod(h, e1),
            Sum(h, Constant(-1))
          ),
          differentiate(e1, varName)
        )
      }

      case Power(c, e1) if nonZero(c) => {
        Prod(
          Constant(Math.log(eval(c, Map.empty))),
          Power(c, e1)
        )
      }
      case Power(_, _) =>
        throw new IllegalArgumentException("beyond the scope of the task")
    }
  }

  // forms a new expression that simplifies the given expression e: Expression
  // the goal of this function is produce an expression that is easier to
  // evaluate and/or differentiate.  If there's a canonical form you'd like to
  // follow, use this function to put an expression in this form.
  // you may leave this function blank if can't find any use. 
  def simplify(e: Expression): Expression = { e }

}
