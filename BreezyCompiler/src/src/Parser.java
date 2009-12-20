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
    6,    6,    6,    8,    8,    8,    8,    8,    9,    9,
    9,    9,    9,    7,    7,    7,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   17,   20,   20,   21,   21,
   18,   19,   22,   16,   16,   14,   15,   15,    4,    4,
   23,   23,   11,   11,   11,   25,   25,   12,   12,   24,
   24,   24,   24,   24,    3,    3,   10,   10,   10,   10,
   26,   26,   26,   26,   26,   27,   27,   27,   28,   28,
   28,   28,   28,   28,   28,   28,   29,   29,   29,   29,
   29,   29,
};
final static short yylen[] = {                            2,
    1,    1,    1,    2,    2,   11,    2,    0,    3,    3,
    2,    1,    0,    2,    2,    2,    2,    2,    4,    4,
    4,    6,    6,    1,    2,    0,    1,    2,    2,    2,
    1,    1,    1,    4,    1,   10,    9,    0,    5,    0,
    8,   12,    1,    2,    2,    4,    6,    5,    1,    1,
    2,    4,    1,    0,    1,    1,    3,    7,    5,    1,
    1,    1,    1,    1,    1,    1,    3,    3,    3,    1,
    3,    3,    3,    3,    1,    2,    2,    1,    3,    3,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    4,    0,    0,   60,
   61,   62,   63,   64,   66,    0,   65,    0,   49,    0,
   50,    0,    0,    0,   12,    0,    0,    0,    0,   35,
   11,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   24,    0,    0,    0,   31,   32,
   33,   52,    6,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   85,   86,   81,   84,    0,    0,
    0,    0,   83,   45,    0,   75,   78,    0,   27,   25,
    9,   10,   28,   29,   30,    0,    0,   53,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   89,   87,   90,   88,   91,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   34,   46,
    0,    0,    0,    0,    0,    0,    0,   43,    0,    0,
   79,    0,   92,    0,    0,    0,   71,   74,   72,   73,
    0,    0,   48,    0,    0,    0,    0,    0,    0,    0,
   47,   22,    0,   23,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   41,   59,    0,
    0,    0,    0,    0,    0,    0,    0,   36,   58,    0,
    0,    0,   42,    0,    0,    0,    0,    0,   39,    0,
    0,   37,
};
final static short yydgoto[] = {                          2,
    3,    4,   16,   20,   26,   27,   42,   43,   44,  100,
   90,  147,   45,   73,   47,   48,   49,   50,   51,  173,
  178,  129,   21,   22,   91,   75,   76,   77,  113,
};
final static short yysindex[] = {                      -253,
 -260,    0, -231,    0, -196, -260,    0, -215, -229,    0,
    0,    0,    0,    0,    0, -239,    0, -166,    0, -188,
    0, -175, -168, -199,    0, -165,  -90, -189, -153,    0,
    0, -275, -149, -146, -144, -143, -142, -151, -173, -157,
 -172, -235, -174, -164,    0, -162, -159, -155,    0,    0,
    0,    0,    0, -113, -209, -129, -152, -138, -136, -133,
 -132, -139, -113, -264,    0,    0,    0,    0, -113, -113,
 -113,  338,    0,    0, -278,    0,    0, -113,    0,    0,
    0,    0,    0,    0,    0, -137,  227,    0,  338, -163,
 -147, -125, -113, -113, -113, -140, -134,  -83,  241,  338,
    0,    0,  253,    0,    0,    0,    0,    0, -113, -111,
 -113, -113, -113, -113, -113, -113, -113,  265,    0,    0,
 -113, -201,  338,  338,  338, -209, -121,    0,  -96,  -86,
    0, -278,    0, -278, -278,  338,    0,    0,    0,    0,
  -82, -147,    0, -117, -110, -113, -237, -113, -235, -235,
    0,    0,  187,    0, -112,  277,  -60,  -34, -113, -113,
  -77,  -79,  -81,  289,  199, -235,  -71,    0,    0, -113,
  -30,  -98,  -67,  301,  -69, -113,  -61,    0,    0,  -59,
  313, -235,    0,  -54,   -4, -235,  -65,    7,    0,  -52,
  -71,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,  216,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   32,  -47,    0,    0,  -46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -42,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -70,    0,  -68,  -66,  -56,  -45,
  -41,    0,    0,  211,    0,    0,    0,    0,    0,    0,
    0,  -40,    0,    0,   99,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,    0,    0, -218,    0,
 -286,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   55,   77,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -36,  -33,  -31,  -55,    0,    0,    0,    0,
    0,  121,    0,  143,  165,  316,    0,    0,    0,    0,
    0, -250,    0,    0,    0,    0,    0,    0,  -46,  -46,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -46,  -64,    0,    0,    0,
    0,    0,   26,    0,    0,    0,    0,    0,    0,    0,
    0,  -46,    0,    0,    0,  -46,    0,    0,    0,    0,
  -64,    0,
};
final static short yygindex[] = {                         0,
    0,  228,    0,    0,    0,    0, -141,    0,    0,  -35,
  -84,    0,  -39,  -27,  192,    0,    0,    0,    0,   42,
    0,    0,  207,  230,  127,   -9,  -63,    0,    0,
};
final static int YYTABLESIZE=631;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
    3,    5,   80,    1,   72,  101,  102,  157,  158,  114,
   54,   55,    5,   55,   46,  115,  116,  117,   87,   89,
   30,   79,   32,   55,  171,    6,   18,   99,   56,   10,
   11,   12,   13,   14,   55,  103,   38,  144,   39,   56,
  185,  145,  118,   40,  188,   41,   15,   57,   86,   57,
  137,  138,  139,  140,   65,   66,   86,  123,  124,  125,
  154,    8,   65,   66,    9,  155,   88,   67,   68,   10,
   11,   12,   13,   14,   88,   67,   68,  136,   23,   56,
   69,   56,   24,   70,   56,   89,   89,   25,   69,   71,
   89,   70,   10,   11,   12,   13,   14,   71,  143,  132,
   64,  134,  135,   28,   53,   29,   65,   66,   57,   19,
  153,   58,  156,   59,   60,   61,   62,   80,   80,   67,
   68,   46,   46,  164,  165,   63,   78,   81,   92,   46,
   46,   80,   69,   93,  174,   70,  120,   82,   46,   83,
  181,   71,   84,   46,   86,   80,   85,   94,   80,   95,
   65,   66,   96,   97,   46,  121,  126,   46,   46,   98,
   46,   55,  127,   67,   68,   30,   31,   32,   33,   34,
   35,   36,   37,  122,  128,  133,   69,  146,  148,   70,
  149,   38,  151,   39,  150,   71,  160,  152,   40,  166,
   41,   38,   38,   38,  167,   30,   79,   32,  172,  168,
  176,  177,  180,  189,   38,  182,   38,   38,  183,   38,
  162,   38,  186,   39,   38,    1,   38,  191,   40,   51,
   41,   30,   79,   32,   26,   30,   79,   32,    7,   54,
    7,   74,  192,   15,   52,   14,  163,   38,   17,   39,
  175,   38,   54,   39,   40,   16,   41,  142,   40,    0,
   41,   30,   79,   32,    0,    0,   17,    3,    5,    0,
   18,   44,   30,   79,   32,   21,  187,   38,   19,   39,
   20,    0,    0,    0,   40,    0,   41,  190,   38,    0,
   39,   40,   40,   40,    0,   40,    0,   41,   13,   13,
   13,   13,   13,   13,   13,    0,   40,   40,    0,   40,
    0,    0,    8,   13,   40,   13,   40,    0,    0,    0,
   13,    0,   13,    0,   82,   82,   82,   82,    0,   82,
   82,   82,   82,    0,   82,   82,   82,   82,   82,    0,
   82,    0,   82,    0,   82,   82,   75,   75,   75,   75,
    0,   75,   75,   75,   75,    0,   75,   75,   75,   75,
   75,    0,   76,    0,   76,    0,   76,   76,   75,   75,
   75,   75,    0,   75,   75,   75,   75,    0,   75,   75,
   75,   75,   75,    0,   77,    0,   77,    0,   77,   77,
   70,   70,   70,   70,    0,   70,    0,   70,   70,    0,
   70,   70,    0,    0,    0,    0,   70,    0,   70,    0,
   70,   70,   67,   67,   67,   67,    0,   67,    0,   67,
   67,    0,   67,   67,    0,    0,    0,    0,   67,    0,
   67,    0,   67,   67,   68,   68,   68,   68,    0,   68,
    0,   68,   68,    0,   68,   68,    0,    0,    0,    0,
   68,    0,   68,    0,   68,   68,   69,   69,   69,   69,
    0,   69,    0,   69,   69,    0,   69,   69,    0,    0,
    0,    0,   69,    0,   69,    0,   69,   69,  104,  105,
  106,  107,    0,  108,    0,  109,  110,    0,  111,  112,
  104,  105,  106,  107,    0,  108,    0,  109,  110,  159,
  111,  112,   82,   82,   82,   82,    0,   82,   82,   82,
   82,  170,   82,   82,   82,   82,   82,    0,  104,  105,
  106,  107,   82,  108,    0,  109,  110,    0,  111,  112,
    0,    0,  104,  105,  106,  107,    0,  108,  119,  109,
  110,    0,  111,  112,  104,  105,  106,  107,    0,  108,
  130,  109,  110,    0,  111,  112,  104,  105,  106,  107,
    0,  108,  131,  109,  110,    0,  111,  112,  104,  105,
  106,  107,    0,  108,  141,  109,  110,    0,  111,  112,
  104,  105,  106,  107,    0,  108,  161,  109,  110,    0,
  111,  112,  104,  105,  106,  107,    0,  108,  169,  109,
  110,    0,  111,  112,  104,  105,  106,  107,    0,  108,
  179,  109,  110,   80,  111,  112,    0,    0,    0,   80,
   80,   80,  184,   80,    0,   80,    0,   80,   80,  104,
  105,  106,  107,    0,  108,    0,  109,  110,    0,  111,
  112,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         27,
    0,    0,   42,  257,   40,   69,   70,  149,  150,  288,
  286,  298,  273,  300,   42,  294,  295,  296,   54,   55,
  256,  257,  258,  299,  166,  257,  266,   63,  304,  259,
  260,  261,  262,  263,  299,   71,  272,  122,  274,  304,
  182,  126,   78,  279,  186,  281,  276,  298,  258,  300,
  114,  115,  116,  117,  264,  265,  258,   93,   94,   95,
  298,  258,  264,  265,  280,  303,  276,  277,  278,  259,
  260,  261,  262,  263,  276,  277,  278,  113,  267,  298,
  290,  300,  258,  293,  303,  121,  122,  256,  290,  299,
  126,  293,  259,  260,  261,  262,  263,  299,  300,  109,
  258,  111,  112,  303,  258,  271,  264,  265,  258,  276,
  146,  258,  148,  258,  258,  258,  268,  157,  158,  277,
  278,  149,  150,  159,  160,  299,  299,  302,  258,  157,
  158,  171,  290,  286,  170,  293,  300,  302,  166,  302,
  176,  299,  302,  171,  258,  185,  302,  286,  188,  286,
  264,  265,  286,  286,  182,  303,  297,  185,  186,  299,
  188,  299,  297,  277,  278,  256,  257,  258,  259,  260,
  261,  262,  263,  299,  258,  287,  290,  299,  275,  293,
  267,  272,  300,  274,  267,  299,  299,  298,  279,  267,
  281,  256,  257,  258,  274,  256,  257,  258,  270,  281,
  299,  269,  272,  269,  269,  267,  271,  272,  268,  274,
  271,  272,  267,  274,  279,    0,  281,  270,  279,  267,
  281,  256,  257,  258,  271,  256,  257,  258,  271,  300,
    3,   40,  191,  302,   28,  302,  271,  272,    9,  274,
  271,  272,  298,  274,  279,  302,  281,  121,  279,   -1,
  281,  256,  257,  258,   -1,   -1,  302,  257,  257,   -1,
  302,  302,  256,  257,  258,  302,  271,  272,  302,  274,
  302,   -1,   -1,   -1,  279,   -1,  281,  271,  272,   -1,
  274,  256,  257,  258,   -1,  279,   -1,  281,  257,  258,
  259,  260,  261,  262,  263,   -1,  271,  272,   -1,  274,
   -1,   -1,  271,  272,  279,  274,  281,   -1,   -1,   -1,
  279,   -1,  281,   -1,  282,  283,  284,  285,   -1,  287,
  288,  289,  290,   -1,  292,  293,  294,  295,  296,   -1,
  298,   -1,  300,   -1,  302,  303,  282,  283,  284,  285,
   -1,  287,  288,  289,  290,   -1,  292,  293,  294,  295,
  296,   -1,  298,   -1,  300,   -1,  302,  303,  282,  283,
  284,  285,   -1,  287,  288,  289,  290,   -1,  292,  293,
  294,  295,  296,   -1,  298,   -1,  300,   -1,  302,  303,
  282,  283,  284,  285,   -1,  287,   -1,  289,  290,   -1,
  292,  293,   -1,   -1,   -1,   -1,  298,   -1,  300,   -1,
  302,  303,  282,  283,  284,  285,   -1,  287,   -1,  289,
  290,   -1,  292,  293,   -1,   -1,   -1,   -1,  298,   -1,
  300,   -1,  302,  303,  282,  283,  284,  285,   -1,  287,
   -1,  289,  290,   -1,  292,  293,   -1,   -1,   -1,   -1,
  298,   -1,  300,   -1,  302,  303,  282,  283,  284,  285,
   -1,  287,   -1,  289,  290,   -1,  292,  293,   -1,   -1,
   -1,   -1,  298,   -1,  300,   -1,  302,  303,  282,  283,
  284,  285,   -1,  287,   -1,  289,  290,   -1,  292,  293,
  282,  283,  284,  285,   -1,  287,   -1,  289,  290,  303,
  292,  293,  282,  283,  284,  285,   -1,  287,  288,  289,
  290,  303,  292,  293,  294,  295,  296,   -1,  282,  283,
  284,  285,  302,  287,   -1,  289,  290,   -1,  292,  293,
   -1,   -1,  282,  283,  284,  285,   -1,  287,  302,  289,
  290,   -1,  292,  293,  282,  283,  284,  285,   -1,  287,
  300,  289,  290,   -1,  292,  293,  282,  283,  284,  285,
   -1,  287,  300,  289,  290,   -1,  292,  293,  282,  283,
  284,  285,   -1,  287,  300,  289,  290,   -1,  292,  293,
  282,  283,  284,  285,   -1,  287,  300,  289,  290,   -1,
  292,  293,  282,  283,  284,  285,   -1,  287,  300,  289,
  290,   -1,  292,  293,  282,  283,  284,  285,   -1,  287,
  300,  289,  290,  288,  292,  293,   -1,   -1,   -1,  294,
  295,  296,  300,  298,   -1,  300,   -1,  302,  303,  282,
  283,  284,  285,   -1,  287,   -1,  289,  290,   -1,  292,
  293,
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
"declarations : error",
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
"statement : error",
"if_statement : IF LPAREN exp RPAREN BEGIN control_body END IF else_if else",
"else_if : ELSEIF LPAREN exp RPAREN BEGIN control_body END ELSEIF else_if",
"else_if :",
"else : ELSE BEGIN control_body END ELSE",
"else :",
"while_loop : WHILE LPAREN exp RPAREN BEGIN control_body END WHILE",
"for_loop : FOR EACH LPAREN loop_id IN exp RPAREN BEGIN control_body END FOR EACH",
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

//#line 294 "Breezy.yacc"

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
//#line 537 "Parser.java"
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
{yyerror("Error at line: " + getLine(), getLine() );  }
break;
case 13:
//#line 57 "Breezy.yacc"
{yyval.sval = "";}
break;
case 14:
//#line 61 "Breezy.yacc"
{yyval.sval = "String " + val_peek(0).sval + "=\"\""; ba.typeTrack.addID(val_peek(0).sval,"string",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 15:
//#line 62 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval + "=false"; ba.typeTrack.addID(val_peek(0).sval,"boolean",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 16:
//#line 63 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval + "=0"; ba.typeTrack.addID(val_peek(0).sval,"number",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 17:
//#line 64 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval); ba.typeTrack.addID(val_peek(0).sval,"ArrayList",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 18:
//#line 65 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval); ba.typeTrack.addID(val_peek(0).sval,"HashMap",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 19:
//#line 70 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertStringType(val_peek(0));
                                                                         ba.typeTrack.addID(val_peek(2).sval,"string",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 20:
//#line 73 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertNumberType(val_peek(0));
                                                                         ba.typeTrack.addID(val_peek(2).sval,"number",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 21:
//#line 76 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                          ba.typeTrack.addID(val_peek(2).sval,"boolean",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 22:
//#line 79 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 23:
//#line 81 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 24:
//#line 85 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 25:
//#line 86 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 26:
//#line 87 "Breezy.yacc"
{yyval.sval = "";}
break;
case 27:
//#line 91 "Breezy.yacc"
{yyval.sval = "";}
break;
case 28:
//#line 92 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 29:
//#line 93 "Breezy.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 30:
//#line 94 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
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
{yyval.sval = val_peek(0).sval;}
break;
case 34:
//#line 98 "Breezy.yacc"
{val_peek(3).obj = ba.typeTrack.getType(val_peek(3),Scope.LOCAL.getName());
                                                                        ba.typeTrack.assertSameType(val_peek(3),val_peek(1),val_peek(2));
                                                                        yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 35:
//#line 101 "Breezy.yacc"
{yyerror("Error at line: " + getLine(), getLine() ); }
break;
case 36:
//#line 107 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(7));
                                                    yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 37:
//#line 116 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(6));
                                                yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 38:
//#line 118 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 39:
//#line 125 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 40:
//#line 126 "Breezy.yacc"
{yyval.sval = "";}
break;
case 41:
//#line 133 "Breezy.yacc"
{ ba.typeTrack.assertBoolType(val_peek(5));
                                        yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 42:
//#line 140 "Breezy.yacc"
{ba.typeTrack.assertArrayOrHashType(val_peek(6));
                                                    ba.typeTrack.removeLocalID(val_peek(8));
                                                    yyval.sval = "for(TypedParserVal " +val_peek(8).sval+ " : " + val_peek(6).sval + "){" + val_peek(3).sval + "\n}\n";}
break;
case 43:
//#line 145 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(0).sval,"TypedParserVal",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 44:
//#line 148 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 45:
//#line 149 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 46:
//#line 152 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                            yyval.obj = ba.typeTrack.getType(val_peek(3), Scope.GLOBAL.getName());
                                                            yyval.line = val_peek(3).line;}
break;
case 47:
//#line 160 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, Scope.LOCAL.getName(), val_peek(1).sval);}
break;
case 48:
//#line 161 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, Scope.LOCAL.getName(), null);}
break;
case 49:
//#line 165 "Breezy.yacc"
{yyval.sval = "";}
break;
case 50:
//#line 166 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 51:
//#line 169 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(0).sval,val_peek(1).obj.toString(),val_peek(0).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 52:
//#line 171 "Breezy.yacc"
{ba.typeTrack.addID(val_peek(2).sval,val_peek(3).obj.toString(),val_peek(2).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 53:
//#line 175 "Breezy.yacc"
{yyval.sval = "";}
break;
case 54:
//#line 176 "Breezy.yacc"
{yyval.sval = "";}
break;
case 55:
//#line 177 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 56:
//#line 180 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 57:
//#line 181 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 58:
//#line 184 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")" + val_peek(6).sval;}
break;
case 59:
//#line 185 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 60:
//#line 189 "Breezy.yacc"
{yyval.sval = "boolean "; yyval.obj = "boolean";}
break;
case 61:
//#line 190 "Breezy.yacc"
{yyval.sval = "String "; yyval.obj = "string";}
break;
case 62:
//#line 191 "Breezy.yacc"
{yyval.sval = "double "; yyval.obj = "number";}
break;
case 63:
//#line 192 "Breezy.yacc"
{yyval.sval = "ArrayList "; yyval.obj = "ArrayList";}
break;
case 64:
//#line 193 "Breezy.yacc"
{yyval.sval = "HashMap "; yyval.obj = "HashMap";}
break;
case 65:
//#line 196 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 66:
//#line 197 "Breezy.yacc"
{yyval.sval = "void"; yyval.obj = "void";}
break;
case 67:
//#line 200 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                        yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                        yyval.obj = val_peek(2).obj;
                                                        yyval.line = val_peek(2).line;}
break;
case 68:
//#line 204 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 69:
//#line 208 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 70:
//#line 212 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 71:
//#line 216 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(2),val_peek(0),val_peek(1));
                                                yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 72:
//#line 220 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 73:
//#line 224 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 74:
//#line 228 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 75:
//#line 232 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 76:
//#line 237 "Breezy.yacc"
{ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.sval = " !" + val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 77:
//#line 241 "Breezy.yacc"
{  ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.sval = " -"+ val_peek(0).sval;
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 78:
//#line 245 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 79:
//#line 250 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 80:
//#line 253 "Breezy.yacc"
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
case 81:
//#line 264 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 82:
//#line 267 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0), Scope.LOCAL.getName());
                                                    yyval.line = val_peek(0).line;}
break;
case 83:
//#line 270 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 84:
//#line 273 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 85:
//#line 276 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 86:
//#line 279 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 87:
//#line 284 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 88:
//#line 285 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 89:
//#line 286 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 90:
//#line 287 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 91:
//#line 288 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 92:
//#line 289 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1126 "Parser.java"
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
