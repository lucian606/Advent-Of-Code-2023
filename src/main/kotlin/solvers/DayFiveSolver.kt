package solvers

import kotlin.math.max
import kotlin.math.min

class DayFiveSolver(inputPath: String) : DaySolver(inputPath) {
    data class MapStep (val destinationStart: Long, val sourceStart: Long, val rangeLength: Long)

    data class Interval(val start: Long, val end: Long)
    private fun intersectIntervals(interval1: Interval, interval2: Interval): Interval? {
        val start = max(interval1.start, interval2.start)
        val end = min(interval1.end, interval2.end)
        if (start > end) {
            return null
        }
        return Interval(start, end)
    }

    private fun getIntervalDifference(interval1: Interval, interval2: Interval): List<Interval> {
        val intersection = intersectIntervals(interval1, interval2) ?: return listOf(interval1)

        val result = ArrayList<Interval>()

        if (interval1.start < intersection.start) {
            result.add(Interval(interval1.start, intersection.start - 1))
        }
        if (interval1.end > intersection.end) {
            result.add(Interval(intersection.end + 1, interval1.end))
        }

        return result
    }


    private var inputAsString = ""

    init {
        input.clear()
        parseInput(true)
        inputAsString = input[0]
    }

    private fun getSeeds(): List<Long> {
        val seedsStr = inputAsString.split("\n\n")[0].split(":")[1]
        return convertStringToList(seedsStr)
    }


    private fun getMapSteps(mapString: String): List<MapStep> {
        val mapStepsString = mapString.split(":\n")[1]
        val result = ArrayList<MapStep>()
        for (line in mapStepsString.lines()) {
            if (line.isEmpty() || line.isBlank()) {
                continue
            }
            val stepInfo = convertStringToList(line)
            result.add(MapStep(stepInfo[0], stepInfo[1], stepInfo[2]))
        }
        return result
    }

    private fun getMinimumSeedLocation(seeds: List<Long>): String {
        val seedLocations = HashMap<Long, Long>()
        for (seed in seeds) {
            seedLocations[seed] = seed
        }
        val mapStrings = inputAsString.split("\n\n").toMutableList()
        mapStrings.removeAt(0)
        for (mapString in mapStrings) {
            val mapSteps = getMapSteps(mapString)
            val modifiedSeeds = HashMap<Long, Boolean>()
            for (seed in seeds) {
                modifiedSeeds[seed] = false
            }
            for (mapStep in mapSteps) {
                for ((seed, location) in seedLocations.entries) {
                    val sourceEnd = mapStep.sourceStart + mapStep.rangeLength
                    if (location in mapStep.sourceStart until sourceEnd && !modifiedSeeds[seed]!!) {
                        seedLocations[seed] = mapStep.destinationStart + (location - mapStep.sourceStart)
                        modifiedSeeds[seed] = true
                    }
                }
            }
        }
        return seedLocations.values.min().toString()
    }

    override fun solvePart1(): String {
        val seeds = getSeeds()
        return getMinimumSeedLocation(seeds)
    }

    private fun getSeedIntervals(): List<Interval> {
        val seedsStr = inputAsString.split("\n\n")[0].split(":")[1]
        val seeds = convertStringToList(seedsStr)
        val result = ArrayList<Interval>()
        for (i in seeds.indices step 2) {
            result.add(Interval(seeds[i], seeds[i] + seeds[i + 1] - 1))
        }
        return result
    }

    override fun solvePart2(): String {
        val seedIntervals = getSeedIntervals().toMutableList()
        val mapStrings = inputAsString.split("\n\n").toMutableList()
        mapStrings.removeAt(0)
        for (mapString in mapStrings) {
            val mapSteps = getMapSteps(mapString)
            val alreadyComputed = HashSet<Interval>()
            for (step in mapSteps) {
                val ruleInterval = Interval(step.sourceStart, step.sourceStart + step.rangeLength - 1)
                val resultInterval = Interval(step.destinationStart, step.destinationStart + step.rangeLength - 1)
                val intervalsToRemove = ArrayList<Interval>()
                val intervalsToAdd = ArrayList<Interval>()
                for (interval in seedIntervals) {
                    if (interval in alreadyComputed) {
                        continue
                    }
                    val intersection = intersectIntervals(interval, ruleInterval)
                    val differentIntervals = getIntervalDifference(interval, ruleInterval)
                    if (intersection != null) {
                        val startDifference = intersection.start - ruleInterval.start
                        val intersectionLength = intersection.end - intersection.start
                        val newStart = resultInterval.start + startDifference
                        val newEnd = newStart + intersectionLength
                        val newInterval = Interval(newStart, newEnd)
                        intervalsToAdd.add(newInterval)
                        alreadyComputed.add(newInterval)
                    }
                    intervalsToAdd += differentIntervals
                    intervalsToRemove.add(interval)
                }
                for (interval in intervalsToRemove) {
                    seedIntervals.remove(interval)
                }
                for (interval in intervalsToAdd) {
                    seedIntervals.add(interval)
                }
            }
        }

        return seedIntervals.minOfOrNull { interval -> interval.start }.toString()
    }
}