

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class UcodeGenerator {
	public static void main(String... args) throws Exception {
		MiniGoLexer lexer = new MiniGoLexer(CharStreams.fromFileName("test.go"));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MiniGoParser parser = new MiniGoParser(tokens);
		ParseTree tree = parser.program();
		
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(new UCodeGenListener(), tree);
	}
}
