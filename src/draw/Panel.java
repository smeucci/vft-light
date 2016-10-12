package draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JPanel;

import tree.ConcreteVisitor;
import tree.Tree;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int[] x_values;
	private Tree tree;
	private HashMap<Integer, Point> map;
	private int x_start = 0;
	private int y_start = 0;
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
		for (int i = 0; i < x_values.length; i++) {
			x_values[i] = 0;
		}
		x_values[0] = 600;
	}

	public int getX(int level){
		x_values[level] = x_values[level] + 32;
		if(x_values[level] > 1500) {
			x_values[level] = 32;
		}
		x_start = x_values[level];
		return x_start;
	}
	
	public void paintComponent(Graphics g) {		
		g.setFont(new Font("SansSerif",Font.BOLD,10));
		ConcreteVisitor visitor = new ConcreteVisitor();
		tree.acceptVisit(visitor);
		result = visitor.getResult();
		for (Tree the_node:result) {
			
			Color c = new Color(0, 0, 0);
			g.setColor(c);
			
			x_start = getX(the_node.getLevel());	
			y_start+= the_node.getLevel()*60;
			g.drawArc(x_start, y_start, 5, 5, 0, 360);
						
			String str = the_node.getName();
			g.drawString(str, x_start - 10, y_start - 5); 
			g.setFont(new Font("SansSerif",Font.BOLD, 10));
			
			map.put(the_node.getID(), new Point(x_start,y_start));		
			x_start = 0;
			y_start = 0;
		}
		x_start = 200;
		y_start = 30;
		//drawArchs(g);
	}
	
	public void drawArchs(Graphics g) {
		for (int i = 0; i < result.size(); i++) {	
			Tree act = result.get(i);
			int actid = act.getID(); 
			int fatherid = act.getIDFather();			
			if (fatherid >= 0) {				
				Point first = map.get(actid);
				Point second = map.get(fatherid);
				int x1 = first.x;
				int y1 = first.y;
				int x2 = second.x;
				int y2 = second.y;
				g.drawLine(x1, y1, x2, y2);
			}
		}
	}
	
}