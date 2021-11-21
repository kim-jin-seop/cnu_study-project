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
		File file  = new File("graph_sample_prim_kruskal.txt");   // input
		File output1 = new File("Output_Prim.txt");   // output
		File output2 = new File("Output_Kruskal.txt");   // output
		int[][] graphdata = null; // graph 인접행렬
		BufferedReader br = new BufferedReader(new FileReader(new File(file.getName())));
		BufferedWriter fw1 = new BufferedWriter(new FileWriter(output1));
		BufferedWriter fw2 = new BufferedWriter(new FileWriter(output2));
		ArrayList<Edge> Edge = new ArrayList<Edge>();

		try { // make String Data -> X, Y
			int ver = Integer.parseInt(br.readLine()); // ver은 정점의 개수
			graphdata = new int[ver][ver]; //인접행렬 생성

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
				graphdata[j][i] = result;
				Edge.add(new Edge(result,i,j));
			}
			Prim prim = new Prim(graphdata);
			prim.doPrim(0);
			prim.doPrint(fw1);
			
			Kruskal kruskal = new Kruskal(Edge, graphdata.length);
			kruskal.doKruskal();
			kruskal.doPrint(fw2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fw1.close();
		fw2.close();
		br.close();
	}
}
