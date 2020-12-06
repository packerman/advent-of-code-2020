import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day6Test {

    @Test
    internal fun shouldCollectAnswers() {
        val input = """
            abc

            a
            b
            c

            ab
            ac

            a
            a
            a
            a

            b
        """.trimIndent()

        assertEquals(listOf(
            setOf('a', 'b', 'c'),
            setOf('a', 'b', 'c'),
            setOf('a', 'b', 'c'),
            setOf('a'),
            setOf('b')
        ), Day6.collectAnswers(input, Set<Char>::union)
        )
    }
}
