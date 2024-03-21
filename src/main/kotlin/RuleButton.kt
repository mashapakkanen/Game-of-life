import org.jetbrains.skija.Canvas
import org.jetbrains.skija.Font
import org.jetbrains.skija.Paint
import org.jetbrains.skija.Typeface

class RuleButton(centerX_: Float, centerY_: Float, num_: Int, type_: Boolean) : Button(centerX_, centerY_) {
    val type: Boolean
    val num: Int
    val state: Boolean
        get() {
            if (type)
                return continueLiving[num]
            else
                return startLiving[num]
        }

    init {
        type = type_
        num = num_
    }

    val btnFillDead = Paint().apply {
        color = 0xFF005582.toInt()
    }
    val btnFillAlive = Paint().apply {
        color = 0xFFDAF8E3.toInt()
    }

    override fun drawBtn(canvas: Canvas) {
        if (state)
            drawButton(canvas, btnFillAlive)
        else
            drawButton(canvas, btnFillDead)

        canvas.drawString(num.toString(),
            xl + radius / 2.5f,
            yr - radius / 3,
            Font(Typeface.makeFromFile("fonts/JetBrainsMono-Regular.ttf"), radius * 2),
            Paint().apply { color = 0xFFFF6289.toInt() })

    }

    override fun clickedOnBtn(eventX: Float, eventY: Float) {
        if (!isClickedOnBtn(eventX, eventY))
            return
        if (type)
            continueLiving[num] = !continueLiving[num]
        else
            startLiving[num] = !startLiving[num]
    }
}