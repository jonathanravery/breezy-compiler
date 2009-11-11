package lexer;

public class Word extends Token{
	public String lexeme = "";
	public Word(String s, int tag){
		super(tag); 
		lexeme = s;
	}
	public String toString(){
		return lexeme;
	}
	public static final Word
		and = new Word("AND", Tag.AND ), or = new Word("OR", Tag.OR),
		eq = new Word("EQUAL", Tag.EQUAL), le = new Word("<=", Tag.le),
		ge = new Word(">=", Tag.ge);
}
