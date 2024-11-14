import solvers.DaySolverFactory

fun main() {
    val daySolverFactory = DaySolverFactory()
    val dayNumber = getDayNumberFromToday()
    val daySolver = daySolverFactory.getDaySolver(10)
    daySolver!!.printSolution()
}