import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {
	public static void main(String args[]) throws IOException {
		File file  = new File("Data2.txt");       
		BufferedReader br = null;
		File writefile = new File("Sort_result.txt"); 
		FileWriter fw = new FileWriter(writefile);
		int size = 0;
		int[] data;
		
		try {
			br = new BufferedReader(new FileReader(file));
			String ReadLine;  
			while ((ReadLine = br.readLine()) != null) { //크기 가져오기
				size ++;
			}
			data = new int[size];  //데이터 배열
			br = new BufferedReader(new FileReader(new File(file.getName())));
			int index = 0;
			while ((ReadLine = br.readLine()) != null) { //데이터 넣기
				data[index] = Integer.parseInt(ReadLine);
				index++;
			}
			BinarySearchTree tree = new BinarySearchTree(); //트리 생성
			/*일반적으로 데이터 순차적인 삽입 후 데이터 모두 찾는데 걸린시간 확인 시 아래 주석 삭제*/
			 //tree.Insert(data);
			 
			
			/*median Insert를 수행한 후  데이터를 모두 찾는데 걸린 시간 확인 시 아래 주석 삭제*/
			 tree.MedianInsert(data);
			 
			 
			
			 
			 
			 
			long startTime = System.nanoTime();
			for(int i = 0; i < data.length; i++) {
				tree.RecursiveSearch(tree.root,data[i]);
			}
			long endTime = System.nanoTime();
			System.out.println("걸린시간 : "+ (endTime - startTime)+"ns");
			tree.printInorder(tree.root,fw);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}