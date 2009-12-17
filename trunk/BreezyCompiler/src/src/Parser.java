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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    5,    5,    6,    6,    6,    6,
    6,    8,    8,    8,    8,    8,    9,    9,    9,   13,
   13,    7,    7,   15,   15,   15,   15,   15,   15,   19,
   18,   18,   18,   18,   17,   17,   17,   16,    4,    4,
    4,   14,   14,   14,   14,   14,   21,   21,   21,   21,
   21,    3,    3,   13,   13,   10,   10,   10,   11,   11,
   11,   22,   22,   22,   22,   23,   23,   24,   24,   24,
   24,   20,   12,   12,   12,   12,   12,   12,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    2,    2,    3,    3,
    0,    2,    2,    2,    2,    2,    4,    4,    4,    7,
    6,    1,    2,    1,    1,    1,    1,    1,    2,    8,
    3,    3,    3,    2,    4,    4,    4,    5,    1,    2,
    4,    1,    1,    1,    1,    3,    1,    1,    1,    1,
    1,    1,    1,    7,    6,    1,    1,    3,    3,    3,
    1,    3,    3,    3,    1,    2,    1,    3,    1,    1,
    1,    1,    3,    3,    2,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   47,   48,
   49,   50,   51,   53,    0,   52,    0,   39,    0,    0,
    0,    0,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   15,   16,    0,    0,
    0,   69,    0,    0,    0,    0,    0,    0,    0,   25,
   22,    0,   26,   27,   28,    0,   65,   67,    7,    8,
   41,    0,    0,    0,    4,    0,    0,    0,    0,    0,
    0,    0,   34,    0,   71,   66,    0,   23,    9,   10,
    0,    0,   29,    0,    0,    0,    0,    0,   77,   78,
    0,    0,   56,    0,   57,    0,    0,    0,    0,   42,
   45,   44,   43,    0,    0,    0,    0,   31,   32,   33,
   68,    0,    0,   62,   63,   64,    0,    0,    0,    0,
    0,   37,   36,   35,    0,    0,    0,    0,   76,    0,
    0,    0,   38,    0,    0,    0,    0,   21,    0,    0,
   20,    0,   30,
};
final static short yydgoto[] = {                          2,
    3,    4,   15,   19,   29,   30,   46,   31,   32,   94,
   49,   91,   50,  104,   51,   75,   53,   54,   55,  107,
   16,   56,   57,   58,
};
final static short yysindex[] = {                      -254,
 -256,    0, -254,    0, -240,    0, -222, -119,    0,    0,
    0,    0,    0,    0, -206,    0, -114,    0, -257, -169,
  -97,  -92,    0, -160, -145, -121, -101,  -99,  -93, -198,
 -213, -120,  -81, -107, -106, -105,    0,    0,  -77, -276,
 -111,    0, -156, -252, -252, -189, -113, -112, -267,    0,
    0,    0,    0,    0,    0, -118,    0,    0,    0,    0,
    0, -139, -248, -252,    0, -135, -138,  -73, -139, -289,
 -110, -109,    0, -104,    0,    0, -223,    0,    0,    0,
 -252, -252,    0, -252, -252, -252, -139, -139,    0,    0,
 -242, -104,    0,  -90,    0, -185, -103, -102, -100,    0,
    0,    0,    0, -263,  -98, -242,  -96,    0,    0,    0,
    0, -118, -118,    0,    0,    0, -242, -162, -139, -139,
 -248,    0,    0,    0,  -95, -138, -235,  -75,    0, -242,
 -242,  -90,    0,  -94,  -91, -218,  -97,    0,  -89,  -76,
    0,  -80,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  197,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -201,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -88,  -87,  -86,    0,    0,    0, -182,
    0,    0,    0,    0,    0,  -69,    0,    0,    0,    0,
    0, -243,    0,    0,    0, -172,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -191,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -85,    0,    0,  -84,    0,  -83,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -79,    0,    0,    0,    0,
    0, -167, -163,    0,    0,    0, -228,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -175,
 -166,  -82,    0,  -78,    0,    0, -201,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  199,    0,    0,   67,    0,    0,  170,  176,   96,
  -40,  -68,    0,   25,  172,  -30,    0,    0,    0,    0,
   63,   74,  -42,    0,
};
final static int YYTABLESIZE=218;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
  106,   76,    1,   67,   77,   74,  108,   21,   66,   92,
   81,   82,   73,    5,   24,   52,   67,    7,  117,  118,
   42,   68,  100,   96,   24,   93,   44,   24,   83,   24,
  125,   24,   95,  126,   71,   24,  101,  102,  103,   22,
   45,  114,  115,  116,  119,  120,   71,   71,   71,   24,
  130,  131,   71,    8,   81,   82,   11,   17,  135,   40,
   24,   25,   26,   27,   28,   75,    6,   75,   40,   11,
  111,   11,   41,   11,   42,  139,   43,   11,  126,   20,
   44,   41,   59,   42,   33,   43,   70,   70,   23,   44,
   95,   11,   81,   82,   45,   70,   70,   34,   70,   70,
   70,   70,   70,   45,   70,   61,   61,   70,   70,   70,
   59,   59,   35,   70,   60,   60,   71,   72,   74,  100,
   74,   61,   97,   61,  119,  120,   59,   73,   59,   73,
   60,  129,   60,  101,  102,  103,   36,   98,   99,    9,
   10,   11,   12,   13,    9,   10,   11,   12,   13,   87,
  134,  136,   14,   88,  112,  113,   37,   18,   38,   89,
   90,   24,   25,   26,   27,   28,    9,   10,   11,   12,
   13,   84,   85,   86,   39,   60,   61,   62,   63,   64,
   65,   69,   79,   80,  105,  109,  110,  121,   67,  137,
  143,  142,  122,  123,  127,  124,    1,  128,    5,   47,
  133,    6,  126,  140,  138,   48,  141,   13,   12,   14,
   19,   17,   18,   58,   72,   46,  132,   78,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         30,
   69,   44,  257,  293,   45,  258,  296,  265,  285,  258,
  278,  279,   43,  270,  258,   46,  293,  258,   87,   88,
  273,  298,  258,   64,  268,  274,  279,  271,  296,  273,
  294,  275,   63,  297,  278,  279,  272,  273,  274,  297,
  293,   84,   85,   86,  287,  288,  290,  291,  292,  293,
  119,  120,  296,  276,  278,  279,  258,  264,  294,  258,
  259,  260,  261,  262,  263,  294,  268,  296,  258,  271,
  294,  273,  271,  275,  273,  294,  275,  279,  297,   17,
  279,  271,  296,  273,   22,  275,  278,  279,  258,  279,
  121,  293,  278,  279,  293,  278,  279,  258,  290,  291,
  292,  258,  294,  293,  296,  278,  279,  290,  291,  292,
  278,  279,  258,  296,  278,  279,  273,  274,  294,  258,
  296,  294,  258,  296,  287,  288,  294,  294,  296,  296,
  294,  294,  296,  272,  273,  274,  258,  273,  274,  259,
  260,  261,  262,  263,  259,  260,  261,  262,  263,  289,
  126,  127,  272,  293,   81,   82,  258,  272,  258,  299,
  300,  259,  260,  261,  262,  263,  259,  260,  261,  262,
  263,  290,  291,  292,  268,  296,  258,  285,  285,  285,
  258,  293,  296,  296,  258,  296,  296,  278,  293,  265,
  271,  268,  296,  296,  293,  296,    0,  294,  268,   30,
  296,    3,  297,  137,  296,   30,  296,  296,  296,  296,
  296,  296,  296,  296,  294,  294,  121,   46,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=300;
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
"SEMICOLON","COMMA","DOT","TRUE","FALSE",
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
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
"control_body : statement",
"control_body : control_body statement",
"statement : function_declaration",
"statement : complex_type_method_invocation",
"statement : type_initialization",
"statement : return_statement",
"statement : if_statement",
"statement : arith_exp SEMICOLON",
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

//#line 182 "Breezy.yacc"

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
//#line 407 "Parser.java"
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
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 13:
//#line 55 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 14:
//#line 56 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 15:
//#line 57 "Breezy.yacc"
{yyval.sval = ba.createComplexType(val_peek(1).sval, val_peek(0).sval);}
break;
case 16:
//#line 58 "Breezy.yacc"
{yyval.sval = ba.createComplexType(val_peek(1).sval, val_peek(0).sval);}
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
//#line 71 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 21:
//#line 72 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 22:
//#line 76 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 23:
//#line 77 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 24:
//#line 81 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 25:
//#line 82 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
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
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 30:
//#line 92 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(5).sval + "){\n" + val_peek(2).sval + "}\n";}
break;
case 31:
//#line 95 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 32:
//#line 96 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 33:
//#line 97 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 34:
//#line 98 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval + "\n";}
break;
case 35:
//#line 101 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 36:
//#line 102 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 37:
//#line 103 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 38:
//#line 106 "Breezy.yacc"
{yyval.sval = val_peek(4).sval + "(" + val_peek(2).sval + ");\n";}
break;
case 39:
//#line 110 "Breezy.yacc"
{yyval.sval = "";}
break;
case 40:
//#line 111 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 41:
//#line 112 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + ", " + val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 42:
//#line 116 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 43:
//#line 117 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 44:
//#line 118 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 45:
//#line 119 "Breezy.yacc"
{yyval.sval = "";}
break;
case 46:
//#line 120 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 47:
//#line 124 "Breezy.yacc"
{yyval.sval = "boolean";}
break;
case 48:
//#line 125 "Breezy.yacc"
{yyval.sval = "String";}
break;
case 49:
//#line 126 "Breezy.yacc"
{yyval.sval = "double";}
break;
case 50:
//#line 127 "Breezy.yacc"
{yyval.sval = "ArrayList";}
break;
case 51:
//#line 128 "Breezy.yacc"
{yyval.sval = "HashMap";}
break;
case 52:
//#line 131 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 53:
//#line 132 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 54:
//#line 136 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 55:
//#line 137 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 56:
//#line 140 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 57:
//#line 141 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 58:
//#line 142 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;}
break;
case 59:
//#line 145 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval; }
break;
case 60:
//#line 146 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval; }
break;
case 61:
//#line 147 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 62:
//#line 150 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval; }
break;
case 63:
//#line 151 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval; }
break;
case 64:
//#line 152 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval; }
break;
case 65:
//#line 153 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 66:
//#line 156 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval;}
break;
case 67:
//#line 157 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 68:
//#line 160 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 69:
//#line 161 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 70:
//#line 162 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 71:
//#line 163 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 72:
//#line 166 "Breezy.yacc"
{yyval = val_peek(0);}
break;
case 73:
//#line 171 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 74:
//#line 172 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 75:
//#line 173 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; }
break;
case 76:
//#line 174 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 77:
//#line 175 "Breezy.yacc"
{yyval.sval = "true";}
break;
case 78:
//#line 176 "Breezy.yacc"
{yyval.sval = "false";}
break;
//#line 868 "Parser.java"
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
        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
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
