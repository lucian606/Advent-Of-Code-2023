import solvers.DayEightSolver
import org.junit.jupiter.api.Test
import solvers.DayElevenSolver
import kotlin.test.assertEquals

class DayElevenTest {
    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day11.in"
        val solver = DayElevenSolver(path)
        assertEquals("374", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day11.in"
        val solver = DayElevenSolver(path)
        assertEquals("82000210", solver.solvePart2())
    }
}