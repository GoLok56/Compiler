package io.github.golok56.compiler

class Tokenizer {
    fun getToken(lexeme: String) = when {
        lexeme in KEYWORDS -> Token(
            lexeme,
            KEYWORD
        )
        lexeme in OPERATORS -> Token(
            lexeme,
            OPERATOR
        )
        lexeme in DELIMITERS -> Token(
            lexeme,
            DELIMITER
        )
        lexeme.startsWith("\"") && lexeme.endsWith("\"") -> Token(
            lexeme,
            LITERAL
        )
        !lexeme[0].isDigit() -> Token(
            lexeme,
            IDENTIFIER
        )
        else -> Token(lexeme, LITERAL)
    }

    companion object {
        const val KEYWORD = "keyword"
        const val OPERATOR = "operator"
        const val IDENTIFIER = "identifier"
        const val DELIMITER = "delimiter"
        const val EPSILON = "epsilon"

        const val LITERAL = "literal"
        const val COMPARATOR = "comparator"
        const val NUMBER_LITERAL = "numliteral"
        const val STRING_LITERAL = "strliteral"
        const val OPEN_CURLY_BRACE = "ocb"
        const val CLOSE_CURLY_BRACE = "ccb"
        const val OPEN_PARENTHESES = "op"
        const val CLOSE_PARENTHESES = "cp"
        const val OPEN_SQUARE_BRACKET = "osp"
        const val CLOSE_SQUARE_BRACKET = "csp"
        const val ADDITION = "add"
        const val SUBSTRACTION = "sub"
        const val MULTIPLIER = "mult"
        const val TERNARY = "ternary"
        const val DIVISION = "div"
        const val MODULUS = "mod"
        const val DTYPE = "dtype"
        const val SEMI_COLON = "scl"
        const val COLON = "cl"
        const val COMMA = "cmm"
        const val DOT = "dot"
        const val RETURN = "return"
        const val ASSIGNMENT = "assignment"
        const val VOID = "void"
        const val CONST = "const"
        const val INCDEC = "incdec"
        const val OR = "or"
        const val AND = "and"
        const val STATIC = "static"
        const val IF = "if"
        const val ELSE = "else"
        const val WHILE = "while"
        const val BREAK = "break"

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

        val DELIMITERS = arrayOf(";", ",", ":", "(", ")", "{", "}", ".")
    }
}