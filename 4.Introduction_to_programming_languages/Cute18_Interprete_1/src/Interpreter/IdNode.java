package Interpreter;

// IdNode의 정보
public class IdNode implements ValueNode{
	/*IdNode는 특별한 경우를 제외한 String 형을 의미한다.*/
	// 새로 수정된 IdNode Class
	String idString;
	public IdNode(String text) {
		idString = text;
	}
	@Override
	public String toString(){
		return "ID: " + idString;
	}
}
