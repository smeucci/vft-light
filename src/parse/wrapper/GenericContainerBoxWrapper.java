package parse.wrapper;

import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.googlecode.mp4parser.AbstractContainerBox;

public class GenericContainerBoxWrapper implements Wrapper {

	private AbstractContainerBox box;
	
	public GenericContainerBoxWrapper(AbstractContainerBox box) {
		this.box = box;
	}
	
	public String toString() {
		int version = 0, flags = 0;
		if (box instanceof DataReferenceBox) {
			version = ((DataReferenceBox) this.box).getVersion();
			flags = ((DataReferenceBox) this.box).getFlags();
		} else if (box instanceof SampleDescriptionBox) {
			version = ((SampleDescriptionBox) this.box).getVersion();
			flags = ((SampleDescriptionBox) this.box).getFlags();
		} else if (box instanceof MetaBox) {
			version = ((MetaBox) this.box).getVersion();
			flags = ((MetaBox) this.box).getFlags();
		}
		
		StringBuilder result = new StringBuilder();
		result.append("[");
		result.append("version=").append(version);
        result.append(";");
        result.append("flags=").append(flags);
        result.append("]");
		return result.toString();
	}
}