package parse.wrapper;

import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.EditListBox.Entry;

public class EditListBoxWrapper implements Wrapper {

	private EditListBox elst;
	
	public EditListBoxWrapper(EditListBox box) {
		this.elst = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		Entry entry = this.elst.getEntries().get(0);       
        result.append(entry.toString().replace("}", ""));
        result.append(";");
        result.append("version=").append(this.elst.getVersion());
        result.append(";");
        result.append("flags=").append(this.elst.getFlags());
        result.append("}");
		return result.toString();
	}
	
}
