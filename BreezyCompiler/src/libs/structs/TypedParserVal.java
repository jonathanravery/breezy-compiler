package libs.structs;

import java.util.ArrayList;
import java.util.HashMap;
import src.ParserVal;

public class TypedParserVal extends ParserVal {

    public TypedParserVal(HashMap value) {
        super.obj = value;
    }

    public TypedParserVal(ArrayList<TypedParserVal> value) {
        super.obj = value;
    }

    public TypedParserVal(String value) {
        super.obj = value;
    }

    public TypedParserVal(double value) {
        super.obj = value;
    }

    public TypedParserVal(boolean value) {
        super.obj = value;
    }

    public TypedParserVal(Object value) {
        super.obj = value;
    }

    public TypedParserVal(Object value, String type) {
        super.obj = value;
        this.type = type;
    }

    public TypedParserVal(Object value, String type, String scope) {
        super.obj = value;
        this.type = type;
        this.scope = scope;
    }
    public String type;
    public String scope;

    public String convertToCode() {
        return "new TypedParserVal(" + obj.toString() + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((obj == null) ? 0 : obj.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TypedParserVal other = (TypedParserVal) obj;
        if (obj == null) {
            if (other.obj != null) {
                return false;
            }
        } else if (!obj.equals(other.obj)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return obj.toString();
    }
}
