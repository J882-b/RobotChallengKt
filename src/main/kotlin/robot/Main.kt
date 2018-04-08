package robot

import org.w3c.dom.HTMLImageElement
import kotlin.browser.document
import kotlin.js.Math

class Main {
    fun main(args: Array<String>) {
        println("Hello console!")
        Game().start()
    }
}

fun <E> MutableList<E>.shift(): E? {
    if (this.size == 0) return null
    val result = this[0]
    this.removeAt(0)
    return result
}

fun random(n : Int): Int {
    // returns an integer in the range {0, n-1}
    return Math.floor(Math.random() * n);
}

fun path2image(path: String, function: (HTMLImageElement) -> Unit) {
    val image = document.createElement("img") as HTMLImageElement
    image.asDynamic().src = path
    image.asDynamic().onload = function(image)
}