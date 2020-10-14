package eu.miroslavlehotsky.parser.exception;

import java.text.MessageFormat;
import eu.miroslavlehotsky.parser.lexical.Lexer.Token;

public class ParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String MSG = "Syntax error on token {0}. {1}";

	public ParsingException(Token token, String msg) {
		super(MessageFormat.format(MSG, token, msg));
	}
}
