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
public final static short ACCEPTS=258;
public final static short BEGIN=259;
public final static short BOOLEAN=260;
public final static short EACH=261;
public final static short ELSE=262;
public final static short END=263;
public final static short FOR=264;
public final static short FUNCTION=265;
public final static short IF=266;
public final static short NOTHING=267;
public final static short NUMERIC=268;
public final static short QUOTE=269;
public final static short RETURN=270;
public final static short RETURNS=271;
public final static short WHILE=272;
public final static short PLUS=273;
public final static short MINUS=274;
public final static short EQUAL=275;
public final static short REL_OP_LE=276;
public final static short REL_OP_LT=277;
public final static short REL_OP_GE=278;
public final static short REL_OP_GT=279;
public final static short EQUALS=280;
public final static short LOG_OP_EQUAL=281;
public final static short LOG_OP_AND=282;
public final static short LOG_OP_OR=283;
public final static short LOG_OP_NOT=284;
public final static short MUL=285;
public final static short DIV=286;
public final static short MOD=287;
public final static short LPAREN=288;
public final static short RPAREN=289;
public final static short COLON=290;
public final static short SEMICOLON=291;
public final static short COMMA=292;
public final static short DOT=293;
public final static short TRUE=294;
public final static short FALSE=295;
public final static short IDENTIFIER=296;
public final static short ARRAY=297;
public final static short STRING=298;
public final static short NUMBER=299;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    5,    5,    6,    6,    6,    6,
    6,    8,    9,    9,    7,    7,   11,   11,   11,   11,
   11,   11,   11,   17,   16,   16,   16,   16,   15,   15,
   15,   12,    4,    4,    4,   20,   20,   20,   20,   20,
   10,   10,   10,    3,    3,   13,   14,   14,   18,   18,
   18,   21,   21,   21,   21,   22,   22,   23,   23,   23,
   23,   19,   24,   24,   24,   24,   24,   24,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    2,    2,    3,    3,
    0,    2,    3,    3,    1,    2,    1,    1,    1,    1,
    1,    1,    2,    8,    3,    3,    3,    2,    4,    4,
    4,    5,    1,    2,    4,    1,    1,    1,    1,    3,
    1,    1,    1,    1,    1,    3,    7,    6,    3,    3,
    1,    3,    3,    3,    1,    2,    1,    3,    1,    1,
    1,    1,    3,    3,    2,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   41,   45,
   42,   43,    0,   44,    0,   33,    0,    0,    0,    0,
   34,    0,    0,    0,    0,    0,    0,    0,    0,   59,
    0,    0,    0,    0,    0,    0,   15,    0,   18,   19,
   20,   21,   22,    0,    0,   55,   57,    0,    0,    0,
   12,   35,    4,    0,    0,    0,    0,   28,    0,   61,
   56,    0,    0,    0,    0,    0,   16,    0,    0,   23,
    0,    0,    0,   14,   13,    9,   10,    0,    0,   67,
   68,    0,    0,   26,   27,   25,   58,    0,    0,    0,
   39,   38,   37,   36,    0,    0,   46,    0,    0,   52,
   53,   54,    0,    0,    0,    0,    0,   30,   29,   31,
    0,    0,    0,   66,    0,    0,    0,   32,    0,    0,
    0,    0,   48,    0,    0,   47,   24,
};
final static short yydgoto[] = {                          2,
    3,    4,   13,   17,   22,   23,   36,   24,   25,   26,
   37,   60,   39,   40,   41,   42,   43,   44,   82,   95,
   45,   46,   47,   83,
};
final static short yysindex[] = {                      -245,
 -260,    0, -245,    0, -282,    0, -218, -256,    0,    0,
    0,    0, -196,    0, -254,    0, -258, -211, -252, -252,
    0, -173, -202, -191, -178, -141, -136, -135, -145,    0,
 -266, -171, -171, -273, -133, -202,    0,    0,    0,    0,
    0,    0,    0, -264, -153,    0,    0, -250, -252, -252,
    0,    0,    0, -138, -129, -127, -253,    0, -123,    0,
    0, -257, -181, -244, -130, -124,    0, -171, -171,    0,
 -171, -171, -171,    0,    0,    0,    0, -138, -138,    0,
    0, -121, -134,    0,    0,    0,    0, -122, -120, -119,
    0,    0,    0,    0, -222, -118,    0, -153, -153,    0,
    0,    0, -134, -261,  -86, -138, -138,    0,    0,    0,
 -117, -244, -212,    0, -252, -134, -134,    0, -116, -114,
 -151,  -88,    0, -113,  -87,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  180,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -205,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -167,    0,  -82,    0, -237,    0,    0,
    0,    0,    0,    0, -152,    0,    0,    0, -195, -192,
    0,    0,    0,    0,    0,    0,    0,    0, -175,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -107,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -147, -144,    0,
    0,    0, -106,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -205, -105, -104,    0, -103,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  184,    0,    0,   73,  102,    0,    0,    0,  120,
  153,  100,    0,    0,    0,    0,    0,  157,    0,   41,
   90,  -32,    0,    2,
};
final static int YYTABLESIZE=190;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         61,
   19,   55,   56,    9,    5,    9,   63,    9,   68,   69,
   10,    1,   16,    7,   64,   68,   69,   74,   75,   65,
  106,  107,   91,   92,   93,   17,   70,  114,   17,   57,
   17,   87,   17,   20,   64,   61,   17,   86,  100,  101,
  102,   11,   12,   11,   12,   11,   12,   61,   61,   61,
   17,   94,    8,   61,   91,   92,   93,    6,   17,   17,
   11,   15,   11,   29,   11,   30,  111,   31,   11,  112,
    7,   32,    7,    8,    7,    8,  120,    8,    7,  103,
  104,    8,   11,   94,   21,   33,   88,   89,   48,   28,
   11,   11,    7,   34,   35,    8,   30,   60,   60,   49,
    7,    7,   32,    8,    8,   60,   60,  116,  117,   60,
   60,   60,   50,   60,   90,   60,   33,   60,   60,   60,
   51,   51,   38,   60,   59,   49,   49,   14,   50,   50,
   58,   71,   72,   73,   18,   38,   51,  124,   51,   27,
  112,   49,   54,   49,   50,   78,   50,  106,  107,   79,
   76,   77,  119,  121,   51,   80,   81,   98,   99,   52,
   53,   84,   66,   85,   64,   96,   97,  105,  108,  113,
  109,  110,  115,  118,  125,  112,  123,  126,  127,    1,
    5,   62,   65,   64,   63,   40,    6,  122,   67,   62,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         32,
  259,  268,  269,  260,  265,  260,  280,  260,  273,  274,
  267,  257,  267,  296,  288,  273,  274,  268,  269,  293,
  282,  283,  267,  268,  269,  263,  291,  289,  266,  296,
  268,  289,  270,  292,  288,  273,  274,  291,   71,   72,
   73,  298,  299,  298,  299,  298,  299,  285,  286,  287,
  288,  296,  271,  291,  267,  268,  269,  263,  296,  297,
  266,  258,  268,  266,  270,  268,  289,  270,  274,  292,
  266,  274,  268,  266,  270,  268,  289,  270,  274,   78,
   79,  274,  288,  296,  296,  288,  268,  269,  280,  263,
  296,  297,  288,  296,  297,  288,  268,  273,  274,  291,
  296,  297,  274,  296,  297,  273,  274,  106,  107,  285,
  286,  287,  291,  289,  296,  291,  288,  285,  286,  287,
  273,  274,   23,  291,  296,  273,  274,    8,  273,  274,
   31,  285,  286,  287,   15,   36,  289,  289,  291,   20,
  292,  289,  288,  291,  289,  284,  291,  282,  283,  288,
   49,   50,  112,  113,  296,  294,  295,   68,   69,  296,
  296,  291,  296,  291,  288,  296,  291,  289,  291,  288,
  291,  291,  259,  291,  263,  292,  291,  291,  266,    0,
  263,  289,  289,  289,  289,  289,    3,  115,   36,   33,
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
null,null,null,"COMMENT","ACCEPTS","BEGIN","BOOLEAN","EACH","ELSE","END","FOR",
"FUNCTION","IF","NOTHING","NUMERIC","QUOTE","RETURN","RETURNS","WHILE","PLUS",
"MINUS","EQUAL","REL_OP_LE","REL_OP_LT","REL_OP_GE","REL_OP_GT","EQUALS",
"LOG_OP_EQUAL","LOG_OP_AND","LOG_OP_OR","LOG_OP_NOT","MUL","DIV","MOD","LPAREN",
"RPAREN","COLON","SEMICOLON","COMMA","DOT","TRUE","FALSE","IDENTIFIER","ARRAY",
"STRING","NUMBER",
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
"declarations : type_declaration SEMICOLON declarations",
"declarations : type_declaration_assignment SEMICOLON declarations",
"declarations :",
"type_declaration : type IDENTIFIER",
"type_declaration_assignment : type_declaration EQUALS QUOTE",
"type_declaration_assignment : type_declaration EQUALS NUMERIC",
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
"return_type : type",
"return_type : NOTHING",
"complex_type_declaration : ARRAY IDENTIFIER SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
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

//#line 168 "Breezy.yacc"

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
//#line 382 "Parser.java"
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
//#line 24 "Breezy.yacc"
{ba.DumpFile(val_peek(0).sval);}
break;
case 2:
//#line 27 "Breezy.yacc"
{yyval.sval= val_peek(0).sval;}
break;
case 3:
//#line 28 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 4:
//#line 38 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6).sval,val_peek(4).sval,val_peek(2).sval,val_peek(0).sval); }
break;
case 5:
//#line 43 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 6:
//#line 44 "Breezy.yacc"
{yyval.sval = "";}
break;
case 7:
//#line 48 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 8:
//#line 49 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 9:
//#line 50 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 10:
//#line 51 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 11:
//#line 52 "Breezy.yacc"
{yyval.sval = "";}
break;
case 12:
//#line 56 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 13:
//#line 61 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 14:
//#line 62 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 15:
//#line 66 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 16:
//#line 67 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 17:
//#line 71 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 18:
//#line 72 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 19:
//#line 73 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 20:
//#line 74 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 21:
//#line 75 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 22:
//#line 76 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 23:
//#line 77 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 24:
//#line 83 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(5).sval + "){\n" + val_peek(2).sval + "}\n";}
break;
case 25:
//#line 86 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 26:
//#line 87 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 27:
//#line 88 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 28:
//#line 89 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval + "\n";}
break;
case 29:
//#line 92 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 30:
//#line 93 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 31:
//#line 94 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 32:
//#line 97 "Breezy.yacc"
{yyval.sval = val_peek(4).sval + "(" + val_peek(2).sval + ");\n";}
break;
case 33:
//#line 101 "Breezy.yacc"
{yyval.sval = "";}
break;
case 34:
//#line 102 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 35:
//#line 103 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + ", " + val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 36:
//#line 107 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 37:
//#line 108 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 38:
//#line 109 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 39:
//#line 110 "Breezy.yacc"
{yyval.sval = "";}
break;
case 40:
//#line 111 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 41:
//#line 115 "Breezy.yacc"
{yyval.sval = "boolean";}
break;
case 42:
//#line 116 "Breezy.yacc"
{yyval.sval = "String";}
break;
case 43:
//#line 117 "Breezy.yacc"
{yyval.sval = "double";}
break;
case 44:
//#line 120 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 45:
//#line 121 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 46:
//#line 123 "Breezy.yacc"
{yyval.sval = ba.createComplexType(val_peek(2).sval, val_peek(1).sval);}
break;
case 47:
//#line 127 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 48:
//#line 128 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 49:
//#line 131 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval; }
break;
case 50:
//#line 132 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval; }
break;
case 51:
//#line 133 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 136 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval; }
break;
case 53:
//#line 137 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval; }
break;
case 54:
//#line 138 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval; }
break;
case 55:
//#line 139 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 56:
//#line 142 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval;}
break;
case 57:
//#line 143 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 58:
//#line 146 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 59:
//#line 147 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 60:
//#line 148 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 61:
//#line 149 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 62:
//#line 152 "Breezy.yacc"
{yyval = val_peek(0);}
break;
case 63:
//#line 157 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 64:
//#line 158 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 65:
//#line 159 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; }
break;
case 66:
//#line 160 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 67:
//#line 161 "Breezy.yacc"
{yyval.sval = "true";}
break;
case 68:
//#line 162 "Breezy.yacc"
{yyval.sval = "false";}
break;
//#line 803 "Parser.java"
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
