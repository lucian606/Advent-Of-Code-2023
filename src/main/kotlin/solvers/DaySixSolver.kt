package solvers

class DaySixSolver(inputPath: String) : DaySolver(inputPath) {

    private fun canBeatRecordDistance(time: Long, holdDuration: Long, recordDistance: Long): Boolean {
        return ((time - holdDuration) * holdDuration) > recordDistance
    }

    private fun getTotalWaysToWinRace(time: Long, recordDistance: Long): Long {
        var waysToWin: Long = 0
        for (holdDuration in 1 until time) {
            if (canBeatRecordDistance(time, holdDuration, recordDistance)) {
                waysToWin += 1
            }
        }
        return waysToWin
    }

    override fun solvePart1(): String {
        val times = convertStringToList(input[0].split(":")[1])
        val recordDistances = convertStringToList(input[1].split(":")[1])
        var totalWaysToWin: Long = 1
        for (i in times.indices) {
            totalWaysToWin *= getTotalWaysToWinRace(times[i], recordDistances[i])
        }
        return totalWaysToWin.toString()
    }

    override fun solvePart2(): String {
        val time = input[0].split(":")[1].replace(" ", "").toLong()
        val recordDistance = input[1].split(":")[1].replace(" ", "").toLong()
        return getTotalWaysToWinRace(time, recordDistance).toString()
    }
}
