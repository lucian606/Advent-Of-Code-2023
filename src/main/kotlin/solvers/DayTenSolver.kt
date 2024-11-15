package solvers

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

typealias Point = Pair<Int, Int>
typealias Edge = Pair<Point, Point>

class DayTenSolver(inputPath: String) : DaySolver(inputPath) {

    var startPipe = 'S'

    fun getStartPosition(): Point {
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (input[i][j] == 'S') {
                    return Pair(i, j)
                }
            }
        }
        return Point(-1, -1)
    }

    fun getPipesConnectedToStart(startPos: Point): Set<Point> {
        val row = startPos.first
        val col = startPos.second
        val result = mutableSetOf<Point>()
        if (row - 1 >= 0 && input[row - 1][col] in "F|7") {
            result.add(Point(row - 1, col))
        }
        if (row + 1 < input.size && input[row + 1][col] in "J|L") {
            result.add(Point(row + 1, col))
        }
        if (col + 1 < input[0].length && input[row][col + 1] in "J-7") {
            result.add(Point(row, col + 1))
        }
        if (col - 1 >= 0 && input[row][col - 1] in "L-F") {
            result.add(Point(row, col - 1))
        }

        return result
    }

    fun replaceStartPipe(startPos: Point) {
        val row = startPos.first
        val col = startPos.second
        var connections = ""
        if (row - 1 >= 0 && input[row - 1][col] in "F|7") {
            connections += "U"
        }
        if (row + 1 < input.size && input[row + 1][col] in "J|L") {
            connections += "D"
        }
        if (col + 1 < input[0].length && input[row][col + 1] in "J-7") {
            connections += "R"
        }
        if (col - 1 >= 0 && input[row][col - 1] in "L-F") {
            connections += "L"
        }

        startPipe = when (connections) {
            "UR" -> 'L'
            "UL" -> 'J'
            "DL" -> '7'
            "DR" -> 'F'
            "UD" -> '|'
            "LR" -> '-'
            else -> 'S'
        }

    }

    fun getNeighbourPipes(curr: Point, grid: MutableList<String> = input): ArrayList<Point> {
        val row = curr.first
        val col = curr.second
        val result = ArrayList<Point>()
        when (grid[row][col]) {
            '|' -> {
                if (row + 1 < grid.size)
                    result.add(Point(row + 1, col))
                if (row - 1 >= 0)
                    result.add(Point(row - 1, col))
            }
            'F' -> {
                if (row + 1 < grid.size)
                    result.add(Point(row + 1, col))
                if (col + 1 < grid[0].length)
                    result.add(Point(row, col + 1))
            }
            '7' -> {
                if (row + 1 < grid.size)
                    result.add(Point(row + 1, col))
                if (col - 1 >= 0)
                    result.add(Point(row, col - 1))
            }
            '-' -> {
                if (col + 1 < grid[0].length)
                    result.add(Point(row, col + 1))
                if (col - 1 >= 0)
                    result.add(Point(row, col - 1))
            }
            'L' -> {
                if (col + 1 < grid[0].length)
                    result.add(Point(row, col + 1))
                if (row - 1 >= 0)
                    result.add(Point(row - 1, col))
            }
            'J' -> {
                if (row - 1 >= 0)
                    result.add(Point(row - 1, col))
                if (col - 1 >= 0)
                    result.add(Point(row, col - 1))
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
        val queue = LinkedList<Pair<Point, Int>>()
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

    fun getLoop(): Set<Point> {
        val loop = LinkedHashSet<Point>()
        val queue = LinkedList<Point>()
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

    fun getLoopVertices(loop: Set<Point>): Set<Point> {
        val startPos = getStartPosition()
        replaceStartPipe(startPos)
        println("Start replaced with $startPipe")

        val loopVertices = loop.filter { (x,y) -> !"|-S".contains(input[x][y]) }.toMutableSet()
        if (startPipe != '-' && startPipe != '|') {
            loopVertices.add(startPos)
        }
        return loopVertices
    }

    fun getLoopEdges(loop: Set<Point>, vertices: Set<Point>): Set<Edge> {
        val startlessInput = input.map { it.replace('S', startPipe) }.toMutableList()

        val edges = LinkedHashSet<Edge>()
        val stack = Stack<Point>()
        var edgeStart = getStartPosition()
        val visited = HashSet<Point>()
        stack.push(edgeStart)
        while (stack.isNotEmpty()) {
            val currentVertex = stack.pop()
            if (currentVertex in visited) {
                continue
            }
            visited.add(currentVertex)
            val neighbours = getNeighbourPipes(currentVertex, startlessInput)
            for (neighbour in neighbours) {
                if (neighbour in loop && neighbour !in visited) {
                    stack.push(neighbour)
                }

                if (neighbour in vertices && neighbour != edgeStart && neighbour !in visited) {
                    val edge = Edge(edgeStart, neighbour)
                    edges.add(edge)
                    edgeStart = neighbour
                }
            }
        }

        edges.add(Edge(edgeStart, getStartPosition()))

        return edges
    }

    fun getShapeArea(edges: Set<Edge>): Int {
        val vertices = edges.map { it.first }.toMutableList()
        val verticesCount = vertices.size
        vertices.add(vertices[0])

        var firstShoelace = 0
        var secondShoelace = 0
        for (i in 0 until verticesCount) {
            firstShoelace += vertices[i].first * vertices[i + 1].second
            secondShoelace += vertices[i].second * vertices[i + 1].first
        }

        vertices.removeLast()

        return Math.abs(firstShoelace - secondShoelace) / 2
    }

    fun getPointsInsideLoop(loop: Set<Point>, area: Int): Int {
        return (area + 1 - loop.size / 2)
    }
    override fun solvePart2(): String {

        val loop = getLoop()
        val loopVertices = getLoopVertices(loop)
        val loopEdges = getLoopEdges(loop, loopVertices)
        val area = getShapeArea(loopEdges)

        return getPointsInsideLoop(loop, area).toString()
    }
}
