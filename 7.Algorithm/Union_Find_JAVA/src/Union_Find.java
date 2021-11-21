
public class Union_Find {
	//집합을 합친다. 최상위 루트의 부모를 변경해 집합을 바꿔준다.
	public void Union(Node x, Node y) {
		Find_Set(y).SetParent(Find_Set(x)); 
	}
	
	//자신이 포함된 집합을 찾는다. -> 최상위 root를 재귀로 찾아간다.
	public Node Find_Set(Node x) { 
		if(x.getParent() == x)
			return x;
		else 
			return Find_Set(x.getParent());
	}
	
	//집합을 생성하는 부분. -> 부모를 자기자신으로 설정.
	public void Make_Set(Node x) { 
		x.SetParent(x);
	}
}
