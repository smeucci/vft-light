package tool;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import com.coremedia.iso.IsoFile;

import static util.Util.*;
import draw.Painter;
import io.*;
import parse.*;
import tree.Field;
import tree.Leaf;
import tree.Node;
import tree.Tree;

public class VFT {
	 
	private static int id = 0;

	public static void parse(String url, String xmlDestinationPath) throws Exception {
		try {
			if (url.toLowerCase().endsWith(".mp4") || url.toLowerCase().endsWith(".mov")) {
				FileReaderSaver fileSaver = new FileReaderSaver(url, xmlDestinationPath);
				//create an ISOFILE using the FileReaderSaver
				IsoFile isoFile = fileSaver.getIsoFile();
				Element root = parser(isoFile);
				//save the content of the container on a xml file using the saveOnFile function of the FileReaderSaver class
				fileSaver.saveOnFile(new Document(root));
				String message = "XML file '" + xmlDestinationPath + "/" + fileSaver.getFilename() + ".xml' created.";
				System.out.println(message.replace("//", "/"));
			} else {
				System.err.println("Wrong input format. The input file should be a video file (mp4 or mov).");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Could not parse video: " + url);
		}
	}
	
	public static Element parser(IsoFile isoFile) throws Exception {
		Element root = new Element("root");
		//TODO modelName and phoneBrandName should be read from the console during the parsing phase
		root.setAttribute("modelName", "phoneBrandName");
		BoxParser boxparser = new BoxParser(isoFile);
		boxparser.getBoxes(null, root);
		return root;
	}
	
	public static void batch(String datasetPath, String outputPath) throws Exception {
		File datasetFolder = new File(datasetPath);
		if (!datasetFolder.exists() || !datasetFolder.isDirectory()) {
			System.err.println("Could not find the dataset folder at '" + datasetPath + "'");
		} else if (!new File(outputPath).exists() || !new File(outputPath).isDirectory()) {
			System.err.println("Could not find the output folder at '" + outputPath + "'");
		} else {
			System.out.println("Batch parsing the dataset at '" + datasetPath + "'");
			parseDirectory(datasetFolder, outputPath);
		}
	}
	
	public static void parseDirectory(File folder, String outputPath) throws Exception {
		File[] files = folder.listFiles();
		for (File f: files) {
			if (f.isFile() && !f.getName().startsWith(".")) {
				parse(f.getAbsolutePath(), outputPath);
			} else if (f.isDirectory() && !f.getName().toLowerCase().endsWith(".not")) {
				File subfolder = new File(f.getAbsolutePath());
				new File(outputPath + "/" + f.getName()).mkdir();
				parseDirectory(subfolder, outputPath + "/" + f.getName());
			}
		}
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
	
	public static Element buildXML(Tree tree) throws Exception {
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
	
	public static Tree buildTree(Element root, Tree father, int level) throws Exception {
		Tree tree;
		List<Attribute> attr = root.getAttributes();
		List<Field> fields = new ArrayList<Field>();
		
		for (Attribute a: attr) {
			String name = a.getName();
			String value = a.getValue();
			fields.add(new Field(name, value));
		}
		
		List<Element> children = root.getChildren();
		if (children.isEmpty() && !root.getName().equals("root")) {
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
	
	public static void merge(String a, String b, String xmlDestinationPath, boolean withAttributes) throws Exception {
		try {
			Tree ta = buildTreeFromXMLFile(a);
			Tree tb = buildTreeFromXMLFile(b);
			
			mergeTree(ta, tb, withAttributes);
			
			Document document = buildXMLDocumentFromTree(ta);
			FileReaderSaver fileSaver = new FileReaderSaver("merged", xmlDestinationPath);
			fileSaver.saveOnFile(document);
			String message = "Merged '" + a + "' and '" + b + "' on XML file: "
					+ "'" + xmlDestinationPath + "/" + fileSaver.getFilename() + ".xml'";
			System.out.println(message.replace("//", "/"));
		} catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Could not merge '" + a + "' and '" + b + "'.");
		}
	}
	
	public static void mergeTree(Tree config, Tree tree, boolean withAttributes) throws Exception {
		
		if (tree.getNumChildren() > 0) {
			Tree toBeUpdated = null;
			Iterator<Tree> treeIterator = tree.iterator();
			while (treeIterator.hasNext()) {
				boolean isPresent = false;
				Tree treeChild = treeIterator.next();
					
				Iterator<Tree> configIterator = config.iterator();
				if (configIterator != null) {
					while (configIterator.hasNext() && !isPresent) {
						Tree configChild = configIterator.next();
						if (configChild.getName().equals(treeChild.getName()) && !configChild.getName().equals("trak")) {
							isPresent = true;
							toBeUpdated = configChild;
						} else if (contains(checkTrakType(treeChild), "vide") && contains(checkTrakType(configChild), "vide")) {	
							isPresent = true;
							toBeUpdated = configChild;	
						} else if (contains(checkTrakType(treeChild), "soun") && contains(checkTrakType(configChild), "soun")) {
							isPresent = true;
							toBeUpdated = configChild;
						}
					}
				}
			
				if (!isPresent) {
					toBeUpdated = treeChild.clone();
					toBeUpdated.setFather(config);
					toBeUpdated.setLevel(config.getLevel() + 1);
					config.addChild(toBeUpdated);
				}
				
				if (withAttributes) {
					checkAttributes(toBeUpdated, treeChild);
				}
				
				mergeTree(toBeUpdated, treeChild, withAttributes);		
			}	
		}
	
	}
	
	public static void checkAttributes(Tree config, Tree node) {
		
		for (Field nodeField: node.getFieldsList()) {
			
			String nodeFieldName = nodeField.getName();
			String nodeFieldValue = nodeField.getValue();
			
			if (!nodeFieldName.equals("stuff")) {
				String nodeSplit = nodeFieldValue.split("\\;")[0];
				String[] nodeFieldValues = nodeSplit.replaceAll("\\[|\\]", "").split("\\,");
				
				Field configField = config.getFieldByName(nodeFieldName);
				if (configField == null) {
					config.addField(nodeField);
					configField = config.getFieldByName(nodeFieldName);
				}
				
				String configFieldValue = configField.getValue();
				String configSplit = configFieldValue.split("\\;")[0];
				List<String> configFieldValues = new ArrayList<String>(Arrays.asList(configSplit.replaceAll("\\[|\\]", "").split("\\,")));
				
				for (int i = 0; i < nodeFieldValues.length; i++) {
					if (!configFieldValues.contains(nodeFieldValues[i])) {
						configFieldValues.add(nodeFieldValues[i]);
					}
				}
				
				String result = formatFieldValues(configFieldValues);
				configField.setvalue(result);		
			}
		}
		
	}
	
	public static String formatFieldValues(List<String> values) {
		StringBuilder result = new StringBuilder();
		result.append("[");
		for (int i = 0; i < values.size(); i++) {
			result.append(values.get(i));
			String comma = (i == values.size() - 1) ? "" : ",";
			result.append(comma);
		}
		result.append("]");
		result.append(";[");
		for (int j = 0; j < values.size(); j++) {
			result.append("0");
			String comma = (j == values.size() - 1) ? "" : ",";
			result.append(comma);
		}
		result.append("]");
		return result.toString();
	}
	
	public static String checkTrakType(Tree trak) {
		if (trak instanceof Node && trak.getName().equals("trak")) {
			Node mdia = (Node) ((Node) trak).getChildByName("mdia");
			Tree hdlr = mdia.getChildByName("hdlr");
			return hdlr.getFieldValue("handlerType");
		}
		return "";
	}
	
}
