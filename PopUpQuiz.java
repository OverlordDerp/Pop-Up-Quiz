
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

// HEAD
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
<<<<<<< HEAD

}
=======
    
	public static void main (String[] args)
		    {
				char quot = 34;
				int min = 0;
				int max = 3;
				String test;
		        ArrayList<String> choices = new ArrayList<String>(20);
		        
		        choices.add(0, "When was the first " + quot + "Where's Waldo?" + quot + " book published?");
		        //Correct answer: 1987
		        choices.add(1, "How often are human brain cells replaced?");
		        //Correct answer: Never
		        choices.add(2, "What was Al Capone's nickname?");
		        //Correct Answer: Scarface
		        for (max = max; max >= 1; max--)
		        {
		        test = chooseRandomQuestion(min, max, choices);
		        System.out.println(test);
		        System.out.println(choices.indexOf(test));
		        choices.remove(test);
		        System.out.println(max);
		        }
		    }   
		        
	public static String chooseRandomQuestion(int min, int max, ArrayList<String> C)
		    	{	
					int selection = (int) ((Math.random()*max));
					return (C.get(selection));
					
		    	}
	
}

// Question Class + Random Question Selector
>>>>>>> 01996495b3fbdccdaaf81714521fa094a9616a2e
