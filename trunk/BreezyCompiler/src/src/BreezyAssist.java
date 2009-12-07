/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leighton Minor
 */
public class BreezyAssist {

    private static ArrayList<String> errorList = new ArrayList<String>();
    private static boolean mainCreated = false;

    public BreezyAssist() {
    }

    public void DumpFile(String s){
        DumpFile(s,"BreezyProg");
    }

    public void DumpFile(String s, String fileName){
        try {
            FileWriter fw = new FileWriter(fileName + ".java");

            //TODO: REMOVE
            fw.write("import src.BreezyDefault;\n\n");
            fw.write("public class " + fileName + " extends BreezyDefault{\n");
            fw.write(s);
            fw.write("\n}");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(BreezyAssist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String createFunction(String id, String retType, String params, String body, String id2){
        String retFunction = "";

        //Check and see if block ends with correct ending
        if(checkFunctionEnding(id,id2) == false)
            return "";

        if(id.equals("main")){
            if(!mainCreated){
                return createMain(retType,params,body);
            }
            else{
                //Error.  User has two main functions
                return "";
            }
        }
        else{
            retFunction = "public static " + retType + " " + id + "(" + params + ")" + "{\n" + body + "}";
        }

        
        return retFunction;
    }

    private String createMain(String retType, String params, String body){
        String systemMain = "";
        String userMain = "public static " + retType + " main(" + params + ")" + "{\n" + body + "}";

        if(params.equals("")){
            systemMain = "public static void main(String[] args){main();}\n\n";
        }
        else{
            String[] paramList = params.split(" ");

            systemMain = "public static void main(String[] args){main(";
            if(paramList[0].equals("String")){
                systemMain = systemMain.concat("args[0]");
            }
            else if(paramList[0].equals("double")){
                systemMain = systemMain.concat("Double.parseDouble(args[0])");
            }
            else //error
                System.out.println("Error:  Accepts parameters in main function");
            for(int i = 1; i< (paramList.length)/2; i++){
                if(paramList[i*2].equals("String")){
                    systemMain = systemMain.concat(",args["+i+"]");
                }
                else if(paramList[i*2].equals("double")){
                    systemMain = systemMain.concat(",Double.parseDouble(args["+i+"])");
                }
                else //error
                    System.out.println("Error:  Accepts parameters in main function");
            }
            systemMain = systemMain.concat(");}\n");
        }

        return systemMain + userMain;
    }

    public boolean checkFunctionEnding(String expected, String actual){
        if(expected.equals(actual))
            return true;
        else{
            errorList.add("Function ended mismatch: Expeced " + expected + " but actual " + actual);
            return false;
        }
    }
    
    public String createComplexType(String type, String name) {
    	return "ArrayList " + name + " = new ArrayList();\n";
    }
    
    public String createComplexTypeMethodInvocation(String objectName, String methodName, String params) {
    	return objectName + "." + methodName + "(" + params + ");\n";
    }
}
