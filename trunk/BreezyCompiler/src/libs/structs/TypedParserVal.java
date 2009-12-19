package libs.structs;

import src.ParserVal;

public class TypedParserVal extends ParserVal {
	
	public TypedParserVal(Object value, String type) {
		super.obj = value;
		this.type = type;
	}

        public TypedParserVal(Object value, String type, String scope){
		super.obj = value;
		this.type = type;
                this.scope = scope;
	}
        
	public String type;
        public String scope;
	
	public String convertToCode() {
		return "new TypedParserVal<" + type + ">(" + obj.toString() + ")";
	}
}
