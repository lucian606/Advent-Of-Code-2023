import org.junit.jupiter.api.Test
import solvers.DayFiveSolver
import kotlin.test.assertEquals

class DayFiveTest {
    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day05.in"
        val solver = DayFiveSolver(path)
        assertEquals("35", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day05.in"
        val solver = DayFiveSolver(path)
        assertEquals("46", solver.solvePart2())
    }
}