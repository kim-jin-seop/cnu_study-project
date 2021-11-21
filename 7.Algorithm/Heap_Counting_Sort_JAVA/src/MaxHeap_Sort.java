
public class MaxHeap_Sort {
	private int size; //데이터의 크기
	
	/*생성자
	 * size -> 데이터의 크기*/
	public MaxHeap_Sort(int size){ 
		this.size = size;
	}
	
	/*Sort
	 * Heap_Sort(MaxHeap Sort실행)
	 * 최대값을 뽑아서 배열의 앞으로 두므로
	 * 내림차순으로 수행*/
	public int[] Sort(int[] data) {
		return this.Heap_Sort(data);
	}

	/*Heap_Sort
	 * return_Data -> Sort된 데이터
	 * builedHeap을 이용하여 MaxHeap 만들기
	 * for문으로 돌아가며 return_Data의 i index에 최대값 넣기
	 * swap을 이용하여 사용한 max데이터 삭제를 위해 제일 아래 data와 교체
	 * -> 완전 이진트리를 형성하기 위해(마지막 level의 노드 제외 모두 가득 차있으며 마지막 level은 좌측부터 차있음)
	 * heapify로 다시 heap 생성
	 * */
	private int[] Heap_Sort(int[] data) {
		int[] return_Data = new int[size]; // return 데이터
		builedHeap(data); // builedHeap으로 생성
		for(int i = 0; i < return_Data.length; i++) {
			return_Data[i] = data[0]; // max값 넣기
			size--; // size 줄이기
			swap(data,0,size); //맨 아래 값과 최대값 위치옮기기
			heapify(data,0); // heapify로 맨 아래 있던 값 다시 원래 위치로 옮기기
		}
		return return_Data;
	}

	/*heapify를 이용하여 Heap 생성
	 * size/2-1 부터 수행하는 이유는 말단 노드는 자식이 없어서
	 * 비교를 안해줘도 가능하기 때문*/
	private void builedHeap(int[] data) {
		for(int i = size/2-1; i >= 0 ; i --) {
			heapify(data,i);
		}
	}

	/*heapify
	 * 재귀적으로 수행
	 * MaxHeap -> 부모 노드가 자식 노드보다 항상 데이터 값이 크다.
	 * */
	private void heapify(int[] data, int rootIndex) {
		int left = leftChiledIndex(rootIndex); //왼쪽 자식 index
		int right = rightChiledIndex(rootIndex); //오른쪽 자식 index
		if(left >= size) // 자식이 없는경우
			return;
		if(right < size) { //자식이 왼쪽 오른쪽 둘 다 있는 경우
			if(data[left] < data[rootIndex] && data[right] < data[rootIndex]) { // 부모가 제일 큰 경우
				return;
			}else if(data[left] > data[rootIndex] && data[right] < data[rootIndex]) { // 왼쪽 자식이 더 클경우
				swap(data,left,rootIndex);
				heapify(data,left);
			}else if(data[right] > data[rootIndex] && data[left] < data[rootIndex]) { // 오른쪽 자식이 더 큰경우
				swap(data,right,rootIndex);
				heapify(data,right);
			}else { // 두 자식 모두 큰 경우
				if(data[right] > data[left]) { //자식끼리 비교 오른쪽 자식이 더 큰 경우
					swap(data,right,rootIndex);
					heapify(data,right);
				}
				else { // 그 외 왼쪽자식이 크거나 같은경우
					swap(data,left,rootIndex);
					heapify(data,left);
				}
			}
		}else { // 왼쪽만 있는 경우
			if(data[left] > data[rootIndex]) { // 왼쪽 자식이 더 클 때
				swap(data,left,rootIndex);
			}
		}
	}
	
	/*왼쪽 자식의 index*/
	private int leftChiledIndex(int index) {
		return 2*index+1;
	}
	
	/*오른쪽 자식의 index*/
	private int rightChiledIndex(int index) {
		return 2*index+2;
	}
	
	/*데이터를 교환하는 메소드*/
	private void swap(int[] data, int a, int b) {
		int temp = data[a];
		data[a] = data[b];
		data[b] = temp;
	}
}
