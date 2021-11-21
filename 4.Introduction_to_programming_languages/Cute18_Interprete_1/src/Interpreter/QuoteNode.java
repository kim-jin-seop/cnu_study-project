package Interpreter;

public class QuoteNode implements Node { // 새로 추가된 QuoteNode Class
	/*QuoteNode는 새로 추가된 노드형태이다.
	 * QuoteNode는 생성자로 Node를 가지고 있는데, 이는 CuteParser 클래스에서 사용하는것을 보면, parseExpr()메소드를 실행한 결과값으로, '뒤에 나오는 노드임을 알 수 있다.
	 * nodeInside는 뒤의 노드를 가리킨다. */
	Node quoted;
	public QuoteNode(Node quoted) {
		this.quoted = quoted;
	}
	@Override
	public String toString(){
		return quoted.toString();
	}
	public Node nodeInside() {
		// TODO Auto-generated method stub
		return quoted;
	}
}
