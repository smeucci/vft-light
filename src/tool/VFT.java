package tool;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;

import io.*;
import parse.*;
import static util.Util.*;

public class VFT {
	 
	public static String getXmlContainer(String url, String xmlDestinationPath) throws Exception {
		String filename = null;
		try {
			filename = parser_recursive(url, xmlDestinationPath);
			System.out.println("XML file '" + filename + ".xml' created.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Could not parse video: " + url);
		}
		return filename;
	}
	
	public static String parser_recursive(String url, String xmlDestinationPath) throws Exception {
		//save the container of the video on a xml file and return the xml filename
		Element root = new Element("root");
		//TODO modelName and phoneBrandName should be read from the console during the parsing phase
		root.setAttribute("modelName", "phoneBrandName");
		//create an ISOFILE using the FileReaderSaver
		FileReaderSaver fileSaver = new FileReaderSaver(url, xmlDestinationPath, true);
		IsoFile isoFile = fileSaver.getIsoFile();
		NewBoxParser boxparser = new NewBoxParser(isoFile);
		boxparser.getBoxes(null, root);
		//save the content of the container on a xml file using the saveOnFile function of the FileReaderSaver class
		fileSaver.saveOnFile(new Document(root));
		return fileSaver.getFilename();
	}

	public static String parser(String url, String xmlDestinationPath) throws Exception {
		//choose which parser to used base on the file format (MP4 or MOV)
		String filename = null;
		try {
			if (contains(url, ".MP4") || contains(url, ".mp4")) {
				filename = parserMP4(url, xmlDestinationPath);
			} else if (contains(url, ".MOV") || contains(url, ".mov")) {
				filename = parserMOV(url, xmlDestinationPath);
			}
			System.out.println("XML file '" + filename + ".xml' created.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Could not parse video: " + url);
		}
		return filename;
	}
	
	private static String parserMP4(String url, String xmlDestinationPath) throws Exception {
		//save the container of the video on a xml file and return the xml filename
		Element root = new Element("root");
		//TODO modelName and phoneBrandName should be read from the console during the parsing phase
		root.setAttribute("modelName", "phoneBrandName");
		//create an ISOFILE using the FileReaderSaver
		FileReaderSaver fileSaver = new FileReaderSaver(url, xmlDestinationPath, true);
		IsoFile isoFile = fileSaver.getIsoFile();
		//use the BoxParserMP4 class to fill the root element, in which it is inserted the content of the container
		//of the passed video specified by url
		List<Box> boxes = isoFile.getBoxes();
		BoxParserMP4 boxparser = new BoxParserMP4(boxes);
		boxparser.getBoxes(isoFile, root);
		//save the content of the container on a xml file using the saveOnFile function of the FileReaderSaver class
		fileSaver.saveOnFile(new Document(root));
		return fileSaver.getFilename();
	}
	
	private static String parserMOV(String url, String xmlDestinationPath) throws Exception {
		//save the container of the video on a xml file and return the xml filename
		Element root = new Element("root");
		//TODO modelName and phoneBrandName should be read from the console during the parsing phase
		root.setAttribute("modelName", "phoneBrandName");
		//create an ISOFILE using the FileReaderSaver
		FileReaderSaver fileSaver = new FileReaderSaver(url, xmlDestinationPath, true);
		IsoFile isoFile = fileSaver.getIsoFile();
		//use the BoxParserMOV class to fill the root element, in which it is inserted the content of the container
		//of the passed video specified by url
		List<Box> boxes = isoFile.getBoxes();
		BoxParserMOV boxparser = new BoxParserMOV(boxes);
		boxparser.getBoxes(isoFile, root);
		//save the content of the container on a xml file using the saveOnFile function of the FileReaderSaver class
		fileSaver.saveOnFile(new Document(root));
		return fileSaver.getFilename();
	}
	
}
