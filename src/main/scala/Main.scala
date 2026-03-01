import MinPathFinder.PathResult
import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val lines = Source.stdin.getLines()
    val result = TriangleParser.parse(lines)

    result match {
      case Right(triangle) =>
        val PathResult(path, sum) = MinPathFinder.findMinPath(triangle)
        val pathStr = path.mkString(" + ")
        println(s"Minimal path is: $pathStr = $sum")
      case Left(error) =>
        System.err.println(s"Error: $error")
        System.exit(1)
    }
  }
}
