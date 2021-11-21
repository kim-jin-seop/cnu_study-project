
public class Recursion {

	// main
	public static void main(String[] args) {
		System.out.println(fibonacci(10));
		System.out.println(recursiveAnt(10));
		System.out.println(recursiveAnt(11));
	}

	//피보나치
	public static int fibonacci(int n) {
		if(n == 1 || n == 2) {  //첫째 항, 둘째 항일 경우 return 1
			return 1;
		}
		else {    // 그 외 경우 이전 항 두개를 더하여, n위치의 값 return
			return fibonacci(n-1) + fibonacci(n-2);
		}
	}

	//개미 수열
	public static String recursiveAnt(int n) {
		if(n == 1) {    // 첫째항일 경우 "1"
			return "1";
		}
		else if(n == 2) {
			return "11";
		}
		else {   //첫째항이 아닐 경우
			return makeResult(recursiveAnt(n-1));
		}
	}

	//개미 수열 n번째 값 생성
	public static String makeResult(String previous){
		String[] sp = previous.split("");   
		String result = "";
		if(previous.length() != 0) { //우선 모든 데이터를 읽기 위해 재귀 실행
			result += makeResult(previous.substring(1,sp.length -1));
		}
		else { // 모든 데이터 읽기 준비 완료
			return "";
		}
		String[] resultsp = result.split("");

		if(result.length() == 0) { // 만약 데이터를 읽는데, 시작이라면
			if(sp[0].equals(sp[1])) { // 읽는 두 데이터 값이 같으면
				return sp[0] + "2";
			}
			else { // 다를경우
				return sp[0] + "1" + sp[1] + "1";
			}
		}
		else { // 데이터를 읽는데 시작이 아니면
			//앞의 데이터 삽입
			if(sp[0].equals(resultsp[0])) { // 앞의 데이터는 앞의 값과 비교. 같으면 개수를 늘려준다.
				result = sp[0]+ Integer.toString(Integer.parseInt(resultsp[1]) + 1) + result.substring(2, result.length());
			}
			else { // 앞과 다르면 새로운 값 읽어주기.
				result = sp[0] + "1" + result;
			}
			// 뒤의 데이터 삽입
			if(sp[sp.length-1].equals(resultsp[resultsp.length-2])) {  // 뒤의 데이터 읽기 앞 데이터 읽기와 동일 설명 생략
				result = result.substring(0, result.length() - 1) + Integer.toString(Integer.parseInt(resultsp[resultsp.length-1]) + 1);
			}
			else {
				result = result + sp[sp.length-1] + "1";
			}
			return result;
		}
	}
}
