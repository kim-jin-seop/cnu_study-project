import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	public static void main(String args[]) throws IOException {
		File file  = new File("Data_updated.txt");   // 입력파일
		File output1 = new File("Output1.txt");      // 출력파일1
		File output2 = new File("Output2.txt");      // 출력파일2
		BufferedReader br = new BufferedReader(new FileReader(new File(file.getName())));
		BufferedWriter fw1 = new BufferedWriter(new FileWriter(output1));
		BufferedWriter fw2 = new BufferedWriter(new FileWriter(output2));
		Node[] data = new Node[6]; // index에 따라서 Set생성
		Union_Find union_Find = new Union_Find(); // Union과 Find 수행하는 객체
		Node firstinputNode = null; // 처음 삽입하는 노드 저장(추후 Output2를 위해서)
		try {
			String ReadLine;
			Node inputNode;
			Node preNode = null;
			while((ReadLine=br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(ReadLine);
				inputNode = new Node(st.nextToken()); 				// 데이터를 쪼개서 받는다.
				int index = Integer.parseInt(st.nextToken()); 		// index
				if(preNode != null)	preNode.inputNext = inputNode;  // 다음에 삽입되는 데이터 저장
				if(firstinputNode == null)	firstinputNode = inputNode; //firstinputNode는 처음 삽입하는 노드 저장
				preNode = inputNode;
 				union_Find.Make_Set(inputNode); //집합을 만든다.
				if(data[index] == null) { //해당 번호에 집합이 없다면, root로 설정한다.
					data[index] = inputNode;
				}else { //그 외에 경우에는 그 노드를 가져와 union시킨다.
					union_Find.Union(data[index], inputNode);
				}
				fw1.write(inputNode.getId()+"\t"+inputNode.getParent().getId()); //최종적으로 나온 결과를 출력한다. output1
				fw1.newLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Output2를 위해 모든 집합을 Union시킨다.
		for(int i = 1; i < 5; i ++) {
			union_Find.Union(data[i], data[i+1]);
			data[i+1] = union_Find.Find_Set(data[i+1]);
		}
		//firstinputNode부터 순서대로 Output2를 출력한다.
		while(firstinputNode != null) {
			fw2.write(firstinputNode.getId()+"\t"+firstinputNode.getParent().getId());
			fw2.newLine();
			firstinputNode = firstinputNode.inputNext;
		}
		fw1.close();
		fw2.close();
		br.close();
	}
}
