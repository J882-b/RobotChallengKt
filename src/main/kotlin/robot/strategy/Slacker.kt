package robot.strategy

import robot.Input
import robot.Move

class Slacker : Strategy() {
    override val name = "Eric idle"
    override val author = "Martin"

    override fun getNextMove(input: Input): Move {
        return Move.WAIT
    }
}