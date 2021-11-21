package lexer;

class Char { 
	private final char value;   // 값
	private final CharacterType type;  //type - 상태

	/*LETTER -> A~Z, a~z
	 *DIGIT -> 0~9
	 *SPECIAL_CHAR -> +,-제외한 특수문자
	 *WS -> 공백
	 *END_OF_STREAM -> STREAM의 끝인지 확인
	 *SIGN -> +,-의미 SPECIAL_CHAR와 SIGN을 나눈 이유는, SIGN의 뒤에 DIGIT이 오면 INT로 바뀌기 때문
	 *SHARP-> #이 올 때, boolean을 위해 정의
	 *Q -> ?  null? 과 같은 데이터를 처리하기 위하여 생성
	 */
	enum CharacterType {
		LETTER, DIGIT, SPECIAL_CHAR, WS, END_OF_STREAM,SHARP,SIGN,Q  
	}
	
	
	/*Char of는 getType을 통하여 문자 하나의 Type을 가져온다.*/
	static Char of(char ch) {   // Char을 가져온다.
		return new Char(ch, getType(ch));
	}
	
	/*end는 STREAM의 종료를 의미한다.*/
	static Char end() {  //value = null 값 CharacterType = END_OF_STREAM
		return new Char(Character.MIN_VALUE, CharacterType.END_OF_STREAM);
	}
	
	/*Char의 생성자 -> value에 ch를 넣고, CharacterType에 type삽입*/
	private Char(char ch, CharacterType type) { //Char getter
		this.value = ch;
		this.type = type;
	}
	
	char value() {        //value 값 getter
		return this.value;
	}
	
	CharacterType type() {  //타입 getter
		return this.type;
	}
	
	/*문자의 타입 정하기 위에 CharacterType 설명에 기재된 대로 문자의 타입 삽입*/
	private static CharacterType getType(char ch) {        //문자의 타입 정하기
		int code = (int)ch;
		if ( (code >= (int)'A' && code <= (int)'Z')        // A~Z, a~z -> letter
			|| (code >= (int)'a' && code <= (int)'z')) {
			return CharacterType.LETTER;
		}
		
		if ( Character.isDigit(ch) ) {                     // 0~9 -> Digit
			return CharacterType.DIGIT;
		}
		switch ( ch ) {                                 // -> SPECIAL_CHAR, SIGN, Q, SHARP;
			case '-': 
				return CharacterType.SIGN;  //내가 수정
			case '(':
				return CharacterType.SPECIAL_CHAR;
			case ')':
				return CharacterType.SPECIAL_CHAR;
			case '+':
				return CharacterType.SIGN;
			case '*':
				return CharacterType.SPECIAL_CHAR;
			case '/':
				return CharacterType.SPECIAL_CHAR;
			case '<':
				return CharacterType.SPECIAL_CHAR;
			case '=':
				return CharacterType.SPECIAL_CHAR;
			case '>':
				return CharacterType.SPECIAL_CHAR;
			case '\'':
				return CharacterType.SPECIAL_CHAR;
			case '#' :
				return CharacterType.SHARP; //추가 SHARP일 때.
			case '?' :
				return CharacterType.Q;
		}
		
		if ( Character.isWhitespace(ch) ) {             // Chhar가 공백인지 확인
			return CharacterType.WS;
		}
		
		//위의 문자들이 아닐 경우.
		throw new IllegalArgumentException("input=" + ch); 
	}
}
