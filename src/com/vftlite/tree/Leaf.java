package com.vftlite.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* <h1>Build a leaf.</h1>
* <p> The Leaf class implements the interface Tree.
* It is used as the Leaf class in the Composite
* pattern to implements trees.
* </p>
*
* @author  Saverio Meucci
*/
public class Leaf implements Tree {

	private int ID;
	private int IDFather;
	private int level;
	private int numChildren;
	private String name;
	private Tree father;
	private List<Field> fields;
	
	/**
	 * The constructor of the Leaf class.
	 * @param ID The id of the leaf.
	 * @param name The name of the leaf.
	 * @param level The level (depth) of the leaf.
	 * @param father The father of the leaf.
	 * @param fields The list of fields of the leaf.
	 */
	public Leaf(int ID, String name, int level, Tree father, List<Field> fields) {
		this.name = name;
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
	 * Since a Leaf object cannot have children, this method
	 * returns null.
	 * @return It returns null.
	 */
	public Iterator<Tree> iterator() {
		return null;
	}
	
	/**
	 * Set the name of the leaf.
	 * @param name A string representing the name of the leaf.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the name of the leaf
	 * @return It returns the name of the leaf.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Since a Leaf cannot have children, throws an exception.
	 * @throws Exception
	 */
	public void addChild(Tree c) throws Exception {
		throw new Exception();		
	}

	/**
	 * Since a Leaf cannot have children, throws an exception.
	 * @throws Exception
	 */
	public void removeChild(Tree c) throws Exception {
		throw new Exception();
	}

	/**
	 * This is method is used to accept a visit to navigate the leaf and
	 * its children from an object implementing the Visitor pattern.
	 * @param v The Visitor object.
	 */
	public void acceptVisit(Visitor v) {
		v.visitLeaf(this);
	}

	/**
	 * Set the father for the leaf.
	 * @param f The Tree object to set as father.
	 */
	public void setFather(Tree f) {
		this.father = f;
	}
	
	/**
	 * Get the father of the leaf.
	 * @return The father, also a tree.
	 */
	public Tree getFather() {
		return this.father;
	}

	/**
	 * Get the number of children of the leaf.
	 * @return The size of the list of children.
	 */
	public int getNumChildren() {
		return this.numChildren;
	}

	/**
	 * Get the level of the leaf. Start from 0 at the root;
	 * every child increments by one the level of the father.
	 * @return The level of the leaf.
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * Set the level of the leaf.
	 * @param l The new level to set.
	 */
	public void setLevel(int l) {
		this.level = l;		
	}

	/**
	 * This method is used to return as a string the list of fields
	 * of the leaf. Couples of name=value.
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
	 * Set an id for the leaf.
	 * @param ID The id to set.
	 */
	public void setID(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Get the id of the leaf.
	 * @return The id of the leaf.
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
	 * Get the value of field of the leaf given the name.
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
	 * Get a field of the leaf given a name.
	 * @param name The name of the field to get.
	 * @return The field of the leaf. It returns null if there is no field
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
	 * Get the list of fields of the leaf.
	 * @return The list of fields of the leaf.
	 */
	public List<Field> getFieldsList() {
		return new ArrayList<Field>(this.fields);
	}
	
	/**
	 * Set the list of fields of the leaf.
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
	 * Since a Leaf cannot have children, it returns null.
	 * @return It returns null.
	 */
	public Tree getChildByName(String name) {
		return null;
	}
	
	/**
	 * This method is used to clone (shallow copy) a Tree object.
	 * @return It returns a new Tree that is
	 * a clone of the node.
	 */
	public Tree clone() {
		return new Leaf(this.ID, this.name, this.level, this.father, this.fields);
	}
	
	/**
	 * This method is used to clone (deep copy) a Tree object. 
	 * It also clones the field objects.
	 * @return It returns a new Tree that is
	 * a deep copy of the tree.
	 */
	public Tree cloneAll() {
		List<Field> fields = new ArrayList<Field>();
		for (Field f: this.fields) {
			fields.add(new Field(f.getName(), f.getValue()));
		}
		return new Leaf(this.ID, this.name, this.level, this.father, fields);
	}
	
	/**
	 * This method is used to represents as string
	 * the attribute of the leaf as string.
	 * @return It returns the attributes of the 
	 * leaf as string.
	 */
	public String toString() {
		return "Leaf[name=" + this.name + ", "
				+ "ID=" + this.ID + ", "
				+ "nameFather=" + this.father.getName() + ", "
				+ "IDFather=" + this.IDFather + ", "
				+ "level=" + this.level + "]";
	}	

}