package tool;

import org.jdom2.Document;
import org.jdom2.Element;

import com.coremedia.iso.IsoFile;

import io.*;
import parse.*;

public class VFT {
	 
	public static String getXmlContainer(String url, String xmlDestinationPath) throws Exception {
		String filename = null;
		try {
			filename = parser(url, xmlDestinationPath);
			System.out.println("XML file '" + filename + ".xml' created.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Could not parse video: " + url);
		}
		return filename;
	}
	
	public static String parser(String url, String xmlDestinationPath) throws Exception {
		//save the container of the video on a xml file and return the xml filename
		Element root = new Element("root");
		//TODO modelName and phoneBrandName should be read from the console during the parsing phase
		root.setAttribute("modelName", "phoneBrandName");
		//create an ISOFILE using the FileReaderSaver
		FileReaderSaver fileSaver = new FileReaderSaver(url, xmlDestinationPath, true);
		IsoFile isoFile = fileSaver.getIsoFile();
		BoxParser boxparser = new BoxParser(isoFile);
		boxparser.getBoxes(null, root);
		//save the content of the container on a xml file using the saveOnFile function of the FileReaderSaver class
		fileSaver.saveOnFile(new Document(root));
		return fileSaver.getFilename();
	}
	
}
