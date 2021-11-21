package lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class CharStream {
	private final Reader reader;
	private Character cache;
	
	static CharStream from(File file) throws FileNotFoundException { //CharStream의 reader
		return new CharStream(new FileReader(file));
	}
	
	/*CharStream의 생성자*/
	CharStream(Reader reader) {  // CharStream 생성자
		this.reader = reader;
		this.cache = null;
	}
	
	/*Char의 다음 위치 가져오기
	 *cache를 확인하는 이유 = pushBack
	 *nextChar()하나 읽을 때 마다 한글자씩 이동.
	*/
	Char nextChar() { 
		if ( cache != null ) {  //cache가 null이 아니면
			char ch = cache;    // ch를 cache로 변경.
			cache = null;       // ch에 넣었으면 cache null
			
			return Char.of(ch);   //Char of -> Char를 가져온다. Char의 상태와 함께.
		}
		else { // cache가 null이면
			try {
				int ch = reader.read(); // reader로 하나 읽어오기.
				if ( ch == -1 ) {  //ch가 -1이면
					return Char.end(); // end return
				}
				else {
					return Char.of((char)ch); //Char 가져온다.
				}
			}
			catch ( IOException e ) {
				throw new ScannerException("" + e);
			}
		}
	}
	
	/*pushBack-> cache에 ch를 삽입한다.
	 * 하는 이유는 nextChar을 하여줄 때 cache에 데이터가 들어있으므로 그 데이터를 사용하게 된다.
	 * 즉 그 데이터를 다음에 사용해야할 때 사용한다. -> 이후 특수문자, sign 처리할 때 사용
	 * */
	void pushBack(char ch) { //cache에 ch 넣기.
		cache = ch;
	}
}
