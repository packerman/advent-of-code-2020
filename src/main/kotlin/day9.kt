import Day9.findEncryptionWeakness
import com.google.common.io.Resources
import java.lang.Long.parseLong

fun main() {
    val numbers = Resources.getResource("inputs/day9.txt")
        .readText()
        .trim()
        .lines()
        .map { parseLong(it) }
        .toLongArray()

    val invalidNumber = requireNotNull(Day9.findFirstInvalidNumber(numbers, 25)) { "All number are valid" }
    println(invalidNumber)
    println(numbers.findEncryptionWeakness(invalidNumber))
}

object Day9 {

    fun findFirstInvalidNumber(numbers: LongArray, window: Int): Long? {
        fun Set<Long>.hasPairSumTo(number: Long): Boolean {
            for (elem in this) {
                if (number - elem in this) {
                    return true
                }
            }
            return false
        }

        val previous = mutableSetOf<Long>()
        (0 until window).forEach { previous += numbers[it] }
        for (i in window until numbers.size) {
            val number = numbers[i]
            if (!previous.hasPairSumTo(number)) {
                return number
            }
            previous -= numbers[i - window]
            previous += number
        }
        return null
    }

    fun LongArray.findRangeThatSumTo(sum: Long): Pair<Int, Int>? {
        var partialSum = 0L
        var i = 0
        var j = -1
        while (i < this.size) {
            if (partialSum < sum) {
                while (partialSum < sum) {
                    partialSum += this[++j]
                }
            } else if (partialSum > sum) {
                while (partialSum > sum) {
                    partialSum -= this[j--]
                }
            }
            if (partialSum == sum) {
                return i to j
            } else {
                partialSum -= this[i++]
            }
        }
        return null
    }

    fun LongArray.findMinMax(i: Int, j: Int): Pair<Long, Long> {
        var min = Long.MAX_VALUE
        var max = Long.MIN_VALUE
        for (k in i..j) {
            if (this[k] < min) {
                min = this[k]
            }
            if (this[k] > max) {
                max = this[k]
            }
        }
        return min to max
    }

    fun LongArray.findEncryptionWeakness(invalidNumber: Long): Long {
        val (i, j) = requireNotNull(this.findRangeThatSumTo(invalidNumber)) { "Cannot find sequence that sum to $invalidNumber" }
        val (min, max) = this.findMinMax(i, j)
        return min + max
    }
}
