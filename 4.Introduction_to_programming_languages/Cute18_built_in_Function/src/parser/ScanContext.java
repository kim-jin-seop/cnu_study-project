package parser;


import java.io.File;
import java.io.FileNotFoundException;

class ScanContext {
	private final CharStream input;
	private StringBuilder builder;
	
	ScanContext(File file) throws FileNotFoundException {
		this.input = CharStream.from(file);
		this.builder = new StringBuilder();
	}
	
	CharStream getCharStream() {
		return input;
	}
	
	//문자열을 읽어오면 그동아 append했던 문자열이 날라간다.
	String getLexime() {
		String str = builder.toString(); 
		builder.setLength(0); //기존에 가지고 있는 문자열 날려 보내기
		return str;
	}
	
	//문자 추가
	void append(char ch) {
		builder.append(ch); // builder에 데이터 값 넣기
	}
}
