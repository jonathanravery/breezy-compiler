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
   25,   25,   13,   13,   26,   26,   26,   24,   24,   24,
   24,   24,    3,    3,   20,   20,   10,   10,   10,   27,
   27,   27,   27,   28,   28,   29,   29,   29,   29,   29,
   11,   11,   30,   30,   31,   31,   31,   31,   31,   31,
   31,   32,   32,   32,   32,   32,   32,
};
final static short yylen[] = {                            2,
    1,    1,    1,    2,    2,   11,    2,    0,    3,    3,
    2,    0,    2,    2,    2,    2,    2,    4,    4,    4,
    6,    6,    1,    2,    0,    1,    2,    2,    2,    1,
    1,    4,   10,    9,    0,    5,    0,    8,    2,    2,
    4,    6,    5,    1,    1,    2,    4,    1,    1,    1,
    1,    3,    7,    5,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    3,
    3,    3,    1,    2,    1,    3,    1,    1,    1,    1,
    3,    1,    3,    1,    2,    3,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    0,    4,    0,    0,   58,
   59,   60,   61,   62,   64,    0,   63,    0,   44,    0,
   45,    0,    0,    0,    0,    0,    0,    0,   11,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   23,    0,    0,    0,   30,   31,   47,    6,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   88,
   89,   77,   80,    0,    0,    0,    0,    0,    0,   40,
   39,    0,   73,   75,    0,   84,    0,   26,   24,    9,
   10,   27,   28,   29,    0,    0,   48,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   85,
    0,    0,   79,   74,    0,    0,   94,   92,   95,   93,
   96,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   32,   41,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   76,   86,   97,    0,    0,    0,    0,   72,
   70,   71,   83,    0,    0,   43,    0,    0,    0,    0,
    0,    0,   42,   21,   57,   56,   55,    0,   22,    0,
    0,    0,    0,    0,    0,    0,   38,    0,    0,    0,
    0,    0,    0,   33,   53,    0,    0,    0,    0,    0,
    0,    0,   36,    0,    0,   34,
};
final static short yydgoto[] = {                          2,
    3,    4,   16,   20,   25,   26,   39,   40,   41,   98,
   89,   90,  150,   42,   69,   44,   45,   46,   47,   71,
  170,  174,   21,   22,   91,  158,   72,   73,   74,   75,
   76,  115,
};
final static short yysindex[] = {                      -238,
 -242,    0, -219,    0, -217, -242,    0, -235, -146,    0,
    0,    0,    0,    0,    0, -196,    0,  -96,    0, -220,
    0, -192,    0, -225, -190,  -54,  184, -172,    0, -263,
 -157, -153, -140, -126, -108, -141, -117, -122, -174, -203,
 -149,    0, -143, -124, -116,    0,    0,    0,    0, -103,
 -168,  -70,  -95,  -93,  -91,  -71,  -69, -103, -293,    0,
    0,    0,    0, -103, -243, -103,  150,  -73,    0,    0,
    0, -150,    0,    0,  -64,    0, -103,    0,    0,    0,
    0,    0,    0,    0,  -80,  -74,    0,  150,  -73,  -62,
  -63,  -57, -103, -243, -243,  -51,  -49,  150, -276,    0,
  -80, -243,    0,    0,  129, -274,    0,    0,    0,    0,
    0,  -38, -243, -243, -243, -103, -243, -243, -243, -103,
 -251,    0,    0, -103, -247,  -73, -180, -180, -168,  -43,
  -11, -189,    0,    0,    0, -150, -150, -180,  -64,    0,
    0,    0,    0,  -10,  -63,    0,  -35,  -22, -250,  -14,
 -174, -174,    0,    0,    0,    0,    0,   -9,    0, -200,
  -87, -250,   -5,    8,    4,   29,    0,   -1,   12,   46,
  -43, -103,   54,    0,    0, -232, -174,   56,  -46, -174,
   63,  -36,    0,   55,   29,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,  337,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -61,   73,    0,   70,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   71,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   42,   44,   53,   58,   61,    0,  113,    0,
    0,    0,    0,    0,    0,    0,   62,   64,  -21,    0,
    0,   47,    0,    0,  -48,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    3,    0,    0,  -66,  -20,    0,
 -224,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   25,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   66,   68,   75,    0,    0,
    0,    0,    0,    0,    0,   69,   91, -119,  128,    0,
    0,    0,    0,    0, -160,    0,    0,    0,    0,    0,
   70,   70,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -138,    0,   50,    0,  -28,
    0,    0,    0,    0,    0,    0,   70,    0,    0,   70,
    0,    0,    0,    0, -138,    0,
};
final static short yygindex[] = {                         0,
    0,  361,    0,    0,    0,    0,  -98,    0,    0,  -30,
  -34,  -79,  210,  -33,  -26,  347,    0,    0,    0,  335,
  201,    0,  360,  380,  267,  236,    9,  -56,    0,  287,
  -60,    0,
};
final static int YYTABLESIZE=447;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
    3,    5,   68,  100,   51,   79,   67,  155,  104,   52,
   85,  116,   43,  116,  101,   68,   60,   61,    1,   67,
   88,   50,  131,   99,  134,  156,  157,   87,   62,   63,
    5,  106,   62,   63,   51,  105,  116,    6,  103,   52,
    8,   64,  121,    9,   65,  147,   23,  144,   65,  148,
   66,  146,  160,  161,  102,  116,   78,   30,  126,  143,
  140,  141,  142,  127,  128,   24,  178,  103,  103,   18,
  163,  132,   49,   36,   49,  103,   27,   37,  179,   38,
   28,  182,   78,   30,  138,   49,  103,  103,  103,   85,
  103,  103,  103,   88,   88,   60,   61,   80,   88,   36,
   53,  113,  114,   37,   54,   38,   87,   62,   63,  133,
  113,  114,   10,   11,   12,   13,   14,   55,   35,   35,
   64,  136,  137,   65,   43,   43,   79,   79,   15,   66,
   35,   56,   35,   43,   43,   35,   52,  176,   52,   35,
   59,   35,  117,  118,  119,   79,   60,   61,   79,   57,
   43,   81,   43,   43,   85,   43,   58,   82,   62,   63,
   60,   61,   10,   11,   12,   13,   14,   87,   87,   78,
   30,   64,   62,   63,   65,   77,   83,   87,   19,   87,
   66,   87,   87,  164,   84,   64,   36,   92,   65,   93,
   37,   94,   38,   95,   66,   12,   12,   12,   12,   12,
   12,   12,   29,   30,   31,   32,   33,   34,   35,    8,
   78,   30,   12,   96,  116,   97,   12,   51,   12,   36,
   78,   30,  120,   37,  181,   38,  122,   36,   37,   37,
   50,   37,   50,   38,  184,   50,  123,   36,  124,   82,
  125,   37,   37,   38,  129,   37,  130,  135,   82,   37,
   82,   37,   82,   82,  149,  151,  152,    3,    5,   79,
   79,   79,   79,  153,   79,   91,   91,   79,  166,   79,
   79,   79,   79,   79,  154,   79,   51,   79,   51,   79,
   79,   51,  159,   78,   78,   78,   78,  167,   78,   90,
   90,   78,  162,   78,   78,   78,   78,   78,  169,   78,
  171,   78,  168,   78,   78,   78,   78,   78,   78,  172,
   78,   78,   78,   78,  173,   78,   78,   78,   78,   78,
  177,   78,  180,   78,  185,   78,   78,   69,   69,   69,
   69,  183,   69,   69,   69,   69,    1,   69,   69,   46,
   25,    7,   14,   69,   13,   69,   54,   69,   69,   67,
   67,   67,   67,   15,   67,   67,   67,   67,   16,   67,
   67,   17,   66,    7,   65,   67,   20,   67,   18,   67,
   67,   68,   68,   68,   68,   19,   68,   68,   68,   68,
  175,   68,   68,   70,   86,  186,   48,   68,   17,   68,
  145,   68,   68,   78,   78,   78,   78,  165,   78,   90,
   90,   78,  139,   78,   78,   78,   78,   78,    0,  107,
  108,  109,  110,   78,  111,   81,    0,  112,    0,  113,
  114,    0,    0,    0,   81,    0,   81,  133,   81,   81,
  107,  108,  109,  110,    0,  111,    0,    0,  112,    0,
  113,  114,   10,   11,   12,   13,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         26,
    0,    0,   37,   64,  298,   39,   37,  258,   65,  303,
  258,  288,   39,  288,  258,   50,  264,  265,  257,   50,
   51,  285,  299,   58,  299,  276,  277,  275,  276,  277,
  273,   66,  276,  277,  298,   66,  288,  257,   65,  303,
  258,  289,   77,  279,  292,  125,  267,  299,  292,  129,
  298,  299,  151,  152,  298,  288,  257,  258,   93,  120,
  117,  118,  119,   94,   95,  258,  299,   94,   95,  266,
  271,  102,  297,  274,  299,  102,  302,  278,  177,  280,
  271,  180,  257,  258,  115,  258,  113,  114,  115,  258,
  117,  118,  119,  124,  125,  264,  265,  301,  129,  274,
  258,  291,  292,  278,  258,  280,  275,  276,  277,  299,
  291,  292,  259,  260,  261,  262,  263,  258,  257,  258,
  289,  113,  114,  292,  151,  152,  160,  161,  275,  298,
  269,  258,  271,  160,  161,  274,  297,  172,  299,  278,
  258,  280,  293,  294,  295,  179,  264,  265,  182,  258,
  177,  301,  179,  180,  258,  182,  298,  301,  276,  277,
  264,  265,  259,  260,  261,  262,  263,  287,  288,  257,
  258,  289,  276,  277,  292,  298,  301,  297,  275,  299,
  298,  301,  302,  271,  301,  289,  274,  258,  292,  285,
  278,  285,  280,  285,  298,  257,  258,  259,  260,  261,
  262,  263,  257,  258,  259,  260,  261,  262,  263,  271,
  257,  258,  274,  285,  288,  285,  278,  298,  280,  274,
  257,  258,  287,  278,  271,  280,  301,  274,  257,  258,
  297,  278,  299,  280,  271,  302,  299,  274,  302,  288,
  298,  278,  271,  280,  296,  274,  296,  286,  297,  278,
  299,  280,  301,  302,  298,  267,  267,  257,  257,  281,
  282,  283,  284,  299,  286,  287,  288,  289,  274,  291,
  292,  293,  294,  295,  297,  297,  297,  299,  299,  301,
  302,  302,  297,  281,  282,  283,  284,  280,  286,  287,
  288,  289,  302,  291,  292,  293,  294,  295,  270,  297,
  302,  299,  299,  301,  302,  281,  282,  283,  284,  298,
  286,  287,  288,  289,  269,  291,  292,  293,  294,  295,
  267,  297,  267,  299,  270,  301,  302,  281,  282,  283,
  284,  269,  286,  287,  288,  289,    0,  291,  292,  267,
  271,  271,  301,  297,  301,  299,  297,  301,  302,  281,
  282,  283,  284,  301,  286,  287,  288,  289,  301,  291,
  292,  301,  301,    3,  301,  297,  301,  299,  301,  301,
  302,  281,  282,  283,  284,  301,  286,  287,  288,  289,
  171,  291,  292,   37,   50,  185,   27,  297,    9,  299,
  124,  301,  302,  281,  282,  283,  284,  162,  286,  287,
  288,  289,  116,  291,  292,  293,  294,  295,   -1,  281,
  282,  283,  284,  301,  286,  288,   -1,  289,   -1,  291,
  292,   -1,   -1,   -1,  297,   -1,  299,  299,  301,  302,
  281,  282,  283,  284,   -1,  286,   -1,   -1,  289,   -1,
  291,  292,  259,  260,  261,  262,  263,
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

//#line 309 "Breezy.yacc"

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
//#line 495 "Parser.java"
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
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6).sval,val_peek(4).sval,val_peek(2).sval,val_peek(0).sval,val_peek(8).line,Scope.GLOBAL.getName()); }
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
{yyval.sval = "";}
break;
case 13:
//#line 61 "Breezy.yacc"
{yyval.sval = "String " + val_peek(0).sval + "=\"\""; ba.addIdentifier(val_peek(0).sval,"string",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 14:
//#line 62 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(0).sval + "=false"; ba.addIdentifier(val_peek(0).sval,"boolean",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 15:
//#line 63 "Breezy.yacc"
{yyval.sval = "double " + val_peek(0).sval + "=0"; ba.addIdentifier(val_peek(0).sval,"number",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 16:
//#line 64 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(0).sval); ba.addIdentifier(val_peek(0).sval,"ArrayList",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 17:
//#line 65 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(0).sval); ba.addIdentifier(val_peek(0).sval,"HashMap",val_peek(0).line,Scope.LOCAL.getName());}
break;
case 18:
//#line 70 "Breezy.yacc"
{yyval.sval = "String " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertStringType(val_peek(0));
                                                                         ba.addIdentifier(val_peek(2).sval,"string",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 19:
//#line 73 "Breezy.yacc"
{yyval.sval = "double " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                        ba.typeTrack.assertNumberType(val_peek(0));
                                                                         ba.addIdentifier(val_peek(2).sval,"number",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 20:
//#line 76 "Breezy.yacc"
{yyval.sval = "boolean " + val_peek(2).sval + " = " + val_peek(0).sval;
                                                                          ba.addIdentifier(val_peek(2).sval,"boolean",val_peek(2).line,Scope.LOCAL.getName());}
break;
case 21:
//#line 79 "Breezy.yacc"
{yyval.sval = ba.createComplexType("ArrayList", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 22:
//#line 81 "Breezy.yacc"
{yyval.sval = ba.createComplexType("HashMap", val_peek(4).sval, val_peek(1).sval,val_peek(4).line,Scope.LOCAL.getName());}
break;
case 23:
//#line 85 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 24:
//#line 86 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 25:
//#line 87 "Breezy.yacc"
{yyval.sval = "";}
break;
case 26:
//#line 91 "Breezy.yacc"
{yyval.sval = "";}
break;
case 27:
//#line 92 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 28:
//#line 93 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 29:
//#line 94 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + ";\n";}
break;
case 30:
//#line 95 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 31:
//#line 96 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 32:
//#line 97 "Breezy.yacc"
{val_peek(3).obj = ba.typeTrack.getType(val_peek(3),Scope.LOCAL.getName());
                                                                        ba.typeTrack.assertSameType(val_peek(3),val_peek(1),val_peek(2));
                                                                        yyval.sval = val_peek(3).sval + "=" + val_peek(1).sval + ";\n" ;}
break;
case 33:
//#line 105 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(7).sval + "){\n" + val_peek(4).sval + "}\n" + val_peek(1).sval + "\n" + val_peek(0).sval;}
break;
case 34:
//#line 112 "Breezy.yacc"
{ yyval.sval = "else if(" + val_peek(6).sval + "){\n" + val_peek(3).sval + "}\n" + val_peek(0).sval + "\n";}
break;
case 35:
//#line 113 "Breezy.yacc"
{ yyval.sval = "";}
break;
case 36:
//#line 120 "Breezy.yacc"
{ yyval.sval = "else{\n" + val_peek(2).sval + "}\n";}
break;
case 37:
//#line 121 "Breezy.yacc"
{yyval.sval = "";}
break;
case 38:
//#line 128 "Breezy.yacc"
{ yyval.sval = "while( " + val_peek(5).sval + " ){\n" + val_peek(2).sval + "}\n";}
break;
case 39:
//#line 131 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval;}
break;
case 40:
//#line 132 "Breezy.yacc"
{yyval.sval = "return " +val_peek(0).sval;}
break;
case 41:
//#line 135 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
                                                            yyval.obj = ba.typeTrack.getType(val_peek(3), Scope.GLOBAL.getName());}
break;
case 42:
//#line 142 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, Scope.LOCAL.getName(), val_peek(1).sval);}
break;
case 43:
//#line 143 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(4).sval, val_peek(2).sval, Scope.LOCAL.getName(), null);}
break;
case 44:
//#line 147 "Breezy.yacc"
{yyval.sval = "";}
break;
case 45:
//#line 148 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 46:
//#line 151 "Breezy.yacc"
{ba.addIdentifier(val_peek(0).sval,val_peek(1).obj.toString(),val_peek(0).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 47:
//#line 153 "Breezy.yacc"
{ba.addIdentifier(val_peek(2).sval,val_peek(3).obj.toString(),val_peek(2).line,Scope.LOCAL.getName());
                                                            yyval.sval = val_peek(3).sval + " " + val_peek(2).sval + ", " + val_peek(0).sval;}
break;
case 48:
//#line 157 "Breezy.yacc"
{yyval.sval = "";}
break;
case 49:
//#line 158 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 50:
//#line 161 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 51:
//#line 162 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 52:
//#line 163 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 53:
//#line 166 "Breezy.yacc"
{yyval.sval = "(" + val_peek(5).sval + "," + val_peek(3).sval + ")" + val_peek(0).sval;}
break;
case 54:
//#line 167 "Breezy.yacc"
{yyval.sval = "(" + val_peek(3).sval + "," + val_peek(1).sval + ")";}
break;
case 55:
//#line 170 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 56:
//#line 171 "Breezy.yacc"
{yyval.ival = val_peek(0).ival;}
break;
case 57:
//#line 172 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 58:
//#line 176 "Breezy.yacc"
{yyval.sval = "boolean "; yyval.obj = "boolean";}
break;
case 59:
//#line 177 "Breezy.yacc"
{yyval.sval = "String "; yyval.obj = "string";}
break;
case 60:
//#line 178 "Breezy.yacc"
{yyval.sval = "double "; yyval.obj = "number";}
break;
case 61:
//#line 179 "Breezy.yacc"
{yyval.sval = "ArrayList "; yyval.obj = "ArrayList";}
break;
case 62:
//#line 180 "Breezy.yacc"
{yyval.sval = "HashMap "; yyval.obj = "HashMap";}
break;
case 63:
//#line 183 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 64:
//#line 184 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 65:
//#line 187 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.line = val_peek(0).line;
                                                yyval.obj = val_peek(0).obj;}
break;
case 66:
//#line 190 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                                yyval.line = val_peek(0).line;
                                                yyval.obj = val_peek(0).obj;}
break;
case 67:
//#line 195 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "+" + val_peek(0).sval;
                                              ba.typeTrack.assertNumberOrStringType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 68:
//#line 199 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 69:
//#line 203 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.line = val_peek(0).line;}
break;
case 70:
//#line 207 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 71:
//#line 211 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 72:
//#line 215 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " % " + val_peek(0).sval;
                                              ba.typeTrack.assertNumberType(val_peek(2),val_peek(0), val_peek(1));
                                                yyval.obj = val_peek(2).obj;
                                                yyval.line = val_peek(2).line; }
break;
case 73:
//#line 219 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 74:
//#line 224 "Breezy.yacc"
{ yyval.sval = " -"+ val_peek(0).sval; 
                                        ba.typeTrack.assertNumberType(val_peek(0));
                                        yyval.obj = val_peek(0).obj;
                                        yyval.line = val_peek(0).line;}
break;
case 75:
//#line 228 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;
                                    yyval.obj = val_peek(0).obj;
                                    yyval.line = val_peek(0).line;}
break;
case 76:
//#line 233 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) ";
                                                    yyval.obj = val_peek(1).obj;
                                                    yyval.line = val_peek(2).line; }
break;
case 77:
//#line 236 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line; }
break;
case 78:
//#line 239 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = ba.typeTrack.getType(val_peek(0), Scope.LOCAL.getName());
                                                    yyval.line = val_peek(0).line;}
break;
case 79:
//#line 242 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 80:
//#line 245 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval; 
                                                    yyval.obj = val_peek(0).obj;
                                                    yyval.line = val_peek(0).line;}
break;
case 81:
//#line 250 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " || " + val_peek(0).sval;
                                                         yyval.obj = val_peek(2).obj;
                                                            yyval.line = val_peek(2).line;}
break;
case 82:
//#line 253 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                                            yyval.obj = val_peek(0).obj;
                                                            yyval.line = val_peek(0).line;}
break;
case 83:
//#line 258 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " && " + val_peek(0).sval;  
                                                            yyval.obj = val_peek(2).obj;
                                                            yyval.line = val_peek(2).line; }
break;
case 84:
//#line 261 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                                                    yyval.obj = val_peek(0).obj;
                                                                    yyval.line = val_peek(0).line;}
break;
case 85:
//#line 266 "Breezy.yacc"
{yyval.sval = " !" + val_peek(0).sval; 
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(1).line;}
break;
case 86:
//#line 269 "Breezy.yacc"
{yyval.sval = " ( " + val_peek(1).sval + " ) "; 
                                                yyval.obj = val_peek(1).obj;
                                                yyval.line = val_peek(2).line;}
break;
case 87:
//#line 272 "Breezy.yacc"
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
case 88:
//#line 283 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 89:
//#line 285 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 90:
//#line 287 "Breezy.yacc"
{yyval.sval = val_peek(0).sval; 
                                            val_peek(0).obj = ba.typeTrack.getType(val_peek(0), Scope.GLOBAL.getName());
                                            ba.typeTrack.assertBoolType(val_peek(0));
                                            yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line; }
break;
case 91:
//#line 292 "Breezy.yacc"
{ yyval.sval = val_peek(0).sval;
                                                val_peek(0).obj = ba.typeTrack.getType(val_peek(0), Scope.GLOBAL.getName());
                                                ba.typeTrack.assertBoolType(val_peek(0));
                                                yyval.obj = val_peek(0).obj;
                                                yyval.line = val_peek(0).line;}
break;
case 92:
//#line 299 "Breezy.yacc"
{yyval.sval = "<";}
break;
case 93:
//#line 300 "Breezy.yacc"
{yyval.sval = ">";}
break;
case 94:
//#line 301 "Breezy.yacc"
{yyval.sval = "<=";}
break;
case 95:
//#line 302 "Breezy.yacc"
{yyval.sval = ">=";}
break;
case 96:
//#line 303 "Breezy.yacc"
{yyval.sval = "==";}
break;
case 97:
//#line 304 "Breezy.yacc"
{yyval.sval = "!=";}
break;
//#line 1111 "Parser.java"
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
