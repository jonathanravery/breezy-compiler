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
    private static Vector<LateTypeValidation> validate_list;
    private static Vector<LateTypeValidationPair> pair_list;

    /**
     * Default constructor
     */
    public TypeTracker() {
        id_list = new Vector<TypedParserVal>();
        validate_list = new Vector<LateTypeValidation>();
        pair_list = new Vector<LateTypeValidationPair>();

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
                if(t.type.equals("not_def") && t.scope.equals(Scope.GLOBAL.getName())){
                    /*If we had a function that was defined AFTER it was called we would
                     find it here.  Now we need to assign it's type so that if it is
                     used later, it will have a type and there can be more type
                     checking.  We also need to validate it's previous uses.  If it was
                     used out of context, throw a type error.*/
                    t.type = type;
                    doLateTypeValidation(id, type);
                    return;
                }
                else
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
            //if(debug)System.err.println(t.obj + "." + t.scope + "::" + pv.sval+ "." + scope);

            //If the identifier is already in the list, return the type.
            if(((String)t.obj).equals(pv.sval.trim()) && (t.scope.equals(scope))){
                if(debug)System.err.println("TypeTracker::getType()::type " + t.type);
                return t.type;
            }
        }

        /*If the identifier is not in the list, but it might be, say for instance if the
         function is not defined until later.  Add the identifier name with the scope
         "not_def".*/
        if(scope.equals(Scope.GLOBAL.getName())){
            this.addID(pv.sval, "not_def", pv.line, scope);
            return "not_def";
        }

        //Default behavior
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

        if(!t1.equals(t2) && !t1.equals("not_def") && !t2.equals("not_def") )
            throw new Exception ("Line: "+pv1.line+
                                " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t2.toUpperCase() +
                                ".\nConfirm the types are both the same.");

        /*If we don't throw a type error, make sure that variables are not "not_def
        and if they are, record this info so that we can check type later when
         the programmer defines the function.*/
        if(pv1.obj.equals("not_def") && !pv2.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv1.sval.split("[(]"))[0],pv2.obj.toString(),pv1.line);
            validate_list.add(ltv);
        }
        else if(pv2.obj.equals("not_def") && !pv1.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv2.sval.split("[(]"))[0],pv1.obj.toString(),pv2.line);
            validate_list.add(ltv);
        }
        else{
            /*Here we have two function identifiers that have not been declared.  Add them
             to a LateValidationPair and put them in a vector.  Validate later that they are
             both the same type.*/
            LateTypeValidation ltv = new LateTypeValidation((pv1.sval.split("[(]"))[0],"",pv1.line);
            LateTypeValidation ltv2 = new LateTypeValidation((pv2.sval.split("[(]"))[0],"",pv2.line);
            LateTypeValidationPair pair = new LateTypeValidationPair(ltv,ltv2);
            pair.setCanBeNumber(true);
            pair.setCanBeString(true);
            pair.setCanBeBoolean(true);
            pair_list.add(pair);
        }

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

        if(!t1.equals(t2) && !t1.equals("not_def") && !t2.equals("not_def") )
            throw new Exception ("Line: "+pv1.line+  
                                " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t2.toUpperCase() +
                                ".\nConfirm the types are both NUMBER or STRING.");

        /*If we don't throw a type error, make sure that variables are not "not_def
        and if they are, record this info so that we can check type later when
         the programmer defines the function.*/
        if(pv1.obj.equals("not_def") && !pv2.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv1.sval.split("[(]"))[0],pv2.obj.toString(),pv1.line);
            validate_list.add(ltv);
        }
        else if(pv2.obj.equals("not_def") && !pv1.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv2.sval.split("[(]"))[0],pv1.obj.toString(),pv2.line);
            validate_list.add(ltv);
        }
        else{
            /*Here we have two function identifiers that have not been declared.  Add them
             to a LateValidationPair and put them in a vector.  Validate later that they are
             both the same type.*/
            LateTypeValidation ltv = new LateTypeValidation((pv1.sval.split("[(]"))[0],"",pv1.line);
            LateTypeValidation ltv2 = new LateTypeValidation((pv2.sval.split("[(]"))[0],"",pv2.line);
            LateTypeValidationPair pair = new LateTypeValidationPair(ltv,ltv2);
            pair.setCanBeNumber(true);
            pair.setCanBeString(true);
            pair_list.add(pair);
        }

    }


    public void assertNumberType(ParserVal pv1, ParserVal pv2, ParserVal pvOP) throws Exception{
        if(debug){
            System.err.println("TypeTracker::assertNumberType()::type1 " + pv1.obj);
            System.err.println("TypeTracker::assertNumberType()::type2 " + pv2.obj);
            System.err.println("TypeTracker::assertNumberType()::op " + pvOP.sval);
        }
        String t1 = (String)pv1.obj;
        String t2 = (String)pv2.obj;
        if( (!t1.equals("number") && !t1.equals("not_def")) || (!t2.equals("number") && !t2.equals("not_def")))
            throw new Exception ("Line: "+pv1.line+  
                                " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t2.toUpperCase() +
                                ".\nConfirm the types are both numbers.");

        /*If we don't throw a type error, make sure that variables are not "not_def
        and if they are, record this info so that we can check type later when
         the programmer defines the function.*/
        if(pv1.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv1.sval.split("[(]"))[0],"number",pv1.line);
            validate_list.add(ltv);
        }
        if(pv2.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv2.sval.split("[(]"))[0],"number",pv2.line);
            validate_list.add(ltv);
        }

    }


    public void assertStringType(ParserVal pv) throws Exception{
        if(debug)System.err.println("TypeTracker::assertStringType()::type1 " + pv.obj);

        if(!pv.obj.equals("string") && !pv.obj.equals("not_def"))
            throw new Exception ("Line: "+pv.line+  
                                " Type Error.\nSTRING type required.");

        /*If we don't throw a type error, make sure that variables are not "not_def
        and if they are, record this info so that we can check type later when
         the programmer defines the function.*/
        if(pv.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv.sval.split("[(]"))[0],"string",pv.line);
            validate_list.add(ltv);
        }

    }


    public void assertNumberType(ParserVal pv) throws Exception{
        if(debug)System.err.println("TypeTracker::assertNumberType()::type1 " + pv.obj);

        if(!pv.obj.equals("number") && !pv.obj.equals("not_def"))
            throw new Exception ("Line: "+pv.line+  
                                    " Type Error.\nNUMBER type required.");

        /*If we don't throw a type error, make sure that variables are not "not_def
        and if they are, record this info so that we can check type later when
         the programmer defines the function.*/
        if(pv.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv.sval.split("[(]"))[0],"number",pv.line);
            validate_list.add(ltv);
        }

    }


    public void assertBoolType(ParserVal pv1, ParserVal pv2, ParserVal pvOP) throws Exception{
        if(debug){
            System.err.println("TypeTracker::assertNumberType()::type1 " + pv1.obj);
            System.err.println("TypeTracker::assertNumberType()::type2 " + pv2.obj);
            System.err.println("TypeTracker::assertNumberType()::op " + pvOP.sval);
        }
        String t1 = (String)pv1.obj;
        String t2 = (String)pv2.obj;
        if((!t1.equals("boolean") && !t1.equals("not_def")) || (!t2.equals("boolean") && !t2.equals("not_def")))
            throw new Exception ("Line: "+pv1.line+
                                " Type Error.  Performed " + t1.toUpperCase() + " " + pvOP.sval + " " + t2.toUpperCase() +
                                ".\nConfirm the types are both boolean expressions.");

        /*If we don't throw a type error, make sure that variables are not "not_def
        and if they are, record this info so that we can check type later when
         the programmer defines the function.*/
        if(pv1.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv1.sval.split("[(]"))[0],"boolean",pv1.line);
            validate_list.add(ltv);
        }
        if(pv2.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv2.sval.split("[(]"))[0],"boolean",pv2.line);
            validate_list.add(ltv);
        }

    }


    public void assertBoolType(ParserVal pv) throws Exception{
        if(debug)System.err.println("TypeTracker::assertBool()::type1 " + pv.obj);
        
        if(!pv.obj.equals("boolean") && !pv.obj.equals("not_def"))
            throw new Exception ("Line: "+pv.line+  
                                    " Type Error.\n  Required Boolean and found "+ pv.obj);

        /*If we don't throw a type error, make sure that variables are not "not_def
        and if they are, record this info so that we can check type later when
         the programmer defines the function.*/
        if(pv.obj.equals("not_def")){
            LateTypeValidation ltv = new LateTypeValidation((pv.sval.split("[(]"))[0],"boolean",pv.line);
            validate_list.add(ltv);
        }

    }

    private void doLateTypeValidation(String id, String type) throws Exception{
        String errorMsg = "";
        boolean throwError = false;
        
        /*Loop through the LateValidation pairs to see if the id is in any of them.
        if it is then remove it and add the other in the pair to the single set with
        it's expected type*/
        for(int i = 0; i < pair_list.size(); i++){
            LateTypeValidationPair pair = pair_list.get(i);
            LateTypeValidation obj1 = pair.ltv1;
            LateTypeValidation obj2 = pair.ltv2;

            if(obj1.name.equals(id)){
                if(pair.canBeType(type)){
                    obj2.expectedType = type;
                    validate_list.add(obj2);
                    pair_list.removeElementAt(i);
                    i--;
                }
                else{ //Error with type
                    errorMsg.concat("Type Error.  Line " + obj1.line + ".  "  + obj1.name + " is the wrong type.\n");
                    pair_list.removeElementAt(i);
                    i--;
                    throwError = true;
                }
            }
            else if(obj2.name.equals(id)){
                if(pair.canBeType(type)){
                    obj1.expectedType = type;
                    validate_list.add(obj1);
                    pair_list.removeElementAt(i);
                    i--;
                }
                else{ //Error with type
                    errorMsg.concat("Type Error.  Line " + obj2.line + ".  "  + obj2.name + " is the wrong type.\n");
                    pair_list.removeElementAt(i);
                    i--;
                    throwError = true;
                }
            }

        }//end for

        /*Now loop through the singles and see if there are any type errors*/
        for(int i = 0; i < validate_list.size(); i++){
            LateTypeValidation obj = validate_list.get(i);
            if(obj.name.equals(id)){
                if(obj.expectedType.equals(type)){
                    //No error here, just remove the item from the list
                    validate_list.removeElementAt(i);
                    i--;
                }
                else{
                    //Error.  Print message.
                    errorMsg =errorMsg.concat("Type Error.  Line " +
                            obj.line + ".  "  + obj.name + " returns " + type.toUpperCase() +
                            " but should return " + obj.expectedType.toUpperCase() +".\n");
                    validate_list.removeElementAt(i);
                    i--;
                    throwError = true;
                }
            }

        }//end for

        if(throwError)
            throw new Exception(errorMsg);
    }


}


class LateTypeValidation{
    protected String name;
    protected String expectedType;
    protected int line;

    public LateTypeValidation(){
    }

    public LateTypeValidation(String name, String expectedType, int line) {
        this.name = name;
        this.expectedType = expectedType;
        this.line = line;
    }
}

class LateTypeValidationPair{
    protected LateTypeValidation ltv1;
    protected LateTypeValidation ltv2;
    
    /*These are flags.
     TRUE means that the unknowns can be that type
     FALSE means they can't.*/
    boolean canBeNumber = false;
    boolean canBeString = false;
    boolean canBeBoolean = false;
    boolean canBeArray = false;
    boolean canBeHash = false;

    public LateTypeValidationPair(LateTypeValidation obj1, LateTypeValidation obj2){
        this.ltv1 = obj1;
        this.ltv2 = obj2;
    }

    public boolean canBeType(String type){
        if("string".equals(type))
            return this.isCanBeString();
        else if("number".equals(type))
            return this.isCanBeNumber();
        else if("boolean".equals(type))
            return this.isCanBeBoolean();
        else if("ArrayList".equals(type))
            return this.isCanBeArray();
        else if("HashMap".equals(type))
            return this.isCanBeHash();
        else
            return false;
    }

    public boolean isCanBeArray() {
        return canBeArray;
    }

    public void setCanBeArray(boolean canBeArray) {
        this.canBeArray = canBeArray;
    }

    public boolean isCanBeBoolean() {
        return canBeBoolean;
    }

    public void setCanBeBoolean(boolean canBeBoolean) {
        this.canBeBoolean = canBeBoolean;
    }

    public boolean isCanBeHash() {
        return canBeHash;
    }

    public void setCanBeHash(boolean canBeHash) {
        this.canBeHash = canBeHash;
    }

    public boolean isCanBeNumber() {
        return canBeNumber;
    }

    public void setCanBeNumber(boolean canBeNumber) {
        this.canBeNumber = canBeNumber;
    }

    public boolean isCanBeString() {
        return canBeString;
    }

    public void setCanBeString(boolean canBeString) {
        this.canBeString = canBeString;
    }
}