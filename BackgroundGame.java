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
		setFocusable(true);
		setDoubleBuffered(true);
		
		try {
			loadSprites();
		}
		catch(IOException e) {
			System.out.println(e);
			System.exit(-1);
		}

		rb = new RecycleBin();
		add(rb);
		rb.setBounds(new Rectangle((int) d.getWidth()/2, 
				(int) (d.getHeight() - RecycleBin.fullBin.getHeight()),
				RecycleBin.fullBin.getWidth(), 
				RecycleBin.fullBin.getHeight()));
		
				//Create a background loop thread
		(new Thread(new Runnable() {
			public void run() {
				while(true) {
					rb.cycle();
					repaint();
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
	private RecycleBin rb;

	
	/**
	 * Gets the state of the game
	 * @return GameState.PLAYING or GameState.LOST
	 */
	GameState getState() {
		return state;
	}
	
	public void keyTyped(KeyEvent e) {

	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				rb.increaseVelocity();
				break;
			case KeyEvent.VK_RIGHT:
				rb.decreaseVelocity();
				break;
			default:
				System.out.println("POO");
		}
	}
	
	private void loadSprites() throws IOException {
		RecycleBin.fullBin = ImageIO.read(new File("user-trash-full64.png"));
		RecycleBin.emptyBin = ImageIO.read(new File("user-trash64.png"));
	}

}