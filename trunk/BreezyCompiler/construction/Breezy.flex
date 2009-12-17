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
  public int ln = 0;

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
%}

digit=		[0-9]

/* comments and line terminators from JFlex Manual*/

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = [ \t\f]

Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}

%%

{Comment}			{p("COMMENT");	return yyparser.COMMENT;}
{LineTerminator}accepts		{p("ACCEPTS");	return yyparser.ACCEPTS;}
{LineTerminator}{WhiteSpace}*begin		{pl("BEGIN");	return yyparser.BEGIN;}
array                           {p("ARRAY");    return yyparser.ARRAY;}
hash                           {p("HASH");    return yyparser.HASH;}
boolean				{p("BOOLEAN");	return yyparser.BOOLEAN;}
each				{p("EACH");	return yyparser.EACH;}
else				{p("ELSE");	return yyparser.ELSE;}
{LineTerminator}{WhiteSpace}*end				{pl("END");	return yyparser.END;}
false				{p("FALSE");	return yyparser.FALSE;}
for				{p("FOR");	return yyparser.FOR;}
function			{p("FUNCTION");	return yyparser.FUNCTION;}
if				{p("IF");	return yyparser.IF;}
nothing				{p("NOTHING");	return yyparser.NOTHING;}
number				{p("NUMBER");	return yyparser.NUMBER;}
return				{p("RETURN");	return yyparser.RETURN;}
{LineTerminator}returns		{p("RETURNS");	return yyparser.RETURNS;}
string				{p("STRING");	return yyparser.STRING;}
true				{p("TRUE");	return yyparser.TRUE;}
while				{p("WHILE");	return yyparser.WHILE;}
"+"					{	return yyparser.PLUS;}
"-"					{	return yyparser.MINUS;}
"*"					{	return yyparser.MUL;}
"/"					{	return yyparser.DIV;}
"%"					{	return yyparser.MOD;}
"<="					{	return yyparser.REL_OP_LE;}
"<"					{	return yyparser.REL_OP_LT;}
">="					{	return yyparser.REL_OP_GE;}
">"					{	return yyparser.REL_OP_GT;}
"="					{p("EQUALS");	return yyparser.EQUALS;}
"EQUAL"					{	return yyparser.LOG_OP_EQUAL;}
"AND"					{	return yyparser.LOG_OP_AND;}
"OR"					{	return yyparser.LOG_OP_OR;}
"NOT"					{	return yyparser.LOG_OP_NOT;}
"("					{p("LPAREN");	return yyparser.LPAREN;}
")"					{p("RPAREN");	return yyparser.RPAREN;}
":"					{p("COLON");	return yyparser.COLON;}
";"			{pl("SEMICOLON");	return yyparser.SEMICOLON;}
","					{p("COMMA");	return yyparser.COMMA;}
\.					{p("DOT");	return yyparser.DOT;}
[a-zA-Z][a-zA-Z0-9]*			{p(yytext());	yyparser.yylval = new ParserVal(yytext()); return yyparser.IDENTIFIER;}
\"([^\"]|\\\")*\"			{p(yytext());	yyparser.yylval = new ParserVal(yytext()); return yyparser.QUOTE;}
-?{digit}*(\.{digit}+)?			{p(yytext());	yyparser.yylval = new ParserVal(yytext()); return yyparser.NUMERIC;}
{LineTerminator}			{pl("");}
{WhiteSpace}				{}
.					{p("WHO KNOWS");	return 0;}