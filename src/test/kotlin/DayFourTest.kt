import org.junit.jupiter.api.Test
import solvers.DayFourSolver
import solvers.DayThreeSolver
import kotlin.test.assertEquals

class DayFourTest {

    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day04.in"
        val solver = DayFourSolver(path)
        assertEquals("13", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day04.in"
        val solver = DayFourSolver(path)
        assertEquals("30", solver.solvePart2())
    }
}