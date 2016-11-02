package parse.wrapper;

import com.googlecode.mp4parser.AbstractFullBox;

/**
 * <h1>Wrapper for the AbstractFullBox class of mp4parser</h1>
 * <p>GenericBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class GenericBoxWrapper implements Wrapper {

	private AbstractFullBox box;
	
	/**
	 * The constructor of the GenericBoxWrapper class.
	 * @param box A box of type AbstractFullBox.
	 */
	public GenericBoxWrapper(AbstractFullBox box) {
		this.box = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(this.box.toString().replaceAll("\\]|\\}", "").replace("[", "{"));
		result.append(";");
		result.append("version=").append(this.box.getVersion());
		result.append(";");
		result.append("flags=").append(this.box.getFlags());
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();
	}
}
