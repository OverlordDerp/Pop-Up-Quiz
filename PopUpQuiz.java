
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
		
		final JDialog dialog = new JDialog(this,"Question!");
		JLabel randQuestion = new JLabel (choices.get(0));
		randQuestion.setHorizontalAlignment(JLabel.CENTER);
		Font font = randQuestion.getFont();
        randQuestion.setFont(randQuestion.getFont().deriveFont(font.PLAIN,14.0f));
        JButton choice1 = new JButton("1986");
        JButton choice2 = new JButton("1987");
        JButton choice3 = new JButton("1984");
        JButton choice4 = new JButton("1973");
        choice1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dialog.setVisible(false);
        		dialog.dispose();
        	}
        });
        choice2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dialog.setVisible(false);
        		dialog.dispose();
        	}
        });
        choice3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dialog.setVisible(false);
        		dialog.dispose();
        	}
        });
        choice4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dialog.setVisible(false);
        		dialog.dispose();
        	}
        });
        
        dialog.setVisible(true);
		
		for (max = max; max >= 1; max--) {
			test = chooseRandomQuestion(min, max, choices);
			System.out.println(test);
			System.out.println(choices.indexOf(test));
			choices.remove(test);
			System.out.println(max);
		}
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
	
	public static String chooseRandomQuestion(int min, int max, ArrayList<String> C) {
		int selection = (int) ((Math.random() * max));
		return (C.get(selection));

	}

}
   
