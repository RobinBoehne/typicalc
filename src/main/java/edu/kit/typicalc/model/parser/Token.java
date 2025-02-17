package edu.kit.typicalc.model.parser;

import java.util.Objects;

/**
 * A token of a lambda term or type assumption.
 */
public class Token {
    /**
     * Used to distinguish what kind of token we have.
     * Most of them stand for exactly one character.
     * VARIABLE and NUMBER have a regular expression associated with them.
     * EOF is a special token to indicate that the end of file is reached.
     */
    public enum TokenType {
        UNIVERSAL_QUANTIFIER, // ∀ or exclamation mark
        LAMBDA, // λ or a backslash
        VARIABLE, // [a-z][a-zA-Z0-9]* except "let" or "in" or constants
        LET, // let
        IN, // in
        TRUE, // true
        FALSE, // false
        NUMBER, // [0-9]+
        LEFT_PARENTHESIS, // (
        RIGHT_PARENTHESIS, // )
        DOT, // .
        COMMA, // ,
        COLON, // :
        EQUALS, // =
        ARROW, // ->
        EOF // pseudo token if end of input is reached
    }

    /**
     * token type of this Token
     */
    private final TokenType type;
    /**
     * the text of this token in the source code
     */
    private final String text;
    private final String sourceText;
    private final int pos;

    /**
     * Constructs a token.
     *
     * @param type the token type
     * @param text text of this token in the source code
     * @param sourceText context of this token
     * @param pos  position this token begins in sourceText
     */
    public Token(TokenType type, String text, String sourceText, int pos) {
        this.type = type;
        this.text = text;
        this.sourceText = sourceText;
        this.pos = pos;
    }

    /**
     * Returns the token type
     *
     * @return token type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Returns the text of this token in the source code
     *
     * @return text of this token in the source code
     */
    public String getText() {
        return text;
    }

    /**
     * @return the surrounding text of this token
     */
    public String getSourceText() {
        return sourceText;
    }

    /**
     * Returns the position this token is in
     *
     * @return position this token is in
     */
    public int getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return type + "(\"" + text + "\")[" + pos + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Token token = (Token) o;
        return pos == token.pos && type == token.type
                && Objects.equals(text, token.text) && sourceText.equals(token.sourceText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, text, sourceText, pos);
    }
}
