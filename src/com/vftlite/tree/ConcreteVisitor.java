package com.vftlite.tree;

import java.util.LinkedList;

/**
 * <h1>Implement a concrete visitor</h1>
 * <p>Concrete visitor implements Visitor.
 * 
 * @author Saverio Meucci
 */
public class ConcreteVisitor implements Visitor {

	private LinkedList<Tree> visited = new LinkedList<Tree>();
	
	/**
	 * This method is used to visit a node.
	 * @param n The node to visit.
	 */
	public void visitNode(Node n) {
		this.visited.add(n);
		for (Tree _n: n) {
			_n.acceptVisit(this);
		}		
	}

	/**
	 * This method is used to visit a leaf.
	 * @param l The leaf to visit.
	 */
	public void visitLeaf(Leaf l) {
		this.visited.add(l);		
	}

	/**
	 * This method is used to get the list of Tree objects visited.
	 * @return A Linked List of Tree objects visited.
	 */
	public LinkedList<Tree> getResult() {
		return this.visited;
	}
	
	/**
	 * Get size of the said linked list of Tree objects visisted.
	 * @return The number of Tree objects visited.
	 */
	public int getSize() {
		return this.visited.size();
	}
	
}