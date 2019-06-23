package io.github.golok56

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
                if (it == '\n') {
                    currentLexeme = ""
                    skipUntilNewLine = false
                    return@forEach
                }

                if (currentLexeme.isNotEmpty()) {
                    if (isString) currentLexeme += it
                    else if (currentLexeme.isNonToken()) {
                        currentLexeme = ""
                        skipUntilNewLine = true
                    }
                    else addToTokenStream()
                }
                return@forEach
            }

            if (skipUntilNewLine) {
                return@forEach
            }

            if ((it.isOperator() || it.isDelimiter()) && (currentLexeme.isNumeric() || currentLexeme.isLetter())) {
                addToTokenStream()
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
        tokenStream.add(tokenizer.getToken(currentLexeme))
        currentLexeme = ""
        return tokenStream
    }
}