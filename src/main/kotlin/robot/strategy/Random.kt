package robot.strategy

import robot.Input
import robot.Move

/*
    function Random() {
        this.name = 'Random';
        this.author = 'Martin';
        this.getNextMove = function() {
            return Move.prototype.random();
        };
    }
    Random.prototype = new Strategy();
    Random.prototype.constructor = Random;
 */
class Random : Strategy() {
    override val name = "Random"
    override val author = "Martin"

    override fun getNextMove(input: Input): Move {
        console.log("Random getNextMove()")
        return Move.random()
    }
}