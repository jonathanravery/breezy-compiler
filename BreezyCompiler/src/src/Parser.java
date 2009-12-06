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
public final static short ACCEPTS=258;
public final static short BEGIN=259;
public final static short BOOLEAN=260;
public final static short EACH=261;
public final static short ELSE=262;
public final static short END=263;
public final static short FOR=264;
public final static short FUNCTION=265;
public final static short IF=266;
public final static short NOTHING=267;
public final static short NUMERIC=268;
public final static short QUOTE=269;
public final static short RETURN=270;
public final static short RETURNS=271;
public final static short WHILE=272;
public final static short PLUS=273;
public final static short MINUS=274;
public final static short MUL=275;
public final static short DIV=276;
public final static short MOD=277;
public final static short EQUAL=278;
public final static short REL_OP_LE=279;
public final static short REL_OP_LT=280;
public final static short REL_OP_GE=281;
public final static short REL_OP_GT=282;
public final static short EQUALS=283;
public final static short LOG_OP_EQUAL=284;
public final static short LOG_OP_AND=285;
public final static short LOG_OP_OR=286;
public final static short LOG_OP_NOT=287;
public final static short LPAREN=288;
public final static short RPAREN=289;
public final static short COLON=290;
public final static short SEMICOLON=291;
public final static short COMMA=292;
public final static short DOT=293;
public final static short TRUE=294;
public final static short FALSE=295;
public final static short IDENTIFIER=296;
public final static short ARRAY=297;
public final static short STRING=298;
public final static short NUMBER=299;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    5,    5,    6,    6,    6,    6,
    6,    6,    6,   13,   12,   12,   12,   12,   11,   11,
   11,    8,    7,    4,    4,    4,   15,   15,   15,   15,
   15,    3,    3,    3,    3,    9,   10,   10,   14,   16,
   16,
};
final static short yylen[] = {                            2,
    1,    1,    2,   11,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    8,    3,    3,    3,    2,    4,    4,
    4,    3,    5,    1,    2,    4,    1,    1,    1,    1,
    3,    1,    1,    1,    1,    3,    7,    6,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,   32,   35,
   33,   34,    0,    0,    0,    0,    0,   25,    0,    0,
    0,    0,    0,    0,    0,    0,    5,    7,    8,    9,
   10,   11,   12,   13,    0,    0,    0,    0,    0,   18,
    0,    0,    0,    0,    0,    0,    6,   26,   40,   41,
    0,   39,   16,   17,   15,    0,    0,    0,   30,   29,
   28,   27,    0,    0,   36,   22,    4,    0,   20,   19,
   21,    0,    0,    0,    0,   23,    0,    0,    0,    0,
   38,    0,    0,   37,   14,
};
final static short yydgoto[] = {                          2,
    3,    4,   25,   17,   26,   27,   28,   29,   30,   31,
   32,   33,   34,   51,   63,   52,
};
final static short yysindex[] = {                      -252,
 -262,    0, -252,    0, -281,    0, -239, -250,    0,    0,
    0,    0, -224, -242,    0, -277, -257,    0, -246, -250,
 -247, -222, -260, -228, -220, -259,    0,    0,    0,    0,
    0,    0,    0,    0, -216, -268, -255, -218, -221,    0,
 -205, -238, -215, -208, -206, -212,    0,    0,    0,    0,
 -203,    0,    0,    0,    0, -204, -201, -199,    0,    0,
    0,    0, -223, -200,    0,    0,    0, -166,    0,    0,
    0, -197, -238, -207, -246,    0, -196, -194, -217, -254,
    0, -193, -171,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,   99,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -237,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -189,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   98,   57,    0,   27,  -26,   81,    0,    0,    0,
    0,    0,    0,    0,    5,    0,
};
final static int YYTABLESIZE=103;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
    9,   19,    5,   46,    1,    9,   21,   10,   83,    9,
   22,   21,   10,    9,    7,   22,   10,    9,   18,   21,
   10,   24,   41,   22,   15,   49,   50,   42,   59,   60,
   61,    8,   43,   14,   20,   53,   23,   24,   11,   12,
   36,   23,   24,   11,   12,   37,   38,   11,   12,   23,
   24,   11,   12,   47,   24,   11,   12,   62,   35,   59,
   60,   61,   56,   57,   13,   72,   42,   44,   73,   55,
   16,   82,   54,   39,   73,   45,   35,   77,   79,   48,
   64,   78,   65,   67,   66,   68,   69,   74,   62,   70,
   58,   71,   75,   76,   85,   73,   81,   84,    1,   31,
    6,   80,   40,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         26,
  260,  259,  265,  263,  257,  260,  266,  267,  263,  260,
  270,  266,  267,  260,  296,  270,  267,  260,  296,  266,
  267,  259,  283,  270,  267,  294,  295,  288,  267,  268,
  269,  271,  293,  258,  292,  291,  296,  297,  298,  299,
  288,  296,  297,  298,  299,  268,  269,  298,  299,  296,
  297,  298,  299,   80,  292,  298,  299,  296,  296,  267,
  268,  269,  268,  269,    8,  289,  288,  296,  292,  291,
   14,  289,  291,  296,  292,  296,   20,   73,   74,  296,
  296,  289,  291,  296,  291,  289,  291,  288,  296,  291,
  296,  291,  259,  291,  266,  292,  291,  291,    0,  289,
    3,   75,   22,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=299;
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
null,null,null,"COMMENT","ACCEPTS","BEGIN","BOOLEAN","EACH","ELSE","END","FOR",
"FUNCTION","IF","NOTHING","NUMERIC","QUOTE","RETURN","RETURNS","WHILE","PLUS",
"MINUS","MUL","DIV","MOD","EQUAL","REL_OP_LE","REL_OP_LT","REL_OP_GE",
"REL_OP_GT","EQUALS","LOG_OP_EQUAL","LOG_OP_AND","LOG_OP_OR","LOG_OP_NOT",
"LPAREN","RPAREN","COLON","SEMICOLON","COMMA","DOT","TRUE","FALSE","IDENTIFIER",
"ARRAY","STRING","NUMBER",
};
final static String yyrule[] = {
"$accept : start",
"start : program",
"program : method",
"program : program method",
"method : COMMENT FUNCTION IDENTIFIER RETURNS type ACCEPTS aparams BEGIN body END IDENTIFIER",
"body : statement",
"body : body statement",
"statement : function_declaration",
"statement : type_declaration",
"statement : complex_type_declaration",
"statement : complex_type_method_invocation",
"statement : type_initialization",
"statement : return_statement",
"statement : if_statement",
"if_statement : IF LPAREN condition RPAREN BEGIN body END IF",
"return_statement : RETURN IDENTIFIER SEMICOLON",
"return_statement : RETURN NUMERIC SEMICOLON",
"return_statement : RETURN QUOTE SEMICOLON",
"return_statement : RETURN function_declaration",
"type_initialization : IDENTIFIER EQUALS QUOTE SEMICOLON",
"type_initialization : IDENTIFIER EQUALS NUMERIC SEMICOLON",
"type_initialization : IDENTIFIER EQUALS IDENTIFIER SEMICOLON",
"type_declaration : type IDENTIFIER SEMICOLON",
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
"type : NOTHING",
"complex_type_declaration : ARRAY IDENTIFIER SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN params RPAREN SEMICOLON",
"complex_type_method_invocation : IDENTIFIER DOT IDENTIFIER LPAREN RPAREN SEMICOLON",
"condition : bool_exp",
"bool_exp : TRUE",
"bool_exp : FALSE",
};

//#line 119 "Breezy.yacc"

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
//#line 321 "Parser.java"
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
//#line 23 "Breezy.yacc"
{ba.DumpFile(val_peek(0).sval);}
break;
case 2:
//#line 26 "Breezy.yacc"
{yyval.sval= val_peek(0).sval;}
break;
case 3:
//#line 27 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 4:
//#line 37 "Breezy.yacc"
{ yyval.sval = ba.createFunction(val_peek(8).sval,val_peek(6).sval,val_peek(4).sval,val_peek(2).sval,val_peek(0).sval); }
break;
case 5:
//#line 41 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 6:
//#line 42 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + val_peek(0).sval;}
break;
case 7:
//#line 46 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 8:
//#line 47 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 9:
//#line 48 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 10:
//#line 49 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 11:
//#line 50 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 12:
//#line 51 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 13:
//#line 52 "Breezy.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 14:
//#line 58 "Breezy.yacc"
{ yyval.sval = "if(" + val_peek(5).sval + "){\n" + val_peek(2).sval + "}\n";}
break;
case 15:
//#line 61 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 16:
//#line 62 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 17:
//#line 63 "Breezy.yacc"
{yyval.sval = "return " + val_peek(1).sval + ";\n";}
break;
case 18:
//#line 64 "Breezy.yacc"
{yyval.sval = "return " + val_peek(0).sval + "\n";}
break;
case 19:
//#line 67 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 20:
//#line 68 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 21:
//#line 69 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + " = " + val_peek(1).sval + ";\n";}
break;
case 22:
//#line 72 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + " " + val_peek(1).sval + ";\n";}
break;
case 23:
//#line 75 "Breezy.yacc"
{yyval.sval = val_peek(4).sval + "(" + val_peek(2).sval + ");\n";}
break;
case 24:
//#line 79 "Breezy.yacc"
{yyval.sval = "";}
break;
case 25:
//#line 80 "Breezy.yacc"
{yyval.sval = val_peek(1).sval + " " + val_peek(0).sval;}
break;
case 26:
//#line 81 "Breezy.yacc"
{yyval.sval = val_peek(3).sval + ", " + val_peek(1).sval + " " + val_peek(0).sval;}
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
{yyval.sval = "";}
break;
case 31:
//#line 89 "Breezy.yacc"
{yyval.sval = val_peek(2).sval + "," + val_peek(0).sval;}
break;
case 32:
//#line 93 "Breezy.yacc"
{yyval.sval = "boolean";}
break;
case 33:
//#line 94 "Breezy.yacc"
{yyval.sval = "String";}
break;
case 34:
//#line 95 "Breezy.yacc"
{yyval.sval = "double";}
break;
case 35:
//#line 96 "Breezy.yacc"
{yyval.sval = "void";}
break;
case 36:
//#line 99 "Breezy.yacc"
{yyval.sval = ba.createComplexType(val_peek(2).sval, val_peek(1).sval);}
break;
case 37:
//#line 103 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(6).sval, val_peek(4).sval, val_peek(2).sval);}
break;
case 38:
//#line 104 "Breezy.yacc"
{yyval.sval = ba.createComplexTypeMethodInvocation(val_peek(5).sval, val_peek(3).sval, null);}
break;
case 39:
//#line 108 "Breezy.yacc"
{yyval = val_peek(0);}
break;
case 40:
//#line 112 "Breezy.yacc"
{yyval.sval = "true";}
break;
case 41:
//#line 113 "Breezy.yacc"
{yyval.sval = "false";}
break;
//#line 634 "Parser.java"
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
