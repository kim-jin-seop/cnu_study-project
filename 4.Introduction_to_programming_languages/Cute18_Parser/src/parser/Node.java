package parser;
public abstract class Node {
		Node next;
		public Node() { this.next = null; }
		
		//Setter
		public void setNext(Node next){ this.next = next;}
		
		//맨 마지막 위치에 데이터 넣기
		public void setLastNext(Node next){ 
			if(this.next != null) this.next.setLastNext(next);
			else this.next = next;
		}
		
		//Getter
		public Node getNext(){ return next; }
	}
