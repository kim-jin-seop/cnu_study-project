package HW_03;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CuteInterpreter {

	private Map<String, Node> map = new HashMap<String, Node>();// define된 값들을 저장할 해쉬맵이다.

	private void errorLog(String err) {
		System.out.println(err);
	}

	public Node runExpr(Node rootExpr) {
		if (rootExpr == null)
			return null;

		if (rootExpr instanceof IdNode) {// IdNode의 경우에는 해쉬맵에 define되어있는 내용을 뜻하는지 확인하여 define되어있는 경우에는 그 value값을 가져온다.
			if (map.containsKey(((IdNode) rootExpr).idString))
				return lookupTable((IdNode) rootExpr);
			return rootExpr;
		} else if (rootExpr instanceof IntNode)
			return rootExpr;
		else if (rootExpr instanceof BooleanNode)
			return rootExpr;
		else if (rootExpr instanceof ListNode)
			return runList((ListNode) rootExpr); // ListNode라면 runList실행
		else if (rootExpr instanceof QuoteNode)
			return rootExpr;
		else
			errorLog("run Expr error");

		return null;
	}

	private Node runList(ListNode list) {
		if (list.equals(ListNode.EMPTYLIST)) // list가 EMPTYLIST라면 list 그대로 반환
			return list;
		if (list.car() instanceof FunctionNode) { // 첫번째 노드가 FunctionNode라면
			return runFunction((FunctionNode) list.car(), list.cdr());
		}
		if (list.car() instanceof BinaryOpNode) { // 첫번째 노드가 BinaryOpNode라면
			return runBinary(list); // runBinary함수 실행
		}
		return list;
	}

	private Node runQuote(ListNode node) {
		return ((QuoteNode) node.car()).nodeInside(); // Node타입으로 반환
	}

	private Node runFunction(FunctionNode operator, ListNode operand) {
		switch (operator.value) {
		// CAR, CDR, CONS 등에 대한 동작 구현
		case CAR:
			if (operand.car() instanceof IdNode) {// 해쉬맵에 정의되어 있는 값을 사용할 경우
				Node oper = lookupTable((IdNode) (operand.car()));// 해쉬맵에서 값을 가져와
				if (!(oper instanceof QuoteNode))
					return null;// 쿼트노드인지 확인해준다. 아니라면 잘못된 입력값이므로 null을 반환한다.
				oper = ((QuoteNode) oper).quoted;// 쿼트노드가 맞다면 노드안의 상수리스트를 oper로 설정하여
				return ((ListNode) oper).car();// 그 노드의 첫번째 원소를 리턴한다.
			} else {
				Node oper = runQuote(operand);// 해쉬맵에 정의되어 있는 값을 사용하지 않을 경우에는 노드 안의 상수리스트를 oper로 설정하여
				return ((ListNode) oper).car();// 그 첫번째 원소를 리턴한다.
			}
		case CDR:
			if (operand.car() instanceof IdNode) {// car과 같은 방식으로 해쉬맵에 정의되어 있는 값을 oper로 설정하여 첫번째 원소를 제외한 리스트노드를 반환하도록한다.
				Node oper = lookupTable((IdNode) (operand.car()));
				if (!(oper instanceof QuoteNode))
					return null;
				oper = ((QuoteNode) oper).quoted;
				return ((ListNode) oper).cdr();
			} else {
				Node oper = runQuote(operand);
				return ((ListNode) oper).cdr();
			}
		case CONS:
			Node head = operand.car(); // 일단 head를 저장
			if (head instanceof QuoteNode) { // 만약 head로 들어갈 원소가 QuoteNode라면 '를 제거하고 다시 저장한다.
				head = (ListNode) runQuote(operand);
			}
			ListNode tail = operand.cdr(); // 일단 tail을 저장
			if (tail.car() instanceof QuoteNode) { // 만약 tail로 들어갈 ListNode가 QuoteNode라면 '를 제거하고 다시 저장한다.
				tail = (ListNode) runQuote((ListNode) runExpr(operand.cdr()));
			} else if (tail.car() instanceof IdNode) {// tail이 IdNode일 경우 해쉬맵에 정의되어 있는 쿼트노드를 oper로 설정한다.
				Node oper = lookupTable((IdNode) (tail.car()));
				if (!(oper instanceof QuoteNode))
					return null;
				oper = ((QuoteNode) oper).quoted;
				tail = (ListNode) oper;
			}
			return new QuoteNode(ListNode.cons(head, tail));// cons를 사용해서 합병한 후 반환한다.

		case NULL_Q:// 리스트가 null인지 검사한다.
			if (operand.car() instanceof IdNode) { // 만약 id노드라면
				Node oper = lookupTable((IdNode) (operand.car())); // lookupTable을 이용해 key값에 대한 value를 가져옵니다
				if (!(oper instanceof QuoteNode))
					return null; // 만약 quoteNode가 아니라면 리스트가 아니므로 종료
				oper = ((QuoteNode) oper).quoted; // oper값을 quoted로 바꾸어줍니다.
				if (((ListNode) oper).equals(ListNode.ENDLIST)) { // 만약, 비어있다면
					return BooleanNode.TRUE_NODE; // True 리턴
				}
				return (BooleanNode.FALSE_NODE); // false return
			}
			if (operand.car() instanceof QuoteNode) { // 만약 operand의 원소가 QuoteNode라면
				Node temp = runQuote(operand); // '를 제거한후
				if (temp.equals(ListNode.ENDLIST)) { // ENDLIST인지 확인한다.
					return BooleanNode.TRUE_NODE; // 그렇다면 TRUE를 반환
				}
				return BooleanNode.FALSE_NODE; // 아니라면 FALSE를 반환
			} else { // operand의 원소가 QuoteNode가 아니라면
				if (operand.equals(ListNode.ENDLIST)) { // ENDLIST인지 확인한다.
					return BooleanNode.TRUE_NODE; // 그렇다면 TRUE를 반환
				}
				return BooleanNode.FALSE_NODE; // 아니라면 FALSE를 반환한다.
			}

		case EQ_Q: // 앞의 문장과 뒤의 문장을 toString으로 비교해서 통째로 비교 후 결과값을 리턴한다.
			Node oper; // 첫번째 비교할 문장
			Node oper2; // 두번째 비교할 문장
			if (operand.car() instanceof IdNode) { // 첫번째 문장이 변수일 때
				oper = lookupTable((IdNode) (operand.car()));// 저장된 값을 불러온다.
			} else if (operand.car() instanceof QuoteNode) {// 쿼트 노드일때
				oper = runQuote((ListNode) operand); // runQuote 실행
			} else
				oper = operand.car(); // 위의 조건 둘 다 아니라면 그대로 저장
			if (operand.cdr().car() instanceof IdNode) { // 두번째 문장이 변수일 때
				oper2 = lookupTable((IdNode) (operand.cdr().car()));// 저장된 값을 불러옴
			} else if (operand.cdr().car() instanceof QuoteNode) { // 쿼트 노드일때
				oper2 = runQuote((ListNode) operand.cdr()); // runQuote 실행
			} else
				oper2 = operand.cdr().car(); // 위의 조건 둘 다 아니라면 그대로 저장

			if (oper.toString().equals(oper2.toString())) { // 앞 문장과 뒤의 문장의 값을 비교
				return BooleanNode.TRUE_NODE; // 같다면 true 반환
			} else
				return BooleanNode.FALSE_NODE; // 다르다면 false 반환

		case COND: // 조건문
			ListNode temp = operand; // temp에 operand 저장
			while (temp != ListNode.ENDLIST) { // temp가 ENDLIST가 된다면 종료
				if (runExpr(((ListNode) temp.car()).car()) == BooleanNode.TRUE_NODE) {// runExpr을 호출해서 TRUE라면
					return runExpr(((ListNode) temp.car()).cdr().car()); // runExpr()을 재귀적으로 호출
				}
				temp = temp.cdr(); // operand의 그 다음 cdr()을 저장
			}
		case ATOM_Q: // list가 아니면 모두 atom
			if (operand.car() instanceof QuoteNode) { // operand가 QuoteNode일 때
				QuoteNode temp2 = (QuoteNode) operand.car(); // 뒤이어 오는 원소를 저장
				if (temp2.nodeInside() instanceof ListNode) { // QuoteNode안의 값을 확인 후 ListNode라면
					return BooleanNode.FALSE_NODE; // list이므로 FALSE 반환
				} else
					return BooleanNode.TRUE_NODE; // list가 아니라면 TRUE 반환
			} else if (operand.car() instanceof ListNode) // operand가 ListNode일 경우
				return BooleanNode.FALSE_NODE; // list이므로 FALSE 반환
			else
				return BooleanNode.TRUE_NODE; // 아니라면 TRUE반환

		case NOT:
			if (runExpr(operand.car()) instanceof BooleanNode) { // operand의 car을 확인해서 BooleanNode라면
				if (runExpr(operand.car()) == BooleanNode.TRUE_NODE) { // car을 실행했는데 TRUE가 반환되었다면
					return BooleanNode.FALSE_NODE; // FALSE를 반환
				} else
					return BooleanNode.TRUE_NODE; // FALSE가 반환되었다면 TRUE를 반환
			} else {
				System.out.println("BooleanNode가 아닙니다."); // BooleanNode가 아니라면 경고문 출력
			}
		case DEFINE:
			insertTable((IdNode) operand.car(), operand.cdr().car());
		default:
			break;
		}
		return null;
	}

	private void insertTable(IdNode key, Node value) {// define을 할 경우에 해쉬맵에 key값과 value값으로 저장한다.
		value = runExpr(value);// 연산으로 정의하였을 경우에 그 연산의 결과값을 해쉬맵에 저장한다.
		map.put(key.idString, value);
	}

	private Node lookupTable(IdNode findNode) {// define하여 정의해준 변수들을 해쉬맵에서 가져온다. runExpr을 사용하여 노드들의 실제 형태를 확인할 수 있다.
		return runExpr(map.get(findNode.idString));
	}

	private Node runBinary(ListNode list) {
		BinaryOpNode operator = (BinaryOpNode) list.car();
		IntNode op1 = (IntNode) runExpr(list.cdr().car()); // 첫번째 산술연산 대상을 저장
		IntNode op2 = (IntNode) runExpr(list.cdr().cdr().car()); // 두번째 산술연산 대상을 저장
		int temp1 = op1.value; // 첫번째 산술연산 대상자의 값을 저장
		int temp2 = op2.value; // 두번째 산술연산 대상자의 값을 저장

		switch (operator.value) { // operator에 따라 분류
		case PLUS:
			return new IntNode(Integer.toString(temp1 + temp2)); // 첫번째와 두번째의 값을 string으로 변환 후 IntNode반환
		case MINUS:
			return new IntNode(Integer.toString(temp1 - temp2)); // ""
		case DIV:
			return new IntNode(Integer.toString(temp1 / temp2)); // ""
		case TIMES:
			return new IntNode(Integer.toString(temp1 * temp2)); // ""
		case LT:
			if (temp1 < temp2) { // 첫번째보다 두번째가 크다면
				return BooleanNode.TRUE_NODE; // TRUE 반환
			} else
				return BooleanNode.FALSE_NODE; // 아니라면 FALSE 반환
		case GT:
			if (temp1 > temp2) { // 첫번째가 두번째보다 크다면
				return BooleanNode.TRUE_NODE; // TRUE반환
			} else
				return BooleanNode.FALSE_NODE; // 아니라면 FALSE반환
		case EQ:
			if (temp1 == temp2) { // 첫번째와 두번째의 값이 같다면
				return BooleanNode.TRUE_NODE; // TRUE를 반환
			} else
				return BooleanNode.FALSE_NODE; // 다르다면 FALSE를 반환
		default:
			break;
		}
		return null;
	}

	public static void main(String[] args) {
		CuteInterpreter i = new CuteInterpreter();
        try {
           while(true) {                 
              System.out.print("$ ");
              java.util.Scanner sc = new java.util.Scanner(System.in);
              BufferedWriter bw = new BufferedWriter(new FileWriter(new File("data.txt")));
              bw.write(sc.nextLine());
              bw.flush();
              File file = new File("data.txt");
              CuteParser cuteParser = new CuteParser(file);
              Node parseTree = cuteParser.parseExpr();
              Node resultNode = i.runExpr(parseTree);
              System.out.print("...");
              NodePrinter.getPrinter(System.out).prettyPrint(resultNode);
              System.out.println("");
           }
        }catch (FileNotFoundException e) {
           e.printStackTrace();} 
        catch (IOException e) {
           e.printStackTrace();
        }
	}   
}