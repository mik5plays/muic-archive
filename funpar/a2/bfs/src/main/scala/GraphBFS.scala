object GraphBFS {

  def bfs[V](nbrs: V => Set[V], src: V) = {

    def expand(frontier: Set[V], parent: Map[V, V]) = {
      frontier.foldLeft((Set.empty[V], parent)) {
        case ((next, acc), u) =>
          val newNbrs = nbrs(u).filterNot(acc.contains) // only nbrs that have not been conquered
          val acc_ = acc ++ newNbrs.map(v => v -> u) // map nbrs to parent u
          (next ++ newNbrs, acc_)
      }
    }

    def iterate(frontier: Set[V], 
                parent: Map[V, V], 
                distance: Map[V, Int], d: Int
                ): (Map[V, V], Map[V, Int]) =
      if frontier.isEmpty then
        (parent, distance)
      else {
        val (frontier_, parent_) = expand(frontier, parent)
        val distance_ = distance ++ frontier_.map(v => v -> (d + 1)) // new distances

        iterate(frontier_, parent_, distance_, d + 1)
      }

    iterate(Set(src), Map(src -> src), Map(), 0)
  }

}
