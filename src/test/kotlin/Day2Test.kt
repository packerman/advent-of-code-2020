import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day2Test {

    @Test
    internal fun shouldParseRecord() {
        val expected = Record(
            occurrences = 1..3,
            char = 'a',
            password = "abcde"
        )

        assertEquals(expected, Record.fromString("1-3 a: abcde"))
    }

    @Test
    internal fun shouldValidatePasswordOldPolicy() {
        assertTrue(Record.fromString("1-3 a: abcde").isValidOldPolicy())
        assertFalse(Record.fromString("1-3 b: cdefg").isValidOldPolicy())
        assertTrue(Record.fromString("2-9 c: ccccccccc").isValidOldPolicy())
    }

    @Test
    internal fun shouldValidatePasswordNewPolicy() {
        assertTrue(Record.fromString("1-3 a: abcde").isValidNewPolicy())
        assertFalse(Record.fromString("1-3 b: cdefg").isValidNewPolicy())
        assertFalse(Record.fromString("2-9 c: ccccccccc").isValidNewPolicy())
    }
}
