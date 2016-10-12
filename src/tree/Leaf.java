package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Leaf implements Tree {

	private int ID;
	private int IDFather;
	private int level;
	private int numChildren;
	private String name;
	private Tree father;
	private List<Field> fields;
	
	public Leaf(int ID, String name, int level, Tree father, List<Field> fields) {
		this.name = name;
		this.father = father;
		this.numChildren = 0;
		this.level = level;
		this.fields = fields;
		this.ID = ID;
		this.IDFather = father.getID();		
	}
	
	public Iterator<Tree> iterator() {
		return null;
	}

	public String getName() {
		return this.name;
	}

	public void add(Tree c) throws Exception {
		throw new Exception();		
	}

	public void remove(Tree c) throws Exception {
		throw new Exception();
	}

	public void acceptVisit(Visitor v) {
		v.visitLeaf(this);
	}

	public void setFather(Tree f) {
		this.father = f;
	}
	
	public Tree getFather() {
		return this.father;
	}

	public int getNumChildren() {
		return this.numChildren;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int l) {
		this.level = l;		
	}

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

	public int getID() {
		return this.ID;
	}

	public int getIDFather() {
		return this.IDFather;
	}

	public String getFieldValue(String name) {
		for (Field a: this.fields) {
			if (a.getName().equals(name)) {
				return a.getValue();
			}
		}
		return null;
	}

	public List<Field> getFieldsList() {
		return new ArrayList<Field>(this.fields);
	}
	
	public void setFieldsList(List<Field> fields) {
		this.fields = fields;
	}
	
	public String toString() {
		return "Leaf[name=" + this.name + ", "
				+ "ID=" + this.ID + ", "
				+ "nameFather=" + this.father.getName() + ", "
				+ "IDFather=" + this.IDFather + ", "
				+ "level=" + this.level + "]";
	}

}