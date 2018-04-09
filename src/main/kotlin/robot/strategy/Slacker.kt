package robot.strategy

import robot.Input
import robot.Move

/*
    function Slacker() {
        this.name = 'Eric idle';
        this.author = 'Martin';
        this.getNextMove = function() {
            return Move.prototype.wait;
        };

    }
    Slacker.prototype = new Strategy();
    Slacker.prototype.constructor = Slacker;
 */
class Slacker : Strategy() {
    override val name = "Eric idle"
    override val author = "Martin"

    override fun getNextMove(input: Input): Move {
        console.log("Slacker getNextMove()")
        return Move.WAIT
    }
}