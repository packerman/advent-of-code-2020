import Day13PartOne.earliestDeport
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day13Test {

    @Test
    internal fun testPartOne() {
        val busIds = setOf(7, 13, 59, 31, 19)
        val arriveTime = 939
        assertEquals(Day13PartOne.Depart(waitTime = 5, busId = 59), Day13PartOne.Input(arriveTime, busIds).earliestDeport())
    }

    @Test
    internal fun testPartTwo() {
        val pairs = listOf(0L to 7L, 1L to 13L, 4L to 59L, 6L to 31L, 7L to 19L)

        assertEquals(1068781L, Day13PartTwo.answer(pairs))
    }
}
