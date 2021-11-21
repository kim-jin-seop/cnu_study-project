import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loader {

	public static void main(String[] args) {

		Grammar g = new Grammar();

		try{
			BufferedReader fileReader = new BufferedReader(new FileReader(new File("grammar.txt"))); // grammer 가져오기
			String line = "";
			while((line = fileReader.readLine()) != null){
				String[] grammar = line.split("->");
				String NonTerminal = grammar[0].replaceAll(" ", ""); // left 항상 NonTerminal
				String[] RuleSet = grammar[1].replaceAll(" ", "").split("\\|");  //Rule Right |로 나누어서 모든 문법 가져옴
				for(int i = 0; i < RuleSet.length; i++){
					g.AddRule(NonTerminal, RuleSet[i]); //문법에 추가하기.
				}
			}
			fileReader.close();
		} catch (IOException e){
			e.printStackTrace();
		}

		g.viewGrammar(); // Grammer 가져오기
		System.out.println("------------------");
		g.findFirst();
		System.out.println("------------------");
		g.findFollow();
		System.out.println("------------------");
		RecursiveDescentParser parser = new RecursiveDescentParser(g, "( id * id )");
	}
}
