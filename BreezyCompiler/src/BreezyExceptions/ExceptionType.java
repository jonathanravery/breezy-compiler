/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BreezyExceptions;

/**
 *
 * @author Leighton Minor
 */
public enum ExceptionType {

    TYPE("type error"),
    FUNCTION_ENDING("function ending"),
    DUPLICATE_MAIN("duplicate main"),
    DUPLICATE_ID("duplicate variable name"),
    VARIABLE_MISSING("identifier not found"),
    SYNTAX("syntax error"),
    INVALID_MAIN_PARAMS("invalid parameter/s"),
    FUNCTION_NOT_DEFINED("function not defined"),
    NO_MAIN("no main");
    private final String name;

    ExceptionType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
