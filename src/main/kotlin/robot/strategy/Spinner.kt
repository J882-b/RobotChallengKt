package robot.strategy

import robot.Input
import robot.Move

/*
    function Spinner() {
        var shot = true;
        this.name = 'Spinner';
        this.author = 'Martin';
        this.getNextMove = function() {
            if (shot) {
                shot = false;
                return Move.prototype.turnRight;
            }
            shot = true;
            return Move.prototype.fire;
        };
    }
    Spinner.prototype = new Strategy();
    Spinner.prototype.constructor = Spinner;
 */
class Spinner : Strategy() {
    override val name = "Spinner"
    override val author = "Martin"
    private var shot = false

    override fun getNextMove(input: Input): Move {
        console.log("Spinner getNextMove()")
        shot = !shot
        return if (shot) Move.FIRE else Move.TURN_RIGHT
    }
}
