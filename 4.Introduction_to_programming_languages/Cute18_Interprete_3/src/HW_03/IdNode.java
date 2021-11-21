package HW_03;

public class IdNode implements ValueNode {
	public String idString;	//식별자 스트링

	public IdNode(String text) {
		idString = text;
	}
	
	@Override
	public String toString() {//IdNode의 객체에 해당하는 toString 메소드
		return "ID: " + idString;
	}
}