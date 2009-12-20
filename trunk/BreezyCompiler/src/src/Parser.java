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
public final static short EQUALS=282;
public final static short LOG_OP_EQUAL=283;
public final static short LOG_OP_AND=284;
public final static short LOG_OP_OR=285;
public final static short LOG_OP_NOT=286;
public final static short REL_OP_LE=287;
public final static short REL_OP_LT=288;
public final static short REL_OP_GE=289;
public final static short REL_OP_GT=290;
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
    6,    6,    6,    8,    8,    8,    8,    8,    8,    9,
    9,    9,    9,    9,    7,    7,    7,   13,   13,   13,
   13,   13,   13,   13,   13,   17,   20,   20,   21,   21,
   18,   19,   19,   22,   16,   16,   14,   15,   15,    4,
    4,   23,   23,   23,   11,   11,   11,   25,   25,   25,
   12,   12,   12,   24,   24,   24,   24,   24,   24,    3,
    3,    3,   10,   10,   10,   10,   10,   26,   26,   26,
   26,   26,   26,   27,   27,   27,   27,   28,   28,   28,
   28,   28,   28,   28,   28,   28,   29,   29,   29,   29,
   29,   29,
};
final static short yylen[] = {                            2,
    1,    1,    1,    2,    2,   11,    2,    0,    3,    3,
    2,    1,    0,    2,    2,    2,    2,    2,    0,    4,
    4,    4,    6,    6,    1,    2,    0,    1,    2,    2,
    2,    1,    1,    1,    4,   10,    9,    0,    5,    0,
    8,   12,    1,    1,    2,    2,    4,    6,    5,    1,
    1,    2,    4,    1,    1,    0,    1,    1,    3,    1,
    7,    5,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    3,    3,    3,    1,    1,    3,    3,    3,
    3,    1,    1,    2,    2,    1,    1,    3,    3,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    4,    0,    0,   69,
   64,   65,   66,   67,   68,   71,    0,   70,    0,    0,
   50,    0,   51,    0,    0,    0,   12,    0,    0,    0,
    0,   43,   11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   25,    0,    0,    0,
   32,   33,   34,   53,    6,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   77,    0,   94,   95,   90,
   93,    0,    0,    0,    0,   92,   46,    0,   82,   86,
    0,   28,   26,    9,   10,   29,   30,   31,    0,    0,
    0,   55,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  101,    0,    0,
   99,   97,  100,   98,    0,    0,    0,    0,    0,    0,
    0,    0,   35,   47,    0,    0,    0,    0,    0,    0,
    0,   44,    0,    0,   88,    0,  102,    0,    0,    0,
   78,   81,   79,   80,    0,    0,   49,    0,    0,   63,
    0,    0,    0,    0,    0,   48,   23,    0,   24,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   41,   62,    0,    0,    0,    0,    0,    0,
    0,    0,   36,   61,    0,    0,    0,   42,    0,    0,
    0,    0,    0,   39,    0,    0,   37,
};
final static short yydgoto[] = {                          2,
    3,    4,   17,   22,   28,   29,   44,   45,   46,  104,
   94,  152,   47,   76,   49,   50,   51,   52,   53,  178,
  183,  133,   23,   24,   95,   78,   79,   80,  117,
};
final static short yysindex[] = {                      -243,
 -241,    0, -198,    0, -209, -241,    0, -207,  243,    0,
    0,    0,    0,    0,    0,    0, -194,    0,  251,    0,
    0, -192,    0, -181, -177, -219,    0, -185,  -43,  261,
 -171,    0,    0, -270, -168, -160, -159, -158, -156, -153,
 -179, -110, -175,   34, -184, -183,    0, -173, -172, -170,
    0,    0,    0,    0,    0, -105, -182, -127, -146, -145,
 -143, -142, -141, -154, -105,    0, -253,    0,    0,    0,
    0, -105, -105, -105,  406,    0,    0, -254,    0,    0,
 -105,    0,    0,    0,    0,    0,    0,    0, -149,  263,
    0,    0,  406, -157, -147, -136, -105, -105, -105, -140,
 -132,  -92,  294,  406,    0,    0,  310,    0, -105, -113,
    0,    0,    0,    0, -105, -105, -105, -105, -105, -105,
 -105,  326,    0,    0,  -87, -208,  406,  406,  406, -182,
 -252,    0, -101,  -88,    0, -254,    0, -254, -254,  406,
    0,    0,    0,    0,  -85, -147,    0, -125, -118,    0,
 -105, -240, -105,   34,   34,    0,    0, -180,    0, -115,
  342,  -32,   -6, -105, -105,  -75,  -81,  -86,  358,   31,
   34,  -74,    0,    0, -105,   -2, -102,  -64,  374,  -63,
 -105,  -56,    0,    0,  -47,  390,   34,    0,  -45,   24,
   34,  -46,   28,    0,  -42,  -74,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,  227,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -249,
    0,    0,    0,    0, -236,  -35,    0,    0, -263,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -38,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -70,    0,  -68,  -67,
  -65,  -61,  -58,    0,    0,    0,  245,    0,    0,    0,
    0,    0,    0,    0,  -57,    0,    0,  135,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   53,    0,
  116,    0,  -96,    0, -287,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   74,   95,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -54,  -49,  -40,  -55,
    0,    0,    0,    0,    0,  156,    0,  177,  198,  273,
    0,    0,    0,    0,    0, -267,    0,    0,    0,    0,
    0,    0,    0,  -25,  -25,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -25,  -71,    0,    0,    0,    0,    0,   54,    0,    0,
    0,    0,    0,    0,    0,    0,  -25,    0,    0,    0,
  -25,    0,    0,    0,    0,  -71,    0,
};
final static short yygindex[] = {                         0,
    0,  254,    0,    0,    0,    0,  -90,    0,    0,  -37,
  -59,    0,  -41,  -29,  218,    0,    0,    0,    0,   65,
    0,    0,  233,  255,  142,  -99,  -66,    0,    0,
};
final static int YYTABLESIZE=699;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         48,
    3,    5,   83,  150,   75,  105,  106,   27,   69,  136,
   57,   56,   57,    1,   48,  138,  139,   54,   90,   93,
   13,   13,   13,   13,   13,   13,   13,  103,   57,  118,
   59,    5,   59,   58,    8,   13,  107,   13,   19,  119,
  120,  121,   13,  122,   13,   57,  151,   91,    8,   89,
   58,  141,  142,  143,  144,   68,   69,  159,    6,  127,
  128,  129,  160,  162,  163,   13,  148,   92,   70,   71,
  149,   19,    9,   91,   25,   89,   26,   72,   27,  140,
  176,   68,   69,   30,   73,   31,   55,   93,   93,   59,
   74,  147,   93,   92,   70,   71,  190,   60,   61,   62,
  193,   63,  108,   72,  109,  110,  111,  112,  113,  114,
   73,  115,  116,  158,   64,  161,   74,   84,   85,   65,
   83,   83,  164,   81,   48,   48,  169,  170,   86,   87,
   96,   88,   48,   48,   83,   97,   98,  179,   99,  100,
  101,   48,  124,  186,  102,   66,   48,   67,   83,   57,
   66,   83,   89,   68,   69,  125,  130,   48,   68,   69,
   48,   48,  126,   48,  131,  132,   70,   71,   91,  137,
   89,   70,   71,  153,  156,   72,   68,   69,  154,  157,
   72,  155,   73,  165,   38,   38,   38,   73,   74,   70,
   71,  171,  172,   74,  173,  177,  181,   38,   72,   38,
   38,   58,   38,   58,  182,   73,   58,   38,  185,   38,
  187,   74,   32,   33,   34,   35,   36,   37,   38,   39,
  188,  191,  194,   32,   82,   34,    1,  196,   40,   56,
   41,   52,    7,   15,   14,   42,   16,   43,  167,   40,
   17,   41,   56,   18,   45,   27,   42,   22,   43,   32,
   82,   34,   20,   32,   82,   34,    7,    3,    5,   77,
  197,   21,   54,   18,  168,   40,  146,   41,  180,   40,
    0,   41,   42,    0,   43,    0,   42,    0,   43,   32,
   82,   34,    0,   32,   82,   34,    0,    0,    0,   32,
   82,   34,    0,    0,  192,   40,    0,   41,  195,   40,
    0,   41,   42,    0,   43,   40,   42,   41,   43,   40,
   40,   40,   42,  108,   43,  109,  110,  111,  112,  113,
  114,    0,  115,  116,   40,   40,    0,   40,    0,    0,
    0,    0,   40,  175,   40,   91,   91,   91,   91,   91,
   91,   91,   91,    0,   91,   91,   91,   91,   91,    0,
   91,    0,   91,    0,   91,   91,   82,   82,   82,   82,
   82,   82,   82,   82,    0,   82,   82,   82,   82,   82,
    0,   84,    0,   84,    0,   84,   84,   82,   82,   82,
   82,   82,   82,   82,   82,    0,   82,   82,   82,   82,
   82,    0,   85,    0,   85,    0,   85,   85,   77,   83,
   77,   77,   77,   77,   77,   77,    0,   77,   77,   83,
   83,   83,    0,   60,    0,   60,    0,   76,   60,   76,
   76,   76,   76,   76,   76,    0,   76,   76,    0,    0,
    0,    0,   76,    0,   76,    0,   76,   76,   73,    0,
   73,   73,   73,   73,   73,   73,    0,   73,   73,    0,
    0,    0,    0,   73,    0,   73,    0,   73,   73,   74,
    0,   74,   74,   74,   74,   74,   74,    0,   74,   74,
    0,    0,    0,    0,   74,    0,   74,    0,   74,   74,
   75,    0,   75,   75,   75,   75,   75,   75,    0,   75,
   75,    0,    0,    0,    0,   75,    0,   75,   10,   75,
   75,   11,   12,   13,   14,   15,   20,    0,    0,   11,
   12,   13,   14,   15,    0,    0,   20,    0,   16,   11,
   12,   13,   14,   15,    0,    0,   21,   91,   91,   91,
   91,   91,   91,   91,   91,    0,   91,   91,   91,   91,
   91,    0,    0,    0,    0,  108,   91,  109,  110,  111,
  112,  113,  114,    0,  115,  116,   89,    0,    0,    0,
    0,    0,    0,    0,  123,    0,   89,   89,   89,    0,
   89,    0,   89,    0,   89,   89,  108,    0,  109,  110,
  111,  112,  113,  114,    0,  115,  116,    0,    0,    0,
    0,    0,  108,  134,  109,  110,  111,  112,  113,  114,
    0,  115,  116,    0,    0,    0,    0,    0,  108,  135,
  109,  110,  111,  112,  113,  114,    0,  115,  116,    0,
    0,    0,    0,    0,  108,  145,  109,  110,  111,  112,
  113,  114,    0,  115,  116,    0,    0,    0,    0,    0,
  108,  166,  109,  110,  111,  112,  113,  114,    0,  115,
  116,    0,    0,    0,    0,    0,  108,  174,  109,  110,
  111,  112,  113,  114,    0,  115,  116,    0,    0,    0,
    0,    0,  108,  184,  109,  110,  111,  112,  113,  114,
    0,  115,  116,    0,    0,    0,    0,    0,  108,  189,
  109,  110,  111,  112,  113,  114,    0,  115,  116,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         29,
    0,    0,   44,  256,   42,   72,   73,  271,  258,  109,
  298,  282,  300,  257,   44,  115,  116,  267,   56,   57,
  257,  258,  259,  260,  261,  262,  263,   65,  299,  284,
  298,  273,  300,  304,  271,  272,   74,  274,  302,  294,
  295,  296,  279,   81,  281,  299,  299,  256,  258,  258,
  304,  118,  119,  120,  121,  264,  265,  298,  257,   97,
   98,   99,  303,  154,  155,  302,  126,  276,  277,  278,
  130,  266,  280,  256,  267,  258,  258,  286,  256,  117,
  171,  264,  265,  303,  293,  271,  258,  125,  126,  258,
  299,  300,  130,  276,  277,  278,  187,  258,  258,  258,
  191,  258,  283,  286,  285,  286,  287,  288,  289,  290,
  293,  292,  293,  151,  268,  153,  299,  302,  302,  299,
  162,  163,  303,  299,  154,  155,  164,  165,  302,  302,
  258,  302,  162,  163,  176,  282,  282,  175,  282,  282,
  282,  171,  300,  181,  299,  256,  176,  258,  190,  299,
  256,  193,  258,  264,  265,  303,  297,  187,  264,  265,
  190,  191,  299,  193,  297,  258,  277,  278,  256,  283,
  258,  277,  278,  275,  300,  286,  264,  265,  267,  298,
  286,  267,  293,  299,  256,  257,  258,  293,  299,  277,
  278,  267,  274,  299,  281,  270,  299,  269,  286,  271,
  272,  298,  274,  300,  269,  293,  303,  279,  272,  281,
  267,  299,  256,  257,  258,  259,  260,  261,  262,  263,
  268,  267,  269,  256,  257,  258,    0,  270,  272,  300,
  274,  267,  271,  302,  302,  279,  302,  281,  271,  272,
  302,  274,  298,  302,  302,  271,  279,  302,  281,  256,
  257,  258,  302,  256,  257,  258,    3,  257,  257,   42,
  196,  302,   30,    9,  271,  272,  125,  274,  271,  272,
   -1,  274,  279,   -1,  281,   -1,  279,   -1,  281,  256,
  257,  258,   -1,  256,  257,  258,   -1,   -1,   -1,  256,
  257,  258,   -1,   -1,  271,  272,   -1,  274,  271,  272,
   -1,  274,  279,   -1,  281,  272,  279,  274,  281,  256,
  257,  258,  279,  283,  281,  285,  286,  287,  288,  289,
  290,   -1,  292,  293,  271,  272,   -1,  274,   -1,   -1,
   -1,   -1,  279,  303,  281,  283,  284,  285,  286,  287,
  288,  289,  290,   -1,  292,  293,  294,  295,  296,   -1,
  298,   -1,  300,   -1,  302,  303,  283,  284,  285,  286,
  287,  288,  289,  290,   -1,  292,  293,  294,  295,  296,
   -1,  298,   -1,  300,   -1,  302,  303,  283,  284,  285,
  286,  287,  288,  289,  290,   -1,  292,  293,  294,  295,
  296,   -1,  298,   -1,  300,   -1,  302,  303,  283,  284,
  285,  286,  287,  288,  289,  290,   -1,  292,  293,  294,
  295,  296,   -1,  298,   -1,  300,   -1,  283,  303,  285,
  286,  287,  288,  289,  290,   -1,  292,  293,   -1,   -1,
   -1,   -1,  298,   -1,  300,   -1,  302,  303,  283,   -1,
  285,  286,  287,  288,  289,  290,   -1,  292,  293,   -1,
   -1,   -1,   -1,  298,   -1,  300,   -1,  302,  303,  283,
   -1,  285,  286,  287,  288,  289,  290,   -1,  292,  293,
   -1,   -1,   -1,   -1,  298,   -1,  300,   -1,  302,  303,
  283,   -1,  285,  286,  287,  288,  289,  290,   -1,  292,
  293,   -1,   -1,   -1,   -1,  298,   -1,  300,  256,  302,
  303,  259,  260,  261,  262,  263,  256,   -1,   -1,  259,
  260,  261,  262,  263,   -1,   -1,  256,   -1,  276,  259,
  260,  261,  262,  263,   -1,   -1,  276,  283,  284,  285,
  286,  287,  288,  289,  290,   -1,  292,  293,  294,  295,
  296,   -1,   -1,   -1,   -1,  283,  302,  285,  286,  287,
  288,  289,  290,   -1,  292,  293,  284,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  302,   -1,  294,  295,  296,   -1,
  298,   -1,  300,   -1,  302,  303,  283,   -1,  285,  286,
  287,  288,  289,  290,   -1,  292,  293,   -1,   -1,   -1,
   -1,   -1,  283,  300,  285,  286,  287,  288,  289,  290,
   -1,  292,  293,   -1,   -1,   -1,   -1,   -1,  283,  300,
  285,  286,  287,  288,  289,  290,   -1,  292,  293,   -1,
   -1,   -1,   -1,   -1,  283,  300,  285,  286,  287,  288,
  289,  290,   -1,  292,  293,   -1,   -1,   -1,   -1,   -1,
  283,  300,  285,  286,  287,  288,  289,  290,   -1,  292,
  293,   -1,   -1,   -1,   -1,   -1,  283,  300,  285,  286,
  287,  288,  289,  290,   -1,  292,  293,   -1,   -1,   -1,
   -1,   -1,  283,  300,  285,  286,  287,  288,  289,  290,
   -1,  292,  293,   -1,   -1,   -1,   -1,   -1,  283,  300,
  285,  286,  287,  288,  289,  290,   -1,  292,  293,
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
"EQUALS","LOG_OP_EQUAL","LOG_OP_AND","LOG_OP_OR","LOG_OP_NOT","REL_OP_LE",
"REL_OP_LT","REL_OP_GE","REL_OP_GT","EQUAL","PLUS","MINUS","MOD","MUL","DIV",
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
"declarations : error",
"declarations :",
"type_declaration : STRING IDENTIFIER",
"type_declaration : BOOLEAN IDENTIFIER",
"type_declaration : NUMBER IDENTIFIER",
"type_declaration : ARRAY IDENTIFIER",
"type_declaration : HASH IDENTIFIER",
"type_declaration :",
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
"for_loop : FOR EACH LPAREN loop_id IN exp RPAREN BEGIN control_body END FOR EACH",
"for_loop : error",
"loop_id : IDENTIFIER",
"return_statement : RETURN exp",
"return_statement : RETURN complex_type_method_invocation",
"function_call : IDENTIFIER LPAREN params RPAREN",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN",
"aparams : NOTHING",
"aparams : aparams_",
"aparams_ : type IDENTIFIER",
"aparams_ : type IDENTIFIER COMMA aparams_",
"aparams_ : error",
"params : NOTHING",
"params :",
"params : params_",
"params_ : exp",
"params_ : params_ COMMA params_",
"params_ : error",
"hash_params : hash_params COMMA LPAREN exp COMMA exp RPAREN",
"hash_params : LPAREN exp COMMA exp RPAREN",
"hash_params : error",
"type : BOOLEAN",
"type : STRING",
"type : NUMBER",
"type : ARRAY",
"type : HASH",
"type : error",
"return_type : type",
"return_type : NOTHING",
"return_type : error",
"exp : exp LOG_OP_OR term",
"exp : exp PLUS term",
"exp : exp MINUS term",
"exp : term",
"exp : error",
"term : term LOG_OP_AND unary",
"term : term MUL unary",
"term : term DIV unary",
"term : term MOD unary",
"term : unary",
"term : error",
"unary : LOG_OP_NOT unary",
"unary : MINUS unary",
"unary : factor",
"unary : error",
"factor : LPAREN exp RPAREN",
"factor : exp rel_op exp",
"factor : NUMERIC",
"factor : IDENTIFIER",
"factor : function_call",
"factor : QUOTE",
"factor : TRUE",
"factor : FALSE",
"factor : error",
"rel_op : REL_OP_LT",
"rel_op : REL_OP_GT",
"rel_op : REL_OP_LE",
"rel_op : REL_OP_GE",
"rel_op : LOG_OP_EQUAL",
"rel_op : LOG_OP_NOT LOG_OP_EQUAL",
};

//#line 308 "Breezy.yacc"

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
//#line 561 "Parser.java"
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
//#line 27 "Breezy.yacc"
{ba.DumpFile(val_peek(0).sval);}
break;
case 2:
//#line 30 "Breezy.yacc"
{yyval.sval= val_peek(0).sval;}
break;
case 3:
//#line 31 "Breezy.yacc"
{yyval.sval="";}
break;
case 4:
//#line 32 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 5:
//#line 33 "Breezy.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 6:
//#line 43 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6),val_peek(4).sval,val_peek(2).sval,val_peek(0),val_peek(8).line,Scope.GLOBAL.getName()); }
break;
case 7:
//#line 48 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 8:
//#line 49 "Breezy.yacc"
{yyval.sval = "";}
break;
case 9:
//#line 54 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 10:
//#line 55 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 11:
//#line 56 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + "";}
break;
case 12:
//#line 57 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 13:
//#line 58 "Breezy.yacc"
{yyval.sval = "";}
break;
case 14:
//#line 62 "Breezy.yacc"
{yyval.sval = "String " + val_peek(0).sval + "=\"\""; ba.typeTrack.addID(val_peek(0).sval,"string",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 15:
//#line 63 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval + "=false"; ba.typeTrack.addID(val_peek(0).sval,"boolean",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 16:
//#line 64 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval + "=0"; ba.typeTrack.addID(val_peek(0).sval,"number",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 17:
//#line 65 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval); ba.typeTrack.addID(val_peek(0).sval,"ArrayList",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 18:
//#line 66 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval); ba.typeTrack.addID(val_peek(0).sval,"HashMap",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 20:
//#line 72 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertStringType(val_peek(0));
                                                                         ba.typeTrack.addID(val_peek(2).sval,"string",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 21:
//#line 75 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertNumberType(val_peek(0));
                                                                         ba.typeTrack.addID(val_peek(2).sval,"number",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 22:
//#line 78 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                          ba.typeTrack.addID(val_peek(2).sval,"boolean",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 23:
//#line 81 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 24:
//#line 83 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 25:
//#line 87 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 26:
//#line 88 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 27:
//#line 89 "Breezy.yacc"
{yyval.sval = "";}
break;
case 28:
//#line 93 "Breezy.yacc"
{yyval.sval = "";}
break;
case 29:
//#line 94 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 30:
//#line 95 "Breezy.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 31:
//#line 96 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 32:
//#line 97 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 33:
//#line 98 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 34:
//#line 99 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 35:
//#line 100 "Breezy.yacc"
{val_peek(3).obj = ba.typeTrack.getType(val_peek(3),Scope.LOCAL.getName());
                                                                        ba.typeTrack.assertSameType(val_peek(3),val_peek(1),val_peek(2));
                                                                        yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 36:
//#line 108 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(7));
                                                    yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 37:
//#line 117 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(6));
                                                yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 38:
//#line 119 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 39:
//#line 126 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 40:
//#line 127 "Breezy.yacc"
{yyval.sval = "";}
break;
case 41:
//#line 134 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(5));
                                        yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 42:
//#line 141 "Breezy.yacc"
{ba.typeTrack.assertArrayOrHashType(val_peek(6));
                                                    ba.typeTrack.removeLocalID(val_peek(8));
                                                    yyval.sval = "for(TypedParserVal " +val_peek(8).sval+ " : " + val_peek(6).sval + "){" + val_peek(3).sval + "\n}\n";}
break;
case 43:
//#line 144 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 44:
//#line 147 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(0).sval,"TypedParserVal",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 45:
//#line 150 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 46:
//#line 151 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 47:
//#line 154 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                            yyval.obj = ba.typeTrack.getType(val_peek(3), Scope.GLOBAL.getName());
                                                            yyval.line = val_peek(3).line;}
break;
case 48:
//#line 162 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, Scope.LOCAL.getName(), val_peek(1).sval);
                                                                        yyval.obj = "void";}
break;
case 49:
//#line 164 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, Scope.LOCAL.getName(), null);
                                                                        yyval.obj = "void";}
break;
case 50:
//#line 169 "Breezy.yacc"
{yyval.sval = "";}
break;
case 51:
//#line 170 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 173 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(0).sval,val_peek(1).obj.toString(),val_peek(0).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 53:
//#line 175 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(2).sval,val_peek(3).obj.toString(),val_peek(2).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 54:
//#line 177 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 55:
//#line 180 "Breezy.yacc"
{yyval.sval = "";}
break;
case 56:
//#line 181 "Breezy.yacc"
{yyval.sval = "";}
break;
case 57:
//#line 182 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 58:
//#line 185 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 59:
//#line 186 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 60:
//#line 187 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 61:
//#line 190 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")" + val_peek(6).sval;}
break;
case 62:
//#line 191 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 63:
//#line 192 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 64:
//#line 196 "Breezy.yacc"
{yyval.sval = "boolean "; yyval.obj = "boolean";}
break;
case 65:
//#line 197 "Breezy.yacc"
{yyval.sval = "String "; yyval.obj = "string";}
break;
case 66:
//#line 198 "Breezy.yacc"
{yyval.sval = "double "; yyval.obj = "number";}
break;
case 67:
//#line 199 "Breezy.yacc"
{yyval.sval = "ArrayList "; yyval.obj = "ArrayList";}
break;
case 68:
//#line 200 "Breezy.yacc"
{yyval.sval = "HashMap "; yyval.obj = "HashMap";}
break;
case 69:
//#line 201 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 70:
//#line 204 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 71:
//#line 205 "Breezy.yacc"
{yyval.sval = "void"; yyval.obj = "void";}
break;
case 72:
//#line 206 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 73:
//#line 209 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                        yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                        yyval.obj = val_peek(2).obj;
                                                        yyval.line = val_peek(2).line;}
break;
case 74:
//#line 213 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 75:
//#line 217 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 76:
//#line 221 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 77:
//#line 223 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 78:
//#line 226 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 79:
//#line 230 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 80:
//#line 234 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 81:
//#line 238 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 82:
//#line 242 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 83:
//#line 245 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 84:
//#line 248 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.sval = " !" + val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 85:
//#line 252 "Breezy.yacc"
{  ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.sval = " -"+ val_peek(0).sval;
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 86:
//#line 256 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 87:
//#line 259 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 88:
//#line 262 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 89:
//#line 265 "Breezy.yacc"
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
case 90:
//#line 276 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 91:
//#line 279 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0), Scope.LOCAL.getName());
                                                    yyval.line = val_peek(0).line;}
break;
case 92:
//#line 282 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 93:
//#line 285 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 94:
//#line 288 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 95:
//#line 291 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 96:
//#line 294 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 97:
//#line 298 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 98:
//#line 299 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 99:
//#line 300 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 100:
//#line 301 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 101:
//#line 302 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 102:
//#line 303 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1188 "Parser.java"
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
