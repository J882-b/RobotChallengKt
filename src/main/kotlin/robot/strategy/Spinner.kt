package robot.strategy

import robot.Input
import robot.Move

class Spinner : Strategy() {
    override val name = "Spinner"
    override val author = "Martin"
    private var shot = false

    override fun getNextMove(input: Input): Move {
        shot = !shot
        return if (shot) Move.FIRE else Move.TURN_RIGHT
    }
}
