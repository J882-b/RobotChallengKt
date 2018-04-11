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
                board.gameDiv.appendChild(image);
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
    var laserImages = mutableListOf<HTMLImageElement?>()

    constructor(board: Board) {
        console.log("Laser constructor")
        for (i in 0..(Tank.fireRange - 1)) {
            laserImages.add(null)
        }
        for (i in 0..(Tank.fireRange - 1)) {
            path2image(laserPath, {image ->
                board.gameDiv.appendChild(image)
                image.style.position = "absolute"
                image.style.zIndex = "2"
                laserImages[i] = image
                console.log("Laser laserImages path2image callback, i=$i, $laserImages")})
        }
    }

    fun set(i: Int, point: Point, direction: Direction) {
        console.log("Laser set()")
        if (laserImages.size <= i) {
            console.log("Laser laserImages, size=${laserImages.size}, i=$i")
            return
        }
        val image = laserImages[i]
        if (image == null) {
            console.log("Laser ser(), i=$i, size=${laserImages.size} image == null")
            return
        }

        val laserTransform = "translate(${point.x * 20}px, ${point.y * 20}px) rotate(${direction.angle}deg)"
        console.log(laserTransform)
        image.style.transform = laserTransform
        image.style.display = "block"
    }

    fun reset() {
        laserImages.forEach { image ->
            if (image == null) {
                console.log("Laser reset() laserImage x == null")
            } else {
                image.style.display = "none"
            }
        }
    }
}
