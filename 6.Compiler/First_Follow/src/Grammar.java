import java.util.ArrayList;
import java.util.HashMap;

public class Grammar {
	private HashMap<String, ArrayList<String>> table = new HashMap<String,ArrayList<String>>(); // 문법 저장
	private HashMap<String, ArrayList<String>> first = new HashMap<>(); //first
	private HashMap<String, ArrayList<String>> follow = new HashMap<>(); //follow
	private ArrayList<String> nonTerminal = new ArrayList<>(); // nonTerminal의 데이터
	private Boolean Change = true; // first Follow 구할 때, 변경되었나 확인하고 변경되면 다시 수행하도록 하기 위함
	public ArrayList<String> getNonTerminal(){return nonTerminal;} // NonTerminal 가져오기
	public HashMap<String, ArrayList<String>> getTable(){ return table; } //rule에 대한 정보 가져오기
	public HashMap<String, ArrayList<String>> getFirst() {return this.first;} // first 가져오기
	public HashMap<String, ArrayList<String>> getFollow() {return this.follow;} // follow 가져오기

	/*문법 추가시 사용*/
	public void AddRule(String nonterminal, String rule){
		//Nonterminal이 없는 경우 -> 문법을 처음 넣는 경우
		if(table.get(nonterminal) == null){
			nonTerminal.add(nonterminal); // 처음에 넣을 때, nonTerminal을 삽입
			ArrayList<String> list = new ArrayList<String>(); // map에 넣을 value 생성
			list.add(rule); // rule 추가하기
			table.put(nonterminal, list); // map에 넣기 key : nonterminal, value -> ArrayList(List에는 rule이 있음)
		} else { // Nonterminal이 있는 경우 -> 문법이 과거에 넣었던게 존재하는 경우
			table.get(nonterminal).add(rule);  // map에서 ArrayList가져와서, rule 추가하기
		}
	}

	public String getRule(String nonTerminal, int index) { //Rule 가져오기
		return table.get(nonTerminal).get(index);
	}

	public ArrayList<String> getRules(String nonTerminal) { //Rule이 들어있는 List 가져오기
		return table.get(nonTerminal);
	}

	/*isNonTerminal
	 * true : Nonterminal
	 * false : Terminal*/
	private boolean isNonTerminal(String data) {
		for(int i = 0; i < nonTerminal.size(); i ++) { // NonTerminal인 경우
			if(data.equals(nonTerminal.get(i)))
				return true;
		} 
		return false;
	}

	public void findFirst() {
		//모든 NonTerminal에 대하여 First 찾기
		for(int i = 0; i < nonTerminal.size(); i ++) {
			ArrayList<String> RuleList = this.getRules(nonTerminal.get(i));
			for(int j = 0; j < RuleList.size(); j++) {
				inputFirst_s1(nonTerminal.get(i),RuleList.get(j)); //inputFirst S_1 수행 -> NonTerminal에 대한 정보, Rule
			}
		}
		//First찾는 두번째 과정
		while(Change) {
			this.Change = false;
			for(int i = 0; i < nonTerminal.size(); i ++) {
				ArrayList<String> FirstList = this.first.get(nonTerminal.get(i));
				for(int j = 0; j < FirstList.size(); j++) {
					inputFirst_s2(nonTerminal.get(i),FirstList.get(j),j); //inputFirst S_2수행 -> NonTerminal에 대한 정보, 각각의 Rule, Rule의 index
				}
			}
		}
		viewFirst(); //first 보여주기
	}

	/*Step 1 -> NonTerminal or Terminal 넣기
	 * First에 삽입되는 데이터
	 * NonTerminal : Rule
	 * Terminal : Terminal data
	 * */
	private void inputFirst_s1(String NonTerminal, String Rule){
		String first = "";
		if(((Rule.length() > 1)&&isNonTerminal(Rule.substring(0,2)))|| isNonTerminal(Rule.substring(0,1))) {
			first = Rule; //NonTerminal인 경우
		}				
		else {
			if(((Rule.length() > 1)&&!isNonTerminal(Rule.substring(1,2)))){	
				first = Rule.substring(0,2); //Terminal인 경우 id
			}else {
				first = Rule.substring(0,1); // Terminal인 경우 id x
			}
		}
		//NonTerminal을 처음 넣는 경우(First에)
		if(this.first.get(NonTerminal) == null) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(first);
			this.first.put(NonTerminal, list);
		}
		else {
			this.first.get(NonTerminal).add(first);
		}
	}


	/*Step 2 -> First 구하기*/
	private void inputFirst_s2(String NonTerminal, String Rule, int index) {
		int i,j;
		if(((Rule.length() > 1)&&isNonTerminal(Rule.substring(0,2)))|| isNonTerminal(Rule.substring(0,1))) { //NonTerminal인가?
			ArrayList<String> inputgetFirst_Non; // 가져올 First First 추가하기 위함
			String otherRule;
			if(isNonTerminal(Rule.substring(0, 2))) { // NonTerminal이 '를 포함하는가?
				inputgetFirst_Non = first.get(Rule.substring(0,2));
				otherRule = Rule.substring(2); // 문법 뒤부분
			}
			else { // 포함하지 않는경우
				inputgetFirst_Non = first.get(Rule.substring(0,1)); 
				otherRule = Rule.substring(1);
			}
			first.get(NonTerminal).remove(index); //사용된 문법은 일단 지우기 -> NonTerminal ~~ 이므로
			for(i = 0; i < inputgetFirst_Non.size(); i++) {
				if(inputgetFirst_Non.get(i).equals("e")) { // null 가진경우 -> RingSum 수행
					this.RingSumFirst(first.get(NonTerminal), otherRule, index);
				}
				else { // 삽입
					for(j = 0; j < first.get(NonTerminal).size(); j++) { // 중복 여부 체크
						if(inputgetFirst_Non.get(i).equals(first.get(NonTerminal).get(j)))
							break;
					}
					if(j == first.get(NonTerminal).size()) { 
						this.Change = true;
						first.get(NonTerminal).add(inputgetFirst_Non.get(i));
					}
				}
			}
		}
	}

	/*RingSum수행*/
	private void RingSumFirst(ArrayList<String> input, String otherRule, int index) {
		if(otherRule == "") { // 만약 ringsum 수행하는데 뒤 문법이 없다면, first에 null 추가
			int i;
			for(i = 0; i < input.size(); i++) { //중복여부 확인후 삽입
				if(input.get(i).equals("e"))
					break;
			}
			if(i == input.size())
				input.add("e");
		}
		else { // ringsume 수행
			if(((otherRule.length() > 1)&&isNonTerminal(otherRule.substring(0,2)))|| isNonTerminal(otherRule.substring(0,1))) {
				input.add(otherRule); // NonTerminal
			}else {
				input.add(otherRule.substring(0,1)); // Terminal
			}
		}
	}

	/*Follow 찾기*/
	public void findFollow() {
		for(String key : table.keySet()) {
			ArrayList<String> list = new ArrayList<String>();
			if(key.equals("E")) { // E가 시작이므로
				list.add("$"); // 처음에  $넣기
			}
			follow.put(key, list);	
			inputFollow_s1(key); // key값
		}
		//완료 -> follow에는 각 NonTerminal에 대한, Terminal값과 NonTerminal + ~ + lhs

		this.Change = true;
		while(Change) {
			this.Change = false;
			for(String key : follow.keySet()) {
				for(int i = 0; i < follow.get(key).size(); i++)
					inputFollow_s2(key, follow.get(key).get(i),i);  //table에 대한 NonTerminal과 그에 따른 규칙들과 index
			}
		}
		viewFollow();
	}

	private void inputFollow_s1(String NonTerminal) { // NonTerminal : table의 key값들 -> NonTerminal
		for(String key : table.keySet()) { //모든 rule에 대하여 찾기
			for(String rule : table.get(key)) {
				rule = rule + "!";
				String[] Find = rule.split(NonTerminal);
				if(Find.length > 1) {
					if(!( String.valueOf(Find[1].charAt(0)).equals("'")) && !Find[1].equals("!")) {
						Find[1] = Find[1].substring(0, Find[1].length()-1) + key;
						sub_Follow_s1(NonTerminal,Find[1]); // ~ + lhs 하여 넣기
					}
					else if(Find[1].equals("!")){
						sub_Follow_s1(NonTerminal,key); // lhs 넣기
					}
				}
			}
		}
	}


	private void sub_Follow_s1(String Nonterminal, String Rule) { // Nonterminal : 삽입될 곳, Rule : 규칙
		String follow = "";
		if(((Rule.length() > 1)&&isNonTerminal(Rule.substring(0,2)))|| isNonTerminal(Rule.substring(0,1))) { // 규칙의 처음 -> NonTermiinal의 경우
			follow = Rule; //NonTerminal인 경우
		}				
		else { // Terminal인 경우
			if(((Rule.length() > 1)&&!isNonTerminal(Rule.substring(1,2)))){	
				follow = Rule.substring(0,2); // Terminal이 id
			}else {
				follow = Rule.substring(0,1); // Terminal인 경우 id x
			}
		}
		this.follow.get(Nonterminal).add(follow);
	}


	private void inputFollow_s2(String Nonterminal, String Rule, int index) { //follow 두번째 단계
		int i,j;
		if(((Rule.length() > 1)&&isNonTerminal(Rule.substring(0,2)))|| isNonTerminal(Rule.substring(0,1))) { //NonTerminal인 경우
			ArrayList<String> inputgetFirst_Non; // 가져올 First
			String getNon = "";
			String otherRule = "";
			if((Rule.length() > 1)&&isNonTerminal(Rule.substring(0,2))) { // nonTerminal이 ' 포함하는경우
				getNon = Rule.substring(0,2); // NonTerminal 뽑아내기
				inputgetFirst_Non = first.get(getNon); // first 가져오기
				otherRule = Rule.substring(2); // Rule 이후 otherRule 가져오기
			}
			else { // nonTerminal이  '포함하지 않는 경우
				getNon = Rule.substring(0,1);
				inputgetFirst_Non = first.get(getNon);
				otherRule = Rule.substring(1);
			}

			if(otherRule.equals("")) { // 맨뒤에 있는 경우 
				follow.get(Nonterminal).remove(index);
				this.getFollow(follow.get(Nonterminal), getNon); // Follow 추가하기 위해 수행
			}
			else {
				follow.get(Nonterminal).remove(index);
				for(i = 0; i < inputgetFirst_Non.size(); i++) { //for문 이용해 수행,
					if(inputgetFirst_Non.get(i).equals("e")) {	// null있는 경우
						this.DoNullFollow(follow.get(Nonterminal), otherRule);
					}
					else {
						for(j = 0; j < follow.get(Nonterminal).size(); j++) { // 중복 여부 체크
							if(inputgetFirst_Non.get(i).equals(follow.get(Nonterminal).get(j)))
								break;
						}
						if(j == follow.get(Nonterminal).size()) { 
							this.Change = true;
							follow.get(Nonterminal).add(inputgetFirst_Non.get(i)); // 삽입
						}
					}
				}
			}
		}
	}

	//input - > 추가하는 List, data -> 추가하고자하는 Follow의 NonTerminal
	private void getFollow(ArrayList<String> input , String data) { // A -> ~B의 경우 B에 A의 Follow 추가한다.  
		ArrayList<String> aList = follow.get(data);
		int i,j;
		for(i = 0; i < aList.size(); i ++) {
			for(j = 0; j < input.size(); j ++)      //중복여부 체크
				if(aList.get(i).equals(input.get(j)))
					break;
			if(j == input.size()) {
				input.add(aList.get(i));
				this.Change = true;
			}
		}
	}

	private void DoNullFollow(ArrayList<String> input, String otherRule) { // 뒤에가 Null인 경우
		if(((otherRule.length() > 1)&&isNonTerminal(otherRule.substring(0,2)))|| isNonTerminal(otherRule.substring(0,1))) {
			input.add(otherRule); // 만약, nonTerminal이면
		}else {
			input.add(otherRule.substring(0,1)); // Terminal이면
		}
	}

	//First보여주기
	private void viewFirst() {
		String result = "";
		for(String key : first.keySet()) {
			result = ("First("+(key) + ") : {");
			for(String rule : first.get(key)) {
				result += (rule+", ");
			}
			System.out.print(result.substring(0,result.length()-2));
			if(first.get(key).isEmpty())
				System.out.print("{");
			System.out.println("}");
		}
	}

	//Follow 보여주기
	private void viewFollow() {
		String result = "";
		for(String key : follow.keySet()) {
			result = ("Follow("+(key) + ") : {");
			for(String rule : follow.get(key)) {
				result += (rule+", ");
			}
			System.out.print(result.substring(0,result.length()-2));
			if(follow.get(key).isEmpty())
				System.out.print("{");
			System.out.println("}");
		}
	}

	//Grammer 보여주기
	public void viewGrammar(){
		int index = 1;
		for(String lhs : table.keySet()){
			for(String rhs : table.get(lhs)){
				System.out.println((index++) + "." + lhs + " ->  "+ rhs);
			}
		}
	}
}
