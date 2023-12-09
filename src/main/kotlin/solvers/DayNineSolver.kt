package solvers

import java.util.Locale

class DayNineSolver(inputPath: String) : DaySolver(inputPath) {

    fun getDifferncesBetweenElements(l: List<Long>): List<Long> {
        val result = ArrayList<Long>()
        for (i in 0 until l.size - 1) {
            result.add(l[i + 1] - l[i])
        }
        return result
    }

    override fun solvePart1(): String {
        var sum: Long = 0
        for (line in input) {
            val history = convertStringToList(line)
            val sequences = ArrayList<List<Long>>()
            sequences.add(history)
            while (!sequences.last().all {it == 0L}) {
                val sequence = getDifferncesBetweenElements(sequences.last())
                sequences.add(sequence)
            }
            var extraValue = 0L
            for (i in sequences.indices.reversed())  {
                extraValue += sequences[i].last()
            }
            sum += extraValue
        }
        return sum.toString()
    }

    override fun solvePart2(): String {
        var sum: Long = 0
        for (line in input) {
            val history = convertStringToList(line)
            val sequences = ArrayList<List<Long>>()
            sequences.add(history)
            while (!sequences.last().all {it == 0L}) {
                val sequence = getDifferncesBetweenElements(sequences.last())
                sequences.add(sequence)
            }
            var extraValue = 0L
            for (i in sequences.indices.reversed())  {
                extraValue = sequences[i][0] - extraValue
            }
            sum += extraValue
        }
        return sum.toString()
    }
}