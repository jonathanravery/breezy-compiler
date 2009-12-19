/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src.type;

import java.util.Vector;
import libs.structs.TypedParserVal;

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

    public void addID(String id, String type)throws Exception{
            System.err.println("TypeTracker::addID()::id " + id);
            System.err.println("TypeTracker::addID()::type " + type);
        for(TypedParserVal t : id_list){
            if(t.obj.equals(id))
                throw new Exception("Identifier " + id + " already used");
        }

        //Add it to the list
        TypedParserVal temp = new TypedParserVal(id, type);
        id_list.add(temp);
    }

    public String getType(String id)throws Exception{
            System.err.println("TypeTracker::getType()::id " + id);
        for(TypedParserVal t : id_list){
            if(t.obj.equals(id)){
                System.err.println("TypeTracker::getType()::type " + t.type);
                return t.type;
            }
        }

        throw new Exception("Identifier not found");
    }

    public Object assertSameType(Object type1, Object type2, String op) throws Exception{
        System.err.println("TypeTracker::assertSameType()::type1 " + type1);
        System.err.println("TypeTracker::assertSameType()::type2 " + type2);
            System.err.println("TypeTracker::assertSameType()::op " + op);
        String t1 = (String)type1;
        String t2 = (String)type2;
        if(type1.equals("boolean") || type2.equals("boolean"))
            throw new Exception ("Type Error.  Performed " + t1.toUpperCase() + " " + op + " " + t1.toUpperCase() +".\nCannot use boolean for this type of operation.");

        if(!type1.equals(type2))
            throw new Exception ("Type Error.  Performed " + t1.toUpperCase() + " " + op + " " + t1.toUpperCase() +".\nConfirm the types are both the same.");

        return type1;
    }


    public Object assertNumberType(Object type1, Object type2, String op) throws Exception{
        System.err.println("TypeTracker::assertNumberType()::type1 " + type1);
        System.err.println("TypeTracker::assertNumberType()::type2 " + type2);
        System.err.println("TypeTracker::assertNumberType()::op " + op);

        String t1 = (String)type1;
        String t2 = (String)type2;
        if(!type1.equals("number") || !type2.equals("number"))
            throw new Exception ("Type Error.  Performed " + t1.toUpperCase() + " " + op + " " + t2.toUpperCase() +".\nConfirm the types are both numbers.");

        return type1;
    }


    public Object assertStringType(Object type1) throws Exception{
        System.err.println("TypeTracker::assertStringType()::type1 " + type1);
        if(!type1.equals("string"))
            throw new Exception ("Type Error.\nSTRING type required.");

        return type1;
    }


    public Object assertNumberType(Object type1) throws Exception{
        System.err.println("TypeTracker::assertNumberType()::type1 " + type1);
        if(!type1.equals("number"))
            throw new Exception ("Type Error.\nNUMBER type required.");

        return type1;
    }


    public void assertBoolType(Object type1) throws Exception{
        System.err.println("TypeTracker::assertBool()::type1 " + type1);
        if(!type1.equals("boolean"))
            throw new Exception ("Type Error.\n  Required Boolean and found "+ type1);
    }
}
