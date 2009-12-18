//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "Breezy.yacc"
package src;
import java.io.*;
/*Authored by Jon, Cesar, Vinay, Sharadh*/
/*Adapted to Breezy by Ian, Leighton, Jack, Jon, Elena, Clement*/
//#line 22 "Parser.java"




public class Parser
             extends Yylex
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short COMMENT=257;
public final static short IDENTIFIER=258;
public final static short BOOLEAN=259;
public final static short STRING=260;
public final static short NUMBER=261;
public final static short ARRAY=262;
public final static short HASH=263;
public final static short ACCEPTS=264;
public final static short BEGIN=265;
public final static short EACH=266;
public final static short ELSE=267;
public final static short END=268;
public final static short FOR=269;
public final static short FUNCTION=270;
public final static short IF=271;
public final static short NOTHING=272;
public final static short NUMERIC=273;
public final static short QUOTE=274;
public final static short RETURN=275;
public final static short RETURNS=276;
public final static short WHILE=277;
public final static short PLUS=278;
public final static short MINUS=279;
public final static short EQUAL=280;
public final static short REL_OP_LE=281;
public final static short REL_OP_LT=282;
public final static short REL_OP_GE=283;
public final static short REL_OP_GT=284;
public final static short EQUALS=285;
public final static short LOG_OP_EQUAL=286;
public final static short LOG_OP_AND=287;
public final static short LOG_OP_OR=288;
public final static short LOG_OP_NOT=289;
public final static short MUL=290;
public final static short DIV=291;
public final static short MOD=292;
public final static short LPAREN=293;
public final static short RPAREN=294;
public final static short COLON=295;
public final static short SEMICOLON=296;
public final static short COMMA=297;
public final static short DOT=298;
public final static short TRUE=299;
public final static short FALSE=300;
public final static short LEFT_SQUARE_PAREN=301;
public final static short RIGHT_SQUARE_PAREN=302;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    5,    5,    6,    6,    6,    6,
    6,    8,    8,    8,    8,    8,    9,    9,    9,    9,
   14,   14,    7,    7,    7,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   19,   18,   18,   18,   18,   17,
   17,   17,   16,    4,    4,    4,   13,   13,   13,   13,
   13,   21,   21,   21,   21,   21,    3,    3,   14,   14,
   10,   10,   10,   11,   11,   11,   22,   22,   22,   22,
   23,   23,   24,   24,   24,   24,   20,   12,   12,   12,
   12,   12,   12,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    2,    2,    3,    3,
    0,    2,    2,    2,    2,    2,    4,    4,    4,    6,
    7,    6,    1,    2,    0,    1,    1,    1,    1,    1,
    2,    4,    4,    4,    8,    3,    3,    3,    2,    4,
    4,    4,    5,    1,    2,    4,    1,    1,    1,    1,
    3,    1,    1,    1,    1,    1,    1,    1,    7,    6,
    1,    1,    3,    3,    3,    1,    3,    3,    3,    1,
    2,    1,    3,    1,    1,    1,    1,    3,    3,    2,
    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   52,   53,
   54,   55,   56,   58,    0,   57,    0,   44,    0,    0,
    0,    0,   45,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   16,    0,    0,
    0,   74,    0,    0,    0,    0,    0,    0,    0,   27,
   23,    0,   28,   29,   30,    0,   70,   72,    7,    8,
   46,    0,    0,    0,    0,    4,    0,    0,    0,    0,
    0,    0,    0,   39,    0,   76,   71,    0,   24,    9,
   10,    0,    0,   31,    0,    0,    0,    0,    0,   82,
   83,    0,    0,   61,    0,   62,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   47,   50,   49,   48,
    0,    0,    0,    0,   36,   37,   38,   73,    0,    0,
   67,   68,   69,    0,    0,    0,    0,    0,    0,   42,
   41,   40,   33,   32,   34,    0,    0,    0,    0,   81,
    0,    0,    0,   20,   43,    0,    0,    0,    0,   22,
    0,    0,   21,    0,   35,
};
final static short yydgoto[] = {                          2,
    3,    4,   15,   19,   29,   30,   46,   31,   32,   95,
   49,  125,  111,   50,   51,   76,   53,   54,   55,  114,
   16,   56,   57,   58,
};
final static short yysindex[] = {                      -246,
 -242,    0, -246,    0, -214,    0, -220,  -75,    0,    0,
    0,    0,    0,    0, -185,    0,  -60,    0, -255, -165,
  -45,  -39,    0, -125, -106, -102, -100,  -91, -195, -159,
  -85,  -77,  -87,  -58,  -57,  -56,  -55,    0,  -27, -161,
  -59,    0,  -96, -218, -218, -150,  -64,  -63, -191,    0,
    0,    0,    0,    0,    0,  -82,    0,    0,    0,    0,
    0,  -95, -240, -218,  -66,    0, -250,  -98,  -22,  -95,
 -199,  -54,  -53,    0,  -52,    0,    0, -243,    0,    0,
    0, -218, -218,    0, -218, -218, -218,  -95,  -95,    0,
    0,  -92,  -52,    0,  -41,    0,  -89,  -98, -186,  -51,
  -50, -241, -266, -124, -123,    0,    0,    0,    0,    0,
 -179,  -49,  -92,  -47,    0,    0,    0,    0,  -82,  -82,
    0,    0,    0,  -92,  -81,  -95,  -95, -240, -271,    0,
    0,    0,    0,    0,    0,  -48,  -98, -253,  -26,    0,
  -92,  -92,  -41,    0,    0,  -46,  -44, -175,  -45,    0,
  -43,  -28,    0,  -33,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  249,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -162,    0,    0,    0,    0,    0,    0,    0,    0,  -18,
    0,    0,    0,  -38,  -37,  -36,  -35,    0,    0, -152,
    0,    0,    0,    0,    0,  -14,    0,    0,    0,    0,
    0, -201,    0,    0,    0, -137,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -210,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -34,    0,    0,  -32,    0,  -31,    0, -109,  -99,
  -23,    0,    0,    0,    0, -143,    0,    0,    0,    0,
    0,    0,  -25,    0,    0,    0,    0,    0, -133, -128,
    0,    0,    0, -232,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -211, -166,  -29,    0,    0, -287,    0,    0, -162,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  253,    0,    0,  108,    0,    0,  233,  236,  -65,
  -42,  -61,  -84,    0,  222,  -30,    0,    0,    0,    0,
   54,  143,  -40,    0,
};
final static int YYTABLESIZE=269;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   92,  103,   78,   77,  107,  105,   51,   99,  113,   21,
    1,  128,   74,  129,   51,   52,   75,   93,  108,  109,
  110,   97,  100,  101,  104,  137,  124,    5,   44,  133,
  144,   42,   96,   94,   82,   83,  106,   44,   88,   75,
  147,   22,  102,    7,  121,  122,  123,   88,   90,   91,
  118,  102,  146,  148,   42,    8,   26,   90,   91,   78,
   44,   80,  143,   80,  141,  142,   26,   75,   75,   26,
   20,   26,   39,   26,   45,   33,   76,   26,   17,   75,
   75,   75,   79,   75,   79,   75,   82,   83,   76,   76,
   76,   26,   23,   68,   76,   11,  115,   96,   40,   24,
   25,   26,   27,   28,   84,    6,   68,   40,   11,  130,
   11,   41,   11,   42,  136,   43,   11,  137,  151,   44,
   41,  137,   42,   67,   43,   75,   75,   78,   44,   78,
   11,   68,   34,   45,   62,   76,   69,   75,   75,   75,
   66,   66,   45,   75,   64,   64,   76,   76,   76,   65,
   65,   35,   62,   82,   83,   36,   66,   37,   66,  107,
   64,   71,   64,  126,  127,   65,   38,   65,   75,   75,
   61,  134,  135,  108,  109,  110,   72,   73,   74,   74,
   75,   75,   75,    9,   10,   11,   12,   13,   82,   83,
   74,   74,   74,   88,  126,  127,   14,   89,    9,   10,
   11,   12,   13,   90,   91,  126,  127,   85,   86,   87,
   59,   18,  140,   24,   25,   26,   27,   28,   60,    9,
   10,   11,   12,   13,  119,  120,   62,   63,   64,   65,
   66,   80,   81,   70,   98,  112,  128,  155,  149,  154,
   68,  116,  117,  138,  131,  132,  139,  145,    1,   25,
  137,  150,  153,    5,   61,    6,  152,   13,   12,   14,
   15,   19,   47,   17,   18,   48,   63,   79,   77,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         30,
   62,   67,   45,   44,  258,   67,  294,  258,   70,  265,
  257,  278,   43,   98,  302,   46,  258,  258,  272,  273,
  274,   64,  273,  274,   67,  297,   88,  270,  279,  296,
  302,  273,   63,  274,  278,  279,   67,  279,  289,  258,
  294,  297,  293,  258,   85,   86,   87,  289,  299,  300,
  294,  293,  137,  138,  273,  276,  258,  299,  300,  102,
  279,  294,  128,  296,  126,  127,  268,  278,  279,  271,
   17,  273,  268,  275,  293,   22,  278,  279,  264,  290,
  291,  292,  294,  294,  296,  296,  278,  279,  290,  291,
  292,  293,  258,  293,  296,  258,  296,  128,  258,  259,
  260,  261,  262,  263,  296,  268,  293,  258,  271,  296,
  273,  271,  275,  273,  294,  275,  279,  297,  294,  279,
  271,  297,  273,  285,  275,  278,  279,  294,  279,  296,
  293,  293,  258,  293,  278,  279,  298,  290,  291,  292,
  278,  279,  293,  296,  278,  279,  290,  291,  292,  278,
  279,  258,  296,  278,  279,  258,  294,  258,  296,  258,
  294,  258,  296,  287,  288,  294,  258,  296,  278,  279,
  258,  296,  296,  272,  273,  274,  273,  274,  278,  279,
  290,  291,  292,  259,  260,  261,  262,  263,  278,  279,
  290,  291,  292,  289,  287,  288,  272,  293,  259,  260,
  261,  262,  263,  299,  300,  287,  288,  290,  291,  292,
  296,  272,  294,  259,  260,  261,  262,  263,  296,  259,
  260,  261,  262,  263,   82,   83,  285,  285,  285,  285,
  258,  296,  296,  293,  301,  258,  278,  271,  265,  268,
  293,  296,  296,  293,  296,  296,  294,  296,    0,  268,
  297,  296,  296,  268,  278,    3,  149,  296,  296,  296,
  296,  296,   30,  296,  296,   30,  296,   46,  294,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=302;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"COMMENT","IDENTIFIER","BOOLEAN","STRING","NUMBER","ARRAY",
"HASH","ACCEPTS","BEGIN","EACH","ELSE","END","FOR","FUNCTION","IF","NOTHING",
"NUMERIC","QUOTE","RETURN","RETURNS","WHILE","PLUS","MINUS","EQUAL","REL_OP_LE",
"REL_OP_LT","REL_OP_GE","REL_OP_GT","EQUALS","LOG_OP_EQUAL","LOG_OP_AND",
"LOG_OP_OR","LOG_OP_NOT","MUL","DIV","MOD","LPAREN","RPAREN","COLON",
"SEMICOLON","COMMA","DOT","TRUE","FALSE","LEFT_SQUARE_PAREN",
"RIGHT_SQUARE_PAREN",
};
final static String yyrule[] = {
"$accept : start",
"start : program",
"program : method",
"program : program method",
"method : COMMENT FUNCTION IDENTIFIER RETURNS return_type ACCEPTS aparams BEGIN body END IDENTIFIER",
"body : declarations control_body",
"body :",
"declarations : type_declaration SEMICOLON",
"declarations : type_declaration_assignment SEMICOLON",
"declarations : declarations type_declaration SEMICOLON",
"declarations : declarations type_declaration_assignment SEMICOLON",
"declarations :",
"type_declaration : STRING IDENTIFIER",
"type_declaration : BOOLEAN IDENTIFIER",
"type_declaration : NUMBER IDENTIFIER",
"type_declaration : ARRAY IDENTIFIER",
"type_declaration : HASH IDENTIFIER",
"type_declaration_assignment : STRING IDENTIFIER EQUALS string_exp",
"type_declaration_assignment : NUMBER IDENTIFIER EQUALS arith_exp",
"type_declaration_assignment : BOOLEAN IDENTIFIER EQUALS bool_exp",
"type_declaration_assignment : ARRAY IDENTIFIER EQUALS LEFT_SQUARE_PAREN params RIGHT_SQUARE_PAREN",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
"control_body : statement",
"control_body : control_body statement",
"control_body :",
"statement : function_declaration",
"statement : complex_type_method_invocation",
"statement : type_initialization",
"statement : return_statement",
"statement : if_statement",
"statement : arith_exp SEMICOLON",
"statement : IDENTIFIER EQUALS arith_exp SEMICOLON",
"statement : IDENTIFIER EQUALS string_exp SEMICOLON",
"statement : IDENTIFIER EQUALS bool_exp SEMICOLON",
"if_statement : IF LPAREN condition RPAREN BEGIN body END IF",
"return_statement : RETURN IDENTIFIER SEMICOLON",
"return_statement : RETURN NUMERIC SEMICOLON",
"return_statement : RETURN QUOTE SEMICOLON",
"return_statement : RETURN function_declaration",
"type_initialization : IDENTIFIER EQUALS QUOTE SEMICOLON",
"type_initialization : IDENTIFIER EQUALS NUMERIC SEMICOLON",
"type_initialization : IDENTIFIER EQUALS IDENTIFIER SEMICOLON",
"function_declaration : IDENTIFIER LPAREN params RPAREN SEMICOLON",
"aparams : NOTHING",
"aparams : type IDENTIFIER",
"aparams : aparams COMMA type IDENTIFIER",
"params : IDENTIFIER",
"params : QUOTE",
"params : NUMERIC",
"params : NOTHING",
"params : params COMMA params",
"type : BOOLEAN",
"type : STRING",
"type : NUMBER",
"type : ARRAY",
"type : HASH",
"return_type : type",
"return_type : NOTHING",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
"string_exp : QUOTE",
"string_exp : function_declaration",
"string_exp : string_exp PLUS string_exp",
"arith_exp : arith_exp PLUS term",
"arith_exp : arith_exp MINUS term",
"arith_exp : term",
"term : term MUL unary",
"term : term DIV unary",
"term : term MOD unary",
"term : unary",
"unary : MINUS unary",
"unary : factor",
"factor : LPAREN arith_exp RPAREN",
"factor : NUMERIC",
"factor : IDENTIFIER",
"factor : function_declaration",
"condition : bool_exp",
"bool_exp : bool_exp LOG_OP_OR bool_exp",
"bool_exp : bool_exp LOG_OP_AND bool_exp",
"bool_exp : LOG_OP_NOT bool_exp",
"bool_exp : LPAREN bool_exp RPAREN",
"bool_exp : TRUE",
"bool_exp : FALSE",
};

//#line 187 "Breezy.yacc"

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
//#line 430 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse() throws Exception
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 21 "Breezy.yacc"
{ba.DumpFile(val_peek(0).sval);}
break;
case 2:
//#line 24 "Breezy.yacc"
{yyval.sval= val_peek(0).sval;}
break;
case 3:
//#line 25 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 4:
//#line 35 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6).sval,val_peek(4).sval,val_peek(2).sval,val_peek(0).sval); }
break;
case 5:
//#line 40 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 6:
//#line 41 "Breezy.yacc"
{yyval.sval = "";}
break;
case 7:
//#line 46 "Breezy.yacc"
{System.out.println("Used first in declarations"); yyval.sval = val_peek(1).sval + ";\n";}
break;
case 8:
//#line 47 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 9:
//#line 48 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 10:
//#line 49 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 11:
//#line 50 "Breezy.yacc"
{yyval.sval = "";}
break;
case 12:
//#line 54 "Breezy.yacc"
{yyval.sval = "String " + val_peek(0).sval;}
break;
case 13:
//#line 55 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval;}
break;
case 14:
//#line 56 "Breezy.yacc"
{yyval.sval = "Number " + val_peek(0).sval;}
break;
case 15:
//#line 57 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval);}
break;
case 16:
//#line 58 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval);}
break;
case 17:
//#line 63 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 18:
//#line 64 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 19:
//#line 65 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 20:
//#line 66 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval);}
break;
case 21:
//#line 72 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 22:
//#line 73 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 23:
//#line 77 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 24:
//#line 78 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 25:
//#line 79 "Breezy.yacc"
{yyval.sval = "";}
break;
case 26:
//#line 83 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 27:
//#line 84 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 28:
//#line 85 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 29:
//#line 86 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 30:
//#line 87 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 31:
//#line 88 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 32:
//#line 89 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 33:
//#line 90 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 34:
//#line 91 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 35:
//#line 97 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(5).sval + "){\n" + val_peek(2).sval + "}\n";}
break;
case 36:
//#line 100 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 37:
//#line 101 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 38:
//#line 102 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 39:
//#line 103 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval + "\n";}
break;
case 40:
//#line 106 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 41:
//#line 107 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 42:
//#line 108 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 43:
//#line 111 "Breezy.yacc"
{yyval.sval = val_peek(4).sval + "(" + val_peek(2).sval + ");\n";}
break;
case 44:
//#line 115 "Breezy.yacc"
{yyval.sval = "";}
break;
case 45:
//#line 116 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 46:
//#line 117 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + ", " + val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 47:
//#line 121 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 48:
//#line 122 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 49:
//#line 123 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 50:
//#line 124 "Breezy.yacc"
{yyval.sval = "";}
break;
case 51:
//#line 125 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 52:
//#line 129 "Breezy.yacc"
{yyval.sval = "boolean";}
break;
case 53:
//#line 130 "Breezy.yacc"
{yyval.sval = "String";}
break;
case 54:
//#line 131 "Breezy.yacc"
{yyval.sval = "double";}
break;
case 55:
//#line 132 "Breezy.yacc"
{yyval.sval = "ArrayList";}
break;
case 56:
//#line 133 "Breezy.yacc"
{yyval.sval = "HashMap";}
break;
case 57:
//#line 136 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 58:
//#line 137 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 59:
//#line 141 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 60:
//#line 142 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 61:
//#line 145 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 62:
//#line 146 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 63:
//#line 147 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;}
break;
case 64:
//#line 150 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval; }
break;
case 65:
//#line 151 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval; }
break;
case 66:
//#line 152 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 67:
//#line 155 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval; }
break;
case 68:
//#line 156 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval; }
break;
case 69:
//#line 157 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval; }
break;
case 70:
//#line 158 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 71:
//#line 161 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval;}
break;
case 72:
//#line 162 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 73:
//#line 165 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 74:
//#line 166 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 75:
//#line 167 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 76:
//#line 168 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 77:
//#line 171 "Breezy.yacc"
{yyval = val_peek(0);}
break;
case 78:
//#line 176 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 79:
//#line 177 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 80:
//#line 178 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; }
break;
case 81:
//#line 179 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 82:
//#line 180 "Breezy.yacc"
{yyval.sval = "true";}
break;
case 83:
//#line 181 "Breezy.yacc"
{yyval.sval = "false";}
break;
//#line 911 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  try {
	yyparse();
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
