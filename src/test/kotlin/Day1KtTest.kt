import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1KtTest {

    @Test
    fun findPairThatSum() {
        val array = intArrayOf(1721, 979, 366, 299, 675, 1456)
        val (n1, n2) = requireNotNull(findPairThatSum(array, 2020))
        assertEquals(514579, n1 * n2)
    }
}
