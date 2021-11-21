package HW_03;

import java.util.HashMap;
import java.util.Map;


public class FunctionNode implements ValueNode {//특별한 의미를 가지는 keyword와 해당 token에 대한 enum을 선언
	enum FuncType {						//FunctonNode 키워드에 해당하는 기호들을 enum으로 선언한다.
		ATOM_Q {
			TokenType tokenType() {
				return TokenType.ATOM_Q;
			}
		},
		CAR {
			TokenType tokenType() {
				return TokenType.CAR;
			}
		},
		CDR {
			TokenType tokenType() {
				return TokenType.CDR;
			}
		},
		COND {
			TokenType tokenType() {
				return TokenType.COND;
			}
		},
		CONS {
			TokenType tokenType() {
				return TokenType.CONS;
			}
		},
		DEFINE {
			TokenType tokenType() {
				return TokenType.DEFINE;
			}
		},
		EQ_Q {
			TokenType tokenType() {
				return TokenType.EQ_Q;
			}
		},
		LAMBDA {
			TokenType tokenType() {
				return TokenType.LAMBDA;
			}
		},
		NOT {
			TokenType tokenType() {
				return TokenType.NOT;
			}
		},
		NULL_Q {
			TokenType tokenType() {
				return TokenType.NULL_Q;
			}
		};
		private static Map<TokenType, FuncType> fromTokenType = new HashMap<TokenType, FuncType>();
		static {//Map을 선언해서 킷값과 밸류를 토큰 타입과 funcType으로 설정 후 값을 삽입
			for (FuncType fType : FuncType.values()) {
				fromTokenType.put(fType.tokenType(), fType);
			}
		}

		static FuncType getFuncType(TokenType tType) {//해당하는 funcType에 해당하는 함수를 반환해주는 함수
			return fromTokenType.get(tType);
		}

		abstract TokenType tokenType();
	}
	
	public FuncType value;

	public FunctionNode(TokenType tType) {	//설정자였던 setValue 함수 대신 FunctionNode의 생성자로 변경
		FuncType fType = FuncType.getFuncType(tType);
		value = fType;
	}

	@Override
	public String toString() {//binaryOpNode를 참고한 toString 함수
		return "FunctionNode: " + value.name();  //밸류의 이름을 반환
	}


}
