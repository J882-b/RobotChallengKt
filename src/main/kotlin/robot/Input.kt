package robot

class Input(tank: Tank, board: Board) {
    val playfield: Dimension = Dimension(board.dimension.width, board.dimension.height)
    val ownStatus: Status = Status(board.tanks.filter { it.point == tank.point }[0])
    val opponentStatus: List<Status> = board.tanks.filter { it.point != tank.point }.map { Status(it) }
    val fireRange = Tank.fireRange
}
