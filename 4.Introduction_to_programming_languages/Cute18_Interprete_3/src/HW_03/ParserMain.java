package HW_03;

import java.io.File;

public class ParserMain {
	public static final void main(String... args) throws Exception {
		
		ClassLoader cloader = ParserMain.class.getClassLoader();
		File file = new File(cloader.getResource("HW_03/as07.txt").getFile()); 
		
		CuteParser cuteParser = new CuteParser(file);
		NodePrinter.getPrinter(System.out).prettyPrint(cuteParser.parseExpr());
	}
}
