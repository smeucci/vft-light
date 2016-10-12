package tree;

public class Field {

	String name, value;
	
	public Field() {
		this.name = "";
		this.value = "";
	}
	
	public Field(String name) {
		this.name = name;
		this.value = "";
	}
	
	public Field(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setvalue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return "Field[name=" + this.name + ", "
				+ "value=" + this.value + "]";
	}
	
}