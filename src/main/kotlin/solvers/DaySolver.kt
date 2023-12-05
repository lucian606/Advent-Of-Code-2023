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
    protected fun parseInput(groupOnOneLine: Boolean = false) {
        val file = File(inputPath)
        if (groupOnOneLine) {
            var result = ""
            file.useLines { lines -> lines.forEach { result += it + "\n" } }
            input.add(result)
        } else {
            file.useLines { lines -> lines.forEach { input.add(it) } }
        }
    }

    fun convertStringToList(listStr: String): List<Long> {
        return listStr.split(" ")
            .filter { !it.isEmpty() }
            .map { x -> x.toLong() }
    }

    init {
        parseInput()
    }
}