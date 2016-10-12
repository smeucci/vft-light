package tree;

public interface Visitor {

	public void visitNode(Node n);
	public void visitLeaf(Leaf l);
	
}
