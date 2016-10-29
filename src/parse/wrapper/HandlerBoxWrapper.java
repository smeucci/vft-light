package parse.wrapper;

import com.coremedia.iso.boxes.HandlerBox;

/**
 * <h1>Wrapper for the HandlerBox class of mp4parser</h1>
 * <p>HandlerBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class HandlerBoxWrapper implements Wrapper {

	private HandlerBox hdlr;
	
	/**
	 * The constructor of the HandlerBoxWrapper class.
	 * @param box A box of type HandlerBox.
	 */
	public HandlerBoxWrapper(HandlerBox box) {
		this.hdlr = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
        result.append("handlerType=").append(this.hdlr.getHandlerType());
        result.append(";");
        if (this.hdlr.getName().hashCode() == 0) {
        	result.append("name=null");
        } else {
        	result.append("name=").append(this.hdlr.getName().replace("HandlerBox[", "").replaceAll("||", "").replace(",", ""));
        }
        result.append(";");
        result.append("version=").append(this.hdlr.getVersion());
        result.append(";");
        result.append("flags=").append(this.hdlr.getFlags());
        result.append("}");
		return result.toString();
	}
	
}
