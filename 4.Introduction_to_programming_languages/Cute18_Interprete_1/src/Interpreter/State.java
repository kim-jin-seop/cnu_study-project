package Interpreter;


import static Interpreter.TokenType.ID;
import static Interpreter.TokenType.INT;
import static Interpreter.TokenType.*;
import static Interpreter.TransitionOutput.GOTO_ACCEPT_ID;
import static Interpreter.TransitionOutput.GOTO_ACCEPT_INT;
import static Interpreter.TransitionOutput.GOTO_EOS;
import static Interpreter.TransitionOutput.GOTO_FAILED;
import static Interpreter.TransitionOutput.GOTO_MATCHED;
import static Interpreter.TransitionOutput.GOTO_SIGN;
import static Interpreter.TransitionOutput.GOTO_START; 
import static Interpreter.TransitionOutput.GOTO_BOOL;
import static Interpreter.TransitionOutput.GOTO_SPECIAL;

/*상태를 가져오기
 * START -> 처음 시작할 때 위치
 * SPECIAL -> 특수 문자가 들어왔을 때
 * ACCEPT_ID -> ID가 들어올 때 처리 (특수한 문구는 예외처리를 통하여 해결)
 * ACCEPT_INT ->DIGIT이 들어올 때, SIGN 다음 DIGIT이 올 때
 * SIGN -> SIGN이 올 때
 * FAILED -> 실패 할 때
 * MATCHED -> 성공
 * 등등*/
enum State {  //상태
	START {  //시작 일 때 부터
		@Override
		public TransitionOutput transit(ScanContext context) { 
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
			case LETTER: //문자열이 들어올 때
				context.append(v);
				return GOTO_ACCEPT_ID;
			case SIGN:  // +/-가 들어올 때
				context.append(v); // +,-
				context.getCharStream().pushBack(v);
				/*pushBack을 하는 이유는 만약 단일 문자로 들어왔을 때, GOTO_SIGN을 하면 다음 위치를  읽어와서 빈공간을 가져오게 되고 에러 발생
				 * 따라서 pushBack을 이용하여 데이터를 넣어놓고 다시 사용할 수 있게 한다.*/
				return GOTO_SIGN;  //SIGN으로 이동
			case DIGIT: //0~9의 데이터가 들어올 때
				context.append(v);
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR: //위의 SIGN과 사용법 유사
				context.append(v);
				context.getCharStream().pushBack(v);
				return GOTO_SPECIAL; 
			case SHARP: //TRUE FALSE를 확인하여주기위하여 #이 들어올 때
				context.append(v);
				return GOTO_BOOL;
			case WS:
				return GOTO_START;
			case END_OF_STREAM:
				return GOTO_EOS;
			default:
				throw new AssertionError();
			}
		}
	},
	/*특수문자가 삽입될 때
	 * ch에 들어오는 데이터는 기존에 들어온 특수문자의 Stream
	 * v에 들어온 데이터는 특수문자
	 * nextch는 다음 위치 즉 다음 데이터가 있는지 확인하고 상태 변화*/
	SPECIAL{
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // pushback을 하고 실행하므로 기존의 특수문자 가져옴.
			char v = ch.value();
			Char nextch = context.getCharStream().nextChar();  //다음 위치 읽기.
			switch ( nextch.type() ) {
			case LETTER:
			case DIGIT:
				return GOTO_FAILED;
			case SPECIAL_CHAR:
				return GOTO_FAILED;
			case WS:
			case END_OF_STREAM:
				return GOTO_MATCHED(fromSpecialCharactor(v),context.getLexime());  //만약 마지막이면 그 문자에 대한 정보 매칭
				/*fromSpecialCharator는 글자가 들어왔을 때, 그 데이터가 어떠한 특수문자인지 확인*/
			default:
				throw new AssertionError();
			}
		}
		
	},
	ACCEPT_ID {  //LETTER가 들어올 때.
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
			/*LETTER이든 DIGIT이든 ID이므로 아래처럼 구현*/
			case LETTER:
			case DIGIT:
				context.append(v);
				return GOTO_ACCEPT_ID;
			case SPECIAL_CHAR:
				return GOTO_FAILED;
			case WS:
			case END_OF_STREAM:
				String result = context.getLexime();
				return GOTO_MATCHED(Token.ofName(result).type(),result);
				/*최종적으로 ID라는것을 확인하였을 때, 마지막으로 특수한 경우인지 확인하기 위하여 ofName 메소드 사용.
				 * ofName은 특수한경우 특수한 값을 특수한 경우가 아니면 ID를 반환함.
				 * result를 저장하는 이유는 getLexime을 사용하면 데이터를 날리기 때문에 출력을 하기 위해 저장*/
			case Q: // ?가 들어왔을 때
				context.append(v);
				result = context.getLexime(); //위의 특수 문자의 경우와 유사.
				return Token.ofName(result).type() == ID ? GOTO_FAILED : GOTO_MATCHED(Token.ofName(result).type(),result); 
				/*삼항연산자를 이용하여 Q에 대한 예외처리. ?가 들어와 ofName으로 읽어왔을 때, 그 값이 ID이면 특수한 경우가 아니고 ?가 들어왔으므로  FAIL */
			default:
				throw new AssertionError();
			}
		}
	},
	ACCEPT_INT { //DIGIT이 들어올 때
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			switch ( ch.type() ) {
			case LETTER:
				return GOTO_FAILED;
			case DIGIT:
				context.append(ch.value());
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR:
				return GOTO_FAILED;
			case WS:
			case END_OF_STREAM:
				return GOTO_MATCHED(INT, context.getLexime());
			default:
				throw new AssertionError();
			}
		}
	},
	SIGN { //SIGN 즉 Digit이 들어올 때, SPECIAL과 유사. but DIGIT이 올때만 다름.
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // pushback을 하고 실행하므로 기존의 특수문자 가져옴.
			char v = ch.value();
			Char nextch = context.getCharStream().nextChar();  //다음 위치 읽기.
			switch ( nextch.type() ) {
			case LETTER:
				return GOTO_FAILED;
			case DIGIT: //DIGIT이 들어오면, INT로 바뀌므로 ACCEPT_INT 사용해서 INT로 수행.
				context.append(nextch.value());
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR:
				return GOTO_FAILED;
			case WS:
			case END_OF_STREAM:
				return GOTO_MATCHED(fromSpecialCharactor(v),context.getLexime()); 
			default:
				throw new AssertionError();
			}
		}
	},
	MATCHED { // 끝났을 때
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	FAILED{ // 실패 했을 때
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	EOS {
		@Override
		public TransitionOutput transit(ScanContext context) {
			return GOTO_EOS;
		}
	},
	/*TRUE, FALSE를 설정하기 위해 만든 enum
	 * 한글자를 가져올 때 만약 LETTER가 아니면 오류.
	 * LETTER일 경우 데이터를 하나 더 읽어온다.
	 * 만약 그 읽어온 데이터가 끝이라면, 즉 #T #F라면
	 * 그에 맞는 TRUE FALSE.*/
	BOOL{
		@Override
		TransitionOutput transit(ScanContext context) {
			Char ch =context.getCharStream().nextChar();
			char v = ch.value();
			switch(ch.type()) {
			case LETTER :
				Char nextch = context.getCharStream().nextChar();
				switch(nextch.type()) {
				case END_OF_STREAM:
				case WS:
					context.append(v);
					if(v == 'T') {
						return GOTO_MATCHED(TRUE,context.getLexime());
					}
					else if(v== 'F') {
						return GOTO_MATCHED(FALSE,context.getLexime());
					}
				}
				default:
					return GOTO_FAILED;
			}
		}
		
	};

	abstract TransitionOutput transit(ScanContext context);
}
