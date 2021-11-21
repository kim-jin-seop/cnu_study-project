package parser;
import java.util.HashMap;
import java.util.Map;

public class BinaryOpNode implements ValueNode{
	/*지난 주 과제와 크게 바뀐점이 없으므로 설명을 생략합니다.*/
		enum BinType {
			MINUS { TokenType tokenType() {return TokenType.MINUS;} },
			PLUS { TokenType tokenType() {return TokenType.PLUS;} },
			TIMES { TokenType tokenType() {return TokenType.TIMES;} },
			DIV { TokenType tokenType() {return TokenType.DIV;} },
			LT { TokenType tokenType() {return TokenType.LT;} },
			GT { TokenType tokenType() {return TokenType.GT;} },
			EQ { TokenType tokenType() {return TokenType.EQ;} };
			private static Map<TokenType, BinType> fromTokenType = new HashMap<TokenType, BinType>();
			static {
				for (BinType bType : BinType.values()){
					fromTokenType.put(bType.tokenType(), bType);
				}
			}
			static BinType getBinType(TokenType tType){
				return fromTokenType.get(tType);
			}
			abstract TokenType tokenType();
		}
		public BinType value;
		
		public BinaryOpNode(TokenType tType) {
			BinType bType = BinType.getBinType(tType);
			value = bType;
		}
		@Override
		public String toString(){
			return value.name();
		}
	}

