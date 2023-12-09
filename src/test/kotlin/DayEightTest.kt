import solvers.DayEightSolver
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DayEightTest {
    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day08part1.in"
        val solver = DayEightSolver(path)
        assertEquals("6", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day08part2.in"
        val solver = DayEightSolver(path)
        assertEquals("6", solver.solvePart2())
    }
}