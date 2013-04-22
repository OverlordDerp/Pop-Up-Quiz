/*
 * Main
 * This class will create the application frame.
 * Quincy L, Vivian T, Aravind V
 */

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

public class Main {

	/**
	 * @args Command line arguments.
	 */
	public static void main(String[] args) {
		PopUpQuiz frame = new PopUpQuiz();
		frame.addKeyListener(frame);
		
		GraphicsEnvironment ge 
				= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(frame);		
		frame.setVisible(true);
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
