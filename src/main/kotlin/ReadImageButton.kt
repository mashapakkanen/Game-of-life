import org.jetbrains.skija.Canvas
import java.awt.Color
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

class ReadImageButton(centerX_: Float, centerY_: Float) : Button(centerX_, centerY_) {
    override fun clickedOnBtn(eventX: Float, eventY: Float) {
        if (!isClickedOnBtn(eventX, eventY))
            return
        val jfc: JFileChooser = JFileChooser(FileSystemView.getFileSystemView().createFileObject(path))
        jfc.setDialogTitle("Choose a directory to save your file: ")
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY)
        val returnValue: Int = jfc.showSaveDialog(null)
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (jfc.getSelectedFile().isFile() && jfc.selectedFile.toString().endsWith(".png")) {
                val file = jfc.selectedFile
                val image = ImageIO.read(file)
                if (image == null || image.width < n || image.height < m) {

                    dialog.DialogWarning("Выбран неподходящий файл. Выберите файл подходящего размера .png")
                    return
                }
                for (i in 1..n) {
                    for (j in 1..m) {
                        val color = Color(image.getRGB(i - 1, j - 1))
                        if (color == Color.getColor(null, 0xFF005582.toInt()))
                            field[i][j] = Cell(i, j, 0)
                        else {
                            field[i][j] = Cell(i, j, 1)
                            field[i][j].changeNeighbors(1)
                        }
                    }
                }
            } else {
                dialog.DialogWarning("Выбран неподходящий файл. Выберите файл типа .png")
            }
        }
    }

    override fun drawBtn(canvas: Canvas) {
        drawButton(canvas)
        drawIcon(canvas, "icons/imageRead.png")
    }
}