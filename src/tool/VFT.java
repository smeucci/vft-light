package tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import com.coremedia.iso.IsoFile;

import draw.Painter;
import io.*;
import parse.*;
import tree.Field;
import tree.Leaf;
import tree.Node;
import tree.Tree;

public class VFT {
	 
	private static int id = 0;
	
	public static String parse(String url, String xmlDestinationPath) throws Exception {
		String filename = null;
		try {
			if (!url.endsWith(".xml")) {
				filename = parser(url, xmlDestinationPath);
				System.out.println("XML file '" + xmlDestinationPath + "/" + filename + ".xml' created.");
			} else {
				System.err.println("Wrong input format. The input file should be a video file (mp4 or mov).");
			}
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
	
	public static void draw(String url, String xmlDestinationPath, String name) throws Exception {
		if (url.endsWith(".xml")) {
			Tree tree = buildTreeFromXML(url, xmlDestinationPath);
			Painter.painter(tree, 1200, 600, name);
		} else {
			System.err.println("Wrong input format. The input file should be a xml file.");
		}
	}
	
	public static Tree buildTreeFromXML(String url, String xmlDestinationPath) throws Exception {
		FileReaderSaver fileReader = new FileReaderSaver(url, xmlDestinationPath, false);
		Document document = fileReader.getDocumentFromXMLFile();
		
		Element root = document.getRootElement();
		Tree tree = getChildren(root, null, 0);
		return tree;
	}
	
	private static Tree getChildren(Element root, Tree father, int level) throws Exception {
		Tree tree;
		List<Attribute> attr = root.getAttributes();
		List<Field> fields = new ArrayList<Field>();
		
		for (Attribute a: attr) {
			String name = a.getName();
			String value = a.getValue();
			fields.add(new Field(name, value));
		}
		
		List<Element> children = root.getChildren();
		if (children.isEmpty()) {
			tree = new Leaf(id++, root.getName(), level, father, fields);
			return tree;
		} else {
			tree = new Node(id++, root.getName(), level, father, fields);
			Iterator<Element> iterator = children.iterator();
			while (iterator.hasNext()) {
				Tree child = getChildren(iterator.next(), tree, level + 1);
				tree.add(child);
			}
		}
		
		return tree;
	}
	
}