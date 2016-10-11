package parse.wrappers;

import com.coremedia.iso.boxes.HandlerBox;

public class HandlerBoxWrapper implements Wrapper {

	private HandlerBox hdlr;
	
	public HandlerBoxWrapper(HandlerBox box) {
		this.hdlr = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("[");
        result.append("handlerType=").append(this.hdlr.getHandlerType());
        result.append(";");
        if (this.hdlr.getName().hashCode() == 0) {
        	result.append("name=null");
        } else {
        	result.append("name=").append(this.hdlr.getName().replace("HandlerBox[", "").replaceAll("||", ""));
        }
        result.append(";");
        result.append("version=").append(this.hdlr.getVersion());
        result.append(";");
        result.append("flags=").append(this.hdlr.getFlags());
        result.append("]");
		return result.toString();
	}
	
}
