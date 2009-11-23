%{
package src;
import java.io.*;
//Authored by Jon, Cesar, Vinay, Sharadh
//Adapted to Breezy by Ian, Leighton, Jack, Jon, Elena, Clement
%}


%token COMMENT
%token ACCEPTS BEGIN BOOLEAN EACH ELSE END FOR FUNCTION IF NOTHING NUMERIC QUOTE RETURN RETURNS WHILE
%token PLUS MINUS MUL DIV MOD EQUAL REL_OP_LE REL_OP_LT REL_OP_GE REL_OP_GT EQUALS LOG_OP_EQUAL LOG_OP_AND LOG_OP_OR LOG_OP_NOT
%token LPAREN RPAREN COLON SEMICOLON COMMA DOT
%token TRUE FALSE
%token NEWLINE
%token IDENTIFIER
%token BOOLEAN
%token STRING
%token NUMBER


%%

start	: 	NEWLINE program	{ba.DumpFile($2.sval);}
	|	program		{ba.DumpFile($1.sval);}


program		:	method 		{$$.sval= $1.sval;}
		|	program method	{$$.sval = $1.sval + $2.sval;}
		;


method	: 	method NEWLINE		{$$.sval = $1.sval;}
	|	COMMENT NEWLINE
    		FUNCTION IDENTIFIER NEWLINE
    		RETURNS type NEWLINE
    		ACCEPTS params NEWLINE
    		BEGIN NEWLINE
		body
		END 	{ $$.sval = "public static " + $7.sval + " " + 
					$4.sval + "(" + $10.sval + ")" + "{\n" + $14.sval + "}"; }
	;


body	:	function_declaration		{$$.sval = $1.sval;}
	;


function_declaration	:	IDENTIFIER LPAREN params RPAREN SEMICOLON NEWLINE	{$$.sval = $1.sval + "(" + $3.sval + ");\n";}
			;


params	:	IDENTIFIER			{$$.sval = $1.sval;}
	|	QUOTE				{$$.sval = $1.sval;}
	|	NUMERIC				{$$.sval = $1.sval;}
	|	params COMMA IDENTIFIER		{$$.sval = $1.sval + "," + $3.sval;}
	|	NOTHING				{$$.sval = $1.sval;}
	;


type		:	BOOLEAN		{$$.sval = "boolean";}
		|	STRING		{$$.sval = "String";}
		|	NUMBER		{$$.sval = "double";}
		|	NOTHING		{$$.sval = "void";}
		;


expression	:	bool_exp	{$$ = $1;}
		;


bool_exp	:	TRUE		{$$.sval = "true";}
		|	FALSE		{$$.sval = "false";}
		;



%%

void yyerror(String s){
	System.out.println(s);
}

protected Parser(Reader r) {
	//Yylex lexer = new Yylex(r, this);
	super(r);
	super.yyparser = this;
	ba = new BreezyAssist();
}

private BreezyAssist ba;

public static void main(String args[]) throws IOException {
	Parser yyparser = new Parser(new FileReader(args[0]));
	try{
		yyparser.yyparse();  
	}catch(Exception e){
		e.printStackTrace();
	}  
}