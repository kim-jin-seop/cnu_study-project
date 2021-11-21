
public class Insertion_Sort extends Sort{

	@Override
	/*sort()
	 * Head -> head
	 * aSize -> 정렬할 데이터의  개수 (사용 안함)
	 * */
	public void sort(Node Head, int aSize) {
		Node checkNode = Head.nextNode(); //checkNode -> 삽입할 노드
		Node inputplaceNode; // 삽입될 위치
		Node makeInputPlace; // 삽입될 위치를 만들어주기 위한노드
		while((checkNode = checkNode.nextNode()) != null) { //모든 노드를 정렬하기 위한 반복문
			inputplaceNode = Head.nextNode(); // 삽입될 위치를 앞에서부터 찾아주기 위해 맨 앞 노드를 삽입위치로 지정
			while(!inputplaceNode.equals(checkNode)&&inputplaceNode.data() < checkNode.data()) { //삽입하려는 데이터의 위치를 찾아주기 위한 반복문
				inputplaceNode = inputplaceNode.nextNode();
			}
			makeInputPlace = checkNode;
			int getData = checkNode.data(); // 이후에 데이터를 원하는 위치에 넣어주기 위해서.
			while(!makeInputPlace.equals(inputplaceNode)) { // 원하는 위치에 데이터를 넣어주기위한 반복문
				makeInputPlace.setData(makeInputPlace.preNode().data()); // pre노드를 계속 set해주어 자리생성
				makeInputPlace = makeInputPlace.preNode(); // 계속 원하는 위치까지 이동
			}
			inputplaceNode.setData(getData); //삽입될 위치에 데이터 삽입
		}
	}
} 
/*Insertion_Sort 클래스
 * 삽입정렬을 수행해주는 클래스
 * 삽입정렬은, 앞부분 혹은 뒷부분이 정렬되어있다고 가정해 놓고 계속해서 오름차순or내림차순으로 
 * 맞는 위치를 찾아서 넣어주는 정렬방법 
 * 삽입정렬 도식화 예시(위 코드를 기반으로)
 * Head -> a -> b -> c -> d
 * 1. a는 정렬되어있다고 가정.
 * 2. b가 a보다 큰지 확인하여, 오름차순으로 데이터삽입
 * 3. c는 a,b 차례로 비교하며 위치를 찾고 데이터삽입
 * 4. d도 a,b,c 차례로 비교하며 위치를 찾고 데이터 삽입
 * */
