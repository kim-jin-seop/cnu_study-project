package HW_03;
import java.io.PrintStream;
import java.util.StringTokenizer;

public class NodePrinter {
	PrintStream ps;
	
	public static NodePrinter getPrinter(PrintStream ps) {
		return new NodePrinter(ps);
	}
	private NodePrinter(PrintStream ps) {
		this.ps = ps;
	}
	
	private void printNode(ListNode listNode) {
		if(listNode.equals(ListNode.EMPTYLIST)) {//리스트 노드가 비어있는 리스트 일 때 
			ps.print("( )");				//비어있는 리스트이므로 () 를 출력
			return;
		}
		if(listNode.equals(ListNode.ENDLIST)) {//말단 리스트일 경우 아무 출력 없이 반환
			return;
		}
											//리스트 노드를 출력시 기본으로 LPAREN을 출력하고 시작
		printNode(listNode.car());			//리스트 노드의 바로 안에 존재하는 원소를 car()을 사용해서 printNode함수를 사용해서 출력
		if(listNode.cdr().equals(ListNode.EMPTYLIST)) {
			ps.print(" ");
		}
		printNode(listNode.cdr());
	}
	
	private void printNode(QuoteNode quoteNode) {
		if(quoteNode.nodeInside() == null)	// ' 안에 뒤이어 따라오는 리스트가 없다면 그대로 반환
			return;
		ps.print("'");						//'quote' 와 "\'"는 동일하게 처리되므로 '를 출력 해준다.
		printNode(quoteNode.nodeInside());	//quote뒤의 따라오는 원소에대해서 printNode를 실행
	}
	
	private void printNode(Node node) {		//처음의 모든 노드 타입에 대해서 한번씩 거치는 printNode 함수
		if (node == null)					//노드가 비어있다면 그대로 종료
			return;
		
		if (node instanceof ListNode) {		//instanceof로 객체의 타입을 비교해서 ListNode라면
			ps.print("(");
			printNode((ListNode)node);		//ListNode타입으로 형변환 후 printNode 함수 실행
			ps.print(")");
		}else if (node instanceof QuoteNode) {//instanceof로 객체의 타입을 비교해서 QuoteNode라면 
			printNode((QuoteNode)node);		 //QuoteNode타입으로 형변환 후 printNode 함수 실행
		}else { 
			String temp = node.toString();
			StringTokenizer st = new StringTokenizer(temp, " ");
			//ps.print("[" + node.toString() + "] ");//나머지 valueNode에 대해서는 각자의 toString을 가지고 있으므로 양 쪽의 괄호와 노드에 toString을 출력
			st.nextToken();
			ps.print(" " + st.nextToken());//이 부분에서 nextToken이 두번 실행된다. 이는 BooleanNode의 출력시에 문제가 생긴다. 따라서 BooleanNode에 "bool"이라는 스트링을 추가해주었다.
		}
	}
	public void prettyPrint(Node node) {
		printNode(node);
	}
}
	/* 이전 과제의 구현 부분
	private void printList(Node_before head) {
		if (head == null) {
			ps.print("( ) ");
			return;
		}
		ps.print("( ");
		printNode(head);
		ps.print(") ");
	}

	private void printNode(Node_before head) {
		if (head == null)
			return;
		if (head instanceof ListNode) {
			ListNode ln = (ListNode) head;
			printList(ln.value);
		} else {
			ps.print("[" + head + "] ");
		}
		printNode(head.getNext());
	}

	public void prettyPrint(Node_before root) {
		printNode(root);
	}*/
