/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libs.structs;

/**
 *
 * @author Leighton Minor
 */
public enum Scope {

    LOCAL("LOCAL"),
    GLOBAL("GLOBAL");
    private final String name;

    Scope(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
