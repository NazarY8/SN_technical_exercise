import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TriangleParserSpec extends AnyFlatSpec with Matchers {

  "TriangleParser" should "parse a valid triangle" in {
    val lines = Iterator("7", "6 3", "3 8 5", "11 2 10 9")
    val result = TriangleParser.parse(lines)

    result shouldBe Right(Vector(
      Vector(7L),
      Vector(6L, 3L),
      Vector(3L, 8L, 5L),
      Vector(11L, 2L, 10L, 9L)
    ))
  }

  it should "parse a single-row triangle" in {
    val lines = Iterator("42")
    val result = TriangleParser.parse(lines)

    result shouldBe Right(Vector(Vector(42L)))
  }

  it should "return Left for empty input" in {
    val lines = Iterator.empty[String]
    val result = TriangleParser.parse(lines)

    result shouldBe Left("Triangle is empty: no rows found")
  }

  it should "return Left for blank lines only" in {
    val lines = Iterator("", "  ", "")
    val result = TriangleParser.parse(lines)

    result shouldBe Left("Triangle is empty: no rows found")
  }

  it should "return Left for invalid structure" in {
    val lines = Iterator("7", "6 3 9", "3 8 5")
    val result = TriangleParser.parse(lines)

    result shouldBe Left("Invalid triangle structure: row i must have exactly i+1 elements")
  }

  it should "return Left for non-numeric input" in {
    val lines = Iterator("7", "6 abc")
    val result = TriangleParser.parse(lines)

    result shouldBe Left("Invalid number in row 2: '6 abc'")
  }
}
