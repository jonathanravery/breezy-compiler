%{
package src;
import java.io.*;
//Authored by Jon, Cesar, Vinay, Sharadh
//Adapted to Breezy by Ian, Leighton, Jack, Jon, Elena, Clement
%}


%token COMMENT
%token IDENTIFIER
%token BOOLEAN STRING
%token NUMBER ARRAY HASH
%token TRUE FALSE
%token ACCEPTS BEGIN BOOLEAN EACH ELSE ELSEIF END FOR FUNCTION IF NOTHING NUMERIC QUOTE RETURN RETURNS WHILE
%token REL_OP_LE REL_OP_LT REL_OP_GE REL_OP_GT EQUALS LOG_OP_EQUAL LOG_OP_AND LOG_OP_OR LOG_OP_NOT
%token EQUAL
%left PLUS MINUS
%left MOD
%left MUL DIV
%token LEFT_SQUARE_PAREN RIGHT_SQUARE_PAREN
%token LPAREN RPAREN COLON SEMICOLON COMMA DOT


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
declarations    :   declarations type_declaration  SEMICOLON              {$$.sval = $1.sval + $2.sval + ";\n";}
                |   declarations type_declaration_assignment SEMICOLON    {$$.sval = $1.sval + $2.sval + ";\n";}
                |   declarations COMMENT                                  {$$.sval = $1.sval + "";}
                |                                                           {$$.sval = "";}
                ;


type_declaration	:	STRING IDENTIFIER	{$$.sval = "String " + $2.sval + "=\"\"";}
			|	BOOLEAN IDENTIFIER	{$$.sval = "boolean " + $2.sval + "=false";}
			|	NUMBER IDENTIFIER	{$$.sval = "double " + $2.sval + "=0";}
			|	ARRAY IDENTIFIER	{$$.sval = ba.createComplexType("ArrayList", $2.sval);}
			|	HASH IDENTIFIER         {$$.sval = ba.createComplexType("HashMap", $2.sval);}
			;


/*Add all kinds of type assignments here!*/
type_declaration_assignment :   STRING IDENTIFIER EQUALS arith_exp   {$$.sval = "String " + $2.sval + " = " + $4.sval;}
                            |   NUMBER IDENTIFIER EQUALS arith_exp {$$.sval = "double " + $2.sval + " = " + $4.sval;}
                            |   BOOLEAN IDENTIFIER EQUALS bool_exp  {$$.sval = "boolean " + $2.sval + " = " + $4.sval;}
                            |	ARRAY IDENTIFIER EQUALS LEFT_SQUARE_PAREN params RIGHT_SQUARE_PAREN	{$$.sval = ba.createComplexType("ArrayList", $2.sval, $5.sval);}
                            |	HASH IDENTIFIER EQUALS LEFT_SQUARE_PAREN hash_params RIGHT_SQUARE_PAREN	{$$.sval = ba.createComplexType("HashMap", $2.sval, $5.sval);}
                            ;


control_body	:	statement               {$$.sval = $1.sval;}
                |	control_body statement	{$$.sval = $1.sval + $2.sval;}
                |	{$$.sval = "";};
                ;


statement	:       COMMENT                         {$$.sval = "";}
                |       function_declaration		{$$.sval = $1.sval;}
                |	complex_type_method_invocation	{$$.sval = $1.sval;}
                |	return_statement		{$$.sval = $1.sval;}
                |	if_statement			{$$.sval = $1.sval;}
                |       while_loop                      {$$.sval = $1.sval;}
                |	IDENTIFIER EQUALS exp SEMICOLON {$$.sval = $1.sval + "=" + $3.sval + ";\n" ;}
                ;

if_statement	:	IF LPAREN bool_exp RPAREN
                        BEGIN
                            control_body
                        END IF	else_if else    { $$.sval = "if(" + $3.sval + "){\n" + $6.sval + "}\n" + $9.sval + "\n" + $10.sval;}
                ;


else_if         :       ELSEIF LPAREN bool_exp RPAREN
                        BEGIN
                            control_body
                        END ELSEIF else_if { $$.sval = "else if(" + $3.sval + "){\n" + $6.sval + "}\n" + $9.sval + "\n";}
                        |                   { $$.sval = "";}
                        ;


else            :       ELSE
                        BEGIN
                            control_body
                        END ELSE    { $$.sval = "else{\n" + $3.sval + "}\n";}
                        |           {$$.sval = "";}
                        ;


while_loop	: 	WHILE LPAREN bool_exp RPAREN
			BEGIN
				control_body
			END WHILE    { $$.sval = "while( " + $3.sval + " ){\n" + $6.sval + "}\n";}
                ;

return_statement	:	RETURN exp SEMICOLON		{$$.sval = "return " + $2.sval + ";\n";}
                        |	RETURN function_declaration	{$$.sval = "return " + $2.sval + ";\n";}
                        |       RETURN complex_type_method_invocation   {$$.sval = "return " +$2.sval + ";\n";}
                        ;

function_declaration	:	IDENTIFIER LPAREN params RPAREN SEMICOLON {$$.sval = $1.sval + "(" + $3.sval + ");\n";}
                        ;


//concrete list of functionality
complex_type_method_invocation
		:	IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, $5.sval);}
		|	IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, null);}
		;


aparams         :	NOTHING				{$$.sval = "";}
                |       aparams_                        {$$.sval = $1.sval;}
                ;

aparams_	:	type IDENTIFIER			{$$.sval = $1.sval + " " + $2.sval;}
		|	type IDENTIFIER COMMA aparams_     {$$.sval = $1.sval + " " + $2.sval + ", " + $4.sval;}
		;

params          :       NOTHING				{$$.sval = "";}
                |       params_                         {$$.sval = $1.sval;}
                ;

params_          :	arith_exp			{$$.sval = $1.sval;}
		|	bool_exp			{$$.sval = $1.sval;}
		|	params_ COMMA params_ 		{$$.sval = $1.sval + "," + $3.sval;}
		;
		
hash_params		: 	LPAREN hash_item COMMA hash_item RPAREN COMMA hash_params	{$$.sval = "(" + $2.sval + "," + $4.sval + ")" + $7.sval;}
				|	LPAREN hash_item COMMA hash_item RPAREN		{$$.sval = "(" + $2.sval + "," + $4.sval + ")";}
				;

hash_item		:	QUOTE	{$$.sval = $1.sval;}
				|	NUMERIC	{$$.ival = $1.ival;}
				|	IDENTIFIER	{$$.sval = $1.sval;}
				;


type		:       BOOLEAN		{$$.sval = "boolean ";}
                |	STRING		{$$.sval = "String ";}
                |	NUMBER		{$$.sval = "double ";}
                |       ARRAY           {$$.sval = "ArrayList ";}
                |       HASH            {$$.sval = "HashMap ";}
                ;

return_type     :       type            {$$.sval = $1.sval;}
                |       NOTHING         {$$.sval = "void";}

		
complex_type_method_invocation
		:	IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, $5.sval);}
		|	IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, null);}
		;

exp             :       bool_exp        {$$.sval = $1.sval;}
                |       arith_exp        {$$.sval = $1.sval;}
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
                |	NUMERIC                 { $$.sval = $1.sval; }
                |	IDENTIFIER		{ $$.sval = $1.sval; }
                |	function_declaration    { $$.sval = $1.sval; }
                |       QUOTE           	{ $$.sval = $1.sval; }
                ;

bool_exp	:       bool_exp LOG_OP_OR bool_term {$$.sval = $1.sval + " || " + $3.sval; }
                |       bool_term                         {$$.sval = $1.sval;}
                ;

bool_term       :	bool_term LOG_OP_AND bool_factor {$$.sval = $1.sval + " && " + $3.sval; }
                |       bool_factor                         {$$.sval = $1.sval;}
                ;

bool_factor     :	LOG_OP_NOT bool_factor {$$.sval = " !" + $2.sval; }
                |	LPAREN bool_exp RPAREN {$$.sval = " ( " + $2.sval + " ) "; }
                |       arith_exp rel_op arith_exp  {$$.sval = $1.sval + $2.sval + $3.sval;}
                |	TRUE		{$$.sval = "true";}
                |	FALSE		{$$.sval = "false";}
                |       IDENTIFIER      {$$.sval = $1.sval;}
                |	function_declaration { $$.sval = $1.sval; }
                ;

rel_op          :       REL_OP_LT		{$$.sval = "<";}
                |       REL_OP_GT		{$$.sval = ">";}
                |       REL_OP_LE		{$$.sval = "<=";}
                |       REL_OP_GE		{$$.sval = ">=";}
                |       LOG_OP_EQUAL		{$$.sval = "==";}
                |       LOG_OP_NOT LOG_OP_EQUAL	{$$.sval = "!=";}
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