import com.google.common.io.Resources

fun main() {
    val input = Resources.getResource("inputs/day6.txt").readText()
    println(Day6.collectAnswers(input, Set<Char>::union).map(Set<Char>::size).sum())
    println(Day6.collectAnswers(input, Set<Char>::intersect).map(Set<Char>::size).sum())
}

object Day6 {

    fun collectAnswers(input: String, operation: (acc: Set<Char>, Set<Char>) -> Set<Char>): List<Set<Char>> =
        input.trim()
            .split("\n\n")
            .map { group ->
                group.lines()
                    .map(String::toSet)
                    .reduce(operation)
            }
}
