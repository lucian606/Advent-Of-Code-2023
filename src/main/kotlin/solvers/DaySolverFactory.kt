package solvers

class DaySolverFactory() {
    fun getDaySolver(dayNumber: Int): DayOneSolver? {
        return when (dayNumber) {
            1 -> DayOneSolver("src/main/inputs/day01.in")
            else -> null
        }
    }
}