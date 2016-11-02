package parse.wrapper;

import java.util.List;

import com.coremedia.iso.boxes.SampleDependencyTypeBox;

/**
 * <h1>Wrapper for the SampleDependencyTypeBox class of mp4parser</h1>
 * <p>SampleDependencyTypeBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class SampleDependencyTypeBoxWrapper implements Wrapper {

	private SampleDependencyTypeBox sdtp;
	
	/**
	 * The constructor of the SampleDependencyTypeBoxWrapper class.
	 * @param box A box of type SampleDependencyTypeBox.
	 */
	public SampleDependencyTypeBoxWrapper(SampleDependencyTypeBox box) {
		this.sdtp = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		List<SampleDependencyTypeBox.Entry> entries = this.sdtp.getEntries();
		result.append("{");
		result.append("entryCount=").append(entries.size());
		result.append(";");
		result.append("version=").append(this.sdtp.getVersion());
		result.append(";");
		result.append("flags=").append(this.sdtp.getFlags());
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();
	}
}
