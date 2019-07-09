package io.github.golok56.io

import io.github.golok56.compiler.Parser
import io.github.golok56.compiler.Scanner
import io.github.golok56.compiler.Token

class SourceReader(private val fileFullPath: String) {
    init {
        if (fileFullPath == "Belum ada file") {
            throw Exception("Tidak ditemukan berkas sumber! Silakan ulangi dan pastikan berkas sumber ada.")
        }
    }

    fun readSourceFile(): String? = FileReader().read(fileFullPath)
    fun lexycalAnalysis(sourceCode: String): MutableList<Token>? = Scanner().scan(sourceCode)
    fun syntaxisAnalysis(tokenStream: List<String>): Boolean = Parser(tokenStream).parse()
}