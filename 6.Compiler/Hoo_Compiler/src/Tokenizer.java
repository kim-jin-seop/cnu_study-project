import java.util.StringTokenizer;

public class Tokenizer {
	private StringTokenizer MakeToken; // MakeToken - Token을 생성하기 위하여 쪼개주는 StringTokenizer
	
	public Tokenizer(String TokenData) {
		StringTokenizer RemoveSpace = new StringTokenizer(TokenData); //빈 공간을 없애주기 위함.
		String FortokenData = "";
		while(RemoveSpace.hasMoreTokens()) {
			FortokenData += RemoveSpace.nextToken();
		} //빈 공간을 없애준다.
		MakeToken = new StringTokenizer(FortokenData,"["); // 처음 [로 쪼갠다.
	}
	/*"["로 쪼갠 이유 -> 뒤 옵션이 있는지 확인하기 위하여.*/
	
	public void StackInToToken() {
		if(MakeToken.countTokens() == 1) {  
			NoOptionTokenizer(); // Option이 없는 경우 
			return; //종료조건, Option이 없는경우 실행 후 종료
		}
		HasOptionTokenizer(); //Option이 있는경우
	}
	/*Stack에 Token을 넣기위한 과정
	 * 만약 위 과정에서 Token을 쪼갠 결과가 1개라면 Option이 없음
	 * 2개라면 Option이 있음
	 * */
	
	private void FindString() {
		String buffer = MakeToken.nextToken(); //buffer에 토큰넣기
		if(MakeToken.hasMoreTokens()) 	// 토큰이 더있다면 -> Option이 있을 경우를 위한 예외처리
			buffer += MakeToken.nextToken(); // 토큰을 합쳐서 넣어줄 준비를 함.
		MakeToken = new StringTokenizer(buffer,"]");  //위 데이터를 ]로 쪼개어 준다.
		if(MakeToken.countTokens() == 1) { //만약 데이터가 1개라면, 빈 공백을 의미. 따라서 String이 없으므로 종료
			return;
		}
		Token STRING = new Token(MakeToken.nextToken()); //String Token을 만들어준다.
		STRING.setTokenType(TokenType.STRING); //TokenType을 설정
		Hoo_Compiler.DataControl.push(STRING); //Stack에 쌓아준다.
	}
	/*첫번째 String을 쪼개기
	 * 핵심 - Option에 대한 예외처리.
	 * String 토큰을 생성하기 위하여 토큰에 대한 데이터만 남기기 위해 ]로 쪼개어준다.
	 * 빈 공백에 대한 예외처리.*/
	
	private void FindOption() {
		Token OPTION = new Token(MakeToken.nextToken());  //Option Token 생성
		OPTION.setTokenType(TokenType.OPTION);
		Hoo_Compiler.DataControl.push(OPTION);
	}
	/*두번째 Option이 있다면 Option Token 넣어주기
	 * 핵심 - 위 FindString메소드에서 모두 쪼개어주어 nextToken은 바로 OptionToken
	 * Option에 대한 Token을 만들고 그대로 넣어준다.
	 * */
	
	private void FindInstruction() {
		String buffer = MakeToken.nextToken();     // buffer에 남은 Token을 꺼내어준다.
		MakeToken = new StringTokenizer(buffer,":"); // :로 쪼개어준다.
		if(MakeToken.hasMoreTokens())                // 예외처리, 키워드와 반복문자 모두 없을 경우 고려
			buffer = MakeToken.nextToken();         
		else
			return;                                 // 키워드 반복문자 모두 없음. 종료
		MakeToken = new StringTokenizer(buffer,")");// ')'를 제거하여 반복문자가 있나 확인한다.
		if(buffer.charAt(0) == '(') {               //  첫 문자가 (인지 확인하여 반복문자의 유무를 확인한다.
			buffer = MakeToken.nextToken();         // 반복문자가 있을경우 버퍼에 (반복문자 가 있는 토큰을 넣는다.
			StringTokenizer MakeToNum = new StringTokenizer(buffer,"("); // (를 제거한다. 반복문자만 빼낸다.
			Token NUM = new Token(MakeToNum.nextToken());  // 남은 반복문자를 넣어둔다.
			NUM.setTokenType(TokenType.NUM);	//Num토큰을 만들어 넣어준다.
			Hoo_Compiler.DataControl.push(NUM);
			if(!MakeToken.hasMoreElements()) { // 예외처리- 만약 Token이 없다면, 반복문자가 없는 경우이므로 종료
				return;
			}
		}
		Token KEWORD = new Token(MakeToken.nextToken()); //남은 것은 자연스럽게 KEWORD가 된다.
		KEWORD.setTokenType(TokenType.KEWORD);
		Hoo_Compiler.DataControl.push(KEWORD);
	}
	/*반복문자와 키워드를 빼내어준다.
	 * 반복문자를 우선 빼내어준 뒤, 남은 키워드를 Stack에 넣기*/
	
	private void NoOptionTokenizer() {
		FindString();
		FindInstruction();
	}
	/*옵션이 없으면 String와 뒤 반복문자, 키워드만 분리*/
	
	private void HasOptionTokenizer() {
		FindString();
		FindOption();
		FindInstruction();
	}
	/*옵션이 있으면 위 과정에 Option 분해까지 추가*/
}

/*StringTokenizer 클래스는 Token으로 분리하는 과정을 의미한다.
 * String -> Option -> Num -> Keword 순으로 쪼개가며 데이터를 넣어준다.
 * 분해과정은 위에서 명시
 * Stack에 들어가는 과정 분해순과 동일
 * Stack에서 추후 빼내는 과정 - 역순
 * */
