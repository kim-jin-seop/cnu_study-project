
public class Node {
	private Node pNode;
	private String ID;	
	public Node inputNext;
	
	public Node(String getId) {
		this.ID = getId;
	}
	
	public void SetParent(Node pNode) {
		this.pNode = pNode;
	}
	
	public Node getParent() {
		return this.pNode;
	}
	
	public String getId() {
		return this.ID;
	}
}
