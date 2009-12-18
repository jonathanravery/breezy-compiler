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
   16,   16,    4,    4,   23,   23,   13,   13,   25,   25,
   25,   25,   24,   24,   24,   24,   24,    3,    3,   16,
   16,   20,   20,   20,   10,   10,   26,   26,   26,   11,
   11,   11,   27,   27,   27,   27,   28,   28,   29,   29,
   29,   29,   12,   12,   30,   30,   31,   31,   31,   31,
   31,   31,   31,   32,   32,   32,   32,   32,   32,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    3,    3,    2,    0,
    2,    2,    2,    2,    2,    4,    4,    4,    6,    6,
    1,    2,    0,    1,    1,    1,    1,    1,    1,    4,
   10,    9,    0,    5,    0,    8,    3,    2,    2,    5,
    7,    6,    1,    1,    2,    4,    1,    1,    1,    1,
    1,    3,    1,    1,    1,    1,    1,    1,    1,    7,
    6,    1,    1,    1,    3,    1,    1,    1,    1,    3,
    3,    1,    3,    3,    3,    1,    2,    1,    3,    1,
    1,    1,    3,    1,    3,    1,    2,    3,    3,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   53,   54,
   55,   56,   57,   59,    0,   58,    0,   43,    0,   44,
    0,    0,    0,    0,    0,    0,    0,    9,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   21,   25,   26,   27,   28,   29,   46,    4,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   90,   91,
   80,   67,    0,    0,    0,    0,    0,    0,    0,   39,
    0,   66,    0,   76,   78,    0,   86,    0,   24,   22,
    7,    8,    0,    0,    0,   47,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   87,    0,    0,   82,   77,    0,    0,    0,   96,
   94,   97,   95,   98,    0,    0,    0,    0,    0,   37,
    0,    0,    0,    0,    0,   30,    0,    0,    0,    0,
    0,    0,   68,    0,    0,    0,    0,    0,   79,   88,
   65,   99,    0,    0,    0,    0,   75,   73,   74,   85,
    0,   40,    0,    0,    0,    0,    0,    0,    0,   42,
    0,   19,   20,    0,    0,   41,    0,    0,    0,   36,
    0,    0,    0,    0,   31,    0,    0,    0,    0,    0,
    0,    0,   34,    0,    0,   32,
};
final static short yydgoto[] = {                          2,
    3,    4,   15,   19,   24,   25,   38,   39,   40,   87,
   99,   89,   90,   41,   42,   43,   44,   45,   46,   71,
  172,  175,   20,   21,   91,   72,   73,   74,   75,   76,
   77,  118,
};
final static short yysindex[] = {                      -249,
 -262,    0, -249,    0, -241,    0, -244, -151,    0,    0,
    0,    0,    0,    0, -239,    0,  -37,    0, -228,    0,
 -209,    0, -247, -203,   -8,  290, -180,    0, -200, -163,
 -159, -152, -133, -130, -155,  -86, -149,   36, -167, -154,
    0,    0,    0,    0,    0,    0,    0,    0,  -57,  -94,
 -112, -135, -134, -132, -111, -109,  -71, -201,    0,    0,
    0,    0,  -71, -252,  -71, -118,  250, -129,    0,    0,
 -124,    0, -218,    0,    0, -107,    0,  -71,    0,    0,
    0,    0, -110,    0, -116,    0, -118,  250, -129, -103,
 -105, -106,  -71, -254, -252,  -81,  -67, -110,  250, -278,
    0,    0, -110, -252,    0,    0,  229, -263, -254,    0,
    0,    0,    0,    0,  -84, -252, -252, -252,  -71,    0,
 -252, -252, -252,  -71, -255,    0,  -91,  -57, -185, -129,
 -110, -118,    0, -175,  -94,  -94,  -36, -235,    0,    0,
    0,    0, -218, -218, -175, -107,    0,    0,    0,    0,
  -27,    0, -105,  -65,  -42,  -63,  -39,   36,   36,    0,
  -34,    0,    0, -226,    3,    0,  -10,    4,   10,    0,
   -2,   19,  -71,   30,    0, -246,   36,   31,   11,   36,
   33,   21,    0,   20,   10,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  304,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -15,   38,    0,   35,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   40,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    7,   12,   14,   16,   26,    0,  213,    0,    0,
    0,    0,    0,    0,    0,   32,   34,   43, -126,    0,
    0,    0,  147,    0,    0,  -26,    0,    0,    0,    0,
    0,    0,   37,   59,    0,    0, -290, -179, -113,    0,
 -232,    0,    0,    0,    0,    0,    0,   81,    0,    0,
  103,    0,  125,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   48,
  246,   54,    0,   56,    0,    0,    0,    0,    0,    0,
    0,    0,  169,  191,  -88,  228,    0,    0,    0,    0,
    0,    0, -164,    0,    0,    0,    0,   35,   35,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -41,    0,
    0,   29,    0,    0,    0,    0,   35,    0,    0,   35,
    0,    0,    0,    0,  -41,    0,
};
final static short yygindex[] = {                         0,
    0,  309,    0,    0,    0,    0, -139,    0,    0,  -33,
    1,  -31,  -14,  -38,  -35,  286,    0,    0,    0,  288,
  174,    0,  340,  363,  249,  270,   23,  -51,    0,  262,
  -61,    0,
};
final static int YYTABLESIZE=553;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         80,
   69,  102,   66,  131,   68,  103,   50,    1,   50,  119,
    5,   50,  106,   84,   84,   66,    7,   68,  164,  165,
  137,  101,   62,   61,  119,  100,   17,  101,  105,  101,
   79,   29,  119,  108,    8,  140,   67,  179,   22,   64,
  182,  119,  101,  151,  167,  104,  125,   35,   23,   67,
   88,   36,  178,   37,   26,  116,  117,  101,  133,  105,
  132,  130,  150,  139,   48,  107,   48,   27,  105,  147,
  148,  149,   83,  133,  121,  122,  123,   48,   59,   60,
  105,  105,  105,  101,   49,  105,  105,  105,  101,   86,
   61,   62,   84,   84,   52,  134,   50,   50,   53,   84,
   84,   51,   51,   63,  138,   54,   64,    9,   10,   11,
   12,   13,   65,  154,  155,  116,  117,   49,  145,   49,
  156,  157,   49,   14,   55,   80,   80,   56,   88,   88,
   38,   38,   52,   81,   52,   88,   88,  101,  143,  144,
   80,  176,   57,   80,   38,   92,   82,   38,   78,   93,
   94,   38,   95,   38,   82,   82,   82,   82,  119,   82,
   93,   93,   82,   83,   68,   82,   82,   82,   82,   59,
   60,   58,  109,   96,   68,   97,  120,   59,   60,  124,
   86,   61,   62,   51,  126,   51,   98,   50,   51,   61,
   62,  129,   59,   60,   63,  127,  128,   64,   89,   89,
   83,  142,   63,   65,   61,   64,   59,   60,   89,  152,
   89,   65,   89,   89,  135,   33,   33,   63,   61,   62,
   64,    9,   10,   11,   12,   13,   65,   33,  136,   33,
  158,   63,   33,  162,   64,  160,   33,   18,   33,  159,
   65,   10,   10,   10,   10,   10,   10,   10,   28,   29,
   30,   31,   32,   33,   34,    6,  161,  163,   10,   79,
   29,   84,   10,  169,   10,   35,  166,   79,   29,   36,
   84,   37,   84,  168,   84,   84,   35,   79,   29,  171,
   36,  181,   37,  170,   35,   35,   35,  174,   36,  185,
   37,  184,   79,   29,   35,  173,  177,  180,   36,   35,
   37,  183,   35,    1,   45,   23,   35,   12,   35,   35,
    5,    6,   11,   36,   13,   37,   14,   81,   81,   81,
   81,   70,   81,   92,   92,   81,   15,   69,   81,   81,
   81,   81,   62,   69,   64,   69,   85,   69,   69,   82,
   82,   82,   82,   63,   82,   93,   93,   82,   18,   68,
   82,   82,   82,   82,   16,   68,   17,   68,  186,   68,
   68,   81,   81,   81,   81,   47,   81,   92,   92,   81,
   16,   81,   81,   81,   81,   81,  153,   92,  141,   81,
  146,   92,   92,   82,   82,   82,   82,    0,   82,   93,
   93,   82,    0,   82,   82,   82,   82,   82,    0,   93,
    0,   82,    0,   93,   93,   81,   81,   81,   81,    0,
   81,   81,   81,   81,    0,   81,   81,   81,   81,   81,
    0,   81,    0,   81,    0,   81,   81,   72,   72,   72,
   72,    0,   72,   72,   72,   72,    0,   72,   72,    0,
    0,    0,    0,   72,    0,   72,    0,   72,   72,   70,
   70,   70,   70,    0,   70,   70,   70,   70,    0,   70,
   70,    0,    0,    0,    0,   70,    0,   70,    0,   70,
   70,   71,   71,   71,   71,    0,   71,   71,   71,   71,
    0,   71,   71,    0,    0,    0,    0,   71,    0,   71,
    0,   71,   71,   81,   81,   81,   81,    0,   81,   92,
   92,   81,    0,   69,   81,   81,   81,   81,    0,  110,
  111,  112,  113,   69,  114,   83,    0,  115,    0,  116,
  117,    0,    0,    0,   83,    0,   83,  139,   83,   83,
  110,  111,  112,  113,    0,  114,   69,    0,  115,    0,
  116,  117,   69,    0,   69,    0,   69,   69,    9,   10,
   11,   12,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         38,
   36,   63,   36,  258,   36,  258,  297,  257,  299,  288,
  273,  302,   64,   49,   50,   49,  258,   49,  158,  159,
  299,   57,  277,  276,  288,   57,  266,   63,   64,   65,
  257,  258,  288,   65,  279,  299,   36,  177,  267,  292,
  180,  288,   78,  299,  271,  298,   78,  274,  258,   49,
   50,  278,  299,  280,  302,  291,  292,   93,   94,   95,
   94,   93,  124,  299,  297,   65,  299,  271,  104,  121,
  122,  123,  258,  109,  293,  294,  295,  258,  264,  265,
  116,  117,  118,  119,  285,  121,  122,  123,  124,  275,
  276,  277,  128,  129,  258,   95,  298,  298,  258,  135,
  136,  303,  303,  289,  104,  258,  292,  259,  260,  261,
  262,  263,  298,  299,  129,  291,  292,  297,  118,  299,
  135,  136,  302,  275,  258,  164,  165,  258,  128,  129,
  257,  258,  297,  301,  299,  135,  136,  173,  116,  117,
  179,  173,  298,  182,  271,  258,  301,  274,  298,  285,
  285,  278,  285,  280,  281,  282,  283,  284,  288,  286,
  287,  288,  289,  258,  291,  292,  293,  294,  295,  264,
  265,  258,  291,  285,  301,  285,  301,  264,  265,  287,
  275,  276,  277,  297,  301,  299,  258,  298,  302,  276,
  277,  298,  264,  265,  289,  299,  302,  292,  287,  288,
  258,  286,  289,  298,  276,  292,  264,  265,  297,  301,
  299,  298,  301,  302,  296,  257,  258,  289,  276,  277,
  292,  259,  260,  261,  262,  263,  298,  269,  296,  271,
  267,  289,  274,  297,  292,  301,  278,  275,  280,  267,
  298,  257,  258,  259,  260,  261,  262,  263,  257,  258,
  259,  260,  261,  262,  263,  271,  299,  297,  274,  257,
  258,  288,  278,  274,  280,  274,  301,  257,  258,  278,
  297,  280,  299,  271,  301,  302,  274,  257,  258,  270,
  278,  271,  280,  280,  274,  257,  258,  269,  278,  270,
  280,  271,  257,  258,  274,  298,  267,  267,  278,  271,
  280,  269,  274,    0,  267,  271,  278,  301,  280,  274,
  271,    3,  301,  278,  301,  280,  301,  281,  282,  283,
  284,   36,  286,  287,  288,  289,  301,  291,  292,  293,
  294,  295,  301,  297,  301,  299,   49,  301,  302,  281,
  282,  283,  284,  301,  286,  287,  288,  289,  301,  291,
  292,  293,  294,  295,  301,  297,  301,  299,  185,  301,
  302,  281,  282,  283,  284,   26,  286,  287,  288,  289,
    8,  291,  292,  293,  294,  295,  128,  297,  109,  299,
  119,  301,  302,  281,  282,  283,  284,   -1,  286,  287,
  288,  289,   -1,  291,  292,  293,  294,  295,   -1,  297,
   -1,  299,   -1,  301,  302,  281,  282,  283,  284,   -1,
  286,  287,  288,  289,   -1,  291,  292,  293,  294,  295,
   -1,  297,   -1,  299,   -1,  301,  302,  281,  282,  283,
  284,   -1,  286,  287,  288,  289,   -1,  291,  292,   -1,
   -1,   -1,   -1,  297,   -1,  299,   -1,  301,  302,  281,
  282,  283,  284,   -1,  286,  287,  288,  289,   -1,  291,
  292,   -1,   -1,   -1,   -1,  297,   -1,  299,   -1,  301,
  302,  281,  282,  283,  284,   -1,  286,  287,  288,  289,
   -1,  291,  292,   -1,   -1,   -1,   -1,  297,   -1,  299,
   -1,  301,  302,  281,  282,  283,  284,   -1,  286,  287,
  288,  289,   -1,  291,  292,  293,  294,  295,   -1,  281,
  282,  283,  284,  301,  286,  288,   -1,  289,   -1,  291,
  292,   -1,   -1,   -1,  297,   -1,  299,  299,  301,  302,
  281,  282,  283,  284,   -1,  286,  291,   -1,  289,   -1,
  291,  292,  297,   -1,  299,   -1,  301,  302,  259,  260,
  261,  262,  263,
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
"type_declaration_assignment : STRING IDENTIFIER EQUALS string_exp",
"type_declaration_assignment : NUMBER IDENTIFIER EQUALS arith_exp",
"type_declaration_assignment : BOOLEAN IDENTIFIER EQUALS bool_exp",
"type_declaration_assignment : ARRAY IDENTIFIER EQUALS LEFT_SQUARE_PAREN params RIGHT_SQUARE_PAREN",
"type_declaration_assignment : HASH IDENTIFIER EQUALS LEFT_SQUARE_PAREN params RIGHT_SQUARE_PAREN",
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
"params_ : string_exp",
"params_ : bool_exp",
"params_ : params_ COMMA params_",
"type : BOOLEAN",
"type : STRING",
"type : NUMBER",
"type : ARRAY",
"type : HASH",
"return_type : type",
"return_type : NOTHING",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
"exp : string_exp",
"exp : bool_exp",
"exp : arith_exp",
"string_exp : string_exp PLUS string_",
"string_exp : string_",
"string_ : QUOTE",
"string_ : function_declaration",
"string_ : IDENTIFIER",
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

//#line 232 "Breezy.yacc"

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
            e.printStackTrace();
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
{yyval.sval = "String " + val_peek(0).sval + "=\"\"";}
break;
case 12:
//#line 59 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval + "=false";}
break;
case 13:
//#line 60 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval + "=0";}
break;
case 14:
//#line 61 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval);}
break;
case 15:
//#line 62 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval);}
break;
case 16:
//#line 67 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 17:
//#line 68 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 18:
//#line 69 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 19:
//#line 70 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval);}
break;
case 20:
//#line 71 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(4).sval, val_peek(1).sval);}
break;
case 21:
//#line 75 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 22:
//#line 76 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 23:
//#line 77 "Breezy.yacc"
{yyval.sval = "";}
break;
case 24:
//#line 81 "Breezy.yacc"
{yyval.sval = "";}
break;
case 25:
//#line 82 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 26:
//#line 83 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 27:
//#line 84 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 28:
//#line 85 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 29:
//#line 86 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 30:
//#line 87 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 31:
//#line 93 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 32:
//#line 100 "Breezy.yacc"
{ yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 33:
//#line 101 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 34:
//#line 108 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 35:
//#line 109 "Breezy.yacc"
{yyval.sval = "";}
break;
case 36:
//#line 116 "Breezy.yacc"
{ yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 37:
//#line 119 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 38:
//#line 120 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval + ";\n";}
break;
case 39:
//#line 121 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval + ";\n";}
break;
case 40:
//#line 124 "Breezy.yacc"
{yyval.sval = val_peek(4).sval + "(" + val_peek(2).sval + ");\n";}
break;
case 41:
//#line 130 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 42:
//#line 131 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 43:
//#line 135 "Breezy.yacc"
{yyval.sval = "";}
break;
case 44:
//#line 136 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 45:
//#line 139 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 46:
//#line 140 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 47:
//#line 143 "Breezy.yacc"
{yyval.sval = "";}
break;
case 48:
//#line 144 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 49:
//#line 147 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 50:
//#line 148 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 51:
//#line 149 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 150 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 53:
//#line 154 "Breezy.yacc"
{yyval.sval = "boolean ";}
break;
case 54:
//#line 155 "Breezy.yacc"
{yyval.sval = "String ";}
break;
case 55:
//#line 156 "Breezy.yacc"
{yyval.sval = "double ";}
break;
case 56:
//#line 157 "Breezy.yacc"
{yyval.sval = "ArrayList ";}
break;
case 57:
//#line 158 "Breezy.yacc"
{yyval.sval = "HashMap ";}
break;
case 58:
//#line 161 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 59:
//#line 162 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 60:
//#line 166 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 61:
//#line 167 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 62:
//#line 170 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 63:
//#line 171 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 64:
//#line 172 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 65:
//#line 175 "Breezy.yacc"
{yyval.sval = val_peek(2).sval +  " + "  + val_peek(0).sval;}
break;
case 66:
//#line 176 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 67:
//#line 179 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 68:
//#line 180 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 69:
//#line 181 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 70:
//#line 184 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval; }
break;
case 71:
//#line 185 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval; }
break;
case 72:
//#line 186 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 73:
//#line 189 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval; }
break;
case 74:
//#line 190 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval; }
break;
case 75:
//#line 191 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval; }
break;
case 76:
//#line 192 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 77:
//#line 195 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval;}
break;
case 78:
//#line 196 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 79:
//#line 199 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 80:
//#line 200 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 81:
//#line 201 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 82:
//#line 202 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 83:
//#line 205 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 84:
//#line 206 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 85:
//#line 209 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 86:
//#line 210 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 87:
//#line 213 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; }
break;
case 88:
//#line 214 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 89:
//#line 215 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + val_peek(0).sval;}
break;
case 90:
//#line 216 "Breezy.yacc"
{yyval.sval = "true";}
break;
case 91:
//#line 217 "Breezy.yacc"
{yyval.sval = "false";}
break;
case 92:
//#line 218 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 93:
//#line 219 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 94:
//#line 222 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 95:
//#line 223 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 96:
//#line 224 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 97:
//#line 225 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 98:
//#line 226 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 99:
//#line 227 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1063 "Parser.java"
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
