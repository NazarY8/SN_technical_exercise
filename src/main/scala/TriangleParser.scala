object TriangleParser {

  type Triangle = Vector[Vector[Long]]

  def parse(lines: Iterator[String]): Either[String, Triangle] = {
    val triangle = lines
      .map(_.trim)
      .filter(_.nonEmpty)
      .map(parseLine)
      .toVector

    validate(triangle)
  }

  private def parseLine(line: String): Vector[Long] =
    line.split("\\s+").map(_.toLong).toVector

  private def validate(triangle: Triangle): Either[String, Triangle] =
    if (triangle.isEmpty)
      Left("Triangle is empty: no rows found")
    else if (!hasValidStructure(triangle))
      Left("Invalid triangle structure: row i must have exactly i+1 elements")
    else
      Right(triangle)

  private def hasValidStructure(triangle: Triangle): Boolean =
    triangle.zipWithIndex.forall { case (row, i) => row.length == i + 1 }
}
