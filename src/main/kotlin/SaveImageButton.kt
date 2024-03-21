import org.jetbrains.skija.Canvas
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

class SaveImageButton(centerX_: Float, centerY_: Float) : Button(centerX_, centerY_) {
    override fun clickedOnBtn(eventX: Float, eventY: Float) {
        if (!isClickedOnBtn(eventX, eventY))
            return
        val bImg = BufferedImage(n, m, BufferedImage.TYPE_INT_ARGB)
        val graphics = bImg.createGraphics()
        graphics.color = Color.getColor(null, 0xFF005582.toInt())
        graphics.fillRect(0, 0, n, m)
        graphics.color = Color.getColor(null, 0xFFDAF8E3.toInt())
        for (i in 1..n) {
            for (j in 1..m) {
                if (field[i][j].isAlive())
                    graphics.fillRect(i - 1, j - 1, 1, 1)
            }
        }
        graphics.dispose()
        val jfc: JFileChooser = JFileChooser(FileSystemView.getFileSystemView().createFileObject(path))
        jfc.setDialogTitle("Choose a directory to save your file: ")
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY)
        val returnValue: Int = jfc.showSaveDialog(null)
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (jfc.getSelectedFile().isFile() && jfc.selectedFile.toString().endsWith(".png")) {
                val file = jfc.selectedFile
                ImageIO.write(bImg, "png", file)

            } else {
                dialog.DialogWarning("Выбран неподходящий файл. Выберите файл типа .png")
            }
        }

    }


    override fun drawBtn(canvas: Canvas) {
        drawButton(canvas)
        drawIcon(canvas, "icons/imageSave.png")
    }
}