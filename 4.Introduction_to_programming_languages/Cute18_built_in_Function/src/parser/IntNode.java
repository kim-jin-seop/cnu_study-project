package parser;

// IntNode의 정보
public class IntNode implements ValueNode { // 새로 수정된 IntNode
	/*IntNode는 value값으로 Integer를 갖는다.
	 * 생성자로, text를 받아서 Integer로 지정한다.*/
	public Integer value; // value 값
	@Override
	public String toString(){
		return "INT: " + value;
	}
	public IntNode(String text) {
		this.value = new Integer(text);
	}
}