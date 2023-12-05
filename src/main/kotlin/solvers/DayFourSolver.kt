package solvers

class DayFourSolver(inputPath: String) : DaySolver(inputPath) {

    private fun getCardPoints(winningNumbers: List<Long>, pickedNumbers: List<Long>): Int {
        val matchedNumbers = winningNumbers.intersect(pickedNumbers.toSet()).size
        return if (matchedNumbers == 0) 0 else 1 shl matchedNumbers - 1
    }

    override fun solvePart1(): String {
        var points = 0
        for (line in input) {
            val numbers = line.split(":")[1]
            val winningNumbersString = numbers.split("|")[0].trim()
            val pickedNumbersString = numbers.split("|")[1].trim()
            val winningNumbers = convertStringToList(winningNumbersString)
            val pickedNumbers = convertStringToList(pickedNumbersString)
            points += getCardPoints(winningNumbers, pickedNumbers)
        }
        return points.toString()
    }

    private fun getMatchedNumbers(winningNumbers: List<Long>, pickedNumbers: List<Long>): Int {
        return winningNumbers.intersect(pickedNumbers.toSet()).size
    }

    override fun solvePart2(): String {
        val cardCopies = MutableList(input.size) { 1 }
        for ((i, line) in input.withIndex()) {
            val numbers = line.split(":")[1]
            val winningNumbersString = numbers.split("|")[0].trim()
            val pickedNumbersString = numbers.split("|")[1].trim()
            val winningNumbers = convertStringToList(winningNumbersString)
            val pickedNumbers = convertStringToList(pickedNumbersString)
            val matchedNumbers = getMatchedNumbers(winningNumbers, pickedNumbers)
            for (j in i + 1 .. i + matchedNumbers) {
                if (j == cardCopies.size) {
                    break
                }
                cardCopies[j] += cardCopies[i]
            }
        }
        return cardCopies.sum().toString()
    }
}