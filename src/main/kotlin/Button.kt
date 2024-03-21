import org.jetbrains.skija.Canvas
import org.jetbrains.skija.Image
import org.jetbrains.skija.Paint
import org.jetbrains.skija.Rect
import java.io.File
import java.lang.Float.min

open class Button(val centerX: Float, val centerY: Float) {
    var curW = 0f
    var curH = 0f
    val radius
        get() = min(curW, curH * 0.8f) / 18 * 0.8f
    val xl: Float
        get() = centerX * curW - radius
    val xr: Float
        get() = centerX * curW + radius
    val yl: Float
        get() = centerY * curH - radius
    val yr: Float
        get() = centerY * curH + radius

    fun upd(newW: Int, newH: Int) {
        curW = newW.toFloat()
        curH = newH.toFloat()
    }

    fun getRect(): Rect = Rect.makeLTRB(xl, yl, xr, yr)
    fun isClickedOnBtn(eventX: Float, eventY: Float) = (xl <= eventX && eventX <= xr &&
            yl <= eventY && eventY <= yr)

    fun drawButton(canvas: Canvas, paint: Paint = Paint().apply { color = 0xFF97EBDB.toInt() }) {
        canvas.drawRect(getRect(), paint)
        //println(xl.toString()  + " " + yl + " " + xr + " " + yr )
    }

    fun drawIcon(canvas: Canvas, pathImg: String) {
        val img = Image.makeFromEncoded(File(pathImg).readBytes())
        canvas.drawImageRect(img, getRect())
    }

    open fun clickedOnBtn(eventX: Float, eventY: Float) {}
    open fun drawBtn(canvas: Canvas) {}


}