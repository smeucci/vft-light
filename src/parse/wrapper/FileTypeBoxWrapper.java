package parse.wrapper;

import java.util.List;

import com.coremedia.iso.boxes.FileTypeBox;

public class FileTypeBoxWrapper {

	private FileTypeBox ftyp;
	
	public FileTypeBoxWrapper(FileTypeBox box) {
		this.ftyp = box;
	}
	
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
		result.append("}");
		return result.toString();		
	}
	
}
