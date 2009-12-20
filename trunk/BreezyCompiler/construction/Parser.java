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
import libs.structs.Scope;
/*Authored by Jon, Cesar, Vinay, Sharadh*/
/*Adapted to Breezy by Ian, Leighton, Jack, Jon, Elena, Clement*/
//#line 23 "Parser.java"




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
public final static short TRUE=264;
public final static short FALSE=265;
public final static short ACCEPTS=266;
public final static short BEGIN=267;
public final static short EACH=268;
public final static short ELSE=269;
public final static short ELSEIF=270;
public final static short END=271;
public final static short FOR=272;
public final static short FUNCTION=273;
public final static short IF=274;
public final static short NOTHING=275;
public final static short NUMERIC=276;
public final static short QUOTE=277;
public final static short RETURN=278;
public final static short RETURNS=279;
public final static short WHILE=280;
public final static short REL_OP_LE=281;
public final static short REL_OP_LT=282;
public final static short REL_OP_GE=283;
public final static short REL_OP_GT=284;
public final static short EQUALS=285;
public final static short LOG_OP_EQUAL=286;
public final static short LOG_OP_AND=287;
public final static short LOG_OP_OR=288;
public final static short LOG_OP_NOT=289;
public final static short EQUAL=290;
public final static short PLUS=291;
public final static short MINUS=292;
public final static short MOD=293;
public final static short MUL=294;
public final static short DIV=295;
public final static short LEFT_SQUARE_PAREN=296;
public final static short RIGHT_SQUARE_PAREN=297;
public final static short LPAREN=298;
public final static short RPAREN=299;
public final static short COLON=300;
public final static short SEMICOLON=301;
public final static short COMMA=302;
public final static short DOT=303;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    1,    2,    5,    5,    6,    6,
    6,    6,    8,    8,    8,    8,    8,    9,    9,    9,
    9,    9,    7,    7,    7,   13,   13,   13,   13,   13,
   13,   13,   17,   19,   19,   20,   20,   18,   16,   16,
   14,   15,   15,    4,    4,   21,   21,   11,   11,   11,
   23,   23,   12,   12,   22,   22,   22,   22,   22,    3,
    3,   10,   10,   10,   10,   24,   24,   24,   24,   24,
   25,   25,   25,   26,   26,   26,   26,   26,   26,   26,
   26,   27,   27,   27,   27,   27,   27,
};
final static short yylen[] = {                            2,
    1,    1,    1,    2,    2,   11,    2,    0,    3,    3,
    2,    0,    2,    2,    2,    2,    2,    4,    4,    4,
    6,    6,    1,    2,    0,    1,    2,    2,    2,    1,
    1,    4,   10,    9,    0,    5,    0,    8,    2,    2,
    4,    6,    5,    1,    1,    2,    4,    1,    0,    1,
    1,    3,    7,    5,    1,    1,    1,    1,    1,    1,
    1,    3,    3,    3,    1,    3,    3,    3,    3,    1,
    2,    2,    1,    3,    3,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    4,    0,    0,   55,
   56,   57,   58,   59,   61,    0,   60,    0,   44,    0,
   45,    0,    0,    0,    0,    0,    0,    0,   11,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   23,    0,    0,    0,   30,   31,   47,    6,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   80,
   81,   76,   79,    0,    0,    0,    0,   78,   40,    0,
   70,   73,    0,   26,   24,    9,   10,   27,   28,   29,
    0,    0,   48,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   84,   82,   85,
   83,   86,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   32,   41,    0,    0,    0,    0,    0,    0,
    0,    0,   74,    0,   87,    0,    0,    0,   66,   69,
   67,   68,    0,    0,   43,    0,    0,    0,    0,    0,
    0,   42,   21,    0,   22,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   38,   54,    0,    0,    0,
    0,    0,    0,   33,   53,    0,    0,    0,    0,    0,
    0,    0,   36,    0,    0,   34,
};
final static short yydgoto[] = {                          2,
    3,    4,   16,   20,   25,   26,   39,   40,   41,   94,
   85,  139,   42,   68,   44,   45,   46,   47,  160,  164,
   21,   22,   86,   70,   71,   72,  107,
};
final static short yysindex[] = {                      -241,
 -243,    0, -214,    0, -205, -243,    0, -235, -239,    0,
    0,    0,    0,    0,    0, -188,    0,  249,    0, -201,
    0, -174,    0, -212, -168, -103,  282, -152,    0, -266,
 -151, -150, -145, -140, -139, -171, -250, -170,  -58, -181,
 -177,    0, -167, -166, -161,    0,    0,    0,    0, -200,
 -204, -116, -120, -119, -118, -111, -107, -200, -228,    0,
    0,    0,    0, -200, -200, -200,  248,    0,    0, -156,
    0,    0, -200,    0,    0,    0,    0,    0,    0,    0,
 -153,  153,    0,  248, -130, -115, -114, -200, -200, -200,
 -108, -104,  -35,  248,    0,    0,  167,    0,    0,    0,
    0,    0, -200,  -90, -200, -200, -200, -200, -200, -200,
 -200,  179,    0,    0, -200, -230,  248,  248,  248, -204,
 -100,  -65,    0, -156,    0, -156, -156,  248,    0,    0,
    0,    0,  -62, -115,    0, -117,  -91, -200, -216,  -58,
  -58,    0,    0,  113,    0,  -89, -148,  -95, -200, -200,
  -66,  -70,  191,  125,  -56,    0,    0, -200,  -86,  -54,
  203, -200,  -50,    0,    0,  215,  -58,  -49,  -85,  -58,
  -48,  -77,    0,  -51,  -56,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,  223,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -110,  -39,    0,  -38,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -32,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -47,    0,  -60,  -46,  -24,  -22,  -19,    0,  137,    0,
    0,    0,    0,    0,    0,    0,  -18,    0,    0,   25,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -57,    0,    0, -198,    0, -268,    0,    0,    0,    0,
    0,    0,    0,    0,  -21,    3,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -13,   -8,   -2,  -28,
    0,    0,    0,   47,    0,   69,   91,  226,    0,    0,
    0,    0,    0, -202,    0,    0,    0,    0,    0,  -38,
  -38,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -178,    0,    0,    0,    0,  -67,
    0,    0,    0,    0,    0,    0,  -38,    0,    0,  -38,
    0,    0,    0,    0, -178,    0,
};
final static short yygindex[] = {                         0,
    0,  240,    0,    0,    0,    0, -129,    0,    0,  -33,
  -53,    0,  -36,  -26,  213,    0,    0,    0,  100,    0,
  274,  294,  195,  -96,  -59,    0,    0,
};
final static int YYTABLESIZE=545;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
    3,    5,   75,   67,   95,   96,  124,   59,  126,  127,
  147,  148,   43,   60,   61,    1,   82,   84,   50,   10,
   11,   12,   13,   14,   93,   62,   63,   81,   50,    5,
   50,   51,   97,   60,   61,   15,   52,  169,   64,  112,
  172,   65,    6,    9,   83,   62,   63,   66,  129,  130,
  131,  132,    8,   81,  117,  118,  119,   81,   64,   60,
   61,   65,  136,   60,   61,   23,  137,   66,  135,   51,
   83,   62,   63,  128,   52,   62,   63,   18,   35,   35,
  145,   84,   84,   24,   64,  146,   84,   65,   64,   27,
   35,   65,   35,   66,   52,   35,   52,   66,   51,   35,
   51,   35,   28,   51,  144,   49,   53,   54,   74,   30,
   75,   75,   55,   43,   43,  153,  154,   56,   57,   76,
   43,   43,  151,   77,  161,   36,   58,   73,  166,   37,
  108,   38,   75,   78,   79,   75,  109,  110,  111,   80,
   43,   87,   43,   43,   51,   43,   12,   12,   12,   12,
   12,   12,   12,   29,   30,   31,   32,   33,   34,   35,
    8,   74,   30,   12,   88,   89,   90,   12,  114,   12,
   36,   74,   30,   91,   37,  152,   38,   92,   36,   74,
   30,  142,   37,  116,   38,  171,  115,  120,   36,   37,
   37,  121,   37,  174,   38,  125,   36,  138,   74,   30,
   37,  140,   38,   37,  141,  143,   37,  155,  150,  156,
   37,  162,   37,  159,  163,   36,  167,  170,  175,   37,
  173,   38,    1,   77,   77,   77,   77,   46,   77,   77,
   77,   77,   25,   77,   77,   77,   77,   77,    7,   77,
   14,   77,    7,   77,   77,   98,   99,  100,  101,   69,
  102,   49,  103,  104,   13,  105,  106,    3,    5,   70,
   70,   70,   70,  122,   70,   70,   70,   70,   49,   70,
   70,   70,   70,   70,  176,   71,   15,   71,   16,   71,
   71,   17,   39,   70,   70,   70,   70,   20,   70,   70,
   70,   70,   18,   70,   70,   70,   70,   70,   19,   72,
   48,   72,   17,   72,   72,   65,   65,   65,   65,  134,
   65,    0,   65,   65,    0,   65,   65,    0,    0,    0,
    0,   65,    0,   65,    0,   65,   65,   62,   62,   62,
   62,    0,   62,    0,   62,   62,    0,   62,   62,    0,
    0,    0,    0,   62,    0,   62,    0,   62,   62,   63,
   63,   63,   63,    0,   63,    0,   63,   63,    0,   63,
   63,    0,    0,    0,    0,   63,    0,   63,    0,   63,
   63,   64,   64,   64,   64,    0,   64,    0,   64,   64,
    0,   64,   64,    0,    0,    0,    0,   64,    0,   64,
    0,   64,   64,   98,   99,  100,  101,    0,  102,    0,
  103,  104,    0,  105,  106,   98,   99,  100,  101,    0,
  102,    0,  103,  104,  149,  105,  106,   77,   77,   77,
   77,    0,   77,   77,   77,   77,  158,   77,   77,   77,
   77,   77,    0,   98,   99,  100,  101,   77,  102,    0,
  103,  104,    0,  105,  106,    0,    0,   98,   99,  100,
  101,    0,  102,  113,  103,  104,    0,  105,  106,   98,
   99,  100,  101,    0,  102,  123,  103,  104,    0,  105,
  106,   98,   99,  100,  101,    0,  102,  133,  103,  104,
    0,  105,  106,   98,   99,  100,  101,    0,  102,  157,
  103,  104,    0,  105,  106,   98,   99,  100,  101,    0,
  102,  165,  103,  104,    0,  105,  106,   10,   11,   12,
   13,   14,   75,  168,    0,    0,    0,    0,   75,   75,
   75,    0,   75,   19,   75,    0,   75,   75,   98,   99,
  100,  101,    0,  102,    0,  103,  104,    0,  105,  106,
   10,   11,   12,   13,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         26,
    0,    0,   39,   37,   64,   65,  103,  258,  105,  106,
  140,  141,   39,  264,  265,  257,   50,   51,  285,  259,
  260,  261,  262,  263,   58,  276,  277,  258,  297,  273,
  299,  298,   66,  264,  265,  275,  303,  167,  289,   73,
  170,  292,  257,  279,  275,  276,  277,  298,  108,  109,
  110,  111,  258,  258,   88,   89,   90,  258,  289,  264,
  265,  292,  116,  264,  265,  267,  120,  298,  299,  298,
  275,  276,  277,  107,  303,  276,  277,  266,  257,  258,
  297,  115,  116,  258,  289,  302,  120,  292,  289,  302,
  269,  292,  271,  298,  297,  274,  299,  298,  297,  278,
  299,  280,  271,  302,  138,  258,  258,  258,  257,  258,
  147,  148,  258,  140,  141,  149,  150,  258,  258,  301,
  147,  148,  271,  301,  158,  274,  298,  298,  162,  278,
  287,  280,  169,  301,  301,  172,  293,  294,  295,  301,
  167,  258,  169,  170,  298,  172,  257,  258,  259,  260,
  261,  262,  263,  257,  258,  259,  260,  261,  262,  263,
  271,  257,  258,  274,  285,  285,  285,  278,  299,  280,
  274,  257,  258,  285,  278,  271,  280,  285,  274,  257,
  258,  299,  278,  298,  280,  271,  302,  296,  274,  257,
  258,  296,  278,  271,  280,  286,  274,  298,  257,  258,
  278,  267,  280,  271,  267,  297,  274,  274,  298,  280,
  278,  298,  280,  270,  269,  274,  267,  267,  270,  278,
  269,  280,    0,  281,  282,  283,  284,  267,  286,  287,
  288,  289,  271,  291,  292,  293,  294,  295,  271,  297,
  301,  299,    3,  301,  302,  281,  282,  283,  284,   37,
  286,  299,  288,  289,  301,  291,  292,  257,  257,  281,
  282,  283,  284,  299,  286,  287,  288,  289,  297,  291,
  292,  293,  294,  295,  175,  297,  301,  299,  301,  301,
  302,  301,  301,  281,  282,  283,  284,  301,  286,  287,
  288,  289,  301,  291,  292,  293,  294,  295,  301,  297,
   27,  299,    9,  301,  302,  281,  282,  283,  284,  115,
  286,   -1,  288,  289,   -1,  291,  292,   -1,   -1,   -1,
   -1,  297,   -1,  299,   -1,  301,  302,  281,  282,  283,
  284,   -1,  286,   -1,  288,  289,   -1,  291,  292,   -1,
   -1,   -1,   -1,  297,   -1,  299,   -1,  301,  302,  281,
  282,  283,  284,   -1,  286,   -1,  288,  289,   -1,  291,
  292,   -1,   -1,   -1,   -1,  297,   -1,  299,   -1,  301,
  302,  281,  282,  283,  284,   -1,  286,   -1,  288,  289,
   -1,  291,  292,   -1,   -1,   -1,   -1,  297,   -1,  299,
   -1,  301,  302,  281,  282,  283,  284,   -1,  286,   -1,
  288,  289,   -1,  291,  292,  281,  282,  283,  284,   -1,
  286,   -1,  288,  289,  302,  291,  292,  281,  282,  283,
  284,   -1,  286,  287,  288,  289,  302,  291,  292,  293,
  294,  295,   -1,  281,  282,  283,  284,  301,  286,   -1,
  288,  289,   -1,  291,  292,   -1,   -1,  281,  282,  283,
  284,   -1,  286,  301,  288,  289,   -1,  291,  292,  281,
  282,  283,  284,   -1,  286,  299,  288,  289,   -1,  291,
  292,  281,  282,  283,  284,   -1,  286,  299,  288,  289,
   -1,  291,  292,  281,  282,  283,  284,   -1,  286,  299,
  288,  289,   -1,  291,  292,  281,  282,  283,  284,   -1,
  286,  299,  288,  289,   -1,  291,  292,  259,  260,  261,
  262,  263,  287,  299,   -1,   -1,   -1,   -1,  293,  294,
  295,   -1,  297,  275,  299,   -1,  301,  302,  281,  282,
  283,  284,   -1,  286,   -1,  288,  289,   -1,  291,  292,
  259,  260,  261,  262,  263,
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
"HASH","TRUE","FALSE","ACCEPTS","BEGIN","EACH","ELSE","ELSEIF","END","FOR",
"FUNCTION","IF","NOTHING","NUMERIC","QUOTE","RETURN","RETURNS","WHILE",
"REL_OP_LE","REL_OP_LT","REL_OP_GE","REL_OP_GT","EQUALS","LOG_OP_EQUAL",
"LOG_OP_AND","LOG_OP_OR","LOG_OP_NOT","EQUAL","PLUS","MINUS","MOD","MUL","DIV",
"LEFT_SQUARE_PAREN","RIGHT_SQUARE_PAREN","LPAREN","RPAREN","COLON","SEMICOLON",
"COMMA","DOT",
};
final static String yyrule[] = {
"$accept : start",
"start : program",
"program : method",
"program : COMMENT",
"program : program method",
"program : program COMMENT",
"method : COMMENT FUNCTION IDENTIFIER RETURNS return_type ACCEPTS aparams BEGIN body END IDENTIFIER",
"body : declarations control_body",
"body :",
"declarations : declarations type_declaration SEMICOLON",
"declarations : declarations type_declaration_assignment SEMICOLON",
"declarations : declarations COMMENT",
"declarations :",
"type_declaration : STRING IDENTIFIER",
"type_declaration : BOOLEAN IDENTIFIER",
"type_declaration : NUMBER IDENTIFIER",
"type_declaration : ARRAY IDENTIFIER",
"type_declaration : HASH IDENTIFIER",
"type_declaration_assignment : STRING IDENTIFIER EQUALS exp",
"type_declaration_assignment : NUMBER IDENTIFIER EQUALS exp",
"type_declaration_assignment : BOOLEAN IDENTIFIER EQUALS exp",
"type_declaration_assignment : ARRAY IDENTIFIER EQUALS LEFT_SQUARE_PAREN params RIGHT_SQUARE_PAREN",
"type_declaration_assignment : HASH IDENTIFIER EQUALS LEFT_SQUARE_PAREN hash_params RIGHT_SQUARE_PAREN",
"control_body : statement",
"control_body : control_body statement",
"control_body :",
"statement : COMMENT",
"statement : function_call SEMICOLON",
"statement : complex_type_method_invocation SEMICOLON",
"statement : return_statement SEMICOLON",
"statement : if_statement",
"statement : while_loop",
"statement : IDENTIFIER EQUALS exp SEMICOLON",
"if_statement : IF LPAREN exp RPAREN BEGIN control_body END IF else_if else",
"else_if : ELSEIF LPAREN exp RPAREN BEGIN control_body END ELSEIF else_if",
"else_if :",
"else : ELSE BEGIN control_body END ELSE",
"else :",
"while_loop : WHILE LPAREN exp RPAREN BEGIN control_body END WHILE",
"return_statement : RETURN exp",
"return_statement : RETURN complex_type_method_invocation",
"function_call : IDENTIFIER LPAREN params RPAREN",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN",
"aparams : NOTHING",
"aparams : aparams_",
"aparams_ : type IDENTIFIER",
"aparams_ : type IDENTIFIER COMMA aparams_",
"params : NOTHING",
"params :",
"params : params_",
"params_ : exp",
"params_ : params_ COMMA params_",
"hash_params : hash_params COMMA LPAREN exp COMMA exp RPAREN",
"hash_params : LPAREN exp COMMA exp RPAREN",
"type : BOOLEAN",
"type : STRING",
"type : NUMBER",
"type : ARRAY",
"type : HASH",
"return_type : type",
"return_type : NOTHING",
"exp : exp LOG_OP_OR term",
"exp : exp PLUS term",
"exp : exp MINUS term",
"exp : term",
"term : term LOG_OP_AND unary",
"term : term MUL unary",
"term : term DIV unary",
"term : term MOD unary",
"term : unary",
"unary : LOG_OP_NOT unary",
"unary : MINUS unary",
"unary : factor",
"factor : LPAREN exp RPAREN",
"factor : exp rel_op exp",
"factor : NUMERIC",
"factor : IDENTIFIER",
"factor : function_call",
"factor : QUOTE",
"factor : TRUE",
"factor : FALSE",
"rel_op : REL_OP_LT",
"rel_op : REL_OP_GT",
"rel_op : REL_OP_LE",
"rel_op : REL_OP_GE",
"rel_op : LOG_OP_EQUAL",
"rel_op : LOG_OP_NOT LOG_OP_EQUAL",
};

//#line 278 "Breezy.yacc"

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
//#line 498 "Parser.java"
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
//#line 26 "Breezy.yacc"
{ba.DumpFile(val_peek(0).sval);}
break;
case 2:
//#line 29 "Breezy.yacc"
{yyval.sval= val_peek(0).sval;}
break;
case 3:
//#line 30 "Breezy.yacc"
{yyval.sval="";}
break;
case 4:
//#line 31 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 5:
//#line 32 "Breezy.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 6:
//#line 42 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6).sval,val_peek(4).sval,val_peek(2).sval,val_peek(0).sval,val_peek(8).line,Scope.GLOBAL.getName()); }
break;
case 7:
//#line 47 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 8:
//#line 48 "Breezy.yacc"
{yyval.sval = "";}
break;
case 9:
//#line 53 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 10:
//#line 54 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 11:
//#line 55 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + "";}
break;
case 12:
//#line 56 "Breezy.yacc"
{yyval.sval = "";}
break;
case 13:
//#line 60 "Breezy.yacc"
{yyval.sval = "String " + val_peek(0).sval + "=\"\""; ba.addIdentifier(val_peek(0).sval,"string",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 14:
//#line 61 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval + "=false"; ba.addIdentifier(val_peek(0).sval,"boolean",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 15:
//#line 62 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval + "=0"; ba.addIdentifier(val_peek(0).sval,"number",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 16:
//#line 63 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval); ba.addIdentifier(val_peek(0).sval,"ArrayList",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 17:
//#line 64 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval); ba.addIdentifier(val_peek(0).sval,"HashMap",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 18:
//#line 69 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertStringType(val_peek(0));
                                                                         ba.addIdentifier(val_peek(2).sval,"string",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 19:
//#line 72 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertNumberType(val_peek(0));
                                                                         ba.addIdentifier(val_peek(2).sval,"number",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 20:
//#line 75 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                          ba.addIdentifier(val_peek(2).sval,"boolean",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 21:
//#line 78 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 22:
//#line 80 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 23:
//#line 84 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 24:
//#line 85 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 25:
//#line 86 "Breezy.yacc"
{yyval.sval = "";}
break;
case 26:
//#line 90 "Breezy.yacc"
{yyval.sval = "";}
break;
case 27:
//#line 91 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 28:
//#line 92 "Breezy.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 29:
//#line 93 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 30:
//#line 94 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 31:
//#line 95 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 32:
//#line 96 "Breezy.yacc"
{val_peek(3).obj = ba.typeTrack.getType(val_peek(3),Scope.LOCAL.getName());
                                                                        ba.typeTrack.assertSameType(val_peek(3),val_peek(1),val_peek(2));
                                                                        yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 33:
//#line 104 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(7));
                                                    yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 34:
//#line 112 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(6));
                                                yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 35:
//#line 114 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 36:
//#line 121 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 37:
//#line 122 "Breezy.yacc"
{yyval.sval = "";}
break;
case 38:
//#line 129 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(5));
                                        yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 39:
//#line 133 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 40:
//#line 134 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 41:
//#line 137 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                            yyval.obj = ba.typeTrack.getType(val_peek(3), Scope.GLOBAL.getName());}
break;
case 42:
//#line 144 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, Scope.LOCAL.getName(), val_peek(1).sval);}
break;
case 43:
//#line 145 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, Scope.LOCAL.getName(), null);}
break;
case 44:
//#line 149 "Breezy.yacc"
{yyval.sval = "";}
break;
case 45:
//#line 150 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 46:
//#line 153 "Breezy.yacc"
{ba.addIdentifier(val_peek(0).sval,val_peek(1).obj.toString(),val_peek(0).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 47:
//#line 155 "Breezy.yacc"
{ba.addIdentifier(val_peek(2).sval,val_peek(3).obj.toString(),val_peek(2).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 48:
//#line 159 "Breezy.yacc"
{yyval.sval = "";}
break;
case 49:
//#line 160 "Breezy.yacc"
{yyval.sval = "";}
break;
case 50:
//#line 161 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 51:
//#line 164 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 165 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 53:
//#line 168 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")" + val_peek(6).sval;}
break;
case 54:
//#line 169 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 55:
//#line 173 "Breezy.yacc"
{yyval.sval = "boolean "; yyval.obj = "boolean";}
break;
case 56:
//#line 174 "Breezy.yacc"
{yyval.sval = "String "; yyval.obj = "string";}
break;
case 57:
//#line 175 "Breezy.yacc"
{yyval.sval = "double "; yyval.obj = "number";}
break;
case 58:
//#line 176 "Breezy.yacc"
{yyval.sval = "ArrayList "; yyval.obj = "ArrayList";}
break;
case 59:
//#line 177 "Breezy.yacc"
{yyval.sval = "HashMap "; yyval.obj = "HashMap";}
break;
case 60:
//#line 180 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 61:
//#line 181 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 62:
//#line 184 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                        yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                        yyval.obj = val_peek(2).obj;
                                                        yyval.line = val_peek(2).line;}
break;
case 63:
//#line 188 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 64:
//#line 192 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 65:
//#line 196 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 66:
//#line 200 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 67:
//#line 204 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 68:
//#line 208 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 69:
//#line 212 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 70:
//#line 216 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 71:
//#line 221 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.sval = " !" + val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 72:
//#line 225 "Breezy.yacc"
{  ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.sval = " -"+ val_peek(0).sval;
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 73:
//#line 229 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 74:
//#line 234 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 75:
//#line 237 "Breezy.yacc"
{ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0),val_peek(1));
                                                        if(val_peek(2).obj.toString().equals("string") && val_peek(1).sval.equals("=="))
                                                            yyval.sval = "(" +val_peek(2).sval + ").equals(" + val_peek(0).sval + ")";
                                                        else if(val_peek(2).obj.toString().equals("string") && val_peek(1).sval.equals("!="))
                                                            yyval.sval = "!((" +val_peek(2).sval + ").equals(" + val_peek(0).sval + "))";
                                                        else if(val_peek(2).obj.toString().equals("string"))
                                                            throw new Exception("You cannot use " + val_peek(1).sval + " with two STRING types.");
                                                        else
                                                            yyval.sval = val_peek(2).sval + val_peek(1).sval + val_peek(0).sval;
                                                         yyval.obj = "boolean";
                                                        yyval.line = val_peek(2).line;}
break;
case 76:
//#line 248 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 77:
//#line 251 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0), Scope.LOCAL.getName());
                                                    yyval.line = val_peek(0).line;}
break;
case 78:
//#line 254 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 79:
//#line 257 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 80:
//#line 260 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 81:
//#line 263 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 82:
//#line 268 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 83:
//#line 269 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 84:
//#line 270 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 85:
//#line 271 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 86:
//#line 272 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 87:
//#line 273 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1064 "Parser.java"
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
