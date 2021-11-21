import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class hw02{
	public enum TokenType{  //Token의 상태 정의
		ID(3), INT(2);   // state가 3일 경우 id, 2일 경우 int

		private final int finalState; 

		TokenType(int finalState) {
			this.finalState = finalState;
		}
	}


	public static class Token { //Token 소개
		public final TokenType type;
		public final String lexme;

		Token(TokenType type, String lexme) {
			this.type = type;
			this.lexme = lexme;
		}

		@Override
		public String toString() { //Token 출력 시 결과 출력
			return String.format("[%s: %s]", type.toString(), lexme);
		}
	}

	public static class Scanner{ //Scanner class
		private StringTokenizer st; 
		private int transM[][]; //transition Matrix
		private String source;  //받아온 데이터

		public Scanner(String source) {
			this.transM = new int[4][128];
			this.source = source == null ? "" : source;
			st = new StringTokenizer(this.source);  // 데이터 받아서 쪼갠다.
			initTM();
		}

		private void initTM() { //transition Matrix 생성
			
			//전부 -1로 우선 초기화
			for(int i = 0 ; i < 4; i ++) {
				for(int j = 0; j < 128; j++) {
					transM[i][j] = -1;
				}
			}

			//state = 0일 때
			for(int i = 0; i < 10; i ++) {             //Digit이 들어올 때
				transM[0]['0'+i] = 2;
			}
			transM[0]['-'] = 1;      // -가 들어올 때
			for(int i = 0; i < 26; i++) {              // 알파벳이 들어올 때
				transM[0]['a' + i] = 3;
				transM[0]['A' + i] = 3;
			}

			//state = 1일 때
			for(int i = 0; i < 10; i++) {               //Digit이 들어올 때
				transM[1]['0'+i] = 2;
			}

			//state = 2일 때
			for(int i = 0; i < 10; i++) {               //Digit이 들어올 때
				transM[2]['0'+i] = 2;
			}

			//state = 3일 때
			for(int i = 0; i < 26; i++) {                //알파벳이 들어올 때
				transM[3]['a' + i] = 3;
				transM[3]['A' + i] = 3;
			}
			for(int i = 0; i < 10; i++) {               //Digit이 들어올 때
				transM[3]['0'+i] = 3;
			}
		}

		
		private Token nextToken(){	
			int stateOld = 0, stateNew;

			//토큰이 더 있는지 검사
			if(!st.hasMoreTokens()) return null;

			//그 다음 토큰을 받음
			String temp = st.nextToken();

			Token result = null;	
			for(int i = 0; i<temp.length();i++){
				//문자열의 문자를 하나씩 가져와 상태 판별
				stateNew = transM[stateOld][temp.charAt(i)];

				if(stateNew == -1){
					//입력된 문자의 상태가 reject 이므로 에러메세지 출력후 return함
					System.out.println(String.format("acceptState error %s\n", temp)); return null;
				}
				stateOld = stateNew;
			}
			for (TokenType t : TokenType.values()){
				if(t.finalState == stateOld){
					result = new Token(t, temp);
					break;
				}
			}			
			return result;	
		}

		//tokenize
		public List<Token> tokenize() {
			List<Token> list = new ArrayList<Token>(); //List 생성
			list.add(nextToken()); //토큰 추가
			return list; // 토큰 넣은 리스트 return
		}
	}

	public static void main(String[] args){
		FileReader fr; //파일 읽기
		try {
			fr = new FileReader("as02.txt");
			BufferedReader br = new BufferedReader(fr);
			String split[] = br.readLine().split(" "); //데이터 쪼개어 넣기 쪼개진 데이터 = source
			for(int i = 0; i < split.length; i++) { //for문을 이용해 쪼개어진 데이터 모두 넣기
				String source = split[i];
				Scanner s = new Scanner(source);
				List<Token> tokens = s.tokenize();
				System.out.println(tokens.get(0)); //get하여 token가져와서  출력
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}