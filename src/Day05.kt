import java.util.*
import kotlin.collections.ArrayList

fun main() {

    fun part1(input: List<String>): String {
        val (startRow, stackList) = parseStacks(input)
        val moveRows = input.subList(startRow, input.size)
        return moveCargo(moveRows, stackList)
    }

    // fun part2(input: List<String>): Int {
    //     TODO()
    // }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    // check(part2(testInput) == 45000)

    val input = readInput("Day05")
    println(part1(input))
    // println(part2(input))
}

fun parseStacks(input: List<String>): Pair<Int, List<Stack<String>>> {
    // 找到 move 所在行，索引即 Cargo 所在行数
    val moveRow = input.first { it -> it.startsWith("move") }
    val moveIndex = input.indexOf(moveRow)

    // 前 moveIndex - 2 是矩阵
    val columNumOfRow = moveIndex - 2
    val matrix = input.subList(0, columNumOfRow)

    // 找到 move 前倒数第二行，提取数字，数字个数即 Stack 数
    val columnNumStr = input[columNumOfRow]
    val stackNum = (columnNumStr.length + 2) / 4

    // 每行第 2 + (col - 1) * 4 个数字即 Cargo 存储的值
    val stackList = ArrayList<Stack<String>>()

    (0 until stackNum).forEach { col ->
        matrix.forEach { row ->
            val index = 2 + col * 4
            val cargoValue = row.substring(index - 1, index)
            
            if (cargoValue.isNotBlank()) {
                if (stackList.isEmpty() || stackList.size < col + 1) {
                    stackList.add(Stack())
                }
                stackList[col].add(0, cargoValue)
            }
        }
    }

    return moveIndex to stackList
}

fun moveCargo(moveList: List<String>, stacks: List<Stack<String>>): String {
    // 提取移动的起始点和个数
    val regexPattern = Regex("move (\\d+) from (\\d+) to (\\d+)")
    val moveOperationList = moveList.map { row ->
        val find = regexPattern.find(row)
        Triple(find!!.groupValues[1].toInt(), find.groupValues[2].toInt(), find.groupValues[3].toInt())
    }
    moveOperationList.forEach { (moveNum, popIndex, addIndex) ->
        (0 until moveNum).forEach { _ ->
            val popValue = stacks[popIndex - 1].pop()
            stacks[addIndex - 1].add(popValue)
        }
    }
    
    return stacks.joinToString("") { stack ->
        stack.pop()
    }
}