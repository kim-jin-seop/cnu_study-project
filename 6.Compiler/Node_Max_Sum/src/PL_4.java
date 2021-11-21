import java.util.Stack;

import ast.*;
import compile.TreeFactory;

public class PL_4 {
	public static int max(Node node) {
		Stack<ListNode> stack = new Stack();      //다른 List를 만났을 때 Stack에 넣고, 나중에 모두 꺼내서 확인하기.
		Node findNode = ((ListNode) node).value;  //노드 위치 옮겨가면서 값 찾기
		int max = Integer.MIN_VALUE;              //최대값 찾기 (우선 MIN값으로 초기화)

		while(true) {                             //모든 데이터 확인
			if(findNode instanceof ListNode) {    //A instanceof B -> A가 B 클래스로부터 나왔는지 확인 
				stack.push((ListNode) findNode);  //stack을 이용하여 만약 ListNode라면 우선 넣어둔다.(다른 리스트)
			}
			else if(findNode instanceof IntNode) {   //만약 데이터라면, 비교하여준다.
				if(((IntNode)findNode).value > max) {//findNode의 값이 max값보다 큰지 확인하고,
					max = ((IntNode)findNode).value; //크면 max값 교체
				}
			}
			if(findNode.getNext() == null && stack.isEmpty()) { //만약, 다음위치의 값이 null이라면, stack이 비어있는지 확인
				break; //stack까지 비어있다면, break하여 max값 찾기 종료
			}
			else if(findNode.getNext() == null) { //stack이 안비어있다면
				findNode = stack.pop().value;     //stack에서 하나 꺼내서 데이터 넣기
				while(findNode == null)           //그것이 데이터가 없을 경우
					if(!stack.isEmpty()) {        //stack이 비어있다면
						findNode = stack.pop().value;  //데이터 계속 가져오기
					}else {                            //Stack이 비어있지 않다면
						return max;                    //max값 return 하기
					}
				continue;
			}
			findNode = findNode.getNext();
		}
		return max;
	}

	public static int sum(Node node) {
		Stack<ListNode> stack = new Stack();      //다른 List를 만났을 때 Stack에 넣고, 나중에 모두 꺼내서 확인하기.
		Node findNode = ((ListNode) node).value;  //노드 위치 옮겨가면서 값 찾기
		int sum = 0;                              //총 합 

		while(true) {                             //모든 데이터 확인
			if(findNode instanceof ListNode) {    //A instanceof B -> A가 B 클래스로부터 나왔는지 확인 
				stack.push((ListNode) findNode);  //stack을 이용하여 만약 ListNode라면 우선 넣어둔다.(다른 리스트)
			}
			else if(findNode instanceof IntNode) {   //만약 데이터라면, 비교하여준다.
				sum += ((IntNode)findNode).value;    //모두 더하기(sum)
			}
			if(findNode.getNext() == null && stack.isEmpty()) { //만약, 다음위치의 값이 null이라면, stack이 비어있는지 확인
				break; //stack까지 비어있다면, break하여 총 합 return
			}
			else if(findNode.getNext() == null) { //stack이 안비어있다면
				findNode = stack.pop().value;     //stack에서 하나 꺼내서 데이터 넣기
				while(findNode == null)           //그것이 데이터가 없을 경우
					if(!stack.isEmpty()) {        //stack이 비어있다면
						findNode = stack.pop().value;  //데이터 계속 가져오기
					}else {                            //Stack이 비어있지 않다면
						return sum;                    //sum값 return 하기
					}
				continue;
			}
			findNode = findNode.getNext();
		}
		return sum;
		
	}

	public static void main(String... args) {
		Node node = TreeFactory.createtTree("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");
		System.out.println("최대 값 : " + max(node));
		System.out.println("총합 : " + sum(node));
		//이하 결과를 출력하도록 작성
	}
}
