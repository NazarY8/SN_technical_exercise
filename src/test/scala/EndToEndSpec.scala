import MinPathFinder.PathResult
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source

class EndToEndSpec extends AnyFlatSpec with Matchers {

  private def runPipeline(input: String): String = {
    val lines = input.linesIterator
    val triangle = TriangleParser.parse(lines) match {
      case Right(t) => t
      case Left(error) => fail(s"Parser failed: $error")
    }
    val PathResult(path, sum) = MinPathFinder.findMinPath(triangle)
    s"Minimal path is: ${path.mkString(" + ")} = $sum"
  }

  "End-to-end pipeline" should "produce correct output based on the exercise example" in {
    val input = "7\n6 3\n3 8 5\n11 2 10 9"
    val output = runPipeline(input)

    output shouldBe "Minimal path is: 7 + 6 + 3 + 2 = 18"
  }

  it should "produce correct output for data_small.txt" in {
    val input = loadResource("datasets/data_small.txt")
    val output = runPipeline(input)

    output shouldBe s"Minimal path is: ${List.fill(50)("1").mkString(" + ")} = 50"
  }

  it should "produce correct output for data_big.txt" in {
    val input = loadResource("datasets/data_big.txt")
    val output = runPipeline(input)

    output shouldBe s"Minimal path is: ${List.fill(2000)("1").mkString(" + ")} = 2000"
  }

  private def loadResource(name: String): String =
    Source.fromResource(name).mkString
}
