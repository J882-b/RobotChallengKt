package robot

/*
function Move() {
    }

    function Fire() {
        this.name = 'Fire';
    }
    Fire.prototype = new Move();
    Fire.prototype.construction = Fire;

    function TurnLeft() {
        this.name = 'TurnLeft';
    }
    TurnLeft.prototype = new Move();
    TurnLeft.prototype.construction = TurnLeft;

    function Forward() {
        this.name = 'Forward';
    }
    Forward.prototype = new Move();
    Forward.prototype.construction = Forward;

    function TurnRight() {
        this.name = 'TurnRight';
    }
    TurnRight.prototype = new Move();
    TurnRight.prototype.construction = TurnRight;

    function Wait() {
        this.name = 'Wait';
    }
    Wait.prototype = new Move();
    Wait.prototype.construction = Wait;

    Move.prototype.fire = new Fire();
    Move.prototype.turnLeft = new TurnLeft();
    Move.prototype.forward = new Forward();
    Move.prototype.turnRight = new TurnRight();
    Move.prototype.wait = new Wait();
    Move.prototype.random = function() {
        switch (random(5)) {
        case 0:
            return Move.prototype.fire;
        case 1:
            return Move.prototype.turnLeft;
        case 2:
            return Move.prototype.forward;
        case 3:
            return Move.prototype.turnRight;
        case 4:
            return Move.prototype.wait;
        }
    };

 */
enum class Move {
    Fire, TurnLeft, Forward, TurnRight, Wait
}

