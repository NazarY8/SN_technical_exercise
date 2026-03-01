import java.util.concurrent.TimeUnit
import scala.io.Source

object PerformanceCheck extends App {

  private def measure[A](label: String)(block: => A): A = {
    val start = System.nanoTime()
    val result = block
    val elapsed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start)
    println(s"$label: ${elapsed}ms")
    result
  }

  // File I/O is done before timing starts.
  // I measure only parse and algorithm, not disk read.
  private def run(resourceName: String): Unit = {
    val input = Source.fromResource(resourceName).mkString
    val lines = input.linesIterator

    val triangle = measure("  Parse") {
      TriangleParser.parse(lines) match {
        case Right(t) => t
        case Left(error) => throw new RuntimeException(s"Parse error: $error")
      }
    }

    val totalElements = triangle.map(_.length.toLong).sum
    println(s"  Rows: ${triangle.length}, Elements: $totalElements")

    val result = measure("  Algorithm") {
      MinPathFinder.findMinPath(triangle)
    }

    println(s"  Sum: ${result.sum}")
    println()
  }

  println("\nPerformance Check: \n")

  println("data_small.txt:")
  run("datasets/data_small.txt")

  println("data_big.txt:")
  run("datasets/data_big.txt")
}
