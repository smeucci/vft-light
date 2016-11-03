package com.vftlite.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* <h1>Build a node.</h1>
* <p> The Node class implements the interface Tree.
* It is used as the Composite class in the Composite
* pattern to implements trees.
* </p>
*
* @author  Saverio Meucci
*/
public class Node implements Tree {

	private int ID;
	private int IDFather;
	private int level;
	private int numChildren;
	private String name;
	private Tree father;
	private List<Tree> children;
	private List<Field> fields;
	
	/**
	 * The constructor of the Node class.
	 * @param ID The id of the node.
	 * @param name The name of the node.
	 * @param level The level (depth) of the node.
	 * @param father The father of the node.
	 * @param fields The list of fields of the node.
	 */
	public Node(int ID, String name, int level, Tree father, List<Field> fields) {
		this.name = name;
		this.children = new ArrayList<Tree>();
		this.father = father;
		this.numChildren = 0;
		this.level = level;
		this.fields = fields;
		this.ID = ID;
		if (father == null) {
			this.IDFather = -1;
		} else {
			this.IDFather = father.getID();
		}
	}
	
	/**
	 * This method is used to iterate through the list of
	 * children.
	 * @return An iterator of tree for the list of children.
	 */
	public Iterator<Tree> iterator() {
		return children.iterator();
	}

	/**
	 * Set the name of the node.
	 * @param name A string representing the name of the node.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the name of the node
	 * @return It returns the name of the node.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Add another tree to the list of children of the node.
	 * @param c A Tree object to add to the list.
	 * @throws Exception If something goes wrong when adding an element
	 * to a List.
	 */
	public void addChild(Tree c) throws Exception {
		this.children.add(c);
		this.numChildren++;		
	}
	
	/**
	 * Remove a child tree from the list of children of the node.
	 * @param c The Tree object to remove from the list.
	 * @throws Exception If something goes wrong when removing an element
	 * from a List.
	 */
	public void removeChild(Tree c) throws Exception {
		this.children.remove(c);		
	}

	/**
	 * This is method is used to accept a visit to navigate the node and
	 * its children from an object implementing the Visitor pattern.
	 * @param v The Visitor object.
	 */
	public void acceptVisit(Visitor v) {
		v.visitNode(this);
	}

	/**
	 * Set the father for the node.
	 * @param f The Tree object to set as father.
	 */
	public void setFather(Tree f) {
		this.father = f;
	}

	/**
	 * Get the father of the node.
	 * @return The father, also a tree.
	 */
	public Tree getFather() {
		return this.father;
	}

	/**
	 * Get the number of children of the node.
	 * @return The size of the list of children.
	 */
	public int getNumChildren() {
		return this.numChildren;
	}
	
	/**
	 * Get the level of the node. Start from 0 at the root;
	 * every child increments by one the level of the father.
	 * @return The level of the node.
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * Set the level of the node.
	 * @param l The new level to set.
	 */
	public void setLevel(int l) {
		this.level = l;
	}

	/**
	 * This method is used to return as a string the list of fields
	 * of the node. Couples of name=value.
	 * @return A String representing all fields as a string.
	 */
	public String getAllFieldsAsString() {
		if (this.fields.size() == 0) {
			return null;
		} else {
			StringBuilder result = new StringBuilder();
			for (Field a: this.fields) {
				result.append(a.getName());
				result.append("=");
				result.append(a.getValue());
				result.append("\n");
			}
			return result.toString();
		}
	}

	/**
	 * Set an id for the node.
	 * @param ID The id to set.
	 */
	public void setID(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Get the id of the node.
	 * @return The id of the node.
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Get the id of the father.
	 * @return The id of the father.
	 */
	public int getIDFather() {
		return this.IDFather;
	}

	/**
	 * Get the value of field of the node given the name.
	 * @param name The name of the field.
	 * @return The value of the field. It returns null if there is no field
	 * corresponding to that name.
	 */
	public String getFieldValue(String name) {
		for (Field a: this.fields) {
			if (a.getName().equals(name)) {
				return a.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Get a field of the node given a name.
	 * @param name The name of the field to get.
	 * @return The field of the node. It returns null if there is no field
	 * corresponding to that name.
	 */
	public Field getFieldByName(String name) {
		for (Field a: this.fields) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Get the list of fields of the node.
	 * @return The list of fields of the node.
	 */
	public List<Field> getFieldsList() {
		return new ArrayList<Field>(this.fields);
	}

	/**
	 * Set the list of fields of the node.
	 * @param attr The list of fields to set.
	 */
	public void setFieldsList(List<Field> fields) {
		this.fields = fields;		
	}
	
	/**
	 * Add a field to the list of fields.
	 * @param f The field to add.
	 */
	public void addField(Field f) {
		this.fields.add(f);
	}
	
	/**
	 * Add a field to the list of fields, given a name and a value.
	 * @param name The name of the field.
	 * @param value The value of the field.
	 */
	public void addField(String name, String value) {
		this.fields.add(new Field(name, value));
	}
	
	/**
	 * Get a tree child from the list of children by its name.
	 * @param name The name of the tree child.
	 * @return The tree child. It returns null if there is no tree
	 * corresponding to that name.
	 */
	public Tree getChildByName(String name) {
		for (Tree child: this.children) {
			if (child.getName().equals(name)) {
				return child;
			}
		}
		return null;		
	}
	
	/**
	 * This method is used to clone a Tree object. Shallow copy.
	 * @return It returns a new Tree that is
	 * a clone of the node.
	 */
	public Tree clone() {
		return new Node(this.ID, this.name, this.level, this.father, this.fields);
	}
	
	/**
	 * This method is used to clone a Tree object. 
	 * Deep copy: it also clones the children and the field objects.
	 * @return It returns a new Tree that is
	 * a deep copy of the tree.
	 * @throws Exception If something goes wrong when adding
	 * a child.
	 */
	public Tree cloneAll() throws Exception {
		List<Field> fields = new ArrayList<Field>();
		for (Field f: this.fields) {
			fields.add(new Field(f.getName(), f.getValue()));
		}
		Tree t = new Node(this.ID, this.name, this.level, this.father, fields);
		for (Tree child: this.children) {
			Tree c = child.cloneAll();
			c.setFather(t);
			t.addChild(c);
		}
		return t;
	}
	
	/**
	 * This method is used to represents as string
	 * the attribute of the node as string.
	 * @return It returns the attributes of the 
	 * node as string.
	 */
	public String toString() {
		return "Node[name=" + this.name + ", "
				+ "ID=" + this.ID + ", "
				+ "nameFather=" + this.father.getName() + ", "
				+ "IDFather=" + this.IDFather + ", "
				+ "level=" + this.level + ", "
				+ "numChildren=" + this.numChildren + "]";
	}

}