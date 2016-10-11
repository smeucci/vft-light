package parse.boxwrappers;

import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;

public class GenericContainerBoxWrapper {

	private DataReferenceBox dref;
	private SampleDescriptionBox stsd;
	private MetaBox meta;
	
	public GenericContainerBoxWrapper(DataReferenceBox box) {
		this.dref = box;
		this.stsd = null;
		this.meta = null;
	}
	
	public GenericContainerBoxWrapper(SampleDescriptionBox box) {
		this.dref = null;
		this.stsd = box;
		this.meta = null;
	}
	
	public GenericContainerBoxWrapper(MetaBox box) {
		this.dref = null;
		this.stsd = null;
		this.meta = box;
	}
	
	public String toString() {
		int version = 0, flags = 0;
		if (this.dref != null) {
			version = this.dref.getVersion();
			flags = this.dref.getFlags();
		} else if (this.stsd != null) {
			version = this.stsd.getVersion();
			flags = this.stsd.getFlags();
		} else if (this.meta != null) {
			version = this.meta.getVersion();
			flags = this.meta.getFlags();
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
