package solvers

class DayThreeSolver(inputPath: String) : DaySolver(inputPath) {

    data class MatrixNumber(val value: Int, val start: Int, val end: Int, val row: Int)

    fun getNumbersFromString(str: String, row: Int): List<MatrixNumber> {
        val result = ArrayList<MatrixNumber>()
        var i = 0
        while (i < str.length) {
            if (str[i].isDigit()) {
                var currentValue = Character.getNumericValue(str[i])
                val start = i
                var end = -1
                i++
                while (i < str.length && str[i].isDigit()) {
                    currentValue = currentValue * 10 + Character.getNumericValue(str[i])
                    i++
                }
                end = i - 1

                result.add(MatrixNumber(currentValue, start, end, row))
            } else {
                i++
            }
        }
        return result
    }

    fun isSymbol(row: Int, col: Int, str: List<String>, filterGears: Boolean = false): Boolean {
        if (row >= str.size
            || row < 0
            || col < 0
            || col >= str[0].length)
            return false
        if (filterGears) {
            return str[row][col] == '*'
        }
        if (str[row][col].isDigit()
            || str[row][col] == '.')
            return false
        return true
    }

    fun isPartNumber(number: MatrixNumber, str: List<String>, filterGears: Boolean = false): Boolean {
        for (i in number.row-1..number.row+1) {
            for (j in number.start-1..number.end+1) {
                if (isSymbol(i, j, str, filterGears)) {
                    return true
                }
            }
        }
        return false
    }

    override fun solvePart1(): String {
        var sum = 0
        for ((i, line) in input.withIndex()) {
            val numbers = getNumbersFromString(line, i)
            for (number in numbers) {
                if (isPartNumber(number, input)) {
                    sum += number.value
                }
            }
        }
        return sum.toString()
    }

    fun isNearNumber(row: Int, col: Int, number: MatrixNumber): Boolean {
        return (col in number.start-1..number.end+1
                && row in number.row-1..number.row+1)
    }

    override fun solvePart2(): String {
        val gearNumbers = ArrayList<MatrixNumber>()
        var gearRatioSum = 0
        for ((i, line) in input.withIndex()) {
            val numbers = getNumbersFromString(line, i)
            for (number in numbers) {
                if (isPartNumber(number, input, true)) {
                    gearNumbers.add(number)
                }
            }
        }
        for (i in 0 until input.size) {
            for (j in 0 until input[0].length) {
                if (input[i][j] == '*') {
                    val nearbyGearNumbers = ArrayList<MatrixNumber>()
                    for (partNumber in gearNumbers) {
                        if (isNearNumber(i, j, partNumber)) {
                            nearbyGearNumbers.add(partNumber)
                        }
                    }
                    if (nearbyGearNumbers.size == 2) {
                        gearRatioSum += nearbyGearNumbers[0].value * nearbyGearNumbers[1].value
                    }
                }
            }
        }
        return gearRatioSum.toString()
    }
}