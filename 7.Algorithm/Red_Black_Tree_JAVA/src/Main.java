import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static void main(String args[]) throws IOException {
		File file  = new File("Data1.txt");   // 받아온 입력파일
		BufferedReader br = null;
		ArrayList<Integer> data = new ArrayList<Integer>(); // 데이터 받아올 ArrayList

		try {
			br = new BufferedReader(new FileReader(file));
			String ReadLine;  
			br = new BufferedReader(new FileReader(new File(file.getName())));
			while ((ReadLine = br.readLine()) != null) { //데이터 넣기
				data.add(Integer.parseInt(ReadLine));
			}
			/*BST 생성*/
			BinarySearchTree BSTtree = new BinarySearchTree();
			BSTtree.Insert(data);
			/*Code 사용 후 다시 데이터 생성*/
			br = new BufferedReader(new FileReader(new File(file.getName())));
			while ((ReadLine = br.readLine()) != null) { //데이터 넣기
				data.add(Integer.parseInt(ReadLine));
			}
			/*RedBlackTree 생성*/
			RedBlackTree RedBlacktree = new RedBlackTree(data.remove(0)); //RedBlack트리 생성 
			RedBlacktree.Insert(data); // RedBlacktree에 ArrayList 넣어서 삽입
			br = new BufferedReader(new FileReader(new File(file.getName())));
			while ((ReadLine = br.readLine()) != null) { //데이터 넣기
				data.add(Integer.parseInt(ReadLine));
			}
			long startTime = System.nanoTime();
			for(int i = 0; i < data.size(); i ++) {
				RedBlacktree.RecursiveSearch(BSTtree.root,data.get(i));
			}
			long endTime = System.nanoTime();
			System.out.println("RedBlackTree 탐색 걸린시간 : "+ (endTime - startTime)+"ns");
			startTime = System.nanoTime();
			for(int i = 0; i < data.size(); i ++) {
				RedBlacktree.RecursiveSearch(BSTtree.root,data.get(i));
			}
			endTime = System.nanoTime();
			System.out.println("BST 탐색 걸린시간 : "+ (endTime - startTime)+"ns");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}