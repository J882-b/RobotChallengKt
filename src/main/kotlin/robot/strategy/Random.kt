package robot.strategy

import robot.Input
import robot.Move

class Random : Strategy("Random", "Martin") {

    override fun getNextMove(input: Input): Move {
        return Move.random()
    }
}
