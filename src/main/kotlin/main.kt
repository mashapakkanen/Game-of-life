import org.jetbrains.skiko.SkiaWindow
import java.awt.Dimension
import java.io.File
import javax.swing.WindowConstants

var n = 128
var m = 128
var field = Array(n + 2) { Array<Cell>(m + 2) { Cell(0, 0, CellState.DEAD) } }
var game = Board()
var moving = false
val path = File("").absolutePath + "/savings/"
val continueLibingBtns = Array<RuleButton>(9, { RuleButton(1f / 18, 1f / 18 + it * 1f / 9, it, true) })
val startLibingBtns = Array<RuleButton>(9, { RuleButton(3f / 18, 1f / 18 + it * 1f / 9, it, false) })
val btns = mutableListOf<Button>(
    SaveFiledButton(5f / 18, 7f / 18), ReadFiledButton(5f / 18, 9f / 18), MakeMovesButton(5f / 18, 11f / 18),
    SaveImageButton(5f / 18, 13f / 18), ReadImageButton(5f / 18, 15f / 18)
)

fun main() {
    game.readlrtb()
    readRules()
    dialog.DialogStartGame()
}


fun createWindow(title: String)/* = runBlocking(Dispatchers.Swing)*/ {
    val window = SkiaWindow()
    window.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
    window.title = title
    window.layer.renderer = Renderer(window.layer)
    window.layer.addMouseMotionListener(MouseMotionAdapter)
    window.layer.addMouseListener(myMouseListener)
    window.layer.addKeyListener(myKeyListener)
    window.addWindowListener(myWindowListener)
    window.preferredSize = Dimension(800, 600)
    window.minimumSize = Dimension(100, 100)
    window.pack()
    //window.layer.awaitRedraw()
    window.isVisible = true

}


fun createField() {

    for (i in 1..n) {
        for (j in 1..m) {
            if ((0..15).random() < 8)
                field[i][j] = Cell(i, j, CellState.DEAD)
            else {
                field[i][j] = Cell(i, j, CellState.ALIVE)
                field[i][j].changeNeighbors(1)
            }
        }
    }

}

