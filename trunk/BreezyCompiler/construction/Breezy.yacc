%{
package src;
import java.io.*;
//Authored by Jon, Cesar, Vinay, Sharadh
//Adapted to Breezy by Ian, Leighton, Jack, Jon, Elena, Clement
%}


%token COMMENT
%token IDENTIFIER
%token BOOLEAN STRING NUMBER ARRAY HASH
%token ACCEPTS BEGIN BOOLEAN EACH ELSE END FOR FUNCTION IF NOTHING NUMERIC QUOTE RETURN RETURNS WHILE
%token PLUS MINUS EQUAL REL_OP_LE REL_OP_LT REL_OP_GE REL_OP_GT EQUALS LOG_OP_EQUAL LOG_OP_AND LOG_OP_OR LOG_OP_NOT
%token MUL DIV MOD
%token LPAREN RPAREN COLON SEMICOLON COMMA DOT
%token TRUE FALSE


%%

start	: 	program		{ba.DumpFile($1.sval);}


program		:	method 		{$$.sval= $1.sval;}
                |	program method	{$$.sval = $1.sval + $2.sval;}
                ;


method	: 	COMMENT
    		FUNCTION IDENTIFIER
    		RETURNS return_type
    		ACCEPTS aparams
    		BEGIN
                body
                END IDENTIFIER	{ $$.sval = ba.createFunction($3.sval,$5.sval,$7.sval,$9.sval,$11.sval); }
		;


body    :       declarations
                control_body            {$$.sval = $1.sval + $2.sval;}
        |                               {$$.sval = "";}
        ;


//init variables so no crash
declarations    :   type_declaration SEMICOLON                           {System.out.println("Used first in declarations"); $$.sval = $1.sval + ";\n";}
                |   type_declaration_assignment SEMICOLON                {$$.sval = $1.sval + ";\n";}
                |   declarations type_declaration  SEMICOLON              {$$.sval = $1.sval + $2.sval + ";\n";}
                |   declarations type_declaration_assignment SEMICOLON    {$$.sval = $1.sval + $2.sval + ";\n";}
                |                                                        {$$.sval = "";}
                ;


type_declaration	:	STRING IDENTIFIER	{$$.sval = "String " + $2.sval;}
			|	BOOLEAN IDENTIFIER	{$$.sval = "boolean " + $2.sval;}
			|	NUMBER IDENTIFIER	{$$.sval = "Number " + $2.sval;}
			|	ARRAY IDENTIFIER	{$$.sval = ba.createComplexType("ArrayList", $2.sval);}
			|	HASH IDENTIFIER         {$$.sval = ba.createComplexType("HashMap", $2.sval);}
			;


/*Add all kinds of type assignments here!*/
type_declaration_assignment :   STRING IDENTIFIER EQUALS string_exp   {$$.sval = $1.sval + $2.sval + " = " + $4.sval;}
                            |   NUMBER IDENTIFIER EQUALS arith_exp {$$.sval = $1.sval + $2.sval + " = " + $4.sval;}
                            |   BOOLEAN IDENTIFIER EQUALS bool_exp  {$$.sval = $1.sval + $2.sval + " = " + $4.sval;}
                            |	ARRAY IDENTIFIER EQUALS LEFT_SQUARE_PAREN params RIGHT_SQUARE_PAREN	{$$.sval = ba.createComplexType("ArrayList", $2.sval, $5.sval);}
                            ;


//concrete list of functionality
complex_type_method_invocation
		:	IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, $5.sval);}
		|	IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, null);}
		;


control_body	:	statement               {$$.sval = $1.sval;}
                |	control_body statement	{$$.sval = $1.sval + $2.sval;}
                |	{$$.sval = "";};
                ;


statement	:	function_declaration		{$$.sval = $1.sval;}
			|	complex_type_method_invocation	{$$.sval = $1.sval;}
			|	type_initialization		{$$.sval = $1.sval;}
			|	return_statement		{$$.sval = $1.sval;}
			|	if_statement			{$$.sval = $1.sval;}
			|	arith_exp SEMICOLON			{$$.sval = $1.sval + ";\n";}
			|	IDENTIFIER EQUALS arith_exp SEMICOLON {$$.sval = $1.sval + "=" + $3.sval + ";\n" ;}
			|	IDENTIFIER EQUALS string_exp SEMICOLON {$$.sval = $1.sval + "=" + $3.sval + ";\n" ;}
			|	IDENTIFIER EQUALS bool_exp SEMICOLON {$$.sval = $1.sval + "=" + $3.sval + ";\n" ;}
			;

if_statement	:	IF LPAREN condition RPAREN
					BEGIN
					body
					END IF	{ $$.sval = "if(" + $3.sval + "){\n" + $6.sval + "}\n";}
				;

return_statement	:	RETURN IDENTIFIER SEMICOLON	{$$.sval = "return " + $2.sval + ";\n";}
					|	RETURN NUMERIC SEMICOLON	{$$.sval = "return " + $2.sval + ";\n";}
					|	RETURN QUOTE SEMICOLON		{$$.sval = "return " + $2.sval + ";\n";}
					|	RETURN function_declaration	{$$.sval = "return " + $2.sval + "\n";}
					;

type_initialization	:	IDENTIFIER EQUALS QUOTE SEMICOLON	{$$.sval = $1.sval + " = " + $3.sval + ";\n";}
					|	IDENTIFIER EQUALS NUMERIC SEMICOLON	{$$.sval = $1.sval + " = " + $3.sval + ";\n";}
					|	IDENTIFIER EQUALS IDENTIFIER SEMICOLON	{$$.sval = $1.sval + " = " + $3.sval + ";\n";}
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
		|	NOTHING				{$$.sval = "";}
		|	params COMMA params 		{$$.sval = $1.sval + "," + $3.sval;}
		;


type		:       BOOLEAN		{$$.sval = "boolean";}
                |	STRING		{$$.sval = "String";}
                |	NUMBER		{$$.sval = "double";}
                |       ARRAY           {$$.sval = "ArrayList";}
                |       HASH            {$$.sval = "HashMap";}
                ;

return_type     :       type            {$$.sval = $1.sval;}
                |       NOTHING         {$$.sval = "void";}

		
complex_type_method_invocation
		:	IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, $5.sval);}
		|	IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, null);}
		;

string_exp      :       QUOTE                       {$$.sval = $1.sval;}
                |       function_declaration        {$$.sval = $1.sval;}
                |       string_exp PLUS string_exp  {$$.sval = $1.sval + " + " + $3.sval;}
                ;

arith_exp	: 	arith_exp PLUS term {$$.sval = $1.sval + "+" + $3.sval; }
			|	arith_exp MINUS term {$$.sval = $1.sval + " - " + $3.sval; }
			|	term	 {$$.sval = $1.sval;}
			;
			
term		:	term MUL unary {$$.sval = $1.sval + " * " + $3.sval; }
			|	term DIV unary {$$.sval = $1.sval + " / " + $3.sval; }
			|	term MOD unary {$$.sval = $1.sval + " % " + $3.sval; }
			|	unary	 {$$.sval = $1.sval;}
			;

unary		:	MINUS unary { $$.sval = " -"+ $2.sval;}
			|	factor	 {$$.sval = $1.sval;}
			;

factor 		: 	LPAREN arith_exp RPAREN	{$$.sval = " ( " + $2.sval + " ) "; }
			|	NUMERIC		{ $$.sval = $1.sval; }
			|	IDENTIFIER		{ $$.sval = $1.sval; }
			|	function_declaration { $$.sval = $1.sval; }
			;

condition	:	bool_exp	{$$ = $1;}
		;



bool_exp	:	bool_exp LOG_OP_OR bool_exp {$$.sval = $1.sval + " || " + $3.sval; }
			|	bool_exp LOG_OP_AND bool_exp {$$.sval = $1.sval + " && " + $3.sval; }
			|	LOG_OP_NOT bool_exp {$$.sval = " !" + $2.sval; }
			|	LPAREN bool_exp RPAREN {$$.sval = " ( " + $2.sval + " ) "; }
			|	TRUE		{$$.sval = "true";}
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