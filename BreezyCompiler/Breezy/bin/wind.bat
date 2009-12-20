@echo off


java -jar %BREEZY_HOME%\dist\BreezyCompiler.jar %1 


xcopy BreezyProg.java %BREEZY_HOME%\dist\ /Q /Y


@echo on
@echo _____
@echo *****
@echo Launching Java Compiler
@echo *****
@echo off
javac -classpath %BREEZY_HOME%\dist %BREEZY_HOME%\dist\BreezyProg.java


@echo on
@echo _____
@echo *****
@echo Launching Your Program
@echo ****
@echo off
java -cp %BREEZY_HOME%\dist BreezyProg


@echo on
@echo _____
@echo *****
@echo Program Complete
@echo ****
@echo off
del %BREEZY_HOME%\dist\BreezyProg.java


del %BREEZY_HOME%\dist\BreezyProg.class


del BreezyProg.java




