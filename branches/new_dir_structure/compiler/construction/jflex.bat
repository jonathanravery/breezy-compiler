@echo on
REM Please adjust the paths JFLEX_HOME and JAVA_HOME to suit your needs
REM (please do not add a trailing backslash)

set JFLEX_HOME=C:\jflex-1.4.3

REM only needed for JDK 1.1.x:
set JAVA_HOME=C:\Java\jdk1.6.0_12

REM ------------------------------------------------------------------- 

set CLPATH=%JAVA_HOME%\lib\classes.zip;%JFLEX_HOME%\lib\JFlex.jar

REM for JDK 1.1.x
%JAVA_HOME%\bin\java -classpath %CLPATH% JFlex.Main -d ..\src\src Breezy.flex 
