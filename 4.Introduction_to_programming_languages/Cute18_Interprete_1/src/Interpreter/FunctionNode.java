package Interpreter;
import java.util.HashMap;
import java.util.Map;

public class FunctionNode implements ValueNode{ //binaryOpNode클래스를 보고 참고해서 작성 
	/*지난 주 과제와 크게 바뀐점이 없으므로 설명을 생략합니다.*/
		enum FunctionType{ //FunctionType enum
			ATOM_Q{TokenType tokenType(){return TokenType.ATOM_Q;}},
			CAR{TokenType tokenType(){return TokenType.CAR;}},
			CDR{TokenType tokenType(){return TokenType.CDR;}},
			COND{TokenType tokenType(){return TokenType.COND;}},
			CONS{TokenType tokenType(){return TokenType.CONS;}},
			DEFINE{TokenType tokenType(){return TokenType.DEFINE;}},
			EQ_Q{TokenType tokenType(){return TokenType.EQ_Q;}},
			LAMBDA{TokenType tokenType(){return TokenType.LAMBDA;}},
			NOT{TokenType tokenType(){return TokenType.NOT;}},
			NULL_Q{TokenType tokenType(){return TokenType.NULL_Q;}};
			private static Map<TokenType, FunctionType> fromTokenType = new HashMap<TokenType, FunctionType>();
			
			//map에 데이터값 넣기. key 와  value ( TokenType과 FunctionType)
			static {
				for(FunctionType fType : FunctionType.values()) {
					fromTokenType.put(fType.tokenType(), fType);  
				}
			}
			
			//map에서 해당 FunctionType 데이터 가져오기.
			static FunctionType getFunctionType(TokenType fType) {
				return fromTokenType.get(fType);
			}
			abstract TokenType tokenType();
		}
		public FunctionType value;
		
		/*setValue를 지우고, 생성자를 추가적으로 사용*/
		public FunctionNode(TokenType tType) {
			FunctionType fType = FunctionType.getFunctionType(tType);
			value = fType;
		}
		@Override
		public String toString(){ //내용 채우기
			return value.name();
		}
	}
