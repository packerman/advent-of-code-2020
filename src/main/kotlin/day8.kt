import com.google.common.io.Resources
import java.lang.Integer.parseInt

fun main() {

    val input = Resources.getResource("inputs/day8.txt").readText()
    val program = Day8.readProgram(input)

    println(Day8.part1Answer(program))

}

object Day8 {

    enum class Operation {
        acc,
        jmp,
        nop
    }

    data class Instruction(val operation: Operation, val argument: Int) {

        companion object {

            val regex = Regex("(acc|jmp|nop) ([+-](?:\\d+))")

            fun fromString(input: String): Instruction {
                val matchResult = requireNotNull(regex.matchEntire(input)) { "Unrecognized instruction: '$input'" }
                return Instruction(Operation.valueOf(matchResult.groupValues[1]), parseInt(matchResult.groupValues[2]))
            }
        }
    }

    fun readProgram(input: String): Array<Instruction> =
        input.trim()
            .lines()
            .map { Instruction.fromString(it) }
            .toTypedArray()

    fun part1Answer(program: Array<Instruction>): Int {
        val simulator = Simulator(program)
        val executed = mutableSetOf<Int>()
        while (simulator.next !in executed) {
            executed.add(simulator.next)
            simulator.executeNext()
        }
        return simulator.acc
    }

    class Simulator(private val program: Array<Instruction>) {

        private var _next = 0
        private var _acc = 0

        val next: Int
            get() = _next

        val acc: Int
            get() = _acc

        fun executeNext() {
            val instruction = program[_next]
            when (instruction.operation) {
                Operation.nop -> _next++
                Operation.acc -> {
                    _acc += instruction.argument
                    _next++
                }
                Operation.jmp -> _next += instruction.argument
            }
        }
    }
}
