import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Bellman_Ford {
	private int[][] g;
	private vertex[] result;
	ArrayList<vertex> vertexList;
	boolean error = false;

	public Bellman_Ford(int[][] g) {
		this.g = g;
		vertexList= new ArrayList<vertex>();
		result = new vertex[g.length];
		for(int i = 0; i < g.length; i++) {
			result[i] = new vertex(i);
		}
	}

	public void doPrint(BufferedWriter fw1File) throws IOException {
		if(!error)
			for(int i = 0; i < result.length; i++) {
				vertex x = result[i];
				while(x.pi.index != x.index) {
					fw1File.write(x.index+" <- ");
					x = x.pi;
				}
				fw1File.write(x.index+" "+result[i].key);
				fw1File.newLine();
			}
		else
			fw1File.write("ERROR : 음의 사이클이 존재합니다.");
	}

	public void DoBellman_Ford(int start) {
		//start
		result[start].key = 0;
		vertexList.add(result[start]);
		for(int i = 1; i < result.length; i++) { //간선을 i개 사용해서   수행
			int size = vertexList.size();
			for(int j = 0; j < size; j ++) {
				vertex u = vertexList.remove(0);
				for(int k = 0; k < result.length; k ++) {
					if(g[u.index][k] != Integer.MAX_VALUE)
						RELAX(u,result[k],g[u.index][k]);
				}
			}
		}
		
		for(int i = 0; i < g.length; i ++) {
			for(int j = 0; j < g[i].length; j++) {
				if(result[i].key+g[i][j] < result[j].key && g[i][j] != Integer.MAX_VALUE)
					error = true;
			}
		}
	}

	private void RELAX(vertex u, vertex v, int weight ) {
		if(v.key > u.key+weight) {
			vertexList.add(v); //새롭게 가게됨. 다음에 사용
			v.key = u.key+weight;
			v.pi = u;
		}
	}

	private class vertex{
		public int key;
		public vertex pi;
		public int index;

		public vertex(int index) {
			key = Integer.MAX_VALUE;
			pi = this;
			this.index = index;
		}
	}
}
