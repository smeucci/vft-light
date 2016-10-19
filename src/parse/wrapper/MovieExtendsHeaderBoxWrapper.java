package parse.wrapper;

import com.coremedia.iso.boxes.fragment.MovieExtendsHeaderBox;

public class MovieExtendsHeaderBoxWrapper {

	private MovieExtendsHeaderBox mehd;
	
	public MovieExtendsHeaderBoxWrapper(MovieExtendsHeaderBox box) {
		this.mehd = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("fragmentDuration=").append(this.mehd.getFragmentDuration());
		result.append(";");
		result.append("version=").append(this.mehd.getVersion());
		result.append(";");
		result.append("flags=").append(this.mehd.getFlags());
		result.append("}");
		return result.toString();
	}
	
}
