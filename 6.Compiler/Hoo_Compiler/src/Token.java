
public class Token {
	private TokenType Type; // Type
	private String data;    // 그에 대한 Data
	
	public Token(String data) { //생성자 - String을 받아 data를 설정.
		this.data = data;
	}
	
	/*Setter*/
	public void setTokenType(TokenType t) {
		Type = t;
	}
	
	public void setData(String s) {
		data = s;
	}
	
	/*Getter*/
	public String getData() {
		return data;
	}
	
	public TokenType getTokenType() {
		return Type;
	}
}
/*
 * Token Class 토큰에 대한 객체를 정의
 */