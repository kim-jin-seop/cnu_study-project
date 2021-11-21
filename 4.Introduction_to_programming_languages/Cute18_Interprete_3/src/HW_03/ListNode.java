package HW_03;

public interface ListNode extends Node {
	Node car(); 
	ListNode cdr(); 
	
	static ListNode cons(Node head, ListNode tail){//cons는 한개의 원소(head)와 한 개의 리스트(tail)를 붙여서 새로운 리스트를 만들어 리턴한다.
		return new ListNode(){  
			@Override  
			public Node car() {//List의 맨 처음 원소를 리턴한다. 
				return head; 
			}   
			@Override  
			public ListNode cdr() {//List의 맨 처음 원소를 제외한 나머지 list를 리턴한다. list가 아닌 데이터에 대헤서는 error를 낸다.
				return tail;  
			}     
			
			public String toString() {	//toString 메소드를 추가해줌으로써 뒤의 equal 명령어를 수행할 수 있게 된다.
				return head.toString() +"\n"+ tail.toString();	//head와 tail을 합쳐서 통째로 출력한 것을 반환한다.
			}
		}; 
	}  
	
	static ListNode EMPTYLIST = new ListNode(){//비어있는 리스트에 해당하는 객체
		@Override
		public Node car() { 
			return null; 
		} 

		@Override  
		public ListNode cdr() { 
			return null; 
		} 
	}; 
	
	static ListNode ENDLIST = new ListNode(){//말단 리스트에 해당하는 객체
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
