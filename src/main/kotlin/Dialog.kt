import java.awt.event.ActionListener
import javax.swing.*

class Dialog : JFrame() {
    val btnSaveGame = JButton("Сохранить игру")
    val btnDontSaveGame = JButton("Не сохранять игру")
    val btnNewGame = JButton("Начать новую игру")
    val btnContinueGame = JButton("Продолжить игру")
    fun cantMakeMoves(reason: String) {
        val message = "Не удалось сделать несколько ходов. Причина: $reason"

        JOptionPane.showMessageDialog(null, message)
    }

    fun DialogMoves() {
        if (moving) {
            cantMakeMoves("автоматическое выполнение ходов не оставновлено")
            return
        }
        val message = JOptionPane.showInputDialog("Сколько ходов сделать? Введите число от 1 до 100")
        if (message == null)
            return
        val cnt = message.toIntOrNull()
        if (cnt == null || cnt < 1 || cnt > 100) {
            cantMakeMoves("введена неподходящая строка. Требуется число от 1 до 100.")
            return
        }
        for (i in 1..cnt)
            makeMove()
    }

    fun DialogWarning(text: String) {
        JOptionPane.showMessageDialog(null, text)
    }

    fun DialogEndGame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE)
        val contents = JPanel()
        contents.add(btnSaveGame)
        contents.add(btnDontSaveGame)
        setContentPane(contents)
        setSize(500, 140);
        setVisible(true);
        btnSaveGame.addActionListener(ActionListener {
            saveField()
            dispose()

        })
        btnDontSaveGame.addActionListener(ActionListener {
            dispose()
        })
    }

    fun DialogStartGame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE)
        val contents = JPanel()
        contents.add(btnNewGame)
        contents.add(btnContinueGame)
        setContentPane(contents)
        setSize(500, 140);
        setVisible(true);
        btnNewGame.addActionListener(ActionListener {
            createField()
            dispose()
            println('?')
            createWindow("Life")

        })
        btnContinueGame.addActionListener(ActionListener {
            readField()
            dispose()
            createWindow("Life")
        })
    }

}
