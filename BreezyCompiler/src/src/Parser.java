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
public final static short ACCEPTS=264;
public final static short BEGIN=265;
public final static short EACH=266;
public final static short ELSE=267;
public final static short ELSEIF=268;
public final static short END=269;
public final static short FOR=270;
public final static short FUNCTION=271;
public final static short IF=272;
public final static short NOTHING=273;
public final static short NUMERIC=274;
public final static short QUOTE=275;
public final static short RETURN=276;
public final static short RETURNS=277;
public final static short WHILE=278;
public final static short PLUS=279;
public final static short MINUS=280;
public final static short EQUAL=281;
public final static short REL_OP_LE=282;
public final static short REL_OP_LT=283;
public final static short REL_OP_GE=284;
public final static short REL_OP_GT=285;
public final static short EQUALS=286;
public final static short LOG_OP_EQUAL=287;
public final static short LOG_OP_AND=288;
public final static short LOG_OP_OR=289;
public final static short LOG_OP_NOT=290;
public final static short MUL=291;
public final static short DIV=292;
public final static short MOD=293;
public final static short LEFT_SQUARE_PAREN=294;
public final static short RIGHT_SQUARE_PAREN=295;
public final static short LPAREN=296;
public final static short RPAREN=297;
public final static short COLON=298;
public final static short SEMICOLON=299;
public final static short COMMA=300;
public final static short DOT=301;
public final static short TRUE=302;
public final static short FALSE=303;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    5,    5,    6,    6,    6,    6,
    6,    8,    8,    8,    8,    8,    9,    9,    9,    9,
   14,   14,    7,    7,    7,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   19,   21,   21,   22,
   22,   20,   18,   18,   18,   18,   17,   17,   17,   16,
    4,    4,    4,   13,   13,   13,   13,   13,   23,   23,
   23,   23,   23,    3,    3,   14,   14,   10,   10,   10,
   11,   11,   11,   24,   24,   24,   24,   25,   25,   26,
   26,   26,   26,   27,   12,   12,   12,   12,   12,   12,
   12,   28,   28,   28,   28,   28,   28,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    2,    0,    2,    2,    3,    3,
    0,    2,    2,    2,    2,    2,    4,    4,    4,    6,
    7,    6,    1,    2,    0,    1,    1,    1,    1,    1,
    1,    1,    2,    4,    4,    4,   10,    9,    0,    5,
    0,    8,    3,    3,    3,    2,    4,    4,    4,    5,
    1,    2,    4,    1,    1,    1,    1,    3,    1,    1,
    1,    1,    1,    1,    1,    7,    6,    1,    1,    3,
    3,    3,    1,    3,    3,    3,    1,    2,    1,    3,
    1,    1,    1,    1,    3,    3,    3,    2,    3,    1,
    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   59,   60,
   61,   62,   63,   65,    0,   64,    0,   51,    0,    0,
    0,    0,   52,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   16,    0,   26,
    0,    0,   81,    0,    0,    0,    0,    0,    0,    0,
    0,   28,   23,    0,   29,   30,   31,   32,    0,   77,
   79,    7,    8,   53,    0,    0,    0,    0,    4,    0,
    0,    0,    0,    0,    0,    0,   46,    0,    0,   83,
   78,    0,   24,    9,   10,    0,    0,   33,    0,    0,
    0,    0,    0,   90,   91,    0,    0,    0,   68,    0,
   69,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,   57,   56,   55,    0,    0,    0,   43,   44,   45,
    0,   80,    0,    0,   74,   75,   76,    0,    0,    0,
   94,   92,   95,   93,   96,    0,    0,    0,    0,    0,
    0,   49,   48,   47,   35,   34,   36,    0,    0,    0,
    0,    0,   89,   97,    0,    0,    0,    0,   20,   50,
    0,    0,    0,    0,    0,   22,    0,    0,    0,   21,
    0,    0,    0,   42,    0,    0,    0,    0,   37,    0,
    0,    0,    0,    0,    0,    0,   40,    0,    0,   38,
};
final static short yydgoto[] = {                          2,
    3,    4,   15,   19,   29,   30,   48,   31,   32,  100,
   51,   97,  115,   52,   53,   80,   55,   56,   57,   58,
  176,  179,   16,   59,   60,   61,    0,  137,
};
final static short yysindex[] = {                      -237,
 -219,    0, -237,    0, -200,    0, -198,  -43,    0,    0,
    0,    0,    0,    0, -143,    0,  188,    0, -258, -133,
  140,  207,    0, -122, -106, -103,  -94,  -92,  -96, -131,
 -124, -120,  -72, -109, -105,  -98,  -89,    0,  -68,    0,
 -220, -185,    0, -236, -104, -239, -239,   12,  -85,  -84,
 -255,    0,    0,    0,    0,    0,    0,    0, -130,    0,
    0,    0,    0,    0, -213, -242, -239,  -95,    0, -243,
 -158,  -64, -213, -290,  -78,  -76,    0, -213,  -93,    0,
    0, -188,    0,    0,    0, -239, -239,    0, -239, -239,
 -239, -213, -213,    0,    0,  173,  -81,  -93,    0,  -51,
    0,   -8, -158, -253,  -67,  -63, -269,   14, -175,    0,
    0,    0,    0,    0, -235,  -91, -129,    0,    0,    0,
   53,    0, -130, -130,    0,    0,    0,  -81,  129,   76,
    0,    0,    0,    0,    0,  -86, -239, -213, -213, -242,
 -283,    0,    0,    0,    0,    0,    0,  -58, -158, -155,
  -31,  -22,    0,    0,   -8,  -81,  -81,  -51,    0,    0,
  -55,  -52, -228,   12,   12,    0,  -50,  -87,  -74,    0,
  -20,  -24,  -12,    0,  -38,   -7, -213,    3,    0,   92,
   12,    8,  -45,   12,   -2,  -32,    0,   11,  -12,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  280,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -19,    0,    0,    0,    0,    0,    0,    0,    0,   22,
    0,    0,    0,   -4,    1,    4,   23,    0,    0,    0,
  -17,    0,    0,    0,    0,    0,    0,   33,    0,    0,
    0,    0,    0, -194,    0,    0,    0,    0,   48,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   27,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   24,    0,    0,   26,
    0,   30,    0,  138,  153,   42,    0,    0,    0,   69,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   87,  108,    0,    0,    0, -229,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  176, -191, -153,   35,    0,    0,
 -147,    0,    0,   22,   22,    0,    0,    0,    0,    0,
    0,    0, -100,    0,    0,    9,    0,    0,    0,    0,
   22,    0,    0,   22,    0,    0,    0,    0, -100,    0,
};
final static short yygindex[] = {                         0,
    0,  336,    0,    0,    0,    0,  -77,  310,  313,  -69,
  -44,  -65,  -99,    0,  -46,  -30,    0,    0,    0,    0,
  155,    0,   79,  124,  -35,    0,    0,    0,
};
final static int YYTABLESIZE=475;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
  107,   83,   82,  141,  109,   71,   21,  117,  118,  140,
   81,  159,  121,   77,  104,   98,  149,   54,   79,    1,
   96,   74,  102,   86,   87,  108,  128,  130,   96,  145,
  105,  106,   99,   96,   43,  101,   46,   75,   76,  110,
   46,   22,   71,   88,   79,  142,   92,   96,  129,  161,
  163,    5,   93,  125,  126,  127,   47,    7,   94,   95,
   43,  148,   27,   27,  149,   70,   46,   88,  167,   88,
  158,  149,  156,  157,   27,   71,   92,   27,    8,   27,
   72,   27,   93,   27,   83,   27,  168,  169,   94,   95,
   86,   87,  155,   96,   96,   20,   83,   83,   83,  111,
   33,   27,  111,  183,   83,   87,  186,   87,  122,  101,
   73,  180,  138,  139,  112,  113,  114,  112,  113,  114,
   17,   83,   83,  147,   23,   40,   41,   24,   25,   26,
   27,   28,   96,   54,   54,   34,   83,   54,   54,   83,
   42,  162,   43,   86,   44,   86,   45,   58,   46,   58,
   54,   35,   54,   54,   36,   54,   39,   39,  138,  139,
   89,   90,   91,   37,   47,   38,   39,  151,   39,   40,
   41,   39,   39,   39,   62,   39,   65,   39,   63,   39,
   66,  171,   40,   41,   42,   64,   43,   67,   44,   69,
   45,   78,   46,  116,  172,   39,   68,   42,  103,   43,
  154,   44,   71,   45,  150,   46,  138,  139,   47,  123,
  124,   40,   41,   84,   85,    9,   10,   11,   12,   13,
  119,   47,  120,  185,   40,   41,   42,  140,   43,   14,
   44,  143,   45,  164,   46,  144,  188,   11,   11,   42,
  160,   43,  165,   44,  149,   45,  166,   46,  170,    6,
   47,  173,   11,  174,   11,  175,   11,  177,   11,  178,
   11,   82,   82,   47,  187,   41,   41,  181,   40,   41,
   86,   87,  184,   82,   82,   82,   11,   41,  189,    1,
   41,   82,   41,   42,   41,   43,   41,   44,   41,   45,
   25,   46,   86,   87,   13,  131,  132,  133,  134,   12,
  135,    5,   14,  136,   41,   82,   82,   47,   82,   82,
   82,   82,  146,   82,   82,   82,   82,   82,   82,   82,
   68,   15,   19,   82,   17,   82,   73,   73,   18,   73,
   73,   73,   73,   70,   73,   73,   73,   73,    6,   49,
  138,  139,   50,  190,   73,    0,   73,   69,   83,  152,
   83,   83,   83,   83,    0,   83,    0,    0,   83,   83,
   83,   83,    0,  138,  139,   71,   71,   69,   71,   71,
   71,   71,  153,   71,   71,   71,   71,    0,    0,  138,
  139,    0,    0,   71,    0,   71,   72,   72,  182,   72,
   72,   72,   72,    0,   72,   72,   72,   72,   24,   25,
   26,   27,   28,    0,   72,    0,   72,   86,   87,    0,
  131,  132,  133,  134,    0,  135,   82,   82,  136,   82,
   82,   82,   82,    0,   82,  122,    0,   82,   82,   82,
   82,   81,   81,    0,   81,   81,   81,   81,    0,   81,
    0,    0,   81,   81,   81,   81,    9,   10,   11,   12,
   13,   86,   87,    0,  131,  132,  133,  134,    0,  135,
   18,    0,  136,   85,   85,    9,   10,   11,   12,   13,
    0,    0,   85,    0,   85,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         30,
   70,   48,   47,  103,   70,  296,  265,   73,  299,  279,
   46,  295,   78,   44,  258,  258,  300,   48,  258,  257,
   65,  258,   67,  279,  280,   70,   92,   93,   73,  299,
  274,  275,  275,   78,  274,   66,  280,  274,  275,   70,
  280,  300,  296,  299,  258,  299,  290,   92,   93,  149,
  150,  271,  296,   89,   90,   91,  296,  258,  302,  303,
  274,  297,  257,  258,  300,  286,  280,  297,  297,  299,
  140,  300,  138,  139,  269,  296,  290,  272,  277,  274,
  301,  276,  296,  278,  279,  280,  164,  165,  302,  303,
  279,  280,  137,  138,  139,   17,  291,  292,  293,  258,
   22,  296,  258,  181,  299,  297,  184,  299,  297,  140,
  296,  177,  288,  289,  273,  274,  275,  273,  274,  275,
  264,  168,  169,  299,  258,  257,  258,  259,  260,  261,
  262,  263,  177,  164,  165,  258,  183,  168,  169,  186,
  272,  297,  274,  297,  276,  299,  278,  295,  280,  297,
  181,  258,  183,  184,  258,  186,  257,  258,  288,  289,
  291,  292,  293,  258,  296,  258,  267,  297,  269,  257,
  258,  272,  269,  274,  299,  276,  286,  278,  299,  280,
  286,  269,  257,  258,  272,  258,  274,  286,  276,  258,
  278,  296,  280,  258,  269,  296,  286,  272,  294,  274,
  287,  276,  296,  278,  296,  280,  288,  289,  296,   86,
   87,  257,  258,  299,  299,  259,  260,  261,  262,  263,
  299,  296,  299,  269,  257,  258,  272,  279,  274,  273,
  276,  299,  278,  265,  280,  299,  269,  257,  258,  272,
  299,  274,  265,  276,  300,  278,  299,  280,  299,  269,
  296,  272,  272,  278,  274,  268,  276,  296,  278,  267,
  280,  279,  280,  296,  267,  257,  258,  265,  257,  258,
  279,  280,  265,  291,  292,  293,  296,  269,  268,    0,
  272,  299,  274,  272,  276,  274,  278,  276,  280,  278,
  269,  280,  279,  280,  299,  282,  283,  284,  285,  299,
  287,  269,  299,  290,  296,  279,  280,  296,  282,  283,
  284,  285,  299,  287,  288,  289,  290,  291,  292,  293,
  279,  299,  299,  297,  299,  299,  279,  280,  299,  282,
  283,  284,  285,  299,  287,  288,  289,  290,    3,   30,
  288,  289,   30,  189,  297,   -1,  299,  279,  280,  297,
  282,  283,  284,  285,   -1,  287,   -1,   -1,  290,  291,
  292,  293,   -1,  288,  289,  279,  280,  299,  282,  283,
  284,  285,  297,  287,  288,  289,  290,   -1,   -1,  288,
  289,   -1,   -1,  297,   -1,  299,  279,  280,  297,  282,
  283,  284,  285,   -1,  287,  288,  289,  290,  259,  260,
  261,  262,  263,   -1,  297,   -1,  299,  279,  280,   -1,
  282,  283,  284,  285,   -1,  287,  279,  280,  290,  282,
  283,  284,  285,   -1,  287,  297,   -1,  290,  291,  292,
  293,  279,  280,   -1,  282,  283,  284,  285,   -1,  287,
   -1,   -1,  290,  291,  292,  293,  259,  260,  261,  262,
  263,  279,  280,   -1,  282,  283,  284,  285,   -1,  287,
  273,   -1,  290,  288,  289,  259,  260,  261,  262,  263,
   -1,   -1,  297,   -1,  299,
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
"HASH","ACCEPTS","BEGIN","EACH","ELSE","ELSEIF","END","FOR","FUNCTION","IF",
"NOTHING","NUMERIC","QUOTE","RETURN","RETURNS","WHILE","PLUS","MINUS","EQUAL",
"REL_OP_LE","REL_OP_LT","REL_OP_GE","REL_OP_GT","EQUALS","LOG_OP_EQUAL",
"LOG_OP_AND","LOG_OP_OR","LOG_OP_NOT","MUL","DIV","MOD","LEFT_SQUARE_PAREN",
"RIGHT_SQUARE_PAREN","LPAREN","RPAREN","COLON","SEMICOLON","COMMA","DOT","TRUE",
"FALSE",
};
final static String yyrule[] = {
"$accept : start",
"start : program",
"program : method",
"program : program method",
"method : COMMENT FUNCTION IDENTIFIER RETURNS return_type ACCEPTS aparams BEGIN body END IDENTIFIER",
"body : declarations control_body",
"body :",
"declarations : type_declaration SEMICOLON",
"declarations : type_declaration_assignment SEMICOLON",
"declarations : declarations type_declaration SEMICOLON",
"declarations : declarations type_declaration_assignment SEMICOLON",
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
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
"control_body : statement",
"control_body : control_body statement",
"control_body :",
"statement : COMMENT",
"statement : function_declaration",
"statement : complex_type_method_invocation",
"statement : type_initialization",
"statement : return_statement",
"statement : if_statement",
"statement : while_loop",
"statement : arith_exp SEMICOLON",
"statement : IDENTIFIER EQUALS arith_exp SEMICOLON",
"statement : IDENTIFIER EQUALS string_exp SEMICOLON",
"statement : IDENTIFIER EQUALS bool_exp SEMICOLON",
"if_statement : IF LPAREN bool_exp RPAREN BEGIN control_body END IF else_if else",
"else_if : ELSEIF LPAREN bool_exp RPAREN BEGIN control_body END ELSEIF else_if",
"else_if :",
"else : ELSE BEGIN control_body END ELSE",
"else :",
"while_loop : WHILE LPAREN bool_exp RPAREN BEGIN control_body END WHILE",
"return_statement : RETURN IDENTIFIER SEMICOLON",
"return_statement : RETURN NUMERIC SEMICOLON",
"return_statement : RETURN QUOTE SEMICOLON",
"return_statement : RETURN function_declaration",
"type_initialization : IDENTIFIER EQUALS QUOTE SEMICOLON",
"type_initialization : IDENTIFIER EQUALS NUMERIC SEMICOLON",
"type_initialization : IDENTIFIER EQUALS IDENTIFIER SEMICOLON",
"function_declaration : IDENTIFIER LPAREN params RPAREN SEMICOLON",
"aparams : NOTHING",
"aparams : type IDENTIFIER",
"aparams : aparams COMMA type IDENTIFIER",
"params : IDENTIFIER",
"params : QUOTE",
"params : NUMERIC",
"params : NOTHING",
"params : params COMMA params",
"type : BOOLEAN",
"type : STRING",
"type : NUMBER",
"type : ARRAY",
"type : HASH",
"return_type : type",
"return_type : NOTHING",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
"string_exp : QUOTE",
"string_exp : function_declaration",
"string_exp : string_exp PLUS string_exp",
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
"condition : bool_exp",
"bool_exp : arith_exp rel_op arith_exp",
"bool_exp : bool_exp LOG_OP_OR bool_exp",
"bool_exp : bool_exp LOG_OP_AND bool_exp",
"bool_exp : LOG_OP_NOT bool_exp",
"bool_exp : LPAREN bool_exp RPAREN",
"bool_exp : TRUE",
"bool_exp : FALSE",
"rel_op : REL_OP_LT",
"rel_op : REL_OP_GT",
"rel_op : REL_OP_LE",
"rel_op : REL_OP_GE",
"rel_op : LOG_OP_EQUAL",
"rel_op : LOG_OP_NOT LOG_OP_EQUAL",
};

//#line 221 "Breezy.yacc"

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
//#line 498 "Parser.java"
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
//#line 22 "Breezy.yacc"
{ba.DumpFile(val_peek(0).sval);}
break;
case 2:
//#line 25 "Breezy.yacc"
{yyval.sval= val_peek(0).sval;}
break;
case 3:
//#line 26 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 4:
//#line 36 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6).sval,val_peek(4).sval,val_peek(2).sval,val_peek(0).sval); }
break;
case 5:
//#line 41 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 6:
//#line 42 "Breezy.yacc"
{yyval.sval = "";}
break;
case 7:
//#line 47 "Breezy.yacc"
{System.out.println("Used first in declarations"); yyval.sval = val_peek(1).sval + ";\n";}
break;
case 8:
//#line 48 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 9:
//#line 49 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 10:
//#line 50 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + ";\n";}
break;
case 11:
//#line 51 "Breezy.yacc"
{yyval.sval = "";}
break;
case 12:
//#line 55 "Breezy.yacc"
{yyval.sval = "String " + val_peek(0).sval;}
break;
case 13:
//#line 56 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval;}
break;
case 14:
//#line 57 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval;}
break;
case 15:
//#line 58 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval);}
break;
case 16:
//#line 59 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval);}
break;
case 17:
//#line 64 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 18:
//#line 65 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 19:
//#line 66 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval;}
break;
case 20:
//#line 67 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval);}
break;
case 21:
//#line 73 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 22:
//#line 74 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 23:
//#line 78 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 24:
//#line 79 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 25:
//#line 80 "Breezy.yacc"
{yyval.sval = "";}
break;
case 27:
//#line 85 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 28:
//#line 86 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 29:
//#line 87 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 30:
//#line 88 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 31:
//#line 89 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 32:
//#line 90 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 33:
//#line 91 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 34:
//#line 92 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 35:
//#line 93 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 36:
//#line 94 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 37:
//#line 100 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 38:
//#line 107 "Breezy.yacc"
{ yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 39:
//#line 108 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 40:
//#line 115 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 41:
//#line 116 "Breezy.yacc"
{yyval.sval = "";}
break;
case 42:
//#line 123 "Breezy.yacc"
{ yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 43:
//#line 126 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 44:
//#line 127 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 45:
//#line 128 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 46:
//#line 129 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval + "\n";}
break;
case 47:
//#line 132 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 48:
//#line 133 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 49:
//#line 134 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 50:
//#line 137 "Breezy.yacc"
{yyval.sval = val_peek(4).sval + "(" + val_peek(2).sval + ");\n";}
break;
case 51:
//#line 141 "Breezy.yacc"
{yyval.sval = "";}
break;
case 52:
//#line 142 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 53:
//#line 143 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + ", " + val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 54:
//#line 147 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 55:
//#line 148 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 56:
//#line 149 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 57:
//#line 150 "Breezy.yacc"
{yyval.sval = "";}
break;
case 58:
//#line 151 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 59:
//#line 155 "Breezy.yacc"
{yyval.sval = "boolean ";}
break;
case 60:
//#line 156 "Breezy.yacc"
{yyval.sval = "String ";}
break;
case 61:
//#line 157 "Breezy.yacc"
{yyval.sval = "double ";}
break;
case 62:
//#line 158 "Breezy.yacc"
{yyval.sval = "ArrayList ";}
break;
case 63:
//#line 159 "Breezy.yacc"
{yyval.sval = "HashMap ";}
break;
case 64:
//#line 162 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 65:
//#line 163 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 66:
//#line 167 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 67:
//#line 168 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 68:
//#line 171 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 69:
//#line 172 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 70:
//#line 173 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;}
break;
case 71:
//#line 176 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval; }
break;
case 72:
//#line 177 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval; }
break;
case 73:
//#line 178 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 74:
//#line 181 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval; }
break;
case 75:
//#line 182 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval; }
break;
case 76:
//#line 183 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval; }
break;
case 77:
//#line 184 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 78:
//#line 187 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval;}
break;
case 79:
//#line 188 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 80:
//#line 191 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 81:
//#line 192 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 82:
//#line 193 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 83:
//#line 194 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; }
break;
case 84:
//#line 197 "Breezy.yacc"
{yyval = val_peek(0);}
break;
case 85:
//#line 202 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + val_peek(1).sval + val_peek(0).sval;}
break;
case 86:
//#line 203 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval; }
break;
case 87:
//#line 204 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval; }
break;
case 88:
//#line 205 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; }
break;
case 89:
//#line 206 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; }
break;
case 90:
//#line 207 "Breezy.yacc"
{yyval.sval = "true";}
break;
case 91:
//#line 208 "Breezy.yacc"
{yyval.sval = "false";}
break;
case 92:
//#line 211 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 93:
//#line 212 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 94:
//#line 213 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 95:
//#line 214 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 96:
//#line 215 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 97:
//#line 216 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1031 "Parser.java"
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
