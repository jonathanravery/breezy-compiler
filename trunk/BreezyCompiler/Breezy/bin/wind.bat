java -jar %BREEZY_HOME%\dist\BreezyCompiler.jar %1

xcopy BreezyProg.java %BREEZY_HOME%\dist\ /Y

javac -classpath %BREEZY_HOME%\dist %BREEZY_HOME%\dist\BreezyProg.java

java -cp %BREEZY_HOME%\dist BreezyProg
