package HW_03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class CuteParser {
	private Iterator<Token> tokens;
	private static Node END_OF_LIST = new Node() {};

	public CuteParser(File file) {
		try {
			tokens = Scanner.scan(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Token getNextToken() {//그 다음 토큰을 반환하는 함수
		if (!tokens.hasNext())
			return null;
		return tokens.next();
	}

	public Node parseExpr() {//토큰을 분석해서 해당하는 타입을 가지는 객체를 반환해주는 함수
		Token t = getNextToken();
		if (t == null) {
			System.out.println("No more token");
			return null;
		}
		TokenType tType = t.type();
		String tLexeme = t.lexme();
		switch (tType) {
		case ID:
			return new IdNode(tLexeme);
		case INT:
			if (tLexeme == null)
				System.out.println("???");
			return new IntNode(tLexeme);
			// BinaryOpNode +, -, /, * 가 해당
		case DIV:
		case EQ:
		case MINUS:
		case GT:
		case PLUS:
		case TIMES:
		case LT:
			return new BinaryOpNode(tType);
			//FunctionNode 키워드가 FunctionNode에 해당
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
			return new FunctionNode(tType);
		//새로 수정된 부분
		case FALSE:
			return BooleanNode.FALSE_NODE;
		case TRUE:
			return BooleanNode.TRUE_NODE;
			// case L_PAREN일 경우와 case R_PAREN일 경우   
			// L_PAREN일 경우 parseExprList()를 호출하여 처리	
		case L_PAREN:
			return parseExprList();
		case R_PAREN: // if next token is RPAREN
			return END_OF_LIST;
		case APOSTROPHE:
			return new QuoteNode(parseExpr());
		case QUOTE:
			return new QuoteNode(parseExpr());// ' 과 quote 는 여기서 동일하게 반환해줌. 따라서 print 해줄때 신경 써줄 필요가 없음.
		default:
			System.out.println("Parsing Error!");
			return null;
		}
	}
	//새로 수정된 부분
	private ListNode parseExprList() {
		Node head = parseExpr();
		if(head == null)
			return null;
		if(head == END_OF_LIST)	//if next token is RPAREN
			return ListNode.ENDLIST;

		ListNode tail = parseExprList();

		if(tail == null)
			return null;

		return ListNode.cons(head, tail);
	}







}