fun main() {

    fun parseGroupStringToRange(group: String): Pair<IntRange, IntRange> {
        val pairs = group.split(",")
        val pair1: IntRange = (pairs[0].split("-")[0].toInt()..pairs[0].split("-")[1].toInt())
        val pair2: IntRange = (pairs[1].split("-")[0].toInt()..pairs[1].split("-")[1].toInt())
        return pair1 to pair2
    }

    fun listContains(list1: IntRange, list2: IntRange): Boolean {
        // 1. list1 ⊂ list2
        // 2. list2 ⊂ list1
        return (list1.first() >= list2.first() && list1.last() <= list2.last())
                || (list1.first() <= list2.first() && list1.last() >= list2.last())
    }

    fun listOverlap(group: String): Boolean {
        val (pair1, pair2) = parseGroupStringToRange(group)
        return pair1.first in pair2 || pair2.first in pair1
    }

    fun part1(input: List<String>): Int {
        var totalScore = 0
        input.forEach { group ->
            val pairs = parseGroupStringToRange(group)
            listContains(pairs.first, pairs.second).let {
                if (it) totalScore++
            }
        }
        return totalScore
    }

    fun part2(input: List<String>): Int {
        val lambda = { group: String ->
            if (listOverlap(group)) 1 else 0
        }
        return input.sumOf(lambda)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
