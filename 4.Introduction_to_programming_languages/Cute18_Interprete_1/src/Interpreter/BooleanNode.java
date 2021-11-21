package Interpreter;

public class BooleanNode implements ValueNode { // 새로 수정된 BooleanNode Class
	/*BooleanNode는 value값으로 Boolean을 갖는다.
	 * Value값이 True를 갖는가, False를 갖는가에 따라 출력이 바뀐다.
	 * toString을 재 정의함으로써, class를 출력하면 #T or #F가 출력되게 설정하였다.*/
	Boolean value;

	@Override
	public String toString(){
		return value ? "BOOLEAN: #T" : "BOOLEAN: #F";
	}
	public static BooleanNode FALSE_NODE = new BooleanNode(false);
	public static BooleanNode TRUE_NODE = new BooleanNode(true);
	public BooleanNode(Boolean b){
		value = b;
	}
}