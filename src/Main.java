
import static tool.VFT.*;

public class Main {

	public static void main(String args[]) throws Exception {
		
		String[] urls = {
				"/media/saverio/DATA/dataset-righini/videos/ipad2_giulia/outdoor/garden_move_1.MOV",
				"/media/saverio/DATA/dataset-righini/videos/galaxytrendplus_davide/flat/sky_move_1.mp4",
				"/media/saverio/DATA/dataset-righini/videos/galaxys3_dasara/indoor/uni_move_1.mp4",
				"/media/saverio/DATA/dataset-righini/videos/huaweig6_rossana/outdoor/road_move_1.mp4",
				"/media/saverio/DATA/dataset-righini/videos/galaxytaba_ilaria/outdoor/railway_move_1.mp4",
				"/media/saverio/DATA/dataset-righini/videos/ipadmini_marco/indoor/uni_move_1.MOV"};
		String xmlDestinationPath = "/home/saverio/Projects/vft-light/dataset/";
		
		getXmlContainer(urls[0], xmlDestinationPath);
		
	}
	
}