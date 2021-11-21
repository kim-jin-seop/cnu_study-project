

public class DFS {
	private int[][] g;
	private int time;
	Vertex[] ver;

	public DFS(int[][] g) {
		time = 0;
		this.g = g;
		ver = new Vertex[g.length];
		for(int i = 0; i < ver.length; i++) {
			ver[i] = new Vertex(i);
		}
	}
	
	public void DFS() {
		for(int i = 0; i < ver.length; i++) {
			if(ver[i].color == Vertex.white) {
				DFS_Visit(ver[i]);
			}
		}
	}
	
	private void DFS_Visit(Vertex v){
		time = time +1;
		v.d = time;
		v.color = Vertex.gray;
		for(int i = 0; i < ver.length; i ++) {
			if(g[v.vertexIndex][i] == 1) {
				if(ver[i].color == Vertex.white) {
					ver[i].pi =v;
					DFS_Visit(ver[i]);
				}
			}
		}
		v.color = Vertex.black;
		time = time +1;
		v.f = time;
	}
	
	public void print() {
		for(int i = 0 ; i < ver.length; i ++) {
			System.out.println(ver[i].d+"\t"+ver[i].color +"\t" +  ver[i].f);
		}
	}
}
