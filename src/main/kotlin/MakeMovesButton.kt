import org.jetbrains.skija.Canvas

class MakeMovesButton(centerX_: Float, centerY_: Float) : Button(centerX_, centerY_) {
    override fun clickedOnBtn(eventX: Float, eventY: Float) {
        if (!isClickedOnBtn(eventX, eventY))
            return
        dialog.DialogMoves()
    }

    override fun drawBtn(canvas: Canvas) {
        drawButton(canvas)
        drawIcon(canvas, "icons/moveImg.png")
    }
}