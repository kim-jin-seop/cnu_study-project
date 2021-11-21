public class Select_Sort extends Sort{
	@Override
	/*sort()
	 * aHead -> head부분
	 * aSize -> Sort할 데이터의 크기
	 * */
	public void sort(Node aHead ,int aSize) {
		Node ceiling_value; // 최대값을 가지고있는 노드
		Node checkNode;     // 최대값 체크를 위한 값
		
		for(int i = 1; i < aSize; i ++) { // 데이터를 Sort하기 위한 반복의 수 = 총 데이터 개수 - 1 (마지막은 자연스럽게 처리되기 때문) 
			ceiling_value = aHead.nextNode(); // 첫번째 데이터 값을 최대값으로 임의 정의
			checkNode = aHead.nextNode();
			for(int j = i; j < aSize ; j++) { // 정렬이 안된 모든 데이터를 비교하기 위한 반복문(최대값을 찾기위한 for문) 
				checkNode = checkNode.nextNode(); // checkNode 다음위치로 이동 
				
				if(ceiling_value.data() < checkNode.data()) // 최대값으로 선정된 값보다 크면, 최대값 변경.
					ceiling_value = checkNode;
			}
			swap(checkNode,ceiling_value); // 위에서 찾아낸 최대값 노드 맨 뒤쪽에 붙여주기.
			}
	}
}
/*Select_Sort 클래스
 * 선택정렬을 수행하여주는 클래스
 * 선택정렬이란, 데이터의 최대값 or 최소값을 계속 찾아주고, 내림차순 or 오름차순에 맞게 정렬하여 하나씩 넣어주는 원리
 * 선택정렬 도식화 예시( 위 코드를 기반으로)
 * Head -> a -> b -> c -> d
 * 1. a,b,c,d 중 최대값을 찾는다.
 * 2. 최대값은 d로 이동한다.
 * 3. 남은 a,b,c중 최대값을 구한다.
 * 4. 최대값을 c에 넣어준다. 이하 생략 
 */