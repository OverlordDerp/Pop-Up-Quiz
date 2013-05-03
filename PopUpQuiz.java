
/*
 * Pop-Up-Quiz
 * This class will create the application frame.
 * Quincy L, Vivian T, Aravind V
 */

// Question Class + Random Question Selector
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.JOptionPane;
/**
 * The main graphical class of the game.
 * @author quincy
 */
public class PopUpQuiz extends JFrame implements KeyListener {
	/**
	 * The default constructor. 
	 * After calling the base constructor, it sets up listeners for
	 * key events and close events.
	 * Stuff concerning full-screen is done in @link Main.main.
	 */
	public PopUpQuiz() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		Container c = getContentPane();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final HUD h = new HUD(screenSize);
		c.add(h);
		h.setLocation(0, (int) screenSize.getHeight() - HUD.taskbarHeight);
		
		
		final BackgroundGame bg = new BackgroundGame(new Dimension( 
				(int) screenSize.getWidth(), 
				(int) screenSize.getHeight() - h.getTaskbarHeight()));
		c.add(bg);
		bg.addKeyListener(bg);
		
		// Loop that updates CPU
		(new Thread(new Runnable() {
			public void run() {
				while(true) {
					//h.setCpuUsage(bg.getCpuUsage());
					h.setCpuUsage(20);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) 
					{}
				}
			}
		})).start();
		
		validate();


		
		

	}
	
	/**
	 * Handles key press events. Principally, the frame closes if the user
	 * presses Escape.
	 * @param e The KeyEvent object.
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
			break;
		}
	}
		
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	

}
   
