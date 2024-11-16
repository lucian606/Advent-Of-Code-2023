import solvers.DaySolverFactory

fun main() {
    val daySolverFactory = DaySolverFactory()
    val dayNumber = getDayNumberFromToday()
    val daySolver = daySolverFactory.getDaySolver(11)
    daySolver!!.printSolution()
}