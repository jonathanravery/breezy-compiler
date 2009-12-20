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
public final static short IN=275;
public final static short NOTHING=276;
public final static short NUMERIC=277;
public final static short QUOTE=278;
public final static short RETURN=279;
public final static short RETURNS=280;
public final static short WHILE=281;
public final static short REL_OP_LE=282;
public final static short REL_OP_LT=283;
public final static short REL_OP_GE=284;
public final static short REL_OP_GT=285;
public final static short EQUALS=286;
public final static short LOG_OP_EQUAL=287;
public final static short LOG_OP_AND=288;
public final static short LOG_OP_OR=289;
public final static short LOG_OP_NOT=290;
public final static short EQUAL=291;
public final static short PLUS=292;
public final static short MINUS=293;
public final static short MOD=294;
public final static short MUL=295;
public final static short DIV=296;
public final static short LEFT_SQUARE_PAREN=297;
public final static short RIGHT_SQUARE_PAREN=298;
public final static short LPAREN=299;
public final static short RPAREN=300;
public final static short COLON=301;
public final static short SEMICOLON=302;
public final static short COMMA=303;
public final static short DOT=304;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    1,    2,    5,    5,    6,    6,
    6,    6,    8,    8,    8,    8,    8,    9,    9,    9,
    9,    9,    7,    7,    7,   13,   13,   13,   13,   13,
   13,   13,   13,   17,   20,   20,   21,   21,   18,   19,
   16,   16,   14,   15,   15,    4,    4,   22,   22,   11,
   11,   11,   24,   24,   12,   12,   23,   23,   23,   23,
   23,    3,    3,   10,   10,   10,   10,   25,   25,   25,
   25,   25,   26,   26,   26,   27,   27,   27,   27,   27,
   27,   27,   27,   28,   28,   28,   28,   28,   28,
};
final static short yylen[] = {                            2,
    1,    1,    1,    2,    2,   11,    2,    0,    3,    3,
    2,    0,    2,    2,    2,    2,    2,    4,    4,    4,
    6,    6,    1,    2,    0,    1,    2,    2,    2,    1,
    1,    1,    4,   10,    9,    0,    5,    0,    8,   12,
    2,    2,    4,    6,    5,    1,    1,    2,    4,    1,
    0,    1,    1,    3,    7,    5,    1,    1,    1,    1,
    1,    1,    1,    3,    3,    3,    1,    3,    3,    3,
    3,    1,    2,    2,    1,    3,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    4,    0,    0,   57,
   58,   59,   60,   61,   63,    0,   62,    0,   46,    0,
   47,    0,    0,    0,    0,    0,    0,    0,   11,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   23,    0,    0,    0,   30,   31,   32,   49,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   82,   83,   78,   81,    0,    0,    0,    0,
   80,   42,    0,   72,   75,    0,   26,   24,    9,   10,
   27,   28,   29,    0,    0,   50,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   86,   84,   87,   85,   88,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   33,   43,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   76,    0,   89,
    0,    0,    0,   68,   71,   69,   70,    0,    0,   45,
    0,    0,    0,    0,    0,    0,    0,   44,   21,    0,
   22,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   39,   56,    0,    0,    0,    0,
    0,    0,    0,    0,   34,   55,    0,    0,    0,   40,
    0,    0,    0,    0,    0,   37,    0,    0,   35,
};
final static short yydgoto[] = {                          2,
    3,    4,   16,   20,   25,   26,   40,   41,   42,   98,
   88,  144,   43,   71,   45,   46,   47,   48,   49,  170,
  175,   21,   22,   89,   73,   74,   75,  111,
};
final static short yysindex[] = {                      -246,
 -247,    0, -216,    0, -212, -247,    0, -220,  313,    0,
    0,    0,    0,    0,    0, -205,    0,  320,    0, -198,
    0, -193,    0, -230, -189,  -72,  354, -180,    0, -276,
 -171, -165, -162, -159, -158, -164, -196, -163, -194, -236,
 -195, -192,    0, -186, -183, -178,    0,    0,    0,    0,
    0, -152, -210, -150, -154, -151, -146, -143, -142, -153,
 -152, -287,    0,    0,    0,    0, -152, -152, -152,  319,
    0,    0, -224,    0,    0, -152,    0,    0,    0,    0,
    0,    0,    0, -149,  192,    0,  319, -166, -145, -148,
 -152, -152, -152, -130, -127, -109,  206,  319,    0,    0,
  218,    0,    0,    0,    0,    0, -152, -135, -152, -152,
 -152, -152, -152, -152, -152,  230,    0,    0, -152, -202,
  319,  319,  319, -210, -126,  -96,  -95,    0, -224,    0,
 -224, -224,  319,    0,    0,    0,    0,  -87, -145,    0,
 -119, -115, -152, -278, -152, -236, -236,    0,    0,  152,
    0, -107,  242, -242, -103, -152, -152,  -74,  -80,  -86,
  254,  164, -236,  -71,    0,    0, -152,  -43, -101,  -68,
  266,  -69, -152,  -63,    0,    0,  -56,  278, -236,    0,
  -51,  -32, -236,  -64,   -7,    0,  -53,  -71,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,  219,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -97,  -46,    0,  -65,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -49,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -77,    0,  -78,  -75,  -70,  -67,  -59,    0,
    0,  176,    0,    0,    0,    0,    0,    0,    0,  -58,
    0,    0,   64,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -2,    0,    0, -219,    0, -251,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   20,   42,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -57,  -54,  -50,  -52,    0,    0,    0,    0,   86,    0,
  108,  130,  297,    0,    0,    0,    0,    0, -206,    0,
    0,    0,    0,    0,    0,  -65,  -65,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -65,  -61,    0,    0,    0,    0,    0,   -3,
    0,    0,    0,    0,    0,    0,    0,    0,  -65,    0,
    0,    0,  -65,    0,    0,    0,    0,  -61,    0,
};
final static short yygindex[] = {                         0,
    0,  227,    0,    0,    0,    0, -139,    0,    0,  -34,
 -111,    0,  -37,  -26,  195,    0,    0,    0,    0,   46,
    0,  210,  232,  134,  -76,  -62,    0,    0,
};
final static int YYTABLESIZE=617;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         44,
    3,    5,   78,   70,   99,  100,  154,  155,  141,   52,
    1,   53,  142,   44,   77,   30,   54,   85,   87,  151,
   77,   30,   53,  168,  152,    5,   97,   54,  159,   36,
  129,   37,  131,  132,  101,   36,   38,   37,   39,  182,
    6,  116,   38,  185,   39,    8,   52,   84,   52,  134,
  135,  136,  137,   63,   64,   84,  121,  122,  123,    9,
   18,   63,   64,  112,   24,   86,   65,   66,   23,  113,
  114,  115,   27,   86,   65,   66,  133,   51,   53,   67,
   53,   28,   68,   53,   87,   87,   55,   67,   69,   87,
   68,   54,   56,   54,   62,   57,   69,  140,   58,   59,
   63,   64,   61,   60,   76,   84,   79,   90,  150,   80,
  153,   63,   64,   65,   66,   81,   78,   78,   82,   44,
   44,  161,  162,   83,   65,   66,   67,   44,   44,   68,
   78,   91,  171,  118,   92,   69,   44,   67,  178,   93,
   68,   44,   94,   95,   78,   96,   69,   78,  126,   53,
  120,  130,   44,   77,   30,   44,   44,  119,   44,   12,
   12,   12,   12,   12,   12,   12,  124,  160,   36,  125,
   37,  146,  143,    8,   12,   38,   12,   39,  145,  147,
  148,   12,  149,   12,   29,   30,   31,   32,   33,   34,
   35,  157,  163,  164,  165,   36,   36,  173,  169,   36,
  174,   37,  177,  179,  186,   25,   38,   36,   39,   36,
   36,  180,   36,   77,   30,  183,  188,   36,    1,   36,
   48,    7,   51,   14,   77,   30,   13,  172,   36,    7,
   37,   15,   72,  189,   16,   38,   50,   39,  184,   36,
   17,   37,   17,   41,   20,   51,   38,   18,   39,   77,
   30,   19,  139,   38,   38,    0,    0,    3,    5,    0,
    0,    0,    0,  187,   36,    0,   37,   38,   38,    0,
   38,   38,    0,   39,    0,   38,    0,   38,    0,   79,
   79,   79,   79,    0,   79,   79,   79,   79,    0,   79,
   79,   79,   79,   79,    0,   79,    0,   79,    0,   79,
   79,   72,   72,   72,   72,    0,   72,   72,   72,   72,
    0,   72,   72,   72,   72,   72,    0,   73,    0,   73,
    0,   73,   73,   72,   72,   72,   72,    0,   72,   72,
   72,   72,    0,   72,   72,   72,   72,   72,    0,   74,
    0,   74,    0,   74,   74,   67,   67,   67,   67,    0,
   67,    0,   67,   67,    0,   67,   67,    0,    0,    0,
    0,   67,    0,   67,    0,   67,   67,   64,   64,   64,
   64,    0,   64,    0,   64,   64,    0,   64,   64,    0,
    0,    0,    0,   64,    0,   64,    0,   64,   64,   65,
   65,   65,   65,    0,   65,    0,   65,   65,    0,   65,
   65,    0,    0,    0,    0,   65,    0,   65,    0,   65,
   65,   66,   66,   66,   66,    0,   66,    0,   66,   66,
    0,   66,   66,    0,    0,    0,    0,   66,    0,   66,
    0,   66,   66,  102,  103,  104,  105,    0,  106,    0,
  107,  108,    0,  109,  110,  102,  103,  104,  105,    0,
  106,    0,  107,  108,  156,  109,  110,   79,   79,   79,
   79,    0,   79,   79,   79,   79,  167,   79,   79,   79,
   79,   79,    0,  102,  103,  104,  105,   79,  106,    0,
  107,  108,    0,  109,  110,    0,    0,  102,  103,  104,
  105,    0,  106,  117,  107,  108,    0,  109,  110,  102,
  103,  104,  105,    0,  106,  127,  107,  108,    0,  109,
  110,  102,  103,  104,  105,    0,  106,  128,  107,  108,
    0,  109,  110,  102,  103,  104,  105,    0,  106,  138,
  107,  108,    0,  109,  110,  102,  103,  104,  105,    0,
  106,  158,  107,  108,    0,  109,  110,  102,  103,  104,
  105,    0,  106,  166,  107,  108,    0,  109,  110,  102,
  103,  104,  105,    0,  106,  176,  107,  108,    0,  109,
  110,   10,   11,   12,   13,   14,    0,  181,   10,   11,
   12,   13,   14,    0,   77,    0,    0,    0,   15,    0,
   77,   77,   77,    0,   77,   19,   77,    0,   77,   77,
  102,  103,  104,  105,    0,  106,    0,  107,  108,    0,
  109,  110,   10,   11,   12,   13,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         26,
    0,    0,   40,   38,   67,   68,  146,  147,  120,  286,
  257,  299,  124,   40,  257,  258,  304,   52,   53,  298,
  257,  258,  299,  163,  303,  273,   61,  304,  271,  272,
  107,  274,  109,  110,   69,  272,  279,  274,  281,  179,
  257,   76,  279,  183,  281,  258,  298,  258,  300,  112,
  113,  114,  115,  264,  265,  258,   91,   92,   93,  280,
  266,  264,  265,  288,  258,  276,  277,  278,  267,  294,
  295,  296,  303,  276,  277,  278,  111,  258,  298,  290,
  300,  271,  293,  303,  119,  120,  258,  290,  299,  124,
  293,  298,  258,  300,  258,  258,  299,  300,  258,  258,
  264,  265,  299,  268,  299,  258,  302,  258,  143,  302,
  145,  264,  265,  277,  278,  302,  154,  155,  302,  146,
  147,  156,  157,  302,  277,  278,  290,  154,  155,  293,
  168,  286,  167,  300,  286,  299,  163,  290,  173,  286,
  293,  168,  286,  286,  182,  299,  299,  185,  258,  299,
  299,  287,  179,  257,  258,  182,  183,  303,  185,  257,
  258,  259,  260,  261,  262,  263,  297,  271,  272,  297,
  274,  267,  299,  271,  272,  279,  274,  281,  275,  267,
  300,  279,  298,  281,  257,  258,  259,  260,  261,  262,
  263,  299,  267,  274,  281,  257,  258,  299,  270,  272,
  269,  274,  272,  267,  269,  271,  279,  269,  281,  271,
  272,  268,  274,  257,  258,  267,  270,  279,    0,  281,
  267,  271,  300,  302,  257,  258,  302,  271,  272,    3,
  274,  302,   38,  188,  302,  279,   27,  281,  271,  272,
    9,  274,  302,  302,  302,  298,  279,  302,  281,  257,
  258,  302,  119,  257,  258,   -1,   -1,  257,  257,   -1,
   -1,   -1,   -1,  271,  272,   -1,  274,  271,  272,   -1,
  274,  279,   -1,  281,   -1,  279,   -1,  281,   -1,  282,
  283,  284,  285,   -1,  287,  288,  289,  290,   -1,  292,
  293,  294,  295,  296,   -1,  298,   -1,  300,   -1,  302,
  303,  282,  283,  284,  285,   -1,  287,  288,  289,  290,
   -1,  292,  293,  294,  295,  296,   -1,  298,   -1,  300,
   -1,  302,  303,  282,  283,  284,  285,   -1,  287,  288,
  289,  290,   -1,  292,  293,  294,  295,  296,   -1,  298,
   -1,  300,   -1,  302,  303,  282,  283,  284,  285,   -1,
  287,   -1,  289,  290,   -1,  292,  293,   -1,   -1,   -1,
   -1,  298,   -1,  300,   -1,  302,  303,  282,  283,  284,
  285,   -1,  287,   -1,  289,  290,   -1,  292,  293,   -1,
   -1,   -1,   -1,  298,   -1,  300,   -1,  302,  303,  282,
  283,  284,  285,   -1,  287,   -1,  289,  290,   -1,  292,
  293,   -1,   -1,   -1,   -1,  298,   -1,  300,   -1,  302,
  303,  282,  283,  284,  285,   -1,  287,   -1,  289,  290,
   -1,  292,  293,   -1,   -1,   -1,   -1,  298,   -1,  300,
   -1,  302,  303,  282,  283,  284,  285,   -1,  287,   -1,
  289,  290,   -1,  292,  293,  282,  283,  284,  285,   -1,
  287,   -1,  289,  290,  303,  292,  293,  282,  283,  284,
  285,   -1,  287,  288,  289,  290,  303,  292,  293,  294,
  295,  296,   -1,  282,  283,  284,  285,  302,  287,   -1,
  289,  290,   -1,  292,  293,   -1,   -1,  282,  283,  284,
  285,   -1,  287,  302,  289,  290,   -1,  292,  293,  282,
  283,  284,  285,   -1,  287,  300,  289,  290,   -1,  292,
  293,  282,  283,  284,  285,   -1,  287,  300,  289,  290,
   -1,  292,  293,  282,  283,  284,  285,   -1,  287,  300,
  289,  290,   -1,  292,  293,  282,  283,  284,  285,   -1,
  287,  300,  289,  290,   -1,  292,  293,  282,  283,  284,
  285,   -1,  287,  300,  289,  290,   -1,  292,  293,  282,
  283,  284,  285,   -1,  287,  300,  289,  290,   -1,  292,
  293,  259,  260,  261,  262,  263,   -1,  300,  259,  260,
  261,  262,  263,   -1,  288,   -1,   -1,   -1,  276,   -1,
  294,  295,  296,   -1,  298,  276,  300,   -1,  302,  303,
  282,  283,  284,  285,   -1,  287,   -1,  289,  290,   -1,
  292,  293,  259,  260,  261,  262,  263,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=304;
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
"FUNCTION","IF","IN","NOTHING","NUMERIC","QUOTE","RETURN","RETURNS","WHILE",
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
"statement : for_loop",
"statement : IDENTIFIER EQUALS exp SEMICOLON",
"if_statement : IF LPAREN exp RPAREN BEGIN control_body END IF else_if else",
"else_if : ELSEIF LPAREN exp RPAREN BEGIN control_body END ELSEIF else_if",
"else_if :",
"else : ELSE BEGIN control_body END ELSE",
"else :",
"while_loop : WHILE LPAREN exp RPAREN BEGIN control_body END WHILE",
"for_loop : FOR EACH LPAREN IDENTIFIER IN exp RPAREN BEGIN control_body END FOR EACH",
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

//#line 287 "Breezy.yacc"

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
//#line 518 "Parser.java"
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
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6),val_peek(4).sval,val_peek(2).sval,val_peek(0),val_peek(8).line,Scope.GLOBAL.getName()); }
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
{yyval.sval = "String " + val_peek(0).sval + "=\"\""; ba.typeTrack.addID(val_peek(0).sval,"string",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 14:
//#line 61 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval + "=false"; ba.typeTrack.addID(val_peek(0).sval,"boolean",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 15:
//#line 62 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval + "=0"; ba.typeTrack.addID(val_peek(0).sval,"number",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 16:
//#line 63 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval); ba.typeTrack.addID(val_peek(0).sval,"ArrayList",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 17:
//#line 64 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval); ba.typeTrack.addID(val_peek(0).sval,"HashMap",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 18:
//#line 69 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertStringType(val_peek(0));
                                                                         ba.typeTrack.addID(val_peek(2).sval,"string",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 19:
//#line 72 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertNumberType(val_peek(0));
                                                                         ba.typeTrack.addID(val_peek(2).sval,"number",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 20:
//#line 75 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                          ba.typeTrack.addID(val_peek(2).sval,"boolean",val_peek(2).line,Scope.LOCAL.getName());}
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
{yyval.sval = val_peek(0).sval;}
break;
case 33:
//#line 97 "Breezy.yacc"
{val_peek(3).obj = ba.typeTrack.getType(val_peek(3),Scope.LOCAL.getName());
                                                                        ba.typeTrack.assertSameType(val_peek(3),val_peek(1),val_peek(2));
                                                                        yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 34:
//#line 105 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(7));
                                                    yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 35:
//#line 113 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(6));
                                                yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 36:
//#line 115 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 37:
//#line 122 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 38:
//#line 123 "Breezy.yacc"
{yyval.sval = "";}
break;
case 39:
//#line 130 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(5));
                                        yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 40:
//#line 137 "Breezy.yacc"
{ba.typeTrack.assertArrayOrHashType(val_peek(6));
                                                    yyval.sval = "for(TypedParserVal " +val_peek(8).sval+ " : " + val_peek(6).sval + "){" + val_peek(3).sval + "\n}\n";}
break;
case 41:
//#line 141 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 42:
//#line 142 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 43:
//#line 145 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                            yyval.obj = ba.typeTrack.getType(val_peek(3), Scope.GLOBAL.getName());
                                                            yyval.line = val_peek(3).line;}
break;
case 44:
//#line 153 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, Scope.LOCAL.getName(), val_peek(1).sval);}
break;
case 45:
//#line 154 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, Scope.LOCAL.getName(), null);}
break;
case 46:
//#line 158 "Breezy.yacc"
{yyval.sval = "";}
break;
case 47:
//#line 159 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 48:
//#line 162 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(0).sval,val_peek(1).obj.toString(),val_peek(0).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 49:
//#line 164 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(2).sval,val_peek(3).obj.toString(),val_peek(2).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 50:
//#line 168 "Breezy.yacc"
{yyval.sval = "";}
break;
case 51:
//#line 169 "Breezy.yacc"
{yyval.sval = "";}
break;
case 52:
//#line 170 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 53:
//#line 173 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 54:
//#line 174 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 55:
//#line 177 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")" + val_peek(6).sval;}
break;
case 56:
//#line 178 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 57:
//#line 182 "Breezy.yacc"
{yyval.sval = "boolean "; yyval.obj = "boolean";}
break;
case 58:
//#line 183 "Breezy.yacc"
{yyval.sval = "String "; yyval.obj = "string";}
break;
case 59:
//#line 184 "Breezy.yacc"
{yyval.sval = "double "; yyval.obj = "number";}
break;
case 60:
//#line 185 "Breezy.yacc"
{yyval.sval = "ArrayList "; yyval.obj = "ArrayList";}
break;
case 61:
//#line 186 "Breezy.yacc"
{yyval.sval = "HashMap "; yyval.obj = "HashMap";}
break;
case 62:
//#line 189 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 63:
//#line 190 "Breezy.yacc"
{yyval.sval = "void"; yyval.obj = "void";}
break;
case 64:
//#line 193 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                        yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                        yyval.obj = val_peek(2).obj;
                                                        yyval.line = val_peek(2).line;}
break;
case 65:
//#line 197 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 66:
//#line 201 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 67:
//#line 205 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 68:
//#line 209 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 69:
//#line 213 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 70:
//#line 217 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 71:
//#line 221 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 72:
//#line 225 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 73:
//#line 230 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.sval = " !" + val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 74:
//#line 234 "Breezy.yacc"
{  ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.sval = " -"+ val_peek(0).sval;
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 75:
//#line 238 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 76:
//#line 243 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 77:
//#line 246 "Breezy.yacc"
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
case 78:
//#line 257 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 79:
//#line 260 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0), Scope.LOCAL.getName());
                                                    yyval.line = val_peek(0).line;}
break;
case 80:
//#line 263 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 81:
//#line 266 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 82:
//#line 269 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 83:
//#line 272 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 84:
//#line 277 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 85:
//#line 278 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 86:
//#line 279 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 87:
//#line 280 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 88:
//#line 281 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 89:
//#line 282 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1094 "Parser.java"
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
