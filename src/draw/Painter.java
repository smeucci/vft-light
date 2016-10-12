package draw;

import java.awt.Container;

import javax.swing.JFrame;

import tree.Tree;

public class Painter {

	private static JFrame window;
	private static Panel panel;
	
	public static void painter(Tree tree, int x, int y, String name) {
		
		window = new JFrame(name);
		window.setLocation(x, y);
		Container container = window.getContentPane();
		
		panel = new Panel(tree, x, y);
		container.add(panel);
		
		window.pack();
		window.setVisible(true);
	}
	
}
