package robot.strategy

import robot.Input
import robot.Move

abstract class Strategy(val name: String, val author: String) {

    abstract fun getNextMove(input: Input): Move
}
