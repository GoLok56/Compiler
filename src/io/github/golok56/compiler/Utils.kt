package io.github.golok56.compiler

fun String.isNumeric(): Boolean {
    this.forEach { if (!it.isDigit()) return false }
    return this.isNotEmpty()
}

fun String.isLetter(): Boolean {
    this.forEach { if (!it.isLetter()) return false }
    return this.isNotEmpty()
}

fun String.isOperator(): Boolean {
    this.forEach { if (!it.isOperator()) return false }
    return this.isNotEmpty()
}

fun String.isDelimiter(): Boolean {
    this.forEach { if (!it.isDelimiter()) return false }
    return this.isNotEmpty()
}

fun String.isNonToken() = this.startsWith("#") || this.startsWith("//") || this.startsWith("<")

fun Char.isOperator() = this.toString() in Tokenizer.OPERATORS
fun Char.isDelimiter() = this.toString() in Tokenizer.DELIMITERS