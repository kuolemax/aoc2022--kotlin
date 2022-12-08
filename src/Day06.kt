fun main() {

    fun part1(input: List<String>): Int {
        return findFourDifferentCharactersInARow(input[0], 4)
    }

    fun part2(input: List<String>): Int {
        return findFourDifferentCharactersInARow(input[0], 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

private fun findFourDifferentCharactersInARow(str: String, size: Int): Int {
    val chars = str.toCharArray()
    var differentCharacter = mutableListOf<Char>()
    var index = 0
    while (differentCharacter.size < size){
        val it = chars[index]
        val sameCharacterIndex = differentCharacter.indexOf(it)
        if (sameCharacterIndex != -1) {
            differentCharacter = differentCharacter.subList(sameCharacterIndex + 1, differentCharacter.size)
        }
        differentCharacter.add(it)
        index++
    }
    return index
}
