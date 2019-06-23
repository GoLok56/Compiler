package io.github.golok56

class Tokenizer {
    fun getToken(lexeme: String) = when {
        lexeme in KEYWORDS -> Token(lexeme, KEYWORD)
        lexeme in OPERATORS -> Token(lexeme, OPERATOR)
        lexeme in DELIMITERS -> Token(lexeme, DELIMITER)
        !lexeme[0].isDigit() -> Token(lexeme, IDENTIFIER)
        else -> Token(lexeme, LITERAL)
    }

    companion object {
        const val KEYWORD = "keyword"
        const val OPERATOR = "operator"
        const val IDENTIFIER = "identifier"
        const val DELIMITER = "delimiter"
        const val LITERAL = "literal"

        const val OPEN_CURLY_BRACE = "ocb"
        const val CLOSE_CURLY_BRACE = "ccb"
        const val OPEN_PARENTHESES = "op"
        const val CLOSE_PARENTHESES = "cp"
        const val ADDITION = "add"
        const val SUBSTRACTION = "sub"
        const val MULTIPLIER = "mult"
        const val DIVISION = "div"
        const val MODULUS = "mod"
        const val DTYPE = "dtype"
        const val SEMI_COLON = "scl"
        const val COLON = "cl"
        const val COMMA = "cmm"
        const val RETURN = "return"
        const val ASSIGNMENT = "assignment"

        val DATA_TYPE = arrayOf("int", "char", "float", "double")

        val KEYWORDS = arrayOf(
            "auto",
            "double",
            "int",
            "struct",
            "break",
            "else",
            "long",
            "switch",
            "static",
            "case",
            "enum",
            "register",
            "typedef",
            "char",
            "extern",
            "return",
            "union",
            "const",
            "float",
            "short",
            "while",
            "unsigned",
            "continue",
            "for",
            "signed",
            "void",
            "default",
            "goto",
            "sizeof",
            "volatile",
            "do",
            "if"
        )

        val OPERATORS = arrayOf(
            "++", "--", "+", "-", "*", "/", "%", "<", "<=", ">", ">=", "==", "!=", "&&", "||",
            "!", "&", "|", "<<", ">>", "~", "^", "=", "+=", "-=", "*=", "/=", "%="
        )

        val DELIMITERS = arrayOf(";", ",", ":", "(", ")", "{", "}")
    }
}