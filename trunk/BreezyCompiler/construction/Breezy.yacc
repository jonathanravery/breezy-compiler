%{
package src;
import java.io.*;
import libs.structs.Scope;
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
                END IDENTIFIER	{ $$.sval = ba.createFunction($3.sval,$5.sval,$7.sval,$9.sval,$11.sval,$3.line,Scope.GLOBAL.getName()); }
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


type_declaration	:	STRING IDENTIFIER	{$$.sval = "String " + $2.sval + "=\"\""; ba.addIdentifier($2.sval,"string",$2.line,Scope.LOCAL.getName());}
			|	BOOLEAN IDENTIFIER	{$$.sval = "boolean " + $2.sval + "=false"; ba.addIdentifier($2.sval,"boolean",$2.line,Scope.LOCAL.getName());}
			|	NUMBER IDENTIFIER	{$$.sval = "double " + $2.sval + "=0"; ba.addIdentifier($2.sval,"number",$2.line,Scope.LOCAL.getName());}
			|	ARRAY IDENTIFIER	{$$.sval = ba.createComplexType("ArrayList", $2.sval); ba.addIdentifier($2.sval,"ArrayList",$2.line,Scope.LOCAL.getName());}
			|	HASH IDENTIFIER         {$$.sval = ba.createComplexType("HashMap", $2.sval); ba.addIdentifier($2.sval,"HashMap",$2.line,Scope.LOCAL.getName());}
			;


/*Add all kinds of type assignments here!*/
type_declaration_assignment :   STRING IDENTIFIER EQUALS arith_exp   {$$.sval = "String " + $2.sval + " = " + $4.sval;
                                                                        ba.typeTrack.assertStringType($4);
                                                                         ba.addIdentifier($2.sval,"string",$2.line,Scope.LOCAL.getName());}
                            |   NUMBER IDENTIFIER EQUALS arith_exp {$$.sval = "double " + $2.sval + " = " + $4.sval;
                                                                        ba.typeTrack.assertNumberType($4);
                                                                         ba.addIdentifier($2.sval,"number",$2.line,Scope.LOCAL.getName());}
                            |   BOOLEAN IDENTIFIER EQUALS bool_exp  {$$.sval = "boolean " + $2.sval + " = " + $4.sval;
                                                                          ba.addIdentifier($2.sval,"boolean",$2.line,Scope.LOCAL.getName());}
                            |	ARRAY IDENTIFIER EQUALS LEFT_SQUARE_PAREN params RIGHT_SQUARE_PAREN	
                                        {$$.sval = ba.createComplexType("ArrayList", $2.sval, $5.sval,$2.line,Scope.LOCAL.getName());}
                            |	HASH IDENTIFIER EQUALS LEFT_SQUARE_PAREN hash_params RIGHT_SQUARE_PAREN
                                	{$$.sval = ba.createComplexType("HashMap", $2.sval, $5.sval,$2.line,Scope.LOCAL.getName());}
                            ;


control_body	:	statement               {$$.sval = $1.sval;}
                |	control_body statement	{$$.sval = $1.sval + $2.sval;}
                |	{$$.sval = "";};
                ;


statement	:       COMMENT                                     {$$.sval = "";}
                |       function_call SEMICOLON              {$$.sval = $1.sval + ";\n";}
                |	complex_type_method_invocation SEMICOLON    {$$.sval = $1.sval + ";\n";}
                |	return_statement SEMICOLON                  {$$.sval = $1.sval + ";\n";}
                |	if_statement                                {$$.sval = $1.sval;}
                |       while_loop                                  {$$.sval = $1.sval;}
                |	IDENTIFIER EQUALS exp SEMICOLON             {$1.obj = ba.typeTrack.getType($1,Scope.LOCAL.getName());
                                                                        ba.typeTrack.assertSameType($1,$3,$2);
                                                                        $$.sval = $1.sval + "=" + $3.sval + ";\n" ;}
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

return_statement	:	RETURN exp 		{$$.sval = "return " + $2.sval;}
                        |       RETURN complex_type_method_invocation   {$$.sval = "return " +$2.sval;}
                        ;

function_call	:	IDENTIFIER LPAREN params RPAREN {$$.sval = $1.sval + "(" + $3.sval + ")";
                                                            $$.obj = ba.typeTrack.getType($1, Scope.GLOBAL.getName());}
                        ;


//concrete list of functionality
complex_type_method_invocation
		:	IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, Scope.LOCAL.getName(), $5.sval);}
		|	IDENTIFIER DOT IDENTIFIER LPAREN RPAREN {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, Scope.LOCAL.getName(), null);}
		;


aparams         :	NOTHING				{$$.sval = "";}
                |       aparams_                        {$$.sval = $1.sval;}
                ;

aparams_	:	type IDENTIFIER			{ba.addIdentifier($2.sval,$1.obj.toString(),$2.line,Scope.LOCAL.getName());
                                                            $$.sval = $1.sval + " " + $2.sval;}
		|	type IDENTIFIER COMMA aparams_     {ba.addIdentifier($2.sval,$1.obj.toString(),$2.line,Scope.LOCAL.getName());
                                                            $$.sval = $1.sval + " " + $2.sval + ", " + $4.sval;}
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


type		:       BOOLEAN		{$$.sval = "boolean "; $$.obj = "boolean";}
                |	STRING		{$$.sval = "String "; $$.obj = "string";}
                |	NUMBER		{$$.sval = "double "; $$.obj = "number";}
                |       ARRAY           {$$.sval = "ArrayList "; $$.obj = "ArrayList";}
                |       HASH            {$$.sval = "HashMap "; $$.obj = "HashMap";}
                ;

return_type     :       type            {$$.sval = $1.sval;}
                |       NOTHING         {$$.sval = "void";}
                ;

exp             :       bool_exp        {$$.sval = $1.sval;
                                                $$.line = $1.line;
                                                $$.obj = $1.obj;}
                |       arith_exp        {$$.sval = $1.sval;
                                                $$.line = $1.line;
                                                $$.obj = $1.obj;}
                ;

arith_exp	: 	arith_exp PLUS term {$$.sval = $1.sval + "+" + $3.sval;
                                              ba.typeTrack.assertNumberOrStringType($1,$3, $2);
                                                $$.obj = $1.obj;
                                                $$.line = $1.line; }
                |	arith_exp MINUS term {$$.sval = $1.sval + " - " + $3.sval;
                                              ba.typeTrack.assertNumberType($1,$3, $2);
                                                $$.obj = $1.obj;
                                                $$.line = $1.line; }
                |	term	 {$$.sval = $1.sval;
                                    $$.line = $1.line;}
                ;
			
term		:	term MUL unary {$$.sval = $1.sval + " * " + $3.sval;
                                              ba.typeTrack.assertNumberType($1,$3, $2);
                                                $$.obj = $1.obj;
                                                $$.line = $1.line; }
                |	term DIV unary {$$.sval = $1.sval + " / " + $3.sval;
                                              ba.typeTrack.assertNumberType($1,$3, $2);
                                                $$.obj = $1.obj;
                                                $$.line = $1.line; }
                |	term MOD unary {$$.sval = $1.sval + " % " + $3.sval;
                                              ba.typeTrack.assertNumberType($1,$3, $2);
                                                $$.obj = $1.obj;
                                                $$.line = $1.line; }
                |	unary	 {$$.sval = $1.sval; 
                                    $$.obj = $1.obj;
                                    $$.line = $1.line;}
                ;

unary		:	MINUS unary { $$.sval = " -"+ $2.sval; 
                                        ba.typeTrack.assertNumberType($2);
                                        $$.obj = $2.obj;
                                        $$.line = $2.line;}
                |	factor	 {$$.sval = $1.sval;
                                    $$.obj = $1.obj;
                                    $$.line = $1.line;}
                ;

factor 		: 	LPAREN arith_exp RPAREN	{$$.sval = " ( " + $2.sval + " ) ";
                                                    $$.obj = $2.obj;
                                                    $$.line = $1.line; }
                |	NUMERIC                 { $$.sval = $1.sval; 
                                                    $$.obj = $1.obj;
                                                    $$.line = $1.line; }
                |	IDENTIFIER		{ $$.sval = $1.sval; 
                                                    $$.obj = ba.typeTrack.getType($1, Scope.LOCAL.getName());
                                                    $$.line = $1.line;}
                |	function_call    { $$.sval = $1.sval;
                                                    $$.obj = $1.obj;
                                                    $$.line = $1.line;}
                |       QUOTE           	{ $$.sval = $1.sval; 
                                                    $$.obj = $1.obj;
                                                    $$.line = $1.line;}
                ;

bool_exp	:       bool_exp LOG_OP_OR bool_term {$$.sval = $1.sval + " || " + $3.sval;
                                                         $$.obj = $1.obj;
                                                            $$.line = $1.line;}
                |       bool_term                         {$$.sval = $1.sval; 
                                                            $$.obj = $1.obj;
                                                            $$.line = $1.line;}
                ;

bool_term       :	bool_term LOG_OP_AND bool_factor {$$.sval = $1.sval + " && " + $3.sval;  
                                                            $$.obj = $1.obj;
                                                            $$.line = $1.line; }
                |       bool_factor                         {$$.sval = $1.sval; 
                                                                    $$.obj = $1.obj;
                                                                    $$.line = $1.line;}
                ;

bool_factor     :	LOG_OP_NOT bool_factor {$$.sval = " !" + $2.sval; 
                                                $$.obj = $2.obj;
                                                $$.line = $1.line;}
                |	LPAREN bool_exp RPAREN {$$.sval = " ( " + $2.sval + " ) "; 
                                                $$.obj = $2.obj;
                                                $$.line = $1.line;}
                |       arith_exp rel_op arith_exp  {ba.typeTrack.assertNumberOrStringType($1,$3,$2);
                                                        if($1.obj.toString().equals("string") && $2.sval.equals("=="))
                                                            $$.sval = "(" +$1.sval + ").equals(" + $3.sval + ")";
                                                        else if($1.obj.toString().equals("string") && $2.sval.equals("!="))
                                                            $$.sval = "!((" +$1.sval + ").equals(" + $3.sval + "))";
                                                        else if($1.obj.toString().equals("string"))
                                                            throw new Exception("You cannot use " + $2.sval + " with two STRING types.");
                                                        else
                                                            $$.sval = $1.sval + $2.sval + $3.sval;
                                                         $$.obj = "boolean";
                                                        $$.line = $1.line;}
                |	TRUE		{$$.sval = $1.sval; $$.obj = $1.obj;
                                                $$.line = $1.line;}
                |	FALSE		{$$.sval = $1.sval; $$.obj = $1.obj;
                                                $$.line = $1.line;}
                |       IDENTIFIER      {$$.sval = $1.sval; 
                                            $1.obj = ba.typeTrack.getType($1, Scope.GLOBAL.getName());
                                            ba.typeTrack.assertBoolType($1);
                                            $$.obj = $1.obj;
                                                $$.line = $1.line; }
                |	function_call { $$.sval = $1.sval;
                                                $1.obj = ba.typeTrack.getType($1, Scope.GLOBAL.getName());
                                                ba.typeTrack.assertBoolType($1);
                                                $$.obj = $1.obj;
                                                $$.line = $1.line;}
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
            System.err.println(e.getMessage());
	}  
}