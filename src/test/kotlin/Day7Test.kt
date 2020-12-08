import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day7Test {

    @Test
    internal fun shouldParseRule() {
        val rule = Day7.Rule.parse("light red bags contain 1 bright white bag, 2 muted yellow bags.")
        assertEquals(
            Day7.Rule("light red", mapOf("bright white" to 1, "muted yellow" to 2)),
            rule
        )
    }

    @Test
    internal fun shouldParseRuleForBagThatContainsNoOtherBags() {
        val rule = Day7.Rule.parse("dotted black bags contain no other bags.")
        assertEquals(
            Day7.Rule("dotted black", mapOf()),
            rule
        )
    }

    @Test
    internal fun shouldParseRuleWithOneBag() {
        val rule = Day7.Rule.parse("bright white bags contain 1 shiny gold bag.")
        assertEquals(
            Day7.Rule("bright white", mapOf("shiny gold" to 1)),
            rule
        )
    }

    @Test
    internal fun shouldCountBagsContaining() {
        val input = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.
        """.trimIndent()

        val rules = Day7.readInput(input)

        assertEquals(4, Day7.countBagsContaining("shiny gold", rules))
    }

    @Test
    internal fun shouldParseRule2() {
        val rule = Day7.Rule.parse("mirrored yellow bags contain 1 drab lavender bag, 4 shiny gold bags, 3 drab turquoise bags, 2 light silver bags.")
        assertEquals(
            Day7.Rule("mirrored yellow", mapOf("drab lavender" to 1, "shiny gold" to 4, "drab turquoise" to 3, "light silver" to 2)),
            rule
        )
    }

    @Test
    internal fun shouldCountBagsWithin() {
        val input = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.
        """.trimIndent()

        val rules = Day7.readInput(input)

        assertEquals(0, Day7.countBagsWithin("faded blue", rules))
        assertEquals(0, Day7.countBagsWithin("dotted black", rules))
        assertEquals(11, Day7.countBagsWithin("vibrant plum", rules))
        assertEquals(7, Day7.countBagsWithin("dark olive", rules))
        assertEquals(32, Day7.countBagsWithin("shiny gold", rules))
    }
}
