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

/**
 * <h1>The VFT core</h1>
 * <p>VFT is a static class that implements methods to parse
 * and elaborate xml file, jdom Element and Document objects, and Tree objects.
 * 
 * @author Saverio Meucci
 */
public class VFT {
	 
	private static int id = 0;

	/**
	 * This method serves as an interface for the cli app to parse
	 * a mp4-like video container into a xml file.
	 * @param url The path of the video file.
	 * @param xmlDestinationPath The folder where to save the resulting xml file.
	 * @throws Exception
	 */
	public static void parse(String url, String xmlDestinationPath) throws Exception {
		try {
			if (url.toLowerCase().endsWith(".mp4") || url.toLowerCase().endsWith(".mov")) {
				FileReaderSaver fileSaver = new FileReaderSaver(url, xmlDestinationPath);
				//create an ISOFILE using the FileReaderSaver
				IsoFile isoFile = fileSaver.getIsoFile();
				Element root = parser(isoFile);
				//save the content of the container on a xml file using the saveOnFile function of the FileReaderSaver class
				fileSaver.saveOnFile(new Document(root));
				System.out.println("XML file '" + fileSaver.getDestinationPath() + ".xml' created.");
			} else {
				System.err.println("Wrong input format. The input file should be a video file (mp4 or mov).");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Could not parse video: " + url);
		}
	}
	
	/**
	 * This method is used to parse a IsoFile into a jdom Element.
	 *  @param isoFile The IsoFile representing the container of the video file.
	 *  @return A jdom Element.
	 */
	public static Element parser(IsoFile isoFile) throws Exception {
		Element root = new Element("root");
		//TODO modelName and phoneBrandName should be read from the console during the parsing phase
		root.setAttribute("modelName", "phoneBrandName");
		BoxParser boxparser = new BoxParser(isoFile);
		boxparser.getBoxes(null, root);
		return root;
	}
	
	/**
	 * This method servers as an interface for the cli app to
	 * parse a directory of video file containers. It also considers sufolders
	 * and recreates the same folder structure for the outputted xml files.
	 * @param datasetPath The path of the folder that needs to be parsed.
	 * @param outputPath The folder where the xml files will be saved.
	 * @throws Exception
	 */
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
	
	/**
	 * This method is used to parse a directory of video file containers. It also considers subfolders
	 * and recreates the same folder structure for the outputted xml files.
	 * @param folder A File objects representing the folder under consideration.
	 * @param outputPath The path where to save the xml files.
	 * @throws Exception
	 */
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
	
	/**
	 * This method serves as an interface for the cli app to
	 * draw a tree into a JFrame window, given an xml file.
	 * @param url The path of the xml file.
	 * @param name The name of the window.
	 * @throws Exception
	 */
	public static void draw(String url, String name) throws Exception {
		if (url.endsWith(".xml")) {
			Tree tree = buildTreeFromXMLFile(url);
			Painter.painter(tree, 1200, 600, name);
		} else {
			System.err.println("Wrong input format. The input file should be a xml file.");
		}
	}
	
	/**
	 * This method is used to build a Tree object given a xml files.
	 * @param url The path of the xml file to be drawn.
	 * @return A Tree object representing the xml file.
	 * @throws Exception
	 */
	public static Tree buildTreeFromXMLFile(String url) throws Exception {
		FileReaderSaver fileReader = new FileReaderSaver(url);
		Document document = fileReader.getDocumentFromXMLFile();
		Element root = document.getRootElement();
		Tree tree = buildTree(root, null, 0);
		return tree;
	}
	
	/**
	 * This method is used to build a Tree object given a jdom Document
	 * representing the xml file.
	 * @param document The jdom Document representing the xml file.
	 * @return A Tree object representing the jdom Document.
	 * @throws Exception
	 */
	public static Tree buildTreeFromXMLDocument(Document document) throws Exception {
		Element root = document.getRootElement();
		Tree tree = buildTree(root, null, 0);
		return tree;
	}
	
	/**
	 * This method is used to build a jdom Document from a Tree object.
	 * @param tree The Tree object.
	 * @return A jdom Document representing the Tree object.
	 * @throws Exception
	 */
	public static Document buildXMLDocumentFromTree(Tree tree) throws Exception {
		Element root = buildXML(tree);
		return new Document(root);
	}
	
	/**
	 * This method is used to build a xml file, represented as a jdom Element
	 * given a Tree object.
	 * @param tree The Tree object to represent.
	 * @return A jdom Element representing the Tree object.
	 * @throws Exception
	 */
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
	
	/**
	 * This method is used to build a Tree object, given a jdom Element.
	 * @param root The jdom Element to represent.
	 * @param father A Tree object representing the father.
	 * @param level An integer representing the level of depth of the Tree object.
	 * @return A Tree object representing a jdom Element.
	 * @throws Exception
	 */
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
	
	/**
	 * This method serves as an interface to merge two xml file
	 * into a single one. Can consider tag only and
	 * also attributes values.
	 * @param a The path of the first xml file.
	 * @param b The path of the second xml file.
	 * @param xmlDestinationPath The path where to save the merged xml file.
	 * @param withAttributes A boolean to determined is attributes need to be considered.
	 * @throws Exception
	 */
	public static void merge(String a, String b, String xmlDestinationPath, boolean withAttributes) throws Exception {
		try {
			Tree ta = buildTreeFromXMLFile(a);
			Tree tb = buildTreeFromXMLFile(b);
			
			mergeTree(ta, tb, withAttributes);
			
			Document document = buildXMLDocumentFromTree(ta);
			FileReaderSaver fileSaver = new FileReaderSaver("config", xmlDestinationPath);
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
	
	/**
	 * Given two Tree objects, merge them into a the first Tree object.
	 * Can consider tag only and also attributes values.
	 * @param config The Tree object that will be updated by the second.
	 * @param tree The Tree object to merge.
	 * @param withAttributes Whether or not consider attributes.
	 * @throws Exception
	 */
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
	
	/**
	 * This method, given two Tree objects with the same name and at the same
	 * level, check if attributes are equals. If an attributes of the second
	 * Tree object contains a value that is not present for the same attribute
	 * in the first Tree object, adds that new value to the attribute of the
	 * first Tree object.
	 * @param config The Tree object that will be updated by the second.
	 * @param tree The Tree object to merge.
	 */
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
	
	/**
	 * Format attributes value, when a new value is added.
	 * @param values A list of string of new values for an attribute.
	 * @return A formatted string that represents the new value for
	 * an attribute of the first Tree object.
	 */
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
	
	/**
	 * This method is used to determine the type of the Tree with name "trak".
	 * @param trak The Tree object with name "trak".
	 * @return The type of the trak; empty if the type cannot be determined.
	 */
	public static String checkTrakType(Tree trak) {
		if (trak instanceof Node && trak.getName().equals("trak")) {
			Node mdia = (Node) ((Node) trak).getChildByName("mdia");
			Tree hdlr = mdia.getChildByName("hdlr");
			return hdlr.getFieldValue("handlerType");
		}
		return "";
	}
	
	/**
	 * This method servers as an interface for the cli app to merge all xml files
	 * of a directory into one. It also considers subfolders.
	 * Can consider tag only and also attributes values.
	 * @param datasetPath The path of the folder containing the xml files.
	 * @param outputPath The path where to save the merged xml files.
	 * @param withAttributes Whether or not consider attributes.
	 * @throws Exception
	 */
	public static void update(String datasetPath, String outputPath, boolean withAttributes) throws Exception {
		try {
			Tree config;
			if (new File(outputPath + "/config.xml").exists()) {
				config = buildTreeFromXMLFile(outputPath + "/config.xml");
			} else {
				List<Field> fields = new ArrayList<Field>();
				fields.add(new Field("modelName", "phoneBrandName"));
				config = new Node(0, "root", 0, null, fields);
			}
			
			updateConfig(config, datasetPath, withAttributes);
			
			Document document = buildXMLDocumentFromTree(config);
			FileReaderSaver fileSaver = new FileReaderSaver("config", outputPath);
			fileSaver.saveOnFile(document);
			System.out.println("Created '" + fileSaver.getDestinationPath() + "'");
		} catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Could not create config file from dataset folder '" + datasetPath + "'.");
		}
	}
	
	/**
	 * This method is used to create a Tree object by merging all xml files in a
	 * given folder. Can consider tag only and also attributes values.
	 * @param config The Tree object that represent the merging.
	 * @param datasetPath The path of the folder containing the xml files.
	 * @param withAttributes Wheter or not consider the attributes.
	 * @throws Exception
	 */
	public static void updateConfig(Tree config, String datasetPath, boolean withAttributes) throws Exception {
		
		File folder = new File(datasetPath);
		File[] files = folder.listFiles();
		for (File f: files) {
			if (f.isFile() && !f.getName().startsWith(".") && !f.getName().endsWith("~") && !f.getName().equals("config.xml")) {
				System.out.println("Merging xml file: " + f.getAbsolutePath());
				mergeTree(config, buildTreeFromXMLFile(f.getAbsolutePath()), withAttributes);
			} else if (f.isDirectory()) {
				updateConfig(config, f.getAbsolutePath(), withAttributes);
			}
		}
	}
	
}