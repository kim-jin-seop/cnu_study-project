
public class RedBlackNode {
	private RedBlackNode left;
	private RedBlackNode right;
	private RedBlackNode parent;
	private int data;
	private boolean isBlack; // 색 -> true : Black False : Red
	
	static RedBlackNode NIL = new RedBlackNode(); // RedBlack Node의 리프노드
	
	private RedBlackNode() { //NIL의 생성자
		left = null;
		right = null;
		this.isBlack = true;
	}
	
	public RedBlackNode(int data) { //RedBlackTree 생성자
		this.isBlack = false;
		this.data = data;
		parent = null;
		left = NIL;
		right = NIL;
	}
	/*GetterSetter*/
	public RedBlackNode getParent() {
		return parent;
	}
	public void setParent(RedBlackNode parent) {
		this.parent = parent;
	}
	public void setColorBlack(){
		this.isBlack = true;
	}
	public void setColorRed() {
		this.isBlack = false;
	}
	public boolean isBlack() {
		return isBlack;
	}
	public RedBlackNode getLeft() {
		return left;
	}
	public void setLeft(RedBlackNode left) {
		this.left = left;
	}
	public RedBlackNode getRight() {
		return right;
	}
	public void setRight(RedBlackNode right) {
		this.right = right;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public boolean hasLeft() {
		return !(left == NIL);
	}
	public boolean hasRight() {
		return !(right == NIL);
	}
}
