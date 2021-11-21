import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String args[]) throws IOException {
		File file  = new File("test_10000.txt");       
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
			Quick_Sort quick = new Quick_Sort(true);
			MergeWithInsertion_Sort merge = new MergeWithInsertion_Sort();
			long start = System.currentTimeMillis();
			merge.Sort(data, size, 10000);
			long finish = System.currentTimeMillis();
			for(int i = 0; i < size; i++) {
				System.out.println(data[i]);
				fw.write(String.valueOf(data[i]) + " ");
				fw.flush();
			}
			System.out.println((finish-start) + "Millis");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}