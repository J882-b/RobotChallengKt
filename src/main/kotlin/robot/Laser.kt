package robot

import org.w3c.dom.HTMLImageElement


class Laser {
    private val laserPath = "images/laser.png"
    var laserImages = mutableListOf<HTMLImageElement?>()

    constructor(board: Board) {
        for (i in 0..(Tank.fireRange - 1)) {
            laserImages.add(null)
        }
        for (i in 0..(Tank.fireRange - 1)) {
            path2image(laserPath, {image ->
                board.gameDiv.appendChild(image)
                image.style.position = "absolute"
                image.style.zIndex = "2"
                laserImages[i] = image})
        }
    }

    fun set(i: Int, point: Point, direction: Direction) {
        if (laserImages.size <= i) {
            return
        }
        val image = laserImages[i] ?: return

        val laserTransform = "translate(${point.x * 20}px, ${point.y * 20}px) rotate(${direction.angle}deg)"
        image.style.transform = laserTransform
        image.style.display = "block"
    }

    fun reset() {
        laserImages.forEach { image ->
            image?.style?.display = "none"
        }
    }
}
