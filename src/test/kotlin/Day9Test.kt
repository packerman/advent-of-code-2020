import Day9.findRangeThatSumTo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day9Test {

    @Test
    internal fun shouldFindFirstNotValid() {
        val numbers = longArrayOf(
            35, 20, 15, 25, 47, 40, 62, 55, 65, 95, 102, 117, 150, 182, 127, 219, 299, 277, 309, 576
        )
        val window = 5

        val result = Day9.findFirstInvalidNumber(numbers, window)

        assertEquals(127, result)
    }

    @Test
    internal fun shouldRangeThatSumTo() {
        val numbers = longArrayOf(
            35, 20, 15, 25, 47, 40, 62, 55, 65, 95, 102, 117, 150, 182, 127, 219, 299, 277, 309, 576
        )

        val result = numbers.findRangeThatSumTo(127)

        assertEquals(2 to 5, result)
    }
}
