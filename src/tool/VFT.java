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
				FileReaderSaver fileSaver = new FileReaderSaver(url, xmlDestinationPath);
				//create an ISOFILE using the FileReaderSaver
				IsoFile isoFile = fileSaver.getIsoFile();
				Element root = parser(isoFile);
				//save the content of the container on a xml file using the saveOnFile function of the FileReaderSaver class
				fileSaver.saveOnFile(new Document(root));
				System.out.println("XML file '" + xmlDestinationPath + "/" + fileSaver.getFilename() + ".xml' created.");
			} else {
				System.err.println("Wrong input format. The input file should be a video file (mp4 or mov).");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Could not parse video: " + url);
		}
		return filename;
	}
	
	public static Element parser(IsoFile isoFile) throws Exception {
		//save the container of the video on a xml file and return the xml filename
		Element root = new Element("root");
		//TODO modelName and phoneBrandName should be read from the console during the parsing phase
		root.setAttribute("modelName", "phoneBrandName");
		BoxParser boxparser = new BoxParser(isoFile);
		boxparser.getBoxes(null, root);
		return root;
	}
	
	public static void draw(String url, String name) throws Exception {
		if (url.endsWith(".xml")) {
			Tree tree = buildTreeFromXMLFile(url);
			Painter.painter(tree, 1200, 600, name);
		} else {
			System.err.println("Wrong input format. The input file should be a xml file.");
		}
	}
	
	public static Tree buildTreeFromXMLFile(String url) throws Exception {
		FileReaderSaver fileReader = new FileReaderSaver(url);
		Document document = fileReader.getDocumentFromXMLFile();
		Element root = document.getRootElement();
		Tree tree = buildTree(root, null, 0);
		return tree;
	}
	
	public static Tree buildTreeFromXMLDocument(Document document) throws Exception {
		Element root = document.getRootElement();
		Tree tree = buildTree(root, null, 0);
		return tree;
	}
	
	public static Document buildXMLDocumentFromTree(Tree tree) throws Exception {
		Element root = buildXML(tree);
		return new Document(root);
	}
	
	private static Element buildXML(Tree tree) throws Exception {
		Element root = new Element(tree.getName());
		List<Field> fields = tree.getFieldsList();
		
		for (Field f: fields) {
			root.setAttribute(f.getName(), f.getValue());
		}
		
		Iterator<Tree> iterator = tree.iterator();
		if (iterator != null) {
			while(iterator.hasNext()) {
				Element child = buildXML(iterator.next());
				root.addContent(child);
			}
		}
		
		return root;
	}
	
	private static Tree buildTree(Element root, Tree father, int level) throws Exception {
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
				Tree child = buildTree(iterator.next(), tree, level + 1);
				tree.addChild(child);
			}
		}
		
		return tree;
	}
	
}