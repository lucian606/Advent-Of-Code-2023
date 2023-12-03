package solvers

class DayTwoSolver(inputPath: String) : DaySolver(inputPath) {

    private val RED = 0
    private val GREEN = 1
    private val BLUE = 2
    private val MAX_RED = 12
    private val MAX_GREEN = 13
    private val MAX_BLUE = 14
    private val COLORS = listOf("red", "green", "blue")

    fun getBallsFromRound(round: String): List<Int> {
        val result = mutableListOf<Int>(0, 0, 0)
        val ballGroups = round.split(", ")
        for (group in ballGroups) {
            val ballsAmount = group.split(' ')[0].toInt()
            val color = COLORS.indexOf(group.split(' ')[1])
            result[color] = ballsAmount
        }
        return result
    }

    override fun solvePart1(): String {
        var idSum = 0
        for ((i, line) in input.withIndex()) {
            val roundsString = line.split(':')[1]
            val rounds = roundsString.split(';')
            var isRoundValid = true
            for (round in rounds) {
                val balls = getBallsFromRound(round.trim())
                if (balls[RED] > MAX_RED
                    || balls[GREEN] > MAX_GREEN
                    || balls[BLUE] > MAX_BLUE) {
                    isRoundValid = false
                    break
                }
            }
            if (isRoundValid)
                idSum += (i + 1)
        }
        return idSum.toString()
    }

    override fun solvePart2(): String {
        var powerSum = 0
        for (line in input) {
            val roundsString = line.split(':')[1]
            val rounds = roundsString.split(';')
            val minimumCubeQuantities = mutableListOf(0, 0, 0)
            for (round in rounds) {
                val balls = getBallsFromRound(round.trim())
                if (balls[RED] > minimumCubeQuantities[RED]) {
                    minimumCubeQuantities[RED] = balls[RED]
                }
                if (balls[GREEN] > minimumCubeQuantities[GREEN]) {
                    minimumCubeQuantities[GREEN] = balls[GREEN]
                }
                if (balls[BLUE] > minimumCubeQuantities[BLUE]) {
                    minimumCubeQuantities[BLUE] = balls[BLUE]
                }
            }
            powerSum += minimumCubeQuantities.reduce({ acc, x -> x * acc })
        }
        return powerSum.toString()
    }
}