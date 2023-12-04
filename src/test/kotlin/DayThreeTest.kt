import org.junit.jupiter.api.Test
import solvers.DayThreeSolver
import kotlin.test.assertEquals

class DayThreeTest {

    @Test
    fun testGetNumbersFromString() {
        val path = "src/test/inputs/day03.in"
        val solver = DayThreeSolver(path)
        val numbers = solver.getNumbersFromString(solver.input[0], 0)
        assertEquals(numbers[0], DayThreeSolver.MatrixNumber(467, 0, 2, 0))
        assertEquals(numbers[1], DayThreeSolver.MatrixNumber(114, 5, 7, 0))
    }

    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day03.in"
        val solver = DayThreeSolver(path)
        assertEquals("4361", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day03.in"
        val solver = DayThreeSolver(path)
        assertEquals("467835", solver.solvePart2())
    }
}