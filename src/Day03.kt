fun main() {

    fun part1(input: List<String>): Int {
        // 将String变成char数组，均分后取交集，转成ascii相加
        return input.sumOf { backpack ->
            val chunked = backpack.toCharArray().toList().chunked(backpack.length / 2)
            val chars = chunked[0].toSet() intersect chunked[1].toSet()
            if (chars.first().code < 96) {
                // Upper 65 -> 27
                chars.first().code - 38
            } else {
                // lower 97 -> 1
                chars.first().code - 96
            }
        }
    }

    // fun part2(input: List<String>): Int {
    //     TODO()
    // }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    // check(part2(testInput) == 45000)

    val input = readInput("Day03")
    println(part1(input))
    // println(part2(input))
}
