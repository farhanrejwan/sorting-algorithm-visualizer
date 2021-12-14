package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import visuals.MainScreen;

public class Main {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	
	private JFrame jFrame = null;
	
	private void start() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		MainScreen mainScreen = new MainScreen(new Dimension(WIDTH, HEIGHT));
		
		jFrame = new JFrame("Sorting Algorithm Visualizer");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.setContentPane(mainScreen);
		jFrame.validate();
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);
		mainScreen.requestFocusInWindow();
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main().start();
			}			
		});
	}
}