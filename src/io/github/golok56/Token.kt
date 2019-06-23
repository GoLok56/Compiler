package io.github.golok56

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
                lexeme == "=" -> Tokenizer.ASSIGNMENT
                lexeme == "return" -> Tokenizer.RETURN
                lexeme in Tokenizer.DATA_TYPE -> Tokenizer.DTYPE
                token == Tokenizer.LITERAL || token == Tokenizer.IDENTIFIER -> token
                else -> ""
            }
        }
}