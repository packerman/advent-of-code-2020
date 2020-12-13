import Day10.partOneAnswer
import com.google.common.io.Resources
import java.lang.Integer.parseInt

fun main() {
    val joltageRatings = Resources.getResource("inputs/day10.txt")
        .readText()
        .trim()
        .lines()
        .map { parseInt(it) }
        .toIntArray()

    println(partOneAnswer(joltageRatings))
}

object Day10 {

    fun partOneAnswer(sorted: IntArray): Int {
        val sorted = sorted
            .sortedArray()
        val differences = sorted.differences()
        val first = sorted[0]
        val diffByOne = differences.count { it == 1 } + (if (first == 1) 1 else 0)
        val diffByThree = differences.count { it == 3 } + 1 + (if (first == 3) 1 else 0)
        return diffByOne * diffByThree
    }

    fun IntArray.differences(): IntArray {
        return IntArray(size - 1) { i -> this[i + 1] - this[i] }
    }
}
