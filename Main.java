/*
 * Main
 * This class will create the application frame.
 * Quincy L, Vivian T, Aravind V
 */

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.util.ArrayList;

public class Main {

	/**
	 * @args Command line arguments.
	 */
	public static void main(String[] args) {
		PopUpQuiz frame = new PopUpQuiz();
		frame.addKeyListener(frame);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(frame);
		frame.setVisible(true);


	}


}
