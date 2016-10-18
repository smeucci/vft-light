package parse.wrapper;

import com.coremedia.iso.boxes.Box;

public class DefaultBoxWrapper {

	private Box box;
	
	public DefaultBoxWrapper(Box box) {
		this.box = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("stuff=").append(this.box.toString());
		result.append("}");		
		return result.toString();
	}
}
