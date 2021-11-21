package Interpreter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class CuteParser {
	private Iterator<Token> tokens; 
	private static Node END_OF_LIST = new Node(){}; // 새로 추가된 부분

	//생성자 -> 파일을 읽어온다.
	public CuteParser(File file) {
		try {
			tokens = Scanner.scan(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//다음 토큰을 가져오기.
	private Token getNextToken() {
		if (!tokens.hasNext()) //토큰이 없을 때
			return null;
		return tokens.next(); // 토큰이 있을 때
	}

	//parse 수행
	public Node parseExpr() {
		Token t = getNextToken();  // 토큰 계속 읽기
		if (t == null) {  // token이  null일 경우 즉 토큰이 없을 때
			System.out.println("No more token");
			return null;
		}
		TokenType tType = t.type(); // token의 type
		String tLexeme = t.lexme(); 

		switch (tType) {//TokenType에 따라 다르게 수행
		case ID: // ID일 경우 수행
			IdNode idNode = new IdNode(tLexeme);
			return idNode;
		case INT: //Int일 경우 실행
			if(tLexeme == null) {
				System.out.println("???");
			}
			return new IntNode(tLexeme);
			
			// BinaryOpNode일 경우 수행
		case DIV:
		case EQ:
		case MINUS:
		case GT:
		case PLUS:
		case TIMES:
		case LT:
			BinaryOpNode binaryNode = new BinaryOpNode(tType);
			return binaryNode;
			// FunctionNode일 경우 수행
		case ATOM_Q:
		case CAR:
		case CDR:
		case COND:
		case CONS:
		case DEFINE:
		case EQ_Q:
		case LAMBDA:
		case NOT:
		case NULL_Q:
			FunctionNode functionNode = new FunctionNode(tType);
			return functionNode;
			// BooleanNode
		case FALSE:
			return BooleanNode.FALSE_NODE;
		case TRUE:
			return BooleanNode.TRUE_NODE; 
			//새로 구현된 L_PAREN, R_PAREN Case
		case L_PAREN: // 왼쪽 괄호일때는, List의 시작ㅣ므로 pareExperList를 통해 ListNode를 가져간다.
			return parseExprList(); 
		case R_PAREN:
			return END_OF_LIST ; // 오른쪽 괄호는 List의 끝을 의미한다.
			//새로 추가된 APOSTROPHE, QUOTE
		case APOSTROPHE: //APOSTROPHE나 QUOTE의 경우 다음 위치의 Node값을 가지고 새롭게 만들어진 값을 return하여준다.
		case QUOTE:
			return new QuoteNode(parseExpr());
		default:
			System.out.println("Parsing Error!");
			return null;
		}
	}
	// List의 value를 생성하는 메소드
	private ListNode parseExprList() {
		Node head = parseExpr(); //parseExpr를 통하여 맨 앞 Node하나 가져온다.
		if (head == null)
			return null;
		if (head == END_OF_LIST) // if next token is RPAREN
			return ListNode.ENDLIST;
		
		ListNode tail = parseExprList();  // tail을 parseExprList로 만들어준다.
		if (tail == null) return null;
		return ListNode.cons(head, tail); //cons로 List와 node로 채운다.
	}
}