import solvers.DayOneSolver
import org.junit.jupiter.api.Test
import solvers.DayTwoSolver
import kotlin.test.assertEquals

class DayTwoTest {

    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day02.in"
        val solver = DayTwoSolver(path)
        assertEquals("8", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day02.in"
        val solver = DayTwoSolver(path)
        assertEquals("2286", solver.solvePart2())
    }
}