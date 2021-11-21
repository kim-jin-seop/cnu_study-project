package parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class CuteParser {
	//Token의 iterator
	private Iterator<Token> tokens;
	
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
			IdNode idNode = new IdNode();
			idNode.value = tLexeme;
			return idNode;
		case INT: //Int일 경우 실행
			IntNode intNode = new IntNode();
			if (tLexeme == null)
				System.out.println("???");
			intNode.value = new Integer(tLexeme);
			return intNode;
			// BinaryOpNode일 경우 수행
		case DIV:
		case EQ:
		case MINUS:
		case GT:
		case PLUS:
		case TIMES:
		case LT:
			BinaryOpNode binaryNode = new BinaryOpNode();
			binaryNode.setValue(tType);
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
			FunctionNode functionNode = new FunctionNode();
			functionNode.setValue(tType);
			return functionNode;
			// BooleanNode
		case FALSE:
			BooleanNode falseNode = new BooleanNode();
			falseNode.value = false;
			return falseNode;
		case TRUE:
			BooleanNode trueNode = new BooleanNode();
			trueNode.value = true;
			return trueNode;
			// case L_PAREN일 경우와 case R_PAREN일 경우
			// L_PAREN일 경우 parseExprList()를 호출하여 처리
		case L_PAREN:  // ( 일 경우
			ListNode listNode = new ListNode();
			listNode.value = parseExprList();
			return listNode;
		case R_PAREN:  // )일 경우
			return null;
		default:
			// head의 next를 만들고 head를 반환하도록 작성
			System.out.println("Parsing Error!");
			return null;
		}
	}
	// List의 value를 생성하는 메소드
	private Node parseExprList() {
		Node head = parseExpr();
		// head의 next 노드를 set하시오.
		if (head == null) // if next token is RPAREN
			return null;
		head.setNext(parseExprList());
		return head;
	}
}