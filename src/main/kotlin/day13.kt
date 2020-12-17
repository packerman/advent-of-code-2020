import Day13PartOne.earliestDeport
import com.google.common.io.Resources
import java.lang.Integer.parseInt
import java.lang.Long.parseLong

fun main() {
    val text = Resources.getResource("inputs/day13.txt")
        .readText()
        .trim()
    val input = Day13PartOne.Input.fromString(text)
    println(input.earliestDeport()?.answer)
    with(Day13PartTwo) {
        println(answer(listOf(0L to 1789L, 1L to 37L, 2L to 47L, 3L to 1889)))
    }
}

object Day13PartOne {

    data class Depart(val waitTime: Int, val busId: Int) {

        val answer: Int = waitTime * busId
    }

    fun Input.earliestDeport(): Depart? {
        fun waitTime(busId: Int) =
            if (arriveTime % busId == 0) 0
            else (arriveTime / busId + 1) * busId - arriveTime
        return busIds.map { Depart(waitTime(it), it) }.minByOrNull { it.waitTime }
    }

    data class Input(val arriveTime: Int, val busIds: Set<Int>) {

        companion object {
            fun fromString(input: String): Input {
                val (line1, line2) = input.trim().lines()
                val arriveTime = parseInt(line1)
                val busIds = line2.split(",")
                    .asSequence()
                    .filter { it != "x" }
                    .map { parseInt(it) }
                    .toSet()
                return Input(arriveTime, busIds)
            }
        }
    }
}

object Day13PartTwo {

    fun readInput(input: String): List<Pair<Long, Long>> {
        val (_, line) = input.trim().lines()
        return line.split(",")
            .asSequence()
            .mapIndexed { i, s -> i to s }
            .filter { p -> p.second != "x" }
            .map { (i, s) -> i.toLong() to parseLong(s) }
            .toList()
    }

    fun answer(input: List<Pair<Long, Long>>): Long {
        val sorted = input.sortedByDescending { it.second }
        val head = sorted.first()
        val rest = sorted.drop(1)
        var n = head.second - head.first
        while (true) {
            if (rest.all { (r, k) -> (n + r) % k == 0L }) {
                return n
            }
            n += head.second
        }
    }
}
