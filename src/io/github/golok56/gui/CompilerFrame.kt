package io.github.golok56.gui

import io.github.golok56.compiler.Token
import io.github.golok56.compiler.Tokenizer
import io.github.golok56.io.SourceReader
import java.awt.Component
import java.awt.Dimension
import javax.swing.*
import javax.swing.filechooser.FileSystemView

class CompilerFrame : JFrame() {
    private var fileFullPath = "Belum ada file"

    private val lFileName = JLabel(fileFullPath)
    private val lIsSuccess = JLabel().apply { alignmentX = Component.CENTER_ALIGNMENT }
    private val mainPanel = JPanel()

    private lateinit var sourceReader: SourceReader

    init {
        title = "Scanner & Parser"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
        isResizable = false
        setSize(500, 200)
        setLocationRelativeTo(null)
        createLayout()
    }

    private fun createLayout() {
        val verticalBox = Box.createVerticalBox().apply {
            add(Box.createHorizontalBox().apply {
                add(createButton("Pilih File") { openFileChooser() })
                add(Box.createRigidArea(Dimension(10, 0)))
                add(lFileName)
            })
            add(Box.createRigidArea(Dimension(0, 10)))
            add(createButton("Compile") { compile() }.apply { alignmentX = Component.CENTER_ALIGNMENT })
            add(Box.createRigidArea(Dimension(0, 10)))
            add(lIsSuccess)
        }

        mainPanel.layout = BoxLayout(mainPanel, BoxLayout.LINE_AXIS)
        mainPanel.add(Box.createHorizontalGlue())
        mainPanel.add(verticalBox)
        mainPanel.add(Box.createHorizontalGlue())
        contentPane.add(mainPanel)
    }

    private fun createButton(title: String, onClick: () -> Unit) = JButton(title).apply {
        addActionListener { onClick() }
    }

    private fun openFileChooser() {
        val fileChooser = JFileChooser(FileSystemView.getFileSystemView())
        val result = fileChooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            fileFullPath = fileChooser.selectedFile.absolutePath
            lFileName.text = fileFullPath
        }
    }

    private fun compile() = try {
        parse()
    } catch (ex: Exception) {
        JOptionPane.showMessageDialog(null, ex.message)
    }

    private fun parse() {
        sourceReader = SourceReader(fileFullPath)
        val sourceCode = sourceReader.readSourceFile()
        val tokenStream = sourceReader.lexycalAnalysis(sourceCode!!)
        tokenStream?.add(Token(Tokenizer.EPSILON, Tokenizer.EPSILON))
        val tokens = tokenStream?.map { it.detailedToken }
        if (sourceReader.syntaxisAnalysis(tokens!!)) lIsSuccess.text = "Sukses"
        else lIsSuccess.text = "Gagal"
    }
}