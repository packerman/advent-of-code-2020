import com.google.common.io.Resources
import java.lang.Integer.parseInt

fun main() {
    val passports =
        Day4.readPassports(Resources.getResource("inputs/day4.txt").readText())

    println(passports.count(Day4::hasRequiredFields))
    println(passports.count(Day4::isValidPassport))
}

private typealias Passport = Map<String, String>

private typealias Validator = (String) -> Boolean

private object Day4 {

    fun countValidPassports(input: String): Int {
        return readPassports(input)
            .count(this::hasRequiredFields)
    }

    fun readPassports(input: String): List<Passport> = input.trim()
        .split("\n\n")
        .map(this::parsePassport)

    private val whitespace = Regex("\\s")

    private fun parsePassport(input: String): Passport {
        return input.split(whitespace)
            .map { entry ->
                val (key, value) = entry.split(":", limit = 2)
                key to value
            }.toMap()
    }

    fun hasRequiredFields(passport: Passport): Boolean =
        requiredFields.keys.all { it in passport }

    fun isValidPassport(passport: Passport): Boolean =
        requiredFields.all { (field, validator) ->
            val value = passport[field]
            value != null && validator(value)
        }

    private val yearRegex = Regex("\\d{4}")

    val BirthYearValidator: Validator = { string ->
        yearRegex.matches(string) && parseInt(string) in 1920..2002
    }

    val IssueYearValidator: Validator = { string ->
        yearRegex.matches(string) && parseInt(string) in 2010..2020
    }

    val ExpirationYearValidator: Validator = { string ->
        yearRegex.matches(string) && parseInt(string) in 2020..2030
    }

    object HeightValidator: Validator {

        private val regex = Regex("(\\d{2,3})(cm|in)")

        override fun invoke(string: String): Boolean {
            val matchResult = regex.matchEntire(string) ?: return false
            val height = parseInt(matchResult.groups[1]?.value)
            val unit = requireNotNull(matchResult.groups[2]?.value)
            return if (unit == "cm") {
                height in 150..193
            } else {
                height in 59..76
            }
        }
    }

    object HairColorValidator: Validator {

        private val regex = Regex("#[0-9a-f]{6}")

        override fun invoke(string: String): Boolean = regex.matches(string)
    }

    object EyeColorValidator: Validator {

        private val values = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

        override fun invoke(string: String): Boolean = string in values
    }

    object PassportIDValidator: Validator {

        private val regex = Regex("\\d{9}")

        override fun invoke(string: String): Boolean = regex.matches(string)
    }

    private val requiredFields = mapOf(
        "byr" to BirthYearValidator,
        "iyr" to IssueYearValidator,
        "eyr" to ExpirationYearValidator,
        "hgt" to HeightValidator,
        "hcl" to HairColorValidator,
        "ecl" to EyeColorValidator,
        "pid" to PassportIDValidator)
}
