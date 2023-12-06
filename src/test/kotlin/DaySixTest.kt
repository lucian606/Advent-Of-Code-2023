import org.junit.jupiter.api.Test
import solvers.DaySixSolver
import kotlin.test.assertEquals

class DaySixTest {
    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day06.in"
        val solver = DaySixSolver(path)
        assertEquals("288", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day06.in"
        val solver = DaySixSolver(path)
        assertEquals("71503", solver.solvePart2())
    }
}