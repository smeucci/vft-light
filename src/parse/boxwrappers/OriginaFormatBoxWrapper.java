package parse.boxwrappers;

import com.coremedia.iso.boxes.OriginalFormatBox;

public class OriginaFormatBoxWrapper {

	private OriginalFormatBox frma;
	
	public OriginaFormatBoxWrapper(OriginalFormatBox box) {
		this.frma = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
        result.append("[");
        result.append("dataFormat=").append(this.frma.getDataFormat());
        result.append("]");
		return result.toString();
	}
	
}
