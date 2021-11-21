package HW_03;
import static HW_03.TokenType.FALSE;
import static HW_03.TokenType.INT;
import static HW_03.TokenType.SHARP;
import static HW_03.TokenType.TRUE;
import static HW_03.TransitionOutput.GOTO_ACCEPT_FALSE;
import static HW_03.TransitionOutput.GOTO_ACCEPT_ID;
import static HW_03.TransitionOutput.GOTO_ACCEPT_INT;
import static HW_03.TransitionOutput.GOTO_ACCEPT_SHARP;
import static HW_03.TransitionOutput.GOTO_ACCEPT_TRUE;
import static HW_03.TransitionOutput.GOTO_EOS;
import static HW_03.TransitionOutput.GOTO_FAILED;
import static HW_03.TransitionOutput.GOTO_MATCHED;
import static HW_03.TransitionOutput.GOTO_SIGN;
import static HW_03.TransitionOutput.GOTO_START;

import HW_03.Token;


enum State {//State 클래스 상태 추가 (#T/F)	상태 클래스
	START {//START 노드의 상태를 나타내는 enum
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();//문자 하나를 읽어옴
			char v = ch.value();	//ch는 다음에 이어지는 character 문자 하나.
			switch ( ch.type() ) { 
				case LETTER:
					context.append(v);
					return GOTO_ACCEPT_ID;//시작을 문자로 할경우 식별자로 판단하고 진행하게 됨
				case DIGIT:
					context.append(v);
					return GOTO_ACCEPT_INT;//숫자가 시작될 경우 INT로 가정하고 accept 과정을 거침
				case SPECIAL_CHAR:		//+ or - 그외에 기호들 
					context.append(v);//기호를 append()
					context.getCharStream().pushBack(v);//해당 기호를 pushback()을 사용해서 저장
					return GOTO_SIGN;
				case WS:				//엔터랑 스페이스는 ws로 처리된다.
					return GOTO_START;	//다음 값이 공백이면 GOTO_START로 다시 돌아간다.
				case END_OF_STREAM:
					return GOTO_EOS;//파일의 끝이면 EOS로 진행
				case SHARP://# 일 경우의 상태
					context.append(v);
					return GOTO_ACCEPT_SHARP;
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_ID { //ID 는 identifier를 의미
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
				case LETTER://여기서 keywords인지 아닌지 확인
				case DIGIT:
					context.append(v);
					return GOTO_ACCEPT_ID;		//id 사이에 숫자가 나온다면 identifier로 간주하고 진행
				case SPECIAL_CHAR:
					return GOTO_FAILED;
				case WS:
				case END_OF_STREAM:
					return GOTO_MATCHED(Token.ofName(context.getLexime()));
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_INT {// ------>integer 를 accept
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			switch ( ch.type() ) {
				case LETTER:
					return GOTO_FAILED;	//d 뒤에 i가 온다면 fail
				case DIGIT:
					context.append(ch.value());//d뒤에 d가 온다면 go
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
	SIGN {//기호 및 부호 및 다른 기호도 포함
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();	//v는 이전 기호 .
			Char ch2 = context.getCharStream().nextChar();
			char v2 = ch2.value();	//v2는 DIGIT이라고 가정
			switch ( ch2.type() ) {
				case LETTER:	//기호 뒤에 문자가 나오면 fail
					return GOTO_FAILED;
				case DIGIT:
					context.append(v2);//오류가 있는 input은 없다고 가정
					return GOTO_ACCEPT_INT;//SIGN 부호 뒤에 INT 가 나오면 APPEND로 붙여주고 INT의 ACCEPT 과정을 간다.
				case WS:
				case END_OF_STREAM://기호 단 하나만 나왓을경우.
					return GOTO_MATCHED(TokenType.fromSpecialCharcter(v) ,context.getLexime());
				default:
					throw new AssertionError();
			}
		}
	},
	MATCHED {
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	FAILED{
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	EOS {//End of Stream
		@Override
		public TransitionOutput transit(ScanContext context) {
			return GOTO_EOS;
		}
	},

	ACCEPT_TRUE {//#T 인 토큰을 accept 하는 enum
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			switch ( ch.type() ) {
				case LETTER:
				case DIGIT:
					return GOTO_FAILED;//#T 다음에 문자나 숫자가 오게된다면 FAILED!
				case WS:
				case END_OF_STREAM:
					return GOTO_MATCHED(TRUE, context.getLexime());//이제 더이상 문자가 오지 않는다면 TRUE라는 토큰타입을 가지고 반환
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_FALSE {//TRUE와 마찬가지로 진행
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			switch ( ch.type() ) {
				case LETTER:
				case DIGIT:
					return GOTO_FAILED;
				case WS:
				case END_OF_STREAM:
					return GOTO_MATCHED(FALSE, context.getLexime());
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_SHARP {//# 하나인 경우에 대해서의 enum
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
				case LETTER:
					if(v == 'T') {					//#뒤에 문자 T가 온다면 append로 붙이고 goto_accept_true
						context.append(v);
						return GOTO_ACCEPT_TRUE;
					} else if(v == 'F') {			//#뒤에 문자 F가 온다면 append로 붙이고 goto_accept_true
						context.append(v);
						return GOTO_ACCEPT_FALSE;
					} else
						return GOTO_FAILED;
				case DIGIT:
					return GOTO_FAILED;
				case WS:
				case END_OF_STREAM:
					return GOTO_MATCHED(SHARP, context.getLexime());
				default:
					throw new AssertionError();
			}
		}
	};

	abstract TransitionOutput transit(ScanContext context);
}
