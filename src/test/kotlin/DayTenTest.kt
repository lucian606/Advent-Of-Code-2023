import org.junit.jupiter.api.Test
import solvers.DayTenSolver
import kotlin.test.assertEquals

class DayTenTest {
    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day10part1.in"
        val solver = DayTenSolver(path)
        assertEquals("4", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day10part2.in"
        val solver = DayTenSolver(path)
        assertEquals("4", solver.solvePart2())
    }
}