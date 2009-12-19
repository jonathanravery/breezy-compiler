/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src.type;

import java.util.Vector;
import libs.structs.Scope;
import libs.structs.TypedParserVal;
import src.ParserVal;

/**
 *
 * @author Leighton Minor
 */
public class TypeTracker {

private boolean debug = false;
    private static Vector<TypedParserVal> id_list;

    /**
     * Default constructor
     */
    public TypeTracker() {
        id_list = new Vector<TypedParserVal>();

        //TODO: Add our functions to this list
        TypedParserVal t1 = new TypedParserVal("ReadCharacterFromScreen", "string",Scope.GLOBAL.getName());        id_list.add(t1);
        TypedParserVal t2 = new TypedParserVal("ReadLineFromScreen", "string",Scope.GLOBAL.getName());        id_list.add(t2);
        TypedParserVal t3 = new TypedParserVal("ReadAllFromScreen", "string",Scope.GLOBAL.getName());        id_list.add(t3);
        TypedParserVal t4 = new TypedParserVal("ReadCharacterFromFile", "string",Scope.GLOBAL.getName());        id_list.add(t4);
        TypedParserVal t5 = new TypedParserVal("ReadLineFromFile", "string",Scope.GLOBAL.getName());        id_list.add(t5);
        TypedParserVal t6 = new TypedParserVal("ReadAllFromFile", "string",Scope.GLOBAL.getName());        id_list.add(t6);
        TypedParserVal t7 = new TypedParserVal("WriteToScreen", "nothing",Scope.GLOBAL.getName());        id_list.add(t7);
        TypedParserVal t8 = new TypedParserVal("WriteToFile", "nothing",Scope.GLOBAL.getName());        id_list.add(t8);
        TypedParserVal t9 = new TypedParserVal("WriteCharacterToScreen", "nothing",Scope.GLOBAL.getName());        id_list.add(t9);
        TypedParserVal t10 = new TypedParserVal("WriteLineToScreen", "nothing",Scope.GLOBAL.getName());        id_list.add(t10);
        TypedParserVal t11 = new TypedParserVal("WriteAllToScreen", "nothing",Scope.GLOBAL.getName());        id_list.add(t11);
        TypedParserVal t12 = new TypedParserVal("WriteCharacterToFile", "nothing",Scope.GLOBAL.getName());        id_list.add(t12);
        TypedParserVal t13 = new TypedParserVal("WriteLineToFile", "nothing",Scope.GLOBAL.getName());        id_list.add(t13);
        TypedParserVal t14 = new TypedParserVal("WriteAllToFile", "nothing",Scope.GLOBAL.getName());        id_list.add(t14);
        TypedParserVal t15 = new TypedParserVal("StringToNumber", "number",Scope.GLOBAL.getName());        id_list.add(t15);
        TypedParserVal t16 = new TypedParserVal("NumberToString", "string",Scope.GLOBAL.getName());        id_list.add(t16);
        TypedParserVal t17 = new TypedParserVal("WriteCharacterToFile", "nothing",Scope.GLOBAL.getName());        id_list.add(t17);

    }

    public void addID(String id, String type, int line, String scope)throws Exception{
        if(debug){
            System.err.println("TypeTracker::addID()::id " + id);
            System.err.println("TypeTracker::addID()::type " + type);
        }

        for(TypedParserVal t : id_list){
            if(t.obj.equals(id) && t.scope.equals(scope))
                throw new Exception("Line: "+line+  
                                    " Identifier " + id + " is already being used.");
        }

        //Add it to the list
        TypedParserVal temp = new TypedParserVal(id, type, scope);
        id_list.add(temp);
    }

    public void removeLocals(){
        for(int i = 0; i < id_list.size(); i++){
            if(id_list.get(i).scope.equals(Scope.LOCAL.getName())){
                id_list.removeElementAt(i);
                i--;
            }
        }
    }

    public String getType(ParserVal pv, String scope)throws Exception{
        if(debug)System.err.println("TypeTracker::getType()::id " + pv.sval);

        for(TypedParserVal t : id_list){
            if(debug)System.err.println(t.obj + "." + t.scope + "::" + pv.sval+ "." + scope);

            if(((String)t.obj).equals(pv.sval.trim()) && t.scope.equals(scope)){
                if(debug)System.err.println("TypeTracker::getType()::type " + t.type);
                return t.type;
            }
        }
        throw new Exception("Line: "+pv.line+  
                                " Identifier \""+ pv.sval +"\" not found");
    }
    
    public String getType(String id, String scope) throws Exception {
        for(TypedParserVal t : id_list){
            if(((String)t.obj).equals(id) && t.scope.equals(scope)){
                if(debug)System.err.println("TypeTracker::getType()::type " + t.type);
                return t.type;
            }
        }
        return null;
    }

    public void assertSameType(ParserVal pv1, ParserVal pv2, ParserVal pvOP) throws Exception{
        if(debug){
            System.err.println("TypeTracker::assertSameType()::type1 " + pv1.obj);
            System.err.println("TypeTracker::assertSameType()::type2 " + pv2.obj);
            System.err.println("TypeTracker::assertSameType()::op " + pvOP.sval);
        }
        String t1 = (String)pv1.obj;
        String t2 = (String)pv2.obj;

        if(!t1.equals(t2))
            throw new Exception ("Line: "+pv1.line+
                                " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t2.toUpperCase() +
                                ".\nConfirm the types are both the same.");

    }

    public void assertNumberOrStringType(ParserVal pv1, ParserVal pv2, ParserVal pvOP) throws Exception{
        if(debug){
            System.err.println("TypeTracker::assertNumberOrStringType()::type1 " + pv1.obj);
            System.err.println("TypeTracker::assertNumberOrStringType()::type2 " + pv2.obj);
            System.err.println("TypeTracker::assertNumberOrStringType()::op " + pvOP.sval);
        }
        String t1 = (String)pv1.obj;
        String t2 = (String)pv2.obj;
        if(t1.equals("boolean") || t2.equals("boolean"))
            throw new Exception ("Line: "+pv1.line+  
                    " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t2.toUpperCase() +
                    ".\nYou cannot use boolean for this type of operation.");

        if(!t1.equals(t2))
            throw new Exception ("Line: "+pv1.line+  
                                " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t2.toUpperCase() +
                                ".\nConfirm the types are both NUMBER or STRING.");

    }


    public void assertNumberType(ParserVal pv1, ParserVal pv2, ParserVal pvOP) throws Exception{
        if(debug){
            System.err.println("TypeTracker::assertNumberType()::type1 " + pv1.obj);
            System.err.println("TypeTracker::assertNumberType()::type2 " + pv2.obj);
            System.err.println("TypeTracker::assertNumberType()::op " + pvOP.sval);
        }
        String t1 = (String)pv1.obj;
        String t2 = (String)pv2.obj;
        if(!t1.equals("number") || !t2.equals("number"))
            throw new Exception ("Line: "+pv1.line+  
                                " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t2.toUpperCase() +
                                ".\nConfirm the types are both numbers.");

    }


    public void assertStringType(ParserVal pv) throws Exception{
        if(debug)System.err.println("TypeTracker::assertStringType()::type1 " + pv.obj);

        if(!pv.obj.equals("string"))
            throw new Exception ("Line: "+pv.line+  
                                " Type Error.\nSTRING type required.");

    }


    public void assertNumberType(ParserVal pv) throws Exception{
        if(debug)System.err.println("TypeTracker::assertNumberType()::type1 " + pv.obj);

        if(!pv.obj.equals("number"))
            throw new Exception ("Line: "+pv.line+  
                                    " Type Error.\nNUMBER type required.");

    }


    public void assertBoolType(ParserVal pv) throws Exception{
        if(debug)System.err.println("TypeTracker::assertBool()::type1 " + pv.obj);
        
        if(!pv.obj.equals("boolean"))
            throw new Exception ("Line: "+pv.line+  
                                    " Type Error.\n  Required Boolean and found "+ pv.obj);
    }
}
