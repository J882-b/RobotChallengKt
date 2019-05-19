package robot.strategy

import robot.Input
import robot.Move

class Slacker : Strategy("Eric idle", "Martin") {

    override fun getNextMove(input: Input): Move {
        return Move.WAIT
    }
}
