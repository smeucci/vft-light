package parse.wrapper;

import com.coremedia.iso.boxes.fragment.MovieExtendsHeaderBox;

/**
 * <h1>Wrapper for the MovieExtendsHeaderBox class of mp4parser</h1>
 * <p>MovieExtendsHeaderBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class MovieExtendsHeaderBoxWrapper {

	private MovieExtendsHeaderBox mehd;
	
	/**
	 * The constructor of the MovieExtendsHeaderBoxWrapper class.
	 * @param box A box of type MovieExtendsHeaderBox.
	 */
	public MovieExtendsHeaderBoxWrapper(MovieExtendsHeaderBox box) {
		this.mehd = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("fragmentDuration=").append(this.mehd.getFragmentDuration());
		result.append(";");
		result.append("version=").append(this.mehd.getVersion());
		result.append(";");
		result.append("flags=").append(this.mehd.getFlags());
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();
	}
	
}
