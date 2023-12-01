package solvers

abstract class DaySolver(val inputPath: String) {
    abstract fun parseInput()
    abstract fun solvePart1() : String
    abstract fun solvePart2() : String
    fun printSolution() {
        println("Part One: ${solvePart1()}")
        println("Part Two: ${solvePart2()}")
    }
}