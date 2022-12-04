fun main() {

    fun parseGroupStringToList(group: String): Pair<List<Int>, List<Int>> {
        val pairs = group.split(",")
        val pair1: IntRange = (pairs[0].split("-")[0].toInt()..pairs[0].split("-")[1].toInt())
        val pair2: IntRange = (pairs[1].split("-")[0].toInt()..pairs[1].split("-")[1].toInt())
        return listOf(pair1).flatten() to listOf(pair2).flatten()
    }

    fun compareListContains(list1: List<Int>, list2: List<Int>): Boolean {
        // 1. list1 ⊂ list2
        // 2. list2 ⊂ list1
        return (list1.first() >= list2.first() && list1.last() <= list2.last())
                || (list1.first() <= list2.first() && list1.last() >= list2.last())
    }

    fun part1(input: List<String>): Int {
        var totalScore = 0
        input.forEach { group ->
            val pairs = parseGroupStringToList(group)
            compareListContains(pairs.first, pairs.second).let {
                if (it) totalScore++
            }
        }
        return totalScore
    }

    // fun part2(input: List<String>): Int {
    //     TODO()
    // }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    // check(part2(testInput) == 45000)

    val input = readInput("Day04")
    println(part1(input))
    // println(part2(input))
}
