package tree;

import java.util.List;

public interface Tree extends Iterable<Tree> {
	
	public String getName();
	public void addChild(Tree c) throws Exception;
	public void removeChild(Tree c) throws Exception;
	
	public void acceptVisit(Visitor v);
	
	public void setFather(Tree f);
	public Tree getFather();
	public int getNumChildren();
	public int getLevel();
	public void setLevel(int l);
	
	public String getAllFieldsAsString();
	
	public int getID();
	public int getIDFather();

	public String getFieldValue(String name);
	public List<Field> getFieldsList();
	public void setFieldsList(List<Field> attr);
	public void addField(Field f);
	public void addField(String name, String value);
	
	public String toString();

	//tracktype methods
	
}