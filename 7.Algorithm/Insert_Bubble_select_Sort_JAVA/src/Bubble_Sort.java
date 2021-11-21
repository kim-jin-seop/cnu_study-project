
public class Bubble_Sort extends Sort{

	@Override
	/*sort()
	 * aHead -> head
	 * aSize -> 데이터의 수
	 * */
	public void sort(Node aHead, int aSize) {
		Node checkNode; // 비교하기 위한 노드
		Node preNode;   // 이전 노드
		
		for(int i = 1; i < aSize; i ++) { // 데이터를 Sort하기 위한 반복의 수 = 총 데이터 개수 - 1 (마지막은 자연스럽게 처리되기 때문)
			preNode = aHead.nextNode();   // 이전 노드
			checkNode = preNode.nextNode(); // 비교하는 위치의 노드
			for(int j = i; j < aSize; j++) { // 아직 정렬되지않은 노드들 체크
				if(checkNode.data() <  preNode.data()) // 만약, checkNode의 값이 더 작으면, 변경 ( 끝에 최대값이 오기 위함)
					swap(checkNode,preNode);
				preNode = checkNode; //다음위치로 이동
				checkNode = checkNode.nextNode(); // 다음 위치로 이동
			}
		}	
	}
}
/*Bubble_Sort 클래스
 * 버블 소트를 위한 클래스
 * 버블소트란, 맨 앞에서부터 계속 데이터를 비교하며 제일 큰 데이터 or 가장 작은 데이터가 우측으로 갈 수 있도록하여 정렬한다.
 * 위 과정을 반복하여 내림 or 오름 차순으로 정렬을 한다.
 * 선택정렬 도식화 예시(위 코드를 기반)
 * Head -> a -> b -> c -> d
 * 1. a,b를 비교해 최대값을 b에 넣는다.
 * 2. b,c를 비교해 최대값을 c에 넣는다.
 * 3. c,d를 비교해 최대값을 d에 넣는다. d에는 최대값이 들어간다.
 * 4. 위 과정을 다시 a,b부터 시작하여 c까지 수행한다. 이하생략
 */