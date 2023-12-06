import solvers.DayOneSolver
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DayOneTest {
    @Test
    fun testPartOne() {
        val path = "src/test/inputs/day01part1.in"
        val solver = DayOneSolver(path)
        assertEquals("142", solver.solvePart1())
    }

    @Test
    fun testPartTwo() {
        val path = "src/test/inputs/day01part2.in"
        val solver = DayOneSolver(path)
        assertEquals("281", solver.solvePart2())
    }
}