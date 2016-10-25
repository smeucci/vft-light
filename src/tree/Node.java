package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node implements Tree {

	private int ID;
	private int IDFather;
	private int level;
	private int numChildren;
	private String name;
	private Tree father;
	private List<Tree> children;
	private List<Field> fields;
	
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
	
	public Iterator<Tree> iterator() {
		return children.iterator();
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void addChild(Tree c) throws Exception {
		this.children.add(c);
		this.numChildren++;		
	}
	
	public void removeChild(Tree c) throws Exception {
		this.children.remove(c);		
	}

	public void acceptVisit(Visitor v) {
		v.visitNode(this);
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

	public void setID(int ID) {
		this.ID = ID;
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
	
	public void addField(Field f) {
		this.fields.add(f);
	}
	
	public void addField(String name, String value) {
		this.fields.add(new Field(name, value));
	}
	
	public Tree getChildByName(String name) {
		for (Tree child: this.children) {
			if (child.getName().equals(name)) {
				return child;
			}
		}
		return null;		
	}
	
	public Tree clone() {
		return new Node(this.ID, this.name, this.level, this.father, this.fields);
	}
	
	public String toString() {
		return "Node[name=" + this.name + ", "
				+ "ID=" + this.ID + ", "
				+ "nameFather=" + this.father.getName() + ", "
				+ "IDFather=" + this.IDFather + ", "
				+ "level=" + this.level + ", "
				+ "numChildren=" + this.numChildren + "]";
	}

}