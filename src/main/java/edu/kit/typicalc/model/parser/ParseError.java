package edu.kit.typicalc.model.parser;

import java.util.*;

/**
 * Errors that can occur when parsing a lambda term or type assumption.
 *
 * @see LambdaLexer
 * @see LambdaParser
 */
public final class ParseError {

    private ParseError(ErrorCause unexpectedToken) {
        this.causeEnum = unexpectedToken;
    }

    /**
     * The reason for this error.
     */
    public enum ErrorCause {
        /**
         * the input contains that a token that is not allowed in that context
         */
        UNEXPECTED_TOKEN,

        /**
         * the string contained a character not allowed in that context
         */
        UNEXPECTED_CHARACTER
    }

    private final ErrorCause causeEnum;

    /**
     * @return the cause of this parse error
     */
    public ErrorCause getCauseEnum() {
        return causeEnum;
    }

    /**
     * Construct a new ParseError that signals an unexpected token.
     *
     * @param cause the incorrect token
     * @param source kind of input parsed
     * @return a new error
     */
    public static ParseError unexpectedToken(Token cause, ErrorSource source) {
        var self = new ParseError(ErrorCause.UNEXPECTED_TOKEN);
        return self.withToken(cause, source);
    }

    /**
     * Construct a new ParseError that signals an unexpected character.
     *
     * @param cause the invalid character
     * @param position position in the input string
     * @param term input string
     * @param errorType kind of input parsed
     * @return
     */
    public static ParseError unexpectedCharacter(char cause, int position, String term, ErrorSource errorType) {
        var self = new ParseError(ErrorCause.UNEXPECTED_CHARACTER);
        return self.withCharacter(cause, position, term, errorType);
    }

    /**
     * Indicates the error source.
     */
    public enum ErrorSource {
        /**
         * This error was created when parsing the input term
         */
        TERM_ERROR,

        /**
         * This error was created when parsing the type assumptions
         */
        TYPE_ASSUMPTION_ERROR
    }

    private Optional<Token> cause = Optional.empty();
    private Optional<Collection<Token.TokenType>> needed = Optional.empty();
    private Optional<ExpectedInput> expected = Optional.empty();
    private Optional<AdditionalInformation> additional = Optional.empty();
    private String term = "";
    private char wrongChar = '\0';
    private char correctChar = '\0';
    private int position = -1;
    private Optional<ErrorSource> errorType = Optional.empty();

    /**
     * Attach a token to this error.
     *
     * @param cause the token that caused the error
     * @return this object
     */
    public ParseError withToken(Token cause, ErrorSource errorType) {
        this.cause = Optional.of(cause);
        this.term = cause.getSourceText();
        this.position = cause.getPos();
        this.errorType = Optional.of(errorType);
        return this;
    }

    /**
     * Attach an expected token type to this error.
     *
     * @param needed the required token type
     * @return this object
     */
    public ParseError expectedType(Token.TokenType needed) {
        this.needed = Optional.of(new ArrayList<>(List.of(needed)));
        return this;
    }

    /**
     * Attach additional information to this error.
     *
     * @param additional the additional information
     * @return this object
     */
    public ParseError additionalInformation(AdditionalInformation additional) {
        this.additional = Optional.of(additional);
        return this;
    }

    /**
     * Set expected token types of this error.
     *
     * @param needed the possible token types
     * @return this object
     */
    public ParseError expectedTypes(Collection<Token.TokenType> needed) {
        this.needed = Optional.of(new ArrayList<>(needed));
        return this;
    }

    /**
     * Add an expected token type to this error.
     *
     * @param needed the possible token type
     * @return this object
     */
    public ParseError attachExpectedType(Token.TokenType needed) {
        if (this.needed.isEmpty()) {
            this.needed = Optional.of(new ArrayList<>());
        }
        this.needed.get().add(needed);
        return this;
    }

    /**
     * Store which kind of input is expected. Clears expected tokens.
     *
     * @param input expected input
     * @return this object
     */
    public ParseError expectedInput(ExpectedInput input) {
        this.needed = Optional.empty();
        this.expected = Optional.of(input);
        return this;
    }

    public Optional<ExpectedInput> getExpectedInput() {
        return this.expected;
    }

    /**
     * Attach an expected character to this error.
     *
     * @param expected the correct character
     * @return this object
     */
    public ParseError expectedCharacter(char expected) {
        this.correctChar = expected;
        return this;
    }

    /**
     * Attach a character and position to this error.
     *
     * @param cause    the character
     * @param position it's position
     * @param term     the term that is parsed
     * @return this object
     */
    public ParseError withCharacter(char cause, int position, String term, ErrorSource errorType) {
        this.wrongChar = cause;
        this.position = position;
        this.term = term;
        this.errorType = Optional.of(errorType);
        return this;
    }

    /**
     * @return the token associated with this error
     */
    public Optional<Token> getCause() {
        return cause;
    }

    /**
     * @return the expected/possible token(s) if this error is UNEXPECTED_TOKEN
     */
    public Optional<Collection<Token.TokenType>> getExpected() {
        return needed;
    }

    /**
     * @return the wrong character if this error is UNEXPECTED_CHARACTER ('\0' otherwise)
     */
    public char getWrongCharacter() {
        return wrongChar;
    }

    /**
     * @return the correct character if this error is UNEXPECTED_CHARACTER ('\0' if unknown)
     */
    public char getExpectedCharacter() {
        return correctChar;
    }

    /**
     * @return the character position if this error is UNEXPECTED_CHARACTER
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return the input term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @return the error type
     */
    public Optional<ErrorSource> getErrorType() {
        return errorType;
    }

    /**
     * @return the additional information
     */
    public Optional<AdditionalInformation> getAdditionalInformation() {
        return additional;
    }

    @Override
    public String toString() {
        return "ParseError{"
                + "cause=" + cause
                + ", needed=" + needed
                + ", expected=" + expected
                + ", term='" + term + '\''
                + ", wrongChar=" + wrongChar
                + ", correctChar=" + correctChar
                + ", position=" + position
                + ", errorType=" + errorType
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParseError that = (ParseError) o;
        return wrongChar == that.wrongChar && correctChar == that.correctChar && position == that.position
                && causeEnum == that.causeEnum && cause.equals(that.cause) && needed.equals(that.needed)
                && expected.equals(that.expected) && additional.equals(that.additional) && term.equals(that.term)
                && errorType.equals(that.errorType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(causeEnum, cause, needed, expected, additional,
                term, wrongChar, correctChar, position, errorType);
    }
}
