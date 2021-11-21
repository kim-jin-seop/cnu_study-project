package HW_03;

public class IntNode implements ValueNode {
	public Integer value; //상수값

	@Override
	public String toString() {//IntNode 객체에 해당하는 toString 메소드
		return "INT: " + value;
	}
	
	public IntNode(String text) { 
		this.value = new Integer(text);
	}
}