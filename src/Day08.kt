import kotlin.math.log

fun main() {

    fun part1(input: List<String>): Int {
        return process(input)
    }

    // fun part2(input: List<String>): Int {
    //     TODO()
    // }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    // check(part2(testInput) == 45000)

    val input = readInput("Day08")
    println(part1(input))
    // println(part2(input))
}

private fun process(input: List<String>): Int {
    val size = input.size
    var result = (size - 1) * 4
    
    val matrix = input.map { row ->
        val map = row.map { it.toString().toInt() }
        map
    }
    
    // col 从 2 -> size - 1 check
    // row 从 2 -> size - 1 check

    for ((r, row) in matrix.withIndex()) {
        if (r == 0 || r == size - 1) {
            continue
        }
        for (c in row.indices) {
            if (c == 0 || c == size - 1) {
                continue
            }
            if (checkIfMoreThanOther(r, c, size, matrix)) result++
        }
    }
    
    return result
}

fun checkIfMoreThanOther(rIndex: Int, cIndex: Int, size: Int, matrix: List<List<Int>>): Boolean {
    val currentValue = matrix[rIndex][cIndex]
    var result = 4
    // 向左检查
    var left = cIndex - 1
    while (left > -1) {
        if (currentValue <= matrix[rIndex][left]) {
            result--
            break
        }
        left--
    }
    if (result == 4) return true
    // 向右检查
    var right = cIndex + 1
    while (right < size) {
        if (currentValue <= matrix[rIndex][right]) {
            result--
            break
        }
        right++
    }
    if (result == 3) return true
    // 向上检查
    var up = rIndex - 1
    while (up > -1) {
        if (currentValue <= matrix[up][cIndex]) {
            result--
            break
        }
        up--
    }
    if (result == 2) return true
    // 向下检查
    var down = rIndex + 1
    while (down < size) {
        if (currentValue <= matrix[down][cIndex]) {
            result--
            break
        }
        down++
    }
    return result == 1
}
