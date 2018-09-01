package robot.strategy

import robot.Input
import robot.Move

class Random : Strategy() {
    override val name = "Random"
    override val author = "Martin"

    override fun getNextMove(input: Input): Move {
        return Move.random()
    }
}