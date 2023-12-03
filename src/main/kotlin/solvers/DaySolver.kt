package solvers

import java.io.File

abstract class DaySolver(val inputPath: String) {
    val input = mutableListOf<String>()
    abstract fun solvePart1() : String
    abstract fun solvePart2() : String
    fun printSolution() {
        println("Part One: ${solvePart1()}")
        println("Part Two: ${solvePart2()}")
    }
    fun parseInput() {
        val file = File(inputPath)
        file.useLines { lines -> lines.forEach { input.add(it) } }
    }
    init {
        parseInput()
    }
}