import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5Test {

    @Test
    internal fun shouldReadSeatFromString() {
        val seat = Seat.fromString("FBFBBFFRLR")
        assertEquals(Seat(44, 5), seat)
        assertEquals(357, seat.id)
        assertEquals(Seat(70, 7), Seat.fromString("BFFFBBFRRR"))
        assertEquals(Seat(14, 7), Seat.fromString("FFFBBBFRRR"))
        assertEquals(Seat(102, 4), Seat.fromString("BBFFBBFRLL"))
    }
}
