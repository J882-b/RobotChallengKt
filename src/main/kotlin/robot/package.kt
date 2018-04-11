package robot

import org.w3c.dom.HTMLImageElement
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.js.Math

fun main(args: Array<String>) {
    Game().start()
}

fun <E> MutableList<E>.shift(): E? {
    if (this.size == 0) return null
    val result = this[0]
    this.removeAt(0)
    return result
}

val tankImagePaths = mutableListOf( "images/tank_blue.png", "images/tank_brown.png",
        "images/tank_coral.png", "images/tank_cyan.png", "images/tank_gold.png",
        "images/tank_green.png", "images/tank_indigo.png", "images/tank_red.png")

fun random(n : Int): Int {
    // returns an integer in the range {0, n-1}
    return Math.floor(Math.random() * n)
}

fun path2image(path: String, function: (HTMLImageElement) -> Unit) {
    val image = document.createElement("img") as HTMLImageElement
    image.src = path
    image.onload = {event: Event ->  if (event.type == "load") function(image) else console.log(event.type)}
}
