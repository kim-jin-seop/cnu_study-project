
public class LinearHashTable {
	private final int NIL = -1;    //삽입이 아직 안된 경우
	private final int DELETE = -2; //삭제가 된 경우
	
	private int[] table;      //테이블
	private int ConflictCount;//테이블 충돌 횟수
	private int Size;         //테이블 크기 
	
	public LinearHashTable() {
		this.Size = 97;                 // 테이블 크기 :  59
		this.table = new int[this.Size]; //테이블
		for(int i = 0; i < Size; i ++) { //NIL로 초기화
			table[i] = this.NIL;
		}
		
		this.ConflictCount = 0;        //테이블 충돌 횟수
	}
	
	//선형 삽입
	public void Insert(int input) {
		int key = input%this.Size; //접근할 key 값
		while(this.table[key] != NIL && this.table[key] != DELETE) { // 삽입할 위치 찾기
			key = (key+1)%this.Size; // 키 위치 이동
			this.ConflictCount ++; //충돌발생
		}
		table[key] = input; //삽입
	}
	
	/*선형 검색
	 * return 값 -> 데이터가 있는 index
	 * */
	public int Search(int find) {
		int key = find%this.Size;
		while(this.table[key] != NIL) {
			if(find == this.table[key]) //데이터가 있음을 확인
				return key; 
			this.ConflictCount++; // 충돌 발생
			key = (key+1)%this.Size; //키 이동
		}
		return -1; //데이터가 없음
	}
	
	//선형 삭제
	public boolean Delete(int removeData) {
		int key = removeData%this.Size; //key값 가져오기
		while(this.table[key] != NIL) { //key값 찾기
			if(removeData == this.table[key]) {//찾았을 때
				this.table[key] = this.DELETE;
				return true;
			}
			this.ConflictCount ++; // 충돌 발생
			key = (key+1)%this.Size; //키  이동
		}
		return false;
	}
	
	public int getConflictCount() {
		return this.ConflictCount;
	}
}
