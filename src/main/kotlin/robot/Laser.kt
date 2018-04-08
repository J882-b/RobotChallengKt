package robot

import org.w3c.dom.HTMLImageElement

/*
    function Laser(board) {
        var self = this;
        var laserPath = 'images/laser.png';
        this.laserImages = [];
        for ( var i = 0; i < Tank.prototype.fireRange; i += 1) {
            path2image(laserPath, function(image) {
                self.laserImages[i] = image;
                board.htmlDiv.appendChild(image);
                image.style.position = "absolute";
                image.style.zIndex = "2";
                image.style.display = "none";
            });
        }
    }
    Laser.prototype.set = function(i, point, direction) {
        var image = this.laserImages[i];
        image.style.transform = 'translate(' + point.x * 20 + 'px, ' + point.y * 20 + 'px) rotate('
                + direction.angle + 'deg)';
        image.style.display = 'block';
    };
    Laser.prototype.reset = function() {
        this.laserImages.forEach(function(image) {
            image.style.display = 'none';
        });
    };
 */
class Laser {
    private val laserPath = "images/laser.png"
    private val laserImages = mutableListOf<HTMLImageElement>()

    constructor(board: Board) {
        for (i in 0 .. Tank.fireRange) {
            path2image(laserPath, {image ->
                laserImages[i] = image
                board.htmlDiv.appendChild(image)
                image.style.position = "absolute"
                image.style.zIndex = "2"
                image.style.display = "none"})
        }
    }

    fun set(i: Int, point: Point, direction: Direction) {
        val image = laserImages[i]
        image.style.transform = "translate(${point.x * 20}px, ${point.y * 20}px) rotate(${direction.angle}deg)"
        image.style.display = "block"
    }

    fun reset() {
        laserImages.forEach {image -> image.style.display = "none"}
    }
}
