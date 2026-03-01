import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val lines = Source.stdin.getLines()
    val result = TriangleParser.parse(lines)

    result match {
      case Right(triangle) =>
        println(s"Parsed triangle with ${triangle.length} rows")
        triangle.headOption.foreach(row => println(s"First row: $row"))
        triangle.lastOption.foreach(row => println(s"Last row: $row"))
      case Left(error) =>
        System.err.println(s"Error: $error")
        System.exit(1)
    }
  }
}

