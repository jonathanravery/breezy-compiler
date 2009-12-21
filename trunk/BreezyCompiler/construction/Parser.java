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
public final static short PRINT=305;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    1,    2,    5,    5,    6,    6,
    6,    6,    6,    8,    8,    8,    8,    8,    8,    9,
    9,    9,    9,    9,    7,    7,    7,   13,   13,   13,
   13,   13,   13,   13,   13,   17,   20,   20,   21,   21,
   18,   19,   19,   22,   16,   16,   14,   14,   15,   15,
    4,    4,   23,   23,   23,   11,   11,   11,   25,   25,
   25,   12,   12,   12,   24,   24,   24,   24,   24,   24,
    3,    3,    3,   10,   10,   10,   10,   10,   26,   26,
   26,   26,   26,   26,   27,   27,   27,   27,   28,   28,
   28,   28,   28,   28,   28,   28,   28,   29,   29,   29,
   29,   29,   29,
};
final static short yylen[] = {                            2,
    1,    1,    1,    2,    2,   11,    2,    0,    3,    3,
    2,    1,    0,    2,    2,    2,    2,    2,    0,    4,
    4,    4,    6,    6,    1,    2,    0,    1,    2,    2,
    2,    1,    1,    1,    4,   10,    9,    0,    5,    0,
    8,   12,    1,    1,    2,    2,    4,    2,    6,    5,
    1,    1,    2,    4,    1,    1,    0,    1,    1,    3,
    1,    7,    5,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    3,    3,    1,    1,    3,    3,
    3,    3,    1,    1,    2,    2,    1,    1,    3,    3,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    4,    0,    0,   70,
   65,   66,   67,   68,   69,   72,    0,   71,    0,    0,
   51,    0,   52,    0,    0,    0,   12,    0,    0,    0,
    0,   43,   11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   25,    0,    0,
    0,   32,   33,   34,   54,    6,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   78,    0,   95,   96,
   91,   94,    0,    0,    0,    0,   93,   46,    0,   83,
   87,    0,   48,   28,   26,    9,   10,   29,   30,   31,
    0,    0,    0,   56,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  102,
    0,    0,  100,   98,  101,   99,    0,    0,    0,    0,
    0,    0,    0,    0,   35,   47,    0,    0,    0,    0,
    0,    0,    0,   44,    0,    0,   89,    0,  103,    0,
    0,    0,   79,   82,   80,   81,    0,    0,   50,    0,
    0,   64,    0,    0,    0,    0,    0,   49,   23,    0,
   24,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,   63,    0,    0,    0,    0,
    0,    0,    0,    0,   36,   62,    0,    0,    0,   42,
    0,    0,    0,    0,    0,   39,    0,    0,   37,
};
final static short yydgoto[] = {                          2,
    3,    4,   17,   22,   28,   29,   45,   46,   47,  106,
   96,  154,   48,   77,   50,   51,   52,   53,   54,  180,
  185,  135,   23,   24,   97,   79,   80,   81,  119,
};
final static short yysindex[] = {                      -235,
 -233,    0, -232,    0, -217, -233,    0, -229,  -24,    0,
    0,    0,    0,    0,    0,    0, -206,    0,  279,    0,
    0, -184,    0, -188, -169, -211,    0, -172, -248,  344,
 -148,    0,    0, -265, -144, -137, -136, -132, -127, -135,
 -167, -180, -165, -120,   32, -163, -161,    0, -160, -159,
 -157,    0,    0,    0,    0,    0, -108, -197, -111, -130,
 -124, -123, -117, -115, -131, -108,    0, -230,    0,    0,
    0,    0, -108, -108, -108,  405,    0,    0, -252,    0,
    0, -108,    0,    0,    0,    0,    0,    0,    0,    0,
 -128,  282,    0,    0,  405, -121, -126, -119, -108, -108,
 -108, -125, -116,  -85,  293,  405,    0,    0,  309,    0,
 -108, -107,    0,    0,    0,    0, -108, -108, -108, -108,
 -108, -108, -108,  325,    0,    0, -103, -228,  405,  405,
  405, -197, -253,    0,  -93,  -83,    0, -252,    0, -252,
 -252,  405,    0,    0,    0,    0,  -81, -126,    0, -105,
  -97,    0, -108, -251, -108,   32,   32,    0,    0,  229,
    0, -110,  341,  -58,  -28, -108, -108,  -61,  -71,  -72,
  357,  241,   32,  -59,    0,    0, -108,   -8,  -87,  -51,
  373,  -53, -108,  -45,    0,    0,  -43,  389,   32,    0,
  -40,    4,   32,  -38,   24,    0,  -44,  -59,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,  233,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -240,
    0,    0,    0,    0,   61,  -33,    0,    0, -236,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -31,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -55,    0,  -60,
  -48,  -47,  -46,  -35,    0,    0,    0,  264,    0,    0,
    0,    0,    0,    0,    0,  -34,    0,    0,  145,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   62,    0,  126,    0, -183,    0, -225,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   84,  105,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -32,  -30,
  -23,  -14,    0,    0,    0,    0,    0,  166,    0,  187,
  208, -191,    0,    0,    0,    0,    0, -212,    0,    0,
    0,    0,    0,    0,    0,   -6,   -6,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   -6,  -64,    0,    0,    0,    0,    0,   36,
    0,    0,    0,    0,    0,    0,    0,    0,   -6,    0,
    0,    0,   -6,    0,    0,    0,    0,  -64,    0,
};
final static short yygindex[] = {                         0,
    0,  254,    0,    0,    0,    0,   31,    0,    0,  -37,
 -109,    0,  -41,  -29,  227,    0,    0,    0,    0,   76,
    0,    0,  256,  278,  164,  -17,  -67,    0,    0,
};
final static int YYTABLESIZE=698;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         49,
    3,    5,  152,   85,   76,  107,  108,   32,   33,   34,
   35,   36,   37,   38,   39,   49,   57,   70,  150,   92,
   95,    1,  151,   40,    6,   41,   55,   93,  105,   91,
   42,  120,   43,   58,   27,   69,   70,  109,   59,    5,
    8,  121,  122,  123,  124,  153,  161,   94,   71,   72,
    9,  162,  143,  144,  145,  146,   44,   73,   93,   19,
   91,  129,  130,  131,   74,   19,   69,   70,   58,   26,
   75,  149,   58,   59,   58,   67,   44,   68,   94,   71,
   72,  142,   25,   69,   70,   60,   27,   60,   73,   95,
   95,   30,   90,  138,   95,   74,   71,   72,   31,  140,
  141,   75,   90,   90,   90,   73,   90,   44,   90,   56,
   90,   90,   74,   60,   59,  160,   59,  163,   75,   59,
   61,   62,   85,   85,   44,   63,   49,   49,  171,  172,
   64,   66,   65,   82,   49,   49,   85,   83,   86,  181,
   87,   88,   89,   49,   90,  188,   98,   67,   49,   91,
   85,   99,   93,   85,   91,   69,   70,  100,  101,   49,
   69,   70,   49,   49,  102,   49,  103,  104,   71,   72,
   58,  132,  134,   71,   72,  139,  127,   73,  126,  128,
  133,  155,   73,  156,   74,  157,  164,  165,  167,   74,
   75,   38,   38,   38,  158,   75,   44,   32,   84,   34,
  159,   44,  174,  178,   38,  173,   38,   38,  175,   38,
  179,  183,  169,   40,   38,   41,   38,  184,  187,  192,
   42,  189,   43,  195,  190,  198,  193,   32,   84,   34,
  196,   10,    1,   53,   11,   12,   13,   14,   15,    7,
   38,   15,  170,   40,   57,   41,   44,   32,   84,   34,
   42,   16,   43,   14,   16,   17,    7,    3,    5,   32,
   84,   34,  182,   40,   27,   41,   18,   45,   78,   22,
   42,   20,   43,  199,  194,   40,   44,   41,   21,   32,
   84,   34,   42,   57,   43,   55,   18,   32,   84,   34,
  148,   40,   40,   40,  197,   40,   44,   41,    0,    0,
    0,    0,   42,   40,   43,   41,   40,   40,   44,   40,
   42,    0,   43,    0,   40,    0,   40,   13,   13,   13,
   13,   13,   13,   13,    0,    0,    0,    0,   44,    0,
    0,    8,   13,    0,   13,    0,   44,    0,    0,   13,
   40,   13,    0,    0,   92,   92,   92,   92,   92,   92,
   92,   92,    0,   92,   92,   92,   92,   92,    0,   92,
    0,   92,   13,   92,   92,   13,   83,   83,   83,   83,
   83,   83,   83,   83,    0,   83,   83,   83,   83,   83,
    0,   85,    0,   85,    0,   85,   85,   83,   83,   83,
   83,   83,   83,   83,   83,    0,   83,   83,   83,   83,
   83,    0,   86,    0,   86,    0,   86,   86,   78,   84,
   78,   78,   78,   78,   78,   78,    0,   78,   78,   84,
   84,   84,    0,   61,    0,   61,    0,   77,   61,   77,
   77,   77,   77,   77,   77,    0,   77,   77,    0,    0,
    0,    0,   77,    0,   77,    0,   77,   77,   74,    0,
   74,   74,   74,   74,   74,   74,    0,   74,   74,    0,
    0,    0,    0,   74,    0,   74,    0,   74,   74,   75,
    0,   75,   75,   75,   75,   75,   75,    0,   75,   75,
    0,    0,    0,    0,   75,    0,   75,    0,   75,   75,
   76,    0,   76,   76,   76,   76,   76,   76,    0,   76,
   76,    0,    0,    0,    0,   76,    0,   76,    0,   76,
   76,  110,    0,  111,  112,  113,  114,  115,  116,    0,
  117,  118,    0,  110,    0,  111,  112,  113,  114,  115,
  116,  166,  117,  118,   20,    0,    0,   11,   12,   13,
   14,   15,    0,  177,    0,    0,   92,   92,   92,   92,
   92,   92,   92,   92,   21,   92,   92,   92,   92,   92,
    0,    0,    0,    0,  110,   92,  111,  112,  113,  114,
  115,  116,    0,  117,  118,  110,    0,  111,  112,  113,
  114,  115,  116,  125,  117,  118,    0,    0,    0,    0,
    0,  110,  136,  111,  112,  113,  114,  115,  116,   20,
  117,  118,   11,   12,   13,   14,   15,  110,  137,  111,
  112,  113,  114,  115,  116,    0,  117,  118,    0,    0,
    0,    0,    0,  110,  147,  111,  112,  113,  114,  115,
  116,    0,  117,  118,    0,    0,    0,    0,    0,  110,
  168,  111,  112,  113,  114,  115,  116,    0,  117,  118,
    0,    0,    0,    0,    0,  110,  176,  111,  112,  113,
  114,  115,  116,    0,  117,  118,    0,    0,    0,    0,
    0,  110,  186,  111,  112,  113,  114,  115,  116,    0,
  117,  118,    0,    0,    0,    0,    0,  110,  191,  111,
  112,  113,  114,  115,  116,    0,  117,  118,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         29,
    0,    0,  256,   45,   42,   73,   74,  256,  257,  258,
  259,  260,  261,  262,  263,   45,  282,  258,  128,   57,
   58,  257,  132,  272,  257,  274,  267,  256,   66,  258,
  279,  284,  281,  299,  271,  264,  265,   75,  304,  273,
  258,  294,  295,  296,   82,  299,  298,  276,  277,  278,
  280,  303,  120,  121,  122,  123,  305,  286,  256,  266,
  258,   99,  100,  101,  293,  302,  264,  265,  299,  258,
  299,  300,  298,  304,  300,  256,  305,  258,  276,  277,
  278,  119,  267,  264,  265,  298,  256,  300,  286,  127,
  128,  303,  284,  111,  132,  293,  277,  278,  271,  117,
  118,  299,  294,  295,  296,  286,  298,  305,  300,  258,
  302,  303,  293,  258,  298,  153,  300,  155,  299,  303,
  258,  258,  164,  165,  305,  258,  156,  157,  166,  167,
  258,  299,  268,  299,  164,  165,  178,  258,  302,  177,
  302,  302,  302,  173,  302,  183,  258,  256,  178,  258,
  192,  282,  256,  195,  258,  264,  265,  282,  282,  189,
  264,  265,  192,  193,  282,  195,  282,  299,  277,  278,
  299,  297,  258,  277,  278,  283,  303,  286,  300,  299,
  297,  275,  286,  267,  293,  267,  156,  157,  299,  293,
  299,  256,  257,  258,  300,  299,  305,  256,  257,  258,
  298,  305,  274,  173,  269,  267,  271,  272,  281,  274,
  270,  299,  271,  272,  279,  274,  281,  269,  272,  189,
  279,  267,  281,  193,  268,  270,  267,  256,  257,  258,
  269,  256,    0,  267,  259,  260,  261,  262,  263,  271,
  305,  302,  271,  272,  300,  274,  305,  256,  257,  258,
  279,  276,  281,  302,  302,  302,    3,  257,  257,  256,
  257,  258,  271,  272,  271,  274,  302,  302,   42,  302,
  279,  302,  281,  198,  271,  272,  305,  274,  302,  256,
  257,  258,  279,  298,  281,   30,    9,  256,  257,  258,
  127,  256,  257,  258,  271,  272,  305,  274,   -1,   -1,
   -1,   -1,  279,  272,  281,  274,  271,  272,  305,  274,
  279,   -1,  281,   -1,  279,   -1,  281,  257,  258,  259,
  260,  261,  262,  263,   -1,   -1,   -1,   -1,  305,   -1,
   -1,  271,  272,   -1,  274,   -1,  305,   -1,   -1,  279,
  305,  281,   -1,   -1,  283,  284,  285,  286,  287,  288,
  289,  290,   -1,  292,  293,  294,  295,  296,   -1,  298,
   -1,  300,  302,  302,  303,  305,  283,  284,  285,  286,
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
  293,   -1,   -1,   -1,   -1,  298,   -1,  300,   -1,  302,
  303,  283,   -1,  285,  286,  287,  288,  289,  290,   -1,
  292,  293,   -1,  283,   -1,  285,  286,  287,  288,  289,
  290,  303,  292,  293,  256,   -1,   -1,  259,  260,  261,
  262,  263,   -1,  303,   -1,   -1,  283,  284,  285,  286,
  287,  288,  289,  290,  276,  292,  293,  294,  295,  296,
   -1,   -1,   -1,   -1,  283,  302,  285,  286,  287,  288,
  289,  290,   -1,  292,  293,  283,   -1,  285,  286,  287,
  288,  289,  290,  302,  292,  293,   -1,   -1,   -1,   -1,
   -1,  283,  300,  285,  286,  287,  288,  289,  290,  256,
  292,  293,  259,  260,  261,  262,  263,  283,  300,  285,
  286,  287,  288,  289,  290,   -1,  292,  293,   -1,   -1,
   -1,   -1,   -1,  283,  300,  285,  286,  287,  288,  289,
  290,   -1,  292,  293,   -1,   -1,   -1,   -1,   -1,  283,
  300,  285,  286,  287,  288,  289,  290,   -1,  292,  293,
   -1,   -1,   -1,   -1,   -1,  283,  300,  285,  286,  287,
  288,  289,  290,   -1,  292,  293,   -1,   -1,   -1,   -1,
   -1,  283,  300,  285,  286,  287,  288,  289,  290,   -1,
  292,  293,   -1,   -1,   -1,   -1,   -1,  283,  300,  285,
  286,  287,  288,  289,  290,   -1,  292,  293,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=305;
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
"COMMA","DOT","PRINT",
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
"function_call : PRINT IDENTIFIER",
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

//#line 309 "Breezy.yacc"

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
//#line 563 "Parser.java"
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
//#line 28 "Breezy.yacc"
{ba.DumpFile(val_peek(0).sval);}
break;
case 2:
//#line 31 "Breezy.yacc"
{yyval.sval= val_peek(0).sval;}
break;
case 3:
//#line 32 "Breezy.yacc"
{yyval.sval="";}
break;
case 4:
//#line 33 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 5:
//#line 34 "Breezy.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 6:
//#line 44 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6),val_peek(4).sval,val_peek(2).sval,val_peek(0),val_peek(8).line,Scope.GLOBAL.getName()); }
break;
case 7:
//#line 49 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 8:
//#line 50 "Breezy.yacc"
{yyval.sval = "";}
break;
case 9:
//#line 55 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 10:
//#line 56 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 11:
//#line 57 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + "";}
break;
case 12:
//#line 58 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 13:
//#line 59 "Breezy.yacc"
{yyval.sval = "";}
break;
case 14:
//#line 63 "Breezy.yacc"
{yyval.sval = "String " + val_peek(0).sval + "=\"\""; ba.typeTrack.addID(val_peek(0).sval,"string",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 15:
//#line 64 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval + "=false"; ba.typeTrack.addID(val_peek(0).sval,"boolean",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 16:
//#line 65 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval + "=0"; ba.typeTrack.addID(val_peek(0).sval,"number",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 17:
//#line 66 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval); ba.typeTrack.addID(val_peek(0).sval,"ArrayList",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 18:
//#line 67 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval); ba.typeTrack.addID(val_peek(0).sval,"HashMap",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 20:
//#line 73 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertStringType(val_peek(0));
                                                                         ba.typeTrack.addID(val_peek(2).sval,"string",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 21:
//#line 76 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertNumberType(val_peek(0));
                                                                         ba.typeTrack.addID(val_peek(2).sval,"number",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 22:
//#line 79 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                          ba.typeTrack.addID(val_peek(2).sval,"boolean",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 23:
//#line 82 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 24:
//#line 84 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 25:
//#line 88 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 26:
//#line 89 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 27:
//#line 90 "Breezy.yacc"
{yyval.sval = "";}
break;
case 28:
//#line 94 "Breezy.yacc"
{yyval.sval = "";}
break;
case 29:
//#line 95 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 30:
//#line 96 "Breezy.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 31:
//#line 97 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 32:
//#line 98 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 33:
//#line 99 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 34:
//#line 100 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 35:
//#line 101 "Breezy.yacc"
{val_peek(3).obj = ba.typeTrack.getType(val_peek(3),Scope.LOCAL.getName());
                                                                        ba.typeTrack.assertSameType(val_peek(3),val_peek(1),val_peek(2));
                                                                        yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 36:
//#line 109 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(7));
                                                    yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 37:
//#line 118 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(6));
                                                yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 38:
//#line 120 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 39:
//#line 127 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 40:
//#line 128 "Breezy.yacc"
{yyval.sval = "";}
break;
case 41:
//#line 135 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(5));
                                        yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 42:
//#line 142 "Breezy.yacc"
{ba.typeTrack.assertArrayOrHashType(val_peek(6));
                                                    ba.typeTrack.removeLocalID(val_peek(8));
                                                    yyval.sval = "for(TypedParserVal " +val_peek(8).sval+ " : " + val_peek(6).sval + "){" + val_peek(3).sval + "\n}\n";}
break;
case 43:
//#line 145 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 44:
//#line 148 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(0).sval,"TypedParserVal",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 45:
//#line 151 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 46:
//#line 152 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 47:
//#line 155 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                            yyval.obj = ba.typeTrack.getType(val_peek(3), Scope.GLOBAL.getName());
                                                            yyval.line = val_peek(3).line;}
break;
case 48:
//#line 158 "Breezy.yacc"
{yyval.sval = ba.createPrintCommand(val_peek(0).sval);}
break;
case 49:
//#line 164 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, Scope.LOCAL.getName(), val_peek(1).sval);
                                                                        yyval.obj = "void";}
break;
case 50:
//#line 166 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, Scope.LOCAL.getName(), null);
                                                                        yyval.obj = "void";}
break;
case 51:
//#line 171 "Breezy.yacc"
{yyval.sval = "";}
break;
case 52:
//#line 172 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 53:
//#line 175 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(0).sval,val_peek(1).obj.toString(),val_peek(0).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 54:
//#line 177 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(2).sval,val_peek(3).obj.toString(),val_peek(2).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 55:
//#line 179 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 56:
//#line 182 "Breezy.yacc"
{yyval.sval = "";}
break;
case 57:
//#line 183 "Breezy.yacc"
{yyval.sval = "";}
break;
case 58:
//#line 184 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 59:
//#line 187 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 60:
//#line 188 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 61:
//#line 189 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 62:
//#line 192 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")" + val_peek(6).sval;}
break;
case 63:
//#line 193 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 64:
//#line 194 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 65:
//#line 198 "Breezy.yacc"
{yyval.sval = "boolean "; yyval.obj = "boolean";}
break;
case 66:
//#line 199 "Breezy.yacc"
{yyval.sval = "String "; yyval.obj = "string";}
break;
case 67:
//#line 200 "Breezy.yacc"
{yyval.sval = "double "; yyval.obj = "number";}
break;
case 68:
//#line 201 "Breezy.yacc"
{yyval.sval = "ArrayList "; yyval.obj = "ArrayList";}
break;
case 69:
//#line 202 "Breezy.yacc"
{yyval.sval = "HashMap "; yyval.obj = "HashMap";}
break;
case 70:
//#line 203 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 71:
//#line 206 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 72:
//#line 207 "Breezy.yacc"
{yyval.sval = "void"; yyval.obj = "void";}
break;
case 73:
//#line 208 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 74:
//#line 211 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                        yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                        yyval.obj = val_peek(2).obj;
                                                        yyval.line = val_peek(2).line;}
break;
case 75:
//#line 215 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 76:
//#line 219 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 77:
//#line 223 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 78:
//#line 225 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 79:
//#line 228 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 80:
//#line 232 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 81:
//#line 236 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 82:
//#line 240 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 83:
//#line 244 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 84:
//#line 247 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 85:
//#line 250 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.sval = " !" + val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 86:
//#line 254 "Breezy.yacc"
{  ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.sval = " -"+ val_peek(0).sval;
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 87:
//#line 258 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 88:
//#line 261 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 89:
//#line 264 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 90:
//#line 267 "Breezy.yacc"
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
case 91:
//#line 278 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 92:
//#line 281 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0), Scope.LOCAL.getName());
                                                    yyval.line = val_peek(0).line;}
break;
case 93:
//#line 284 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 94:
//#line 287 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 95:
//#line 290 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 96:
//#line 293 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 97:
//#line 296 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 98:
//#line 300 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 99:
//#line 301 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 100:
//#line 302 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 101:
//#line 303 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 102:
//#line 304 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 103:
//#line 305 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1194 "Parser.java"
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
