package com.vftlite.draw;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.vftlite.tree.Tree;

/**
 * <h1>Create a JFrame window and draw a tree</h1>
 * <p>The Painter class is used to create a JFrame window in which
 * a Tree object will be drawn.
 * </p>
 * 
 * @author Saverio Meucci
 * 
 */
public class Painter {

	private static JFrame window;
	private static Panel panel;
	
	/**
	 * This method is used to create the window and draw the Tree object.
	 * @param tree The Tree object to be drawn.
	 * @param x The width of the window.
	 * @param y The height of the window.
	 * @param name The name of the window.
	 */
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
