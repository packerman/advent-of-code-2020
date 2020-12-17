import Day11.Board
import Day11.fixedPoint
import com.google.common.io.Resources

fun main() {

    val board = Board.fromString(
        Resources.getResource("inputs/day11.txt")
            .readText()
    )

    with(Day11PartOne) {
        println(fixedPoint(board, { it.generateNewBoard() }).occupiedCount)
    }

    with(Day11PartTwo) {
        println(fixedPoint(board, { it.generateNewBoard() }).occupiedCount)
    }
}

object Day11 {

    class Board(
        private val board: Array<CharArray>
    ) {

        init {
            require(board.isNotEmpty())
        }

        val height = board.size
        val width = board[0].size

        operator fun get(i: Int, j: Int): Char {
            if ((i !in 0 until height) || (j !in 0 until width)) {
                return '.'
            }
            return board[i][j]
        }

        val occupiedCount: Int
            get() = board.asSequence()
                .flatMap { it.asSequence() }
                .count { it == '#' }

        fun firstSeatInDirection(i: Int, j: Int, di: Int, dj: Int): Char? {
            var x = i + di
            var y = j + dj
            while (x in 0 until height && y in 0 until width) {
                val char = board[x][y]
                if (char == 'L' || char == '#') {
                    return char
                }
                x += di
                y += dj
            }
            return null
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Board

            if (!board.contentDeepEquals(other.board)) return false

            return true
        }

        override fun hashCode(): Int {
            return board.contentDeepHashCode()
        }

        companion object {

            fun fromString(input: String): Board = Board(
                input
                    .trim()
                    .lines()
                    .map(String::toCharArray)
                    .toTypedArray()
            )
        }
    }

    fun <T> fixedPoint(seed: T, nextFn: (T) -> T): T {
        fun generate() = generateSequence(seed) {
            val next = nextFn(it)
            if (next == it) null else next
        }
        return generate().last()
    }
}

object Day11PartOne {

    fun Board.generateNewBoard(): Board = Board(Array(this.height) { i ->

        CharArray(this.width) { j ->
            if (this[i, j] == '.') '.'
            else {
                val adjacentOccupied =
                    (if (this[i - 1, j - 1] == '#') 1 else 0) +
                            (if (this[i - 1, j] == '#') 1 else 0) +
                            (if (this[i - 1, j + 1] == '#') 1 else 0) +
                            (if (this[i, j + 1] == '#') 1 else 0) +
                            (if (this[i + 1, j + 1] == '#') 1 else 0) +
                            (if (this[i + 1, j] == '#') 1 else 0) +
                            (if (this[i + 1, j - 1] == '#') 1 else 0) +
                            (if (this[i, j - 1] == '#') 1 else 0)
                when {
                    this[i, j] == 'L' && adjacentOccupied == 0 -> '#'
                    this[i, j] == '#' && adjacentOccupied >= 4 -> 'L'
                    else -> this[i, j]
                }
            }
        }
    })
}

object Day11PartTwo {

    fun Board.generateNewBoard(): Board {
        fun countOccupied(i: Int, j: Int, di: Int, dj: Int): Int {
            return if (firstSeatInDirection(i, j, di, dj) == '#') 1 else 0
        }

        return Board(Array(this.height) { i ->

            CharArray(this.width) { j ->
                if (this[i, j] == '.') '.'
                else {
                    val occupied =
                        countOccupied(i, j, -1, -1) +
                                countOccupied(i, j, -1, 0) +
                                countOccupied(i, j, -1, +1) +
                                countOccupied(i, j, 0, +1) +
                                countOccupied(i, j, +1, +1) +
                                countOccupied(i, j, +1, 0) +
                                countOccupied(i, j, +1, -1) +
                                countOccupied(i, j, 0, -1)
                    when {
                        this[i, j] == 'L' && occupied == 0 -> '#'
                        this[i, j] == '#' && occupied >= 5 -> 'L'
                        else -> this[i, j]
                    }
                }
            }
        })
    }
}
