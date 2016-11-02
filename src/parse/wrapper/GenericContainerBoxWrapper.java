package parse.wrapper;

import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.googlecode.mp4parser.AbstractContainerBox;

/**
 * <h1>Wrapper for the DataReferenceBox, SampleDescriptionBox and MetaBox classes of mp4parser</h1>
 * <p>GenericContainerBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class GenericContainerBoxWrapper implements Wrapper {

	private AbstractContainerBox box;
	
	/**
	 * The constructor of the GenericContainerBoxWrapper class.
	 * @param box A box of type AbstractContainerBox.
	 */
	public GenericContainerBoxWrapper(AbstractContainerBox box) {
		this.box = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		int version = 0, flags = 0;
		if (this.box instanceof DataReferenceBox) {
			version = ((DataReferenceBox) this.box).getVersion();
			flags = ((DataReferenceBox) this.box).getFlags();
		} else if (this.box instanceof SampleDescriptionBox) {
			version = ((SampleDescriptionBox) this.box).getVersion();
			flags = ((SampleDescriptionBox) this.box).getFlags();
		} else if (this.box instanceof MetaBox) {
			version = ((MetaBox) this.box).getVersion();
			flags = ((MetaBox) this.box).getFlags();
		}
		
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("version=").append(version);
        result.append(";");
        result.append("flags=").append(flags);
        result.append(";");
		result.append("count=0");
        result.append("}");
		return result.toString();
	}
}