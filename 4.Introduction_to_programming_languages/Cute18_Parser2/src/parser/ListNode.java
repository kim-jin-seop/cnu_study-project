package parser;
//ListNode의 정보 
public interface ListNode extends Node { // 새로 수정된 ListNode
	
	/*car() 메소드는 맨 처음 위치하는 List의 원소이다. (Node)
	 *cdr() 메소드는 List원소중에 그 나머지들이다. (맨 앞 Node 제외한 부분)
	 *따라서 NodePrinter를 하여줄 때, car()를 이용해 맨 앞 List의 원소를 가져오고 그 외 cdr()의 List를 이용해 모든 List를 출력해준다.(이후에 NodePrinter 부분에서 추가적으로 설명)
	 *cons는 Node원소 1개와 한개의 List를 붙여서 새로운 List를 만드는 것이다.
	 *List 원소에 노드 원소 하나를 붙여서 새로운 List를 만드는 방법이다. 
	 *EMPTYLIST는 비어있을 경우 이고, ENDLIST는 끝날 경우이다.
	 *EMPTYLIST의 경우에는 ()를 출력하도록 하기 위함이고, ENDLIST는 LIST의 끝이므로 return해주기 위함이다.
	 **/
	
	
	Node car(); // List에 맨 처음 원소
	ListNode cdr(); //List의 나머지
	static ListNode cons(Node head, ListNode tail){
		return new ListNode(){
			@Override
			public Node car() {
				return head;
			}
			@Override
			public ListNode cdr() {
				return tail;
			}
		};
	}
	static ListNode EMPTYLIST = new ListNode(){
		@Override
		public Node car() {
			return null;
		}
		@Override
		public ListNode cdr() {
			return null;
		}
	};
	
	static ListNode ENDLIST = new ListNode(){
		@Override
		public Node car() {
			return null;
		}
		@Override
		public ListNode cdr() {
			return null;
		}
	};
}