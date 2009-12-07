import src.BreezyDefault;

public class BreezyProg extends BreezyDefault{
public static void main2(){
String helloWorld;
helloWorld = "HelloWorld";
printHelloWorld(helloWorld,50);
}public static String printHelloWorld(String s, double x){
WriteToScreen(s);
return s;
}public static String objectTest(String s, double x){
WriteToScreen(s);
return s;
}public static void arrayTest(String s, double x){
WriteToScreen(s);
}public static void main(String[] args){main();}

public static double main(){
WriteToScreen("Hello World");
if(true){
double x;
x = 5;
}
return 5;
}
}