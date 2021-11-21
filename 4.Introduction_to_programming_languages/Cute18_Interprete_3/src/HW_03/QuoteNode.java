package HW_03;

public class QuoteNode implements Node {
	Node quoted;  
	
	public QuoteNode(Node quoted) {//생성자
		this.quoted = quoted; 
	}    
	@Override
	public String toString(){ 
		return quoted.toString(); 
	} 
	 
	 public Node nodeInside() {//quote 안에 있는 원소를 반환
		 return quoted; 
	}
}

