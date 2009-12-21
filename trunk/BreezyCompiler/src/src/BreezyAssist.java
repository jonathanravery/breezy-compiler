/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//TODO:  getType() has to include scope
package src;

import BreezyExceptions.BreezyException;
import BreezyExceptions.ExceptionType;
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
    public static boolean errors=false;
    public static Vector<Integer> caught_syntax_erros = new Vector<Integer>();

    public BreezyAssist() {
    }

    public void DumpFile(String s) throws Exception{
        DumpFile(s,"BreezyProg");
    }

    public void DumpFile(String s, String fileName)throws Exception{
        if(!mainCreated)
            throw new BreezyException(0,ExceptionType.NO_MAIN.getName(),"Must have exactly one function named \"main\".");
        if(errors)
            throw new Exception("Total syntax erros: " +caught_syntax_erros.size());

        typeTrack.endOfProgramCheck();

        try {
            File f = new File(fileName + ".java");
            f.createNewFile();
            f.setWritable(true);
            FileWriter fw = new FileWriter(f);

            fw.write("import src.BreezyDefault;\n");
            fw.write("import libs.structs.TypedParserVal;\n");
            fw.write("import java.util.*;\n\n");
            fw.write("public class " + fileName + " extends BreezyDefault{\n");
            fw.write(s);
            fw.write("\n}");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(BreezyAssist.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("_____\n*****\nCongratulations!  You've compiled!\n*****");
    }

    public String createFunction(String id, ParserVal retType, String params, String body, ParserVal id2,int line,String scope)throws Exception{
        String retFunction = "";

        /*This is only called  when yacc reduces a string
         of functions to a method.  Right now we need to clear
         out all of the local variables that were declared in this
         function so that they can be declared in other functions.*/
        typeTrack.removeLocals();


        //Check and see if block ends with correct ending
        checkFunctionEnding(id,id2);

        //Add function name to id list with it's return type
        this.typeTrack.addID(id,retType.obj.toString(),line,scope);

        //TODO: check return value == actual return value

        
        if(id.equals("main")){
            if(!mainCreated){
                return createMain(retType,params,body);
            }
            else{
                throw new BreezyException(line,ExceptionType.DUPLICATE_MAIN.getName(),"You must have exactly one function named \"main\".");
            }
        }
        else{
            retFunction = "public " + retType.sval + " " + id + "(" + params + ")" + "{\n" + body + "}";
        }

        return retFunction;
    }

    private String createMain(ParserVal retType, String params, String body)throws BreezyException{
        String systemMain = "";
        String userMain = "public " + retType.sval + " main(" + params + ")" + "{\n" + body + "}";

        if(params.equals("")){
            systemMain = "public static void main(String[] args){BreezyProg b = new BreezyProg(); b.main();}\n\n";
        }
        else{
            String[] paramList = params.split("[ ]+");

            systemMain = "public static void main(String[] args){BreezyProg b = new BreezyProg(); b.main(";
            if(paramList[0].equals("String")){
                systemMain = systemMain.concat("args[0]");
            }
            else if(paramList[0].equals("double")){
                systemMain = systemMain.concat("Double.parseDouble(args[0])");
            }
            else //error
                throw new BreezyException(retType.line+1,
                                            ExceptionType.INVALID_MAIN_PARAMS.getName(),
                                            "The main function can only accept STRING type and NUMBER type parameters.");
            for(int i = 1; i< (paramList.length)/2; i++){
                if(paramList[i*2].equals("String")){
                    systemMain = systemMain.concat(",args["+i+"]");
                }
                else if(paramList[i*2].equals("double")){
                    systemMain = systemMain.concat(",Double.parseDouble(args["+i+"])");
                }
                else
                    throw new BreezyException(retType.line+1,
                                        ExceptionType.INVALID_MAIN_PARAMS.getName(),
                                        "The main function can only accept STRING type and NUMBER type parameters.");
            }
            systemMain = systemMain.concat(");}\n");
        }

        mainCreated = true;
        return systemMain + userMain;
    }

    public void checkFunctionEnding(String expected, ParserVal actual) throws Exception{
        if(!expected.equals(actual.sval))
            throw new BreezyException(actual.line,
                                        ExceptionType.FUNCTION_ENDING.getName(),
                                        "Expeced " + expected + " but was " + actual.sval + ".");
    }
    
    public String createComplexType(String type, String name) {
        if(type.equals("ArrayList"))
            return type + "<TypedParserVal> " + name + " = new " + type + "<TypedParserVal>();";
        else //hashmap
            return type + "<TypedParserVal,TypedParserVal> " + name + " = new " + type + "<TypedParserVal,TypedParserVal>();";
    }
    
    public String createComplexType(String type, String name, String params, int line, String scope) throws Exception{
        
        //Add id to type checker
        this.typeTrack.addID(name, type, line, scope);

    	String temp = "";
        if(type.equals("ArrayList"))
            temp = type + "<TypedParserVal> " + name + " = new " + type + "<TypedParserVal>();";
        else //hashmap
            temp = type + "<TypedParserVal,TypedParserVal> " + name + " = new " + type + "<TypedParserVal,TypedParserVal>();";
    	
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
    
    public String createPrintCommand(final String identifier) {
    	return "System.out.println(" + identifier + ".toString());\n";
    }
    
    public String createIndexAccess(final String identifier, final int index) {
    	return identifier + ".getItemAt(" + index + ");\n";
    }
}
