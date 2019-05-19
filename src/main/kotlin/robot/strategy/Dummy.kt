package robot.strategy

import robot.Input
import robot.Move

class Dummy : Strategy("Dummy", "JMH__") {
    private val moves = listOf(Move.FIRE, Move.TURN_LEFT, Move.FORWARD)
    private var moveIndex = 0

    override fun getNextMove(input: Input): Move {
        return moves[moveIndex++ % moves.size]
    }
}
