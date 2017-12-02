package exception;

import lexical.Lexer.Token;

public class ParsingException extends RuntimeException {
	public ParsingException(Token token, String msg) {
		super("Syntax error on token \"" + token.getData() + "\". " + msg);
	}
}
