import com.google.common.io.Resources
import java.lang.Integer.parseInt

fun main() {
    val records = Resources.getResource("inputs/day2.txt")
        .readText()
        .lines()
        .filter(String::isNotEmpty)
        .map(Record.Companion::fromString)
    println(records.count(Record::isValidOldPolicy))
    println(records.count(Record::isValidNewPolicy))
}

data class Record(
    val occurrences: IntRange,
    val char: Char,
    val password: String
) {
    companion object {

        private val recordPattern = Regex("(\\d+)-(\\d+) (\\w): (\\w+)")

        fun fromString(line: String): Record {
            val match = requireNotNull(recordPattern.matchEntire(line)) { "Cannot parse line: '$line'" }
            return Record(
                occurrences = parseInt(match.groups[1]?.value)..parseInt(match.groups[2]?.value),
                char = requireNotNull(match.groups[3]?.value?.get(0)),
                password = requireNotNull(match.groups[4]?.value)
            )
        }
    }
}

fun Record.isValidOldPolicy(): Boolean =
    password.count { it == char } in occurrences

fun Record.isValidNewPolicy(): Boolean =
    (password[occurrences.first - 1] == char) xor (password[occurrences.last - 1] == char)
