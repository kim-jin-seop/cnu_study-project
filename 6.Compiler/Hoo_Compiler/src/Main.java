import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	public static void main(String args[]) throws IOException {
		File file  = new File("test.HOO");           // FileReader로 읽어오기 파일경로
		File writefile = new File("test.c");         // FileWriter로 컴파일 결과 작성 파일경로
		FileWriter fw = new FileWriter(writefile);
		fw.write("#include <stdio.h>\n\n");
		fw.flush();
		fw.write("int main(){\n");
		fw.flush();
		BufferedReader br = null;
		Hoo_Compiler Compiler = new Hoo_Compiler();
		try {
			br = new BufferedReader(new FileReader(file));
			String line;  
			System.out.println("Compile...");
			while ((line = br.readLine()) != null) { 
				System.out.println(line);
				fw.write(Compiler.Compile(line));  //한줄씩 컴파일 실행
				fw.flush();
			}
			fw.write("return 0;\n");
			fw.flush();
			fw.write("}");
			fw.close();
			System.out.println("Compile Success!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
/*
 * Main 컴파일러 실행
 * 위 프로그램 실행조건 - 에러없는 데이터를 입력한다.
 * 입력 : test.Hoo
 * 출력 : test.c
 * file path : workspace -> Hoo_Compiler 
 */