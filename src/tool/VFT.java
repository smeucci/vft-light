package tool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

//import designResultTree.*;
import io.*;
import parse.*;
import static util.Util.*;

public class VFT {

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
		//boxparser.getBoxes(isoFile, root);
		boxparser.getBoxes_recursive(isoFile, null, root); //TODO remove isoFile, pass it to the constructor
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
		//boxparser.getBoxes(isoFile, root);
		boxparser.getBoxes_recursive(isoFile, null, root); //TODO remove isoFile, pass it to the constructor
		//save the content of the container on a xml file using the saveOnFile function of the FileReaderSaver class
		fileSaver.saveOnFile(new Document(root));
		return fileSaver.getFilename();
	}
	
}
