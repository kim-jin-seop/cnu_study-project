package HW_03;

public enum TokenType {	//enum 선언
	ID, ALPHA, DIGIT,
	INT, QUESTION,
	TRUE, FALSE, NOT,
	PLUS, MINUS, TIMES, DIV,
	LT, GT, EQ, APOSTROPHE,
	L_PAREN, R_PAREN, SHARP,//SHARP 추가
	DEFINE, LAMBDA, COND, QUOTE,
	CAR, CDR, CONS,
	ATOM_Q, NULL_Q, EQ_Q;

	static TokenType fromSpecialCharcter(char ch) { //character 토큰 하나에 대한 타입을 반환해주는 함수
		int code = (int)ch;
		if ( (code >= (int)'A' && code <= (int)'Z')
			|| (code >= (int)'a' && code <= (int)'z')) {
			return ID;	//문자 하나 짜리에 대해서는 식별자로 판단하고 identifier로 반환
		}
		
		if ( Character.isDigit(ch) ) {
			return INT;	//숫자 하나 짜리에 대해서는 integer로 판단하고 INT를 반환
		}
		
		switch( ch ) {	//정규표현식을 참고하여 ch와 매칭되는 keyword를 반환하는 case문 작성
			case '(' :
				return L_PAREN;
			case ')' :
				return R_PAREN;
			case '+' :
				return PLUS;
			case '-' :
				return MINUS;
			case '*' :
				return TIMES;
			case '/' :
				return DIV;
			case '<' :
				return LT;
			case '=' :
				return EQ;
			case '>' :
				return GT;
			case '\'' :
				return APOSTROPHE;
			case '#' :
				return SHARP;
			default:
				throw new IllegalArgumentException("unregistered char : " + ch);
		}
	}
}
