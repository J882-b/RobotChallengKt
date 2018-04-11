package robot

import org.w3c.dom.HTMLDivElement
import kotlin.browser.document
import kotlin.browser.window

class Board {
    val tanks: MutableList<Tank> = mutableListOf()
    internal val gameDiv = document.getElementById("gameDiv") as HTMLDivElement
    internal val dimension = Dimension(20, 20)
    private var laser: Laser? = null

    constructor() {
        gameDiv.style.width = "400px"
        gameDiv.style.height = "400px"
        gameDiv.style.border = "black 5px solid"
        gameDiv.style.position = "relative"

        path2image("images/game_bg.png", {image ->
            gameDiv.appendChild(image)
            image.style.position = "absolute"
            image.style.zIndex = "1"})

        window.setTimeout({serLaser(Laser(this))}, 100)
    }

    private fun serLaser(laser: Laser) {
        this.laser = laser
    }

    fun addTank(tank : Tank) {
        tanks.add(tank)
    }

    fun availablePoint(point : Point): Boolean {
        for (i in 0..(tanks.size - 1)) {
            if (tanks[i].point == point) {
                return false
            }
        }
        return true
    }

    fun randomPoint(): Point {
        var point : Point
        do {
            point = Point.random(dimension)
        } while (!availablePoint(point))
        return point
    }

    fun validPoint (point : Point): Boolean {
        if (point.x in 0..(dimension.width - 1) && point.y in 0..(dimension.height - 1)) {
            return true
        }
        return false
    }

    fun getAliveTanksInRandomOrder() : List<Tank> {
        val result : MutableList<Tank> = mutableListOf()
        // TODO: Add the random
        this.tanks.forEach {tank ->
            if (tank.isAlive()) {
                result.add(tank)
            }
        }
        return result
    }

    fun fire(tank : Tank) {
        var point = tank.point
        for (i in 0..(Tank.fireRange - 1)) {
            point = point.withOffset(tank.direction)
            if (!validPoint(point)) {
                break
            } else if (availablePoint(point)) {
                laser?.set(i, point, tank.direction)
            } else {
                val otherTank = tankAt(point)
                val aliveBeforeHit = otherTank.isAlive()
                otherTank.hit()
                val aliveAfterHit = otherTank.isAlive()
                if (aliveAfterHit) {
                    tank.hits += 1
                } else if (aliveBeforeHit && !aliveAfterHit) {
                    tank.frags += 1
                }
                break
            }
        }
    }

    private fun tankAt(point: Point): Tank {
        return tanks.filter { tank -> tank.point == point }[0]
    }

    fun moveReset() {
        laser?.reset()
        tanks.forEach { tank -> tank.hitReset() }
    }
}
