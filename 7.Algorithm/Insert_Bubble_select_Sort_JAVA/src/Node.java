public class Node {
	private Node preNode;
	private Node nextNode;
	private int data;
	
	public Node() {}
	
	public Node(Node pre, int data) {
		this.preNode = pre;
		this.data = data;
	}
	
	public Node preNode() {
		return this.preNode;
	}
	
	public Node nextNode() {
		return this.nextNode;
	}
	
	public int data() {
		return this.data;
	}
	
	public void SetpreNode(Node pre) {
		this.preNode = pre;
	}
	
	public void SetnextNode(Node next) {
		this.nextNode = next;
	}
	
	public void setData(int data) {
		this.data = data;
	}
}
/*Node 클래스
 * DoubleLinkedList를 위한 Node
 */