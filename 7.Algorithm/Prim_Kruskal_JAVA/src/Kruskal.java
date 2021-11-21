import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Kruskal {
	private ArrayList<Edge> EdgeArray;
	private ArrayList<Edge> ResultEdge;
	private vertex[] result;
	
	public Kruskal(ArrayList<Edge> array, int vertexNum) {
		ResultEdge = new ArrayList<Edge>();
		this.EdgeArray = array;
		result = new vertex[vertexNum];
		for(int i = 0; i < vertexNum; i++) {
			result[i] = new vertex(i); 
		}
	}

	public void doPrint(BufferedWriter fw2File) throws IOException {
		fw2File.write("시작정점   끝정점   가중치");
		fw2File.newLine();
		for(int i = 0 ; i < ResultEdge.size(); i++) {
			Edge e = ResultEdge.get(i);
			fw2File.write(e.getStart() +"\t"+ e.getFinish() + "\t" +e.getWeight());
			fw2File.newLine();
		}
	}

	public void doKruskal() {
		Collections.sort(EdgeArray);
		while(!EdgeArray.isEmpty()) {
			Edge e = EdgeArray.remove(0);
			if(result[e.getStart()].group != result[e.getFinish()].group) {
				Union(result[e.getStart()],result[e.getFinish()]);
				this.ResultEdge.add(e);
			}
		}
	}
	
	public void Union(vertex v, vertex w) {
		int wG = w.group;
		for(int i = 0; i < result.length; i++) {
			if(result[i].group == wG) {
				result[i].group = v.group;
			}
		}
	}
	
	private class vertex{
		public int group;
		
		public vertex(int index) {
			this.group = index;
		}
	}
}
