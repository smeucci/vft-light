package draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

import tree.ConcreteVisitor;
import tree.Tree;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int[] x_values;
	private Tree tree;
	private HashMap<Integer, Point> map;
	private int x_start = 200;
	private int y_start = 30;
	private LinkedList<Tree> result;
	
	public Panel(Tree tree, int x, int y) {
		this.x_values = new int[30];
		initialize();
		setBackground(Color.WHITE);
		this.tree = tree;
		this.map = new HashMap<Integer, Point>();
		setPreferredSize(new Dimension(x, y));	
	}
	
	private void initialize() {
		x_values[0] = 568;
		for (int i = 1; i < x_values.length; i++) {
			x_values[i] = -32;
		}
	}

	public int getX(int level){
		x_values[level] = (x_values[level] > 1500) ? 32 : x_values[level] + 32;
		x_start = x_values[level];
		return x_start;
	}
	
	public void paintComponent(Graphics g) {		
		g.setFont(new Font("SansSerif", Font.BOLD,10));
		ConcreteVisitor visitor = new ConcreteVisitor();
		tree.acceptVisit(visitor);
		result = visitor.getResult();
		for (Tree node: result) {
			
			Color c = new Color(0, 0, 0);
			g.setColor(c);
			
			x_start = getX(node.getLevel());	
			y_start+= node.getLevel()*60;
			g.drawArc(x_start, y_start, 5, 5, 0, 360);

			String str = node.getName();
			//g.setFont(new Font("medium", Font.PLAIN, 8));
			//g.drawString("" +  node.getID() + "", x_start - 10, y_start - 12);
			g.setFont(new Font("SansSerif", Font.BOLD, 10));
			g.drawString(str, x_start - 10, y_start - 5); 
			
			map.put(node.getID(), new Point(x_start, y_start));
			x_start = 0;
			y_start = 0;
		}
		x_start = 200;
		y_start = 30;
		drawArchs(g);
	}
	
	public void drawArchs(Graphics g) {
		Color c = new Color(236, 78, 46);
		g.setColor(c);
		for (int i = 0; i < result.size(); i++) {
			Tree a = result.get(i);
			Iterator<Tree> itr = a.iterator();
			if (itr != null) {
				while(itr.hasNext()) {
					Tree b = itr.next();
					Point first = map.get(a.getID());
					Point second = map.get(b.getID());
					g.drawLine(first.x, first.y, second.x, second.y);
				}
			}
		}
	}
	
}