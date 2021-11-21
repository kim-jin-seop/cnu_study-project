
public class Edge implements Comparable<Edge> {
	private int weight;
	private int v;
	private int w;

	public Edge(int weight, int v, int w) {
		this.weight = weight;
		this.v = v;
		this.w = w;
	}
	
	public int getStart() {
		return v;
	}
	
	public int getFinish() {
		return w;
	}
	
	public int getWeight() {
		return weight;
	}
	
	@Override
	public int compareTo(Edge e) {
		if(this.weight > e.weight)
			return 1;
		else if(this.weight < e.weight)
			return -1;
		return 0;
	}
}
