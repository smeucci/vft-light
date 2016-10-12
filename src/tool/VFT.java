package tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import com.coremedia.iso.IsoFile;

import draw.Painter;
import io.*;
import parse.*;
import tree.Field;
import tree.Leaf;
import tree.Node;
import tree.Tree;

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
	
	public static Tree buildTreeFromXML(String url, String xmlDestinationPath) throws Exception {
		FileReaderSaver fileReader = new FileReaderSaver(url, xmlDestinationPath, false);
		Document document = fileReader.getDocumentFromXMLFile();
		
		Element root = document.getRootElement();
		Tree tree = getChildren(root, null, 0, 0);
		return tree;
	}
	
	private static Tree getChildren(Element root, Tree father, int id, int level) throws Exception {
		
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
			tree = new Leaf(id, root.getName(), level, father, fields);
			return tree;
		} else {
			tree = new Node(id, root.getName(), level, father, fields);
			Iterator<Element> iterator = children.iterator();
			while (iterator.hasNext()) {
				Tree child = getChildren(iterator.next(), tree, id + 1, level + 1);
				tree.add(child);
				id++;
			}
		}
		
		return tree;
	}
	
	public static void drawTree(String url, String xmlDestinationPath, String name) throws Exception {
		Tree tree = buildTreeFromXML(url, xmlDestinationPath);
		Painter.painter(tree, 1200, 600, name);
	}
	
}