package robot

import org.w3c.dom.HTMLImageElement
import robot.Move.*
import robot.strategy.Strategy

/*
    function Tank(board, strategy) {
        var self = this;
        this.strategy = strategy;
        this.direction = Direction.prototype.random();
        this.point = board.randomPoint();
        this.oldPoint = {};
        this.hitDirection = {};
        this.energy = this.maxEnergy;
        this.frags = 0;
        this.hits = 0;

        this.imageId = tankImagePaths.shift();
        this.image = {};
        path2image(this.imageId, function(image) {
            image.style.position = "absolute";
            image.style.zIndex = "2";
            image.className = 'tank';
            board.htmlDiv.appendChild(image);
            self.image = image;
        });

        this.hitImage = {};
        path2image('images/hit.png', function(image) {
            image.style.position = "absolute";
            image.style.zIndex = "3";
            image.style.display = "none";
            board.htmlDiv.appendChild(image);
            self.hitImage = image;
        });

        this.rotateAngle = this.direction.angle;

        this.updateTransform();

        board.addTank(this);
    }
    Tank.prototype.fireRange = 10;
    Tank.prototype.maxEnergy = 5;
    Tank.prototype.updateTransform = function() {
        this.image.style.transform = "translate(" + this.point.x * 20 + "px, " + this.point.y * 20
                + "px) rotate(" + this.rotateAngle + "deg)";
        this.hitImage.style.transform = "translate(" + this.point.x * 20 + "px, " + this.point.y
                * 20 + "px)";
    };
    Tank.prototype.toString = function() {
        return this.imageId + ' @ x=' + this.point.x + ', y=' + this.point.y + ', '
                + this.direction.name + ', energy=' + this.energy;
    };
    Tank.prototype.move = function(board) {
        var move = this.strategy.getNextMove(new Input(this, board));
        switch (move.name) {
        case FORWARD.name:
            var newPoint = this.point.withOffset(this.direction);
            if (board.validPoint(newPoint) && board.availablePoint(newPoint)) {
                this.oldPoint = this.point;
                this.point = newPoint;
            } else {
                // blocked
            }
            break;
        case TURN_LEFT.name:
            this.direction = this.direction.counterClockwise();
            this.rotateAngle -= 90;
            break;
        case TURN_RIGHT.name:
            this.direction = this.direction.clockwise();
            this.rotateAngle += 90;
            break;
        case FIRE.name:
            board.fire(this);
            break;
        case WAIT.name:
            break;
        }
        this.updateTransform();
    };
    Tank.prototype.hit = function(direction) {
        if (0 < this.energy) {
            this.energy -= 1;
            this.hitImage.style.display = "block";
            if (this.energy === 0) {
                // frag
            } else {
                // hit
                // TODO: hitDirection
            }
        } else {
            // already dead
        }
    };
    Tank.prototype.hitReset = function() {
        if (this.isAlive()) {
            this.hitImage.style.display = "none";
        }
    };
    Tank.prototype.isAlive = function() {
        return 0 < this.energy;
    };
 */
class Tank {
    private val maxEnergy = 5
    var energy: Int = maxEnergy
    var hits: Int = 0
    var frags: Int = 0
    var point: Point
    var direction = Direction.NORTH.random()
    val strategy: Strategy
    private var oldPoint: Point = Point(0, 0)
    var hitDirection: Direction = Direction.NORTH
    val imageId = tankImagePaths.shift()!!
    lateinit var image: HTMLImageElement
    lateinit var hitImage : HTMLImageElement
    var rotateAngle = direction.angle

    constructor(board: Board, strategy: Strategy) {
        this.strategy = strategy
        point = board.randomPoint()

        path2image(imageId, {image ->
            image.style.position = "absolute"
            image.style.zIndex = "2"
            image.className = "tank"
            board.htmlDiv.appendChild(image)
            this.image = image
        })

        path2image("images/hit.png", {image ->
            image.style.position = "absolute"
            image.style.zIndex = "3"
            image.style.display = "none"
            board.htmlDiv.appendChild(image)
            hitImage = image
        })

        updateTransform()

        board.addTank(this)
    }

    private fun updateTransform() {
        image.style.transform = "translate(${point.x * 20}px, ${point.y * 20}px) rotate(${rotateAngle}deg)"
        hitImage.style.transform = "translate(${point.x * 20}px, ${point.y * 20}px)"
    }


    override fun toString(): String {
        return "$imageId @ x=${point.x}, y=${point.y}, ${direction.name}, energy=$energy"
    }

    fun move(board: Board) {
        val move = strategy.getNextMove(Input(this, board))
        when(move) {
            FORWARD -> {
                val newPoint = point.withOffset(direction)
                if (board.validPoint(newPoint) && board.availablePoint(newPoint)) {
                    oldPoint = point
                    point = newPoint
                } else {
                    // blocked
                }
            }
            TURN_LEFT -> {
                direction = direction.counterClockwise()
                rotateAngle -= 90
            }
            TURN_RIGHT -> {
                direction = direction.clockwise()
                rotateAngle += 90
            }
            FIRE -> {
                board.fire(this)
            }
            WAIT -> {}
        }
        updateTransform()
    }

    fun hit(direction: Direction) {
        if (0 < energy) {
            energy -= 1
            hitImage.style.display = "block"
            if (energy == 0) {
                // frag
            } else {
                // hit
                // TODO: hitDirection
            }
        } else {
            // already dead
        }
    }

    fun hitReset() {
        if (isAlive()) {
            hitImage.style.display = "none"
        }
    }

    fun isAlive(): Boolean {
        return 0 < energy
    }

    companion object {
        const val fireRange = 10
    }
}
