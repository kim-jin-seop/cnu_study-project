import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.antlr.v4.runtime.tree.ParseTree;

public class UCodeGenListener extends MiniGoBaseListener {
	private final String SPACE = "           "; // 띄어쓰기 11칸
	File file  = new File("result.txt");   // 파일
	BufferedWriter fw;                       // 파일 쓰기
	HashMap<String,String> global_Var = new HashMap<String,String>(); //전역변수에 대한 정보
	HashMap<String,String> Local_Var; //지역 변수에 대한 정보 
	HashMap<String,String> InBlock_Var; //함수 내에서 if나 for문에서 추가되는 변수
	int globalNum; //전역변수 개수
	int loopNum = 1; // loop 개수
	ArrayList<String> ForPrint; //func의 출력을 위한 메소드
	int localNum; //지역변수 개수

	@Override 
	public void enterProgram(MiniGoParser.ProgramContext ctx) { //글로벌 변수 정의 프로그램 시작 부분 
		ForPrint = new ArrayList<String>(); //출력을 위한 프린터 생성
		globalNum = 1; // 글로벌 변수의 크기?
		try {
			fw = new BufferedWriter(new FileWriter(file)); // 프로그램의 시작 부분, 파일을 쓰기 위하여 변수 생성
			for(int i = 0; i < ctx.getChildCount(); i++) { //for문을 돌려, 전역변수와 함수 작성
				if(ctx.getChild(i).getChild(0) instanceof MiniGoParser.Fun_declContext ) { //함수 인 경우
					continue; //함수는   enterFun_decl에서 구현
				}else if(ctx.getChild(i).getChild(0) instanceof MiniGoParser.Var_declContext ){ // 전역 변수일 경우
					String GlobalKey; //맵에 지정할 값
					if(ctx.getChild(i).getChild(0).getChildCount() == 3) { // 전역변수 case 1 VAR IDENT Type일 경우
						GlobalKey = ctx.getChild(i).getChild(0).getChild(1).getText(); // IDENT는 글로벌변수로 저장할 때 key 값으로 설정
						global_Var.put(GlobalKey, "1 "+(globalNum++)); //전역변수 생성 후 넣기
						System.out.println(SPACE+"sym "+global_Var.get(GlobalKey)+" 1"); // 생성된 전역변수 사용
						fw.write(SPACE+"sym "+global_Var.get(GlobalKey)+" 1"); // 생성된 전역변수 사용 이를 파일로 생성
						fw.newLine(); // 생성 후 다음 화면으로 이동

					}else if(ctx.getChild(i).getChild(0).getChildCount()== 5){ //전역변수 case 2 Var IDENT, IDENT type일 경우
						GlobalKey = ctx.getChild(i).getChild(0).getChild(1).getText(); //첫번째 변수 값 설정
						global_Var.put(GlobalKey, "1 "+(globalNum++)); //전역변수 생성 후 넣기
						System.out.println(SPACE+"sym "+global_Var.get(GlobalKey)+" 1"); // 전역변수 생성 Ucode
						fw.write(SPACE+"sym "+global_Var.get(GlobalKey)+" 1"); // 전역변수 생성 Ucode
						fw.newLine();
						GlobalKey = ctx.getChild(i).getChild(0).getChild(3).getText(); //두번째 변수 값 설정
						global_Var.put(GlobalKey, "1 "+(globalNum++)); //전역변수 생성 후 넣기 
						System.out.println(SPACE+"sym "+global_Var.get(GlobalKey)+" 1"); // 전역변수 생성 Ucode
						fw.write(SPACE+"sym "+global_Var.get(GlobalKey)+" 1"); // 전역변수 생성 Ucode
						fw.newLine();
					}else if(ctx.getChild(i).getChild(0).getChildCount()== 6){ //전역변수 case 3 Var IDENT[LITERAL] type일 경우
						int Size = Integer.parseInt(ctx.getChild(i).getChild(0).getChild(3).getText()); //배열의 크기 지정
 						GlobalKey = ctx.getChild(i).getChild(0).getChild(1).getText(); //배열의 이름 설정
						global_Var.put(GlobalKey,"1 "+globalNum); // list[~]에서 list의 위치 넣어주기
						System.out.println(SPACE+"sym 1 "+globalNum+" "+Size);  // Ucode
						fw.write(SPACE+"sym 1 "+globalNum+" "+Size);     // Ucode
						fw.newLine();
						globalNum = Size + globalNum; // 배열 사이즈만큼 할당
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}

	@Override
	public void exitProgram(MiniGoParser.ProgramContext ctx) { //프로그램이 모두 쓰여지고 종료될 때
		try {
			fw.write(SPACE+"bgn"+" "+(globalNum-1)); //전역변수 얼마나 썻나?
			fw.newLine();
			fw.write(SPACE+"ldp"); // main call 부분  *시작 부분
			fw.newLine();
			fw.write(SPACE+"call"+" main"); // main 부르며 시작
			fw.newLine();
			fw.write(SPACE+"end");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 위 내욜 출력하여 Console로 보여주기
		System.out.println(SPACE+"bgn"+" "+(globalNum-1)); //프로그램의 외부변수 크기
		System.out.println(SPACE+"ldp");  
		System.out.println(SPACE+"call"+" main"); //프로그램 시작
		System.out.println(SPACE+"end");
	}

	@Override
	public void enterFun_decl(MiniGoParser.Fun_declContext ctx) { //함수 처리
		localNum = 1;
		Local_Var = new HashMap<String,String>(); //현재 함수에서의 Local 변수들
		String make = ctx.getChild(1).getText(); //함수의 헤더부분? 을 추가해 주기 위해 생성(라벨)
		for(int i = make.length(); i < 11; i ++) { //for문으로 11칸으로 맞춰주기
			make += " ";
		}
		make += "proc "; //함수 시작 Header
		ForPrint.add(make); //넣어준 뒤 나중에 꺼내서 Header 작성(local변수의 크기를 아직 모르기 때문에)
		String param = SPACE+"sym ";//변수에 대한 정보 처리
		for(int i = 0; i < ctx.getChild(3).getChildCount(); i = i + 2 ) { //파라미터 가져오기
			Local_Var.put(ctx.getChild(3).getChild(i).getChild(0).getText(), "2 "+(localNum++)); //파라미터 넣기 넣기
			ForPrint.add(param+Local_Var.get(ctx.getChild(3).getChild(i).getChild(0).getText())+" 1");  //파라미터 추후 출력
		}

		for(int i = 1; i < ctx.getChild(6).getChildCount()-1; i++ ) { //Stmt에 대한 내용& 지역변수 생성 처리
			if(ctx.getChild(6).getChild(i) instanceof MiniGoParser.StmtContext) { //stmt인 경우
				DoStmt(ctx.getChild(6).getChild(i).getChild(0)); //Stmt를 수행하도록 한다.
			}
			else if(ctx.getChild(6).getChild(i) instanceof MiniGoParser.Local_declContext) {//지역 변수 생성
				if(ctx.getChild(6).getChild(i).getChildCount() == 3) { //지역 변수가 배열이 아닌경우
					Local_Var.put(ctx.getChild(6).getChild(i).getChild(1).getText(),"2 "+(localNum++)); //지역 변수 넣기
					ForPrint.add(param+Local_Var.get(ctx.getChild(6).getChild(i).getChild(1).getText())+" 1");
				}else { //배열인 경우 
					int Size = Integer.parseInt(ctx.getChild(6).getChild(i).getChild(3).getText()); //배열의 크기
					Local_Var.put(ctx.getChild(6).getChild(i).getChild(1).getText(),"2 "+localNum); // 지역변수
					ForPrint.add(param+Local_Var.get(ctx.getChild(6).getChild(i).getChild(1).getText())+" "+Size); //출력 
					localNum = Size + localNum; //지역 변수 Size 설정 배열의 크기 지정
				}		
			}
		}
	}

	@Override
	public void exitFun_decl(MiniGoParser.Fun_declContext ctx) { //함수가 끝날 때 출력 담당
		try {
			String Header = ForPrint.remove(0); //Header 쓰기
			Header += (localNum-1) +" 2 2"; //헤더 생성
			System.out.println(Header); // 헤더 출력
			fw.write(Header); //헤더 파일에 쓰기
			fw.newLine();
			while(!ForPrint.isEmpty()) { //Func의 내부 출력해주기
				System.out.println(ForPrint.get(0)); //모든 프린터 해주기
				fw.write(ForPrint.remove(0)); // 함수 내에서 모든 프린터 내용을 파일에 써준다.
				fw.newLine();
			}
			System.out.println(SPACE+ "ret"); //리턴
			fw.write(SPACE+ "ret");
			fw.newLine();
			System.out.println(SPACE+"end"); // end 써주기 func마지막
			fw.write(SPACE+"end");
			fw.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*Expr에 대한 저리를 하는 메소드*/
	public void DoExpr(ParseTree ctx) {
		if(isOper(ctx.getText()))      // 오퍼레이션인 경우
			Operation(ctx.getText());  // operation 처리 
		if(ctx.getChildCount() == 1) { // Expr에서 1인 경우 -> Terminal일 경우
			UseVar(ctx.getText()); // 변수 처리
		}
		else if(ctx.getChildCount() == 2) { // unarayop에 대한 처리
			String UnarayOP = OP_Unaray(ctx.getChild(0).getText()); // unarayop에 대한 명령어 처리
			String var = ctx.getChild(1).getText(); // unarayop을 처리해줄 데이터
			UseVar(var); // 사용해줄 변수 처리
			ForPrint.add(UnarayOP); // UnarayOP에 대해 처리 
		}
		else if(ctx.getChildCount() == 3) { //3인 경우
			if(ctx.getChild(1).getText().equals("=")) { // 만약 assign이라면
				DoExpr(ctx.getChild(2)); // assign할 값 가져오기 
				AssignVar(ctx.getChild(0).getText());  // assign을 받는 값 넣기
			}else if(ctx.getChild(0).getText().equals("(")) { // 만약, ( Expr )일 경우 
				DoExpr(ctx.getChild(1)); // 가운데 Expr 수행
			}
			else { //그 외에 연산들 + - * / 등등  gt lt 등등
				DoExpr(ctx.getChild(0));
				DoExpr(ctx.getChild(2));
				DoExpr(ctx.getChild(1)); // 연산자 처리
			}
		}
		else if(ctx.getChildCount() ==4) { // 4 인 경우
			if(ctx.getChild(1).getText().equals("[")) { //배열에 대한 처리 
				String var = ctx.getChild(0).getText(); //var 처리
				var = getVar(var);
				String offSet = getVar(ctx.getChild(2).getText()); //offSet처리
				StringTokenizer st = new StringTokenizer(var); // stringTokenizer로 쪼개기 offset만큼 이동 후 데이터 처리를 위해서
				String home = st.nextToken(); // 방 번호
				String size = st.nextToken(); // size 배열의 offset
				size = String.valueOf(Integer.parseInt(size)+Integer.parseInt(offSet)); //offset + list의 위치
				String set = home + " "+size; //위치 생성
				UseVar(set); // 해당위치 데이터 사용
			}else { //func에 대한 처리
				String funcName = ctx.getChild(0).getText(); //함수 이름 넣기
				ForPrint.add(SPACE+ "ldp"); // ldp 넣기
				for(int i = 0; i <  ctx.getChild(2).getChildCount();i = i+2) { // i를 0에서부터 시작하여, 짝수번째 값을 가져온다. args에 대한 값들을 가져옴
					DoExpr(ctx.getChild(2).getChild(i)); //Expr 수행
				}
				ForPrint.add(SPACE+"call "+funcName); // call에 대한 처리
			}
		}
		else if(ctx.getChildCount() ==6) { // 6인 경우 배열에 assign해주는 부분
			String var = ctx.getChild(0).getText();  // 해당 배열의 이름 
			var = getVar(var); // 해당 배열의 주소 가져오기
			String offSet = getVar(ctx.getChild(2).getText()); // 주소에서 얼마나 떨어졌는지 떨어진 거리 
			StringTokenizer st = new StringTokenizer(var); // Tokenizer로 쪼개어준다.
			String home = st.nextToken(); //토크나이저로 어디에 있는 변수인지에 대한 정보를 나눠준다.(1이면 전역 2이면 지역)
			String size = st.nextToken(); // 해당 배열의 초기 위치
			size = String.valueOf(Integer.parseInt(size)+Integer.parseInt(offSet)); //초기위치를 오프셋만큼 추가하여준다.
			String set = home + " "+size; // 새로운 위치 설정
			DoExpr(ctx.getChild(5)); // DoExpr로 수행
			AssignVar(set); // AssignVar을 이용하여 Assign 시켜준다.
		}
	}


	public void DoStmt(ParseTree ctx) { // Stmt를 처리하여주는 경우
		if(ctx instanceof MiniGoParser.If_stmtContext) { // 조건문에 대한 처리
			InBlock_Var = new HashMap<String,String>(); // InBlock_Var을 생성하여 주어 해당 블럭 내에 생성된 변수에 대하여 처리한다.
			DoExpr(ctx.getChild(1).getChild(1)); // 조건에 대하여 처리

			ForPrint.add(SPACE+"fjp "+"$$"+(loopNum++)); //만약 if문에 대한 조건이 옳지 않으면 탈출
			DoStmt(ctx.getChild(2)); // if문의 조건이 옳을 경우 DoStmt로 if가 true일 때 일 수행

			String makeIf = ("$$"+(loopNum-1)); // if문이 아닐 경우에 대한 위치 지정
			for(int i = makeIf.length(); i < 11; i ++) { // 11칸으로 맞춰주기
				makeIf += " ";
			}
			ForPrint.add(makeIf+"nop"); // nop로 label 생성

			if(ctx.getChildCount() == 5) { //else가 있는 경우
				DoStmt(ctx.getChild(4)); // else에 대한 처리
			}
			InBlock_Var = new HashMap<String,String>(); // InBlockVar을 이제 사용 안함.
		}else if(ctx instanceof MiniGoParser.For_stmtContext) { //For문에 대한 처리
			String makeFor = ("$$Loop"+(loopNum)); // for문 초기 위치 
			for(int i = makeFor.length(); i < 11; i ++) { // 11칸으로 맞춰주기
				makeFor += " ";
			}
			ForPrint.add(makeFor+"nop");

			InBlock_Var = new HashMap<String,String>();
			DoExpr(ctx.getChild(1)); //Expr로 조건 확인

			ForPrint.add(SPACE+"fjp "+"$$End"+(loopNum++)); //만약 조건이 틀린경우 바로 탈출
			makeFor = ("$$End"+(loopNum-1)); // for문 초기 위치
			for(int i = makeFor.length(); i < 11; i ++) { // 11칸으로 맞춰주기
				makeFor += " ";
			}
			DoStmt(ctx.getChild(2)); // for문이 만약 조건이 맞으면 수행
			ForPrint.add(SPACE+"ujp"+" "+"$$Loop"+(loopNum-1)); // ujp로 다시 루프 위로 
			ForPrint.add(makeFor+"nop"); //만약 for문의 조건이 맞지 않을 경우 동료될 위치
			InBlock_Var = new HashMap<String,String>(); // InBlockVar을 이제 사용 안함.	
		}
		else if(ctx instanceof MiniGoParser.Compound_stmtContext) { //Compound_stmtContext에 대한 수행
			for(int i = 1; i < ctx.getChildCount()-1; i++) { //for문을 이용해 괄호를 재외한 내부의 Compound_stmt에 대해 수행 될 내용 처리
				if(ctx.getChild(i) instanceof MiniGoParser.Local_declContext) { //만약 지역 변수 처리일 경우
					if(ctx.getChild(i).getChildCount() == 3) { // 배열이 아닌 경우
						String block_var = SPACE+"sym 2 "+(localNum++)+" 1";//변수에 대한 정보 처리
						InBlock_Var.put(ctx.getChild(i).getChild(1).getText(), (localNum-1)+" 1");
						ForPrint.add(block_var);
					}else { //배열인 경우
						int Size = Integer.parseInt(ctx.getChild(i).getChild(3).getText());
						InBlock_Var.put(ctx.getChild(i).getChild(1).getText(),"2 "+localNum);
						ForPrint.add(SPACE+"sym " + InBlock_Var.get(ctx.getChild(i).getChild(1).getText())+" "+Size);
						localNum = Size + localNum;
					}
				}else if(ctx.getChild(i) instanceof MiniGoParser.StmtContext) //만약 stmt에 대한 처리일 경우
					DoStmt(ctx.getChild(i).getChild(0)); // stmt 수행
			}	
		}
		else if(ctx instanceof MiniGoParser.Return_stmtContext) { //만약 Return_stmtContext일 경우
			if(ctx.getChildCount() ==1) { // return에 값이 없는 경우
				ForPrint.add(SPACE+"ret"); 
			}else if(ctx.getChildCount() ==2) { // return에 값이 있는 경우
				DoExpr(ctx.getChild(1));
				ForPrint.add(SPACE+"retv");
			}
		}
		else if(ctx instanceof MiniGoParser.Assign_stmtContext) { //Assign_stmtContext에 대한 처리
			if(ctx.getChildCount() == 9){ // VAR IDENT ',' IDENT type_spec '=' LITERAL ',' LITERAL
				String Var1 = ctx.getChild(1).getText(); // Var1에 대한 처리
				String Var2 =ctx.getChild(3).getText(); // Var2에 대한 처리
				Local_Var.put(Var1, "2 "+(localNum++)); //지역변수 생성 후 넣기	
				ForPrint.add(SPACE+"sym "+Local_Var.get(Var1)+" 1");
				Local_Var.put(Var2, "2 "+(localNum++)); //지역변수 생성 후 넣기
				ForPrint.add(SPACE+"sym "+Local_Var.get(Var2)+" 1");
				UseVar(ctx.getChild(6).getText()); // UseVar를 이용하여 첫번째 원소에 넣어줄 값 가져오기
				AssignVar(Var1); //Var1에 assign

				UseVar(ctx.getChild(8).getText()); // UseVar을 이용하여 두번째 원소에 넣어줄 값 가져오기
				AssignVar(Var2);//Var2에 assign
			}else if(ctx.getChildCount() == 5) { //VAR IDENT type_spec '=' expr
				String Var = ctx.getChild(1).getText();
				Local_Var.put(Var, "2 "+(localNum++)); //지역변수 생성 후 넣기
				ForPrint.add(SPACE+"sym "+Local_Var.get(Var)+" 1");
				UseVar(ctx.getChild(4).getText());
				AssignVar(Var);
			}else if(ctx.getChildCount() == 4) { //IDENT type_spec '=' expr
				String Var = ctx.getChild(0).getText();
				DoExpr(ctx.getChild(3));
				AssignVar(Var);
			}
		}
		else if(ctx instanceof MiniGoParser.Expr_stmtContext) { //만약 Expr_stmt일 경우
			DoExpr(ctx.getChild(0)); //DoExpr를 이용하여 수행
		}
	}

	public boolean isOper(String op) { //op인지 체크
		return (op.equals("<")|| op.equals(">")||op.equals("<=")||op.equals(">=")
				||op.equals("==")||op.equals("!=")||op.equals("+") || op.equals("-")
				||op.equals("*")||op.equals("/")||op.equals("%")||op.equals("and")||op.equals("or"));
	}

	public void Operation(String op) { //크다 작다와 같은 operation에 대한 처리
		switch(op) { 
		case "<":
			ForPrint.add(SPACE+"lt");
			break;
		case ">":
			ForPrint.add(SPACE+"gt");
			break;
		case ">=":
			ForPrint.add(SPACE+"ge");
			break;
		case "<=":
			ForPrint.add(SPACE+"le");
			break;
		case "==":
			ForPrint.add(SPACE+"eq");
			break;
		case "!=":
			ForPrint.add(SPACE+"ne");
			break;
		case "+":
			ForPrint.add(SPACE+"add");
			break;
		case "-":
			ForPrint.add(SPACE+"sub");
			break;
		case "*":
			ForPrint.add(SPACE+"mult");
			break;
		case "/":
			ForPrint.add(SPACE+"div");
			break;
		case "%":
			ForPrint.add(SPACE +"mod");
			break;
		case "and":
			ForPrint.add(SPACE +"add");
			break;
		case "or":
			ForPrint.add(SPACE +"or");
		}
	}

	private String OP_Unaray(String op) {//OP_Unaray에 대한 처리
		switch(op) {
		case "-":
			return SPACE+"neg";
		case "--": 
			return SPACE+"dec";
		case "++":
			return SPACE+"inc";
		case "!":
			return SPACE+"notop";
		default:
			return "";
		}
	}

	public void AssignVar(String var) { //assign될 변수 가져오기 순서는 InBlock->Local -> global, 없으면 그냥 해당 값
		if(this.InBlock_Var != null&&this.InBlock_Var.containsKey(var)) {
			ForPrint.add(SPACE+"str "+InBlock_Var.get(var));
		}
		else if(Local_Var.containsKey(var)) { // 1. 지역변수에 있다면, 찾은 값은 지역변수
			ForPrint.add(SPACE+"str "+Local_Var.get(var));
		}else if(this.global_Var.containsKey(var)) { // 2. 지역변수에 없는 경우 전역 변수에서 찾아준다.
			ForPrint.add(SPACE+"str "+global_Var.get(var));
		}else {
			ForPrint.add(SPACE+"str "+var);
		}
	}

	public void UseVar(String var) { //연산에 사용하는 변수 가져오기 순서는 InBlock->Local -> global, 없으면 그냥 해당 값
		if(this.InBlock_Var != null&&this.InBlock_Var.containsKey(var)) {
			ForPrint.add(SPACE+"lod "+InBlock_Var.get(var));
		}
		else if(Local_Var.containsKey(var)) { // 1. 지역변수에 있다면, 찾은 값은 지역변수
			ForPrint.add(SPACE+"lod "+Local_Var.get(var));
		}else if(this.global_Var.containsKey(var)) { // 2. 지역변수에 없는 경우 전역 변수에서 찾아준다.
			ForPrint.add(SPACE+"lod "+global_Var.get(var));
		}else {
			ForPrint.add(SPACE+"ldc "+var);
		}
	}

	//해당 변수의 주소 가져오기 순서는 InBlock->Local -> global, 없으면 그냥 해당 값
	public String getVar(String var) {
		if(this.InBlock_Var != null&&this.InBlock_Var.containsKey(var)) {
			return InBlock_Var.get(var);
		}
		else if(Local_Var.containsKey(var)) { // 1. 지역변수에 있다면, 찾은 값은 지역변수
			return Local_Var.get(var);
		}else if(this.global_Var.containsKey(var)) { // 2. 지역변수에 없는 경우 전역 변수에서 찾아준다.
			return global_Var.get(var);
		}else {
			return var;
		}
	}
}
