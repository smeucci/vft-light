package parse.wrapper;

public class RootWrapper {

	private String modelName;
	
	public RootWrapper(String modelName) {
		this.modelName = modelName;
	};
	
	public String toString() {
		//TODO modelName and phoneBrandName should be read from the console during the parsing phase
		return "{modelName=" + this.modelName + "}";
	}
}
