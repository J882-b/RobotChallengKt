package robot

class Status(tank: Tank) {
    val direction: Direction = tank.direction
    val location: Point = tank.point
    val isAlive: Boolean = 0 < tank.energy
}
