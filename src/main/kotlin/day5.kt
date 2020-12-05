import com.google.common.io.Resources

fun main() {
    val seats = Resources.getResource("inputs/day5.txt")
        .readText()
        .lines()
        .filter(String::isNotEmpty)
        .map(Seat.Companion::fromString)
    println(seats.maxOf(Seat::id))
    println(findFreeSeatId(seats))
}

data class Seat(val row: Int, val column: Int) {

    val id = 8 * row + column

    companion object {

        fun fromString(input: String): Seat =
            Seat(
                rowFinder.find(input.substring(0, 7)),
                columnFinder.find(input.substring(7))
            )
    }
}

class Finder(
    private val lowChar: Char,
    private val highChar: Char,
    private val initialLow: Int,
    private val initialHigh: Int
) {

    fun find(input: String): Int {
        return find(input, initialLow, initialHigh)
    }

    private tailrec fun find(input: String, low: Int, high: Int): Int {
        require(input.isNotEmpty() || low == high)
        return when {
            input.isEmpty() -> low
            input[0] == lowChar -> find(input.substring(1), low, (low + high) / 2)
            input[0] == highChar -> find(input.substring(1), (low + high) / 2 + 1, high)
            else -> error("Unrecognized char")
        }
    }
}

val rowFinder = Finder('F', 'B', 0, 127)
val columnFinder = Finder('L', 'R', 0, 7)

fun findFreeSeatId(seats: List<Seat>): Int {
    val ids = seats.map(Seat::id).toSet()
    val min = requireNotNull(ids.minOrNull())
    val max = requireNotNull(ids.maxOrNull())

    for (id in min..max) {
        if (id !in ids) {
            return id
        }
    }
    error("Cannot find free seat")
}
