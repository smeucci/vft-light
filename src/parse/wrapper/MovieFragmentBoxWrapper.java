package parse.wrapper;

import com.coremedia.iso.boxes.fragment.MovieFragmentBox;

public class MovieFragmentBoxWrapper {

	private MovieFragmentBox moof;
	
	public MovieFragmentBoxWrapper(MovieFragmentBox box) {
		this.moof = box;
	}
	
	public String toString() {
		return "{trackCount=" + this.moof.getTrackCount() + "}";
	}
	
}
