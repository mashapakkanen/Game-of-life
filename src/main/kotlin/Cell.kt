import org.jetbrains.skija.Canvas
import org.jetbrains.skija.Font
import org.jetbrains.skija.Paint
import org.jetbrains.skija.Typeface
import java.io.File

enum class CellState {
    ALIVE, DEAD
}

val continueLiving = Array<Boolean>(9) { false }
val startLiving = Array<Boolean>(9) { false }

class Cell(val x: Int, val y: Int, var st: CellState) {
    var age = if(isAlive()) 1 else 0
    var aliveNeighbors = 0

    constructor(x: Int, y: Int, st_: Int) : this(x, y, if (st_ == 1) CellState.ALIVE else CellState.DEAD)
    fun isAlive() = st == CellState.ALIVE

    fun changeState() {
        if (isAlive()) {
            st = CellState.DEAD
            changeNeighbors(-1)
            age = 0
        } else {
            st = CellState.ALIVE
            changeNeighbors(1)
            age = 1
        }
    }

    fun changeNeighbors(del: Int) {
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0)
                    continue
                if (x + i in 1..n && y + j in 1..m)
                    field[x + i][y + j].aliveNeighbors += del
            }
        }
    }

    fun updState() {
        if (isAlive()) {
            if (!continueLiving[aliveNeighbors]) { //default 2 3 live
                st = CellState.DEAD
                age = 0
            }
        } else {
            if (startLiving[aliveNeighbors]) { //default 3 live
                st = CellState.ALIVE
                age = 0
            }
        }
        if (isAlive())
            age++
        aliveNeighbors = 0
    }


}

fun makeDefaultRules() {
    continueLiving[2] = true
    continueLiving[3] = true
    startLiving[3] = true
}


fun saveRules() {
    val file = File("$path/lastRules")
    file.delete()
    file.createNewFile()
    val writer = file.bufferedWriter()
    writer.append("rules")
    writer.newLine()
    for (i in 0..8) {
        if (continueLiving[i])
            writer.append("1 ")
        else
            writer.append("0 ")
    }
    writer.newLine()
    for (i in 0..8) {
        if (startLiving[i])
            writer.append("1 ")
        else
            writer.append("0 ")
    }
    writer.close()
}

fun readRules() {
    if (!File("$path/lastRules").isFile) {
        makeDefaultRules()
        return
    }
    val file = File("$path/lastRules")
    if (file.readLines().size != 3) {
        makeDefaultRules()
        return
    }
    val reader = file.bufferedReader()
    if (reader.readLine() != "rules") {
        makeDefaultRules()
        return
    }
    println('!')
    val continuelstr = reader.readLine().split(" ")
    val startlstr = reader.readLine().split(" ")
    if (continuelstr.size < 9 || startlstr.size < 9) {
        makeDefaultRules()
        return
    }
    for (pairI in continuelstr.withIndex()) {
        val i = pairI.index
        val st = pairI.value.toIntOrNull()
        if (i > 8)
            break
        if (st == null || (st != 0 && st != 1)) {
            makeDefaultRules()
            return
        } else continueLiving[i] = st == 1
    }
    for (pairI in startlstr.withIndex()) {
        val i = pairI.index
        val st = pairI.value.toIntOrNull()
        if (i > 8)
            break
        if (st == null || (st != 0 && st != 1)) {
            makeDefaultRules()
            return
        } else startLiving[i] = st == 1
    }
    println('!')
}


fun makeMove() {
    for (i in 1..n) {
        for (j in 1..m) {
            field[i][j].updState()
        }
    }
    for (i in 1..n) {
        for (j in 1..m) {
            if (field[i][j].isAlive())
                field[i][j].changeNeighbors(1)
        }
    }
}

fun displayAge(canvas: Canvas) {
    val xy = game.getCellByPoint(State.mouseX, State.mouseY)
    if (xy == null || !field[xy.first][xy.second].isAlive())
        return
    canvas.drawString(field[xy.first][xy.second].age.toString(),
        State.mouseX,
        State.mouseY,
        Font(Typeface.makeFromFile("fonts/JetBrainsMono-Regular.ttf"), 30F),
        Paint().apply { color = 0xFFFF6289.toInt() })
}

fun saveField(fileName: String = "lastGameField", filePath: String = path) {
    val file = File("$filePath/$fileName")
    file.delete()
    file.createNewFile()
    val writer = file.bufferedWriter()
    writer.write("$n $m")
    writer.newLine()
    for (i in 1..n) {
        for (j in 1..m) {
            if (field[i][j].isAlive())
                writer.write("1 ")
            else
                writer.write("0 ")
        }
        writer.newLine()
    }
    writer.close()
}

fun readField(fileName: String = "lastGameField", filePath: String = path) {
    if (!File("$filePath/$fileName").isFile()) {
        println("$fileName $filePath")
        throw noFile(fileName, filePath)
    }
    val file = File(filePath + fileName)
    if (file.readLines().size < n + 1)
        throw wrongFile(filePath, fileName)
    val reader = file.bufferedReader()
    val nm = reader.readLine().split(" ")
    if (nm.size < 2 || n != nm[0].toIntOrNull() || m != nm[1].toIntOrNull()) {
        throw wrongFile(fileName, "field")
    }
    for (i in 1..n) {
        val arr = reader.readLine().split(" ")
        if (arr.size < m)
            throw wrongFile(fileName, "field")
        for (jval in arr.withIndex()) {
            if (jval.value.toIntOrNull() == null)
                continue
            val j = jval.index + 1
            val st = jval.value.toInt()
            if (st != 0 && st != 1)
                throw wrongFile(fileName, "field")
            field[i][j] = Cell(i, j, st)
            if (st == 1)
                field[i][j].changeNeighbors(1)
        }
    }
}