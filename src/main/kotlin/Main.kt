import solvers.DaySolverFactory

fun main() {
    val daySolverFactory = DaySolverFactory()
    var dayNumber = getDayNumberFromToday()
    var daySolver = daySolverFactory.getDaySolver(dayNumber)
    daySolver!!.printSolution()
}