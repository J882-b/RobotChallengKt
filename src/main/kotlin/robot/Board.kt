package robot

import org.w3c.dom.HTMLDivElement
import kotlin.browser.document

/*
   function Board() {
        var self = this;
        this.gameDiv = document.getElementById('gameDiv');
        this.dimension = new Dimension(20, 20);
        this.tanks = [];
        this.gameDiv.style.width = "400px";
        this.gameDiv.style.height = "400px";
        this.gameDiv.style.border = "black 5px solid";
        this.gameDiv.style.position = "relative";

        path2image('images/game_bg.png', function(image) {
            self.gameDiv.appendChild(image);
            image.style.position = "absolute";
            image.style.zIndex = "1";
        });

        this.laser = new Laser(this);
    }
    Board.prototype.addTank = function(tank) {
        this.tanks.push(tank);
    };
    Board.prototype.addLaser = function(laser) {
        this.laser = laser;
    };
    Board.prototype.availablePoint = function(point) {
        for ( var i = 0; i < this.tanks.length; i += 1) {
            if (this.tanks[i].point.equals(point)) {
                return false;
            }
        }
        return true;
    };
    Board.prototype.randomPoint = function() {
        var point;
        do {
            point = Point.prototype.random(this.dimension);
        } while (!this.availablePoint(point));
        return point;
    };
    Board.prototype.validPoint = function(point) {
        if (0 <= point.x && point.x < 20 && 0 <= point.y && point.y < 20) {
            return true;
        }
        return false;
    };
    Board.prototype.getAliveTanksInRandomOrder = function() {
        var result = [];
        // TODO: Add the random
        this.tanks.forEach(function(tank) {
            if (tank.isAlive()) {
                result.push(tank);
            }
        });
        return result;
    };
    Board.prototype.fire = function(tank) {
        var point = tank.point;
        for ( var i = 0; i < Tank.prototype.fireRange; i += 1) {
            point = point.withOffset(tank.direction);
            if (!this.validPoint(point)) {
                break;
            } else if (this.availablePoint(point)) {
                this.laser.set(i, point, tank.direction);
            } else {
                var otherTank = this.tankAt(point);
                var aliveBeforeHit = otherTank.isAlive();
                otherTank.hit(tank.direction.opposite());
                var aliveAfterHit = otherTank.isAlive();
                if (aliveAfterHit) {
                    tank.hits += 1;
                } else if (aliveBeforeHit && !aliveAfterHit) {
                    tank.frags += 1;
                }
                break;
            }
        }
    };
    Board.prototype.tankAt = function(point) {
        return this.tanks.filter(function(tank) {
            return tank.point.equals(point);
        })[0];
    };
    Board.prototype.moveReset = function() {
        this.laser.reset();
        this.tanks.forEach(function(tank) {
            tank.hitReset();
        });
    };
 */
class Board {
    val tanks: MutableList<Tank> = mutableListOf()
    internal val gameDiv = document.getElementById("gameDiv") as HTMLDivElement
    internal val dimension = Dimension(20, 20)
    private var laser = Laser(this)

    constructor() {
        console.log("Board constructor()")
        gameDiv.style.width = "400px"
        gameDiv.style.height = "400px"
        gameDiv.style.border = "black 5px solid"
        gameDiv.style.position = "relative"

    path2image("images/game_bg.png", {image ->
            gameDiv.appendChild(image)
            image.style.position = "absolute"
            image.style.zIndex = "1"})
    }

    fun addTank(tank : Tank) {
        console.log("Board addTank()")
        tanks.add(tank)
    }

    fun availablePoint(point : Point): Boolean {
        console.log("Board availablePoint()")
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
        console.log("Board validPoint()")
        if (point.x in 0..(dimension.width - 1) && point.y in 0..(dimension.height - 1)) {
            return true
        }
        return false
    }

    fun getAliveTanksInRandomOrder() : List<Tank> {
        console.log("Board getAliveTanksInRandomOrder()")
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
        console.log("Board fire()")
        val tankPoint = tank.point
        for (i in 0..(Tank.fireRange - 1)) {
            val firePoint = tankPoint.withOffset(tank.direction)
            if (!validPoint(firePoint)) {
                break
            } else if (availablePoint(firePoint)) {
                laser.set(i, firePoint, tank.direction)
            } else {
                val otherTank = tankAt(firePoint)
                val aliveBeforeHit = otherTank.isAlive()
                otherTank.hit(tank.direction.opposite())
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
        console.log("Board moveReset()")
        laser.reset()
        tanks.forEach { tank -> tank.hitReset() }
    }
}
