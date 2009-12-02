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
        String retFunction;

        if(checkFunctionEnding(id,id2) == false)
            return "";

        if(id.equals("main")){
            if(params.equals("")){

            }
            else{
                String[] paramList = params.split(" ");
                for(int i = 0; i< paramList.length; i=i+2){
                    
                }
            }
        }

        retFunction = "public static " + retType + " " + id + "(" + params + ")" + "{\n" + body + "}";
        return retFunction;
    }

    public boolean checkFunctionEnding(String expected, String actual){
        if(expected.equals(actual))
            return true;
        else{
            errorList.add("Function ended mismatch: Expeced " + expected + " but actual " + actual);
            return false;
        }
    }
}
