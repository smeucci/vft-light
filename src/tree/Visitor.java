package tree;

/**
 * <h1>Class for the Visitor pattern</h1>
 * <p>Visitor is an interface for the Visitor pattern. It is used to visit Tree object.
 * </p>
 * 
 * @author Saverio
 * 
 */
public interface Visitor {

	/**
	 * This method is used to visit a node
	 * @param n The node to visit.
	 */
	public void visitNode(Node n);
	
	/**
	 * This method is used to visit a leaf.
	 * @param l The leaf to visit.
	 */
	public void visitLeaf(Leaf l);
	
}
