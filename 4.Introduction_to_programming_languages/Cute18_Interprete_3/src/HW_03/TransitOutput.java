package HW_03;
import java.util.Optional;

class TransitionOutput {					//TransitionOutput 클래스 상태 추가 (#T/F)
	private final State nextState;
	private final Optional<Token> token;
	static TransitionOutput GOTO_START = new TransitionOutput(State.START);
	static TransitionOutput GOTO_ACCEPT_ID = new TransitionOutput(State.ACCEPT_ID);
	static TransitionOutput GOTO_ACCEPT_INT = new TransitionOutput(State.ACCEPT_INT);
	static TransitionOutput GOTO_SIGN = new TransitionOutput(State.SIGN);
	static TransitionOutput GOTO_FAILED = new TransitionOutput(State.FAILED);
	static TransitionOutput GOTO_EOS = new TransitionOutput(State.EOS);
	static TransitionOutput GOTO_ACCEPT_TRUE = new TransitionOutput(State.ACCEPT_TRUE);//추가된 TRUE 상태
	static TransitionOutput GOTO_ACCEPT_FALSE = new TransitionOutput(State.ACCEPT_FALSE);//추가된 FALSE 상태
	static TransitionOutput GOTO_ACCEPT_SHARP = new TransitionOutput(State.ACCEPT_SHARP);//추가된 SHARP 상태
	
	static TransitionOutput GOTO_MATCHED(TokenType type, String lexime) {	//타입과 원본을 인자로 받는 생성자
		return new TransitionOutput(State.MATCHED, new Token(type, lexime));//토큰의 타입이 바뀔때 타입이 변경된 토큰을 반환함
	}
	static TransitionOutput GOTO_MATCHED(Token token) {	//TOKEN 만 인자로 받는 생성자
		return new TransitionOutput(State.MATCHED, token);//토큰의 타입이 바뀌지 않았을 때 해당 토큰을 그대로 반환
	}
	
	TransitionOutput(State nextState, Token token) {	//다음 상태와 토큰자체를 인자로 받는 생성자
		this.nextState = nextState;
		this.token = Optional.of(token);
	}
	
	TransitionOutput(State nextState) {		//다음 상태만 인자로 받는 생성자
		this.nextState = nextState;
		this.token = Optional.empty();
	}
	
	State nextState() {					//다음 상태를 반환하는 함수
		return this.nextState;
	}
	
	Optional<Token> token() {
		return this.token;
	}
}