package tree;

import java.util.List;

public interface Tree extends Iterable<Tree> {
	
	public String getName();
	public void add(Tree c) throws Exception;
	public void remove(Tree c) throws Exception;
	
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
	
	public String toString();

	//tracktype methods
	
}