import org.antlr.v4.runtime.tree.ParseTreeProperty;


public class MiniGoPrintLister extends	MiniGoBaseListener {
	private int depth = 0;
	private int count = -1;
	private int paramiterNum; // 파라미터의 번호 지정, 마지막 파라미터인지 확인해줌(Function의 파라미터 구할 때 사용)
	private boolean statement = false; //if문의 상태
	private boolean Fun_decl_2 = false; // Fun_decl의 2번째로 들어간 경우, 파라미터 부분에서 파라미터 처리 안함.
	private boolean inCommonParentheses = false;
	private boolean inputInt = false;  // inputInt는 func실행시, int가 뒤에 들어가있나 확인하고 있으면 출력하여주기 위한 예외처리
	
	private void printDepth() {
		for(int i = 0; i < depth; i ++) {
			System.out.print("....");
		}
	}
	
	private boolean isBinaryOperation(MiniGoParser.ExprContext ctx) { // 이진연산인지 확인
		return ctx.getChildCount() == 3 && ctx.getChild(1) != ctx.expr(); 
	}

	private boolean isPreOperation(MiniGoParser.ExprContext ctx) { // 전위연산인지 확인
		return (ctx.getChildCount() == 2 && ctx.getChild(0) != ctx.expr());
	}

	@Override
	public void enterParam(MiniGoParser.ParamContext ctx) { //파라미터 가져오기
		if(!Fun_decl_2) //Func_decl_2가 실행되면 아래의 과정으로 파라미터를 받아오지 않으므로
			if(paramiterNum != 0) { // 파라미터가 마지막 파라미터가 아닐 경우
				paramiterNum--;
				if(ctx.getChildCount() == 1) { // 만약, 형태(int, string) 같은 것이 없을 경우
					System.out.print(ctx.getChild(0).getText() +","); // 파라미터 출력
				}else {// int가 있는 경우
					System.out.print(ctx.getChild(0).getText() + " "+ctx.getChild(1).getText()+","); // 파라미터 출력
				}
			}else {  // 파라미터가 마지막 파라미터일 경우
				if(ctx.getChildCount() == 1) { // 만약, 형태(int, string) 같은 것이 없을 경우
					if(inputInt) {
						System.out.println(ctx.getChild(0).getText() +")int{");
						inputInt = false;
					}else
						System.out.println(ctx.getChild(0).getText() +"){"); // 파라미터 출력
				}else { // int가 있는 경우
					if(inputInt) {
						System.out.println(ctx.getChild(0).getText() + " "+ctx.getChild(1).getText()+")int{"); // 파라미터 출력
						inputInt = false;
					}else
						System.out.println(ctx.getChild(0).getText() + " "+ctx.getChild(1).getText()+"){"); // 파라미터 출력
				}
			}
	}

	@Override
	public void enterExpr(MiniGoParser.ExprContext ctx) { //Expr일 경우 수행
		if(ctx.getChild(0).getText().equals("(")&& !statement) { //만약, ( 괄호 안에 있는 데이터를 처리한다면, 일반괄호
			printDepth();
			inCommonParentheses = true; // 일반괄호 안에 있음
			System.out.print(ctx.getChild(0).getText()); // '(' 괄호 출력
			if(ctx.getChild(1).getChildCount() == 3) { //괄호 안 연산이 이진 연산인 경우
				System.out.print(ctx.getChild(1).getChild(0).getText() + " "+ctx.getChild(1).getChild(1).getText()+ " " + ctx.getChild(1).getChild(2).getText());
			}
			else if(ctx.getChild(1).getChildCount() == 2) { //괄호 안 연산이 전위 연산인 경우
				System.out.print(ctx.getChild(1).getText());
			}
			System.out.println(ctx.getChild(2).getText()); //괄호 닫기
		}
		else if(isBinaryOperation(ctx) && !inCommonParentheses && !statement) { // 만약, 이진 연산을 수행할 경우 예외처리(일반괄호 연산 수행시 함께 토큰이 읽힘 방지)
			printDepth();
			System.out.println(ctx.getChild(0).getText()+" "+ ctx.getChild(1).getText()+ " "+ ctx.getChild(2).getText()); // 이진 연산 처리(연산자와 피연산자 사이 간격을 둔다)
		}else if(isPreOperation(ctx)) { //전위 연산 수행
			printDepth();
			System.out.println(ctx.getText()); //전위 연산은 피연산자 사이에 빈칸을 주지 않으면 되므로 
		}
		else {
			if(statement && count == 0) { // statement의 expr가 실행되는 경우, count를 통하여 걸러준다. 
				statement = false;
			}else if(count != 0 && statement) {
				count--;
			}
		}
	}

	@Override
	public void exitExpr(MiniGoParser.ExprContext ctx) { //Expr 연산 수행
		if(inCommonParentheses) { //괄호 출력에서 괄호 하나만 데이터 처리하도록 예외처리
			inCommonParentheses = false;
		}
	}

	@Override
	public void enterFun_decl(MiniGoParser.Fun_declContext ctx) { //함수로 들어갈 때
		printDepth();
		depth ++;
		if(ctx.getChildCount() == 7) { // Child가 7 인 경우, Func_decl중 첫번째
			System.out.print(ctx.getChild(0) + " " + ctx.getChild(1) + ctx.getChild(2)); // 함수의 앞 부분만 출력 'func 함수명(' 까지 출력 나머지는, 파라미터 처리에서 출력
			if(ctx.getChild(3).getChildCount() == 0) { // 예외처리 파라미터가 없는 경우
				if(ctx.getChild(5).getChildCount() > 0) {
					System.out.println(")int{");
				}else {
					System.out.println("){");
				}
			}
			if(ctx.getChild(5).getChildCount() > 0) {
				inputInt = true;
			}
		}
		else { // Func_decl중 두번째
			Fun_decl_2 = true;
			System.out.print(ctx.getChild(0) + " " + ctx.getChild(1) + ctx.getChild(2));// 함수의 앞 부분만 출력 'func 함수명(' 까지 출력
			for(int i = 0; i < ctx.getChild(3).getChildCount(); i++) { //매개변수 데이터 가져오기
				if(ctx.getChild(3).getChild(i).getChildCount() == 0) 
					System.out.print(ctx.getChild(3).getChild(i).getText());
				else {
					for(int j = 0; j < ctx.getChild(3).getChild(i).getChildCount()-1; j++) {
						System.out.print(ctx.getChild(3).getChild(i).getChild(j).getText() + " ");
					}
					if(ctx.getChild(3).getChild(i).getChildCount() ==2) {
						System.out.print(ctx.getChild(3).getChild(i).getChild(1).getText());
					}
				}
			}
			for(int i = 4; i < 11; i ++) { // 매개변수 이후에 데이터 출력
				System.out.print(ctx.getChild(i).getText());
			}
			System.out.println(ctx.getChild(11).getChild(0).getText()); // 마지막 { 출력
		}

		paramiterNum=ctx.getChild(3).getChildCount() - (ctx.getChild(3).getChildCount()/2 +1);
		/*paramiterNum -> ctx의 데이터는 ','까지 추가하여 계산
		 * 따라서, 데이터 두개가 들어오면, getChild(3)의  ChildCount는 3 + 2 로 5가됨.
		 * 이는, 데이터가 하나 늘어날 때마다, 등차수열로 2n+1씩 ChildCount가 추가
		 * 따라서, 2로 나눈 결과는 n이고, 찾고자 하는 데이터는 n이므로 2n+1에 (n+1)의 값을 나누어주면됨*/
	}

	@Override
	public void exitFun_decl(MiniGoParser.Fun_declContext ctx) { // Func 종료시 수행
		depth --;
		printDepth();
		System.out.println(ctx.getStop().getText()); // 괄호 닫아주기 
		if(Fun_decl_2)  Fun_decl_2 = false;// Fun_decl_2가 끝남을 의미
	}

	@Override
	public void enterIf_stmt(MiniGoParser.If_stmtContext ctx) { // if문 처리
		printDepth();
		depth ++;
		statement = true;
		System.out.print(ctx.getChild(0));
		if(ctx.getChild(1).getChild(1).getChildCount() == 0) { //만약  if문 뒤에 데이터를 줄 때, ( )를 이용하지 않았다면
			for(int i = 0; i < ctx.getChild(1).getChildCount();i++) { //출력
				System.out.print(" "+ctx.getChild(1).getChild(i).getText());
			}
			count = 0;
		}else { // (괄호를 이용한 경우)
			count = 2; 
			System.out.print(ctx.getChild(1).getChild(0).getText()); // '(' 괄호
			for(int i = 0; i < ctx.getChild(1).getChild(1).getChildCount()-1;i++) { // 데이터 출력
				System.out.print(ctx.getChild(1).getChild(1).getChild(i).getText()+" ");
			}
			System.out.print(ctx.getChild(1).getChild(1).getChild(2).getText()); //마지막 데이터
			System.out.print(ctx.getChild(1).getChild(2).getText()); // 닫기 괄호 ')'
		}
		if(ctx.getChild(2).getText().charAt(0) == '{')  // 열기괄호로 시작한다면,
			System.out.println(ctx.getChild(2).getText().charAt(0)); // 열기괄호 '{' 항상, 열기괄호를 문장 맨 우측에 나오도록 함.
	}

	@Override
	public void exitIf_stmt(MiniGoParser.If_stmtContext ctx) { // if문 종료
		depth --;
		printDepth();
		count = -1; // count를 -1로 설정
		System.out.println(ctx.getStop().getText()); //괄호 닫기
	}
}
