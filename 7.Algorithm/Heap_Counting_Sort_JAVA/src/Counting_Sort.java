
public class Counting_Sort {
	private int[] counting; //카운트할 배열
	private int[] result; //결과 배열
	private int[] data; //데이터 값
	
	/*생성자
	 * data -> 정렬할 데이터
	 * counting-> 카운트를 할 배열
	 * result -> 정렬 결과에 대한 배열*/
 	public Counting_Sort(int[] data) {
		int max = data[0]; // max : 최대값
		for(int i = 1; i < data.length; i++) { //최대값 찾기
			if(data[i] > max) {
				max = data[i];
			}
		}
		counting = new int[max+1];   //최대값으로 정렬 
		result = new int[data.length]; // 결과를 저장할 배열
		this.data = data; // 데이터값 가져오기
	}
 	
	/*Sort
	 * Counting Sort를 하는 메소드
	 * counting(data) -> 카운트
	 * Sorting(data) -> Sort
	 * return값 : 결과*/
 	public int[] sort() {
 		counting(); //카운트 
 		sorting();  //정렬
 		return result;  //결과값 return
 	}
 	
 	/*Counting
 	 *데이터가 얼마나 들어왔는지 개수확인
 	 *-> index를 데이터 값으로 하여 개수 확인
 	 *누적합 계산*/
 	private void counting() {
 		for(int i = 0; i < data.length; i++) { // 데이터의 개수 카운트 
 			counting[data[i]]++; 
 		}
 		for(int i = 1; i < counting.length; i++) { // 누적합 수행
 			counting[i] = counting[i] + counting[i-1];
 		}
 	}
 	
 	/*Sorting
 	 * 정렬 수행 
 	 * 결과배열에 data[i]에 맞추어 counting[data[i]]-1의 위치에 넣어주기
 	 * -1을 한 이유는, 마지막에 채워지는 인덱스가 1이기 때문에 한칸씩 당겨주기 위해
 	 */
 	private void sorting() {
 		for(int i = 0; i < data.length; i++) {
 			 result[counting[data[i]]-1] = data[i]; // result배열에 데이터 넣어주기
 			counting[data[i]]--; // counting에 대한 배열 값 줄여주기.
 		}
 	}
}
