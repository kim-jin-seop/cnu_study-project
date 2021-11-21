public abstract class Sort{
	
	/*swap 메소드
	 * 노드 두개를 받는다
	 * temp에 데이터 저장 후 데이터 교환 하여주는 역할 수행.*/
	protected void swap(Node changeData_first,Node changeData_Second ){
		int temp = changeData_first.data(); 
		changeData_first.setData(changeData_Second.data());
		changeData_Second.setData(temp);
	}
	

	public abstract void sort(Node aHead ,int aSize);
}

/*Sort 클래스
 * 모든 Sort의 부모클래스
 * swap() : 데이터만 변경해 주는 기능 수행
 * sort : abstract로 구현. 
 */