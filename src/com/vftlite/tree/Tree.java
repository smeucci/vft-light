package com.vftlite.tree;

import java.util.List;

/**
* <h1>Build a tree.</h1>
* <p> Tree is an interface. It is used as the Component class in the Composite
* pattern to implements trees. A tree can have children (other tree object) and 
* fields, couples of name=value. Used to represent xml files.
* </p>
*
* @author  Saverio Meucci
*/
public interface Tree extends Iterable<Tree> {
	
	/**
	 * Set the name of the tree.
	 * @param name A string representing the name of the tree.
	 */
	public void setName(String name);
	
	/**
	 * Get the name of the tree
	 * @return It returns the name of the tree.
	 */
	public String getName();
	
	/**
	 * Add another tree to the list of children of the tree.
	 * @param c A Tree object to add to the list.
	 * @throws Exception If something goes wrong when adding an element
	 * to a List.
	 */
	public void addChild(Tree c) throws Exception;
	
	/**
	 * Remove a child tree from the list of children of the tree.
	 * @param c The Tree object to remove from the list.
	 * @throws Exception If something goes wrong when removing an element
	 * from a List.
	 */
	public void removeChild(Tree c) throws Exception;
	
	/**
	 * This is method is used to accept a visit to navigate the tree and
	 * its children from an object implementing the Visitor pattern.
	 * @param v The Visitor object.
	 */
	public void acceptVisit(Visitor v);
	
	/**
	 * Set the father for the tree.
	 * @param f The Tree object to set as father.
	 */
	public void setFather(Tree f);
	
	/**
	 * Get the father of the tree.
	 * @return The father, also a tree.
	 */
	public Tree getFather();
	
	/**
	 * Get the number of children of the tree.
	 * @return The size of the list of children.
	 */
	public int getNumChildren();
	
	/**
	 * Get the level of the tree. Start from 0 at the root;
	 * every child increments by one the level of the father.
	 * @return The level of the tree.
	 */
	public int getLevel();
	
	/**
	 * Set the level of the tree.
	 * @param l The new level to set.
	 */
	public void setLevel(int l);
	
	/**
	 * This method is used to return as a string the list of fields
	 * of the tree. Couple name=value.
	 * @return A String representing all fields as a string.
	 */
	public String getAllFieldsAsString();
	
	/**
	 * Set an id for the tree.
	 * @param ID The id to set.
	 */
	public void setID(int ID);
	
	/**
	 * Get the id of the tree.
	 * @return The id of the tree.
	 */
	public int getID();
	
	/**
	 * Get the id of the father.
	 * @return The id of the father.
	 */
	public int getIDFather();

	/**
	 * Get the value of field of the tree given the name.
	 * @param name The name of the field.
	 * @return The value of the field.
	 */
	public String getFieldValue(String name);
	
	/**
	 * Get a field of the tree given a name.
	 * @param name The name of the field to get.
	 * @return The field of the tree.
	 */
	public Field getFieldByName(String name);
	
	/**
	 * Get the list of fields of the tree.
	 * @return The list of fields of the tree.
	 */
	public List<Field> getFieldsList();
	
	/**
	 * Set the list of fields of the tree.
	 * @param attr The list of fields to set.
	 */
	public void setFieldsList(List<Field> attr);
	
	/**
	 * Add a field to the list of fields.
	 * @param f The field to add.
	 */
	public void addField(Field f);
	
	/**
	 * Add a field to the list of fields, given a name and a value.
	 * @param name The name of the field.
	 * @param value The value of the field.
	 */
	public void addField(String name, String value);
	
	/**
	 * Get a tree child from the list of children by its name.
	 * @param name The name of the tree child.
	 * @return The tree child.
	 */
	public Tree getChildByName(String name);
	
	/**
	 * Tell whether or not a Tree is a leaf.
	 * @return It returns true if Tree is a leaf, ie 
	 * cannot have children; it returns false if Tree is a
	 * node.
	 */
	public Boolean isLeaf();
	
	/**
	 * This method is used to clone (shallow copy) a Tree object.
	 * @return It returns a new Tree that is
	 * a clone of the tree.
	 */
	public Tree clone();
	
	/**
	 * This method is used to clone (deep copy) a Tree object. 
	 * It also clones the children and the field objects.
	 * @return It returns a new Tree that is
	 * a deep copy of the tree.
	 * @throws Exception
	 */
	public Tree cloneAll() throws Exception;
	
	/**
	 * This method is used to represents as string
	 * the attribute of the tree as string.
	 * @return It returns the attributes of the 
	 * tree as string.
	 */
	public String toString();
	
}