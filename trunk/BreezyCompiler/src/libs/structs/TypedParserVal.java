package libs.structs;

import src.ParserVal;

public class TypedParserVal extends ParserVal {
	
	public TypedParserVal(Object value, String type) {
		super.obj = value;
		this.type = type;
	}
	
	public String type;
}
