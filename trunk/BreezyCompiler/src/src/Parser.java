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
   18,   21,   21,   22,   22,   19,   17,   17,   15,   16,
   16,    4,    4,   23,   23,   12,   12,   25,   25,   25,
   13,   13,   26,   26,   26,   24,   24,   24,   24,   24,
    3,    3,   20,   20,   10,   10,   10,   27,   27,   27,
   27,   28,   28,   29,   29,   29,   29,   29,   11,   11,
   30,   30,   31,   31,   31,   31,   31,   31,   31,   32,
   32,   32,   32,   32,   32,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    3,    3,    2,    0,
    2,    2,    2,    2,    2,    4,    4,    4,    6,    6,
    1,    2,    0,    1,    2,    2,    2,    1,    1,    4,
   10,    9,    0,    5,    0,    8,    2,    2,    4,    6,
    5,    1,    1,    2,    4,    1,    1,    1,    1,    3,
    7,    5,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    3,    3,    3,
    1,    2,    1,    3,    1,    1,    1,    1,    3,    1,
    3,    1,    2,    3,    3,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   56,   57,
   58,   59,   60,   62,    0,   61,    0,   42,    0,   43,
    0,    0,    0,    0,    0,    0,    0,    9,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   21,    0,    0,    0,   28,   29,   45,    4,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   86,   87,
   75,   78,    0,    0,    0,    0,    0,    0,   38,   37,
    0,   71,   73,    0,   82,    0,   24,   22,    7,    8,
   25,   26,   27,    0,    0,   46,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   83,    0,
    0,   77,   72,    0,    0,   92,   90,   93,   91,   94,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   30,   39,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   74,   84,   95,    0,    0,    0,    0,   70,   68,
   69,   81,    0,    0,   41,    0,    0,    0,    0,    0,
    0,   40,   19,   55,   54,   53,    0,   20,    0,    0,
    0,    0,    0,    0,    0,   36,    0,    0,    0,    0,
    0,    0,   31,   51,    0,    0,    0,    0,    0,    0,
    0,   34,    0,    0,   32,
};
final static short yydgoto[] = {                          2,
    3,    4,   15,   19,   24,   25,   38,   39,   40,   97,
   88,   89,  149,   41,   68,   43,   44,   45,   46,   70,
  169,  173,   20,   21,   90,  157,   71,   72,   73,   74,
   75,  114,
};
final static short yysindex[] = {                      -245,
 -255,    0, -245,    0, -232,    0, -244, -148,    0,    0,
    0,    0,    0,    0, -218,    0,  -63,    0, -200,    0,
 -187,    0, -227, -190,  -73,  183, -172,    0, -251, -152,
 -140, -130, -115, -103, -139, -160, -127,  -28, -129, -125,
    0, -123, -121, -110,    0,    0,    0,    0, -119, -168,
  -83,  -91,  -90,  -81,  -77,  -71, -119, -289,    0,    0,
    0,    0, -119, -252, -119,  149, -106,    0,    0,    0,
 -126,    0,    0,  -70,    0, -119,    0,    0,    0,    0,
    0,    0,    0,  -80,  -75,    0,  149, -106,  -67,  -82,
  -62, -119, -252, -252,  -68,  -59,  149, -277,    0,  -80,
 -252,    0,    0,  121, -256,    0,    0,    0,    0,    0,
  -46, -252, -252, -252, -119, -252, -252, -252, -119, -233,
    0,    0, -119, -248, -106, -238, -238, -168,  -57,  -18,
 -189,    0,    0,    0, -126, -126, -238,  -70,    0,    0,
    0,    0,  -16,  -82,    0,  -41,  -58, -239,  -44,  -28,
  -28,    0,    0,    0,    0,    0,  -39,    0, -201,  -65,
 -239,   -3,  -11,  -26,   10,    0,  -17,   -7,   24,  -57,
 -119,   28,    0,    0, -225,  -28,   35,  -55,  -28,   38,
  -47,    0,   43,   10,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  315,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -97,   50,    0,   53,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   58,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   31,   32,   33,   34,   36,    0,  105,    0,    0,
    0,    0,    0,    0,    0,   45,   54,  -27,    0,    0,
   39,    0,    0,  -54,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -5,    0,    0, -219, -150,    0, -215,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   17,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   55,   56,   60,    0,    0,    0,
    0,    0,    0,    0,   61,   83,  127,  120,    0,    0,
    0,    0,    0, -149,    0,    0,    0,    0,    0,   53,
   53,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -138,    0,   42,    0,  -36,    0,
    0,    0,    0,    0,    0,   53,    0,    0,   53,    0,
    0,    0,    0, -138,    0,
};
final static short yygindex[] = {                         0,
    0,  348,    0,    0,    0,    0, -146,    0,    0,  -29,
  -34,  -79,  184,  -37,  -25,  323,    0,    0,    0,  319,
  189,    0,  350,  369,  255,  218,  -12,  -56,    0,  266,
  -60,    0,
};
final static int YYTABLESIZE=446;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         42,
   78,   67,   99,  159,  160,  100,   66,  103,   50,   84,
  115,    1,   42,   51,   67,   59,   60,    5,  154,   66,
   87,  130,   98,   61,   62,    7,   86,   61,   62,  178,
  105,  115,  181,   49,    8,  104,  155,  156,  102,   64,
   63,  120,  133,   64,  146,  101,   50,   17,  147,   65,
  145,   51,  112,  113,  115,   77,   29,  125,  142,  139,
  140,  141,  115,  126,  127,  143,   22,  102,  102,  162,
   23,  131,   35,  177,   26,  102,   36,   48,   37,   48,
   27,   47,   48,   47,  137,   48,  102,  102,  102,   84,
  102,  102,  102,   87,   87,   59,   60,   58,   87,  135,
  136,  112,  113,   59,   60,   52,   86,   61,   62,  132,
    9,   10,   11,   12,   13,   61,   62,   53,   33,   33,
   63,   78,   78,   64,   42,   42,   14,   54,   63,   65,
   33,   64,   33,   42,   42,   33,  175,   65,   84,   33,
   78,   33,   55,   78,   59,   60,   49,   50,   49,   50,
   42,   49,   42,   42,   56,   42,   61,   62,   57,   10,
   10,   10,   10,   10,   10,   10,  116,  117,  118,   63,
   76,   79,   64,    6,   91,   80,   10,   81,   65,   82,
   10,  115,   10,   28,   29,   30,   31,   32,   33,   34,
   83,   77,   29,   92,   93,    9,   10,   11,   12,   13,
   35,   77,   29,   94,   36,  163,   37,   95,   35,   77,
   29,   18,   36,   96,   37,  180,  119,   50,   35,  123,
   35,   35,   36,  183,   37,  121,   35,  128,   77,   29,
   36,  122,   37,   80,   35,  124,  129,   35,  153,  134,
  148,   35,   80,   35,   80,   35,   80,   80,  150,   36,
  151,   37,  158,   77,   77,   77,   77,  152,   77,   89,
   89,   77,  161,   77,   77,   77,   77,   77,  166,   77,
  165,   77,  167,   77,   77,   76,   76,   76,   76,  168,
   76,   88,   88,   76,  170,   76,   76,   76,   76,   76,
  171,   76,  172,   76,  176,   76,   76,   76,   76,   76,
   76,  179,   76,   76,   76,   76,  182,   76,   76,   76,
   76,   76,  184,   76,    1,   76,   44,   76,   76,   67,
   67,   67,   67,   23,   67,   67,   67,   67,    5,   67,
   67,   12,   11,   13,   14,   67,   15,   67,   52,   67,
   67,   65,   65,   65,   65,   64,   65,   65,   65,   65,
    6,   65,   65,  174,   63,   18,   16,   65,   69,   65,
   17,   65,   65,   66,   66,   66,   66,   85,   66,   66,
   66,   66,  185,   66,   66,   47,   16,  144,  164,   66,
  138,   66,    0,   66,   66,   76,   76,   76,   76,    0,
   76,   88,   88,   76,    0,   76,   76,   76,   76,   76,
    0,  106,  107,  108,  109,   76,  110,   79,    0,  111,
    0,  112,  113,   85,   85,    0,   79,    0,   79,  132,
   79,   79,    0,   85,    0,   85,    0,   85,   85,  106,
  107,  108,  109,    0,  110,    0,    0,  111,    0,  112,
  113,    9,   10,   11,   12,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         25,
   38,   36,   63,  150,  151,  258,   36,   64,  298,  258,
  288,  257,   38,  303,   49,  264,  265,  273,  258,   49,
   50,  299,   57,  276,  277,  258,  275,  276,  277,  176,
   65,  288,  179,  285,  279,   65,  276,  277,   64,  292,
  289,   76,  299,  292,  124,  298,  298,  266,  128,  298,
  299,  303,  291,  292,  288,  257,  258,   92,  119,  116,
  117,  118,  288,   93,   94,  299,  267,   93,   94,  271,
  258,  101,  274,  299,  302,  101,  278,  297,  280,  299,
  271,  297,  302,  299,  114,  258,  112,  113,  114,  258,
  116,  117,  118,  123,  124,  264,  265,  258,  128,  112,
  113,  291,  292,  264,  265,  258,  275,  276,  277,  299,
  259,  260,  261,  262,  263,  276,  277,  258,  257,  258,
  289,  159,  160,  292,  150,  151,  275,  258,  289,  298,
  269,  292,  271,  159,  160,  274,  171,  298,  258,  278,
  178,  280,  258,  181,  264,  265,  297,  297,  299,  299,
  176,  302,  178,  179,  258,  181,  276,  277,  298,  257,
  258,  259,  260,  261,  262,  263,  293,  294,  295,  289,
  298,  301,  292,  271,  258,  301,  274,  301,  298,  301,
  278,  288,  280,  257,  258,  259,  260,  261,  262,  263,
  301,  257,  258,  285,  285,  259,  260,  261,  262,  263,
  274,  257,  258,  285,  278,  271,  280,  285,  274,  257,
  258,  275,  278,  285,  280,  271,  287,  298,  274,  302,
  257,  258,  278,  271,  280,  301,  274,  296,  257,  258,
  278,  299,  280,  288,  271,  298,  296,  274,  297,  286,
  298,  278,  297,  280,  299,  274,  301,  302,  267,  278,
  267,  280,  297,  281,  282,  283,  284,  299,  286,  287,
  288,  289,  302,  291,  292,  293,  294,  295,  280,  297,
  274,  299,  299,  301,  302,  281,  282,  283,  284,  270,
  286,  287,  288,  289,  302,  291,  292,  293,  294,  295,
  298,  297,  269,  299,  267,  301,  302,  281,  282,  283,
  284,  267,  286,  287,  288,  289,  269,  291,  292,  293,
  294,  295,  270,  297,    0,  299,  267,  301,  302,  281,
  282,  283,  284,  271,  286,  287,  288,  289,  271,  291,
  292,  301,  301,  301,  301,  297,  301,  299,  297,  301,
  302,  281,  282,  283,  284,  301,  286,  287,  288,  289,
    3,  291,  292,  170,  301,  301,  301,  297,   36,  299,
  301,  301,  302,  281,  282,  283,  284,   49,  286,  287,
  288,  289,  184,  291,  292,   26,    8,  123,  161,  297,
  115,  299,   -1,  301,  302,  281,  282,  283,  284,   -1,
  286,  287,  288,  289,   -1,  291,  292,  293,  294,  295,
   -1,  281,  282,  283,  284,  301,  286,  288,   -1,  289,
   -1,  291,  292,  287,  288,   -1,  297,   -1,  299,  299,
  301,  302,   -1,  297,   -1,  299,   -1,  301,  302,  281,
  282,  283,  284,   -1,  286,   -1,   -1,  289,   -1,  291,
  292,  259,  260,  261,  262,  263,
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

//#line 293 "Breezy.yacc"

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
//#line 492 "Parser.java"
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
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 39:
//#line 130 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                                                yyval.obj = ba.typeTrack.getType(val_peek(3));}
break;
case 40:
//#line 137 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, val_peek(1).sval);}
break;
case 41:
//#line 138 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, null);}
break;
case 42:
//#line 142 "Breezy.yacc"
{yyval.sval = "";}
break;
case 43:
//#line 143 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 44:
//#line 146 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 45:
//#line 147 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 46:
//#line 150 "Breezy.yacc"
{yyval.sval = "";}
break;
case 47:
//#line 151 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 48:
//#line 154 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 49:
//#line 155 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 50:
//#line 156 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 51:
//#line 159 "Breezy.yacc"
{yyval.sval = "(" + val_peek(5).sval + "," + val_peek(3).sval + ")" + val_peek(0).sval;}
break;
case 52:
//#line 160 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 53:
//#line 163 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 54:
//#line 164 "Breezy.yacc"
{yyval.ival = val_peek(0).ival;}
break;
case 55:
//#line 165 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 56:
//#line 169 "Breezy.yacc"
{yyval.sval = "boolean ";}
break;
case 57:
//#line 170 "Breezy.yacc"
{yyval.sval = "String ";}
break;
case 58:
//#line 171 "Breezy.yacc"
{yyval.sval = "double ";}
break;
case 59:
//#line 172 "Breezy.yacc"
{yyval.sval = "ArrayList ";}
break;
case 60:
//#line 173 "Breezy.yacc"
{yyval.sval = "HashMap ";}
break;
case 61:
//#line 176 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 62:
//#line 177 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 63:
//#line 180 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.line = val_peek(0).line;}
break;
case 64:
//#line 182 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.line = val_peek(0).line;}
break;
case 65:
//#line 186 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 66:
//#line 190 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 67:
//#line 194 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 68:
//#line 198 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 69:
//#line 202 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 70:
//#line 206 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 71:
//#line 210 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 72:
//#line 215 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval; 
                                        ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 73:
//#line 219 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 74:
//#line 224 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 75:
//#line 227 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 76:
//#line 230 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0));
                                                    yyval.line = val_peek(0).line;}
break;
case 77:
//#line 233 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 78:
//#line 236 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 79:
//#line 241 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                         yyval.obj = val_peek(2).obj;
                                                            yyval.line = val_peek(2).line;}
break;
case 80:
//#line 244 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                                            yyval.obj = val_peek(0).obj;
                                                            yyval.line = val_peek(0).line;}
break;
case 81:
//#line 249 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;  
                                                            yyval.obj = val_peek(2).obj;
                                                            yyval.line = val_peek(2).line; }
break;
case 82:
//#line 252 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                                                    yyval.obj = val_peek(0).obj;
                                                                    yyval.line = val_peek(0).line;}
break;
case 83:
//#line 257 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; 
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 84:
//#line 260 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; 
                                                yyval.obj = val_peek(1).obj;
                                                yyval.line = val_peek(2).line;}
break;
case 85:
//#line 263 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + val_peek(0).sval;
                                                         ba.typeTrack.assertNumberType(val_peek(2),val_peek(0),val_peek(1));
                                                         yyval.obj = "boolean";
                                                        yyval.line = val_peek(2).line;}
break;
case 86:
//#line 267 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 87:
//#line 269 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 88:
//#line 271 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                            val_peek(0).obj = ba.typeTrack.getType(val_peek(0));
                                            ba.typeTrack.assertBoolType(val_peek(0));
                                            yyval.obj = ba.typeTrack.getType(val_peek(0));
                                                yyval.line = val_peek(0).line; }
break;
case 89:
//#line 276 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                val_peek(0).obj = ba.typeTrack.getType(val_peek(0));
                                                ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.obj = ba.typeTrack.getType(val_peek(0));
                                                yyval.line = val_peek(0).line;}
break;
case 90:
//#line 283 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 91:
//#line 284 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 92:
//#line 285 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 93:
//#line 286 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 94:
//#line 287 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 95:
//#line 288 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1087 "Parser.java"
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
