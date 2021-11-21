package HW_03;

class Char {
	private final char value;			//character 문자 하나애 대한 그 값을 가짐
	private final CharacterType type;	//character 문자 하나에 대해서의 type을 가짐

	enum CharacterType {				//enum 선언
		LETTER, DIGIT, SPECIAL_CHAR, WS, END_OF_STREAM, SHARP//문자, 숫자, 특별 기호, 공백, EOS,
	}
	
	static Char of(char ch) {//인자로 받은 ch 와 ch의 타입을 담고있는 생성자를 반환
		return new Char(ch, getType(ch));
	}
	
	static Char end() {
		return new Char(Character.MIN_VALUE, CharacterType.END_OF_STREAM);
	}
	
	private Char(char ch, CharacterType type) {
		this.value = ch;
		this.type = type;
	}
	
	char value() {
		return this.value;
	}
	
	CharacterType type() {
		return this.type;
	}
	//문자 하나의 타입을 반환해주는 함수
	private static CharacterType getType(char ch) {	//Char 클래스의 getType 함수 switch문 수정, 아스키코드 사용하면 될듯
		int code = (int)ch;
		if ( (code >= (int)'A' && code <= (int)'Z')
			|| (code >= (int)'a' && code <= (int)'z')) {
			return CharacterType.LETTER;
		}
		if ( Character.isDigit(ch) )  return CharacterType.DIGIT;
		
		switch ( ch ) {
			case '(':
				return CharacterType.SPECIAL_CHAR; 
			case ')':
				return CharacterType.SPECIAL_CHAR;
			case '+':
				return CharacterType.SPECIAL_CHAR;
			case '-':
				return CharacterType.SPECIAL_CHAR;
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
			case '?' :
				return CharacterType.LETTER;	// ? 일때 SPECIAL_CHAR로 처리되면 FAILED로 가기 때문에 LETTER로 반환 
			case '#':
				return CharacterType.SHARP;		// # 하나일때에 대해서의 상태에 대해서 SHARP라는 상태를 반환
		}
		if ( Character.isWhitespace(ch) ) 	return CharacterType.WS;  //공백인경우
		
		if(ch == '\'') return CharacterType.END_OF_STREAM;//END_OF_STREAM : Stream의 끝임을 알림
		
		throw new IllegalArgumentException("input = " + ch);
	}
}
