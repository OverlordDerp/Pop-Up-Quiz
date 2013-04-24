import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * The game in the background
 * @author quincy
 */
public class BackgroundGame extends JPanel implements KeyListener {
	
	
	/**
	 * The constructor
	 * @param d The size of the game. 
	 */
	public BackgroundGame(Dimension d) {
		super();
		setSize(d);
		
		try {
			fullBin = ImageIO.read(new File("user-trash64.png"));
			emptyBin = ImageIO.read(new File("user-trash-empty64.png"));
		} 
		catch (IOException e) {
			
		}
	}
	
	/**
	 * Describes the state of the game. Something should be done if
	 * the game has been lost
	 */
	public enum GameState { PLAYING, LOST };
	private GameState state;
	private Rectangle binLocation;
	private int movementAccel = 1;
	private int speed = 0;
	private int maxSpeed = 7;
	boolean binIsUsed = false;
	
	BufferedImage emptyBin, fullBin;
	
	/**
	 * Gets the state of the game
	 * @return GameState.PLAYING or GameState.LOST
	 */
	GameState getState() {
		return state;
	}
	
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				speed -= 1;
				binLocation.x += speed;
				if (binLocation.x < 0) {
					binLocation.x = 0;
				}
				break;
			case KeyEvent.VK_RIGHT:
				speed += 1;
				binLocation.x += speed;
				if (binLocation.x + binLocation.width > getWidth()) {
					binLocation.x = getWidth() - binLocation.width;
				}
				break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
}