import scala.util.Try

object TriangleParser {

  type Triangle = Vector[Vector[Long]]

  def parse(lines: Iterator[String]): Either[String, Triangle] = {
    val trimmed = lines
      .map(_.trim)
      .filter(_.nonEmpty)
      .zipWithIndex
      .toVector

    val parsed = trimmed.map { case (line, idx) =>
      parseLine(line, idx + 1)
    }

    parsed.collectFirst { case Left(err) => err } match {
      case Some(err) => Left(err)
      case None => validate(parsed.collect { case Right(row) => row })
    }
  }

  private def parseLine(line: String, rowNumber: Int): Either[String, Vector[Long]] =
    Try(line.split("\\s+").map(_.toLong).toVector).toEither.left
      .map(_ => s"Invalid number in row $rowNumber: '$line'")

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
