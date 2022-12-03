fun main() {
    fun countPerCalories(input: List<String>): MutableList<Int> {
        val arr = mutableListOf<Int>()
        var index = 0
        var newStart = 0
        val lines = input.iterator()
        while (lines.hasNext()) {
            val next = lines.next()
            if (next.isNotBlank()) {
                // 连续的值，累加
                if (index == newStart) {
                    arr.add(next.toInt())
                } else {
                    arr[index] = arr[index] + next.toInt()
                }
                newStart++
            } else {
                // 空行，重新累加
                index++
                newStart = index
            }
        }
        return arr
    }

    fun part1(input: List<String>): Int {
        val arr = countPerCalories(input)
        return arr.max()
    }

    fun part2(input: List<String>): Int {
        val calories = countPerCalories(input)
        calories.sortDescending()
        return calories.subList(0, 3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
