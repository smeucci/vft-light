package parse.boxwrappers;

import com.googlecode.mp4parser.AbstractFullBox;

public class GenericBoxWrapper implements Wrapper {

	private AbstractFullBox box;
	
	public GenericBoxWrapper(AbstractFullBox box) {
		this.box = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(box.toString().replaceAll("\\]|\\}", ""));
		result.append(";");
		result.append("version=").append(this.box.getVersion());
		result.append(";");
		result.append("flags=").append(this.box.getFlags());
		result.append("]");
		return result.toString();		
	}
}
