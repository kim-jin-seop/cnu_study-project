package HW_03;

public class BooleanNode implements ValueNode {
	Boolean value;

	@Override
	public String toString() {
		return "bool " + (value ? "#T" : "#F"); // 가지고 있는 value 값을 반환
	}//Nodeprinter에서 nextToken을 두번 실행하기때문에 출력에 문제가 생긴다. 따라서 "bool" 이라는 스트링을 추가해 주었다.

	public static BooleanNode FALSE_NODE = new BooleanNode(false);
	public static BooleanNode TRUE_NODE = new BooleanNode(true);

	private BooleanNode(Boolean b) {// 생성자. 매개변수로 받은 T/F 를 value값으로 가진다.
		value = b;
	}
}