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
%token IDENTIFIER
%token ARRAY
%token BOOLEAN
%token STRING
%token NUMBER


%%

start	: 	program		{ba.DumpFile($1.sval);}


program		:	method 		{$$.sval= $1.sval;}
		|	program method	{$$.sval = $1.sval + $2.sval;}
		;


method	: 	COMMENT
    		FUNCTION IDENTIFIER
    		RETURNS type
    		ACCEPTS aparams
    		BEGIN
		body
		END IDENTIFIER	{ $$.sval = ba.createFunction($3.sval,$5.sval,$7.sval,$9.sval,$11.sval); }
	;


body	:	statement	{$$.sval = $1.sval;}
	|	body statement	{$$.sval = $1.sval + $2.sval;}
	;


statement	:	function_declaration		{$$.sval = $1.sval;}
		|	type_declaration		{$$.sval = $1.sval;}
		|	complex_type_declaration		{$$.sval = $1.sval;}
		|	complex_type_method_invocation	{$$.sval = $1.sval;}
		|	type_initialization		{$$.sval = $1.sval;}
		|	return_statement		{$$.sval = $1.sval;}
		|	if_statement			{$$.sval = $1.sval;}
		;

if_statement	:	IF LPAREN condition RPAREN
			BEGIN
			body
			END IF	{ $$.sval = "if(" + $3.sval + "){\n" + $6.sval + "}\n";}
		;

return_statement	:	RETURN IDENTIFIER SEMICOLON	{$$.sval = "return " + $2.sval + ";\n";}
			|	RETURN function_declaration	{$$.sval = "return " + $2.sval + "\n";}
			;

type_initialization	:	IDENTIFIER EQUALS QUOTE SEMICOLON	{$$.sval = $1.sval + " = " + $3.sval + ";\n";}
			|	IDENTIFIER EQUALS NUMERIC SEMICOLON	{$$.sval = $1.sval + " = " + $3.sval + ";\n";}
			|	IDENTIFIER EQUALS IDENTIFIER SEMICOLON	{$$.sval = $1.sval + " = " + $3.sval + ";\n";}
			;

type_declaration	:	type IDENTIFIER SEMICOLON	{$$.sval = $1.sval + " " + $2.sval + ";\n";}
			;

function_declaration	:	IDENTIFIER LPAREN params RPAREN SEMICOLON {$$.sval = $1.sval + "(" + $3.sval + ");\n";}
			;


aparams	:	NOTHING				{$$.sval = "";}
	|	type IDENTIFIER			{$$.sval = $1.sval + " " + $2.sval;}
	|	aparams COMMA type IDENTIFIER	{$$.sval = $1.sval + ", " + $3.sval + " " + $4.sval;}
	;


params	:	IDENTIFIER			{$$.sval = $1.sval;}
	|	QUOTE				{$$.sval = $1.sval;}
	|	NUMERIC				{$$.sval = $1.sval;}
	|	params COMMA IDENTIFIER		{$$.sval = $1.sval + "," + $3.sval;}
	|	NOTHING				{$$.sval = "";}
	;


type		:       BOOLEAN		{$$.sval = "boolean";}
		|	STRING		{$$.sval = "String";}
		|	NUMBER		{$$.sval = "double";}
		|	NOTHING		{$$.sval = "void";}
		;
		
complex_type_declaration	:	ARRAY IDENTIFIER SEMICOLON	{$$.sval = ba.createComplexType($1.sval, $2.sval);}
		;
		
complex_type_method_invocation
		:	IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, $5.sval);}
		|	IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, null);}
		;


condition	:	bool_exp	{$$ = $1;}
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

public static void main(String args[]){
	try{
	Parser yyparser = new Parser(new FileReader(args[0]));
		yyparser.yyparse();  
	}catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
	}  
}