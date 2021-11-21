package Interpreter;



public enum TokenType {
	ID,                        
	INT,                       
	QUESTION,              
	TRUE, FALSE, NOT,          
	PLUS, MINUS, TIMES, DIV,
	LT, GT, EQ, APOSTROPHE,
	L_PAREN, R_PAREN,             
 	DEFINE, LAMBDA, COND, QUOTE,   
	CAR, CDR, CONS,
	ATOM_Q, NULL_Q, EQ_Q;

	//TokenType을 받아온다. 특수문자에 대한 정보
	static TokenType fromSpecialCharactor(char ch) {
		switch ( ch ) {
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
		default:
		throw new IllegalArgumentException("unregisteredchar: " + ch);
		}
	}
}
