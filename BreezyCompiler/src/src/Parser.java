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
   24,    3,    3,   16,   16,   20,   20,   10,   10,   10,
   27,   27,   27,   27,   28,   28,   29,   29,   29,   29,
   29,   11,   11,   30,   30,   31,   31,   31,   31,   31,
   31,   31,   32,   32,   32,   32,   32,   32,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    3,    3,    2,    0,
    2,    2,    2,    2,    2,    4,    4,    4,    6,    6,
    1,    2,    0,    1,    1,    1,    1,    1,    1,    4,
   10,    9,    0,    5,    0,    8,    3,    2,    2,    5,
    7,    6,    1,    1,    2,    4,    1,    1,    1,    1,
    3,    7,    5,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    7,    6,    1,    1,    3,    3,    1,
    3,    3,    3,    1,    2,    1,    3,    1,    1,    1,
    1,    3,    1,    3,    1,    2,    3,    3,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   57,   58,
   59,   60,   61,   63,    0,   62,    0,   43,    0,   44,
    0,    0,    0,    0,    0,    0,    0,    9,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   21,   25,   26,   27,   28,   29,   46,    4,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   89,   90,
   78,   81,    0,    0,    0,    0,    0,    0,   39,    0,
    0,   74,   76,    0,   85,    0,   24,   22,    7,    8,
    0,    0,    0,   47,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   86,    0,    0,   80,
   75,    0,    0,   95,   93,   96,   94,   97,    0,    0,
    0,    0,    0,   37,    0,    0,    0,    0,    0,   30,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   77,   87,   98,    0,    0,    0,    0,   73,   71,   72,
   84,    0,   40,    0,    0,    0,    0,    0,    0,    0,
    0,   42,    0,   19,   56,   55,   54,    0,   20,    0,
    0,   41,    0,    0,    0,    0,    0,   36,    0,    0,
    0,    0,    0,    0,   31,   52,    0,    0,    0,    0,
    0,    0,    0,   34,    0,    0,   32,
};
final static short yydgoto[] = {                          2,
    3,    4,   15,   19,   24,   25,   38,   39,   40,   95,
   86,   87,  149,   41,   82,   43,   44,   45,   46,   70,
  171,  175,   20,   21,   88,  158,   71,   72,   73,   74,
   75,  112,
};
final static short yysindex[] = {                      -245,
 -247,    0, -245,    0, -240,    0, -222, -156,    0,    0,
    0,    0,    0,    0, -197,    0,    2,    0, -189,    0,
 -138,    0, -200, -150,  -39,  217, -121,    0, -255, -120,
 -111, -109, -106,  -99, -136, -147, -116, -130, -141, -133,
    0,    0,    0,    0,    0,    0,    0,    0,  -85, -176,
  -77, -102, -100,  -98,  -97,  -92,  -85, -289,    0,    0,
    0,    0,  -85, -252,  -85,  183,  -86,    0,    0, -115,
 -198,    0,    0,  -82,    0,  -85,    0,    0,    0,    0,
  -89,    0,  -87,    0,  183,  -86,  -83,  -74,  -73,  -85,
 -252, -252,  -67,  -66,  183, -266,    0,  -89, -252,    0,
    0,  147, -254,    0,    0,    0,    0,    0,  -53, -252,
 -252, -252,  -85,    0, -252, -252, -252,  -85, -235,    0,
  -59,  -85, -248,  -86, -182, -182, -176,  -64,  -23, -219,
    0,    0,    0, -198, -198, -182,  -82,    0,    0,    0,
    0,  -16,    0,  -74,  -55,  -61,  -30, -239,  -27, -130,
 -130,    0,  -43,    0,    0,    0,    0,  -42,    0, -203,
  -31,    0, -239,   -8,  -12,  -28,    3,    0,  -18,  -24,
    6,  -64,  -85,   12,    0,    0, -223, -130,   22,  -26,
 -130,   26,  -21,    0,   27,    3,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  299,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -63,   39,    0,   40,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   18,   20,   32,   38,   42,    0,  131,    0,    0,
    0,    0,    0,    0,    0,   49,   54, -117,    0,    0,
   65,    0,    0,  146,    0,    0,    0,    0,    0,    0,
   -1,   21,    0,    0, -250, -218,    0, -166,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   43,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   57,   58,   59,    0,    0,    0,    0,
    0,    0,    0,   87,  109,  153,  161,    0,    0,    0,
    0,    0,    0, -165,    0,    0,    0,    0,    0,   40,
   40,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -68,    0,   31,    0,
   -2,    0,    0,    0,    0,    0,    0,   40,    0,    0,
   40,    0,    0,    0,    0,  -68,    0,
};
final static short yygindex[] = {                         0,
    0,  338,    0,    0,    0,    0, -146,    0,    0,  -29,
  -34,  -15,  189,  -37,  -25,  327,    0,    0,    0,  316,
  186,    0,  351,  372,  259,  219,    4,  -56,    0,  270,
  -60,    0,
};
final static int YYTABLESIZE=480;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         42,
   78,   67,   97,  160,  161,   98,   66,  101,   50,   81,
   68,    1,   42,   51,   67,   59,   60,    7,  155,   66,
   85,  113,   96,   61,   62,    5,   84,   61,   62,   49,
  103,  180,  129,  113,  183,  102,  156,  157,  100,   64,
   63,  119,   50,   64,  132,   99,   49,   51,   49,   65,
  145,   49,  113,   77,   29,  124,    8,  141,  138,  139,
  140,  125,  126,  142,  113,  100,  100,  164,   17,  130,
   35,  110,  111,  100,   36,  179,   37,   22,   50,  131,
   50,   81,  136,   50,  100,  100,  100,   59,   60,  100,
  100,  100,   85,   85,  115,  116,  117,   85,   84,   61,
   62,   26,    9,   10,   11,   12,   13,  146,  110,  111,
   58,  147,   63,  134,  135,   64,   59,   60,   14,   23,
   27,   65,   78,   78,   42,   42,   77,   29,   61,   62,
   48,   51,   48,   51,   42,   42,   48,   52,  177,   38,
   38,   63,   78,   35,   64,   78,   53,   36,   54,   37,
   65,   55,   42,   38,   42,   42,   38,   42,   56,   79,
   38,   57,   38,   80,   80,   80,   80,   80,   80,   92,
   92,   80,   81,   80,   80,   80,   80,   80,   59,   60,
   89,   76,   90,   80,   91,  114,   92,   93,   33,   33,
   61,   62,   94,   10,   10,   10,   10,   10,   10,   10,
   33,  113,   33,   63,  118,   33,   64,    6,   50,   33,
   10,   33,   65,  120,   10,  121,   10,   28,   29,   30,
   31,   32,   33,   34,  123,   77,   29,  122,  127,  128,
   77,   29,  133,  148,   35,   77,   29,  153,   36,  165,
   37,  143,   35,  150,  182,  152,   36,   35,   37,  185,
  151,   36,   35,   37,   35,   35,   36,  162,   37,  163,
    9,   10,   11,   12,   13,  167,  154,  168,   35,  159,
  169,   35,  170,  173,  174,   35,   18,   35,  178,   79,
   79,   79,   79,  172,   79,   91,   91,   79,  181,   79,
   79,   79,   79,   79,  184,   79,  186,   79,    1,   79,
   79,   80,   80,   80,   80,   45,   80,   92,   92,   80,
   23,   80,   80,   80,   80,   80,    5,   80,   12,   80,
   11,   80,   80,   79,   79,   79,   79,   53,   79,   79,
   79,   79,   13,   79,   79,   79,   79,   79,   14,   79,
    6,   79,   15,   79,   79,   70,   70,   70,   70,   67,
   70,   70,   70,   70,   66,   70,   70,   18,   16,   17,
  176,   70,   69,   70,   83,   70,   70,   68,   68,   68,
   68,  187,   68,   68,   68,   68,   47,   68,   68,   16,
  144,  166,  137,   68,    0,   68,    0,   68,   68,   69,
   69,   69,   69,    0,   69,   69,   69,   69,    0,   69,
   69,    0,    0,    0,    0,   69,    0,   69,    0,   69,
   69,   79,   79,   79,   79,    0,   79,   91,   91,   79,
    0,   79,   79,   79,   79,   79,    0,  104,  105,  106,
  107,   79,  108,   83,    0,  109,    0,  110,  111,   88,
   88,    0,   83,    0,   83,  131,   83,   83,   82,   88,
    0,   88,    0,   88,   88,    0,    0,   82,    0,   82,
    0,   82,   82,  104,  105,  106,  107,    0,  108,    0,
    0,  109,    0,  110,  111,    9,   10,   11,   12,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         25,
   38,   36,   63,  150,  151,  258,   36,   64,  298,  258,
   36,  257,   38,  303,   49,  264,  265,  258,  258,   49,
   50,  288,   57,  276,  277,  273,  275,  276,  277,  285,
   65,  178,  299,  288,  181,   65,  276,  277,   64,  292,
  289,   76,  298,  292,  299,  298,  297,  303,  299,  298,
  299,  302,  288,  257,  258,   90,  279,  118,  115,  116,
  117,   91,   92,  299,  288,   91,   92,  271,  266,   99,
  274,  291,  292,   99,  278,  299,  280,  267,  297,  299,
  299,  258,  112,  302,  110,  111,  112,  264,  265,  115,
  116,  117,  122,  123,  293,  294,  295,  127,  275,  276,
  277,  302,  259,  260,  261,  262,  263,  123,  291,  292,
  258,  127,  289,  110,  111,  292,  264,  265,  275,  258,
  271,  298,  160,  161,  150,  151,  257,  258,  276,  277,
  297,  297,  299,  299,  160,  161,  258,  258,  173,  257,
  258,  289,  180,  274,  292,  183,  258,  278,  258,  280,
  298,  258,  178,  271,  180,  181,  274,  183,  258,  301,
  278,  298,  280,  281,  282,  283,  284,  301,  286,  287,
  288,  289,  258,  291,  292,  293,  294,  295,  264,  265,
  258,  298,  285,  301,  285,  301,  285,  285,  257,  258,
  276,  277,  285,  257,  258,  259,  260,  261,  262,  263,
  269,  288,  271,  289,  287,  274,  292,  271,  298,  278,
  274,  280,  298,  301,  278,  299,  280,  257,  258,  259,
  260,  261,  262,  263,  298,  257,  258,  302,  296,  296,
  257,  258,  286,  298,  274,  257,  258,  299,  278,  271,
  280,  301,  274,  267,  271,  301,  278,  274,  280,  271,
  267,  278,  274,  280,  257,  258,  278,  301,  280,  302,
  259,  260,  261,  262,  263,  274,  297,  280,  271,  297,
  299,  274,  270,  298,  269,  278,  275,  280,  267,  281,
  282,  283,  284,  302,  286,  287,  288,  289,  267,  291,
  292,  293,  294,  295,  269,  297,  270,  299,    0,  301,
  302,  281,  282,  283,  284,  267,  286,  287,  288,  289,
  271,  291,  292,  293,  294,  295,  271,  297,  301,  299,
  301,  301,  302,  281,  282,  283,  284,  297,  286,  287,
  288,  289,  301,  291,  292,  293,  294,  295,  301,  297,
    3,  299,  301,  301,  302,  281,  282,  283,  284,  301,
  286,  287,  288,  289,  301,  291,  292,  301,  301,  301,
  172,  297,   36,  299,   49,  301,  302,  281,  282,  283,
  284,  186,  286,  287,  288,  289,   26,  291,  292,    8,
  122,  163,  113,  297,   -1,  299,   -1,  301,  302,  281,
  282,  283,  284,   -1,  286,  287,  288,  289,   -1,  291,
  292,   -1,   -1,   -1,   -1,  297,   -1,  299,   -1,  301,
  302,  281,  282,  283,  284,   -1,  286,  287,  288,  289,
   -1,  291,  292,  293,  294,  295,   -1,  281,  282,  283,
  284,  301,  286,  288,   -1,  289,   -1,  291,  292,  287,
  288,   -1,  297,   -1,  299,  299,  301,  302,  288,  297,
   -1,  299,   -1,  301,  302,   -1,   -1,  297,   -1,  299,
   -1,  301,  302,  281,  282,  283,  284,   -1,  286,   -1,
   -1,  289,   -1,  291,  292,  259,  260,  261,  262,  263,
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
"statement : function_declaration",
"statement : complex_type_method_invocation",
"statement : return_statement",
"statement : if_statement",
"statement : while_loop",
"statement : IDENTIFIER EQUALS exp SEMICOLON",
"if_statement : IF LPAREN bool_exp RPAREN BEGIN control_body END IF else_if else",
"else_if : ELSEIF LPAREN bool_exp RPAREN BEGIN control_body END ELSEIF else_if",
"else_if :",
"else : ELSE BEGIN control_body END ELSE",
"else :",
"while_loop : WHILE LPAREN bool_exp RPAREN BEGIN control_body END WHILE",
"return_statement : RETURN exp SEMICOLON",
"return_statement : RETURN function_declaration",
"return_statement : RETURN complex_type_method_invocation",
"function_declaration : IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
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
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
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

//#line 242 "Breezy.yacc"

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
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 4:
//#line 40 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6).sval,val_peek(4).sval,val_peek(2).sval,val_peek(0).sval); }
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
{yyval.sval = "String " + val_peek(0).sval + "=\"\""; ba.addIdentifier(val_peek(0).sval,"string");}
break;
case 12:
//#line 59 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval + "=false"; ba.addIdentifier(val_peek(0).sval,"boolean");}
break;
case 13:
//#line 60 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval + "=0"; ba.addIdentifier(val_peek(0).sval,"number");}
break;
case 14:
//#line 61 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval); ba.addIdentifier(val_peek(0).sval,"ArrayList");}
break;
case 15:
//#line 62 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval); ba.addIdentifier(val_peek(0).sval,"HashMap");}
break;
case 16:
//#line 67 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertStringType(val_peek(0).obj);
                                                                         ba.addIdentifier(val_peek(2).sval,"string");}
break;
case 17:
//#line 70 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertNumberType(val_peek(0).obj);
                                                                         ba.addIdentifier(val_peek(2).sval,"number");}
break;
case 18:
//#line 73 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval; ba.addIdentifier(val_peek(2).sval,"boolean");}
break;
case 19:
//#line 74 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval);}
break;
case 20:
//#line 75 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(4).sval, val_peek(1).sval);}
break;
case 21:
//#line 79 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 22:
//#line 80 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 23:
//#line 81 "Breezy.yacc"
{yyval.sval = "";}
break;
case 24:
//#line 85 "Breezy.yacc"
{yyval.sval = "";}
break;
case 25:
//#line 86 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 26:
//#line 87 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 27:
//#line 88 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 28:
//#line 89 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 29:
//#line 90 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 30:
//#line 91 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 31:
//#line 97 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 32:
//#line 104 "Breezy.yacc"
{ yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 33:
//#line 105 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 34:
//#line 112 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 35:
//#line 113 "Breezy.yacc"
{yyval.sval = "";}
break;
case 36:
//#line 120 "Breezy.yacc"
{ yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 37:
//#line 123 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 38:
//#line 124 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval + ";\n";}
break;
case 39:
//#line 125 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval + ";\n";}
break;
case 40:
//#line 128 "Breezy.yacc"
{yyval.sval = val_peek(4).sval + "(" + val_peek(2).sval + ");\n";
                                                                                yyval.obj = ba.typeTrack.getType(val_peek(4).sval);}
break;
case 41:
//#line 135 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 42:
//#line 136 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 43:
//#line 140 "Breezy.yacc"
{yyval.sval = "";}
break;
case 44:
//#line 141 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 45:
//#line 144 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 46:
//#line 145 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 47:
//#line 148 "Breezy.yacc"
{yyval.sval = "";}
break;
case 48:
//#line 149 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 49:
//#line 152 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 50:
//#line 153 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 51:
//#line 154 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 52:
//#line 157 "Breezy.yacc"
{yyval.sval = "(" + val_peek(5).sval + "," + val_peek(3).sval + ")" + val_peek(0).sval;}
break;
case 53:
//#line 158 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 54:
//#line 161 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 55:
//#line 162 "Breezy.yacc"
{yyval.ival = val_peek(0).ival;}
break;
case 56:
//#line 163 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 57:
//#line 167 "Breezy.yacc"
{yyval.sval = "boolean ";}
break;
case 58:
//#line 168 "Breezy.yacc"
{yyval.sval = "String ";}
break;
case 59:
//#line 169 "Breezy.yacc"
{yyval.sval = "double ";}
break;
case 60:
//#line 170 "Breezy.yacc"
{yyval.sval = "ArrayList ";}
break;
case 61:
//#line 171 "Breezy.yacc"
{yyval.sval = "HashMap ";}
break;
case 62:
//#line 174 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 63:
//#line 175 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 64:
//#line 179 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 65:
//#line 180 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 66:
//#line 183 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 67:
//#line 184 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 68:
//#line 187 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;  yyval.obj = ba.typeTrack.assertSameType(val_peek(2).obj,val_peek(0).obj, "+"); }
break;
case 69:
//#line 188 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;  yyval.obj = ba.typeTrack.assertNumberType(val_peek(2).obj,val_peek(0).obj,"-"); }
break;
case 70:
//#line 189 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 71:
//#line 192 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;  yyval.obj = ba.typeTrack.assertNumberType(val_peek(2).obj,val_peek(0).obj, "*"); }
break;
case 72:
//#line 193 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;  yyval.obj = ba.typeTrack.assertNumberType(val_peek(2).obj,val_peek(0).obj, "/"); }
break;
case 73:
//#line 194 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;  yyval.obj = ba.typeTrack.assertNumberType(val_peek(2).obj,val_peek(0).obj, "%");}
break;
case 74:
//#line 195 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 75:
//#line 198 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval; yyval.obj = val_peek(0).obj; ba.typeTrack.assertNumberType(val_peek(1).obj);}
break;
case 76:
//#line 199 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 77:
//#line 202 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; yyval.obj = val_peek(1).obj; }
break;
case 78:
//#line 203 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj; }
break;
case 79:
//#line 204 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; yyval.obj = ba.typeTrack.getType(val_peek(0).sval); }
break;
case 80:
//#line 205 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; yyval.obj = ba.typeTrack.getType(val_peek(0).sval); }
break;
case 81:
//#line 206 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj; }
break;
case 82:
//#line 209 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; yyval.obj = val_peek(2).obj;}
break;
case 83:
//#line 210 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 84:
//#line 213 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;  yyval.obj = val_peek(2).obj; }
break;
case 85:
//#line 214 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 86:
//#line 217 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 87:
//#line 218 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; yyval.obj = val_peek(1).obj; }
break;
case 88:
//#line 219 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + val_peek(0).sval; ba.typeTrack.assertNumberType(val_peek(2).obj,val_peek(0).obj,val_peek(1).sval); yyval.obj = "boolean";}
break;
case 89:
//#line 220 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 90:
//#line 221 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;}
break;
case 91:
//#line 222 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                            val_peek(0).obj = ba.typeTrack.getType(val_peek(0).sval);
                                            ba.typeTrack.assertBoolType(val_peek(0).obj);
                                            yyval.obj = ba.typeTrack.getType(val_peek(0).sval); }
break;
case 92:
//#line 226 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                val_peek(0).obj = ba.typeTrack.getType(val_peek(0).sval);
                                                ba.typeTrack.assertBoolType(val_peek(0).obj);
                                                yyval.obj = ba.typeTrack.getType(val_peek(0).sval);}
break;
case 93:
//#line 232 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 94:
//#line 233 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 95:
//#line 234 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 96:
//#line 235 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 97:
//#line 236 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 98:
//#line 237 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1053 "Parser.java"
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
