package robot

import org.w3c.dom.HTMLImageElement
import robot.Move.*
import robot.strategy.Strategy

class Tank {
    private val maxEnergy = 5
    var energy: Int = maxEnergy
    var hits: Int = 0
    var frags: Int = 0
    var point: Point
    var direction = Direction.random()
    val strategy: Strategy
    private var oldPoint: Point = Point(0, 0)
    val imageId = tankImagePaths.shift()!!
    var image: HTMLImageElement? = null
    var hitImage : HTMLImageElement? = null
    var rotateAngle = direction.angle

    constructor(board: Board, strategy: Strategy) {
        this.strategy = strategy
        point = board.randomPoint()

        path2image(imageId, {image ->
            image.style.position = "absolute"
            image.style.zIndex = "2"
            image.className = "tank"
            board.gameDiv.appendChild(image)
            this.image = image
        })

        path2image("images/hit.png", {image ->
            image.style.position = "absolute"
            image.style.zIndex = "3"
            //image.style.display = "none"
            board.gameDiv.appendChild(image)
            hitImage = image
        })

        updateTransform()

        board.addTank(this)
    }

    private fun updateTransform() {
        val tankTransform =  "translate(${point.x * 20}px, ${point.y * 20}px) rotate(${rotateAngle}deg)"
        image?.style?.transform = tankTransform
        val hitTransform = "translate(${point.x * 20}px, ${point.y * 20}px)"
        hitImage?.style?.transform = hitTransform
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

    fun hit() {
        if (0 < energy) {
            energy -= 1
            hitImage?.style?.display = "block"
            if (energy == 0) {
                // frag
            }
        }
    }

    fun hitReset() {
        if (isAlive()) {
            hitImage?.style?.display = "none"
        }
    }

    fun isAlive(): Boolean {
        return 0 < energy
    }

    companion object {
        const val fireRange = 10
    }
}
