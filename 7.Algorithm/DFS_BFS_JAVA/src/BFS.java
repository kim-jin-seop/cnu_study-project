import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	private int[][] g;
	Queue<Vertex> q = new LinkedList();
	Vertex[] ver;

	public BFS(int[][] g) {
		this.g = g;
		ver = new Vertex[g.length];
		for(int i = 0; i < ver.length; i++) {
			ver[i] = new Vertex(i);
		}
	}
	
	//BFS 수행
	public void StartBFS(int startIndex) {
		Vertex u = ver[startIndex%ver.length]; // vertex
		u.startVertex(); // 시작점 설정
		q.add(u); //add
		while(!q.isEmpty()) {
			u = q.remove();
			for(int i = 0; i < g[u.vertexIndex].length; i++) {
				if(g[u.vertexIndex][i] == 1) {
					if(ver[i].color == Vertex.white) {
						ver[i].color = Vertex.gray;
						ver[i].distance = u.distance + 1;
						ver[i].pi =u;
						q.add(ver[i]);
					}
				}
			}
			u.color = Vertex.black;
		}
		
		for(int i = 0 ; i < ver.length; i ++) {
			System.out.println(i+"\t"+ver[i].color +"\t" +  ver[i].distance );
		}
		
	}
}
