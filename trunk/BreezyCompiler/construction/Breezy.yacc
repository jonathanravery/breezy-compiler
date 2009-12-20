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
%token ACCEPTS BEGIN BOOLEAN EACH ELSE ELSEIF END FOR FUNCTION IF IN NOTHING NUMERIC QUOTE RETURN RETURNS WHILE
%token EQUALS LOG_OP_EQUAL LOG_OP_AND LOG_OP_OR LOG_OP_NOT
%token REL_OP_LE REL_OP_LT REL_OP_GE REL_OP_GT 
%token EQUAL
%left PLUS MINUS
%left MOD
%left MUL DIV
%token LEFT_SQUARE_PAREN RIGHT_SQUARE_PAREN
%token LPAREN RPAREN COLON SEMICOLON COMMA DOT

%%

start	: 	program		{ba.DumpFile($1.sval);}


program		:	method 		{$$.sval= $1.sval;}
                |       COMMENT         {$$.sval="";}
                |	program method	{$$.sval = $1.sval + $2.sval;}
                |	program COMMENT	{$$.sval = $1.sval;}
                ;


method	: 	COMMENT
    		FUNCTION IDENTIFIER
    		RETURNS return_type
    		ACCEPTS aparams
    		BEGIN
                body
            END IDENTIFIER	{ $$.sval = ba.createFunction($3.sval,$5,$7.sval,$9.sval,$11,$3.line,Scope.GLOBAL.getName()); }
		;


body    :       declarations
                control_body            {$$.sval = $1.sval + $2.sval;}
        |                               {$$.sval = "";}
        ;


//init variables so no crash
declarations    :   declarations type_declaration  SEMICOLON              {$$.sval = $1.sval + $2.sval + ";\n";}
                |   declarations type_declaration_assignment SEMICOLON    {$$.sval = $1.sval + $2.sval + ";\n";}
                |   declarations COMMENT                                  {$$.sval = $1.sval + "";}
                |   error 	{yyerror("Error at line: " + getLine(), getLine() );  }
                |                              {$$.sval = "";}
                ;


type_declaration	:	STRING IDENTIFIER	{$$.sval = "String " + $2.sval + "=\"\""; ba.typeTrack.addID($2.sval,"string",$2.line,Scope.LOCAL.getName());}
			|	BOOLEAN IDENTIFIER	{$$.sval = "boolean " + $2.sval + "=false"; ba.typeTrack.addID($2.sval,"boolean",$2.line,Scope.LOCAL.getName());}
			|	NUMBER IDENTIFIER	{$$.sval = "double " + $2.sval + "=0"; ba.typeTrack.addID($2.sval,"number",$2.line,Scope.LOCAL.getName());}
			|	ARRAY IDENTIFIER	{$$.sval = ba.createComplexType("ArrayList", $2.sval); ba.typeTrack.addID($2.sval,"ArrayList",$2.line,Scope.LOCAL.getName());}
			|	HASH IDENTIFIER         {$$.sval = ba.createComplexType("HashMap", $2.sval); ba.typeTrack.addID($2.sval,"HashMap",$2.line,Scope.LOCAL.getName());}
			|
			;


/*Add all kinds of type assignments here!*/
type_declaration_assignment :   STRING IDENTIFIER EQUALS exp   {$$.sval = "String " + $2.sval + " = " + $4.sval;
                                                                        ba.typeTrack.assertStringType($4);
                                                                         ba.typeTrack.addID($2.sval,"string",$2.line,Scope.LOCAL.getName());}
                            |   NUMBER IDENTIFIER EQUALS exp {$$.sval = "double " + $2.sval + " = " + $4.sval;
                                                                        ba.typeTrack.assertNumberType($4);
                                                                         ba.typeTrack.addID($2.sval,"number",$2.line,Scope.LOCAL.getName());}
                            |   BOOLEAN IDENTIFIER EQUALS exp  {$$.sval = "boolean " + $2.sval + " = " + $4.sval;
                                                                          ba.typeTrack.addID($2.sval,"boolean",$2.line,Scope.LOCAL.getName());}
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
                |	complex_type_method_invocation SEMICOLON    {$$.sval = $1.sval;}
                |	return_statement SEMICOLON                  {$$.sval = $1.sval + ";\n";}
                |	if_statement                                {$$.sval = $1.sval;}
                |       while_loop                                  {$$.sval = $1.sval;}
                |       for_loop                                    {$$.sval = $1.sval;}
                |	IDENTIFIER EQUALS exp SEMICOLON             {$1.obj = ba.typeTrack.getType($1,Scope.LOCAL.getName());
                                                                        ba.typeTrack.assertSameType($1,$3,$2);
                                                                        $$.sval = $1.sval + "=" + $3.sval + ";\n" ;}
                ;

if_statement	:	IF LPAREN exp RPAREN
                        BEGIN
                            control_body
                        END IF	else_if else    { ba.typeTrack.assertBoolType($3);
                                                    $$.sval = "if(" + $3.sval + "){\n" + $6.sval + "}\n" + $9.sval + "\n" + $10.sval;}
                
                ;


else_if         :       ELSEIF LPAREN exp RPAREN
                        BEGIN
                            control_body
                        END ELSEIF else_if { ba.typeTrack.assertBoolType($3);
                                                $$.sval = "else if(" + $3.sval + "){\n" + $6.sval + "}\n" + $9.sval + "\n";}
                        |                   { $$.sval = "";}
                        ;


else            :       ELSE
                        BEGIN
                            control_body
                        END ELSE    { $$.sval = "else{\n" + $3.sval + "}\n";}
                        |           {$$.sval = "";}
                        ;


while_loop	: 	WHILE LPAREN exp RPAREN
			BEGIN
				control_body
			END WHILE    { ba.typeTrack.assertBoolType($3);
                                        $$.sval = "while( " + $3.sval + " ){\n" + $6.sval + "}\n";}
                ;

for_loop        :       FOR EACH LPAREN loop_id IN exp RPAREN
                        BEGIN
                            control_body
                        END FOR EACH            {ba.typeTrack.assertArrayOrHashType($6);
                                                    ba.typeTrack.removeLocalID($4);
                                                    $$.sval = "for(TypedParserVal " +$4.sval+ " : " + $6.sval + "){" + $9.sval + "\n}\n";}
                   |    error  	{yyerror("Error at line: " + getLine(), getLine() );  }
                ;

loop_id         :       IDENTIFIER          {ba.typeTrack.addID($1.sval,"TypedParserVal",$1.line,Scope.LOCAL.getName());}
                ;

return_statement	:	RETURN exp 		{$$.sval = "return " + $2.sval;}
                        |       RETURN complex_type_method_invocation   {$$.sval = "return " +$2.sval;}
                        ;

function_call	:	IDENTIFIER LPAREN params RPAREN {$$.sval = $1.sval + "(" + $3.sval + ")";
                                                            $$.obj = ba.typeTrack.getType($1, Scope.GLOBAL.getName());
                                                            $$.line = $1.line;}
                        ;


//concrete list of functionality
complex_type_method_invocation
		:	IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, Scope.LOCAL.getName(), $5.sval);
                                                                        $$.obj = "void";}
		|	IDENTIFIER DOT IDENTIFIER LPAREN RPAREN {$$.sval = ba.createComplexTypeMethodInvocation($1.sval, $3.sval, Scope.LOCAL.getName(), null);
                                                                        $$.obj = "void";}
		;


aparams         :	NOTHING				{$$.sval = "";}
                |       aparams_                        {$$.sval = $1.sval;}
                ;

aparams_	:	type IDENTIFIER			{ba.typeTrack.addID($2.sval,$1.obj.toString(),$2.line,Scope.LOCAL.getName());
                                                            $$.sval = $1.sval + " " + $2.sval;}
		|	type IDENTIFIER COMMA aparams_     {ba.typeTrack.addID($2.sval,$1.obj.toString(),$2.line,Scope.LOCAL.getName());
                                                            $$.sval = $1.sval + " " + $2.sval + ", " + $4.sval;}
        |    error 	{yyerror("Error at line: " + getLine(), getLine() );  }
		;

params          :       NOTHING				{$$.sval = "";}
                |                                       {$$.sval = "";}
                |       params_                         {$$.sval = $1.sval;}
                ;

params_          :	exp			{$$.sval = $1.sval;}
		|	params_ COMMA params_ 		{$$.sval = $1.sval + "," + $3.sval;}
		|    error  	{yyerror("Error at line: " + getLine(), getLine() );  }
		;
		
hash_params		: 	hash_params COMMA LPAREN exp COMMA exp RPAREN	{$$.sval = "(" + $4.sval + "," + $6.sval + ")" + $1.sval;}
                |	LPAREN exp COMMA exp RPAREN		{$$.sval = "(" + $2.sval + "," + $4.sval + ")";}
                   |    error  	{yyerror("Error at line: " + getLine(), getLine() );  }      
                ;


type		:   BOOLEAN		{$$.sval = "boolean "; $$.obj = "boolean";}
            |	STRING		{$$.sval = "String "; $$.obj = "string";}
            |	NUMBER		{$$.sval = "double "; $$.obj = "number";}
            |   ARRAY           {$$.sval = "ArrayList "; $$.obj = "ArrayList";}
            |   HASH            {$$.sval = "HashMap "; $$.obj = "HashMap";}
            |    error  	{yyerror("Error at line: " + getLine(), getLine() );  }
            ;

return_type     :       type            {$$.sval = $1.sval; $$.obj = $1.obj;}
                |       NOTHING         {$$.sval = "void"; $$.obj = "void";}
                |    error  	{yyerror("Error at line: " + getLine(), getLine() );  }
                ;

exp	: 	exp LOG_OP_OR term {ba.typeTrack.assertBoolType($1,$3,$2);
                                                        $$.sval = $1.sval + " || " + $3.sval;
                                                        $$.obj = $1.obj;
                                                        $$.line = $1.line;}
                |     exp PLUS term {$$.sval = $1.sval + "+" + $3.sval;
                                              ba.typeTrack.assertNumberOrStringType($1,$3, $2);
                                                $$.obj = $1.obj;
                                                $$.line = $1.line; }
                |	exp MINUS term {$$.sval = $1.sval + " - " + $3.sval;
                                              ba.typeTrack.assertNumberType($1,$3, $2);
                                                $$.obj = $1.obj;
                                                $$.line = $1.line; }
                |	term	 {$$.sval = $1.sval;
                                    $$.line = $1.line;}
                | error  	{yyerror("Error at line: " + getLine(), getLine() );  }
                ;
			
term		:	term LOG_OP_AND unary {ba.typeTrack.assertBoolType($1,$3,$2);
                                                $$.sval = $1.sval + " && " + $3.sval;
                                                $$.obj = $1.obj;
                                                $$.line = $1.line; }
                |       term MUL unary {$$.sval = $1.sval + " * " + $3.sval;
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
                   |    error  	{yyerror("Error at line: " + getLine(), getLine() );  }
                ;

unary		:	LOG_OP_NOT unary {ba.typeTrack.assertBoolType($2);
                                                $$.sval = " !" + $2.sval;
                                                $$.obj = $2.obj;
                                                $$.line = $1.line;}
                |       MINUS unary {  ba.typeTrack.assertNumberType($2);
                                        $$.sval = " -"+ $2.sval;
                                        $$.obj = $2.obj;
                                        $$.line = $2.line;}
                |	factor	 {$$.sval = $1.sval;
                                    $$.obj = $1.obj;
                                    $$.line = $1.line;}
                |    error  	{yyerror("Error at line: " + getLine(), getLine() );  }
                ;

factor 		: 	LPAREN exp RPAREN	{$$.sval = " ( " + $2.sval + " ) ";
                                                    $$.obj = $2.obj;
                                                    $$.line = $1.line; }
                |       exp rel_op exp  {ba.typeTrack.assertNumberOrStringType($1,$3,$2);
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
                |	TRUE		{$$.sval = $1.sval;
                                                $$.obj = $1.obj;
                                                $$.line = $1.line;}
                |	FALSE		{$$.sval = $1.sval;
                                                $$.obj = $1.obj;
                                                $$.line = $1.line;}
              |    error  	{yyerror("Error at line: " + getLine(), getLine() );  }
                                                              
                ;

rel_op          :       REL_OP_LT		{$$.sval = "<";}
                |       REL_OP_GT		{$$.sval = ">";}
                |       REL_OP_LE		{$$.sval = "<=";}
                |       REL_OP_GE		{$$.sval = ">=";}
                |       LOG_OP_EQUAL		{$$.sval = "==";}
                |       LOG_OP_NOT LOG_OP_EQUAL	{$$.sval = "!=";}
                ;


%%

void yyerror(String s, int i){
	System.out.println(s);
	ba.errors=true;
	ba.caught_syntax_erros.add(i);
	
}

void yyerror(String s){
	System.out.println(s);
}

protected Parser(Reader r) {
	super(r);
	//Yylex lexer = new Yylex(r, this);
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