import Day8.Instruction.Companion
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day8Test {

    @Test
    internal fun shouldReadProgram() {
        val text = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
        """.trimIndent()

        val program = Day8.readProgram(text)

        val expected = arrayOf(
            Day8.Instruction(Day8.Operation.nop, 0),
            Day8.Instruction(Day8.Operation.acc, 1),
            Day8.Instruction(Day8.Operation.jmp, 4),
            Day8.Instruction(Day8.Operation.acc, 3),
            Day8.Instruction(Day8.Operation.jmp, -3),
            Day8.Instruction(Day8.Operation.acc, -99),
            Day8.Instruction(Day8.Operation.acc, 1),
            Day8.Instruction(Day8.Operation.jmp, -4),
            Day8.Instruction(Day8.Operation.acc, 6),
        )

        assertArrayEquals(expected, program)
    }
}
