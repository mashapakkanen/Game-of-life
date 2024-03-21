import org.jetbrains.skija.Canvas
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView


class ReadFiledButton(centerX_:Float, centerY_: Float):Button(centerX_, centerY_) {
    override fun clickedOnBtn(eventX:Float, eventY:Float){
        if(!isClickedOnBtn(eventX, eventY))
            return
        val jfc: JFileChooser = JFileChooser(FileSystemView.getFileSystemView().createFileObject(path))
        jfc.setDialogTitle("Choose a directory to save your file: ")
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES)

        val returnValue: Int = jfc.showSaveDialog(null)
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (jfc.getSelectedFile().isFile()) {
                val file = jfc.selectedFile!!
                readField("", file.path)
            }
        }

    }
    override fun drawBtn(canvas: Canvas){
        drawButton(canvas)
        drawIcon(canvas, "icons/readFieldIcon.png")
    }
}