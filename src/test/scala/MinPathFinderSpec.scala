import MinPathFinder.PathResult
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MinPathFinderSpec extends AnyFlatSpec with Matchers {

  "MinPathFinder" should "find the minimal path based on the exercise example" in {
    val triangle = Vector(
      Vector(7L),
      Vector(6L, 3L),
      Vector(3L, 8L, 5L),
      Vector(11L, 2L, 10L, 9L)
    )

    val result = MinPathFinder.findMinPath(triangle)

    result.path shouldBe Vector(7L, 6L, 3L, 2L)
    result.sum shouldBe 18L
  }

  it should "handle a single-row triangle" in {
    val triangle = Vector(Vector(42L))

    val result = MinPathFinder.findMinPath(triangle)

    result.path shouldBe Vector(42L)
    result.sum shouldBe 42L
  }

  it should "handle a two-row triangle choosing the smaller child" in {
    val triangle = Vector(
      Vector(1L),
      Vector(10L, 5L)
    )

    val result = MinPathFinder.findMinPath(triangle)

    result.path shouldBe Vector(1L, 5L)
    result.sum shouldBe 6L
  }

  it should "prefer left child when sums are equal" in {
    val triangle = Vector(
      Vector(1L),
      Vector(2L, 2L)
    )

    val result = MinPathFinder.findMinPath(triangle)

    result.path shouldBe Vector(1L, 2L)
    result.sum shouldBe 3L
  }
}
