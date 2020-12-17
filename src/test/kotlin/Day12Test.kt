import Day12.actionSequence
import Day12.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day12Test {

    @Test
    internal fun waypointShipActions() {
        val input = """
            F10
            N3
            F7
            R90
            F11
        """.trimIndent()

        val actions = input
            .actionSequence()
            .toList()

        assertEquals(286, partTwo(actions))
    }

    @Test
    internal fun waypointShip() {
        val ship = Day12.ShipWithWaypoint()
        ship.forward(10)
        assertEquals(Day12.Vector2(100, 10), ship.position)
        ship.north(3)
        ship.forward(7)
        assertEquals(Day12.Vector2(170, 38), ship.position)
    }
}
