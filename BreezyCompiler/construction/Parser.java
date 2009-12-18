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
public final static short ELSEIF=268;
public final static short END=269;
public final static short FOR=270;
public final static short FUNCTION=271;
public final static short IF=272;
public final static short NOTHING=273;
public final static short NUMERIC=274;
public final static short QUOTE=275;
public final static short RETURN=276;
public final static short RETURNS=277;
public final static short WHILE=278;
public final static short PLUS=279;
public final static short MINUS=280;
public final static short EQUAL=281;
public final static short REL_OP_LE=282;
public final static short REL_OP_LT=283;
public final static short REL_OP_GE=284;
public final static short REL_OP_GT=285;
public final static short EQUALS=286;
public final static short LOG_OP_EQUAL=287;
public final static short LOG_OP_AND=288;
public final static short LOG_OP_OR=289;
public final static short LOG_OP_NOT=290;
public final static short MUL=291;
public final static short DIV=292;
public final static short MOD=293;
public final static short LEFT_SQUARE_PAREN=294;
public final static short RIGHT_SQUARE_PAREN=295;
public final static short LPAREN=296;
public final static short RPAREN=297;
public final static short COLON=298;
public final static short SEMICOLON=299;
public final static short COMMA=300;
public final static short DOT=301;
public final static short TRUE=302;
public final static short FALSE=303;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    5,    5,    6,    6,    6,    6,
    6,    8,    8,    8,    8,    8,    9,    9,    9,    9,
   14,   14,    7,    7,    7,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   19,   20,   20,   21,   21,   22,
   18,   18,   18,   18,   17,   17,   17,   16,    4,    4,
    4,   13,   13,   13,   13,   13,   23,   23,   23,   23,
   23,    3,    3,   14,   14,   10,   10,   10,   11,   11,
   11,   24,   24,   24,   24,   25,   25,   26,   26,   26,
   26,   27,   12,   12,   12,   12,   12,   12,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    2,    2,    3,    3,
    0,    2,    2,    2,    2,    2,    4,    4,    4,    6,
    7,    6,    1,    2,    0,    1,    1,    1,    1,    1,
    2,    4,    4,    4,   10,    9,    0,    5,    0,    8,
    3,    3,    3,    2,    4,    4,    4,    5,    1,    2,
    4,    1,    1,    1,    1,    3,    1,    1,    1,    1,
    1,    1,    1,    7,    6,    1,    1,    3,    3,    3,
    1,    3,    3,    3,    1,    2,    1,    3,    1,    1,
    1,    1,    3,    3,    2,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   57,   58,
   59,   60,   61,   63,    0,   62,    0,   49,    0,    0,
    0,    0,   50,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   16,    0,    0,
    0,   79,    0,    0,    0,    0,    0,    0,    0,   27,
   23,    0,   28,   29,   30,    0,   75,   77,    7,    8,
   51,    0,    0,    0,    0,    4,    0,    0,    0,    0,
    0,    0,    0,   44,    0,   81,   76,    0,   24,    9,
   10,    0,    0,   31,    0,    0,    0,    0,    0,   87,
   88,    0,    0,   66,    0,   67,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   52,   55,   54,   53,
    0,    0,    0,   41,   42,   43,   78,    0,    0,   72,
   73,   74,    0,    0,    0,    0,    0,    0,   47,   46,
   45,   33,   32,   34,    0,    0,    0,    0,   86,    0,
    0,    0,   20,   48,    0,    0,    0,    0,   22,    0,
    0,   21,    0,    0,    0,    0,    0,    0,   35,    0,
    0,    0,    0,    0,    0,    0,   38,    0,    0,   36,
};
final static short yydgoto[] = {                          2,
    3,    4,   15,   19,   29,   30,   46,   31,   32,   95,
   49,  124,  111,   50,   51,   76,   53,   54,   55,  156,
  159,    0,   16,   56,   57,   58,    0,
};
final static short yysindex[] = {                      -247,
 -242,    0, -247,    0, -219,    0, -211,  -25,    0,    0,
    0,    0,    0,    0, -184,    0,  -20,    0, -258, -207,
   15,   23,    0, -185, -166, -154, -135, -129, -134, -148,
 -155, -139,  -91, -104,  -98,  -86,  -80,    0,  -43, -115,
  -77,    0, -128, -171, -171,  -79,  -75,  -50, -107,    0,
    0,    0,    0,    0,    0,   -4,    0,    0,    0,    0,
    0,  -36, -213, -171,  -26,    0, -250, -253,   -6,  -36,
 -285,  -34,  -28,    0,  -17,    0,    0, -181,    0,    0,
    0, -171, -171,    0, -171, -171, -171,  -36,  -36,    0,
    0,  -58,  -17,    0,   12,    0,   14, -253, -205,   -2,
   -1, -246, -264,  -76,  -19,    0,    0,    0,    0,    0,
 -180,    3, -113,    0,    0,    0,    0,   -4,   -4,    0,
    0,    0,  -58,  -42,  -36,  -36, -213, -158,    0,    0,
    0,    0,    0,    0,    1, -253, -226,   36,    0,  -58,
  -58,   12,    0,    0,    2,    4,  -68,  -79,    0,    5,
 -131,    0,   33,   38,   11,   42,  -36,   43,    0,  -16,
  -79,   45, -119,  -79,   44, -118,    0,   46,   38,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  312,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -106,    0,    0,    0,    0,    0,    0,    0,    0,   47,
    0,    0,    0,   16,   18,   19,   20,    0,    0,  -71,
    0,    0,    0,    0,    0,   51,    0,    0,    0,    0,
    0, -191,    0,    0,    0, -261,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -81,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   22,    0,    0,   24,    0,   25,    0,  -35,  -29,
   34,    0,    0,    0,    0,  -66,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -225, -204,    0,
    0,    0, -256,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -103,
  -38,   26,    0,    0,   -5,    0,    0,   47,    0,    0,
    0,    0,    0, -190,    0,  -89,    0,    0,    0,    0,
   47,    0,    0,   47,    0,    0,    0,    0, -190,    0,
};
final static short yygindex[] = {                         0,
    0,  319,    0,    0,    0,    0,   41,  296,  297,  -64,
  -41,  -61,  -67,    0,  -44,  -30,    0,    0,    0,  159,
    0,    0,  142,  213,  -27,    0,    0,
};
final static int YYTABLESIZE=328;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   92,   79,  103,   78,  107,  105,   21,   99,  113,    1,
   68,   75,   74,  114,  127,   52,   77,   71,   71,  108,
  109,  110,   97,  100,  101,  104,  123,   42,    5,   44,
  128,  107,   96,   44,  132,   71,  106,   71,    7,   88,
   85,   22,   85,   88,   93,  102,  108,  109,  110,  102,
   23,   90,   91,   69,   69,   90,   91,  120,  121,  122,
   78,   94,  142,  140,  141,    8,   26,   37,  145,  147,
  146,   69,   34,   69,   70,   70,   37,   26,   37,   17,
   26,   37,   26,   37,   26,   37,   75,   81,   26,   37,
   68,   35,   70,  129,   70,  160,   96,   82,   83,   81,
   81,   81,   42,   36,   26,   37,   79,   81,   44,   40,
   24,   25,   26,   27,   28,  117,  135,   52,   79,  136,
   52,   79,   37,   41,   45,   42,   40,   43,   38,   71,
   52,   44,   52,   52,   39,   52,  143,  153,   40,   40,
   41,  136,   42,   59,   43,   72,   73,   45,   44,  165,
  168,   11,   41,   41,   42,   42,   43,   43,   20,   60,
   44,   44,    6,   33,   45,   11,   61,   11,   39,   11,
   67,   82,   83,   11,  125,  126,   45,   45,   40,   39,
   68,   62,   39,  138,   39,   69,   39,   63,  151,   11,
   39,   84,   41,   84,   42,   84,   43,   80,   80,   64,
   44,  163,   82,   83,  166,   65,   39,   80,   80,   80,
   80,   80,   67,   81,   66,   80,   45,   80,   70,   80,
   80,   80,  133,   80,   81,   81,   81,   80,  150,  125,
  126,  136,   67,    9,   10,   11,   12,   13,    9,   10,
   11,   12,   13,   80,   80,  125,  126,   14,   81,   79,
   79,  112,   18,   88,  139,   80,   80,   80,   83,   89,
   83,   79,   79,   79,  115,   90,   91,   98,  125,  126,
  116,  125,  126,   24,   25,   26,   27,   28,   68,  134,
  162,    9,   10,   11,   12,   13,   85,   86,   87,   56,
  127,   56,   82,   83,  118,  119,  130,  131,  137,  144,
  148,  136,  149,  152,  154,  155,  157,  161,  158,  164,
  167,    1,   66,  169,   13,   25,   12,   14,   15,    5,
   19,    6,   17,   18,   68,   47,   48,  170,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         30,
   62,   46,   67,   45,  258,   67,  265,  258,   70,  257,
  296,  258,   43,  299,  279,   46,   44,  279,  280,  273,
  274,  275,   64,  274,  275,   67,   88,  274,  271,  280,
   98,  258,   63,  280,  299,  297,   67,  299,  258,  290,
  297,  300,  299,  290,  258,  296,  273,  274,  275,  296,
  258,  302,  303,  279,  280,  302,  303,   85,   86,   87,
  102,  275,  127,  125,  126,  277,  258,  258,  136,  137,
  297,  297,  258,  299,  279,  280,  267,  269,  269,  264,
  272,  272,  274,  274,  276,  276,  258,  279,  280,  280,
  296,  258,  297,  299,  299,  157,  127,  279,  280,  291,
  292,  293,  274,  258,  296,  296,  151,  299,  280,  258,
  259,  260,  261,  262,  263,  297,  297,  148,  163,  300,
  151,  166,  258,  272,  296,  274,  258,  276,  258,  258,
  161,  280,  163,  164,  269,  166,  295,  269,  258,  258,
  272,  300,  274,  299,  276,  274,  275,  296,  280,  269,
  269,  258,  272,  272,  274,  274,  276,  276,   17,  299,
  280,  280,  269,   22,  296,  272,  258,  274,  258,  276,
  286,  279,  280,  280,  288,  289,  296,  296,  258,  269,
  296,  286,  272,  297,  274,  301,  276,  286,  148,  296,
  280,  299,  272,  297,  274,  299,  276,  279,  280,  286,
  280,  161,  279,  280,  164,  286,  296,  279,  280,  291,
  292,  293,  279,  280,  258,  297,  296,  299,  296,  291,
  292,  293,  299,  299,  291,  292,  293,  299,  297,  288,
  289,  300,  299,  259,  260,  261,  262,  263,  259,  260,
  261,  262,  263,  279,  280,  288,  289,  273,  299,  279,
  280,  258,  273,  290,  297,  291,  292,  293,  297,  296,
  299,  291,  292,  293,  299,  302,  303,  294,  288,  289,
  299,  288,  289,  259,  260,  261,  262,  263,  296,  299,
  297,  259,  260,  261,  262,  263,  291,  292,  293,  295,
  279,  297,  279,  280,   82,   83,  299,  299,  296,  299,
  265,  300,  299,  299,  272,  268,  296,  265,  267,  265,
  267,    0,  279,  268,  299,  269,  299,  299,  299,  269,
  299,    3,  299,  299,  299,   30,   30,  169,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=303;
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
"HASH","ACCEPTS","BEGIN","EACH","ELSE","ELSEIF","END","FOR","FUNCTION","IF",
"NOTHING","NUMERIC","QUOTE","RETURN","RETURNS","WHILE","PLUS","MINUS","EQUAL",
"REL_OP_LE","REL_OP_LT","REL_OP_GE","REL_OP_GT","EQUALS","LOG_OP_EQUAL",
"LOG_OP_AND","LOG_OP_OR","LOG_OP_NOT","MUL","DIV","MOD","LEFT_SQUARE_PAREN",
"RIGHT_SQUARE_PAREN","LPAREN","RPAREN","COLON","SEMICOLON","COMMA","DOT","TRUE",
"FALSE",
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
"if_statement : IF LPAREN bool_exp RPAREN BEGIN control_body END IF else_if else",
"else_if : ELSEIF LPAREN bool_exp RPAREN BEGIN control_body END ELSEIF else_if",
"else_if :",
"else : ELSE BEGIN control_body END ELSE",
"else :",
"while_loop : WHILE LPAREN bool_exp RPAREN BEGIN control_body END WHILE",
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

//#line 211 "Breezy.yacc"

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
//#line 451 "Parser.java"
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
int yyparse()
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
//#line 22 "Breezy.yacc"
{ba.DumpFile(val_peek(0).sval);}
break;
case 2:
//#line 25 "Breezy.yacc"
{yyval.sval= val_peek(0).sval;}
break;
case 3:
//#line 26 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 4:
//#line 36 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6).sval,val_peek(4).sval,val_peek(2).sval,val_peek(0).sval); }
break;
case 5:
//#line 41 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 6:
//#line 42 "Breezy.yacc"
{yyval.sval = "";}
break;
case 7:
//#line 47 "Breezy.yacc"
{System.out.println("Used first in declarations"); yyval.sval = val_peek(1).sval + ";\n";}
break;
case 8:
//#line 48 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 9:
//#line 49 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 10:
//#line 50 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 11:
//#line 51 "Breezy.yacc"
{yyval.sval = "";}
break;
case 12:
//#line 55 "Breezy.yacc"
{yyval.sval = "String " + val_peek(0).sval;}
break;
case 13:
//#line 56 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval;}
break;
case 14:
//#line 57 "Breezy.yacc"
{yyval.sval = "Number " + val_peek(0).sval;}
break;
case 15:
//#line 58 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval);}
break;
case 16:
//#line 59 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval);}
break;
case 17:
//#line 64 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 18:
//#line 65 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 19:
//#line 66 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 20:
//#line 67 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval);}
break;
case 21:
//#line 73 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 22:
//#line 74 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 23:
//#line 78 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 24:
//#line 79 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 25:
//#line 80 "Breezy.yacc"
{yyval.sval = "";}
break;
case 26:
//#line 84 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 27:
//#line 85 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 28:
//#line 86 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 29:
//#line 87 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 30:
//#line 88 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 31:
//#line 89 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 32:
//#line 90 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 33:
//#line 91 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 34:
//#line 92 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 35:
//#line 98 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 36:
//#line 105 "Breezy.yacc"
{ yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 37:
//#line 106 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 38:
//#line 113 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 39:
//#line 114 "Breezy.yacc"
{yyval.sval = "";}
break;
case 41:
//#line 124 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 42:
//#line 125 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 43:
//#line 126 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 44:
//#line 127 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval + "\n";}
break;
case 45:
//#line 130 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 46:
//#line 131 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 47:
//#line 132 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 48:
//#line 135 "Breezy.yacc"
{yyval.sval = val_peek(4).sval + "(" + val_peek(2).sval + ");\n";}
break;
case 49:
//#line 139 "Breezy.yacc"
{yyval.sval = "";}
break;
case 50:
//#line 140 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 51:
//#line 141 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + ", " + val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 52:
//#line 145 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 53:
//#line 146 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 54:
//#line 147 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 55:
//#line 148 "Breezy.yacc"
{yyval.sval = "";}
break;
case 56:
//#line 149 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 57:
//#line 153 "Breezy.yacc"
{yyval.sval = "boolean";}
break;
case 58:
//#line 154 "Breezy.yacc"
{yyval.sval = "String";}
break;
case 59:
//#line 155 "Breezy.yacc"
{yyval.sval = "double";}
break;
case 60:
//#line 156 "Breezy.yacc"
{yyval.sval = "ArrayList";}
break;
case 61:
//#line 157 "Breezy.yacc"
{yyval.sval = "HashMap";}
break;
case 62:
//#line 160 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 63:
//#line 161 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 64:
//#line 165 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 65:
//#line 166 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 66:
//#line 169 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 67:
//#line 170 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 68:
//#line 171 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;}
break;
case 69:
//#line 174 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval; }
break;
case 70:
//#line 175 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval; }
break;
case 71:
//#line 176 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 72:
//#line 179 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval; }
break;
case 73:
//#line 180 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval; }
break;
case 74:
//#line 181 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval; }
break;
case 75:
//#line 182 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 76:
//#line 185 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval;}
break;
case 77:
//#line 186 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 78:
//#line 189 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 79:
//#line 190 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 80:
//#line 191 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 81:
//#line 192 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 82:
//#line 195 "Breezy.yacc"
{yyval = val_peek(0);}
break;
case 83:
//#line 200 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 84:
//#line 201 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 85:
//#line 202 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; }
break;
case 86:
//#line 203 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 87:
//#line 204 "Breezy.yacc"
{yyval.sval = "true";}
break;
case 88:
//#line 205 "Breezy.yacc"
{yyval.sval = "false";}
break;
//#line 948 "Parser.java"
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
  yyparse();
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
