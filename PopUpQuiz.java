
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

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