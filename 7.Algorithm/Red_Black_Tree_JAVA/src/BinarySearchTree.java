import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BinarySearchTree {
	Node root; //BST의 시작

	/*Insert  -> 일반적으로 데이터 삽입
	 * for문을 돌려가며 받아온 데이터 put이용해 삽입*/
	public void Insert(ArrayList<Integer> list) {
		while(!list.isEmpty())
			put(root,new Node(list.remove(0)));
	}

	/*MedianInsert -> 중간값 삽입
	 * data는 정렬된 데이터라고 생각
	 * for문을 이용하여 데이터 끝 부분부터 시작
	 * mid-> 데이터의 반
	 * put이용해 삽입 후 사용 데이터 삭제*/
	public void MedianInsert(int data[]) {
		InsertionSort(data,0, data.length-1);
		for(int i = data.length-1; i >= 0; i--) {
			int mid = i/2; // 사용할 데이터 인덱스
			put(root,new Node(data[mid])); // 삽입
			for(int j = mid; j < i; j++) {
				data[j] = data[j+1]; //for문으로 돌려주기
			}
		}
	}

	/*InsertionSort
	 * 삽입정렬을 위한 메소드
	 * find로 데이터를 넣어줄 공간을 찾고, 그 위치에 데이터를 삽입
	 * */
	public void InsertionSort(int[] data, int left, int right) {
		for(int i = left+1; i <= right; i ++) { // left+1 부터 right까지 모두 수행 ( left는 정렬되어있다고 생각)
			int find;
			int input = data[i];
			for(find = left;find < i;find++) { //적절한 삽입위치찾기
				if(data[find] > input) break;
			}
			for(int j = i; j > find; j --) { // 넣어줄 위치 열어주기
				data[j] = data[j-1];
			}
			data[find] = input;
		}
	}

	/*put -> 데이터 삽입
	 * 적절한 위치를 찾아 삽입한다.(BST를 위함)
	 * BST는 root Node의 값보다 작은건 왼쪽, 큰건 오른쪽
	 * 따라서, 그에 따라 적절한 위치를 찾을때까지 재귀로 타고 내려간다.*/
	private void put(Node root,Node inputNode) {
		if(this.root == null) { //root가 비어있는 경우
			this.root = inputNode;
			return;
		}
		if(root.getData() > inputNode.getData()) { // root보다 작은경우 왼쪽에 삽입
			if(root.hasLeft()) // 왼쪽의 데이터가 있다면,
				put(root.getLeft(),inputNode); // 재귀로 들어감
			else
				root.setLeft(inputNode); // 없다면, 삽입
		}
		else { // root보다 큰 경우
			if(root.hasRight()) { // 오른쪽이 있다면
				put(root.getRight(),inputNode); //재귀로 들어감
			}
			else 
				root.setRight(inputNode); // 없다면 삽입
		}
	}

	/*RecursiveSearch -> 재귀로 Search*/
	public boolean RecursiveSearch(Node root,int findData) {
		if(root.getData() == findData) { // 데이터가 일치하면, 찾았으므로 true 반환
			return true;
		}
		if(root.getData() > findData) { // 데이터가 일치하지 않는 경우, root보다 작으면
			if(root.hasLeft()) { //왼쪽으로 가야하므로 왼쪽이 있는지 확인
				return RecursiveSearch(root.getLeft(),findData); // 있다면, 재귀로 다시 들어간다.
			}
		}else { // root보다 크면
			if(root.hasRight()) { //오른쪽 데이터 확인 
				return RecursiveSearch(root.getRight(),findData); // 재귀로 반복
			}
		}
		return false; // 데이터가 없다면 false 반화
	}

	/*IterativeSearch -> 반복문으로 Search*/
	public boolean IterativeSearch(Node root,int findData) {
		Node findNode = root; //data를 찾으로 다닐 Node
		while(true) { // 무한 루프로 실행
			if(findNode.getData() == findData) { // data가 일치하면
				return true;
			}
			if(findNode.getData() > findData) { // 데이터가 작으면
				if(root.hasLeft()) { // 왼쪽으로 가야하므로 왼쪽확인
					findNode = findNode.getLeft(); //있다면, 왼쪽이동
				}else return false; // 만약, 없다면 false
			}else { // 데이터가 더 큰 경우
				if(findNode.hasRight()) { // 오른쪽으로 이동
					findNode = findNode.getRight(); // Right로 이동
				}else return false; //만약, 없다면 false
			}
		}
	}

	/*Successor 우측 서브트리에서 가장 작은 값 반환*/
	public Node Successor(Node root) {
		Node FindSuccessor; // FindSuccessor 찾기
		if(root.hasRight()) //만약, 오른쪽이 있다면
			FindSuccessor = root.getRight(); //오른쪽으로 이동
		else {
			return root; 
		}
		while(true) {
			if(FindSuccessor.hasLeft())  //왼쪽 계속타고 최소값찾기
				FindSuccessor = FindSuccessor.getLeft();
			else return FindSuccessor; 
		}
	}

	/*Predecessor 좌측 서브트리에서 가장 큰 값 반환*/
	public Node Predecessor(Node root) {
		Node FindPredecessor;
		if(root.hasLeft()) // 만약 왼족이 있다면
			FindPredecessor = root.getLeft(); // 왼쪽으로 이동
		else {
			return root;
		}
		while(true) {
			if(FindPredecessor.hasRight()) // 오른쪽 계속타고 최대값 찾기
				FindPredecessor = FindPredecessor.getRight();
			else return FindPredecessor; 
		}
	}

	/*FindSuccessor 
	 * 위, Predecessor와 Successor를 이용하여,
	 * 삭제시 대체해줄 적절한 적임자 찾아주기
	 * */
	private Node FindSuccessor(Node root) {
		if(root.hasLeft())  // 왼쪽이 있다면
			return Predecessor(root); //왼쪽에서 제일 큰 값 찾기
		else if(root.hasRight()) //오른쪽이 있다면
			return Successor(root); // 오른쪽에서 제일 작은 값 찾기
		else 
			return root;
	}

	public Node Delete(int wantRemoveNodeData) {
		Node wantRemoveNode = root; //data를 찾으로 다닐 Node
		Node preRemoveNode = root; //지워줄 데이터의 앞 데이터
		boolean preLeft = false; // pre의 왼쪽이면 true, 아니면 false;

		if(!RecursiveSearch(root,wantRemoveNodeData))
			return null;
		while(true) { // 무한 루프로  지워질 노드 가져오기
			if(wantRemoveNode.getData() == wantRemoveNodeData) { // data가 일치하면
				break;
			}
			if(wantRemoveNode.getData() > wantRemoveNodeData) { // 데이터가 작으면
				preRemoveNode = wantRemoveNode;
				preLeft = true; // 왼쪽이면 true
				wantRemoveNode = wantRemoveNode.getLeft(); //있다면, 왼쪽이동
			}else { // 데이터가 더 큰 경우
				preRemoveNode = wantRemoveNode;
				preLeft = false; // 오른쪽이면 false
				wantRemoveNode = wantRemoveNode.getRight(); // Right로 이동
			}
		}
		Node returnRemoveNode = wantRemoveNode; // 지워줄 노드 값 저장(추후삭제)

		//두 자식이 모두 있는 경우
		if(wantRemoveNode.hasLeft() && wantRemoveNode.hasRight()) { 
			Node successor = FindSuccessor(wantRemoveNode); // 지우고 대신할 데이터
			Delete(successor.getData());  // successor 삭제
			if(preRemoveNode.equals(wantRemoveNode)) { //만약 root라면
				successor.setRight(root.getRight()); // successor를 지운 데이터 위치로 
				successor.setLeft(root.getLeft());   // 옮겨주기 위한 작업
				root = successor; // root는 successor로
			}
			else{ // root가 아니라면
				if(preLeft) { //만약 preNode의 left를 지우려 한다면,
					successor.setRight(root.getRight()); // successor를 지운 데이터 위치로 
					successor.setLeft(root.getLeft());   // 옮겨주기 위한 작업
					preRemoveNode.setLeft(successor);
				}else { //만약 preNode의 right라면
					successor.setRight(root.getRight()); // successor를 지운 데이터 위치로 
					successor.setLeft(root.getLeft());   // 옮겨주기 위한 작업
					preRemoveNode.setRight(successor);
				}
			}
		}

		//왼쪽 자식만 있는경우
		else if(wantRemoveNode.hasLeft() && !wantRemoveNode.hasRight()) { 
			if(preRemoveNode.equals(wantRemoveNode)) // root가 지워지는 경우
				root = wantRemoveNode.getLeft(); // root 데이터 변환으로 삭제
			else { // root 아래가 지워지는 경우
				if(preLeft) { //왼쪽으로 온 경우
					preRemoveNode.setLeft(wantRemoveNode.getLeft());
					//지워줄 데이터의 왼쪽 데이터로 지워야하므로, preRemoveNode의 left를
					//Left노드로 바꾸어줌.
				}else {
					preRemoveNode.setRight(wantRemoveNode.getLeft());
					//오른쪽 위치에 설정을해주어야함.
				}
			}
		}

		// 오른쪽 자식만 있는 경우
		else if(!wantRemoveNode.hasLeft() && wantRemoveNode.hasRight()) { 
			if(preRemoveNode.equals(wantRemoveNode)) // root가 지워지는 경우
				root = wantRemoveNode.getRight(); // root 데이터 변환으로 삭제
			else { // root 아래가 지워지는 경우
				if(preLeft) { 
					preRemoveNode.setLeft(wantRemoveNode.getRight());
				}else {
					preRemoveNode.setRight(wantRemoveNode.getRight());
				}
			}

			// 자식이 없는 경우
		}else { 
			if(preLeft) {
				preRemoveNode.setLeft(null);
			}else {
				preRemoveNode.setRight(null);
			}
			//단순 삭제
		}
		return returnRemoveNode;
	}

	/*printInorder
	 * 중위 탐색*/
	public void printInorder(Node node) {
		if(node != null) {
			printInorder(node.getLeft());
			System.out.println(node.getData());
			printInorder(node.getRight());
		}
	}
}