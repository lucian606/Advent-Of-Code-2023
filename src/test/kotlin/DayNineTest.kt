import org.junit.jupiter.api.Test
import solvers.DayNineSolver
import kotlin.test.assertEquals

class DayNineTest {
    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day09.in"
        val solver = DayNineSolver(path)
        assertEquals("114", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day09.in"
        val solver = DayNineSolver(path)
        assertEquals("2", solver.solvePart2())
    }
}