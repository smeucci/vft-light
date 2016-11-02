package parse.wrapper;

import com.coremedia.iso.boxes.fragment.MovieFragmentBox;

/**
 * <h1>Wrapper for the MovieFragmentBox class of mp4parser</h1>
 * <p>MovieFragmentBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class MovieFragmentBoxWrapper {

	private MovieFragmentBox moof;
	
	/**
	 * The constructor of the MovieFragmentBoxWrapper class.
	 * @param box A box of type MovieFragmentBox.
	 */
	public MovieFragmentBoxWrapper(MovieFragmentBox box) {
		this.moof = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		return "{trackCount=" + this.moof.getTrackCount() + ";count=0}";
	}
	
}
