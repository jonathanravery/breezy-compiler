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
    0,    1,    1,    2,    5,    5,    6,    6,    6,    6,
    8,    8,    8,    8,    8,    9,    9,    9,    9,    9,
    7,    7,    7,   14,   14,   14,   14,   14,   14,   14,
   18,   21,   21,   22,   22,   19,   17,   17,   17,   15,
   16,   16,    4,    4,   23,   23,   12,   12,   25,   25,
   25,   13,   13,   26,   26,   26,   24,   24,   24,   24,
   24,    3,    3,   20,   20,   10,   10,   10,   27,   27,
   27,   27,   28,   28,   29,   29,   29,   29,   29,   11,
   11,   30,   30,   31,   31,   31,   31,   31,   31,   31,
   32,   32,   32,   32,   32,   32,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    3,    3,    2,    0,
    2,    2,    2,    2,    2,    4,    4,    4,    6,    6,
    1,    2,    0,    1,    2,    2,    2,    1,    1,    4,
   10,    9,    0,    5,    0,    8,    2,    2,    2,    4,
    6,    5,    1,    1,    2,    4,    1,    1,    1,    1,
    3,    7,    5,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    3,    3,    1,    3,    3,
    3,    1,    2,    1,    3,    1,    1,    1,    1,    3,
    1,    3,    1,    2,    3,    3,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   57,   58,
   59,   60,   61,   63,    0,   62,    0,   43,    0,   44,
    0,    0,    0,    0,    0,    0,    0,    9,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   21,    0,    0,    0,   28,   29,   46,    4,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   87,   88,
   76,   79,    0,    0,    0,    0,    0,    0,   39,   37,
    0,   72,   74,    0,   83,    0,   24,   22,    7,    8,
   25,   26,   27,    0,    0,    0,   47,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   84,
    0,    0,   78,   73,    0,    0,   93,   91,   94,   92,
   95,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   30,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   75,   85,   96,    0,    0,    0,    0,   71,
   69,   70,   82,    0,    0,   42,    0,    0,    0,    0,
    0,    0,   41,   19,   56,   55,   54,    0,   20,    0,
    0,    0,    0,    0,    0,    0,   36,    0,    0,    0,
    0,    0,    0,   31,   52,    0,    0,    0,    0,    0,
    0,    0,   34,    0,    0,   32,
};
final static short yydgoto[] = {                          2,
    3,    4,   15,   19,   24,   25,   38,   39,   40,   98,
   89,   90,  150,   41,   85,   43,   44,   45,   46,   70,
  170,  174,   20,   21,   91,  158,   71,   72,   73,   74,
   75,  115,
};
final static short yysindex[] = {                      -245,
 -247,    0, -245,    0, -223,    0, -212,  170,    0,    0,
    0,    0,    0,    0, -194,    0,  177,    0, -182,    0,
 -177,    0, -199, -164,  -72,  187, -154,    0, -251, -139,
 -130, -129, -114, -102, -138, -159, -126,  -30, -128, -124,
    0, -122, -120, -109,    0,    0,    0,    0, -118, -167,
  -82,  -90,  -89,  -88,  -85,  -84, -118, -289,    0,    0,
    0,    0, -118, -252, -118,  176, -105,    0,    0,    0,
 -125,    0,    0,  -78,    0, -118,    0,    0,    0,    0,
    0,    0,    0,  -93,    0,  -83,    0,  176, -105,  -86,
  -91,  -73, -118, -252, -252,  -67,  -66,  176, -256,    0,
  -93, -252,    0,    0,  136, -243,    0,    0,    0,    0,
    0,  -55, -252, -252, -252, -118, -252, -252, -252, -118,
 -235,    0,    0, -118, -248, -105, -237, -237, -167,  -63,
  -33, -216,    0,    0,    0, -125, -125, -237,  -78,    0,
    0,    0,    0,  -27,  -91,    0,  -62,  -51, -239,  -40,
  -30,  -30,    0,    0,    0,    0,    0,  -70,    0, -200,
  -64, -239,  -41,  -39,  -57,   -8,    0,  -32,  -26,   -1,
  -63, -118,   12,    0,    0, -220,  -30,   17,  -59,  -30,
   21,  -54,    0,   22,   -8,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  294,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -96,   34,    0,   35,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   41,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   13,   15,   27,   30,   31,    0,  104,    0,    0,
    0,    0,    0,    0,    0,   32,   33,  120,    0,    0,
   38,    0,    0,  -50,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -28,   -6,    0,    0, -215, -149,    0,
 -185,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   16,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   37,   44,   49,    0,    0,
    0,    0,    0,    0,    0,   60,   82, -186,  154,    0,
    0,    0,    0,    0, -148,    0,    0,    0,    0,    0,
   35,   35,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -137,    0,   26,    0,  -35,
    0,    0,    0,    0,    0,    0,   35,    0,    0,   35,
    0,    0,    0,    0, -137,    0,
};
final static short yygindex[] = {                         0,
    0,  333,    0,    0,    0,    0, -147,    0,    0,  -29,
  -34, -107,  182,  -37,  -25,  318,    0,    0,    0,  306,
  171,    0,  332,  352,  243,  210,  -65,  -56,    0,  259,
  -60,    0,
};
final static int YYTABLESIZE=468;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         42,
   78,   67,  100,  160,  161,  101,   66,  104,   50,   84,
   68,    1,   42,   51,   67,   59,   60,  147,  155,   66,
   88,  148,   99,   61,   62,    5,   87,   61,   62,  179,
  106,  116,  182,   49,    7,  105,  156,  157,  103,   64,
   63,  121,  131,   64,  116,  102,   50,  136,  137,   65,
  146,   51,  116,  113,  114,  134,   77,   29,  126,  143,
  140,  141,  142,  144,  127,  128,    8,  116,  103,  103,
  163,   17,  132,   35,  113,  114,  103,   36,  178,   37,
   23,   49,  133,   49,   22,  138,   49,  103,  103,  103,
   84,  103,  103,  103,   88,   88,   59,   60,   58,   88,
   86,   86,   26,   48,   59,   60,   27,   87,   61,   62,
   86,   48,   86,   48,   86,   86,   61,   62,   52,   33,
   33,   63,   78,   78,   64,   42,   42,   53,   54,   63,
   65,   33,   64,   33,   42,   42,   33,  176,   65,   84,
   33,   78,   33,   55,   78,   59,   60,   50,   51,   50,
   51,   42,   50,   42,   42,   56,   42,   61,   62,   57,
   10,   10,   10,   10,   10,   10,   10,  117,  118,  119,
   63,   76,   79,   64,    6,   92,   80,   10,   81,   65,
   82,   10,  116,   10,   28,   29,   30,   31,   32,   33,
   34,   83,   77,   29,   93,   94,   95,   77,   29,   96,
   97,   35,   77,   29,   50,   36,  164,   37,  120,   35,
  124,  181,  123,   36,   35,   37,  184,  122,   36,   35,
   37,   35,   35,   36,  125,   37,   77,   29,  129,  130,
  135,  162,  166,  151,  149,   35,  153,   81,   35,  152,
  167,  168,   35,   35,   35,  154,   81,   36,   81,   37,
   81,   81,   77,   77,   77,   77,  159,   77,   89,   89,
   77,  169,   77,   77,   77,   77,   77,  173,   77,  171,
   77,  172,   77,   77,   78,   78,   78,   78,  177,   78,
   90,   90,   78,  180,   78,   78,   78,   78,   78,  183,
   78,  185,   78,    1,   78,   78,   77,   77,   77,   77,
   45,   77,   77,   77,   77,   23,   77,   77,   77,   77,
   77,    5,   77,   12,   77,   11,   77,   77,   68,   68,
   68,   68,   53,   68,   68,   68,   68,   13,   68,   68,
   14,   15,   65,   64,   68,    6,   68,   18,   68,   68,
   66,   66,   66,   66,   16,   66,   66,   66,   66,   17,
   66,   66,  175,   69,   86,  186,   66,   47,   66,   16,
   66,   66,   67,   67,   67,   67,  145,   67,   67,   67,
   67,  165,   67,   67,  139,    0,    0,    0,   67,    0,
   67,    0,   67,   67,   77,   77,   77,   77,    0,   77,
   89,   89,   77,    0,   77,   77,   77,   77,   77,    0,
   78,   78,   78,   78,   77,   78,   90,   90,   78,    0,
   78,   78,   78,   78,   78,    0,  107,  108,  109,  110,
   38,  111,    0,    0,  112,    0,  113,  114,    9,   10,
   11,   12,   13,    0,  133,    9,   10,   11,   12,   13,
    0,   80,    0,    0,   14,    9,   10,   11,   12,   13,
   80,   18,   80,    0,   80,   80,  107,  108,  109,  110,
    0,  111,    0,    0,  112,    0,  113,  114,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         25,
   38,   36,   63,  151,  152,  258,   36,   64,  298,  258,
   36,  257,   38,  303,   49,  264,  265,  125,  258,   49,
   50,  129,   57,  276,  277,  273,  275,  276,  277,  177,
   65,  288,  180,  285,  258,   65,  276,  277,   64,  292,
  289,   76,  299,  292,  288,  298,  298,  113,  114,  298,
  299,  303,  288,  291,  292,  299,  257,  258,   93,  120,
  117,  118,  119,  299,   94,   95,  279,  288,   94,   95,
  271,  266,  102,  274,  291,  292,  102,  278,  299,  280,
  258,  297,  299,  299,  267,  115,  302,  113,  114,  115,
  258,  117,  118,  119,  124,  125,  264,  265,  258,  129,
  287,  288,  302,  258,  264,  265,  271,  275,  276,  277,
  297,  297,  299,  299,  301,  302,  276,  277,  258,  257,
  258,  289,  160,  161,  292,  151,  152,  258,  258,  289,
  298,  269,  292,  271,  160,  161,  274,  172,  298,  258,
  278,  179,  280,  258,  182,  264,  265,  297,  297,  299,
  299,  177,  302,  179,  180,  258,  182,  276,  277,  298,
  257,  258,  259,  260,  261,  262,  263,  293,  294,  295,
  289,  298,  301,  292,  271,  258,  301,  274,  301,  298,
  301,  278,  288,  280,  257,  258,  259,  260,  261,  262,
  263,  301,  257,  258,  285,  285,  285,  257,  258,  285,
  285,  274,  257,  258,  298,  278,  271,  280,  287,  274,
  302,  271,  299,  278,  274,  280,  271,  301,  278,  274,
  280,  257,  258,  278,  298,  280,  257,  258,  296,  296,
  286,  302,  274,  267,  298,  271,  299,  288,  274,  267,
  280,  299,  278,  274,  280,  297,  297,  278,  299,  280,
  301,  302,  281,  282,  283,  284,  297,  286,  287,  288,
  289,  270,  291,  292,  293,  294,  295,  269,  297,  302,
  299,  298,  301,  302,  281,  282,  283,  284,  267,  286,
  287,  288,  289,  267,  291,  292,  293,  294,  295,  269,
  297,  270,  299,    0,  301,  302,  281,  282,  283,  284,
  267,  286,  287,  288,  289,  271,  291,  292,  293,  294,
  295,  271,  297,  301,  299,  301,  301,  302,  281,  282,
  283,  284,  297,  286,  287,  288,  289,  301,  291,  292,
  301,  301,  301,  301,  297,    3,  299,  301,  301,  302,
  281,  282,  283,  284,  301,  286,  287,  288,  289,  301,
  291,  292,  171,   36,   49,  185,  297,   26,  299,    8,
  301,  302,  281,  282,  283,  284,  124,  286,  287,  288,
  289,  162,  291,  292,  116,   -1,   -1,   -1,  297,   -1,
  299,   -1,  301,  302,  281,  282,  283,  284,   -1,  286,
  287,  288,  289,   -1,  291,  292,  293,  294,  295,   -1,
  281,  282,  283,  284,  301,  286,  287,  288,  289,   -1,
  291,  292,  293,  294,  295,   -1,  281,  282,  283,  284,
  301,  286,   -1,   -1,  289,   -1,  291,  292,  259,  260,
  261,  262,  263,   -1,  299,  259,  260,  261,  262,  263,
   -1,  288,   -1,   -1,  275,  259,  260,  261,  262,  263,
  297,  275,  299,   -1,  301,  302,  281,  282,  283,  284,
   -1,  286,   -1,   -1,  289,   -1,  291,  292,
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
"program : program method",
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
"type_declaration_assignment : STRING IDENTIFIER EQUALS arith_exp",
"type_declaration_assignment : NUMBER IDENTIFIER EQUALS arith_exp",
"type_declaration_assignment : BOOLEAN IDENTIFIER EQUALS bool_exp",
"type_declaration_assignment : ARRAY IDENTIFIER EQUALS LEFT_SQUARE_PAREN params RIGHT_SQUARE_PAREN",
"type_declaration_assignment : HASH IDENTIFIER EQUALS LEFT_SQUARE_PAREN hash_params RIGHT_SQUARE_PAREN",
"control_body : statement",
"control_body : control_body statement",
"control_body :",
"statement : COMMENT",
"statement : function_declaration SEMICOLON",
"statement : complex_type_method_invocation SEMICOLON",
"statement : return_statement SEMICOLON",
"statement : if_statement",
"statement : while_loop",
"statement : IDENTIFIER EQUALS exp SEMICOLON",
"if_statement : IF LPAREN bool_exp RPAREN BEGIN control_body END IF else_if else",
"else_if : ELSEIF LPAREN bool_exp RPAREN BEGIN control_body END ELSEIF else_if",
"else_if :",
"else : ELSE BEGIN control_body END ELSE",
"else :",
"while_loop : WHILE LPAREN bool_exp RPAREN BEGIN control_body END WHILE",
"return_statement : RETURN exp",
"return_statement : RETURN function_declaration",
"return_statement : RETURN complex_type_method_invocation",
"function_declaration : IDENTIFIER LPAREN params RPAREN",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN",
"aparams : NOTHING",
"aparams : aparams_",
"aparams_ : type IDENTIFIER",
"aparams_ : type IDENTIFIER COMMA aparams_",
"params : NOTHING",
"params : params_",
"params_ : arith_exp",
"params_ : bool_exp",
"params_ : params_ COMMA params_",
"hash_params : LPAREN hash_item COMMA hash_item RPAREN COMMA hash_params",
"hash_params : LPAREN hash_item COMMA hash_item RPAREN",
"hash_item : QUOTE",
"hash_item : NUMERIC",
"hash_item : IDENTIFIER",
"type : BOOLEAN",
"type : STRING",
"type : NUMBER",
"type : ARRAY",
"type : HASH",
"return_type : type",
"return_type : NOTHING",
"exp : bool_exp",
"exp : arith_exp",
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
"factor : QUOTE",
"bool_exp : bool_exp LOG_OP_OR bool_term",
"bool_exp : bool_term",
"bool_term : bool_term LOG_OP_AND bool_factor",
"bool_term : bool_factor",
"bool_factor : LOG_OP_NOT bool_factor",
"bool_factor : LPAREN bool_exp RPAREN",
"bool_factor : arith_exp rel_op arith_exp",
"bool_factor : TRUE",
"bool_factor : FALSE",
"bool_factor : IDENTIFIER",
"bool_factor : function_declaration",
"rel_op : REL_OP_LT",
"rel_op : REL_OP_GT",
"rel_op : REL_OP_LE",
"rel_op : REL_OP_GE",
"rel_op : LOG_OP_EQUAL",
"rel_op : LOG_OP_NOT LOG_OP_EQUAL",
};

//#line 294 "Breezy.yacc"

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
            System.err.println(e.getMessage());;
	}  
}
//#line 497 "Parser.java"
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
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 4:
//#line 40 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6).sval,val_peek(4).sval,val_peek(2).sval,val_peek(0).sval,val_peek(8).line,val_peek(8).column); }
break;
case 5:
//#line 45 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 6:
//#line 46 "Breezy.yacc"
{yyval.sval = "";}
break;
case 7:
//#line 51 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 8:
//#line 52 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 9:
//#line 53 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + "";}
break;
case 10:
//#line 54 "Breezy.yacc"
{yyval.sval = "";}
break;
case 11:
//#line 58 "Breezy.yacc"
{yyval.sval = "String " + val_peek(0).sval + "=\"\""; ba.addIdentifier(val_peek(0).sval,"string",val_peek(0).line,val_peek(0).column);}
break;
case 12:
//#line 59 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval + "=false"; ba.addIdentifier(val_peek(0).sval,"boolean",val_peek(0).line,val_peek(0).column);}
break;
case 13:
//#line 60 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval + "=0"; ba.addIdentifier(val_peek(0).sval,"number",val_peek(0).line,val_peek(0).column);}
break;
case 14:
//#line 61 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval); ba.addIdentifier(val_peek(0).sval,"ArrayList",val_peek(0).line,val_peek(0).column);}
break;
case 15:
//#line 62 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval); ba.addIdentifier(val_peek(0).sval,"HashMap",val_peek(0).line,val_peek(0).column);}
break;
case 16:
//#line 67 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertStringType(val_peek(0));
                                                                         ba.addIdentifier(val_peek(2).sval,"string",val_peek(2).line,val_peek(2).column);}
break;
case 17:
//#line 70 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertNumberType(val_peek(0));
                                                                         ba.addIdentifier(val_peek(2).sval,"number",val_peek(2).line,val_peek(2).column);}
break;
case 18:
//#line 73 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                          ba.addIdentifier(val_peek(2).sval,"boolean",val_peek(2).line,val_peek(2).column);}
break;
case 19:
//#line 76 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,val_peek(4).column);}
break;
case 20:
//#line 78 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,val_peek(4).column);}
break;
case 21:
//#line 82 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 22:
//#line 83 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 23:
//#line 84 "Breezy.yacc"
{yyval.sval = "";}
break;
case 24:
//#line 88 "Breezy.yacc"
{yyval.sval = "";}
break;
case 25:
//#line 89 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 26:
//#line 90 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 27:
//#line 91 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 28:
//#line 92 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 29:
//#line 93 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 30:
//#line 94 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 31:
//#line 100 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 32:
//#line 107 "Breezy.yacc"
{ yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 33:
//#line 108 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 34:
//#line 115 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 35:
//#line 116 "Breezy.yacc"
{yyval.sval = "";}
break;
case 36:
//#line 123 "Breezy.yacc"
{ yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 37:
//#line 126 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 38:
//#line 127 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 39:
//#line 128 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 40:
//#line 131 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                                                yyval.obj = ba.typeTrack.getType(val_peek(3));}
break;
case 41:
//#line 138 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, val_peek(1).sval);}
break;
case 42:
//#line 139 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, null);}
break;
case 43:
//#line 143 "Breezy.yacc"
{yyval.sval = "";}
break;
case 44:
//#line 144 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 45:
//#line 147 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 46:
//#line 148 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 47:
//#line 151 "Breezy.yacc"
{yyval.sval = "";}
break;
case 48:
//#line 152 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 49:
//#line 155 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 50:
//#line 156 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 51:
//#line 157 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 52:
//#line 160 "Breezy.yacc"
{yyval.sval = "(" + val_peek(5).sval + "," + val_peek(3).sval + ")" + val_peek(0).sval;}
break;
case 53:
//#line 161 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 54:
//#line 164 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 55:
//#line 165 "Breezy.yacc"
{yyval.ival = val_peek(0).ival;}
break;
case 56:
//#line 166 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 57:
//#line 170 "Breezy.yacc"
{yyval.sval = "boolean ";}
break;
case 58:
//#line 171 "Breezy.yacc"
{yyval.sval = "String ";}
break;
case 59:
//#line 172 "Breezy.yacc"
{yyval.sval = "double ";}
break;
case 60:
//#line 173 "Breezy.yacc"
{yyval.sval = "ArrayList ";}
break;
case 61:
//#line 174 "Breezy.yacc"
{yyval.sval = "HashMap ";}
break;
case 62:
//#line 177 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 63:
//#line 178 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 64:
//#line 181 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.line = val_peek(0).line;}
break;
case 65:
//#line 183 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.line = val_peek(0).line;}
break;
case 66:
//#line 187 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 67:
//#line 191 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 68:
//#line 195 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 69:
//#line 199 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 70:
//#line 203 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 71:
//#line 207 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 72:
//#line 211 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 73:
//#line 216 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval; 
                                        ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 74:
//#line 220 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 75:
//#line 225 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 76:
//#line 228 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 77:
//#line 231 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0));
                                                    yyval.line = val_peek(0).line;}
break;
case 78:
//#line 234 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 79:
//#line 237 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 80:
//#line 242 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                         yyval.obj = val_peek(2).obj;
                                                            yyval.line = val_peek(2).line;}
break;
case 81:
//#line 245 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                                            yyval.obj = val_peek(0).obj;
                                                            yyval.line = val_peek(0).line;}
break;
case 82:
//#line 250 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;  
                                                            yyval.obj = val_peek(2).obj;
                                                            yyval.line = val_peek(2).line; }
break;
case 83:
//#line 253 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                                                    yyval.obj = val_peek(0).obj;
                                                                    yyval.line = val_peek(0).line;}
break;
case 84:
//#line 258 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; 
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 85:
//#line 261 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; 
                                                yyval.obj = val_peek(1).obj;
                                                yyval.line = val_peek(2).line;}
break;
case 86:
//#line 264 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + val_peek(0).sval;
                                                         ba.typeTrack.assertNumberType(val_peek(2),val_peek(0),val_peek(1));
                                                         yyval.obj = "boolean";
                                                        yyval.line = val_peek(2).line;}
break;
case 87:
//#line 268 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 88:
//#line 270 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 89:
//#line 272 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                            val_peek(0).obj = ba.typeTrack.getType(val_peek(0));
                                            ba.typeTrack.assertBoolType(val_peek(0));
                                            yyval.obj = ba.typeTrack.getType(val_peek(0));
                                                yyval.line = val_peek(0).line; }
break;
case 90:
//#line 277 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                val_peek(0).obj = ba.typeTrack.getType(val_peek(0));
                                                ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.obj = ba.typeTrack.getType(val_peek(0));
                                                yyval.line = val_peek(0).line;}
break;
case 91:
//#line 284 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 92:
//#line 285 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 93:
//#line 286 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 94:
//#line 287 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 95:
//#line 288 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 96:
//#line 289 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1096 "Parser.java"
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
