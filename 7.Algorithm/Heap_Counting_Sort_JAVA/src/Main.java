import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String args[]) throws IOException {
		File file  = new File("test_1000.txt");       
		BufferedReader br = null;
		File writefile = new File("Sort_result.txt"); 
		FileWriter fw = new FileWriter(writefile);
		int size = 0;
		int[] data;
		
		try {
			br = new BufferedReader(new FileReader(file));
			String ReadLine;  
			while ((ReadLine = br.readLine()) != null) {
				size ++;
			}
			data = new int[size];
			br = new BufferedReader(new FileReader(new File(file.getName())));
			int index = 0;
			while ((ReadLine = br.readLine()) != null) {
				data[index] = Integer.parseInt(ReadLine);
				index++;
			}
			/**
			 * MinHeap_Sort 사용시 아래 주석 해제 오름차순 */
//			MinHeap_Sort minHeap = new MinHeap_Sort(size);
//			data = minHeap.Sort(data);
			/**
			 * MaxHeap_Sort 사용시 아래 주석 해체 내림차순*/
//			MaxHeap_Sort maxHeap = new MaxHeap_Sort(size);
//			data = maxHeap.Sort(data);
			/**
			 * Counting_Sort 사용시 아래 주석 해체 오름차순*/
			Counting_Sort counting = new Counting_Sort(data);
			data = counting.sort();
			for(int i = 0; i < size; i++) {
				System.out.println(data[i]);
				fw.write(String.valueOf(data[i]) + " ");
				fw.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}