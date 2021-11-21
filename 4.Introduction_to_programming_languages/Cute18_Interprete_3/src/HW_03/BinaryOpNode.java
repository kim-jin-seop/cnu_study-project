package HW_03;

import java.util.HashMap;
import java.util.Map;

public class BinaryOpNode implements Node {
	enum BinType {
		MINUS {
			TokenType tokenType() {
				return TokenType.MINUS;
			}
		},
		PLUS {
			TokenType tokenType() {
				return TokenType.PLUS;
			}
		},
		TIMES {
			TokenType tokenType() {
				return TokenType.TIMES;
			}
		},
		DIV {
			TokenType tokenType() {
				return TokenType.DIV;
			}
		},
		LT {
			TokenType tokenType() {
				return TokenType.LT;
			}
		},
		GT {
			TokenType tokenType() {
				return TokenType.GT;
			}
		},
		EQ {
			TokenType tokenType() {
				return TokenType.EQ;
			}
		};
		private static Map<TokenType, BinType> fromTokenType = new HashMap<TokenType, BinType>();
		static {
			for (BinType bType : BinType.values()) {
				fromTokenType.put(bType.tokenType(), bType);
			}
		}

		static BinType getBinType(TokenType tType) {
			return fromTokenType.get(tType);
		}

		abstract TokenType tokenType();
	}

	public BinType value;
	
	public BinaryOpNode(TokenType tType) {//설정자였던 setValue 함수를 BinaryOpNode의 생성자로 바꿔준다. void 타입은 생성자이므로 지워준다.
		BinType bType = BinType.getBinType(tType);
		value = bType;
	}

	@Override
	public String toString() {
		return "Binary: "+value.name();
	}
}