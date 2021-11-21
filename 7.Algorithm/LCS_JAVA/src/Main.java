import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String args[]) throws IOException {
		File file  = new File("LCS_Data.txt");   // input
		File output1 = new File("Output.txt");   // output
		char[] X = null; //Data X
		char[] Y = null; //Data Y
		int m = 0,n = 0; //For Length
		BufferedReader br = new BufferedReader(new FileReader(new File(file.getName())));
		BufferedWriter fw = new BufferedWriter(new FileWriter(output1));
		try { // make String Data -> X, Y
			m = Integer.parseInt(br.readLine()); // X Length
			X = new char[m+1]; 
			String readLine = br.readLine();
			for(int i = 0; i < X.length-1; i ++) { // input X data
				X[i+1] = readLine.charAt(i);
			}
			n = Integer.parseInt(br.readLine()); // Y Length
			Y = new char[n+1];
			readLine = br.readLine();
			for(int i = 0; i < Y.length-1; i ++) { // input Y data
				Y[i+1] = readLine.charAt(i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		LCS lcs = new LCS(); //make LCS object
		lcs.LCS_Length(X, Y); //Find LCS
		lcs.Print_LCS(fw,X,m,n); //Print LCS
		fw.close();
		br.close();
	}
}
