// --- Day 2: Rock Paper Scissors ---
// The Elves begin to set up camp on the beach. To decide whose tent gets to be closest to the snack storage, a giant Rock Paper Scissors tournament is already in progress.
//
// Rock Paper Scissors is a game between two players. Each game contains many rounds; in each round, the players each simultaneously choose one of Rock, Paper, or Scissors using a hand shape. Then, a winner for that round is selected: Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock. If both players choose the same shape, the round instead ends in a draw.
//
// Appreciative of your help yesterday, one Elf gives you an encrypted strategy guide (your puzzle input) that they say will be sure to help you win. "The first column is what your opponent is going to play: A for Rock, B for Paper, and C for Scissors. The second column--" Suddenly, the Elf is called away to help with someone's tent.
//
// The second column, you reason, must be what you should play in response: X for Rock, Y for Paper, and Z for Scissors. Winning every time would be suspicious, so the responses must have been carefully chosen.
//
// The winner of the whole tournament is the player with the highest score. Your total score is the sum of your scores for each round. The score for a single round is the score for the shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors) plus the score for the outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won).
//
// Since you can't be sure if the Elf is trying to help you or trick you, you should calculate the score you would get if you were to follow the strategy guide.
//
// For example, suppose you were given the following strategy guide:
//
// A Y
// B X
// C Z
//
// This strategy guide predicts and recommends the following:
//
// In the first round, your opponent will choose Rock (A), and you should choose Paper (Y). This ends in a win for you with a score of 8 (2 because you chose Paper + 6 because you won).
// In the second round, your opponent will choose Paper (B), and you should choose Rock (X). This ends in a loss for you with a score of 1 (1 + 0).
// The third round is a draw with both players choosing Scissors, giving you a score of 3 + 3 = 6.
// In this example, if you were to follow the strategy guide, you would get a total score of 15 (8 + 1 + 6).
//
// What would your total score be if everything goes exactly according to your strategy guide?



fun main() {

    // part1 穷举结果
    val resultMap = mapOf(
        "A X" to 1 + 3,
        "A Y" to 2 + 6,
        "A Z" to 3 + 0,
        "B X" to 1 + 0,
        "B Y" to 2 + 3,
        "B Z" to 3 + 6,
        "C X" to 1 + 6,
        "C Y" to 2 + 0,
        "C Z" to 3 + 3
    )

    fun part1(lines: List<String>): Int {
        var totalScore = 0
        for (line in lines) {
            val theScore = resultMap[line]!!
            totalScore += theScore
        }
        return totalScore
    }

    // part2
    // A:Rock B:Paper C:Scissors
    val cardScore = mapOf(
        "A" to 1,
        "B" to 2,
        "C" to 3,
    )

    val gameScore = mapOf(
        "Z" to 6,
        "X" to 0,
        "Y" to 3,
    )

    /**
     * 根据对手出牌和对局详情决定我方出牌
     * @param [card] 对手牌
     * @param [comparatorResult] 对局情况
     */
    fun cardComparator(card: String, comparatorResult: String): String {
        // 石头，布，剪刀
        val cards = listOf("A", "B", "C")
        val cardIndex = cards.indexOf(card)
        return when (comparatorResult) {
            "Z" -> {
                // 胜利，result > card
                cards[(cardIndex + 1) % cards.size]
            }
            "X" -> {
                // 输
                cards[if ((cardIndex - 1) < 0) (cardIndex - 1) + cards.size else (cardIndex - 1)]
            }
            // 平局
            "Y" -> return card
            else -> ""
        }
    }

    /**
     * 根据出牌情况计算得分
     */
    val gameResultScore = cardScore.keys.flatMap { card ->
        gameScore.keys.map { game ->
            // cardComparator(card, game) 找出结果的应该出的牌
            "$card $game" to (cardScore[cardComparator(card, game)]!! + gameScore[game]!!)
        }
    }.toMap()




    fun part2(lines: List<String>): Int {
        var totalScore = 0
        for (line in lines) {
            val theScore = gameResultScore[line]!!
            totalScore += theScore
        }
        return totalScore
    }

    // val testInput = readInput("Day02_test")
    // check(part1(testInput) == 64)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
