package robot.strategy

import robot.Input
import robot.Move

/*
    function Derp() {
        this.name = 'Derp';
        this.author = 'JMH__';
        var moves = [ Move.prototype.fire, Move.prototype.turnLeft, Move.prototype.forward ];
        var moveIndex = 0;
        this.getNextMove = function() {
            return moves[moveIndex++ % moves.length];
        };
    }
    Derp.prototype = new Strategy();
    Derp.prototype.constructor = Derp;
 */
class Derp : Strategy() {
    override val name = "Derp"
    override val author = "JMH__"
    private val moves = listOf(Move.FIRE, Move.TURN_LEFT, Move.FORWARD)
    private var moveIndex = 0

    override fun getNextMove(input: Input): Move {
        console.log("Derp getNextMove()")
        return moves[moveIndex++ % moves.size]
    }
}