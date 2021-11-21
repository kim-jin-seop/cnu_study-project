import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String args[]) throws IOException {
		File writefile = new File("Sort_result.txt"); // sort 결과를 보여주는 파일
		BufferedWriter fw = new BufferedWriter(new FileWriter(writefile));
		File file1  = new File("Data1.txt");   // 삽입파일
		File file2  = new File("Data2.txt");   // 삭제파일
		File file3  = new File("Data3.txt");   // 검색파일
		BufferedReader br = null;
		ArrayList<Integer> data = new ArrayList<Integer>(); // 데이터 받아올 ArrayList

		try {
			String ReadLine;
			//데이터 정보
			LinearHashTable linearHashTable = new LinearHashTable();
			QuadraticHashTable quadraticHashTable = new QuadraticHashTable();
			DoubleHashTable doubleHashTable = new DoubleHashTable();
			
			/*삽입 과정*/
			br = new BufferedReader(new FileReader(new File(file1.getName())));
			while ((ReadLine = br.readLine()) != null) { //데이터 넣기
				data.add(Integer.parseInt(ReadLine));
			}
			while(data.size() != 0) {
				linearHashTable.Insert(data.get(0));
				quadraticHashTable.Insert(data.get(0));
				doubleHashTable.Insert(data.remove(0));
			}
			
			/*삭제 과정*/
			br = new BufferedReader(new FileReader(new File(file2.getName())));
			while ((ReadLine = br.readLine()) != null) { //데이터 넣기
				data.add(Integer.parseInt(ReadLine));
			}
			while(data.size() != 0) {
				linearHashTable.Delete(data.get(0));
				quadraticHashTable.Delete(data.get(0));
				doubleHashTable.Delete(data.remove(0));
			}
			
			/*찾기 과정*/
			br = new BufferedReader(new FileReader(new File(file3.getName())));
			while ((ReadLine = br.readLine()) != null) { //데이터 넣기
				data.add(Integer.parseInt(ReadLine));
			}
			while(data.size() != 0) {
				fw.write(data.get(0)+" linearHashTable "+linearHashTable.Search(data.get(0)));
				fw.newLine();
				fw.write(data.get(0)+" quadraticHashTable "+quadraticHashTable.Search(data.get(0)));
				fw.newLine();
				fw.write(data.get(0)+" doubleHashTable "+doubleHashTable.Search(data.remove(0)));
				fw.newLine();	
			}
			System.out.println("linearHashTable의 충돌 횟수: "+linearHashTable.getConflictCount());
			System.out.println("quadraticHashTable의 충돌 횟수: "+quadraticHashTable.getConflictCount());
			System.out.println("doubleHashTable의 충돌 횟수: " + doubleHashTable.getConflictCount());
			fw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}