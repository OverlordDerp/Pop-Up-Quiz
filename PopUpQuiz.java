
/*
 * Pop-Up-Quiz
 * This class will create the application frame.
 * Quincy L, Vivian T, Aravind V
 */

// Question Class + Random Question Selector
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;

/**
 * The main graphical class of the game.
 * @author quincy
 */
public class PopUpQuiz extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default constructor. 
	 * After calling the base constructor, it sets up listeners for
	 * key events and close events.
	 * Stuff concerning full-screen is done in @link Main.main.
	 */
	public PopUpQuiz() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		
		HUD h = new HUD(Toolkit.getDefaultToolkit().getScreenSize());
		c.add(h);
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
   
