import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class RecursiveDescentParser {
	private int index = 0;  //순서 체크를 위해서 
	private String nowToken; //보여지는 입력 스트링
	private StringTokenizer input; // 입력스트링 쪼개는 Tokenizer
	private String StackData; // 보여지는 Stack Data
	private HashMap<String, ArrayList<String>> first = new HashMap<>(); //first
	private HashMap<String, ArrayList<String>> follow = new HashMap<>();  // follow
	private HashMap<String, ArrayList<String>> table = new HashMap<String,ArrayList<String>>(); // table
	private HashMap<String, ArrayList<String>> PrintMap = new HashMap<>(); // 출력을 위해 어떤 lhs가 어떤 rhs로 되었는지 넣어주기
	private ArrayList<String> nonTerminal = new ArrayList<>(); // nonTerminal 정보
	private ArrayList<ArrayList<String>> LookaHead = new ArrayList<ArrayList<String>>(); // LookaHead 정보 -> 각각의 문법에 하나의 ArrayList가 들어가있는 ArrayList
	private Stack<String> DataStack; // DataStack -> Stack
	
	//생성자 -> 수행
	public RecursiveDescentParser(Grammar g, String input){ 
		first = g.getFirst(); //first
		follow = g.getFollow(); // follow
		this.table = g.getTable(); // table
		nonTerminal = g.getNonTerminal(); // nonTerminal
		this.input = new StringTokenizer(input+" $"); // inputString -> 끝에 $ 추가
		MakeLookaHead(); // LookaHead 생성
		ForPrint(); // Print전에 수행
		advanced(); // advanced를 이용하여, 입력스트링 하나 보기
		StartStack(); // Stack사용할 준비 $와 시작 NonTerminal E 삽입
		DoDescentParser(); //DoDescentParser -> DescentParser 수행 
		DoPrint(); // DoPrint -> 출력
	}
	
	//LookaHead 생성
	private void MakeLookaHead() {
		for(String key : table.keySet()) {  //준비 ArrayList 생성해서 삽입
			for(int i = 0; i < table.get(key).size(); i++) {
				ArrayList<String> inputData = new ArrayList<String>(); // 0번째 값은 무조건 변형 전 1번째 값은 무조건 변형 결과 A -> B ... A는 0 B는 1에 위치 
				inputData.add(key);
				inputData.add(table.get(key).get(i));
				LookaHead.add(inputData); // LookaHead -> 내부에 또 List -> lhs 0,  rhs 1
			}
		}
		for(int i = 0; i< LookaHead.size(); i ++) { //LookaHead 만들기
			ArrayList<String> getData = LookaHead.get(i); 
			String rule = getData.get(1);
			String OtherRule;
			if(rule.length() > 1 && isNonTerminal(rule.substring(0, 2))) { // NonTerminal인 경우 ' 있는
				OtherRule = rule.substring(2);
				rule = rule.substring(0,2);
				for(int j = 0; j< first.get(rule).size(); j ++) {
					if(first.get(rule).get(j).equals("e")) { // null이 있다면
						RingSum(LookaHead.get(i),OtherRule); // RingSum 수행 
					}else {
						LookaHead.get(i).add(first.get(rule).get(j)); // 삽입
					}
				}	
			}else if(isNonTerminal(rule.substring(0,1))) {  //NonTerminal ' 없는 
				OtherRule = rule.substring(1);
				rule = rule.substring(0,1);
				for(int j = 0; j< first.get(rule).size(); j ++) {
					if(first.get(rule).get(j).equals("e")) { // Null인 경우
						RingSum(LookaHead.get(i),OtherRule); // RungSum 수행
					}else {
						LookaHead.get(i).add(first.get(rule).get(j)); // 삽입
					}
				}	
			}else {// Terminal이라면
				if(rule.equals("e")) { // null인 경우
					RingSum(LookaHead.get(i),""); // RingSume 수행
				}
				else if(rule.length() > 1 && !isNonTerminal(rule.substring(1,2))){	
					LookaHead.get(i).add(rule.substring(0,2));
				}else {
					LookaHead.get(i).add(rule.substring(0,1));
				}
			}
		}
	}
	
	private void RingSum(ArrayList<String> input, String otherRule) { // RingSum 수행
		if(otherRule.equals("")) { // otherRule이 없는 경우 Follow  추가
			ArrayList<String> FollowData = this.follow.get(input.get(0));
			for(int i = 0; i < FollowData.size(); i ++) {
				input.add(FollowData.get(i));
			}
		} // 그 외에 경우
		else {
			String OtherRule;
			String rule = otherRule;
			if(rule.length() > 1 && isNonTerminal(rule.substring(0, 2))) {  // NonTerminal인 경우 ' 포함
				OtherRule = rule.substring(2);
				rule = rule.substring(0,2);
				for(int j = 0; j< first.get(rule).size(); j ++) {
					if(first.get(rule).get(j).equals("e")) {
						RingSum(input,OtherRule);  //RingSum 수행
					}else {
						input.add(first.get(rule).get(j)); // input을 이용하여 add
					}
				}	
			}else if(isNonTerminal(rule.substring(0,1))) { // NonTerminal인 경우 ' 미포함
				OtherRule = rule.substring(1);
				rule = rule.substring(0,1);
				for(int j = 0; j< first.get(rule).size(); j ++) {
					if(first.get(rule).get(j).equals("e")) { // null인 경우
						RingSum(input,OtherRule); // RingSum 수행
					}else {
						input.add(first.get(rule).get(j));
					}
				}	
			}else { // Terminal인 경우 
				if(rule.length() > 1){	
					input.add(rule.substring(0,2));
				}else {
					input.add(rule.substring(0,1));
				}
			}
		}
	}
	
	private void ForPrint() { // print 준비 과정 -> HashMap 데이터 정리
		for(int i = 0; i < this.nonTerminal.size(); i++) { // print 준비하기 위해 HashMap에 데이터 추가
			ArrayList<String> input = new ArrayList<String>();
			PrintMap.put(nonTerminal.get(i), input);
		}
	}

	private void DoPrint() { // 출력
		String Data = "E"; // 초기 시작 
		String newData = "";
		System.out.println("E");
		System.out.println(newData = PrintMap.get(Data).remove(0).substring(1));
		while(!(Data = newData).equals("")) { // newData가 더이상 생기지 않을 때 까지 수행
			newData = "";
			while(!Data.equals("")) { // Data 쪼개기
				int data = 0;
				String printData;
				if(Data.length() > 1 && isNonTerminal(Data.substring(0,2))) {  //만약, NonTerminal이라면
					String checkData = PrintMap.get(Data.substring(0,2)).get(0); 
					for(int i = 1; i < PrintMap.get(Data.substring(0,2)).size(); i++) { // 해당 NonTerminal에 서 사용된 규칙의 먼저쓰인 규칙 찾기
						if(checkData.charAt(0) > PrintMap.get(Data.substring(0,2)).get(i).charAt(0)) {
							checkData = PrintMap.get(Data.substring(0,2)).get(i);
							data = i;
						}
					}
					printData = PrintMap.get(Data.substring(0,2)).remove(data).substring(1); // 그 규칙 찾아서 사용하기 위해 꺼내기(삭제로 꺼내서 중복사용 방지)
					newData += printData; // 다음 입력 준비
					System.out.print(printData+" "); // 출력
					Data = Data.substring(2); // 사용한 값 지우기
	
				}else if(isNonTerminal(Data.substring(0,1))) { // 위와 동일 but NonTerminal 값이 ' 없는 NonTerminal
					String checkData = PrintMap.get(Data.substring(0,1)).get(0);
					for(int i = 1; i < PrintMap.get(Data.substring(0,1)).size(); i++) {
						if(checkData.charAt(0) > PrintMap.get(Data.substring(0,1)).get(i).charAt(0)) {
							checkData = PrintMap.get(Data.substring(0,1)).get(i);
							data = i;
						}
					}
					printData = PrintMap.get(Data.substring(0,1)).remove(data).substring(1);
					newData+= printData;
					System.out.print(printData+" ");
					Data = Data.substring(1);
				}else { // Terminal인 경우 그냥 버려준다. Rule을 출력해주므로 따라서, Terminal은 그대로 나옴 -> NonTerminal의 변경 내용만 보여주면 됨 
					if(Data.length() > 1 && !isNonTerminal(Data.substring(1,2))){	
						Data = Data.substring(2);
					}else {
						Data = Data.substring(1);
					}
				}
			}
			System.out.println("");
		}
	}
	
	private void StartStack() { // 파싱 시작전 스택 준비
		DataStack = new Stack<String>();
		DataStack.push("$");
		DataStack.push("E"); // 처음 시작
		this.StackPop();
	}

	private void DoDescentParser() { // Parsing하기
		System.out.println("Start : E");
		while(!accept()) {
			if(this.nowToken.equals(StackData)) { // 만약, StackData와 nowToken이 일치하는 경우 Pop & advance
				advanced();
				this.StackPop();
			}
			else if(isNonTerminal(StackData)) { // 만약, NonTerminal을 만난 경우 -> expand
				String lhs = StackData;
				String expandData = expand();
				PrintMap.get(lhs).add(((index++)%10)+expandData); //출력 위해서 사용 우선순위 index로 제공-> %10으로 하여 무조건 0번째 index에 우선순위 삽입하도록
				System.out.println("Expand : " + lhs +"->"+ expandData); // Expand 어떠한 룰 사용했는 지 보여주기
			}
			else if(this.StackData.equals("e")) { // e인 경우 그냥 버려주기 (null이라서)
				this.StackPop();
			}
		}
	}

	private String expand() { // Expand
		int i;
		String Data = "";
		String ReturnData = "FAIL";
		for(i = 0; i < this.LookaHead.size(); i++) { // LookaHead보며 적절한 문법 찾기
			ArrayList<String> aList = LookaHead.get(i);
			if(aList.get(0).equals(StackData)) {
				for(int j = 2; j < aList.size(); j ++) {
					if(aList.get(j).equals(nowToken)) {
						Data = aList.get(1);
						ReturnData = aList.get(1);
					}
				}
			}
		}
		while(!Data.equals("")) { // 해당 문법 쪼개서 거꾸로 Stack에 넣어주기
			if(Data.length() > 1 && isNonTerminal(Data.substring(Data.length()-2,Data.length()))) {  
				DataStack.push(Data.substring(Data.length()-2,Data.length()));
				Data = Data.substring(0,Data.length()-2);
			}else if(isNonTerminal(Data.substring(Data.length()-1,Data.length()))) {
				DataStack.push(Data.substring(Data.length()-1,Data.length()));
				Data = Data.substring(0,Data.length()-1);
			}else {
				if(Data.length() > 1 && !isNonTerminal(Data.substring(Data.length()-2,Data.length()-1))){	
					DataStack.push(Data.substring(Data.length()-2,Data.length()));
					Data = Data.substring(0,Data.length()-2);
				}else {
					DataStack.push(Data.substring(Data.length()-1,Data.length()));
					Data = Data.substring(0,Data.length()-1);
				}
			}
		}
		this.StackPop(); // 다음 Stack에서 쓰일 데이터 가져오기
		return ReturnData;
	}

	private String advanced() { // advanced 수행
		nowToken = this.input.nextToken();
		return nowToken;
	}
	
	private String StackPop() { //Stack Pop
		StackData = DataStack.pop();
		return StackData;
	}

	private boolean accept() { // accept확인
		return (this.StackData.equals("$")&&this.nowToken.equals("$"));
	}
	
	private boolean isNonTerminal(String data) { // NonTerminal인지 확인하여주기
		for(int i = 0; i < nonTerminal.size(); i ++) { // NonTerminal인 경우
			if(data.equals(nonTerminal.get(i)))
				return true;
		}
		return false;
	}
}
