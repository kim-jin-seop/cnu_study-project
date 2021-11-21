	package parser;
import java.io.PrintStream;

public class NodePrinter {
	/*NodePrinter class는 출력을 위해 만들어진 클래스
	 * printNode를 ListNode, Node, QuoteNode 3가지 Node의 파라미터를 갖는 값으로 Overload하여 만든다.
	 **/
	PrintStream ps;
	public static NodePrinter getPrinter(PrintStream ps) {     ////////////static 삭제
		return new NodePrinter(ps);
	}
	private NodePrinter(PrintStream ps) {
		this.ps = ps;
	}

	// ListNode, QuoteNode, Node에 대한 printNode 함수를 각각 overload 형식으로 작성
	/*ListNode의 printNode
	 * ListNode에 대한 printNode를 수행하여준다.
	 * 만약, listNode가 비어있다면, ( )를 출력하여주고
	 * 만약, ListNode가 마지막을 가리키면 바로 종료한다.
	 * 아닐 경우에는 (를 열고 car()에 대하여 쓰고 그 외 List를 다시 ListNode를 불러 사용하여 그 외 List 모두 처리하도록 수행한다.
	 * 마지막에는 )를 출력하여준다.
	 **/
	private void printNode(ListNode listNode) {
		if (listNode == ListNode.EMPTYLIST) {
			ps.print("( ) ");
			return;
		}
		if (listNode == ListNode.ENDLIST) {
			return;
		}
		ps.print("( ");
		printNode(listNode.car());
		printNode(listNode.cdr());
		ps.print(") "); 
	}
	
	/*QuoteNode의 printNode
	 * quoteNode의 printNode는 우선 quoteNode의 quoted를 확인한다.
	 * null일 경우 뒤에 Node가 없으므로 return하여준다.
	 * 아닐경우에는 '를 출력한 뒤, quoted를 불러 Node의 printNode를 사용한다.
	 */
	private void printNode(QuoteNode quoteNode) {
		if (quoteNode.nodeInside() == null)
			return; 
		ps.print("'");
		printNode(quoteNode.quoted);
	}

	/* Node의 printNode
	 * Node가 null이면 바로 return
	 * 만약 ListNode라면, ListNode 실행
	 * QuoteNode라면 QuoteNode 실행
	 * 그 외의 경우에는 node값 출력
	 */
	private void printNode(Node node) {
		if (node == null)
			return; 
		if(node instanceof ListNode)
			printNode((ListNode)node);
		else if(node instanceof QuoteNode) {
			printNode((QuoteNode)node);
		}
		else {
			ps.print("[" + node + "] ");
		}
	}

	public void prettyPrint(Node root){ // Node Print
		printNode(root);
	}
}