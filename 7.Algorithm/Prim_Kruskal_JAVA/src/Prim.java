import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Prim {
	private int[][] g;
	private vertex[] result;
	
	public Prim(int[][] g) {
		this.g = g;
		result = new vertex[g.length];
		for(int i = 0; i < g.length; i++) {
			result[i] = new vertex(i);
		}
	}

	public void doPrint(BufferedWriter fw1File) throws IOException {
		for(int i = 0 ; i < result.length; i++) {
			fw1File.write(result[i].index +"\t"+ result[i].pi.index + "\t" +result[i].key);
			fw1File.newLine();
		}
		System.out.println(" ");
	}

	public void doPrim(int start) {
		result[start].key = 0;
		ArrayList<vertex> Q = new ArrayList<vertex>();
		for(int i = 0; i < result.length; i++) {
			Q.add(result[i]);
		}
		while(!Q.isEmpty()) {
			vertex u = ExtractMin(Q);
			u.visit = true;
			for(int i = 0; i < g[u.index].length; i ++) {
				if((g[u.index][i] < result[i].key)&& !result[i].visit) {
					result[i].pi = u;
					result[i].key = g[u.index][i];
				}
			}
		}
	}

	private vertex ExtractMin(ArrayList<vertex> vertex) {
		for(int i = vertex.size()/2-1; i >= 0 ; i --) {
			heapify(vertex,i);
		}
		return vertex.remove(0);
	}

	private void heapify(ArrayList<vertex> vertex, int rootIndex) {
		int left = leftChiledIndex(rootIndex); //왼쪽 자식 
		int right = rightChiledIndex(rootIndex); //오른쪽 자식 
		if(left >= vertex.size()) // 자식이 없는경우
			return;
		if(right < vertex.size()) { //자식이 둘다 있는 경우
			if(vertex.get(left).key > vertex.get(rootIndex).key && vertex.get(right).key > vertex.get(rootIndex).key) { // 부모가 가장 작은경우
				return;
			}else if(vertex.get(left).key < vertex.get(rootIndex).key && vertex.get(right).key > vertex.get(rootIndex).key) { // 왼쪽 자식이 더 작은경우
				swap(vertex ,left,rootIndex);
				heapify(vertex,left);
			}else if(vertex.get(right).key < vertex.get(rootIndex).key && vertex.get(left).key > vertex.get(rootIndex).key) { // 오른쪽 자식이 더 작은 경우
				swap(vertex,right,rootIndex);
				heapify(vertex,right);
			}else { // 두 자식 모두 작은 경우
				if(vertex.get(right).key < vertex.get(left).key) { // 자식끼리 비교 더 작은 자식 찾기
					swap(vertex,right,rootIndex);
					heapify(vertex,right);
				}
				else { // 왼쪽 자식이 더 작은 경우
					swap(vertex,left,rootIndex);
					heapify(vertex,left);
				}
			}
		}else { // 자식이 왼쪽만 있는 경우
			if(vertex.get(left).key < vertex.get(rootIndex).key) {
				swap(vertex,left,rootIndex);
			}
		}
	}
	
	/*왼쪽 자식의 index*/
	private int leftChiledIndex(int index) {
		return 2*index+1;
	}
	
	/*오른쪽 자식의 index*/
	private int rightChiledIndex(int index) {
		return 2*index+2;
	}
	
	/*데이터를 교환하는 메소드*/
	private void swap(ArrayList<vertex> vertex ,int a, int b) {
		Collections.swap(vertex, a, b);
	}
	
	private class vertex{
		public int key;
		public vertex pi;
		public int index;
		boolean visit;
		
		public vertex(int index) {
			key = Integer.MAX_VALUE;
			pi = this;
			this.index = index;
			visit = false;
		}
	}
}
