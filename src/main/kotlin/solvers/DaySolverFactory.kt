package solvers

class DaySolverFactory {
    fun getDaySolver(dayNumber: Int): DaySolver? {
        val dayNumberString = if (dayNumber < 10) "0$dayNumber" else dayNumber.toString()
        val path = "src/main/inputs/day$dayNumberString.in"
        return when (dayNumber) {
            1 -> DayOneSolver(path)
            2 -> DayTwoSolver(path)
            3 -> DayThreeSolver(path)
            4 -> DayFourSolver(path)
            5 -> DayFiveSolver(path)
            else -> null
        }
    }
}