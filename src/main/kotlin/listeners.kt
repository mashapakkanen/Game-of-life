import java.awt.event.*
import java.awt.event.MouseMotionAdapter

fun pressed(event: MouseEvent) {
    continueLibingBtns.forEach { it.clickedOnBtn(event.x.toFloat(), event.y.toFloat()) }
    startLibingBtns.forEach { it.clickedOnBtn(event.x.toFloat(), event.y.toFloat()) }
    btns.forEach { it.clickedOnBtn(event.x.toFloat(), event.y.toFloat()) }
    //println((game.centerX - game.radius).toString() + " " + event.x + " " + (game.centerX + game.radius) + " "  + game.cellRadius)
    val xy = game.getCellByPoint(event.x.toFloat(), event.y.toFloat())
    if (xy == null)
        return
    field[xy.first][xy.second].changeState()
    //println(x.toString() + " " + field[x][y].x)
}

fun pressedKey(event: KeyEvent?) {
    if (event == null)
        return
    if (event.keyCode == KeyEvent.VK_E)
        makeMove()
    if (event.keyCode == KeyEvent.VK_SPACE)
        moving = !moving
    if (event.keyCode == KeyEvent.VK_F5)
        createField()
    if (event.keyCode == KeyEvent.VK_W)
        game.goUp()
    if (event.keyCode == KeyEvent.VK_S)
        game.goDown()
    if (event.keyCode == KeyEvent.VK_A)
        game.goLeft()
    if (event.keyCode == KeyEvent.VK_D)
        game.goRight()
    if (event.keyCode == KeyEvent.VK_EQUALS)
        game.goBigger()
    if (event.keyCode == KeyEvent.VK_MINUS)
        game.goLower()
    if (event.keyCode == KeyEvent.VK_O)
        game.defaultlrtb()
}

object State {
    var mouseX = 0f
    var mouseY = 0f
}

object MouseMotionAdapter : MouseMotionAdapter() {
    override fun mouseMoved(event: MouseEvent) {
        State.mouseX = event.x.toFloat()
        State.mouseY = event.y.toFloat()
    }
}

val myMouseListener = object : MouseListener {
    override fun mouseClicked(e: MouseEvent) {
        pressed(e)
    }

    override fun mouseExited(e: MouseEvent) {}
    override fun mousePressed(e: MouseEvent) {}
    override fun mouseEntered(e: MouseEvent) {}
    override fun mouseReleased(e: MouseEvent) {}
}

val myKeyListener = object : KeyListener {
    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {
        pressedKey(e)
    }

    override fun keyReleased(e: KeyEvent?) {}

}
val dialog = Dialog()

val myWindowListener = object : WindowListener {
    override fun windowOpened(e: WindowEvent?) {}

    override fun windowClosing(e: WindowEvent?) {}

    override fun windowClosed(e: WindowEvent?) {
        saveRules()
        game.savelrtb()
        dialog.DialogEndGame()
    }

    override fun windowIconified(e: WindowEvent?) {}

    override fun windowDeiconified(e: WindowEvent?) {}

    override fun windowActivated(e: WindowEvent?) {}

    override fun windowDeactivated(e: WindowEvent?) {}

}