import java.awt.Color;
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
import java.lang.Thread;
import java.lang.Runnable;
import java.lang.Math.*;
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
			fullBin = ImageIO.read(new File("user-trash-full64.png"));
			emptyBin = ImageIO.read(new File("user-trash64.png"));
		} 
		catch (IOException e) {
			System.out.println(e);
			System.exit(-1);
		}
		
		binLocation = new Rectangle((int) d.getWidth()/2, 
				(int) (d.getHeight() - fullBin.getHeight()),
				fullBin.getWidth(), fullBin.getHeight());
		
		//Create a background loop thread
		(new Thread(new Runnable() {
			public void run() {
				while(true) {
					System.out.println(binLocation.x);
					binLocation.x += speed;
					speed -= Math.signum(speed);
				}
			}
		})).start();
	}
	
	/**
	 * Describes the state of the game. Something should be done if
	 * the game has been lost
	 */
	public enum GameState { PLAYING, LOST };
	private GameState state;
	private Rectangle binLocation;
	private int movementAccel = 0;
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
				if (binLocation.x < 0) {
					binLocation.x = 0;
				}
				break;
			case KeyEvent.VK_RIGHT:
				speed += 1;
				if (binLocation.x + binLocation.width > getWidth()) {
					binLocation.x = getWidth() - binLocation.width;
				}
				break;
		}
		if (speed > Math.signum(speed) * maxSpeed) {
			speed = (int) Math.signum(speed) * maxSpeed;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println(binLocation);
		if (binIsUsed) {
			g.drawImage(fullBin, binLocation.x, binLocation.y, null);
		}
		else {
			g.drawImage(emptyBin, binLocation.x, binLocation.y, null);
			

		}
	}
}