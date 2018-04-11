package robot

import org.w3c.dom.*
import kotlin.browser.document

class Score {
    private val scoreDiv = document.getElementById("scoreDiv") as HTMLDivElement
    private val table = document.createElement("table") as HTMLTableElement
    private val headers = mutableListOf("Image", "Name", "Author", "Energy", "Hits", "Frags")
    private val headerLength = headers.size
    private val roundText = document.createTextNode("Round: ")
    private val roundNo = document.createTextNode("0")

    constructor(players: Int) {
        for (i in 0..players) {
            val tr = document.createElement("tr")
            for (j in 0..(headerLength - 1)) {
                var cell : Node
                var tag = "td"
                if (i == 0) {
                    tag = "th"
                }
                val tx = document.createElement(tag)
                if (j == 0 && i != 0) {
                    cell = document.createElement("img")
                } else {
                    var text = "."
                    if (i == 0) {
                        text = headers.shift()!!
                    }
                    cell = document.createTextNode(text)
                }
                tx.appendChild(cell)
                tr.appendChild(tx)
            }
            table.appendChild(tr)
        }
        table.asDynamic().border = "1"
        scoreDiv.appendChild(table)
        scoreDiv.appendChild(roundText)
        scoreDiv.appendChild(roundNo)
    }

    fun update(round: Int, tanks: List<Tank>) {
        val trNodes = scoreDiv.childNodes[0]!!.childNodes
        var row = 1
        tanks.forEach { tank ->
            if (round == 1) {
                trNodes[row]!!.childNodes[0]!!.childNodes[0].asDynamic().src = tank.imageId
            }
            trNodes[row]!!.childNodes[1]!!.childNodes[0].asDynamic().data = tank.strategy.name
            trNodes[row]!!.childNodes[2]!!.childNodes[0].asDynamic().data = tank.strategy.author
            trNodes[row]!!.childNodes[3]!!.childNodes[0].asDynamic().data = tank.energy
            trNodes[row]!!.childNodes[4]!!.childNodes[0].asDynamic().data = tank.hits
            trNodes[row]!!.childNodes[5]!!.childNodes[0].asDynamic().data = tank.frags
            row += 1
        }
        scoreDiv.childNodes[2].asDynamic().data = round
    }
}


