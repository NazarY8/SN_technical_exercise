object MinPathFinder {

  case class PathResult(path: Vector[Long], sum: Long)

  def findMinPath(triangle: Vector[Vector[Long]]): PathResult =
    if (triangle.length == 1)
      PathResult(triangle.head, triangle.head.head)
    else {
      val choices = computeChoices(triangle)
      val path = reconstructPath(triangle, choices)
      PathResult(path, path.sum)
    }

  // Bottom-up DP: for each node we compute the minimal sum to the bottom
  // and record which child index (i or i+1) gives that minimal path.
  private def computeChoices(triangle: Vector[Vector[Long]]): Vector[Vector[Int]] = {
    val initialSums = triangle.last

    val (_, allChoices) = triangle.init.foldRight((initialSums, Vector.empty[Vector[Int]])) {
      case (row, (sumsBelow, choicesAcc)) =>
        val rowWithChoices = row.zipWithIndex.map { case (value, i) =>
          val leftSum = sumsBelow(i)
          val rightSum = sumsBelow(i + 1)
          if (leftSum <= rightSum)
            (value + leftSum, i)
          else
            (value + rightSum, i + 1)
        }

        val newSums = rowWithChoices.map(_._1)
        val newChoices = rowWithChoices.map(_._2)

        (newSums, newChoices +: choicesAcc)
    }

    allChoices
  }

  // Reconstruct the path by following recorded child indices from top to bottom.
  private def reconstructPath(triangle: Vector[Vector[Long]], choices: Vector[Vector[Int]]): Vector[Long] = {
    val firstValue = triangle.head.head
    val (path, _) = choices.zipWithIndex.foldLeft((Vector(firstValue), 0)) {
      case ((pathAcc, currentIdx), (choiceRow, rowIdx)) =>
        val nextIdx = choiceRow(currentIdx)
        val nextValue = triangle(rowIdx + 1)(nextIdx)
        (pathAcc :+ nextValue, nextIdx)
    }
    path
  }
}
