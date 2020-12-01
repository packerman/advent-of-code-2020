import com.google.common.io.Resources
import java.lang.Integer.parseInt

class Day1(private val numbers: Set<Int>) {

    fun findPairThatSum(sum: Int): Pair<Int, Int>? {
        for (number in numbers) {
            if (sum - number in numbers) {
                return number to sum - number
            }
        }
        return null
    }

    fun findTripleThatSum(sum: Int): Triple<Int, Int, Int>? {
        for (number in numbers) {
            val found = findPairThatSum(sum - number)
            if (found != null) {
                val (n1, n2) = found
                if (n1 != number && n2 != number) {
                    return Triple(number, n1, n2)
                }
            }
        }
        return null
    }
}

fun main() {
    val inputText = Resources.getResource("inputs/day1.txt").readText()
    val input = inputText.lines()
        .filter { it.isNotEmpty() }
        .map { parseInt(it) }
        .toSortedSet()
    val day1 = Day1(input)
    part1(day1)
    part2(day1)
}

private fun part1(day1: Day1) {
    val (n1, n2) = requireNotNull(day1.findPairThatSum(2020))
    val output = n1 * n2
    println(output)
}

private fun part2(day1: Day1) {
    val (n1, n2, n3) = requireNotNull(day1.findTripleThatSum(2020))
    val output = n1 * n2 * n3
    println(output)
}
