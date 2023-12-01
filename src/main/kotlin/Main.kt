import solvers.DaySolverFactory

fun main() {
    val daySolverFactory = DaySolverFactory()
    var dayNumber = getDayNumberFromToday()
    dayNumber = 1
    var daySolver = daySolverFactory.getDaySolver(dayNumber)
    daySolver!!.printSolution()
}