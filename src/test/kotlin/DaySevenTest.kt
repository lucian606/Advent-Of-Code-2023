import org.junit.jupiter.api.Test
import solvers.DaySevenSolver
import kotlin.test.assertEquals

class DaySevenTest {
    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day07.in"
        val solver = DaySevenSolver(path)
        assertEquals("6440", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day07.in"
        val solver = DaySevenSolver(path)
        assertEquals("5905", solver.solvePart2())
    }
}