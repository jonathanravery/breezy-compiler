package lexer;
import java.io.*;
import java.util.*;
import symbol.*;

public class Lexer {
	public static int line = 1;
	char peek = ' ';
	Hashtable words = new Hashtable();
	
	void reserve(Word w) {
		words.put(w.lexeme, w);
	}
	
	public Lexer(){
		reserve( new Word("if", Tag.ifx));
		reserve( new Word("else", Tag.elsex));
		//TODO: Complete this list pg. 968
	}
	
	void readch() throws IOException{
		peek = (char)System.in.read();
	}
	
	boolean readch(char c) throws IOException{
		readch();
		if( peek != c) return false;
		peek = ' ';
		return true;
	}
	
	public Token scan() throws IOException{
		for( ; ; readch()){
			if( peek == ' ' || peek == '\t')continue;
			else if( peek == 'n') line = line + 1;
			else break;
		}
		
		switch( peek ){
		case '=':
			return new Token('=');
		case '<':
			if( readch( '=')) return Word.le; else return new Token('<');
		case '>':
			if( readch( '=')) return Word.ge; else return new Token('>');
		}
		
		if( Character.isDigit(peek)){
			/*In the book example, both a num object and real
			 * object are returned.  However, since we are defaulting
			 * to doubles for all numerical values, we only return
			 * one type of object here.  Pg.969
			 */
			int v = 0;
			do{
				v = 10*v + Character.digit(peek, 10); readch();
			}while( Character.isDigit(peek));
			if( peek != '.')return new Num(v);
			float x = v; float d = 10;
			for(;;)
		}
	}
}
