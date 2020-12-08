import com.google.common.io.Resources
import java.lang.Integer.parseInt

fun main() {
    val input = Resources.getResource("inputs/day7.txt").readText()
    val rules = Day7.readInput(input)

    println(Day7.countBagsContaining("shiny gold", rules))

    println(Day7.countBagsWithin("shiny gold", rules))
}

object Day7 {

    fun readInput(input: String): List<Rule> =
        input.trim()
            .lines()
            .map(Rule.Companion::parse)

    private fun reverseRules(rules: List<Rule>): Map<String, Set<String>> {
        val result = mutableMapOf<String, MutableSet<String>>()
        for (rule in rules) {
            for (bag in rule.contained.keys) {
                result.putIfAbsent(bag, mutableSetOf())
                result[bag]?.add(rule.containing)
            }
        }
        return result
    }

    fun countBagsContaining(bag: String, rules: List<Rule>): Int {
        val reversed = reverseRules(rules)
        fun countBagsContaining(currentBag: String): Set<String> {
            val includingBags = reversed[currentBag] ?: emptySet()
            val result = mutableSetOf<String>()
            result.add(currentBag)
            for (includingBag in includingBags) {
                result.addAll(countBagsContaining(includingBag))
            }
            return result
        }
        return countBagsContaining(bag).size - 1
    }

    fun countBagsWithin(bag: String, rules: List<Rule>): Int {
        val ruleMap = rules.map { it.containing to it }.toMap()
        fun countBagsWithin(currentBag: String): Int {
            val rule = requireNotNull(ruleMap[currentBag])
            return rule.contained.map { (_, count) -> count }.sum() +
                    rule.contained.map { (nextBag, count) -> count * countBagsWithin(nextBag) }.sum()
        }
        return countBagsWithin(bag)
    }

    data class Rule(val containing: String, val contained: Map<String, Int>) {

        companion object {

            private const val noOtherBagsText = "no other bags";

            private val mainRegex = Regex("([a-z ]+) bags contain ([a-z0-9 ,]+|$noOtherBagsText)\\.")
            private val containedRegex = Regex("(\\d+) ([a-z ]+) (?:bag|bags)")

            fun parse(input: String): Rule {
                val matchResult = requireNotNull(mainRegex.matchEntire(input)) { "Cannot match rule: '$input'" }
                if (matchResult.groupValues[2] == noOtherBagsText) {
                    return Rule(matchResult.groupValues[1], mapOf())
                }

                val items = matchResult.groupValues[2]
                    .split(", ")
                    .map(::parseBagIn)
                    .toMap()

                return Rule(matchResult.groupValues[1], items)
            }

            private fun parseBagIn(input: String): Pair<String, Int> {
                val matchResult = requireNotNull(containedRegex.matchEntire(input)) { "Cannot match '$input'" }
                return matchResult.groupValues[2] to parseInt(matchResult.groupValues[1])
            }
        }
    }

}
