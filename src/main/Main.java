package main;
import lexer.*;
import parser.*;
import java.io.*;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Lexer lex = new Lexer();
		Parser parse = new Parser(lex);
		parse.program();
		System.out.write('\n');

	}

}
