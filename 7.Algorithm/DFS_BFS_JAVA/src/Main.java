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
		File file  = new File("graph.txt");   // input
		File output = new File("Output.txt");   // output
		int[][] graphdata = null; // graph 인접행렬
		BufferedReader br = new BufferedReader(new FileReader(new File(file.getName())));
		BufferedWriter fw = new BufferedWriter(new FileWriter(output));
		try { // make String Data -> X, Y
			int ver = Integer.parseInt(br.readLine());
			graphdata = new int[ver][ver];
			for(int i = 0; i < ver; i ++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j = 0; j < ver; j ++) {
					graphdata[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BFS bfs = new BFS(graphdata);
		bfs.StartBFS(0);
		fw.close();
		br.close();
		DFS dfs = new DFS(graphdata);
		dfs.DFS();
		dfs.print();
	}
}
