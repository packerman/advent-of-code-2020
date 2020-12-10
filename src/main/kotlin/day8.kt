import com.google.common.io.Resources
import java.lang.Integer.parseInt

fun main() {

    val input = Resources.getResource("inputs/day8.txt").readText()
    val program = Day8.readProgram(input)

    println(Day8.part1Answer(program))

    println(Day8.part2Answer(program))

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
        val emulator = Emulator(program)
        val executed = mutableSetOf<Int>()
        while (emulator.next !in executed) {
            executed.add(emulator.next)
            emulator.executeNext()
        }
        return emulator.acc
    }

    fun part2Answer(program: Array<Instruction>): Int {
        fun tryWithChangedOperation(changed: Int): Int? {
            val emulator = EmulatorWithChangedOperation(program, changed)
            val executed = mutableSetOf<Int>()
            while (!emulator.terminated) {
                if (emulator.next in executed) {
                    return null
                }
                executed.add(emulator.next)
                emulator.executeNext()
            }
            return emulator.acc
        }
        return program.indices
            .asSequence()
            .filter { program[it].operation == Operation.nop || program[it].operation == Operation.jmp }
            .map { tryWithChangedOperation(it) }
            .first { it != null } ?: error("No such operation")
    }

    class Emulator(private val program: Array<Instruction>) {

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

    class EmulatorWithChangedOperation(
        private val program: Array<Instruction>,
        private val changedIndex: Int
    ) {

        private var _next = 0
        private var _acc = 0

        val next: Int
            get() = _next

        val acc: Int
            get() = _acc

        val terminated: Boolean
            get() = _next >= program.size

        fun executeNext() {
            if (!terminated) {
                val instruction = program[_next]
                val changedInstruction = if (_next == changedIndex) {
                    when (instruction.operation) {
                        Operation.nop -> instruction.copy(operation = Operation.jmp)
                        Operation.jmp -> instruction.copy(operation = Operation.nop)
                        else -> instruction
                    }
                } else instruction
                executeInstruction(changedInstruction)
            }
        }

        private fun executeInstruction(instruction: Instruction) {
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
