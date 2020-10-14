package main;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lexical.Lexer;
import syntactic.Parser;

public class MainClass {
	public static void main(String[] args) throws Exception {
		List<String> sentences = getSentences();
		Lexer lexer = new Lexer();
		Parser parser = new Parser();

		// sentences.forEach(s -> {
		// System.out.println("Lexical analysis for: \"" + s + "\"");
		// lexer.lex(s).forEach(t -> System.out.println(t));
		// System.out.println();
		// });

		sentences.forEach(s -> {
			System.out.println("Syntactic analysis for \"" + s + "\"");
			boolean success = parser.parse(s + "$");

			System.out.println("\nSyntactic analysis for \"" + s + "\": " + (success ? "SUCCESS" : "FAIL"));
			System.out.println();
			System.out.println(String.join("", Collections.nCopies(100, "-")));
			System.out.println();
		});
	}

	private static List<String> getSentences() {
		return Arrays.asList(
				/* CORRECT ONES */
				"http://github.com/bitcoin", "http://github.com/bitcoin/path1/path2?param1+param2", //
				"ftp://ftpadmin:adminPassword@mydomain.sk/someFolder", //
				"telnet://loginName:loginPassword@mydomain.sk", //
				"mailto::miroslav@gmail.com",

				/* INCORRECT ONES */
				"http://github.com/bitcoin/?", //
				"ftp://ftpadmin:adminPassword@mydomain.sk/some_Folder", //
				"telnet://loginName:@mydomain.sk", //
				"mailto::miroslav_l@gmail.com"//
		);
	}
}
