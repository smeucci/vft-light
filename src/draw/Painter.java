package draw;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
		
		window.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				 
				if (e.getKeyCode() == KeyEvent.VK_0) {
					window.dispose();
					System.exit(0);
					return;
				}
				
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		window.pack();
		window.setVisible(true);
		
	}
	
}
