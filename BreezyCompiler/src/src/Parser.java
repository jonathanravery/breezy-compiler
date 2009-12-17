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
public final static short ACCEPTS=263;
public final static short BEGIN=264;
public final static short EACH=265;
public final static short ELSE=266;
public final static short END=267;
public final static short FOR=268;
public final static short FUNCTION=269;
public final static short IF=270;
public final static short NOTHING=271;
public final static short NUMERIC=272;
public final static short QUOTE=273;
public final static short RETURN=274;
public final static short RETURNS=275;
public final static short WHILE=276;
public final static short PLUS=277;
public final static short MINUS=278;
public final static short EQUAL=279;
public final static short REL_OP_LE=280;
public final static short REL_OP_LT=281;
public final static short REL_OP_GE=282;
public final static short REL_OP_GT=283;
public final static short EQUALS=284;
public final static short LOG_OP_EQUAL=285;
public final static short LOG_OP_AND=286;
public final static short LOG_OP_OR=287;
public final static short LOG_OP_NOT=288;
public final static short MUL=289;
public final static short DIV=290;
public final static short MOD=291;
public final static short LPAREN=292;
public final static short RPAREN=293;
public final static short COLON=294;
public final static short SEMICOLON=295;
public final static short COMMA=296;
public final static short DOT=297;
public final static short TRUE=298;
public final static short FALSE=299;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    5,    5,    6,    6,    6,    6,
    6,    8,    9,    9,    9,   14,   15,   15,    7,    7,
   17,   17,   17,   17,   17,   17,   17,   21,   20,   20,
   20,   20,   19,   19,   19,   18,    4,    4,    4,   16,
   16,   16,   16,   16,   10,   10,   10,   10,    3,    3,
   11,   11,   11,   12,   12,   12,   23,   23,   23,   23,
   24,   24,   25,   25,   25,   25,   22,   13,   13,   13,
   13,   13,   13,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    2,    2,    3,    3,
    0,    2,    4,    4,    4,    3,    7,    6,    1,    2,
    1,    1,    1,    1,    1,    1,    2,    8,    3,    3,
    3,    2,    4,    4,    4,    5,    1,    2,    4,    1,
    1,    1,    1,    3,    1,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    1,    3,    3,    3,    1,
    2,    1,    3,    1,    1,    1,    1,    3,    3,    2,
    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   45,   46,
   47,   48,   50,    0,   49,    0,   37,    0,    0,    0,
    0,   38,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   64,    0,
    0,    0,    0,    0,    0,    0,   22,   23,   19,    0,
   24,   25,   26,    0,   60,   62,    7,    8,   12,   39,
    0,    0,    0,    4,    0,    0,    0,    0,    0,    0,
    0,    0,   32,    0,   66,   61,    0,    0,   20,    9,
   10,    0,    0,   27,    0,    0,    0,    0,    0,   72,
   73,    0,    0,   51,    0,   52,    0,    0,    0,    0,
   40,   43,   42,   41,    0,    0,   16,    0,    0,   29,
   30,   31,   63,    0,    0,   57,   58,   59,    0,    0,
    0,    0,    0,   35,   34,   33,    0,    0,    0,    0,
   71,    0,    0,    0,   36,    0,    0,    0,    0,   18,
    0,    0,   17,    0,   28,
};
final static short yydgoto[] = {                          2,
    3,    4,   14,   18,   26,   27,   43,   28,   29,   30,
   95,   46,   92,   47,   48,  105,   49,   75,   51,   52,
   53,  109,   54,   55,   56,
};
final static short yysindex[] = {                      -254,
 -252,    0, -254,    0, -243,    0, -242, -121,    0,    0,
    0,    0,    0, -223,    0, -117,    0, -260, -179, -100,
  -96,    0, -172, -166, -161, -193, -202, -185, -171, -131,
 -110, -132, -128, -107,  -80, -278,  -79, -112,    0, -155,
 -194, -194, -197, -114, -113, -266,    0,    0,    0,    0,
    0,    0,    0, -282,    0,    0,    0,    0,    0,    0,
 -141, -159, -194,    0, -136, -230,  -75, -111, -141, -169,
 -109, -108,    0, -104,    0,    0, -211,  -79,    0,    0,
    0, -194, -194,    0, -194, -194, -194, -141, -141,    0,
    0, -152, -104,    0,  -92,    0, -106, -105, -103, -102,
    0,    0,    0,    0, -164, -101,    0, -152,  -99,    0,
    0,    0,    0, -282, -282,    0,    0,    0, -152, -224,
 -141, -141, -159,    0,    0,    0,  -98, -230, -248,  -69,
    0, -152, -152,  -92,    0,  -97,  -95, -147, -100,    0,
  -94,  -78,    0,  -74,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  198,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -187,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -170,    0,    0,    0,    0,
    0,    0,  -65,    0,    0,    0,    0,    0,    0, -240,
    0,    0,    0, -184,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -189,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -91,    0,    0,  -90,    0,  -89,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -86,    0,    0,
    0,    0,    0, -165, -162,    0,    0,    0, -140,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -126, -125,  -87,    0,  -84,    0,    0, -187,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  200,    0,    0,   71,    0,    0,  184,  185,   23,
   90,  -37,  -68,    0,    0,   45,  171,  -27,    0,    0,
    0,    0,   93,  -39,    0,
};
final static int YYTABLESIZE=214;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
  108,   76,    1,   20,   77,   65,   85,   86,   87,  101,
   82,   83,   73,   66,    7,   50,    5,   21,   67,  119,
  120,   21,  102,  103,  104,   97,   21,  101,   84,   21,
   15,   21,    8,   21,   96,   21,   66,   21,   19,   16,
  102,  103,  104,   31,  137,  116,  117,  118,   66,   66,
   66,   21,  132,  133,   66,   36,   23,   24,   25,   37,
   36,  121,  122,   74,   78,   82,   83,   38,  131,   39,
   11,   40,   38,   35,   39,   41,   40,   39,   22,    6,
   41,  113,   11,   41,   11,   32,   11,   65,   65,   42,
   11,   33,   56,   56,   42,   96,   34,   42,   93,   65,
   65,   65,   70,   65,   11,   65,   65,   65,   56,   57,
   56,   54,   54,   94,   55,   55,   71,   72,   65,   65,
   65,   98,   66,   58,   65,  110,   59,   54,  127,   54,
   55,  128,   55,  121,  122,   99,  100,    9,   10,   11,
   12,    9,   10,   11,   12,  141,   88,   60,  128,   13,
   89,   61,   70,   17,   70,   62,   90,   91,   23,   24,
   25,   12,    9,   10,   11,   12,   69,   68,   69,   68,
   82,   83,  136,  138,  114,  115,   63,   64,   68,   69,
   80,   81,  106,  107,  123,  111,  112,   66,  144,  124,
  129,  125,  126,  130,  139,  145,  135,    1,  128,  140,
  143,    5,    6,   15,   13,   14,   67,   53,   44,  142,
   44,   45,  134,   79,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         27,
   69,   41,  257,  264,   42,  284,  289,  290,  291,  258,
  277,  278,   40,  292,  258,   43,  269,  258,  297,   88,
   89,  262,  271,  272,  273,   63,  267,  258,  295,  270,
    8,  272,  275,  274,   62,  296,  277,  278,   16,  263,
  271,  272,  273,   21,  293,   85,   86,   87,  289,  290,
  291,  292,  121,  122,  295,  258,  259,  260,  261,  262,
  258,  286,  287,  258,  262,  277,  278,  270,  293,  272,
  258,  274,  270,  267,  272,  278,  274,  272,  258,  267,
  278,  293,  270,  278,  272,  258,  274,  277,  278,  292,
  278,  258,  277,  278,  292,  123,  258,  292,  258,  289,
  290,  291,  258,  293,  292,  295,  277,  278,  293,  295,
  295,  277,  278,  273,  277,  278,  272,  273,  289,  290,
  291,  258,  292,  295,  295,  295,  258,  293,  293,  295,
  293,  296,  295,  286,  287,  272,  273,  259,  260,  261,
  262,  259,  260,  261,  262,  293,  288,  258,  296,  271,
  292,  284,  293,  271,  295,  284,  298,  299,  259,  260,
  261,  262,  259,  260,  261,  262,  293,  293,  295,  295,
  277,  278,  128,  129,   82,   83,  284,  258,  258,  292,
  295,  295,  258,  295,  277,  295,  295,  292,  267,  295,
  292,  295,  295,  293,  264,  270,  295,    0,  296,  295,
  295,  267,    3,  295,  295,  295,  293,  295,  293,  139,
   27,   27,  123,   43,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=299;
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
"ACCEPTS","BEGIN","EACH","ELSE","END","FOR","FUNCTION","IF","NOTHING","NUMERIC",
"QUOTE","RETURN","RETURNS","WHILE","PLUS","MINUS","EQUAL","REL_OP_LE",
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
"type_declaration : type IDENTIFIER",
"type_declaration_assignment : STRING IDENTIFIER EQUALS string_exp",
"type_declaration_assignment : NUMBER IDENTIFIER EQUALS arith_exp",
"type_declaration_assignment : BOOLEAN IDENTIFIER EQUALS bool_exp",
"complex_type_declaration : ARRAY IDENTIFIER SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
"control_body : statement",
"control_body : control_body statement",
"statement : function_declaration",
"statement : complex_type_declaration",
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
"return_type : type",
"return_type : NOTHING",
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

//#line 174 "Breezy.yacc"

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
//#line 401 "Parser.java"
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
{System.out.println("Used type_d"); yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 13:
//#line 59 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 14:
//#line 60 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 15:
//#line 61 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 16:
//#line 64 "Breezy.yacc"
{yyval.sval = ba.createComplexType(val_peek(2).sval, val_peek(1).sval);}
break;
case 17:
//#line 69 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 18:
//#line 70 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 19:
//#line 74 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 20:
//#line 75 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 21:
//#line 79 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 22:
//#line 80 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 23:
//#line 81 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 24:
//#line 82 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 25:
//#line 83 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 26:
//#line 84 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 27:
//#line 85 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 28:
//#line 91 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(5).sval + "){\n" + val_peek(2).sval + "}\n";}
break;
case 29:
//#line 94 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 30:
//#line 95 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 31:
//#line 96 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 32:
//#line 97 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval + "\n";}
break;
case 33:
//#line 100 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 34:
//#line 101 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 35:
//#line 102 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 36:
//#line 105 "Breezy.yacc"
{yyval.sval = val_peek(4).sval + "(" + val_peek(2).sval + ");\n";}
break;
case 37:
//#line 109 "Breezy.yacc"
{yyval.sval = "";}
break;
case 38:
//#line 110 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 39:
//#line 111 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + ", " + val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 40:
//#line 115 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 41:
//#line 116 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 42:
//#line 117 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 43:
//#line 118 "Breezy.yacc"
{yyval.sval = "";}
break;
case 44:
//#line 119 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 45:
//#line 123 "Breezy.yacc"
{yyval.sval = "boolean";}
break;
case 46:
//#line 124 "Breezy.yacc"
{yyval.sval = "String";}
break;
case 47:
//#line 125 "Breezy.yacc"
{yyval.sval = "double";}
break;
case 48:
//#line 126 "Breezy.yacc"
{yyval.sval = "ArrayList";}
break;
case 49:
//#line 129 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 50:
//#line 130 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 51:
//#line 132 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 133 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 53:
//#line 134 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;}
break;
case 54:
//#line 137 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval; }
break;
case 55:
//#line 138 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval; }
break;
case 56:
//#line 139 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 57:
//#line 142 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval; }
break;
case 58:
//#line 143 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval; }
break;
case 59:
//#line 144 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval; }
break;
case 60:
//#line 145 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 61:
//#line 148 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval;}
break;
case 62:
//#line 149 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 63:
//#line 152 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 64:
//#line 153 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 65:
//#line 154 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 66:
//#line 155 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 67:
//#line 158 "Breezy.yacc"
{yyval = val_peek(0);}
break;
case 68:
//#line 163 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 69:
//#line 164 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 70:
//#line 165 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; }
break;
case 71:
//#line 166 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 72:
//#line 167 "Breezy.yacc"
{yyval.sval = "true";}
break;
case 73:
//#line 168 "Breezy.yacc"
{yyval.sval = "false";}
break;
//#line 842 "Parser.java"
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
