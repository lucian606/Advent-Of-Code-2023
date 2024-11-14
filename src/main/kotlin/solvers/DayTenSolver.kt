package solvers

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class DayTenSolver(inputPath: String) : DaySolver(inputPath) {

    fun getStartPosition(): Pair<Int, Int> {
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (input[i][j] == 'S') {
                    return Pair(i, j)
                }
            }
        }
        return Pair(-1, -1)
    }

    fun getPipesConnectedToStart(startPos: Pair<Int, Int>): List<Pair<Int, Int>> {
        val row = startPos.first
        val col = startPos.second
        val result = ArrayList<Pair<Int, Int>>()
        if (row - 1 >= 0 && input[row - 1][col] in "F|7") {
            result.add(Pair(row - 1, col))
        }
        if (col + 1 < input[0].length && input[row][col + 1] in "J-7") {
            result.add(Pair(row, col + 1))
        }
        if (row + 1 < input.size && input[row + 1][col] in "J|L") {
            result.add(Pair(row + 1, col))
        }
        if (col - 1 >= 0 && input[row][col - 1] in "L-F") {
            result.add(Pair(row, col - 1))
        }
        return result
    }

    fun getNeighbourPipes(curr: Pair<Int, Int>): ArrayList<Pair<Int, Int>> {
        val row = curr.first
        val col = curr.second
        val result = ArrayList<Pair<Int, Int>>()
        when (input[row][col]) {
            '|' -> {
                if (row + 1 < input.size)
                    result.add(Pair(row + 1, col))
                if (row - 1 >= 0)
                    result.add(Pair(row - 1, col))
            }
            'F' -> {
                if (row + 1 < input.size)
                    result.add(Pair(row + 1, col))
                if (col + 1 < input[0].length)
                    result.add(Pair(row, col + 1))
            }
            '7' -> {
                if (row + 1 < input.size)
                    result.add(Pair(row + 1, col))
                if (col - 1 >= 0)
                    result.add(Pair(row, col - 1))
            }
            '-' -> {
                if (col + 1 < input[0].length)
                    result.add(Pair(row, col + 1))
                if (col - 1 >= 0)
                    result.add(Pair(row, col - 1))
            }
            'L' -> {
                if (col + 1 < input[0].length)
                    result.add(Pair(row, col + 1))
                if (row - 1 >= 0)
                    result.add(Pair(row - 1, col))
            }
            'J' -> {
                if (row - 1 >= 0)
                    result.add(Pair(row - 1, col))
                if (col - 1 >= 0)
                    result.add(Pair(row, col - 1))
            }
        }
        return result
    }

    fun traverseLoop(): Int {
        val visited = ArrayList<ArrayList<Boolean>>()
        for (i in input.indices) {
            visited.add(ArrayList())
            for (j in input[0].indices) {
                visited[i].add(false)
            }
        }
        val queue = LinkedList<Pair<Pair<Int, Int>, Int>>()
        var maxDistance = 0
        val start = getStartPosition()
        visited[start.first][start.second] = true
        val startNeighbours = getPipesConnectedToStart(start)
        startNeighbours.forEach { queue.add(Pair(it, 1)) }
        while (!queue.isEmpty()) {
            val currentPair = queue.poll()
            val currentCell = currentPair.first
            val distance = currentPair.second
            val row = currentCell.first
            val col = currentCell.second
            visited[row][col] = true
            if (distance > maxDistance) {
                maxDistance = distance
            }
            val neighbours = getNeighbourPipes(currentCell)
            for (neighbour in neighbours) {
                val neighRow = neighbour.first
                val neighCol = neighbour.second
                if (!visited[neighRow][neighCol]) {
                    queue.add(Pair(neighbour, distance + 1))
                }
            }
        }
        return maxDistance
    }

    fun getLoop(): Set<Pair<Int, Int>> {
        val loop = HashSet<Pair<Int, Int>>()
        val queue = LinkedList<Pair<Int, Int>>()
        val start = getStartPosition()
        loop.add(start)
        val startNeighbours = getPipesConnectedToStart(start)
        startNeighbours.forEach { queue.add(it) }
        while (!queue.isEmpty()) {
            val currentCell = queue.poll()
            loop.add(currentCell)
            val neighbours = getNeighbourPipes(currentCell)
            for (neighbour in neighbours) {
                if (neighbour !in loop) {
                    queue.add(neighbour)
                }
            }
        }
        return loop
    }

    override fun solvePart1(): String {
        return traverseLoop().toString()
    }


    fun DFS(point: Pair<Int, Int>, loop: Set<Pair<Int, Int>>, visited: MutableSet<Pair<Int, Int>>): Unit {
        if (point in visited || point in loop) {
            return
        }
        val row = point.first
        val col = point.second
        visited.add(point)
        if (row == 0 || col == 0 || row == input.size - 1 || col == input[0].length - 1) {
            return
        }
        DFS(Pair(row - 1, col), loop, visited)
        DFS(Pair(row + 1, col), loop, visited)
        DFS(Pair(row, col - 1), loop, visited)
        DFS(Pair(row, col + 1), loop, visited)
    }

    fun isPointEnclosed(loop: Set<Pair<Int, Int>>, point: Pair<Int, Int>): Boolean {
        val visited = HashSet<Pair<Int, Int>>()
        if (point in loop) {
            return false
        }
        DFS(point, loop, visited)
        if (visited.any { it.first == 0
                    || it.first == input.size - 1
                    || it.second == 0
                    || it.second == input[0].length - 1}) {
            return false
        }
        return true
    }

    override fun solvePart2(): String {
        val loop = getLoop()
        var count = 0
        val modifiedInput = ArrayList<String>()
        for (i in input.indices) {
            var modifiedLine = ""
            for (j in input[0].indices) {
                if (isPointEnclosed(loop, Pair(i, j))) {
                    modifiedLine += "*"
                    count += 1
                } else if (Pair(i, j) in loop) {
                    modifiedLine += input[i][j]
                } else {
                    modifiedLine += "."
                }
            }
            modifiedInput.add(modifiedLine)
        }
        modifiedInput.forEach {
            println(it)
        }
        return count.toString()
    }
}
