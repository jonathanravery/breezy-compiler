/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BreezyExceptions;

/**
 *
 * @author Leighton Minor
 */
public class BreezyException extends Exception {

    private static final long serialVersionUID = -6227538199974405147L;
    private String errorMsg = "";

    public BreezyException(int line, String error, String msg) {
        super(msg);

        errorMsg = "";
        errorMsg = errorMsg.concat("Line " + line + ". ");
        errorMsg = errorMsg.concat(error.toUpperCase() + " :: ");
        errorMsg = errorMsg.concat(msg);

    }

    public BreezyException(Exception e) {
        super(e);
    }

    @Override
    public String getMessage() {
        return this.errorMsg;
    }
}
