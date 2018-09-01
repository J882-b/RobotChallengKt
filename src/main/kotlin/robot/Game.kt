package robot

import robot.strategy.*
import kotlin.browser.window

class Game {
    private val board = Board()
    private val strategies: List<Strategy> =
            listOf(Spinner(), Derp(), Random(), Random(), Random(), Random(), Random(), FireFire())
    private val score: Score
    private var round = 0
    private var nextToMoveQueue = mutableListOf<Tank>()

    constructor() {
        strategies.forEach {strategy -> Tank(board, strategy)}
        score = Score(board.tanks.size)
    }

    fun start() {
        nextMove()
    }

    // TODO: Performance bug when only two tanks left.
    private fun nextMove() {
        var winner = false

        if (nextToMoveQueue.size == 0) {
            round += 1
            nextToMoveQueue.addAll(board.getAliveTanksInRandomOrder())
            if (nextToMoveQueue.size == 1) {
                winner = true
            }
        }
        score.update(round, board.tanks)
        board.moveReset()
        if (!winner) {
            var tank = nextToMoveQueue.shift()

            if (tank?.isAlive() == true) {
                tank.move(board)
            }
            window.setTimeout({nextMove()}, 100)
        } else {
            val alertMessage = "The winner is ${nextToMoveQueue[0].strategy.name}"
            window.alert(alertMessage)
        }
    }
}
