package io.github.golok56.compiler

class Token(
    val lexeme: String,
    val token: String
) {
    val detailedToken: String
        get() {
            return when {
                lexeme == "{" -> Tokenizer.OPEN_CURLY_BRACE
                lexeme == "}" -> Tokenizer.CLOSE_CURLY_BRACE
                lexeme == "(" -> Tokenizer.OPEN_PARENTHESES
                lexeme == ")" -> Tokenizer.CLOSE_PARENTHESES
                lexeme == "+" -> Tokenizer.ADDITION
                lexeme == "-" -> Tokenizer.SUBSTRACTION
                lexeme == "*" -> Tokenizer.MULTIPLIER
                lexeme == "/" -> Tokenizer.DIVISION
                lexeme == "%" -> Tokenizer.MODULUS
                lexeme == ":" -> Tokenizer.COLON
                lexeme == ";" -> Tokenizer.SEMI_COLON
                lexeme == "," -> Tokenizer.COMMA
                lexeme == "?" -> Tokenizer.TERNARY
                lexeme == "." -> Tokenizer.DOT
                lexeme == "||" -> Tokenizer.OR
                lexeme == "&&" -> Tokenizer.AND
                lexeme == "return" -> Tokenizer.RETURN
                lexeme == "void" -> Tokenizer.VOID
                lexeme == "const" -> Tokenizer.CONST
                lexeme == "static" -> Tokenizer.STATIC
                lexeme == "if" -> Tokenizer.IF
                lexeme == "else" -> Tokenizer.ELSE
                lexeme == "while" -> Tokenizer.WHILE
                lexeme == "break" -> Tokenizer.BREAK
                lexeme in arrayOf("==", "!=", "<", ">", "<=", ">=") -> Tokenizer.COMPARATOR
                lexeme in arrayOf("=", "+=", "-=", "*=", "/=", "%=") -> Tokenizer.ASSIGNMENT
                lexeme in Tokenizer.DATA_TYPE -> Tokenizer.DTYPE
                token == Tokenizer.LITERAL && lexeme.startsWith("\"") -> Tokenizer.STRING_LITERAL
                token == Tokenizer.LITERAL -> Tokenizer.NUMBER_LITERAL
                token == Tokenizer.IDENTIFIER || token == Tokenizer.EPSILON -> token
                else -> ""
            }
        }
}