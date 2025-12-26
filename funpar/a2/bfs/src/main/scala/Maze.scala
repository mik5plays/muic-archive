object Maze {

  val maze = Vector(
  "xxxxxxxxxxxxxxxxxx",
  "x    x      x   ex",
  "x    x   x  x xxxx",
  "x        x  x    x",
  "xs   x   x       x",
  "xxxxxxxxxxxxxxxxxx")

  type Coordinate = (Int, Int)

  // same one used in Thesaurus
  private def reconstruct[V](parent: Map[V, V], src: V, dst: V): Option[List[V]] = {
    if (!parent.contains(dst)) then None
    else {
      def loop(u: V, acc: List[V]): List[V] =
        if (u == src) src :: acc
        else loop(parent(u), u :: acc)

      Some(loop(dst, Nil))
    }
  }

  def solveMaze(maze: Vector[String]): Option[String] = {
    val rows = maze.length
    val columns = maze.head.length

    val positions: Vector[Coordinate] =  maze.indices.flatMap(r => (0 until columns).map(c => (r, c))).toVector

    val src = positions.find{ case (r, c) => maze(r)(c) == 's' }.get
    val dst = positions.find{ case (r, c) => maze(r)(c) == 'e' }.get

    def nbrs(p: Coordinate): Set[Coordinate] =
      p match {
        case (r, c) =>
          List((r - 1, c), (r + 1, c), (r, c - 1), (r, c + 1)) // up, down, left, right
            .filter { case (rr, cc) =>
              rr >= 0 && rr < rows &&
                cc >= 0 && cc < columns &&
                maze(rr)(cc) != 'x' // places you can actually go to
            }
            .toSet
      }

    def pathString(path: List[Coordinate]): String = path match {
      case Nil | _ :: Nil => ""
      case (r1, c1) :: (r2, c2) :: rest =>
        val move =
          if r2 == r1 - 1 then "u"
          else if r2 == r1 + 1 then "d"
          else if c2 == c1 - 1 then "l"
          else if c2 == c1 + 1 then "r"
          else "" // probably invalid
        move + pathString((r2, c2) :: rest)
    }

    val (parent, _) = GraphBFS.bfs(nbrs, src)
    reconstruct(parent, src, dst).map(pathString)
  }
}
