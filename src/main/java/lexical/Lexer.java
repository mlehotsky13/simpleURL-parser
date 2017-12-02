package lexical;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	public static enum TokenType {
		PROTOCOL("http://|ftp://|telnet://|mailto::"),
		SEPARATOR("[?|:|.|/|+|@]"),
		LITERAL("\\w"),
		UNDEFINED("[^?|:|.|/|+|@|A-Za-z]");

		private String pattern;

		private TokenType(String pattern) {
			this.pattern = pattern;
		}
	}

	public static class Token {
		private TokenType type;
		private String data;

		public Token(TokenType type, String data) {
			this.type = type;
			this.data = data;
		}
		
		public String getData() {
			return this.data;
		}

		@Override
		public String toString() {
			return String.format("(%s, %s)", type, data);
		}
	}

	public List<Token> lex(String input) {
		List<Token> tokens = new ArrayList<Token>();

		StringBuffer tokenPatternsBuffer = new StringBuffer();
		for (TokenType tokenType : TokenType.values())
			tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
		Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));
		
		Matcher matcher = tokenPatterns.matcher(input);
		  while (matcher.find()) {
		    if (matcher.group(TokenType.PROTOCOL.name()) != null) {
		      tokens.add(new Token(TokenType.PROTOCOL, matcher.group(TokenType.PROTOCOL.name())));
		      continue;
		    } else if (matcher.group(TokenType.SEPARATOR.name()) != null) {
		      tokens.add(new Token(TokenType.SEPARATOR, matcher.group(TokenType.SEPARATOR.name())));
		      continue;
		    } else if (matcher.group(TokenType.LITERAL.name()) != null) {
		      tokens.add(new Token(TokenType.LITERAL, matcher.group(TokenType.LITERAL.name())));
		      continue;
		    } else if (matcher.group(TokenType.UNDEFINED.name()) != null) {
		      tokens.add(new Token(TokenType.UNDEFINED, matcher.group(TokenType.UNDEFINED.name())));
		    }
		  }
		  
		return tokens;
	}
}
