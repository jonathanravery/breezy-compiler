#!/bin/bash

./yacc.exe -J -Jextends=Yylex -Jnorun Breezy.yacc
sed -i -e 's/int yyparse()/int yyparse() throws IOException,Exception/' Parser.java
cp Parser.java ../src/src