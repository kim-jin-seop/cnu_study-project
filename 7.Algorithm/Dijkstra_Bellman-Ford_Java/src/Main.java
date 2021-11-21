import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Main {
	public static void main(String args[]) throws IOException {
		File file  = new File("graph_sample_dijkstra.txt");   // input
		File file2 = new File("graph_sample_bellman.txt");
		File output1 = new File("Output_Dijkstra.txt");   // output
		File output2 = new File("Output_Bellman.txt");   // output
		BufferedReader br = new BufferedReader(new FileReader(new File(file.getName())));
		BufferedReader br2 = new BufferedReader(new FileReader(new File(file2.getName())));
		BufferedWriter fw1 = new BufferedWriter(new FileWriter(output1));
		BufferedWriter fw2 = new BufferedWriter(new FileWriter(output2));
		
		int[][] graphdata = null; // graph 인접행렬
		int[][] graph2 = null;

		try { // make String Data -> X, Y
			int ver2 = Integer.parseInt(br.readLine());
			int ver = Integer.parseInt(br2.readLine()); // ver은 정점의 개수
			graphdata = new int[ver][ver]; //인접행렬 생성
			graph2 = new int[ver2][ver2];

			for(int i = 0; i < ver2; i ++) { // for문 인접행렬 초기화 시켜주기.
				for(int j = 0; j < ver2; j ++) {
					graph2[i][j] = Integer.MAX_VALUE;
				}
			}
			for(int i = 0; i < ver; i ++) { // for문 인접행렬 초기화 시켜주기.
				for(int j = 0; j < ver; j ++) {
					graphdata[i][j] = Integer.MAX_VALUE;
				}
			}
			String line;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line) ;
				int i = Integer.parseInt(st.nextToken());
				int j = Integer.parseInt(st.nextToken());
				int result = Integer.parseInt(st.nextToken());
				graphdata[i][j] = result;
			}
			while((line = br2.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line) ;
				int i = Integer.parseInt(st.nextToken());
				int j = Integer.parseInt(st.nextToken());
				int result = Integer.parseInt(st.nextToken());
				graph2[i][j] = result;
			}
			
			Dijkstra dijkstra = new Dijkstra(graphdata);
			dijkstra.DoDijkstra(0);
			dijkstra.doPrint(fw1);
			
			Bellman_Ford belman_ford = new Bellman_Ford(graph2);
			belman_ford.DoBellman_Ford(0);
			belman_ford.doPrint(fw2);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fw1.close();
		fw2.close();
		br.close();
		br2.close();
	}
}
