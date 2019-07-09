package io.github.golok56.compiler

class Scanner {
    private val tokenizer = Tokenizer()

    fun scan(source: String): MutableList<Token> {
        val tokenStream = mutableListOf<Token>()
        var currentLexeme = ""
        var isString = false
        var skipUntilNewLine = false
        fun addToTokenStream() {
            tokenStream.add(tokenizer.getToken(currentLexeme))
            currentLexeme = ""
        }

        source.forEach {
            if (it.isWhitespace()) {
                if (it == '\n' && skipUntilNewLine) {
                    currentLexeme = ""
                    skipUntilNewLine = false
                    return@forEach
                }

                if (currentLexeme.isNotEmpty()) {
                    when {
                        isString -> currentLexeme += it
                        currentLexeme.isNonToken() -> {
                            currentLexeme = ""
                            skipUntilNewLine = true
                        }
                        else -> addToTokenStream()
                    }
                }
                return@forEach
            }

            if (currentLexeme == "//" && !skipUntilNewLine) {
                skipUntilNewLine = true
                return@forEach
            }

            if (skipUntilNewLine) {
                return@forEach
            }

            if ((it.isOperator() || it.isDelimiter()) && (currentLexeme.isNumeric() || currentLexeme.isLetter())) {
                if (!(it == '.' && currentLexeme.isNumeric())) {
                    addToTokenStream()
                }
            } else if ((it.isDigit() || it.isLetter()) && (currentLexeme.isOperator() || currentLexeme.isDelimiter())) {
                addToTokenStream()
            } else if (currentLexeme.isNumeric() && it.isLetter()) {
                throw Exception("Ditemukan leksikal yang tidak valid.")
            } else if (currentLexeme.isNotEmpty() && it.isDelimiter()) {
                addToTokenStream()
            }

            if (it == '"') {
                if (!isString) addToTokenStream()
                isString = !isString
            }

            currentLexeme += it
        }
        if (!skipUntilNewLine) {
            tokenStream.add(tokenizer.getToken(currentLexeme))
            currentLexeme = ""
        }
        return tokenStream
    }
}