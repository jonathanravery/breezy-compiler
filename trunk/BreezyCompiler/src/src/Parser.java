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
    9,    9,    7,    7,    7,   14,   14,   14,   14,   14,
   14,   14,   18,   21,   21,   22,   22,   19,   17,   17,
   15,   16,   16,    4,    4,   23,   23,   12,   12,   25,
   25,   25,   13,   13,   26,   26,   26,   26,   26,   24,
   24,   24,   24,   24,    3,    3,   20,   20,   10,   10,
   10,   27,   27,   27,   27,   28,   28,   29,   29,   29,
   29,   29,   11,   11,   30,   30,   31,   31,   31,   31,
   31,   31,   31,   32,   32,   32,   32,   32,   32,
};
final static short yylen[] = {                            2,
    1,    1,    1,    2,    2,   11,    2,    0,    3,    3,
    2,    0,    2,    2,    2,    2,    2,    4,    4,    4,
    6,    6,    1,    2,    0,    1,    2,    2,    2,    1,
    1,    4,   10,    9,    0,    5,    0,    8,    2,    2,
    4,    6,    5,    1,    1,    2,    4,    1,    1,    1,
    1,    3,    7,    5,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    3,    3,
    1,    3,    3,    3,    1,    2,    1,    3,    1,    1,
    1,    1,    3,    1,    3,    1,    2,    3,    3,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    4,    0,    0,   60,
   61,   62,   63,   64,   66,    0,   65,    0,   44,    0,
   45,    0,    0,    0,    0,    0,    0,    0,   11,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   23,    0,    0,    0,   30,   31,   47,    6,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   90,
   91,   79,   82,    0,    0,    0,    0,    0,    0,   40,
   39,    0,   75,   77,    0,   86,    0,   26,   24,    9,
   10,   27,   28,   29,    0,    0,   48,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   87,
    0,    0,   81,   76,    0,    0,   96,   94,   97,   95,
   98,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   32,   41,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   78,   88,   99,    0,    0,    0,    0,   74,
   72,   73,   85,    0,    0,   43,    0,    0,    0,    0,
    0,    0,   42,   21,   59,   57,   58,   56,   55,    0,
   22,    0,    0,    0,    0,    0,    0,    0,   38,    0,
    0,    0,    0,    0,    0,   33,   53,    0,    0,    0,
    0,    0,    0,    0,   36,    0,    0,   34,
};
final static short yydgoto[] = {                          2,
    3,    4,   16,   20,   25,   26,   39,   40,   41,   98,
   89,   90,  150,   42,   69,   44,   45,   46,   47,   71,
  172,  176,   21,   22,   91,  160,   72,   73,   74,   75,
   76,  115,
};
final static short yysindex[] = {                      -251,
 -259,    0, -235,    0, -217, -259,    0, -232,  -23,    0,
    0,    0,    0,    0,    0, -222,    0,  169,    0, -197,
    0, -178,    0, -220, -165,  -71,  178, -174,    0, -250,
 -147, -127, -125, -114, -108, -185, -160, -137, -135, -150,
 -132,    0, -131, -130, -128,    0,    0,    0,    0, -117,
 -168,  -84, -107, -105, -103, -101,  -92, -117, -272,    0,
    0,    0,    0, -117, -243, -117,  176, -111,    0,    0,
    0, -175,    0,    0,  -91,    0, -117,    0,    0,    0,
    0,    0,    0,    0, -100, -104,    0,  176, -111,  -98,
  -90,  -96, -117, -243, -243,  -86,  -82,  176, -280,    0,
 -100, -243,    0,    0,  135, -276,    0,    0,    0,    0,
    0,  -67, -243, -243, -243, -117, -243, -243, -243, -117,
 -261,    0,    0, -117, -247, -111, -225, -225, -168,  -74,
  -44, -157,    0,    0,    0, -175, -175, -225,  -91,    0,
    0,    0,    0,  -41,  -90,    0,  -69,  -66, -202,  -65,
 -135, -135,    0,    0,    0,    0,    0,    0,    0,  -61,
    0,  -58,  -53, -202,  -39,  -32,  -46,  -15,    0,  -56,
  -37,   -7,  -74, -117,   -4,    0,    0, -242, -135,   -2,
  -29, -135,    3,  -24,    0,   -3,  -15,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,  277,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -95,   16,    0,   14,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   23,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    4,    6,    8,   15,   20,    0,  119,    0,
    0,    0,    0,    0,    0,    0,   26,   28,  -13,    0,
    0,   53,    0,    0,  148,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    9,    0,    0, -216, -145,    0,
 -239,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   31,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   30,   37,   42,    0,    0,
    0,    0,    0,    0,    0,   75,   97, -187,  154,    0,
    0,    0,    0,    0, -226,    0,    0,    0,    0,    0,
   14,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -63,    0,  -10,
    0,  -14,    0,    0,    0,    0,    0,    0,   14,    0,
    0,   14,    0,    0,    0,    0,  -63,    0,
};
final static short yygindex[] = {                         0,
    0,  296,    0,    0,    0,    0, -142,    0,    0,  -30,
  -34,  -75,  173,  -35,  -26,  310,    0,    0,    0,  298,
  162,    0,  324,  344,  236,  201,  -11,  -40,    0,  252,
  -59,    0,
};
final static int YYTABLESIZE=468;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
    3,    5,   68,   79,  100,    1,   67,  116,  162,  163,
   85,  116,   43,    5,  101,   68,   60,   61,  131,   67,
   88,    6,  134,   99,  104,   51,  116,   87,   62,   63,
   52,  106,   62,   63,   50,  105,  181,  144,  103,  184,
    8,   64,  121,   18,   65,  116,    9,   51,   65,  147,
   66,  146,   52,  148,  102,  155,  180,   49,  126,   49,
  143,  156,  157,  127,  128,  113,  114,  103,  103,   23,
   52,  132,   52,  158,  159,  103,  140,  141,  142,   24,
   50,   27,   50,   49,  138,   50,  103,  103,  103,   85,
  103,  103,  103,   88,   88,   60,   61,   59,   88,   89,
   89,  136,  137,   60,   61,   28,   87,   62,   63,   89,
   53,   89,   58,   89,   89,   62,   63,  117,  118,  119,
   64,   78,   30,   65,   43,   43,   79,   79,   64,   66,
   54,   65,   55,  113,  114,   43,   43,   66,   36,  178,
   85,  133,   37,   56,   38,   79,   60,   61,   79,   57,
   80,   51,   43,   51,   43,   43,   51,   43,   62,   63,
   77,   12,   12,   12,   12,   12,   12,   12,   81,   82,
   83,   64,   84,   92,   65,    8,  116,   93,   12,   94,
   66,   95,   12,   96,   12,   29,   30,   31,   32,   33,
   34,   35,   97,   35,   35,  120,  122,   51,   78,   30,
  123,  125,   36,   78,   30,   35,   37,   35,   38,  129,
   35,  124,  165,  130,   35,   36,   35,  166,  135,   37,
   36,   38,  151,  149,   37,  152,   38,   78,   30,  153,
  154,  161,   78,   30,  168,   10,   11,   12,   13,   14,
  164,  183,   37,   37,   36,  173,  186,  169,   37,   36,
   38,   15,  170,   37,  171,   38,   37,    3,    5,   37,
  174,  175,  179,   37,  182,   37,  187,   81,   81,   81,
   81,  185,   81,   93,   93,   81,    1,   81,   81,   81,
   81,   81,   46,   81,   25,   81,   54,   81,   81,   80,
   80,   80,   80,    7,   80,   92,   92,   80,    7,   80,
   80,   80,   80,   80,   14,   80,   13,   80,   15,   80,
   80,   80,   80,   80,   80,   16,   80,   80,   80,   80,
   17,   80,   80,   80,   80,   80,   68,   80,   67,   80,
   20,   80,   80,   71,   71,   71,   71,   18,   71,   71,
   71,   71,   19,   71,   71,  177,   70,   86,  188,   71,
   48,   71,   17,   71,   71,   69,   69,   69,   69,  145,
   69,   69,   69,   69,  167,   69,   69,  139,    0,    0,
    0,   69,    0,   69,    0,   69,   69,   70,   70,   70,
   70,    0,   70,   70,   70,   70,    0,   70,   70,    0,
    0,    0,    0,   70,    0,   70,    0,   70,   70,   80,
   80,   80,   80,    0,   80,   92,   92,   80,    0,   80,
   80,   80,   80,   80,    0,  107,  108,  109,  110,   80,
  111,    0,    0,  112,    0,  113,  114,   10,   11,   12,
   13,   14,    0,  133,    0,   84,   10,   11,   12,   13,
   14,   83,    0,   19,   84,    0,   84,    0,   84,   84,
   83,    0,   83,    0,   83,   83,  107,  108,  109,  110,
    0,  111,    0,    0,  112,    0,  113,  114,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         26,
    0,    0,   37,   39,   64,  257,   37,  288,  151,  152,
  258,  288,   39,  273,  258,   50,  264,  265,  299,   50,
   51,  257,  299,   58,   65,  298,  288,  275,  276,  277,
  303,   66,  276,  277,  285,   66,  179,  299,   65,  182,
  258,  289,   77,  266,  292,  288,  279,  298,  292,  125,
  298,  299,  303,  129,  298,  258,  299,  297,   93,  299,
  120,  264,  265,   94,   95,  291,  292,   94,   95,  267,
  297,  102,  299,  276,  277,  102,  117,  118,  119,  258,
  297,  302,  299,  258,  115,  302,  113,  114,  115,  258,
  117,  118,  119,  124,  125,  264,  265,  258,  129,  287,
  288,  113,  114,  264,  265,  271,  275,  276,  277,  297,
  258,  299,  298,  301,  302,  276,  277,  293,  294,  295,
  289,  257,  258,  292,  151,  152,  162,  163,  289,  298,
  258,  292,  258,  291,  292,  162,  163,  298,  274,  174,
  258,  299,  278,  258,  280,  181,  264,  265,  184,  258,
  301,  297,  179,  299,  181,  182,  302,  184,  276,  277,
  298,  257,  258,  259,  260,  261,  262,  263,  301,  301,
  301,  289,  301,  258,  292,  271,  288,  285,  274,  285,
  298,  285,  278,  285,  280,  257,  258,  259,  260,  261,
  262,  263,  285,  257,  258,  287,  301,  298,  257,  258,
  299,  298,  274,  257,  258,  269,  278,  271,  280,  296,
  274,  302,  271,  296,  278,  274,  280,  271,  286,  278,
  274,  280,  267,  298,  278,  267,  280,  257,  258,  299,
  297,  297,  257,  258,  274,  259,  260,  261,  262,  263,
  302,  271,  257,  258,  274,  302,  271,  280,  278,  274,
  280,  275,  299,  278,  270,  280,  271,  257,  257,  274,
  298,  269,  267,  278,  267,  280,  270,  281,  282,  283,
  284,  269,  286,  287,  288,  289,    0,  291,  292,  293,
  294,  295,  267,  297,  271,  299,  297,  301,  302,  281,
  282,  283,  284,  271,  286,  287,  288,  289,    3,  291,
  292,  293,  294,  295,  301,  297,  301,  299,  301,  301,
  302,  281,  282,  283,  284,  301,  286,  287,  288,  289,
  301,  291,  292,  293,  294,  295,  301,  297,  301,  299,
  301,  301,  302,  281,  282,  283,  284,  301,  286,  287,
  288,  289,  301,  291,  292,  173,   37,   50,  187,  297,
   27,  299,    9,  301,  302,  281,  282,  283,  284,  124,
  286,  287,  288,  289,  164,  291,  292,  116,   -1,   -1,
   -1,  297,   -1,  299,   -1,  301,  302,  281,  282,  283,
  284,   -1,  286,  287,  288,  289,   -1,  291,  292,   -1,
   -1,   -1,   -1,  297,   -1,  299,   -1,  301,  302,  281,
  282,  283,  284,   -1,  286,  287,  288,  289,   -1,  291,
  292,  293,  294,  295,   -1,  281,  282,  283,  284,  301,
  286,   -1,   -1,  289,   -1,  291,  292,  259,  260,  261,
  262,  263,   -1,  299,   -1,  288,  259,  260,  261,  262,
  263,  288,   -1,  275,  297,   -1,  299,   -1,  301,  302,
  297,   -1,  299,   -1,  301,  302,  281,  282,  283,  284,
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
"type_declaration_assignment : STRING IDENTIFIER EQUALS arith_exp",
"type_declaration_assignment : NUMBER IDENTIFIER EQUALS arith_exp",
"type_declaration_assignment : BOOLEAN IDENTIFIER EQUALS bool_exp",
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
"if_statement : IF LPAREN bool_exp RPAREN BEGIN control_body END IF else_if else",
"else_if : ELSEIF LPAREN bool_exp RPAREN BEGIN control_body END ELSEIF else_if",
"else_if :",
"else : ELSE BEGIN control_body END ELSE",
"else :",
"while_loop : WHILE LPAREN bool_exp RPAREN BEGIN control_body END WHILE",
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
"params : params_",
"params_ : arith_exp",
"params_ : bool_exp",
"params_ : params_ COMMA params_",
"hash_params : LPAREN hash_item COMMA hash_item RPAREN COMMA hash_params",
"hash_params : LPAREN hash_item COMMA hash_item RPAREN",
"hash_item : QUOTE",
"hash_item : NUMERIC",
"hash_item : TRUE",
"hash_item : FALSE",
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
"factor : function_call",
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
"bool_factor : function_call",
"rel_op : REL_OP_LT",
"rel_op : REL_OP_GT",
"rel_op : REL_OP_LE",
"rel_op : REL_OP_GE",
"rel_op : LOG_OP_EQUAL",
"rel_op : LOG_OP_NOT LOG_OP_EQUAL",
};

//#line 312 "Breezy.yacc"

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
//#line 501 "Parser.java"
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
{ yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 34:
//#line 111 "Breezy.yacc"
{ yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 35:
//#line 112 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 36:
//#line 119 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 37:
//#line 120 "Breezy.yacc"
{yyval.sval = "";}
break;
case 38:
//#line 127 "Breezy.yacc"
{ yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 39:
//#line 130 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 40:
//#line 131 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 41:
//#line 134 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                            yyval.obj = ba.typeTrack.getType(val_peek(3), Scope.GLOBAL.getName());}
break;
case 42:
//#line 141 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, Scope.LOCAL.getName(), val_peek(1).sval);}
break;
case 43:
//#line 142 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, Scope.LOCAL.getName(), null);}
break;
case 44:
//#line 146 "Breezy.yacc"
{yyval.sval = "";}
break;
case 45:
//#line 147 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 46:
//#line 150 "Breezy.yacc"
{ba.addIdentifier(val_peek(0).sval,val_peek(1).obj.toString(),val_peek(0).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 47:
//#line 152 "Breezy.yacc"
{ba.addIdentifier(val_peek(2).sval,val_peek(3).obj.toString(),val_peek(2).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 48:
//#line 156 "Breezy.yacc"
{yyval.sval = "";}
break;
case 49:
//#line 157 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 50:
//#line 160 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 51:
//#line 161 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 162 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 53:
//#line 165 "Breezy.yacc"
{yyval.sval = "(" + val_peek(5).sval + "," + val_peek(3).sval + ")" + val_peek(0).sval;}
break;
case 54:
//#line 166 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 55:
//#line 169 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 56:
//#line 170 "Breezy.yacc"
{yyval.ival = val_peek(0).ival;}
break;
case 57:
//#line 171 "Breezy.yacc"
{yyval.sval = "true";}
break;
case 58:
//#line 172 "Breezy.yacc"
{yyval.sval = "false";}
break;
case 59:
//#line 173 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 60:
//#line 177 "Breezy.yacc"
{yyval.sval = "boolean "; yyval.obj = "boolean";}
break;
case 61:
//#line 178 "Breezy.yacc"
{yyval.sval = "String "; yyval.obj = "string";}
break;
case 62:
//#line 179 "Breezy.yacc"
{yyval.sval = "double "; yyval.obj = "number";}
break;
case 63:
//#line 180 "Breezy.yacc"
{yyval.sval = "ArrayList "; yyval.obj = "ArrayList";}
break;
case 64:
//#line 181 "Breezy.yacc"
{yyval.sval = "HashMap "; yyval.obj = "HashMap";}
break;
case 65:
//#line 184 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 66:
//#line 185 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 67:
//#line 188 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.line = val_peek(0).line;
                                                yyval.obj = val_peek(0).obj;}
break;
case 68:
//#line 191 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.line = val_peek(0).line;
                                                yyval.obj = val_peek(0).obj;}
break;
case 69:
//#line 196 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 70:
//#line 200 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 71:
//#line 204 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 72:
//#line 208 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 73:
//#line 212 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 74:
//#line 216 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 75:
//#line 220 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 76:
//#line 225 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval; 
                                        ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 77:
//#line 229 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 78:
//#line 234 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 79:
//#line 237 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 80:
//#line 240 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0), Scope.LOCAL.getName());
                                                    yyval.line = val_peek(0).line;}
break;
case 81:
//#line 243 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 82:
//#line 246 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 83:
//#line 251 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                         yyval.obj = val_peek(2).obj;
                                                            yyval.line = val_peek(2).line;}
break;
case 84:
//#line 254 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                                            yyval.obj = val_peek(0).obj;
                                                            yyval.line = val_peek(0).line;}
break;
case 85:
//#line 259 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;  
                                                            yyval.obj = val_peek(2).obj;
                                                            yyval.line = val_peek(2).line; }
break;
case 86:
//#line 262 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                                                    yyval.obj = val_peek(0).obj;
                                                                    yyval.line = val_peek(0).line;}
break;
case 87:
//#line 267 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; 
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 88:
//#line 270 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; 
                                                yyval.obj = val_peek(1).obj;
                                                yyval.line = val_peek(2).line;}
break;
case 89:
//#line 273 "Breezy.yacc"
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
//#line 284 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 91:
//#line 287 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 92:
//#line 290 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                            val_peek(0).obj = ba.typeTrack.getType(val_peek(0), Scope.LOCAL.getName());
                                            ba.typeTrack.assertBoolType(val_peek(0));
                                            yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line; }
break;
case 93:
//#line 295 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                val_peek(0).obj = ba.typeTrack.getType(val_peek(0), Scope.GLOBAL.getName());
                                                ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 94:
//#line 302 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 95:
//#line 303 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 96:
//#line 304 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 97:
//#line 305 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 98:
//#line 306 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 99:
//#line 307 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1127 "Parser.java"
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
