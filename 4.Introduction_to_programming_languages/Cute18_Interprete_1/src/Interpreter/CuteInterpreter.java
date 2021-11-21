package Interpreter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CuteInterpreter {

	private void errorLog(String err) {
		System.out.println(err);
	}

	//runExpr는 노드에 따라 연산을 처리하여주는데, ListNode의 경우 runList를 실행시켜준다.
	public Node runExpr(Node rootExpr) {
		if (rootExpr == null)
			return null;
		if (rootExpr instanceof IdNode)
			return rootExpr;
		else if (rootExpr instanceof IntNode)
			return rootExpr;
		else if (rootExpr instanceof BooleanNode)
			return rootExpr;
		else if (rootExpr instanceof ListNode)
			return runList((ListNode) rootExpr);
		else
			errorLog("run Expr error");
		return null;
	}

	/*runList의 경우 ListNode가 들어올 경우 처리해준다.
	 * 만약, list가 비어있다면 list를 그냥 return하여준다. 
	 * 만약 list의 연산자가 functionNode라면 runFunction을
	 * BinaryOpNode라면 runBinary를 실행하여준다.*/
	private Node runList(ListNode list) {
		if(list.equals(ListNode.EMPTYLIST))
			return list;
		if(list.car() instanceof FunctionNode){
			return runFunction((FunctionNode)list.car(), list.cdr());
		}
		if(list.car() instanceof BinaryOpNode){
			return runBinary(list);
		}
		return list;
	}

	/*CAR연산, CDR연산, CONS연산 등 특수한 연산을 수행한다.*/
	private Node runFunction(FunctionNode operator, ListNode operand) {
		switch (operator.value){ 
		/*CAR연산을 수행한다. 항상 QuoteNode가 오게되므로  runQuote를 실행 시킨 뒤 car()를 가져온다.*/
		case CAR :
			return ((ListNode)runQuote(operand)).car();
			/*CDR연산을 수행한다.
			 * 항상 QuoteNode가 오므로 runQuote하여 준 뒤 cdr()을 가져온다.*/
		case CDR :
			return new QuoteNode(((ListNode)runQuote(operand)).cdr());
			/*CONS연산을 수행한다.
			 * CONS연산을 위해 우선 car부분을 만들어준다. 그리고, 만약 car가 QuoteNode라면 runQuote를 이용해 car에 넣어준다.
			 * 그리고 cdr을 runQuote를 통해 만들어 준다. 그 뒤 new연산을 이용해 cons한 결과를 return한다.*/
		case CONS :
			Node car = operand.car();
			if(car instanceof QuoteNode) {
				car = ((ListNode)runQuote(operand));
			}
			ListNode cdr = (ListNode)runQuote(operand.cdr());
			return new QuoteNode(ListNode.cons(car,cdr));
			/*비어있는지 확인하는 NULL_Q이다.
			 * runQuote를 이용하여 나온 값의 맨 앞부분이 EMPTYLIST와 같고, cdr()부분도 같으면 NULL로 취급하여 true return
			 * 아닐경우 false return*/
		case NULL_Q :
			if(((ListNode)runQuote(operand)).car() == (ListNode.EMPTYLIST).car() && ((ListNode)runQuote(operand)).cdr() == (ListNode.EMPTYLIST).cdr()) {
				return BooleanNode.TRUE_NODE;
			}
			return BooleanNode.FALSE_NODE;
			/*ATOM_Q는 리스트가 아닌가 확인하는것이다.
			 * 만약 리스트라면 false 아니라면 true를 return 한다.
			 * runQuote를 이용해 QuoteNode의 값을 확인하여
			 * 만약 ListNode라면 false를
			 * 아니라면 True를 return한다.*/
		case ATOM_Q :
			Node oper_atom = runQuote(operand);
			if(oper_atom instanceof ListNode) {
				return BooleanNode.FALSE_NODE;
			}
			return BooleanNode.TRUE_NODE;
			/*EQ_Q는 같은지 확인하는 것이다.
			 * 비교할 두 대상을 우선 저장을 한다.
			 * 그리고 그 Node가 ListNode인지 IdNode인지 IntNode인지 확인을 하고 처리한다.
			 * 만약 ListNode라면 무한루프를 이용하여 두개의 List의 원소 하나하나를 비교해 같은지 확인한다.
			 * 만약, 다른 원소가 나왔다면, break를 하고 false를 return하고, 끝까지 다 찾으면 true를 리턴한다.
			 * 만약 IdNode나 IntNode라면 그에 맞는 value값을 확인한 뒤 같으면 true 다르면 false를 return한다. */
		case EQ_Q :
			Node oper_eq1 = ((QuoteNode)operand.car()).quoted;
			Node oper_eq2 = runQuote(operand.cdr());
			Boolean check = false;

			if(oper_eq1 instanceof ListNode && oper_eq2 instanceof ListNode) {
				ListNode checkList1 = (ListNode)oper_eq1;
				ListNode checkList2 = (ListNode)oper_eq2;
				Node check_listNode1 = checkList1.car();
				Node check_listNode2 = checkList2.car();
				boolean breakcheck = false;
				while(true) {
					if(check_listNode1 == null && check_listNode2 == null) {
						break;
					}
					if(check_listNode1 instanceof IdNode && check_listNode2 instanceof IdNode) {
						String string_check1 = ((IdNode)check_listNode1).idString;
						String string_check2 = ((IdNode)check_listNode2).idString;
						if(!string_check1.equals(string_check2)) {
							breakcheck = true;
							break;
						}
					}
					else if(check_listNode1 instanceof IntNode && check_listNode1 instanceof IntNode) {
						int int_check1 = ((IntNode)check_listNode1).value;
						int int_check2 = ((IntNode)check_listNode2).value;
						if(int_check1 != int_check2) {
							breakcheck = true;
							break;
						}
					}
					else {
						breakcheck = true;
						break;
					}
					checkList1 = checkList1.cdr();
					checkList2 = checkList2.cdr();
					check_listNode1 = checkList1.car();
					check_listNode2 = checkList2.car();
				}
				if(!breakcheck)
					check = true;
			}
			else if(oper_eq1 instanceof IdNode && oper_eq2 instanceof IdNode) {
				String string_check1 = ((IdNode)oper_eq1).idString;
				String string_check2 = ((IdNode)oper_eq2).idString;
				if(string_check1.equals(string_check2))
					check = true;
			}
			else if(oper_eq1 instanceof IntNode && oper_eq2 instanceof IntNode) {
				int int_check1 = ((IntNode)oper_eq1).value;
				int int_check2 = ((IntNode)oper_eq1).value;
				if(int_check1 == int_check2)
					check = true;
			}

			if(check){
				return BooleanNode.TRUE_NODE;
			}
			return BooleanNode.FALSE_NODE;

			/*not은 true의 값은 false로 false의 값은 true로 반환하여준다.
			 * oper_not을 만들고 거기에 operand를 넣어준다.
			 * 그 뒤 operand의 car위치의 값이 ListNode라면, runExpr를 통하여 ListNode를 처리하여준 뒤 그 결과(항상 예외적인 상황이 안들어오므로 이것은 무조건 Boolean)를 
			 * Boolean으로 형변환 한 뒤 저장한다.
			 * 만약, BooleanNode가 바로나왔다면, 그 값을 넣어주고, true면 false false면 true로 반환하여 return한다*/
		case NOT :
			Node oper_not = runExpr(operand);
			if(operand.car() instanceof ListNode) {
				oper_not  = (BooleanNode)runExpr((ListNode)operand.car());
			}
			else if(operand.car() instanceof BooleanNode){
				oper_not = (BooleanNode)operand.car();
			}

			if(((BooleanNode)oper_not).value) {
				return BooleanNode.FALSE_NODE;
			}
			return BooleanNode.TRUE_NODE;
			/*COND는 조건문으로 맨앞에 true가 나오면 바로 뒤 결과값을 return하는 명령어이다.
			 * 우선 무조건으로 List안에 List가 있는 구조이고, List안의 List에는 처음에는 Boolean 그 뒤에는 어떤 값이 있어야한다.
			 * 따라서, true값이 있나, List안 List의 car()부분을 확인하여 찾는다.
			 * 만약 true를 보았다면, 즉시 그 위치의 cdr부분을 runExpr하여 return하여준다.*/
		case COND : 
			ListNode checkList = (ListNode)operand.car();
			ListNode nextList = operand.cdr();
			BooleanNode find = null;
			while(true) {
				if(checkList.car() instanceof ListNode) {
					find  = ((BooleanNode)runExpr((ListNode)checkList.car()));
				}
				else if(checkList.car() instanceof BooleanNode){
					find = ((BooleanNode)checkList.car());
				}
				if(find.value)
					return runExpr(checkList.cdr());

				if(nextList == ListNode.EMPTYLIST || nextList == ListNode.EMPTYLIST) {
					return null;
				}
				checkList = (ListNode)nextList.car();
				nextList = nextList.cdr();
			}
		default:
			break;
		}
		return null;
	}

	/*산술연산 처리하기, < > = 처리하기
	 * 우선, BinaryOpNode의 연산자를 list.car()를 이용하여 가져옵니다.
	 * 그리고, 그 외의 데이터는 피연산자(list.cdr())로 우선 operands에 넣어둡니다.
	 * runBinary는 IntNode의 연산을 수행하기 위한 메소드이므로 operand1,2를 IntNode에 넣어둡니다.
	 * 이 때, runExpr를 수행한 결과를 가져옵니다.
	 * 그 뒤 연산을 수행합니다. */
	private Node runBinary(ListNode list) {
		BinaryOpNode operator = (BinaryOpNode) list.car(); // 구현과정에서 필요한 변수 및 함수 작업 가능
		ListNode operands = (ListNode) list.cdr();
		IntNode operand1 = (IntNode) runExpr(operands.car());
		IntNode operand2 = (IntNode) runExpr(operands.cdr().car());
		switch (operator.value){
		/*PLUS 연산에 경우에는, operand1과 operand2를 더해준 IntNode를 return 하여줍니다.*/
		case PLUS :
			return  new IntNode(String.valueOf(operand1.value + operand2.value));
			/*MINUS 연산에 경우에는 operand1에서 operand2를 빼준 결과를 IntNode에 넣고 return하여줍니다.*/
		case MINUS :
			return new IntNode(String.valueOf(operand1.value - operand2.value));
			/*DIV 연산에 경우에는 operand1에서 operand2를 나눈 결과를 IntNode에 넣고 return하여준다.*/
		case DIV :
			return new IntNode(String.valueOf(operand1.value / operand2.value));
			/*TIMES 연산에 경우에는 operand1에서 operand2를 곱한 결과를 IntNode에 넣고 return하여준다.*/
		case TIMES :
			return  new IntNode(String.valueOf(operand1.value * operand2.value));
			/*LT 연산에 경우에는 크기 비교 연산으로
			 * operand1에서 operand2를 뺀 결과가 0보다 작다면 true를 아니면 false를 return한다.*/	
		case LT :
			return new BooleanNode((operand1.value-operand2.value) < 0);
			/*GT 연산에 경우에는 크기 비교 연산으로
			 * operand1에서 operand2를 뺀 결과가 0보다 크면 true를 아니면 false를 return한다.*/	
		case GT :
			return new BooleanNode((operand1.value-operand2.value) > 0);
			/*EQ연산에 경우에는 같은지 확인하는 연산으로
			 * operand1과 operand2가 같으면 true 다르면 false를 return한다.*/
		case EQ :
			return new BooleanNode(operand1.value.equals(operand2.value));
		}
		return null;
	}

	/*QuoteNode 처리*/
	private Node runQuote(ListNode node) {
		return ((QuoteNode)node.car()).nodeInside();
	}

	public static void main(String[] args) {
		try {
			while(true) {
				System.out.print("$ ");
				java.util.Scanner sc = new java.util.Scanner(System.in);
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Users/김진섭/eclipse-workspace/Interpreter/data.txt")));
				bw.write(sc.nextLine());
				bw.flush();
				File file = new File("C:/Users/김진섭/eclipse-workspace/Interpreter/data.txt");
				CuteParser cuteParser = new CuteParser(file);
				Node parseTree = cuteParser.parseExpr();
				CuteInterpreter i = new CuteInterpreter();
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