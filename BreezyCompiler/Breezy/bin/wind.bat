@echo off


REM Call the Breezy Compiler
@echo on
@echo.
@echo _____
@echo *****
@echo Launching Breezy Compiler
@echo *****
@echo.
@echo off
java -jar %BREEZY_HOME%\dist\BreezyCompiler.jar %1



REM After compiling, copy the new 
REM java file into the folder with 
REM the right class filesso that 
REM the program can run.
if exist BreezyProg.java xcopy BreezyProg.java %BREEZY_HOME%\dist\ /Q /Y > %BREEZY_HOME%\log.txt
if not exist %BREEZY_HOME%\dist\BreezyProg.java goto :cleanup



@echo on
@echo.
@echo _____
@echo *****
@echo Launching Java Compiler
@echo *****
@echo off
javac -nowarn -classpath %BREEZY_HOME%\dist %BREEZY_HOME%\dist\BreezyProg.java"
if errorlevel 1 goto :nocompile



@echo on
@echo.
@echo _____
@echo *****
@echo Launching Your Program
@echo ****
@echo.
@echo off
java -cp %BREEZY_HOME%\dist BreezyProg



@echo on
@echo.
@echo _____
@echo *****
@echo Program Complete
@echo ****
@echo off
goto :cleanup



:nocompile
@echo on
@echo.
@echo _____
@echo *****
@echo Sorry, your program didn't compile in Java.  Our apologies.
@echo ****
@echo off



:cleanup
REM Clean-up generated files so they don't
REM interfere with the next build
If exist %BREEZY_HOME%\dist\BreezyProg.java  del %BREEZY_HOME%\dist\BreezyProg.java  > %Breezy_Home%\log.txt
If exist %BREEZY_HOME%\dist\BreezyProg.class del %BREEZY_HOME%\dist\BreezyProg.class > %Breezy_Home%\log.txt
IF exist oldBreezyProg.java                  del oldBreezyProg.java                  > %Breezy_Home%\log.txt
IF exist BreezyProg.java                     ren BreezyProg.java oldBreezyProg.java  > %Breezy_Home%\log.txt
@echo.




