package com.vftlite.tree;

/**
 * <h1>Build a Field.</h1>
 * <p>The Field class is used to create couple name=value used by the Tree objects.
 * 
 * @author Saverio Meucci
 */
public class Field {

	String name, value;
	
	/**
	 * The default constructor of the class.
	 */
	public Field() {
		this.name = "";
		this.value = "";
	}
	
	/**
	 * The constructor of the class that sets only the
	 * name field.
	 * @param name
	 */
	public Field(String name) {
		this.name = name;
		this.value = "";
	}
	
	/**
	 * The constructor of the class.
	 * @param name The name of the field.
	 * @param value The value of the field.
	 */
	public Field(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Get the name of the field.
	 * @return The name of the field.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the field.
	 * @param name The name of the field to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the value of the field.
	 * @return The value of the field.
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Set the value of the field.
	 * @param value The value to set.
	 */
	public void setvalue(String value) {
		this.value = value;
	}
	
	/**
	 * This method return the name and value of the field
	 * as string.
	 * @return The couple name=value as a string.
	 */
	public String toString() {
		return "Field[name=" + this.name + ", "
				+ "value=" + this.value + "]";
	}
	
}