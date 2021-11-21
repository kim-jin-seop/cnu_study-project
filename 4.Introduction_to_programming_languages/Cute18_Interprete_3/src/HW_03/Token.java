package HW_03;
import java.util.HashMap;
import java.util.Map;

public class Token {												//Token 클래스 변경
	private final TokenType type;
	private final String lexme;
	
	static Token ofName(String lexme) {		//매개변수로 받은 스트링에 대한 타입과 그 스트링 매개변수로 받는 생성자를 생성해서 반환해주는 함수
		TokenType type = KEYWORDS.get(lexme);
		if( type != null ) {										//키워드에 있을때
			return new Token(type, lexme);	//KEYWORDS 해쉬맵을 뒤져서 해당하는 값이 keywords가 아니면 identifier로 간주하고 반환
		}
		else if( lexme.endsWith("?") ) {							//키워드가 아닌데 ?로 끝난다면
			if(lexme.substring(0, lexme.length() -1).contains("?")) {
				throw new ScannerException("invalid ID=" +lexme);	//예외처리
			}
			
			return new Token(TokenType.QUESTION, lexme);		//QUESTION에 해당하는 토큰을 반환
		}
		else if( lexme.contains("?") ) {						//이상한데서 ? 를 포함한다면
			throw new ScannerException("invalid ID=" + lexme);	//예외처리
		}
		else {
			return new Token(TokenType.ID, lexme);				//위의 값이 아니라면 일반 identifier로 간주하고 반환
		}
	}
	
	Token(TokenType type, String lexme) {
		this.type = type;
		this.lexme = lexme;
	}
	
	public TokenType type() {//해당 토큰의 타입을 반환
		return this.type;
	}
	
	public String lexme() {
		return this.lexme;
	}
	
	@Override
	public String toString() {						//TOKEN 클래스의 toString 메소드
		return String.format("%s(%s)", type, lexme);//type은 토큰의 type, lexme는 원래 스트링
	}
	//키값과 밸류값을 가지는 KEYWORDS 라는 맵을 선언 키는 String,밸류는 TokenType
	private static final Map<String,TokenType> KEYWORDS = new HashMap<>();
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
