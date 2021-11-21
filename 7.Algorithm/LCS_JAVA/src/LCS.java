import java.io.BufferedWriter;
import java.io.IOException;

public class LCS {
	private int[][] c; //c is LCS
	private int[][] b; //b is direction
	
	private final int leftUp = 1; //To left up
	private final int up = 2;     //To up
	private final int left = 3;   //To Left
	
	public void LCS_Length(char[] X, char[] Y){ //Find LCS
		int m = X.length; //m is X'Length
		int n = Y.length; //n is Y'Length
		c = new int[m][n];
		b = new int[m][n];
		for(int i = 0; i < m; i++) { //initialization 0 
			c[i][0] = 0;
		}
		for(int i = 0; i < n; i++) { //initialization 0
			c[0][i] = 0;
		}
		for(int i = 1; i < m; i++) { 
			for(int j = 1; j < n; j ++ ) {
				if(X[i] == Y[j]) {            //X[i] equal Y[j]
					c[i][j] = c[i-1][j-1] + 1; // c[i,j] = c[i-1,j-1] +1
					b[i][j] = leftUp; // direction left up
					
				}else if(c[i-1][j] >= c[i][j-1]){ //c[i-1][j] is bigger than c[i-1][j]
					c[i][j] = c[i-1][j];
					b[i][j] = up; //direction up
				}
				else {
					c[i][j] = c[i][j-1];
					b[i][j] = left;
				}
			}
		}
	}
	
	public void Print_LCS(BufferedWriter fw, char[] x, int i, int j) throws IOException { //print LCS
		if(i == 0 || j == 0) { //final
			return;
		}
		if(b[i][j] == leftUp) { //direction : left up
			Print_LCS(fw,x,i-1,j-1);
			fw.write(x[i]);
		}else if(b[i][j] == up) { //direction : up
			Print_LCS(fw, x,i-1,j);
		}
		else {
			Print_LCS(fw,x,i,j-1); //direction : left
		}
	}
}
