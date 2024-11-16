package solvers

class DayElevenSolver(inputPath: String) : DaySolver(inputPath) {

    val EXPANSION_FACTOR = 1000000

    data class Galaxy(val row: Int, val col: Int) {
        fun getDistanceTo(other: Galaxy): Int {
            return Math.abs(row - other.row) + Math.abs(col - other.col)
        }
    }

    fun isRowEmpty(row: Int): Boolean = input[row].all { it == '.' }

    fun isColumnEmpty(col: Int): Boolean = input.all { it[col] == '.'}

    fun getExpandedGrid(): List<String> {
        val result = mutableListOf<String>()

        for (i in input.indices) {
            var newRow = ""
            for (j in input[i].indices) {
                if (isColumnEmpty(j)) {
                    newRow += ".."
                } else {
                    newRow += input[i][j]
                }
            }
            if (isRowEmpty(i)) {
                result += newRow
            }
            result += newRow
        }

        return result
    }

    fun getGalaxies(grid: List<String>): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()

        for (row in grid.indices) {
            for (col in grid[row].indices) {
                if (grid[row][col] == '#') {
                    galaxies.add(Galaxy(row, col))
                }
            }
        }

        return galaxies
    }

    fun getShortestDistanceBetweenGalaxies(galaxies: List<Galaxy>): Int {

        val distances = mutableMapOf<String, Int>()

        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                val distance = galaxies[i].getDistanceTo(galaxies[j])
                distances["$i to $j"] = distance
            }
        }

        return distances.values.sum()
    }

    fun getRowsPathBetweenGalaxies(source: Galaxy, dest: Galaxy): IntRange {
        val sourceRow = source.row
        val destRow = dest.row

        return if (sourceRow == destRow) {
            IntRange.EMPTY
        } else if (sourceRow < destRow) {
            sourceRow + 1..destRow
        } else {
            destRow until sourceRow
        }
    }

    fun getColumnPathBetweenGalaxies(source: Galaxy, dest: Galaxy): IntRange {
        val sourceCol = source.col
        val destCol = dest.col

        return if (sourceCol == destCol) {
            IntRange.EMPTY
        } else if (sourceCol < destCol) {
            sourceCol + 1..destCol
        } else {
            destCol until sourceCol
        }
    }

    fun getExpandedDistanceBetweenGalaxies(source: Galaxy, dest: Galaxy): Long {
        val traversedRows = getRowsPathBetweenGalaxies(source, dest)
        val traversedCols = getColumnPathBetweenGalaxies(source, dest)
        var distance = 0L

        for (row in traversedRows) {
            if (isRowEmpty(row)) {
                distance += EXPANSION_FACTOR
            } else {
                distance++
            }
        }

        for (col in traversedCols) {
            if (isColumnEmpty(col)) {
                distance += EXPANSION_FACTOR
            } else {
                distance++
            }
        }

        return distance
    }

    fun getShortestExpandedDistanceBetweenGalaxies(galaxies: List<Galaxy>): Long {
        val distances = mutableMapOf<String, Long>()


        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                val distance = getExpandedDistanceBetweenGalaxies(galaxies[i], galaxies[j])
                distances["$i to $j"] = distance
            }
        }

        return distances.values.sum()
    }

    override fun solvePart1(): String {
        val expandedGrid = getExpandedGrid()
        val galaxies = getGalaxies(expandedGrid)
        val distance = getShortestDistanceBetweenGalaxies(galaxies)

        return distance.toString()
    }

    override fun solvePart2(): String {
        val galaxies = getGalaxies(input)
        val distance = getShortestExpandedDistanceBetweenGalaxies(galaxies)

        return distance.toString()
    }
}