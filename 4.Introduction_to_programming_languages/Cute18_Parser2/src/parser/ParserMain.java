package parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParserMain {
	public static final void main(String... args) throws Exception {
		ClassLoader cloader = ParserMain.class.getClassLoader();
		File file = new File(cloader.getResource("parser/as07.txt").getFile());
		CuteParser cuteParser = new CuteParser(file); 
		NodePrinter.getPrinter(System.out).prettyPrint(cuteParser.parseExpr());
	}
}