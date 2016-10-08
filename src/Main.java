
import static tool.VFT.parser;

public class Main {

	public static void main(String args[]) throws Exception {
		
		String url1 = "/home/saverio/Projects/vft-light/dataset/garden_move_1.MOV";
		String url2 = "/home/saverio/Projects/vft-light/dataset/sky_move_1.mp4";
		String xmlDestinationPath = "/home/saverio/Projects/vft-light/dataset/";
		
		parser(url1, xmlDestinationPath);
		parser(url2, xmlDestinationPath);
		
	}
	
}
