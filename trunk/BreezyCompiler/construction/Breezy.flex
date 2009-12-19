package src;

%%

%byaccj
%unicode
%line
%column

%{
//Authored by Leighton, Jack, Jon, Ian, Elena, Clement 
//Adapted from pltsnow project by Jon, Cesar, Vinay, Sharadh

  public Parser yyparser;

  /* Default constructor */
  public Yylex(){}

  /* constructor taking an additional parser object */
  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }

  //Print to console
  private void p(String s){
            System.out.print(s + " ");
  }
  //Print line to console
  private void pl(String s){
            System.out.println(s);
  }
  private void l(int line,int col){
            System.out.print("("+line + "," + col + ")");
  }
%}

digit=		[0-9]

/* comments and line terminators from JFlex Manual*/

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = [ \t\f]

Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" [^*] ~"*/" {LineTerminator}| "/*" "*"+ "/" {LineTerminator}
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}

%%

{Comment}			{l(yyline,yycolumn);p("COMMENT");	return yyparser.COMMENT;}
{LineTerminator}accepts		{p("ACCEPTS");	return yyparser.ACCEPTS;}
{LineTerminator}{WhiteSpace}*begin		{pl("BEGIN");	return yyparser.BEGIN;}
array                           {p("ARRAY");    return yyparser.ARRAY;}
hash                           {p("HASH");    return yyparser.HASH;}
boolean				{p("BOOLEAN");	return yyparser.BOOLEAN;}
each				{p("EACH");	return yyparser.EACH;}
else{WhiteSpace}if				{p("ELSEIF");	return yyparser.ELSEIF;}
else				{p("ELSE");	return yyparser.ELSE;}
{LineTerminator}{WhiteSpace}*end {pl("END");	return yyparser.END;}
false				{p("FALSE");	yyparser.yylval = new ParserVal("false",yyline,yycolumn); yyparser.yylval.obj="boolean"; return yyparser.FALSE;}
for				{p("FOR");	return yyparser.FOR;}
function			{p("FUNCTION");	return yyparser.FUNCTION;}
if				{p("IF");	return yyparser.IF;}
nothing				{p("NOTHING");	return yyparser.NOTHING;}
number				{p("NUMBER");	return yyparser.NUMBER;}
return				{p("RETURN");	return yyparser.RETURN;}
{LineTerminator}returns		{p("RETURNS");	return yyparser.RETURNS;}
string				{p("STRING");	return yyparser.STRING;}
true				{p("TRUE");	yyparser.yylval = new ParserVal("true",yyline,yycolumn); yyparser.yylval.obj="boolean"; return yyparser.TRUE;}
while				{p("WHILE");	return yyparser.WHILE;}
"+"					{	yyparser.yylval = new ParserVal(yytext(),yyline,yycolumn); return yyparser.PLUS;}
"-"					{	yyparser.yylval = new ParserVal(yytext(),yyline,yycolumn); return yyparser.MINUS;}
"*"					{	yyparser.yylval = new ParserVal(yytext(),yyline,yycolumn); return yyparser.MUL;}
"/"					{	yyparser.yylval = new ParserVal(yytext(),yyline,yycolumn); return yyparser.DIV;}
"%"					{	yyparser.yylval = new ParserVal(yytext(),yyline,yycolumn); return yyparser.MOD;}
"<="					{	return yyparser.REL_OP_LE;}
"<"					{	return yyparser.REL_OP_LT;}
">="					{	return yyparser.REL_OP_GE;}
">"					{	return yyparser.REL_OP_GT;}
"="					{p("EQUALS");	return yyparser.EQUALS;}
"EQUALS"				{	return yyparser.LOG_OP_EQUAL;}
"AND"					{	return yyparser.LOG_OP_AND;}
"OR"					{	return yyparser.LOG_OP_OR;}
"NOT"					{	return yyparser.LOG_OP_NOT;}
"("					{p("LPAREN");	return yyparser.LPAREN;}
")"					{p("RPAREN");	return yyparser.RPAREN;}
"["					{p("LEFT_SQUARE_PAREN");	return yyparser.LEFT_SQUARE_PAREN;}
"]"					{p("RIGHT_SQUARE_PAREN");	return yyparser.RIGHT_SQUARE_PAREN;}
":"					{p("COLON");	return yyparser.COLON;}
";"                                     {pl("SEMICOLON");	return yyparser.SEMICOLON;}
","					{p("COMMA");	return yyparser.COMMA;}
\.					{p("DOT");	return yyparser.DOT;}
[a-zA-Z][a-zA-Z0-9]*			{l(yyline,yycolumn);p(yytext());	yyparser.yylval = new ParserVal(yytext(),yyline,12); return yyparser.IDENTIFIER;}
\"([^\"]|\\\")*\"			{l(yyline,yycolumn);p(yytext());	yyparser.yylval = new ParserVal(yytext(),yyline,12); yyparser.yylval.obj="string"; return yyparser.QUOTE;}
-?{digit}*(\.{digit}+)?			{l(yyline,yycolumn);p(yytext());	yyparser.yylval = new ParserVal(yytext(),yyline,12); yyparser.yylval.obj="number"; return yyparser.NUMERIC;}
{LineTerminator}			{pl("");}
{WhiteSpace}				{}
.					{p("WHO KNOWS");	return 0;}