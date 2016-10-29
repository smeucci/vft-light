package parse.wrapper;

import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.EditListBox.Entry;

/**
 * <h1>Wrapper for the EditListBox class of mp4parser</h1>
 * <p>EditListBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class EditListBoxWrapper implements Wrapper {

	private EditListBox elst;
	
	/**
	 * The constructor of the EditListBoxWrapper class.
	 * @param box A box of type EditListBox.
	 */
	public EditListBoxWrapper(EditListBox box) {
		this.elst = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
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
