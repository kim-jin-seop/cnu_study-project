import java.util.Stack;
import java.util.StringTokenizer;

public class Hoo_Compiler {
	private String Result;
	static Stack<Token> DataControl;
	private boolean PrintOn;
	/*PrintOn- print명령어 사용여부
	 * DataControl - 토큰 데이터 관리
	 * Result - 컴파일 결과알림
	 *  */

	public Hoo_Compiler() {
		Result = "";
		DataControl = new Stack<Token>();
		PrintOn = false;
	}
	/*생성자*/
	
	public String Compile(String Data) {
		Result = "";
		Tokenizer tokenizer = new Tokenizer(Data); // 토크나이저 생성
		tokenizer.StackInToToken(); // Stack에 토큰넣기
		PrintOn = false;
		Token FirstToken = DataControl.pop(); // 제일 처음 토큰 꺼내기
		TokenScanner(FirstToken); // Token에 대한 정보 읽고 정해진 토큰의 역할 수행
		checkPrintOn();  // print는 하였으나 []만 온경우 next Line 이므로 "\n"생성
		return Result; 
	}
	/*컴파일을 수행하는 메소드
	 * Return 값 -> 컴파일 결과 String
	 * Result,Print여부 항시 초기화
	 * Tokenizer 생성
	 * StackInToToken을 불러내어 토큰 스택에 넣기
	 * 첫 토큰 꺼내어 TokenScanner사용 -> 토큰 의미부여 및 역할별 수행
	 * */

	private void TokenScanner(Token TokenScan) {
		switch(TokenScan.getTokenType()) {
		case KEWORD: //키워드
			KeyWordProcess(TokenScan.getData());
			break;
		case NUM: //반복문자
			NumProcess(TokenScan.getData());
			break;
		case OPTION: //옵션
			OptionProcess(TokenScan.getData());
			break;
		case STRING: //문자열
			StringProcess(TokenScan);
		}
	}
	/*토큰에따라 정해진 역할을 수행하는 메소드로 분리되어 처리*/

	private void KeyWordProcess(String KEWORD) {
		switch(KEWORD) {
		case "ignore":
			while(!DataControl.isEmpty())DataControl.pop(); //DataControl에 데이터 모두 지워주기
			System.out.println(">>\n");                 //무시하므로 빈 공간 출력(보여주기)
			break;
		case "print":
			PrintOn = true;                 //출력신호 확인
			Result += "printf(\"%s\", \"";  //print문 C프로그램 컴파일 과정.
			if(!DataControl.isEmpty())           // 뒤 String토큰이 없을 경우 즉 \n을 희망하는 경우에 대한 예외처리
				TokenScanner(DataControl.pop()); // 다음 토큰으로 이동
		}
	}
	/*KeyWordProcess - KeyWord 토큰에 대한 처리
	 * ignore -> 이전 입력된 모든 토큰 무시 : Stack 비우기
	 * print -> String 데이터 출력: */

	private void NumProcess(String NUMBER) {
		int RepeatNum = Integer.parseInt(NUMBER); //반복해야할 문자를 parseInt를 이용하여 처리
		Token buffer = DataControl.pop();  //buffer에 데이터 넣기 (Option이 있을경우에 대한 예외처리)
		Token StringData;

		if(buffer.getTokenType() != TokenType.STRING) { //String이 아닌경우 -> Option이 있을 수 있음
			StringData = DataControl.pop(); //다음것 꺼내줌 -> String
			String Add = StringData.getData(); //Add해줄 데이터 구하기
			while(!DataControl.isEmpty()) { //비어있지 않다면 즉 그동안 쌓여있는 토큰들 가져오기.
				Token NextSTRING = DataControl.pop(); //DataControl에서 Token하나 pop
				Add = NextSTRING.getData() + Add; //Add에 쌓아서 함께 늘려갈 준비
			}
			StringData.setData(Add); //쌓여있는 토큰의 String 모두 하나로 합치기
			for(int num = 1; num < RepeatNum; num++) { //반복하고싶은 수대로 반복
				StringData.setData(StringData.getData()+Add);
			}
			DataControl.push(StringData); //반복완료 String 넣어주기
			DataControl.push(buffer);     //옵션 넣어주기
		}else {
			StringData = buffer; 
			String Add = StringData.getData();
			while(!DataControl.isEmpty()) {
				Token NextSTRING = DataControl.pop();
				Add = NextSTRING.getData() + Add;
			}
			StringData.setData(Add);
			for(int num = 1; num < RepeatNum; num++) {
				StringData.setData(StringData.getData()+Add);
			}
			DataControl.push(StringData);
			/*위 과정과 동일, 단 옵션이 없는 경우이 없는경우로 따로 처리.*/
		}
		TokenScanner(DataControl.pop()); //다음 토큰 처리
	}
	/*반복문자에 대한처리
	 * 반복문자 수 만큼 반복
	 * 우선, String을 모두 꺼내서 반복
	 * 그 뒤 다시 DataControl에 넣어두기
	 * 다음 토큰 불러내기
	 * */

	private void OptionProcess(String OPTION) {
		Token StringData = DataControl.pop(); //옵션을 적용할 String Token
		switch(OPTION) { //Option에 따라 String 처리
		case "U": // 대문자처리
			StringData.setData(StringData.getData().toUpperCase());
			TokenScanner(StringData);
			return;
		case "L": // 소문자 처리
			StringData.setData(StringData.getData().toLowerCase());
			TokenScanner(StringData);
			return;
		}
		StringTokenizer ForChangeData = new StringTokenizer(OPTION,"/"); // 문자 교환처리
		String ChangeChar = ForChangeData.nextToken();
		String NowChar = ForChangeData.nextToken();
		StringData.setData(StringData.getData().replace(NowChar, ChangeChar)); 
		TokenScanner(StringData);
	}
	/*옵션에 대한 처리
	 *String Token하나 꺼내기
	 *그리고, U, L, x/y에 따라서 처리*/

	private void StringProcess(Token STRING) {
		String result = STRING.getData(); // 토큰 데이터
		if(PrintOn) {
			while(!DataControl.isEmpty()) { // 토큰이 안비어있으면 계속 꺼내서 출력준비
				Token NextSTRING = DataControl.pop();
				result = NextSTRING.getData() + result;
			}
			Result += result + "\");\n"; // 출력문 완성하기
			PrintOn = false; //출력완료 false 신호
			System.out.println(">> "+result + "\n"); //결과 출력(console보여주기
			return;
		}
		else {
			DataControl.push(STRING); //push
		}
	}
	/*String에 대한 처리 마지막이므로, Scnner를 더 사용 안함.
	 * PrintOn확인하여 Print해야하나 확인 아닐경우 Stack에 저장
	 * 위에서 처리되는 모든 토큰은 String
	 * */
	
	private void checkPrintOn() {
		if(PrintOn) {
			System.out.println(">>");  //출력
			System.out.println("");    //출력
			Result += "\\n\");\n";     // \n 결과 출력
		}
	}
	/*checkPrintOn
	 * PrintOn이 True인 경우, PrintOn이 적용되었는데 String이 없는경우
	 * \n를 희망하므로, \n문자 생성*/
}