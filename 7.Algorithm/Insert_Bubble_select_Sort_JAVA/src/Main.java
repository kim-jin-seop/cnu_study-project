import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String args[]) throws IOException {
		File file  = new File("test_10000.txt");           // FileReader로 읽어오기 파일경로
		/*데이터 개수 변경 or 경로 변경은 위 줄 코드수정을 통해 가능*/
		BufferedReader br = null;
		File writefile = new File("Sort_result.txt"); // sort 결과를 보여주는 파일
		FileWriter fw = new FileWriter(writefile);
		int Size = 0;
		Insertion_Sort insertion_Sort = new Insertion_Sort();
		Bubble_Sort bubble_Sort = new Bubble_Sort(); 
		Select_Sort select_Sort = new Select_Sort();
		Node head = new Node();
		try {
			br = new BufferedReader(new FileReader(file)); 
			String ReadLine;  
			while ((ReadLine = br.readLine()) != null) { // 데이터 읽어오는 반복문
				Node inputData = new Node(head,Integer.parseInt(ReadLine)); //inputData -> 넣는 데이터, 항상 head의 앞에 넣는다.
				inputData.SetnextNode(head.nextNode());  // inputData의 다음을 기존에 있던 head의 next로 변경 
				if(head.nextNode() != null)
					head.nextNode().SetpreNode(inputData); // head의 next의 pre를 연결
				head.SetnextNode(inputData); // head의 next 변경
				Size ++; // 총 데이터 수
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		insertion_Sort.sort(head, Size);
		//bubble_Sort.sort(head, Size);
		//select_Sort.sort(head, Size);
		/*
		 * 위 세가지 Sort중 한가지씩 골라가며 확인 가능
		 * */
		while((head = head.nextNode()) != null) { // 결과 출력
			System.out.println(head.data());
			fw.write(String.valueOf(head.data()) + " ");
			fw.flush();
		}
	}
}