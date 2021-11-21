import java.util.ArrayList;

public class RedBlackTree {
	RedBlackNode root;

	public RedBlackTree(int data) {
		root = new RedBlackNode(data); // root 생성
		root.setColorBlack(); // root의 color : Black으로 생성
	}

	/*Insert 삽입*/
	public void Insert(ArrayList<Integer> list) {
		while(!list.isEmpty()) 
			put(root,new RedBlackNode(list.remove(0))); // 모든 데이터 가져가서 삽입
	}

	/*넣는 과정 -> BST와 동일*/
	private void put(RedBlackNode root, RedBlackNode inputNode) {
		if(root.getData() > inputNode.getData()) { // root보다 작은경우 왼쪽에 삽입
			if(root.hasLeft()) // 왼쪽의 데이터가 있다면,
				put(root.getLeft(),inputNode); // 재귀로 들어감
			else {
				root.setLeft(inputNode); // 없다면, 삽입
				inputNode.setParent(root);//부모 설정
				checkRedBlackRule(inputNode);
			}
		}
		else { // root보다 큰 경우
			if(root.hasRight()) { // 오른쪽이 있다면
				put(root.getRight(),inputNode); //재귀로 들어감
			}
			else {
				root.setRight(inputNode); // 없다면 삽입
				inputNode.setParent(root);//부모 설정
				checkRedBlackRule(inputNode); //조건 맞추기
			}
		}
	}

	/*규칙 맞추기*/
	private void checkRedBlackRule(RedBlackNode Node) {
		if(Node.equals(root)) { //만약 Node가 root인 경우
			root.setColorBlack(); 
			return;
		}
		if(Node.getParent().isBlack()) //부모가 black이면 문제 없음
			return;

		RedBlackNode uncleNode = null; // uncleNode
		RedBlackNode ParentNode = Node.getParent(); // parentNode
		RedBlackNode GrandpaNode = ParentNode.getParent();

		if(GrandpaNode.getLeft().equals(ParentNode))
			uncleNode = GrandpaNode.getRight();
		else
			uncleNode = GrandpaNode.getLeft();

		if(!uncleNode.isBlack()) {  // uncleNode가 red일 경우 Recoloring
			uncleNode.setColorBlack();
			ParentNode.setColorBlack();
			GrandpaNode.setColorRed();
			checkRedBlackRule(GrandpaNode); // 재귀로 다시한번 규칙 맞춰야함
		}
		else {  // uncleNode가 Black일 경우
			if(GrandpaNode.getLeft().equals(ParentNode)) { // 부모의 왼쪽에 붙어있는 경우
				if(ParentNode.getRight().equals(Node)) { //부모의 오른쪽이라면
					ParentL_Leftrotation(ParentNode); //왼쪽으로 돌리기 
					checkRedBlackRule(ParentNode);    // 부모에서 다시 수행
				}else{                                   //부모가 왼쪽이라면
					if(GrandpaNode.equals(GrandpaNode.getParent().getLeft())) // 조부모 회전시, 조부모의 아버지를 보고 회전 결정
						ParentL_Rightrotation(GrandpaNode);
					else
						ParentR_Rightrotation(GrandpaNode);
					GrandpaNode.setColorRed(); // 색 맞춰주기
					ParentNode.setColorBlack();
					if(GrandpaNode.equals(root)) { // root가 변경되는 경우
						root = ParentNode;
					}
				}
			}else { // 부모의 오른쪽에 붙어있는 경우 
				if(ParentNode.getRight().equals(Node)) {
					ParentR_Leftrotation(ParentNode);
					checkRedBlackRule(ParentNode);
				}else{
					if(GrandpaNode.equals(GrandpaNode.getParent().getLeft()))
						ParentL_Leftrotation(GrandpaNode); 
					else
						ParentR_Leftrotation(GrandpaNode);
					GrandpaNode.setColorRed();
					ParentNode.setColorBlack();
					if(GrandpaNode.equals(root))
						root = ParentNode;
				}
			}
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

	/*회전 하는 메소드*/
	//부모의 오른쪽일 때, 왼쪽 회전
	private void ParentR_Leftrotation(RedBlackNode Node) {
		if(Node.getParent() != null) {
			Node.getParent().setRight(Node.getRight());//부모와의 관계
			Node.getRight().setParent(Node.getParent());// 
		}else Node.getRight().setParent(null);
		Node.setParent(Node.getRight()); //나와 오른쪽의 관계변경
		Node.setRight(Node.getParent().getLeft());
		Node.getParent().setLeft(Node);
		if(!Node.getRight().equals(RedBlackNode.NIL)) 
			Node.getRight().setParent(Node);
	}
	//부모의 오른쪽일 때, 오른쪽 회전
	private void ParentR_Rightrotation(RedBlackNode Node) {
		if(Node.getParent() != null){
			Node.getParent().setRight(Node.getLeft());
			Node.getLeft().setParent(Node.getParent()); 	
		}else Node.getLeft().setParent(null);  
		Node.setParent(Node.getLeft());
		Node.setLeft(Node.getParent().getRight());
		Node.getParent().setRight(Node);
		if(!Node.getLeft().equals(RedBlackNode.NIL))
			Node.getLeft().setParent(Node);
	}
	//부모의 왼쪽일 때, 왼쪽 회전
	private void ParentL_Leftrotation(RedBlackNode Node) { 
		if(Node.getParent() != null) {
			Node.getParent().setLeft(Node.getRight());   //부모 - 오른쪽 자식 이어주기(부모에서 왼쪽으로)
			Node.getRight().setParent(Node.getParent()); //오른쪽 자식 - 부모 이어주기(왼쪽에서 부모로)
		}else Node.getRight().setParent(null);
		Node.setParent(Node.getRight()); // 대상과 오른쪽의 관계 (부모로 변경)
		Node.setRight(Node.getParent().getLeft()); //대상의 새로운 오른쪽
		Node.getParent().setLeft(Node);
		if(!Node.getRight().equals(RedBlackNode.NIL))
			Node.getRight().setParent(Node);
	}
	//부모의 왼쪽일 때, 오른쪽 회전
	private void ParentL_Rightrotation(RedBlackNode Node) {
		if(Node.getParent() != null) {
			Node.getParent().setLeft(Node.getLeft());
			Node.getLeft().setParent(Node.getParent());
		}else Node.getLeft().setParent(null);
		Node.setParent(Node.getLeft());
		Node.setLeft(Node.getParent().getRight());
		Node.getParent().setRight(Node);
		if(!Node.getLeft().equals(RedBlackNode.NIL))
			Node.getLeft().setParent(Node);
	}
	
	//tree 출력하는 방법
	public void printInorder(RedBlackNode node) {
		if(node != RedBlackNode.NIL) {
			printInorder(node.getLeft());
			System.out.println(node.getData());
			printInorder(node.getRight());
		}
	} 
}
