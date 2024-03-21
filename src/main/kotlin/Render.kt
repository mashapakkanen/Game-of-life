import org.jetbrains.skija.*
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaRenderer
import kotlin.time.ExperimentalTime

class Renderer(val layer: SkiaLayer) : SkiaRenderer {
    val typeface = Typeface.makeFromFile("fonts/JetBrainsMono-Regular.ttf")
    val font = Font(typeface, 30F)

    //TODO() изменить назвния цветов
    val backgroundFill = Paint().apply {
        color = 0xFFFFC2CD.toInt()
    }


    @ExperimentalTime
    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {

        if (moving)
            makeMove()
        val contentScale = layer.contentScale
        canvas.scale(contentScale, contentScale)
        val w = (width / contentScale).toInt()
        val h = (height / contentScale).toInt()
        game.upd(w, h)
        continueLibingBtns.forEach { it.upd(w, h) }
        startLibingBtns.forEach { it.upd(w, h) }
        btns.forEach { it.upd(w, h) }

        canvas.drawRect(Rect.makeXYWH(0f, 0f, width.toFloat(), height.toFloat()), backgroundFill)
        game.drawCells(canvas)
        game.drawGrid(canvas)
        continueLibingBtns.forEach { it.drawBtn(canvas) }
        startLibingBtns.forEach { it.drawBtn(canvas) }
        btns.forEach { it.drawBtn(canvas) }
        displayAge(canvas)

        layer.needRedraw()
    }


    /*private fun displayCells(canvas: Canvas, centerX: Float, centerY: Float, fieldRadiusX: Float, fieldRadiusY: Float) {
        val cellDiam = game.cellRadius
        for (chx in wind.xl..wind.xr) {
            if(chx < 1)
                continue
            if(chx > n)
                break
            for (chy in wind.yl..wind.yr) {
                if(chy < 1)
                    continue
                if(chy > m)
                    break
                val cell = field[chx][chy]
                if(!cell.isAlife())
                    continue
                val rect = Rect.makeXYWH(centerX - fieldRadiusX  + cellDiam * (chx-1), centerY - fieldRadiusY  + cellDiam * (chy-1), cellDiam, cellDiam)
                if(cell.isAlife())
                    canvas.drawRect(rect, cellFillAlife)
                //else
                //    canvas.drawRect(rect, cellFillDead)
            }
        }
    }

    private fun displayAge(canvas: Canvas){
        val xy = getFieldCord(State.mouseX, State.mouseY)
        if(xy == null || !field[xy.first][xy.second].isAlife())
            return
        canvas.drawString(field[xy.first][xy.second].age.toString(), State.mouseX, State.mouseY, font, agePaint)
    }*/


}
