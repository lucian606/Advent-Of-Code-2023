package solvers

class DayFiveSolver(inputPath: String) : DaySolver(inputPath) {

    data class MapStep (val destinationStart: Long, val sourceStart: Long, val rangeLength: Long)

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

    private fun getSeedsFromPairs(): List<Long> {
        val seedsStr = inputAsString.split("\n\n")[0].split(":")[1]
        val pairsList = convertStringToList(seedsStr)
        val resultSet = HashSet<Long>()
        for (i in pairsList.indices step 2) {
            for (seed in pairsList[i] until pairsList[i] + pairsList[i + 1]) {
                resultSet.add(seed)
            }
        }
        return resultSet.toList()
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

    override fun solvePart2(): String {
        val seedsFromPairs = getSeedsFromPairs()
        return getMinimumSeedLocation(seedsFromPairs)
    }
}