import org.jetbrains.skija.Canvas
import org.jetbrains.skija.Paint
import org.jetbrains.skija.Rect
import java.io.File
import java.lang.Float.min
import java.lang.Math.max
import kotlin.math.ceil
import kotlin.math.floor

class Board {
    var top = 0f
    var bottom = 1f
    var left = 0f
    var right = 1f
    var cordLeft = 0f
    var cordRight = 1f
    var cordTop = 0f
    var cordBottom = 1f
    private val eps = 0.0001f

    init {
        defaultlrtb()
    }

    var borderX = 0f
    var borderY = 0f
    var sideLenght = 0f
    var cellDiam = 0f
    var velocity = 0.01f
    val scalingX = 0.01f * (n / m)
    val scalingY = 0.01f
    fun upd(newW: Int, newH: Int) {
        val centerX = newW * 0.67f
        val centerY = newH * 0.5f
        val radius = min(centerY, centerX * 0.5f) * 0.8f

        borderX = centerX - radius
        borderY = centerY - radius
        sideLenght = 2 * radius

        cordLeft = left * sideLenght + borderX
        cordRight = right * sideLenght + borderX
        cordTop = top * sideLenght + borderY
        cordBottom = bottom * sideLenght + borderY
        cellDiam = (cordRight - cordLeft) / n
    }

    fun defaultlrtb() {
        if (n < m) {
            left = (m - n) * 1f / (2 * m)
            right = (m + n) * 1f / (2 * m)
            top = 0f
            bottom = 1f
        } else {
            left = 0f
            right = 1f
            top = (n - m) * 1f / (2 * n)
            bottom = (n + m) * 1f / (2 * n)
        }
    }

    fun savelrtb() {
        val file = File("$path/lastlrtb")
        file.delete()
        file.createNewFile()
        file.writeText("$left $right $top $bottom")
    }

    fun readlrtb() {
        if (!File("$path/lastlrtb").isFile) {
            defaultlrtb()
            return
        }
        val text = File("$path/lastlrtb").readText().split(" ")
        if (text.size != 4 && text.any { it.toFloatOrNull() == null }) {
            defaultlrtb()
            return
        }
        left = text[0].toFloat()
        right = text[1].toFloat()
        top = text[2].toFloat()
        bottom = text[3].toFloat()
    }

    fun getRectByCell(x: Int, y: Int):
            Rect = Rect.makeXYWH(cordLeft + cellDiam * (x - 1), cordTop + cellDiam * (y - 1), cellDiam, cellDiam)

    fun getCellByPoint(x: Float, y: Float): Pair<Int, Int>? {
        if (x < max(cordLeft, borderX) || min(borderX + sideLenght, cordRight) < x ||
            y < max(cordTop, borderY) || min(borderY + sideLenght, cordBottom) < y
        )
            return null
        val i = ((x - cordLeft) / cellDiam).toInt() + 1
        val j = ((y - cordTop) / cellDiam).toInt() + 1
        return Pair(i, j)
    }

    fun goUp() {
        top -= velocity
        bottom -= velocity
    }

    fun goDown() {
        top += velocity
        bottom += velocity
    }

    fun goLeft() {
        left -= velocity
        right -= velocity
    }

    fun goRight() {
        left += velocity
        right += velocity
    }

    fun goBigger(sgn: Int = 1) {
        if (sgn == -1 && min(right - left, bottom - top) <= 0.1)
            return
        if (sgn == 1 && max(right - left, bottom - top) >= 100f)
            return
        left -= scalingX * sgn
        right += scalingX * sgn
        top -= scalingY * sgn
        bottom += scalingY * sgn
    }

    fun goLower() {
        goBigger(-1)
    }

    fun getFirstCellX(): Int = ceil(max(0f, borderX - cordLeft) / cellDiam - eps).toInt() + 1
    fun getLastCellX(): Int = floor((min(borderX + sideLenght, cordRight) - cordLeft) / cellDiam + eps).toInt()
    fun getFirstCellY(): Int = ceil(max(0f, borderY - cordTop) / cellDiam - eps).toInt() + 1
    fun getLastCellY(): Int = floor((min(borderY + sideLenght, cordBottom) - cordTop) / cellDiam + eps).toInt()


    val linesFill = Paint().apply {
        color = 0xFF97EBDB.toInt()
        strokeWidth = cellDiam / 10
    }
    val cellFillDead = Paint().apply {
        color = 0xFF005582.toInt()
    }
    val cellFillAlive = Paint().apply {
        color = 0xFFDAF8E3.toInt()
    }

    fun drawGrid(canvas: Canvas) {
        val lines = mutableListOf<Float>()
        lines.add(borderX)
        lines.add(borderY)
        lines.add(borderX + sideLenght)
        lines.add(borderY)

        lines.add(borderX)
        lines.add(borderY)
        lines.add(borderX)
        lines.add(borderY + sideLenght)

        lines.add(borderX + sideLenght)
        lines.add(borderY)
        lines.add(borderX + sideLenght)
        lines.add(borderY + sideLenght)

        lines.add(borderX)
        lines.add(borderY + sideLenght)
        lines.add(borderX + sideLenght)
        lines.add(borderY + sideLenght)

        canvas.drawLines(lines.toFloatArray(), linesFill)

        lines.clear()
        for (i in getFirstCellX() - 1..getLastCellX()) {
            if (getFirstCellY() > getLastCellY())
                break
            val cordI = i * cellDiam + cordLeft
            lines.add(cordI)
            lines.add(max(borderY, cordTop))
            lines.add(cordI)
            lines.add(min(borderY + sideLenght, cordBottom))
        }
        for (i in getFirstCellY() - 1..getLastCellY()) {
            if (getFirstCellX() > getLastCellX())
                break
            val cordI = i * cellDiam + cordTop
            lines.add(max(borderX, cordLeft))
            lines.add(cordI)
            lines.add(min(borderX + sideLenght, cordRight))
            lines.add(cordI)
        }
        if (lines.size <= 1000) canvas.drawLines(lines.toFloatArray(), linesFill)
        //TODO() убрать константу
    }

    fun drawCells(canvas: Canvas) {
        canvas.drawRect(
            Rect.makeLTRB(
                borderX, borderY, borderX + sideLenght, borderY + sideLenght
            ), cellFillDead
        )
        //println((min(borderX + sideLenght, cordRight) - cordLeft)/ cellDiam)
        //println((min(borderY + sideLenght, cordBottom) - cordTop) / cellDiam)
        //println(getFirstCellX().toString() + " " + getLastCellX())
        for (x in getFirstCellX()..getLastCellX()) {
            for (y in getFirstCellY()..getLastCellY()) {
                if (field[x][y].isAlive()) {
                    canvas.drawRect(getRectByCell(x, y), cellFillAlive)
                    //println(x.toString() + ' ' + y)
                }
            }
        }

        //println("!!!")
        /*println(borderX.toString() + ' ' + (borderX + sideLenght))
        println(cordLeft.toString() + ' ' + cordRight)
        println(borderY.toString() + ' ' + (borderY + sideLenght))
        println(cordTop.toString() + ' ' + cordBottom)*/
    }
}