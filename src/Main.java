
import static tool.VFT.parser;

public class Main {

	public static void main(String args[]) throws Exception {
		
		String url = "/home/saverio/Projects/vft-light/dataset/garden_move_1.MOV";
		//String url = "/home/saverio/Projects/vft-light/dataset/sky_move_1.mp4";
		String xmlDestinationPath = "/home/saverio/Projects/vft-light/dataset/";
		
		parser(url, xmlDestinationPath);
		
	}
	
}
