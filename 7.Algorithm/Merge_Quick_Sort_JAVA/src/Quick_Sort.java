

public class Quick_Sort {
	private boolean random;
	/*생성자 -> random하게 pivot을 받을지 결정*/
	public Quick_Sort(boolean UseRandom){
		random = UseRandom;
	}
	
	/*Sort -> 퀵소트를 재귀로 수행시켜주기 위해 Main에서 수행되는 메소드*/
	public void Sort(int[] data, int size) {
		RecursiveQuickSort(data,0,size-1);
	}
	
	/*RecursiveQuickSort
	 * 퀵소트를 재귀로 수행.
	 * left < right인 경우에 수행.
	 * pivot은 두 구간을 나누는 위치(partition메소드를 이용 항상 left를 pivot으로 설정 후 쪼개어줌)
	 * 재귀로 왼쪽과 오른쪽을 나누어 다시 실행.
	 * */
	private void RecursiveQuickSort(int[] data, int left, int right) {
		if(left < right) {
			int pivot = partition(data,left,right);
			RecursiveQuickSort(data,left,pivot-1);
			RecursiveQuickSort(data,pivot+1,right);
		}
	}
	
	/*partition
	 * pivot을 기준으로 왼쪽은 작은값, 오른쪽은 큰 값으로 설정
	 * pivot은 생성자의 값에 따라 왼쪽 혹은 랜덤하게
	 * toRight은 오른쪽으로 이동하며 데이터 찾기(pivot보다 큰 값)
	 * toLeft는 왼쪽으로 가며 데이터 찾기(pivot보다 작은 값)
	 * dowhile문으로 계속 돌려가며 데이터 찾고, swap하기
	 * */
	private int partition(int[] data, int left, int right) {
		int pivotindex = pivotindex(data,left,right); //피벗값 가져오기
		swap(data,left,pivotindex); // 피벗가져온 값 맨 왼측에 두기
		int pivot = data[left];
		int toRight = left;
		int toLeft = right+1;
		do {
			do {toRight++;}while(toRight < right && data[toRight] < pivot); // 오른쪽으로 옮기며 데이터 찾기
			do {toLeft--;}while(toLeft > left && data[toLeft] > pivot); // 왼쪽으로 옮기며 데이터 찾기
			if(toRight < toLeft) swap(data,toLeft,toRight); //swap하기
		}while(toRight < toLeft);
		swap(data,left,toLeft); // 마지막, toLeft와 pivot 변경
		return toLeft;
	}
	
	/*pivotindex
	 * 피벗값 가져오기
	 * random하게 가져온다면 true 아니면 false
	 * */
	private int pivotindex(int[] data, int left, int right) {
		if(!random) {
			return left;
		}
		return  (int) (Math.random() * (right - left + 1)) + left;
	}
	
	/*swap
	 * 데이터 변경해주는 메소드*/
	private void swap(int[] data, int a, int b) {
		int temp = data[a];
		data[a] = data[b];
		data[b] = temp;
	}
}