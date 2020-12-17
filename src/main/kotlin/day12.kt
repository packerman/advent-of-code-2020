import Day12.actionSequence
import com.google.common.io.Resources
import java.lang.Integer.parseInt
import kotlin.math.abs

fun main() {

    val actions = Resources.getResource("inputs/day12.txt")
        .readText()
        .trim()
        .actionSequence()
        .toList()

    println(Day12.partOne(actions))

    println(Day12.partTwo(actions))
}

object Day12 {

    fun String.actionSequence(): Sequence<Pair<Char, Int>> {
        val regex = Regex("([A-Z])(\\d+)")
        return this.lineSequence()
            .map { line ->
                val matchResult = requireNotNull(regex.matchEntire(line)) { "Incorrect operations '$line'" }
                matchResult.groupValues[1][0] to parseInt(matchResult.groupValues[2])
            }
    }

    class Ship {

        var x: Int = 0
            private set

        var y: Int = 0
            private set

        private var tx = 1
        private var ty = 0

        fun north(value: Int) {
            y += value
        }

        fun south(value: Int) {
            y -= value
        }

        fun east(value: Int) {
            x += value
        }

        fun west(value: Int) {
            x -= value
        }

        fun left(degrees: Int) {
            when (degrees) {
                90 -> setDirection(-ty, tx)
                180 -> setDirection(-tx, -ty)
                270 -> setDirection(ty, -tx)
                else -> error("Unrecognized angle: $degrees")
            }
        }

        fun right(degrees: Int) {
            when (degrees) {
                90 -> setDirection(ty, -tx)
                180 -> setDirection(-tx, -ty)
                270 -> setDirection(-ty, tx)
                else -> error("Unrecognized angle: $degrees")
            }
        }

        fun forward(value: Int) {
            x += tx * value
            y += ty * value
        }

        private fun setDirection(tx: Int, ty: Int) {
            this.tx = tx
            this.ty = ty
        }
    }

    data class Vector2(val x: Int, val y: Int) {

        fun updateX(dx: Int): Vector2 = copy(x = x + dx)

        fun updateY(dy: Int): Vector2 = copy(y = y + dy)

        operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)

        operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)

        operator fun times(factor: Int) = Vector2(x * factor, y * factor)
    }

    class ShipWithWaypoint {

        private var waypointRelative = Vector2(10, 1)

        var position = Vector2(0, 0)
            private set

        fun north(value: Int) {
            waypointRelative = waypointRelative.updateY(value)
        }

        fun south(value: Int) {
            waypointRelative = waypointRelative.updateY(-value)
        }

        fun east(value: Int) {
            waypointRelative = waypointRelative.updateX(value)
        }

        fun west(value: Int) {
            waypointRelative = waypointRelative.updateX(-value)
        }

        fun left(degrees: Int) {
            waypointRelative = when (degrees) {
                90 -> waypointRelative.copy(x = -waypointRelative.y, y = waypointRelative.x)
                180 -> waypointRelative.copy(x = -waypointRelative.x, y = -waypointRelative.y)
                270 -> waypointRelative.copy(x = waypointRelative.y, y = -waypointRelative.x)
                else -> error("Unrecognized angle: $degrees")
            }
        }

        fun right(degrees: Int) {
            waypointRelative = when (degrees) {
                90 -> waypointRelative.copy(x = waypointRelative.y, y = -waypointRelative.x)
                180 -> waypointRelative.copy(x = -waypointRelative.x, y = -waypointRelative.y)
                270 -> waypointRelative.copy(x = -waypointRelative.y, y = waypointRelative.x)
                else -> error("Unrecognized angle: $degrees")
            }
        }

        fun forward(value: Int) {
            position += waypointRelative * value
        }
    }

    fun partOne(actions: List<Pair<Char, Int>>): Int {
        val ship = Ship()

        actions
            .forEach { (action, value) ->
                when (action) {
                    'N' -> ship.north(value)
                    'S' -> ship.south(value)
                    'E' -> ship.east(value)
                    'W' -> ship.west(value)
                    'L' -> ship.left(value)
                    'R' -> ship.right(value)
                    'F' -> ship.forward(value)
                    else -> error("Unrecognized action: $action")
                }
            }
        return abs(ship.x) + abs(ship.y)
    }

    fun partTwo(actions: List<Pair<Char, Int>>): Int {
        val ship = ShipWithWaypoint()

        actions
            .forEach { (action, value) ->
                when (action) {
                    'N' -> ship.north(value)
                    'S' -> ship.south(value)
                    'E' -> ship.east(value)
                    'W' -> ship.west(value)
                    'L' -> ship.left(value)
                    'R' -> ship.right(value)
                    'F' -> ship.forward(value)
                    else -> error("Unrecognized action: $action")
                }
            }
        return abs(ship.position.x) + abs(ship.position.y)
    }
}
