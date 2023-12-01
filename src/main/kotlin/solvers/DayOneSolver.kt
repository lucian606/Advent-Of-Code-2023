package solvers

import java.io.File

class DayOneSolver(inputPath: String) : DaySolver(inputPath) {

    private val input = mutableListOf<String>()

    init {
        parseInput()
    }

    override fun parseInput() {
        val file = File(inputPath)
        file.useLines { lines -> lines.forEach { input.add(it) } }
    }

    override fun solvePart1(): String {
        var calibrationSum = 0
        for (line in input) {
            val digits = line.filter { it.isDigit() }
            if (digits.length == 1)
                calibrationSum += Character.getNumericValue(digits[0]) * 11
            if (digits.length >= 2)
                calibrationSum += Character.getNumericValue(digits[0]) * 10 + Character.getNumericValue(digits.last())
        }
        return calibrationSum.toString()
    }

    fun getDigitStringValue(digitString: String): Int {
        val digitStrings = listOf(
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine"
        )
        if (digitString in digitStrings)
            return digitStrings.indexOf(digitString) + 1
        return -1
    }

    fun getDigitsFromString(s: String): Pair<Int, Int> {
        var first = 0
        var second = 0
        for (i in s.indices) {
            if (s[i].isDigit()) {
                if (first == 0)
                    first = Character.getNumericValue(s[i])
                else
                    second = Character.getNumericValue(s[i])
            }
            for (j in 3..5) {
                if (i + j > s.length)
                    break
                val digitStringValue = getDigitStringValue(s.subSequence(i, i + j).toString())
                if (digitStringValue != -1) {
                    if (first == 0)
                        first = digitStringValue
                    else
                        second = digitStringValue
                }
            }
        }
        if (second == 0)
            second = first
        return Pair(first, second)
    }

    override fun solvePart2(): String {
        var calibrationSum: Int = 0
        for (line in input) {
            val digits: Pair<Int, Int> = getDigitsFromString(line)
            calibrationSum += digits.first * 10 + digits.second
        }
        return calibrationSum.toString()
    }
}