
public class Vertex {
	public static final int white = 0;
	public static final int gray = 1;
	public static final int black = 2;
	public static final Vertex NIL = new Vertex(1) {};
	
	//NIL 생성자
	private Vertex() {
		color = Integer.MAX_VALUE;
		distance = Integer.MAX_VALUE;
		pi = null;
	}
	
	int vertexIndex; //정점의 index 번호
	int color; // 정점의 color 
	int distance; // distance
 	Vertex pi; // 부른 정점
	int d,f; //For DFS -> d - 도착한 시간 f - 빠져나온 시간
	
	public Vertex(int num) { // 정점 생성자
		color = white;
		distance = Integer.MAX_VALUE;
		pi = NIL;
		vertexIndex = num; 
	}
	
	public void startVertex() { //시작 정점 
		color = gray;
		distance = 0;
	}
}
