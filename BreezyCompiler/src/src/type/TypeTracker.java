/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src.type;

import java.util.Vector;
import libs.structs.TypedParserVal;
import src.ParserVal;

/**
 *
 * @author Leighton Minor
 */
public class TypeTracker {

    private static Vector<TypedParserVal> id_list;

    /**
     * Default constructor
     */
    public TypeTracker() {
        id_list = new Vector<TypedParserVal>();

        //TODO: Add our functions to this list
        TypedParserVal t1 = new TypedParserVal("ReadCharacterFromScreen", "string");        id_list.add(t1);
        TypedParserVal t2 = new TypedParserVal("ReadLineFromScreen", "string");        id_list.add(t2);
        TypedParserVal t3 = new TypedParserVal("ReadAllFromScreen", "string");        id_list.add(t3);
        TypedParserVal t4 = new TypedParserVal("ReadCharacterFromFile", "string");        id_list.add(t4);
        TypedParserVal t5 = new TypedParserVal("ReadLineFromFile", "string");        id_list.add(t5);
        TypedParserVal t6 = new TypedParserVal("ReadAllFromFile", "string");        id_list.add(t6);
        TypedParserVal t7 = new TypedParserVal("WriteToScreen", "nothing");        id_list.add(t7);
        TypedParserVal t8 = new TypedParserVal("WriteToFile", "nothing");        id_list.add(t8);
        TypedParserVal t9 = new TypedParserVal("WriteCharacterToScreen", "nothing");        id_list.add(t9);
        TypedParserVal t10 = new TypedParserVal("WriteLineToScreen", "nothing");        id_list.add(t10);
        TypedParserVal t11 = new TypedParserVal("WriteAllToScreen", "nothing");        id_list.add(t11);
        TypedParserVal t12 = new TypedParserVal("WriteCharacterToFile", "nothing");        id_list.add(t12);
        TypedParserVal t13 = new TypedParserVal("WriteLineToFile", "nothing");        id_list.add(t13);
        TypedParserVal t14 = new TypedParserVal("WriteAllToFile", "nothing");        id_list.add(t14);
        TypedParserVal t15 = new TypedParserVal("StringToNumber", "number");        id_list.add(t15);
        TypedParserVal t16 = new TypedParserVal("NumberToString", "string");        id_list.add(t16);
        TypedParserVal t17 = new TypedParserVal("WriteCharacterToFile", "nothing");        id_list.add(t17);

    }

    public void addID(String id, String type, int line, int col)throws Exception{
        //System.err.println("TypeTracker::addID()::id " + id);
        //System.err.println("TypeTracker::addID()::type " + type);
        for(TypedParserVal t : id_list){
            if(t.obj.equals(id))
                throw new Exception("Line: "+line+  
                                    " Identifier " + id + " already used");
        }

        //Add it to the list
        TypedParserVal temp = new TypedParserVal(id, type);
        id_list.add(temp);
    }

    public String getType(ParserVal pv)throws Exception{
        //System.err.println("TypeTracker::getType()::id " + id);
        for(TypedParserVal t : id_list){
            //System.err.println(t.obj + "::" + pv.sval);
            if(((String)t.obj).equals(pv.sval.trim())){
                //System.err.println("TypeTracker::getType()::type " + t.type);
                return t.type;
            }
        }

        throw new Exception("Line: "+pv.line+  
                                " Identifier \""+ pv.sval +"\" not found");
    }

    public void assertNumberOrStringType(ParserVal pv1, ParserVal pv2, ParserVal pvOP) throws Exception{
        //System.err.println("TypeTracker::assertNumberOrStringType()::type1 " + pv1.obj);
        //.err.println("TypeTracker::assertNumberOrStringType()::type2 " + pv2.obj);
        //System.err.println("TypeTracker::assertNumberOrStringType()::op " + pvOP.sval);
        String t1 = (String)pv1.obj;
        String t2 = (String)pv2.obj;
        if(t1.equals("boolean") || t2.equals("boolean"))
            throw new Exception ("Line: "+pv1.line+  
                    " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t1.toUpperCase() +
                    ".\nCannot use boolean for this type of operation.");

        if(!t1.equals(t2))
            throw new Exception ("Line: "+pv1.line+  
                                " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t1.toUpperCase() +
                                ".\nConfirm the types are both the same.");

    }


    public void assertNumberType(ParserVal pv1, ParserVal pv2, ParserVal pvOP) throws Exception{
        //System.err.println("TypeTracker::assertNumberType()::type1 " + pv1.obj);
        //System.err.println("TypeTracker::assertNumberType()::type2 " + pv2.obj);
        //System.err.println("TypeTracker::assertNumberType()::op " + pvOP.sval);

        String t1 = (String)pv1.obj;
        String t2 = (String)pv2.obj;
        if(!t1.equals("number") || !t2.equals("number"))
            throw new Exception ("Line: "+pv1.line+  
                                " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t2.toUpperCase() +
                                ".\nConfirm the types are both numbers.");

    }


    public void assertStringType(ParserVal pv) throws Exception{
        //System.err.println("TypeTracker::assertStringType()::type1 " + pv.obj);
        if(!pv.obj.equals("string"))
            throw new Exception ("Line: "+pv.line+  
                                " Type Error.\nSTRING type required.");

    }


    public void assertNumberType(ParserVal pv) throws Exception{
        //System.err.println("TypeTracker::assertNumberType()::type1 " + pv.obj);
        if(!pv.obj.equals("number"))
            throw new Exception ("Line: "+pv.line+  
                                    " Type Error.\nNUMBER type required.");

    }


    public void assertBoolType(ParserVal pv) throws Exception{
        //System.err.println("TypeTracker::assertBool()::type1 " + pv.obj);
        if(!pv.obj.equals("boolean"))
            throw new Exception ("Line: "+pv.line+  
                                    " Type Error.\n  Required Boolean and found "+ pv.obj);
    }
}
