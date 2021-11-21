package parser;
import java.io.PrintStream;

public class NodePrinter {
		PrintStream ps;
		public static NodePrinter getPrinter(PrintStream ps) {     ////////////static 삭제
			return new NodePrinter(ps);
		}
		private NodePrinter(PrintStream ps) {
			this.ps = ps;
		}
		
		//ListNode일 경우 사용
		private void printList(Node head) {
			if (head == null) { // head에 데이터가 없을 때
				ps.print("( ) ");
				return;
			}
			//head에 데이터가 있으면
			ps.print("( "); // ( 쓰기
			printNode(head); // head 데이터 쓰기
			ps.print(") "); // ) 쓰기
		}
		
		
		private void printNode(Node head) { 
			if (head == null) return; //다 찾으면 종료 
			if (head instanceof ListNode) { // List Node일 경우
				ListNode ln = (ListNode) head; 
				printList(ln.value); // printList -> ListNode일 경우 따로 실행
			} else {
				ps.print("[" + head + "] ");
			}
			printNode(head.getNext()); //다음거 가져오기
		}
		
		public void prettyPrint(Node root){ // Node Print
			printNode(root);
		}
	}