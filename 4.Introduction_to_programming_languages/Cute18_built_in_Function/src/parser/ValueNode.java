package parser;

public interface ValueNode extends Node { // 새로 추가된 ValueNode Class
	/*ValueNode는 5개의 Node로 부터 받아온다.
	 * 그 노드들은 모두 값을 가진다.
	 * 1. BinaryNode
	 * 2. BooleanNode
	 * 3. functionNode
	 * 4. IdNode
	 * 5. IntNode*/
}