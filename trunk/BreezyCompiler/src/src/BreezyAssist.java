/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//TODO:  getType() has to include scope
package src;

import java.io.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import libs.structs.*;
import src.type.TypeTracker;

/**
 *
 * @author Leighton Minor
 */
public class BreezyAssist {

    private static ArrayList<String> errorList = new ArrayList<String>();
    private static boolean mainCreated = false;

    public TypeTracker typeTrack = new TypeTracker();

    public BreezyAssist() {
    }

    public void DumpFile(String s){
        DumpFile(s,"BreezyProg");
    }

    public void DumpFile(String s, String fileName){
        try {
            File f = new File(fileName + ".java");
            f.createNewFile();
            f.setWritable(true);
            FileWriter fw = new FileWriter(f);

            fw.write("import src.BreezyDefault;\n\n");
            fw.write("import java.io.*;\n\n");
            fw.write("public class " + fileName + " extends BreezyDefault{\n");
            fw.write(s);
            fw.write("\n}");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(BreezyAssist.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Congratulations!  You've compiled!");
    }

    public String createFunction(String id, String retType, String params, String body, String id2,int line,String scope){
        String retFunction = "";

        /*This is only called  when yacc reduces a string
         of functions to a method.  Right now we need to clear
         out all of the local variables that were declared in this
         function so that they can be declared in other functions.*/
        typeTrack.removeLocals();


        //Check and see if block ends with correct ending
        if(checkFunctionEnding(id,id2) == false)
            return "";

        //Add function name to id list with it's return type
        this.addIdentifier(id,retType,line,scope);

        //TODO: check return value == actual return value

        
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
            retFunction = "public " + retType + " " + id + "(" + params + ")" + "{\n" + body + "}";
        }

        return retFunction;
    }

    public void addIdentifier(String id, String type, int line, String scope){
        //TODO: check against java keywords

        //Add to our type tracking list of identifiers
        try{
            //System.err.println("BreezyAssist::addIdentifier()::id " + id);
            //System.err.println("BreezyAssist::addIdentifier()::type " + type);
            typeTrack.addID(id, type,line,scope);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    private String createMain(String retType, String params, String body){
        String systemMain = "";
        String userMain = "public " + retType + " main(" + params + ")" + "{\n" + body + "}";

        if(params.equals("")){
            systemMain = "public static void main(String[] args){BreezyProg b = new BreezyProg(); b.main();}\n\n";
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
                    //TODO;
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
    	return type + " " + name + " = new " + type + "();\n";
    }
    
    public String createComplexType(String type, String name, String params, int line, String scope) {
        
        //Add id to type checker
        this.addIdentifier(name, type, line, scope);

    	String temp = type + " " + name + " = new " + type + "();\n";
    	
    	if (type.equals("ArrayList")) {
    		Collection<TypedParserVal> parseParams = parseArrayParams(params);
	    	for (final TypedParserVal value : parseParams) {
	    		if (value.type.equals("Identifier"))
	    			temp += name + ".add(" + value.obj + ");\n";
	    		else
	    			temp += name + ".add(" + value.convertToCode() + ");\n";
	    	}
    	} else { // type is HashMap
    		Map<TypedParserVal, TypedParserVal> parseHashParams = parseHashParams(params);
    		for (final TypedParserVal key : parseHashParams.keySet()) {
    			temp += name + ".put(" + key.convertToCode() + "," + parseHashParams.get(key).convertToCode() + " );\n";
    		}
    		System.out.println();
    	}
    	return temp;
    }
    
    public String createComplexTypeMethodInvocation(String objectName, String methodName, String scope, String params) {
    	String type = null;
		try {
			type = typeTrack.getType(objectName, scope);
		} catch (Exception e) {
		}
    	String returnValue = "";
    	if (type.equals("ArrayList")) {
    		List<TypedParserVal> parseArrayParams = parseArrayParams(params);
    		if (methodName.equals("add")) {
    			for (final TypedParserVal param : parseArrayParams) {
    				returnValue += objectName + "." + methodName + "(" + param.convertToCode() + ");\n";
    			}
    		} else if (methodName.equals("remove")) {
    			for (final TypedParserVal param : parseArrayParams) {
    				returnValue += objectName + ".remove(" + param.convertToCode() + ");\n";
    			}
    		} else if (methodName.equals("removeAt")) {
    			for (final TypedParserVal param : parseArrayParams) {
    				returnValue += objectName + ".removeAt(" + param.obj.toString() + ");\n";
    			}
    		} else if (methodName.equals("size")) {
				returnValue += objectName + ".length();\n";
			}
    	} else if (type.equals("HashMap")) {
    		List<TypedParserVal> parseArrayParams = parseArrayParams(params);
    		if (methodName.equals("add")) {
				returnValue += objectName + ".put(" + parseArrayParams.get(0).convertToCode() + ", " + parseArrayParams.get(1).convertToCode() + ");\n"; 
    		} else if (methodName.equals("get")) {
    			returnValue += objectName + ".get(" + parseArrayParams.get(0).convertToCode() + ");\n";
    		} else if (methodName.equals("remove")) {
    			for (final TypedParserVal toBeRemoved : parseArrayParams) {
    				returnValue += objectName + ".remove(" + toBeRemoved.convertToCode() + ");\n"; 
    			}
    		} else if (methodName.equals("size")) {
    			returnValue += objectName + ".length();\n";
    		}
    	}
    	return returnValue;
    }
    
    /**
     * Converts array instantiating param string into list of TypedParserVal
     * e.g. given "(5, "testing", "testing2")
     * returns list containing TypedParserVal<Number>(5), TypedParserVal<String>("testing") and TypedParserVal<String>("testing2")
     * @param params
     * @return
     */
    private List<TypedParserVal> parseArrayParams(String params){
    	ArrayList<TypedParserVal> arrayList = new ArrayList<TypedParserVal>();
    	StringTokenizer stringTokenizer = new StringTokenizer(params, ",");
    	while(stringTokenizer.hasMoreElements())  {
    		String nextToken = stringTokenizer.nextToken();
    		if (Pattern.matches("\\d.*", nextToken)) {
    			arrayList.add(new TypedParserVal(nextToken, "Number"));
    		} else if (Pattern.matches("\".*", nextToken)) {
    			arrayList.add(new TypedParserVal(nextToken, "String"));
    		} else if (Pattern.matches("true|false", nextToken)) {
    			arrayList.add(new TypedParserVal(nextToken, "Boolean"));
    		} else {
    			arrayList.add(new TypedParserVal(nextToken, "Identifier"));
    		}
    	}
    	return arrayList;
    }
    
    private Map<TypedParserVal,TypedParserVal> parseHashParams(String params) {
    	HashMap<TypedParserVal,TypedParserVal> hashMap = new HashMap<TypedParserVal, TypedParserVal>();
    	StringTokenizer stringTokenizer = new StringTokenizer(params, ")");
    	
    	while(stringTokenizer.hasMoreElements()) {
    		String keyValuePair = stringTokenizer.nextToken();
    		keyValuePair = keyValuePair.replace("(", ""); // Removes leading left parenthesis
    		List<TypedParserVal> keyValue = parseArrayParams(keyValuePair);
    		hashMap.put(keyValue.get(0), keyValue.get(1));
    	}
    	return hashMap;
    }
}
