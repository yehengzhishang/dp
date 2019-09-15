package org.yu.zz.dp.strategy

import java.util.concurrent.ThreadLocalRandom

/**
 *  策略模式 ： 定义了算法族 ，分别封装起来，让它们之间可以相互替换，此模式让算法独立于使用的客户
 */
fun main(args: Array<String>) {
    val x = Player("x")
    x.strategy = RandomStrategy()
    val y = Player("y")
    y.strategy = StudyStrategy()

    for (i in 0 until 1000) {
        val handX = x.nextHand()
        val handY = y.nextHand()
        val resultX = handX.match(handY)
        x.acceptResult(resultX)
        y.acceptResult(resultX.invert())
    }
    println(x.toString())
    println(y.toString())

}

class Hand private constructor(private val value: Int) {
    companion object {
        private const val VALUE_GUU = 0
        private const val VALUE_CHO = 1
        private const val VALUE_PAA = 2
        val HANDS: Array<Hand> = arrayOf(Hand(VALUE_GUU), Hand(VALUE_CHO), Hand(VALUE_PAA))
    }

    fun match(hand: Hand): MatchResult {
        return when {
            hand.value == value -> MatchResult.EVEN
            (this.value + 1) % 3 == hand.value -> MatchResult.WIN
            else -> MatchResult.LOSE
        }
    }

    fun winHand(): Hand {
        var pos = value - 1
        if (pos < 0) {
            pos = 2
        }
        return HANDS[pos]
    }
}

enum class MatchResult {
    WIN, LOSE, EVEN;

    fun invert(): MatchResult {
        return when (this) {
            WIN -> LOSE
            LOSE -> WIN
            else -> EVEN
        }
    }
}

class Player(val name: String) {
    lateinit var strategy: HandStrategy
    var cntWin = 0
    var cntLose = 0
    var cntEven = 0
    var cntMatch = 0

    fun acceptResult(result: MatchResult) {
        ++cntMatch
        when (result) {
            MatchResult.WIN -> win()
            MatchResult.LOSE -> lose()
            MatchResult.EVEN -> even()
        }
        strategy.studyByResult(result)
    }

    fun nextHand(): Hand = strategy.nextHand()

    fun win() = ++cntWin

    fun lose() = ++cntLose

    fun even() = ++cntEven

    override fun toString(): String {
        return "player $name win : $cntWin , lose : $cntLose , even : $cntEven"
    }
}

interface HandStrategy {
    fun nextHand(): Hand
    fun studyByResult(result: MatchResult)
}

class RandomStrategy : HandStrategy {
    override fun studyByResult(result: MatchResult) {
        // noStay
    }

    override fun nextHand(): Hand {
        return Hand.HANDS[ThreadLocalRandom.current().nextInt(3)]
    }
}

class StudyStrategy : HandStrategy {
    var hand = Hand.HANDS[ThreadLocalRandom.current().nextInt(3)]
    override fun studyByResult(result: MatchResult) {
        when (result) {
            MatchResult.LOSE -> hand = hand.winHand()
            else -> Hand.HANDS[ThreadLocalRandom.current().nextInt(3)]
        }
    }

    override fun nextHand(): Hand {
        return hand
    }

}