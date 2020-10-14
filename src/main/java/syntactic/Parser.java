package syntactic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;
import exception.ParsingException;
import lexical.Lexer;
import lexical.Lexer.Token;

public class Parser {
	Map<Integer, List<String>> rules;
	Map<String, Map<String, Integer>> table;

	public Parser() {
		this.rules = new HashMap<Integer, List<String>>(setUpRules());
		this.table = new HashMap<String, Map<String, Integer>>(setUpTable());
	}

	public boolean parse(String input) {
		List<Token> tokens = new Lexer().lex(input);
		Stack<String> parseStack = new Stack<String>();
		parseStack.push("$");
		parseStack.push("_A");
		int i = 0;

		Token token = tokens.get(i);
		while (!parseStack.isEmpty()) {
			try {
				System.out.println(String.format("%-50s %-10s", parseStack, token.getData()));
				String topOfStack = parseStack.pop();
				if ("EPSILON".equals(topOfStack)) {
					continue;
				}
				if (!isTerminal(topOfStack)) {
					String mapToken = getMapToken(token);
					if (table.get(topOfStack).get(mapToken) == null) {
						throw new ParsingException(token, "Undefined state for [" + topOfStack + ", " + mapToken + "].");
					} else {
						List<String> listOfRhs = rules.get(table.get(topOfStack).get(mapToken));
						for (int j = listOfRhs.size() - 1; j >= 0; j--) {
							parseStack.push(listOfRhs.get(j));
						}
					}
				} else {
					if (topOfStack.equals(token.getData()) || Pattern.matches(topOfStack, token.getData())) {
						if (i == tokens.size() - 1)
							continue;
						token = tokens.get(++i);
					} else {
						throw new ParsingException(token, "No matching terminal found.");
					}
				}
			} catch (ParsingException e) {
				System.out.println(e);
				return false;
			}
		}

		return true;
	}

	// @formatter:off
	private Map<Integer, List<String>> setUpRules() {
		Map<Integer, List<String>> rules = new HashMap<Integer, List<String>>();
		
		rules.put(1, Arrays.asList("_B"));
		rules.put(2, Arrays.asList("_C"));
		rules.put(3, Arrays.asList("_D"));
		rules.put(4, Arrays.asList("_E"));
		rules.put(5, Arrays.asList("http://", "_G", "_B1"));
		rules.put(6, Arrays.asList("?", "_K"));
		rules.put(7, Arrays.asList("/", "_J", "_B2"));
		rules.put(8, Arrays.asList("EPSILON"));
		rules.put(9, Arrays.asList("?", "_K"));
		rules.put(10, Arrays.asList("EPSILON"));
		rules.put(11, Arrays.asList("ftp://", "_F", "/", "_J"));
		rules.put(12, Arrays.asList("telnet://", "_F"));
		rules.put(13, Arrays.asList("mailto::", "_O", "@", "_H"));
		rules.put(14, Arrays.asList("_G"));
		rules.put(15, Arrays.asList("_L", "_F1"));
		rules.put(16, Arrays.asList("@", "_G"));
		rules.put(17, Arrays.asList(":", "_M", "@", "_G"));
		rules.put(18, Arrays.asList("_H", "_G1"));
		rules.put(19, Arrays.asList(":", "_I"));
		rules.put(20, Arrays.asList("EPSILON"));
		rules.put(21, Arrays.asList("_O", "_H1"));
		rules.put(22, Arrays.asList(".", "_H"));
		rules.put(23, Arrays.asList("EPSILON"));
		rules.put(24, Arrays.asList("_Q"));
		rules.put(25, Arrays.asList("_N", "_J1"));
		rules.put(26, Arrays.asList("/", "_J"));
		rules.put(27, Arrays.asList("EPSILON"));
		rules.put(28, Arrays.asList("_O", "_K1"));
		rules.put(29, Arrays.asList("+", "_K"));
		rules.put(30, Arrays.asList("EPSILON"));
		rules.put(31, Arrays.asList("_O"));
		rules.put(32, Arrays.asList("_O"));
		rules.put(33, Arrays.asList("_P", "_N"));
		rules.put(34, Arrays.asList("EPSILON"));
		rules.put(35, Arrays.asList("_P", "_O1"));
		rules.put(36, Arrays.asList("_O"));
		rules.put(37, Arrays.asList("EPSILON"));
		rules.put(38, Arrays.asList("_R"));
		rules.put(39, Arrays.asList("_S"));
		rules.put(40, Arrays.asList("_S", "_Q1"));
		rules.put(41, Arrays.asList("_Q"));
		rules.put(42, Arrays.asList("EPSILON"));
		rules.put(43, Arrays.asList("[A-Za-z]"));
		rules.put(44, Arrays.asList("[0-9]"));
		
		return rules;
	}

	private Map<String, Map<String, Integer>> setUpTable() {
		Map<String, Map<String, Integer>> table = new HashMap<String, Map<String, Integer>>();
		
		table.put("_G1", new HashMap<String, Integer>(){{ put("?", 20); put("/", 20); put(":", 19); put("$", 20); }});
		table.put("_A", new HashMap<String, Integer>(){{ put("http://", 1); put("ftp://", 2); put("telnet://", 3); put("mailto::", 4); }});
		table.put("_B", new HashMap<String, Integer>(){{ put("http://", 5); }});
		table.put("_C", new HashMap<String, Integer>(){{ put("ftp://", 11); }});
		table.put("_D", new HashMap<String, Integer>(){{ put("telnet://", 12); }});
		table.put("_E", new HashMap<String, Integer>(){{ put("mailto::", 13); }});
		table.put("_F", new HashMap<String, Integer>(){{ put("alpha", 15); put("digit", 15);}});
		table.put("_G", new HashMap<String, Integer>(){{ put("alpha", 18); put("digit", 18);}});
		table.put("_H", new HashMap<String, Integer>(){{ put("alpha", 21); put("digit", 21);}});
		table.put("_I", new HashMap<String, Integer>(){{ put("digit", 24); }});
		table.put("_J", new HashMap<String, Integer>(){{ put("/", 25); put("alpha", 25); put("digit", 25);}});
		table.put("_K", new HashMap<String, Integer>(){{ put("alpha", 28); put("digit", 28);}});
		table.put("_L", new HashMap<String, Integer>(){{ put("alpha", 31); put("digit", 31);}});
		table.put("_M", new HashMap<String, Integer>(){{ put("alpha", 32); put("digit", 32);}});
		table.put("_N", new HashMap<String, Integer>(){{ put("?", 34); put("/", 34); put("alpha", 33); put("digit", 33); put("$", 34); }});
		table.put("_O", new HashMap<String, Integer>(){{ put("alpha", 35); put("digit", 35);}});
		table.put("_P", new HashMap<String, Integer>(){{ put("alpha", 38); put("digit", 39);}});
		table.put("_Q", new HashMap<String, Integer>(){{ put("digit", 40); }});
		table.put("_R", new HashMap<String, Integer>(){{ put("alpha", 43); }});
		table.put("_S", new HashMap<String, Integer>(){{ put("digit", 44); }});
		table.put("_J1", new HashMap<String, Integer>(){{ put("?", 27); put("/", 26); put("$", 27); }});
		table.put("_H1", new HashMap<String, Integer>(){{ put("?", 23); put("/", 23); put(":", 23); put(".", 22); put("$", 23); }});
		table.put("_F1", new HashMap<String, Integer>(){{ put("@", 16); put(":", 17); }});
		table.put("_B2", new HashMap<String, Integer>(){{ put("?", 9); put("$", 10); }});
		table.put("_B1", new HashMap<String, Integer>(){{ put("?", 6); put("/", 7); put("$", 8); }});
		table.put("_Q1", new HashMap<String, Integer>(){{ put("?", 42); put("/", 42); put("digit", 41); put("$", 42); }});
		table.put("_O1", new HashMap<String, Integer>(){{ put("?", 37); put("/", 37); put("@", 37); put(":", 37); put(".", 37); put("+", 37); put("alpha", 36); put("digit", 36); put("$", 37); }});
		table.put("_K1", new HashMap<String, Integer>(){{ put("+", 29); put("$", 30); }});
		
		return table;
	}
	
	private boolean isTerminal(String token) {
		return token.charAt(0) == '_' ? false : true;
	}
	
	private String getMapToken(Token token) {
		String mapToken = token.getData();

		if (token.getData().length() == 1 && Character.isAlphabetic(token.getData().charAt(0))) {
			mapToken = "alpha";
		} else if (token.getData().length() == 1 && Character.isDigit(token.getData().charAt(0))) {
			mapToken = "digit";
		}
		
		return mapToken;
	}
	// @formatter:on
}
