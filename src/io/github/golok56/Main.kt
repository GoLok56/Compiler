package io.github.golok56

import kotlin.system.exitProcess

fun validateInput(args: Array<String>) {
    if (args.isEmpty()) handleError(
        Error.NO_INPUT,
        "Tidak ditemukan berkas sumber! Silakan ulangi dan pastikan berkas sumber ada."
    )
}

fun tryReadSourceFile(filename: String): String? = try {
    SourceReader().read(filename)
} catch (exception: IllegalArgumentException) {
    handleError(Error.ILLEGAL_INPUT, exception.message!!)
    null
}

fun tryLexycalAnalysis(sourceCode: String): MutableList<Token>? = try {
    Scanner().scan(sourceCode)
} catch (exception: Exception) {
    handleError(Error.LEXYCAL_ERROR, exception.message!!)
    null
}

fun handleError(errorCode: Int, message: String) {
    println("Error #$errorCode: $message")
    exitProcess(errorCode)
}

fun main(args: Array<String>) {
    validateInput(args)
    val sourceCode = tryReadSourceFile(args[0])
    val tokenStream = tryLexycalAnalysis(sourceCode!!)
    tokenStream?.forEach { println("${it.lexeme} ${it.token} ${it.detailedToken}") }
}