package parser;


import java.util.HashMap;
import java.util.Map;

public class Token {
	private final TokenType type;
	private final String lexme;
	
	static Token ofName(String lexme) {        // Token Type 판별
		TokenType type = KEYWORDS.get(lexme);  // Token의 KEYWORDS인지 확인- map 사용 ( 아닐 경우 NULL 반환 )
		if ( type != null ) {  //type이 null이 아니면 = KEYWORDS 이므로 Token 바로 대입
			return new Token(type, lexme);
		}
		else if ( lexme.endsWith("?") ) { //마지막이 ? 이면
			if ( lexme.substring(0, lexme.length()-1).contains("?") ) { // ?를 하나 더 포함하고 있으면
				throw new ScannerException("invalid ID=" + lexme);  
			}
			return new Token(TokenType.QUESTION, lexme); // QUESTION으로 Type 설정 
		}
		else if ( lexme.contains("?") ) { // ?를 포함하고 있으면
			throw new ScannerException("invalid ID=" + lexme);
		}
		else {                             // 모두 아니면 ID
			return new Token(TokenType.ID, lexme);
		}
	}

	Token(TokenType type, String lexme) {  // Token의 생성자
		this.type = type;
		this.lexme = lexme;
	}

	public TokenType type() { // Type 반환
		return this.type;
	}

	public String lexme() {  //lexme 반환
		return this.lexme;
	}

	@Override
	public String toString() {  //Token 출력
		return String.format("%s(%s)", type, lexme);
	}
	
	private static final Map<String,TokenType> KEYWORDS = new HashMap<>();  //Token의 KEYWORDS들.
	static {
	KEYWORDS.put("define", TokenType.DEFINE);
	KEYWORDS.put("lambda", TokenType.LAMBDA);
	KEYWORDS.put("cond", TokenType.COND);
	KEYWORDS.put("quote", TokenType.QUOTE);
	KEYWORDS.put("not", TokenType.NOT);
	KEYWORDS.put("cdr", TokenType.CDR);
	KEYWORDS.put("car", TokenType.CAR);
	KEYWORDS.put("cons", TokenType.CONS);
	KEYWORDS.put("eq?", TokenType.EQ_Q);
	KEYWORDS.put("null?", TokenType.NULL_Q);
	KEYWORDS.put("atom?", TokenType.ATOM_Q);
	}
}
