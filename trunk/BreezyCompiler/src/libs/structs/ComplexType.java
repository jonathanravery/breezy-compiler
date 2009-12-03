package libs.structs;

public class ComplexType<T> {
	private Object actualContent;
	private Class<T> classType;
	
	private ComplexType(Object actualContent2) {
		this.actualContent = actualContent2;
	}
	
}
