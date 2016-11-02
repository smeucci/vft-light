package parse.wrapper;

import java.util.List;

import com.coremedia.iso.boxes.FileTypeBox;

/**
 * <h1>Wrapper for the FileTypeBox class of mp4parser</h1>
 * <p>FileTypeBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class FileTypeBoxWrapper {

	private FileTypeBox ftyp;
	
	/**
	 * The constructor of the FileTypeBoxWrapper class.
	 * @param box A box of type FileTypeBox.
	 */
	public FileTypeBoxWrapper(FileTypeBox box) {
		this.ftyp = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("majorBrand=").append(this.ftyp.getMajorBrand());
		result.append(";");
		result.append("minorVersion=").append(this.ftyp.getMinorVersion());
		List<String> compatibleBrands = this.ftyp.getCompatibleBrands();
		for (int i = 0; i < compatibleBrands.size(); i++) {
			result.append(";");
			result.append("compatibleBrand_" + (i + 1) + "=").append(compatibleBrands.get(i));
		}
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();		
	}
	
}
