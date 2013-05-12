

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.util.ArrayList;
/**
 * Main
 * This class will create the application frame.
 * @author quincy
 */
public class Main {

	/**
	 * Creates an instance of @link PopUpQuiz. Puts the game in full-screen
	 * mode.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		PopUpQuiz frame = new PopUpQuiz();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(frame);
		frame.setVisible(true);


	}


}
