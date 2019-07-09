package io.github.golok56.compiler

class Parser(private val inputTokens: List<String>) {
    private var currentPos = 0
    private val currentToken
        get() = inputTokens[currentPos]

    fun parse(): Boolean {
        return declarationList()
    }

    private fun match(token: String) = if (currentToken == token) {
        if (currentPos < inputTokens.size) currentPos++
        true
    } else false

    private fun declarationList(): Boolean = when {
        declaration() -> {
            if (match(Tokenizer.EPSILON)) true
            else declarationList()
        }
        else -> false
    }

    private fun declaration(): Boolean {
        val currentPosition = currentPos
        val result = varDeclaration()
        return if (!result) {
            currentPos = currentPosition
            funDeclaration()
        } else result
    }

    private fun func() = match(Tokenizer.IDENTIFIER) && match(Tokenizer.OPEN_PARENTHESES) && params() &&
            match(Tokenizer.CLOSE_PARENTHESES) && statement()

    private fun funDeclaration() = (match(Tokenizer.DTYPE) && func()) || func()

    private fun statement(): Boolean = expressionStmt() || compoundStmt() || selectionStmt() || iterationStmt() ||
            returnStmt() || breakStmt()

    private fun expressionStmt() = (expression() && match(Tokenizer.SEMI_COLON)) || match(
        Tokenizer.SEMI_COLON
    )

    private fun condition() = match(Tokenizer.OPEN_PARENTHESES) && simpleExpression() &&
            match(Tokenizer.CLOSE_PARENTHESES) && statement()

    private fun ifStmt() = match(Tokenizer.IF) && condition()
    private fun selectionStmt() = ifStmt() || (ifStmt() && match(Tokenizer.ELSE) && statement())
    private fun iterationStmt() = match(Tokenizer.WHILE) || condition()
    private fun breakStmt() = match(Tokenizer.BREAK) && match(Tokenizer.SEMI_COLON)
    private fun returnStmt() = if (match(Tokenizer.RETURN)) {
        if (match(Tokenizer.SEMI_COLON)) true
        else expression() && match(Tokenizer.SEMI_COLON)
    } else false


    private fun compoundStmt() = match(Tokenizer.OPEN_CURLY_BRACE) && localDeclarations() && statementList() &&
            match(Tokenizer.CLOSE_CURLY_BRACE)

    private fun localDeclarations(): Boolean {
        while (scopedVarDeclaration());
        return true
    }

    private fun scopedVarDeclaration() = scopedTypeIdentifier() || varDeclarationList()
    private fun scopedTypeIdentifier() = (match(Tokenizer.STATIC) && match(Tokenizer.DTYPE)) || match(
        Tokenizer.DTYPE
    )

    private fun statementList(): Boolean {
        while (statement());
        return if (match(Tokenizer.CLOSE_CURLY_BRACE)) {
            currentPos--
            true
        } else false
    }

    private fun params() = when {
        match(Tokenizer.CLOSE_PARENTHESES) -> {
            currentPos--
            true
        }
        paramList() -> true
        else -> false
    }

    private fun paramList(): Boolean = if (paramTypeList()) {
        if (match(Tokenizer.SEMI_COLON)) paramList()
        else true
    } else false

    private fun paramTypeList() = match(Tokenizer.DTYPE) && paramIdList()

    private fun paramIdList(): Boolean = if (paramId()) {
        if (match(Tokenizer.COMMA)) paramIdList()
        else true
    } else false

    private fun paramId() = match(Tokenizer.IDENTIFIER) || (match(Tokenizer.IDENTIFIER)
            && match(Tokenizer.OPEN_SQUARE_BRACKET) && match(Tokenizer.CLOSE_SQUARE_BRACKET))

    private fun varDeclaration() = if (match(Tokenizer.DTYPE)) {
        if (varDeclarationList()) match(Tokenizer.SEMI_COLON)
        else false
    } else false

    private fun varDeclarationList(): Boolean {
        var isSuccess = false
        return when {
            varDeclInitialize() -> {
                if (match(Tokenizer.SEMI_COLON)) {
                    isSuccess = true
                    currentPos--
                } else if (match(Tokenizer.COMMA)) {
                    isSuccess = varDeclarationList()
                }
                isSuccess
            }
            else -> false
        }
    }

    private fun varDeclInitialize(): Boolean = if (varDeclId()) {
        when {
            match(Tokenizer.ASSIGNMENT) -> {
                if (simpleExpression(true)) {
                    if (match(Tokenizer.COMMA)) varDeclarationList()
                    else true
                } else false
            }
            match(Tokenizer.COMMA) || match(Tokenizer.SEMI_COLON) -> {
                currentPos--
                true
            }
            else -> true
        }
    } else false

    private fun varDeclId() = if (match(Tokenizer.IDENTIFIER)) {
        if ((match(Tokenizer.OPEN_SQUARE_BRACKET) && match(Tokenizer.NUMBER_LITERAL) &&
                    match(Tokenizer.CLOSE_SQUARE_BRACKET))
        ) true
        else if (match(Tokenizer.COMMA) || match(Tokenizer.SEMI_COLON) || match(
                Tokenizer.ASSIGNMENT
            )) {
            currentPos--
            true
        } else false
    } else false

    private fun simpleExpression(isVarDeclaration: Boolean = false): Boolean {
        var isSuccess = false
        return when {
            andExpression(isVarDeclaration) -> {
                if (match(Tokenizer.SEMI_COLON) || (isVarDeclaration && match(Tokenizer.COMMA))) {
                    isSuccess = true
                    currentPos--
                }
                while (match(Tokenizer.OR)) {
                    isSuccess = andExpression(isVarDeclaration)
                }
                isSuccess
            }
            else -> false
        }
    }

    private fun andExpression(isVarDeclaration: Boolean): Boolean {
        var isSuccess = false
        return when {
            unaryRelExpression(isVarDeclaration) -> {
                if (match(Tokenizer.SEMI_COLON) || (isVarDeclaration && match(Tokenizer.COMMA))) {
                    isSuccess = true
                    currentPos--
                }
                while (match(Tokenizer.AND)) {
                    isSuccess = unaryRelExpression(isVarDeclaration)
                }
                isSuccess
            }
            else -> false
        }
    }

    private fun unaryRelExpression(isVarDeclaration: Boolean) = relExpression(isVarDeclaration)

    private fun relExpression(isVarDeclaration: Boolean) = if (sumExpression(isVarDeclaration)) {
        when {
            match(Tokenizer.SEMI_COLON) || (isVarDeclaration && match(Tokenizer.COMMA)) -> {
                currentPos--
                true
            }
            relop() -> sumExpression(isVarDeclaration)
            else -> false
        }
    } else false

    private fun sumExpression(isVarDeclaration: Boolean): Boolean {
        var isSuccess = false
        return when {
            term(isVarDeclaration) -> {
                if (match(Tokenizer.SEMI_COLON) || (isVarDeclaration && match(Tokenizer.COMMA))) {
                    isSuccess = true
                    currentPos--
                }
                while (sumop()) isSuccess = term(isVarDeclaration)
                isSuccess
            }
            else -> false
        }
    }

    private fun term(isVarDeclaration: Boolean): Boolean {
        var isSuccess = false
        return when {
            unaryExpression() -> {
                if (match(Tokenizer.SEMI_COLON) || (isVarDeclaration && match(Tokenizer.COMMA))) {
                    isSuccess = true
                    currentPos--
                }
                while (mulop()) {
                    isSuccess = unaryExpression()
                }
                isSuccess
            }
            else -> false
        }
    }

    private fun constant() = match(Tokenizer.NUMBER_LITERAL) || match(Tokenizer.STRING_LITERAL)
    private fun sumop() = match(Tokenizer.ADDITION) || match(Tokenizer.SUBSTRACTION)
    private fun mulop() = match(Tokenizer.MULTIPLIER) || match(Tokenizer.MODULUS) || match(
        Tokenizer.DIVISION
    )
    private fun unaryOp() = match(Tokenizer.SUBSTRACTION) || match(Tokenizer.MULTIPLIER) || match(
        Tokenizer.TERNARY
    )
    private fun factor() = immutable() || mutable()
    private fun relop() = match(Tokenizer.COMPARATOR)

    private fun unaryExpression(): Boolean = if (unaryOp()) {
        val result = factor()
        if (result) result
        else unaryExpression()
    } else factor()

    private fun immutable() = (match(Tokenizer.OPEN_PARENTHESES) && expression() &&
            match(Tokenizer.CLOSE_PARENTHESES)) || call() || constant()

    private fun call() = match(Tokenizer.IDENTIFIER) && match(Tokenizer.OPEN_PARENTHESES) && args() &&
            match(Tokenizer.CLOSE_PARENTHESES)

    private fun args() = when {
        match(Tokenizer.CLOSE_PARENTHESES) -> {
            currentPos--
            true
        }
        argslist() -> true
        else -> false
    }

    private fun argslist(): Boolean {
        var isSuccess = false
        return when {
            expression() -> {
                if (match(Tokenizer.CLOSE_PARENTHESES)) {
                    isSuccess = true
                    currentPos--
                }
                if (match(Tokenizer.COMMA)) isSuccess = argslist()
                isSuccess
            }
            else -> false
        }
    }

    private fun mutable(): Boolean = if (match(Tokenizer.IDENTIFIER)) {
        when {
            match(Tokenizer.DOT) -> mutable()
            match(Tokenizer.OPEN_SQUARE_BRACKET) && expression() && match(Tokenizer.CLOSE_SQUARE_BRACKET) -> true
            else -> true
        }
    } else false

    private fun expression(): Boolean {
        return (mutable() && match(Tokenizer.ASSIGNMENT) && simpleExpression()) ||
                (mutable() && match(Tokenizer.INCDEC)) && simpleExpression()
    }
}