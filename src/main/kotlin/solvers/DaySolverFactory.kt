package solvers

class DaySolverFactory() {
    fun getDaySolver(dayNumber: Int): DaySolver? {
        return when (dayNumber) {
            1 -> DayOneSolver("src/main/inputs/day01.in")
            2 -> DayTwoSolver("src/main/inputs/day02.in")
            else -> null
        }
    }
}