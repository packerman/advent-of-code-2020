import Day10.partOneAnswer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {

    @Test
    internal fun partOneExample1() {
        val joltageRatings = intArrayOf(
            16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4
        )

        assertEquals(35, partOneAnswer(joltageRatings))
    }
}
