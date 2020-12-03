import com.google.common.io.Resources

fun main() {
    val map = Resources.getResource("inputs/day3.txt")
        .readText()
        .lines()
        .filter(String::isNotEmpty)
        .toTypedArray()
    println(howManyTreeEncountered(map, down = 1, right = 3))
    println(
        howManyTreeEncountered(map, down = 1, right = 1).toLong() *
                howManyTreeEncountered(map, down = 1, right = 3) *
                howManyTreeEncountered(map, down = 1, right = 5) *
                howManyTreeEncountered(map, down = 1, right = 7) *
                howManyTreeEncountered(map, down = 2, right = 1)
    )
}

fun howManyTreeEncountered(map: Array<String>, down: Int, right: Int): Int {
    require(map.isNotEmpty())
    val width = map[0].length
    var x = 0
    var y = 0
    var count = 0
    while (y < map.size) {
        if (map[y][x] == '#') {
            count++
        }
        x = (x + right) % width
        y += down
    }
    return count
}
