
public class MergeWithInsertion_Sort {

	/*Sort
	 * MergeSort를 하기 위한 메소드
	 * data -> 데이터
	 * size -> 크기
	 * level -> 얼마만큼을 쪼개고, 삽입정렬을 할지 정한다.
	 * 만약, level이 1이라면, 한번만 쪼개고 그 다음은 삽입정렬을 수행한다. */
	public void Sort(int[] data, int size, int level) {
		RecursiveMergeSort(data,0,size-1,level);
	}

	/*RecursiveMergeSort
	 * 재귀를 이용하여 MergeSort 구현
	 * level을 내려, insertionSort를 수행
	 * mid를 기준으로 좌우 나누어 데이터를 재귀로 MergeSort 수행*/
	private void RecursiveMergeSort(int[] data, int left, int right, int level) {
		if(left < right && level > 0) {
			int mid = ( left + right ) / 2;
			RecursiveMergeSort(data,left,mid, level-1);
			RecursiveMergeSort(data,mid+1,right, level-1);
			Merge(data,left,mid,right);
		}
		else if(level <= 0) {
			InsertionForMerge(data,left,right);
		}

	}

	/*Merge
	 * 병합을 수행하는 메소드
	 * arrayA는 병합하기 위한 하나의 구간
	 * arrayB는 병합하기 위한 또다른 구간
	 * while문을 이용하여, A구간의 데이터가 있다면, 계속해서 데이터를 빼내어 사용
	 * A구간에 데이터가 없다면 혹시나 B구간에 남은 데이터가 있나 확인 후 사용*/
	private void Merge(int[] data, int left, int mid, int right) {
		int[] arrayA = new int[mid-left+1]; //A구간 데이터
		int[] arrayB = new int[right-mid];  //B구간 데이터

		for(int i = 0; i < arrayA.length; i++) { //A구간 데이터 가져오기
			arrayA[i] = data[left + i];
		}
		for(int i = 0; i< arrayB.length; i++) { // B구간 데이터 가져오기
			arrayB[i] = data[mid+1+i];
		}
		
		//접근할 데이터에 대한 index
		int indexB = 0, dataindex = left, indexA = 0;
		while(indexA < arrayA.length) { // A 구간에 사용하지 않은 데이터가 있다면
			int dataA = arrayA[indexA] ,dataB = 0; 
			if(indexB < arrayB.length) { // 만약 B구간에 데이터가 있다면,
				dataB = arrayB[indexB];
				if(dataA > dataB) { // 두 데이터를 비교후 적절한 값 넣어주기
					data[dataindex] = arrayB[indexB];
					indexB ++;
				}else {
					data[dataindex] = arrayA[indexA];
					indexA ++;
				}
			}
			else { // B구간에 데이터가 없는 경우
				data[dataindex] = arrayA[indexA];
				indexA++;
			}
			dataindex++;  //넣어주는 위치 다음으로 옮기기
		}
		while(indexB < arrayB.length) {  // B구간에서 데이터 확인 있다면 넣어준다.
			data[dataindex] = arrayB[indexB];
			indexB++;
			dataindex++;
		}
	}

	/*InsertionForMerge
	 * 삽입정렬을 위한 메소드
	 * find로 데이터를 넣어줄 공간을 찾고, 그 위치에 데이터를 삽입
	 * */
	private void InsertionForMerge(int[] data, int left, int right) {
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

	/*swap
	 * 데이터 변경해주는 메소드*/
	private void swap(int[] data, int a, int b) {
		int temp = data[a];
		data[a] = data[b];
		data[b] = temp;
	}
}
