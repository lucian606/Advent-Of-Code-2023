package solvers

class DayEightSolver(inputPath: String) : DaySolver(inputPath) {
    fun getGraphFromInput(): Map<String, List<String>> {
        val graph = HashMap<String, List<String>>()
        for (i in 2 until input.size) {
            val lineContent = input[i].split("=")
            val node = lineContent[0].trim()
            val adjList = lineContent[1]
                .trim()
                .replace("(","")
                .replace(")","")
                .split(", ")
            graph[node] = adjList
        }
        return graph
    }
    override fun solvePart1(): String {
        val instructions = input[0]
        val graph = getGraphFromInput()
        var steps = 0
        var currentNode = "AAA"
        while (currentNode != "ZZZ") {
            if (instructions[steps % instructions.length] == 'L') {
                currentNode = graph[currentNode]!![0]
                steps++
            } else {
                currentNode = graph[currentNode]!![1]
                steps++
            }
        }
        return steps.toString()
    }

    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
    override fun solvePart2(): String {
        val instructions = input[0]
        val graph = getGraphFromInput()
        val startPoints = graph.keys.filter { it.last() == 'A' }
        val necessarySteps = ArrayList<Long>()
        for (startPoint in startPoints) {
            var currentNode = startPoint
            var steps = 0L
            while (currentNode.last() != 'Z') {
                if (instructions[(steps % instructions.length).toInt()] == 'L') {
                    currentNode = graph[currentNode]!![0]
                    steps++
                } else {
                    currentNode = graph[currentNode]!![1]
                    steps++
                }
            }
            necessarySteps.add(steps)
        }

        val stepsLCM = necessarySteps.reduce{ stepsLCM, steps -> findLCM(stepsLCM, steps)}

        return stepsLCM.toString()
    }
}