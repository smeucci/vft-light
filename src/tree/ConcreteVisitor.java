package tree;

import java.util.LinkedList;

public class ConcreteVisitor implements Visitor {

	private LinkedList<Tree> visited = new LinkedList<Tree>();
	
	public void visitNode(Node n) {
		this.visited.add(n);
		for (Tree _n: n) {
			_n.acceptVisit(this);
		}		
	}

	public void visitLeaf(Leaf l) {
		this.visited.add(l);		
	}

	public LinkedList<Tree> getResult() {
		return this.visited;
	}
	
	public int getSize() {
		return this.visited.size();
	}
	
}