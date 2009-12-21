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
public final static short PRINT=305;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    1,    2,    5,    5,    6,    6,
    6,    6,    6,    8,    8,    8,    8,    8,    8,    9,
    9,    9,    9,    9,    7,    7,    7,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   17,   20,   20,   21,
   21,   18,   19,   19,   22,   16,   16,   14,   14,   14,
   15,   15,   15,    4,    4,   23,   23,   23,   11,   11,
   11,   25,   25,   25,   12,   12,   12,   24,   24,   24,
   24,   24,   24,    3,    3,    3,   10,   10,   10,   10,
   10,   26,   26,   26,   26,   26,   26,   27,   27,   27,
   27,   28,   28,   28,   28,   28,   28,   28,   28,   28,
   29,   29,   29,   29,   29,   29,
};
final static short yylen[] = {                            2,
    1,    1,    1,    2,    2,   11,    2,    0,    3,    3,
    2,    1,    0,    2,    2,    2,    2,    2,    0,    4,
    4,    4,    6,    6,    1,    2,    0,    1,    2,    2,
    2,    1,    1,    1,    4,    4,   10,    9,    0,    5,
    0,    8,   12,    1,    1,    2,    2,    4,    2,    2,
    6,    5,    4,    1,    1,    2,    4,    1,    1,    0,
    1,    1,    3,    1,    7,    5,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    3,    3,    1,
    1,    3,    3,    3,    3,    1,    1,    2,    2,    1,
    1,    3,    3,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    4,    0,    0,   73,
   68,   69,   70,   71,   72,   75,    0,   74,    0,    0,
   54,    0,   55,    0,    0,    0,   12,    0,    0,    0,
    0,   44,   11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   25,    0,    0,
    0,   32,   33,   34,   57,    6,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   81,    0,   98,
   99,   94,   97,    0,    0,    0,    0,   96,   47,    0,
   86,   90,    0,    0,   50,   28,   26,    9,   10,   29,
   30,   31,    0,    0,    0,    0,    0,   59,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  105,    0,    0,  103,  101,  104,  102,
    0,    0,    0,    0,    0,    0,    0,    0,   36,   35,
   53,   48,    0,    0,    0,    0,    0,    0,    0,   45,
    0,    0,   92,    0,  106,    0,    0,    0,   82,   85,
   83,   84,    0,    0,   52,    0,    0,   67,    0,    0,
    0,    0,    0,   51,   23,    0,   24,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   42,   66,    0,    0,    0,    0,    0,    0,    0,    0,
   37,   65,    0,    0,    0,   43,    0,    0,    0,    0,
    0,   40,    0,    0,   38,
};
final static short yydgoto[] = {                          2,
    3,    4,   17,   22,   28,   29,   45,   46,   47,  110,
  100,  160,   48,   78,   50,   51,   52,   53,   54,  186,
  191,  141,   23,   24,  101,   80,   81,   82,  123,
};
final static short yysindex[] = {                      -236,
 -221,    0, -204,    0, -196, -221,    0, -243,  283,    0,
    0,    0,    0,    0,    0,    0, -203,    0,  291,    0,
    0, -202,    0, -194, -177, -214,    0, -176, -187,  301,
 -153,    0,    0, -250, -152, -151, -150, -149, -148, -157,
 -184,  -97, -182, -138,  -63, -175, -165,    0, -162, -154,
 -144,    0,    0,    0,    0,    0,  -97, -133, -102, -130,
 -137, -131, -129, -117, -111, -135,  -79,    0, -249,    0,
    0,    0,    0,  -79,  -79,  -79,  446,    0,    0, -253,
    0,    0,  -79, -294,    0,    0,    0,    0,    0,    0,
    0,    0,  303, -124, -125,    0, -116,    0,  446, -113,
 -121, -109,  -79,  -79,  -79, -105,  -96,  -70,  334,  446,
    0,    0,  350,    0,  -79, -134,    0,    0,    0,    0,
  -79,  -79,  -79,  -79,  -79,  -79,  -79,  366,    0,    0,
    0,    0,  -43, -174,  446,  446,  446, -102, -248,    0,
  -75,  -62,    0, -253,    0, -253, -253,  446,    0,    0,
    0,    0,  -61, -121,    0,  -90,  -94,    0,  -79, -210,
  -79,  -63,  -63,    0,    0,  233,    0,  -87,  382, -158,
  -33,  -79,  -79,  -50,  -55,  -54,  398,  245,  -63,  -42,
    0,    0,  -79,  -27,  -67,  -36,  414,  -35,  -79,  -31,
    0,    0,  -28,  430,  -63,    0,  -18,    8,  -63,  -16,
   12,    0,  -15,  -42,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,  251,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -239,
    0,    0,    0,    0,   44,  -10,    0,    0, -262,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -11,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -39,    0,
  -26,  -25,  -21,  -17,  -14,    0,    0,    0,  285,    0,
    0,    0,    0,    0,    0,    0,  -12,    0,    0,   41,
    0,    0,    0,   67,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  151,   88,    0, -220,    0,
 -283,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  109,  130,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   -8,   -7,   -3,  -24,    0,    0,
    0,    0,    0,  170,    0,  191,  212,  313,    0,    0,
    0,    0,    0, -265,    0,    0,    0,    0,    0,    0,
    0,   -4,   -4,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -4, -245,
    0,    0,    0,    0,    0,   40,    0,    0,    0,    0,
    0,    0,    0,    0,   -4,    0,    0,    0,   -4,    0,
    0,    0,    0, -245,    0,
};
final static short yygindex[] = {                         0,
    0,  260,    0,    0,    0,    0, -118,    0,    0,  -37,
 -120,    0,  -41,  -29,  -19,    0,    0,    0,    0,   69,
    0,    0,  241,  266,  159,   17,  -68,    0,    0,
};
final static int YYTABLESIZE=739;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         49,
    3,    5,   58,   87,   77,  111,  112,  158,   27,   60,
   39,   39,   39,  156,   61,   49,   61,  157,   73,   93,
    1,   99,   79,   39,   85,   39,   39,   58,   39,  109,
  124,   57,   63,   39,   63,   39,    9,   94,  113,   19,
  125,  126,  127,  170,  171,  128,   58,   58,   59,   59,
  159,    5,    6,   60,   60,  149,  150,  151,  152,   39,
  184,    8,   19,   26,   25,  135,  136,  137,   32,   33,
   34,   35,   36,   37,   38,   39,  198,   62,   27,   62,
  201,   96,   62,   97,   40,  148,   41,  167,   30,   70,
   71,   42,  168,   43,   31,   99,   99,   32,   86,   34,
   99,   98,   72,   73,   56,   61,   62,   63,   64,   65,
   66,   74,  175,   40,   67,   41,   83,   44,   75,   84,
   42,  166,   43,  169,   76,  155,   88,  102,   87,   87,
   44,  144,   49,   49,  177,  178,   89,  146,  147,   90,
   49,   49,   87,   95,  103,  187,   44,   91,  145,   49,
  104,  194,  105,   96,   49,   97,   87,   92,   68,   87,
   69,   70,   71,  108,  106,   49,   70,   71,   49,   49,
  107,   49,  131,   98,   72,   73,   68,  130,   97,   72,
   73,  133,   59,   74,   70,   71,  132,  140,   74,  134,
   75,  138,   32,   86,   34,   75,   76,   72,   73,  161,
  139,   76,   44,  165,  162,  163,   74,   44,   40,  164,
   41,  173,   96,   75,   97,   42,  179,   43,  180,   76,
   70,   71,   32,   86,   34,   44,  181,  185,   32,   86,
   34,  189,  190,   72,   73,  195,  193,  176,   40,  196,
   41,   44,   74,  188,   40,   42,   41,   43,  199,   75,
    1,   42,  202,   43,  204,   76,   56,    3,    5,    7,
   60,   44,    7,   32,   86,   34,   27,   32,   86,   34,
   55,   44,  205,   60,   18,   15,   14,   44,  200,   40,
   16,   41,  203,   40,   17,   41,   42,   18,   43,   46,
   42,  154,   43,   22,   20,   41,   41,   41,   21,    0,
   13,   13,   13,   13,   13,   13,   13,    0,    0,    0,
   41,   41,   44,   41,    8,   13,   44,   13,   41,    0,
   41,    0,   13,   80,   13,   80,   80,   80,   80,   80,
   80,    0,   80,   80,    0,    0,    0,    0,   80,    0,
   80,    0,   80,   80,   41,   13,    0,    0,   13,   49,
   49,   49,   49,   49,   49,   49,   49,    0,   49,   49,
   49,   49,   49,    0,   49,    0,   49,    0,   49,   49,
   95,   95,   95,   95,   95,   95,   95,   95,    0,   95,
   95,   95,   95,   95,    0,   95,    0,   95,    0,   95,
   95,   86,   86,   86,   86,   86,   86,   86,   86,    0,
   86,   86,   86,   86,   86,    0,   88,    0,   88,    0,
   88,   88,   86,   86,   86,   86,   86,   86,   86,   86,
    0,   86,   86,   86,   86,   86,    0,   89,    0,   89,
    0,   89,   89,   81,   87,   81,   81,   81,   81,   81,
   81,    0,   81,   81,   87,   87,   87,    0,   64,    0,
   64,    0,   77,   64,   77,   77,   77,   77,   77,   77,
    0,   77,   77,    0,    0,    0,    0,   77,    0,   77,
    0,   77,   77,   78,    0,   78,   78,   78,   78,   78,
   78,    0,   78,   78,    0,    0,    0,    0,   78,    0,
   78,    0,   78,   78,   79,    0,   79,   79,   79,   79,
   79,   79,    0,   79,   79,    0,    0,    0,    0,   79,
    0,   79,    0,   79,   79,  114,    0,  115,  116,  117,
  118,  119,  120,    0,  121,  122,    0,  114,    0,  115,
  116,  117,  118,  119,  120,  172,  121,  122,   10,    0,
    0,   11,   12,   13,   14,   15,   20,  183,    0,   11,
   12,   13,   14,   15,    0,    0,   20,    0,   16,   11,
   12,   13,   14,   15,    0,    0,   21,   95,   95,   95,
   95,   95,   95,   95,   95,    0,   95,   95,   95,   95,
   95,    0,    0,    0,    0,  114,   95,  115,  116,  117,
  118,  119,  120,    0,  121,  122,   93,    0,    0,    0,
    0,    0,    0,    0,  129,    0,   93,   93,   93,    0,
   93,    0,   93,    0,   93,   93,  114,    0,  115,  116,
  117,  118,  119,  120,    0,  121,  122,    0,    0,    0,
    0,    0,  114,  142,  115,  116,  117,  118,  119,  120,
    0,  121,  122,    0,    0,    0,    0,    0,  114,  143,
  115,  116,  117,  118,  119,  120,    0,  121,  122,    0,
    0,    0,    0,    0,  114,  153,  115,  116,  117,  118,
  119,  120,    0,  121,  122,    0,    0,    0,    0,    0,
  114,  174,  115,  116,  117,  118,  119,  120,    0,  121,
  122,    0,    0,    0,    0,    0,  114,  182,  115,  116,
  117,  118,  119,  120,    0,  121,  122,    0,    0,    0,
    0,    0,  114,  192,  115,  116,  117,  118,  119,  120,
    0,  121,  122,    0,    0,    0,    0,    0,  114,  197,
  115,  116,  117,  118,  119,  120,    0,  121,  122,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         29,
    0,    0,  297,   45,   42,   74,   75,  256,  271,  304,
  256,  257,  258,  134,  298,   45,  300,  138,  258,   57,
  257,   59,   42,  269,   44,  271,  272,  267,  274,   67,
  284,  282,  298,  279,  300,  281,  280,   57,   76,  302,
  294,  295,  296,  162,  163,   83,  297,  297,  299,  299,
  299,  273,  257,  304,  304,  124,  125,  126,  127,  305,
  179,  258,  266,  258,  267,  103,  104,  105,  256,  257,
  258,  259,  260,  261,  262,  263,  195,  298,  256,  300,
  199,  256,  303,  258,  272,  123,  274,  298,  303,  264,
  265,  279,  303,  281,  271,  133,  134,  256,  257,  258,
  138,  276,  277,  278,  258,  258,  258,  258,  258,  258,
  268,  286,  271,  272,  299,  274,  299,  305,  293,  258,
  279,  159,  281,  161,  299,  300,  302,  258,  170,  171,
  305,  115,  162,  163,  172,  173,  302,  121,  122,  302,
  170,  171,  184,  277,  282,  183,  305,  302,  283,  179,
  282,  189,  282,  256,  184,  258,  198,  302,  256,  201,
  258,  264,  265,  299,  282,  195,  264,  265,  198,  199,
  282,  201,  298,  276,  277,  278,  256,  302,  258,  277,
  278,  303,  299,  286,  264,  265,  300,  258,  286,  299,
  293,  297,  256,  257,  258,  293,  299,  277,  278,  275,
  297,  299,  305,  298,  267,  267,  286,  305,  272,  300,
  274,  299,  256,  293,  258,  279,  267,  281,  274,  299,
  264,  265,  256,  257,  258,  305,  281,  270,  256,  257,
  258,  299,  269,  277,  278,  267,  272,  271,  272,  268,
  274,  305,  286,  271,  272,  279,  274,  281,  267,  293,
    0,  279,  269,  281,  270,  299,  267,  257,  257,  271,
  300,  305,    3,  256,  257,  258,  271,  256,  257,  258,
   30,  305,  204,  298,    9,  302,  302,  305,  271,  272,
  302,  274,  271,  272,  302,  274,  279,  302,  281,  302,
  279,  133,  281,  302,  302,  256,  257,  258,  302,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,   -1,   -1,
  271,  272,  305,  274,  271,  272,  305,  274,  279,   -1,
  281,   -1,  279,  283,  281,  285,  286,  287,  288,  289,
  290,   -1,  292,  293,   -1,   -1,   -1,   -1,  298,   -1,
  300,   -1,  302,  303,  305,  302,   -1,   -1,  305,  283,
  284,  285,  286,  287,  288,  289,  290,   -1,  292,  293,
  294,  295,  296,   -1,  298,   -1,  300,   -1,  302,  303,
  283,  284,  285,  286,  287,  288,  289,  290,   -1,  292,
  293,  294,  295,  296,   -1,  298,   -1,  300,   -1,  302,
  303,  283,  284,  285,  286,  287,  288,  289,  290,   -1,
  292,  293,  294,  295,  296,   -1,  298,   -1,  300,   -1,
  302,  303,  283,  284,  285,  286,  287,  288,  289,  290,
   -1,  292,  293,  294,  295,  296,   -1,  298,   -1,  300,
   -1,  302,  303,  283,  284,  285,  286,  287,  288,  289,
  290,   -1,  292,  293,  294,  295,  296,   -1,  298,   -1,
  300,   -1,  283,  303,  285,  286,  287,  288,  289,  290,
   -1,  292,  293,   -1,   -1,   -1,   -1,  298,   -1,  300,
   -1,  302,  303,  283,   -1,  285,  286,  287,  288,  289,
  290,   -1,  292,  293,   -1,   -1,   -1,   -1,  298,   -1,
  300,   -1,  302,  303,  283,   -1,  285,  286,  287,  288,
  289,  290,   -1,  292,  293,   -1,   -1,   -1,   -1,  298,
   -1,  300,   -1,  302,  303,  283,   -1,  285,  286,  287,
  288,  289,  290,   -1,  292,  293,   -1,  283,   -1,  285,
  286,  287,  288,  289,  290,  303,  292,  293,  256,   -1,
   -1,  259,  260,  261,  262,  263,  256,  303,   -1,  259,
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
"statement : IDENTIFIER EQUALS complex_type_method_invocation SEMICOLON",
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
"function_call : PRINT complex_type_method_invocation",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN",
"complex_type_method_invocation : IDENTIFIER LEFT_SQUARE_PAREN NUMERIC RIGHT_SQUARE_PAREN",
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

//#line 313 "Breezy.yacc"

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
//#line 577 "Parser.java"
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
                                                                                    yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 36:
//#line 103 "Breezy.yacc"
{val_peek(3).obj = ba.typeTrack.getType(val_peek(3),Scope.LOCAL.getName());
                                                                        ba.typeTrack.assertSameType(val_peek(3),val_peek(1),val_peek(2));
                                                                        yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 37:
//#line 111 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(7));
                                                    yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 38:
//#line 120 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(6));
                                                yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 39:
//#line 122 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 40:
//#line 129 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 41:
//#line 130 "Breezy.yacc"
{yyval.sval = "";}
break;
case 42:
//#line 137 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(5));
                                        yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 43:
//#line 144 "Breezy.yacc"
{ba.typeTrack.assertArrayOrHashType(val_peek(6));
                                                    ba.typeTrack.removeLocalID(val_peek(8));
                                                    yyval.sval = "for(TypedParserVal " +val_peek(8).sval+ " : " + val_peek(6).sval + "){" + val_peek(3).sval + "\n}\n";}
break;
case 44:
//#line 147 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 45:
//#line 150 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(0).sval,"TypedParserVal",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 46:
//#line 153 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 47:
//#line 154 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 48:
//#line 157 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                            yyval.obj = ba.typeTrack.getType(val_peek(3), Scope.GLOBAL.getName());
                                                            yyval.line = val_peek(3).line;}
break;
case 49:
//#line 160 "Breezy.yacc"
{yyval.sval = ba.createPrintCommand(val_peek(0).sval);}
break;
case 50:
//#line 161 "Breezy.yacc"
{yyval.sval = "System.out.println(" + val_peek(0).sval.substring(0, val_peek(0).sval.length()-3) + ");\n";}
break;
case 51:
//#line 167 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, Scope.LOCAL.getName(), val_peek(1).sval);
                                                                        yyval.obj = "void";}
break;
case 52:
//#line 169 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, Scope.LOCAL.getName(), null);
                                                                        yyval.obj = "void";}
break;
case 53:
//#line 171 "Breezy.yacc"
{yyval.sval = ba.createIndexAccess(val_peek(3).sval, val_peek(1).ival);}
break;
case 54:
//#line 175 "Breezy.yacc"
{yyval.sval = "";}
break;
case 55:
//#line 176 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 56:
//#line 179 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(0).sval,val_peek(1).obj.toString(),val_peek(0).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 57:
//#line 181 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(2).sval,val_peek(3).obj.toString(),val_peek(2).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 58:
//#line 183 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 59:
//#line 186 "Breezy.yacc"
{yyval.sval = "";}
break;
case 60:
//#line 187 "Breezy.yacc"
{yyval.sval = "";}
break;
case 61:
//#line 188 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 62:
//#line 191 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 63:
//#line 192 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 64:
//#line 193 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 65:
//#line 196 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")" + val_peek(6).sval;}
break;
case 66:
//#line 197 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 67:
//#line 198 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 68:
//#line 202 "Breezy.yacc"
{yyval.sval = "boolean "; yyval.obj = "boolean";}
break;
case 69:
//#line 203 "Breezy.yacc"
{yyval.sval = "String "; yyval.obj = "string";}
break;
case 70:
//#line 204 "Breezy.yacc"
{yyval.sval = "double "; yyval.obj = "number";}
break;
case 71:
//#line 205 "Breezy.yacc"
{yyval.sval = "ArrayList "; yyval.obj = "ArrayList";}
break;
case 72:
//#line 206 "Breezy.yacc"
{yyval.sval = "HashMap "; yyval.obj = "HashMap";}
break;
case 73:
//#line 207 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 74:
//#line 210 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 75:
//#line 211 "Breezy.yacc"
{yyval.sval = "void"; yyval.obj = "void";}
break;
case 76:
//#line 212 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 77:
//#line 215 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                        yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                        yyval.obj = val_peek(2).obj;
                                                        yyval.line = val_peek(2).line;}
break;
case 78:
//#line 219 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 79:
//#line 223 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 80:
//#line 227 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 81:
//#line 229 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 82:
//#line 232 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 83:
//#line 236 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 84:
//#line 240 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 85:
//#line 244 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 86:
//#line 248 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 87:
//#line 251 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 88:
//#line 254 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.sval = " !" + val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 89:
//#line 258 "Breezy.yacc"
{  ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.sval = " -"+ val_peek(0).sval;
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 90:
//#line 262 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 91:
//#line 265 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 92:
//#line 268 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 93:
//#line 271 "Breezy.yacc"
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
case 94:
//#line 282 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 95:
//#line 285 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0), Scope.LOCAL.getName());
                                                    yyval.line = val_peek(0).line;}
break;
case 96:
//#line 288 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 97:
//#line 291 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 98:
//#line 294 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 99:
//#line 297 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 100:
//#line 300 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 101:
//#line 304 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 102:
//#line 305 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 103:
//#line 306 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 104:
//#line 307 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 105:
//#line 308 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 106:
//#line 309 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1221 "Parser.java"
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
