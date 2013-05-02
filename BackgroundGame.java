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
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Point;
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
		setBackground(Color.red);
		
		sprites = new HashMap<>();
		try {
			loadSprites();
		}
		catch(IOException e) {
			System.out.println(e);
			System.exit(-1);
		}

		Rectangle boundsRect = new Rectangle(d);
		
		objects = new ArrayList<>();
		rb = new RecycleBin(boundsRect);
		rb.setPosition(new Point2D.Double(d.width/2, 
				d.height - sprites.get(rb.getSprite()).getHeight()));
		
		objects.add(rb);
		
		GameObject a = new Sysfile(boundsRect, Sysfile.Size.L);
		objects.add(a);
		a.setAccel(new Point2D.Double(0, 0.1));
		objects.add(new Sysfile(boundsRect,Sysfile.Size.M));
		objects.add(new Sysfile(boundsRect, Sysfile.Size.S));
		
		//Create a background loop
		(new Thread(new Runnable() {
			public void run() {
				while(true) {
					// Use an iterator for the outer loop because of
					// for its good deletion sematnics
					for (ListIterator<GameObject> i = objects.listIterator(); 
							i.hasNext();) {
						final GameObject g = i.next();
						
						//Delete dead objects
						if (g.isDead) {
							i.remove();
							continue;
						}
						
						//Put objects within bounds
						confine(g);

												
						//Store state of kinematics variables
						g.stashKinematicsVars();

						//Handle collisions
						Rectangle myCollRect = calculateCollRect(g);
						for (ListIterator<GameObject> j = objects.listIterator(); 
							j.hasNext();) {
							final GameObject h = j.next();
							if (g == h) {
								continue;
							}
							
							if (myCollRect.intersects(calculateCollRect(h))) {
								new Thread(new Runnable() {
									public void run() {
										g.onCollide(h);
									}
								}).start();
							}
						}
										
						// Execute cycling methdos
						g.cycle();
						

					}
				} // While loop ends
			}
		})).start();
		
		// Another loop for painting
		(new Thread(new Runnable() {
			public void run() {
				while(true) {
					repaint();
					try {
						Thread.sleep(16);
					} catch (InterruptedException e) 
					{}
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
	public static HashMap<String, BufferedImage> sprites;
	private ArrayList<GameObject> objects;
	
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
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				rb.setAccel(new Point2D.Double(0, 0));
				break;
			case KeyEvent.VK_RIGHT:
				rb.setAccel(new Point2D.Double(0, 0));
				break;
		}		
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				rb.setAccel(new Point2D.Double(-0.1, 0));
				break;
			case KeyEvent.VK_RIGHT:
				rb.setAccel(new Point2D.Double(+0.1, 0));
				break;
		}
	}
	
	private void loadSprites() throws IOException {
		sprites.put("fullBin", ImageIO.read(new File("user-trash-full64.png")));
		sprites.put("emptyBin", ImageIO.read(new File("user-trash64.png")));
		sprites.put("sysfileLarge", ImageIO.read(new File("sysfile1-48.png")));
		sprites.put("sysfileMedium", ImageIO.read(new File("sysfile2-32.png")));
		sprites.put("sysfileSmall", ImageIO.read(new File("sysfile3-16.png")));
		sprites.put("junk", ImageIO.read(new File("junk.png")));
	}
	

	/**
	 * Moves g until it is contained within its boundary rectangle
	 * @param g 
	 */
	private void confine(GameObject g) {
		Rectangle bounds = g.getBounds();
		if (bounds == null) {
			return;
		}
		
		Rectangle areaRect = calculateAreaRect(g);
		
		if (bounds.contains(areaRect)) {
			return;
		}
		
		if (areaRect.x < bounds.x) {
			areaRect.x = bounds.x;
		}
		else if (areaRect.x + areaRect.width > bounds.x + bounds.width) {
			areaRect.x = bounds.x + bounds.width - areaRect.width;
		}
		
		if (areaRect.y < bounds.y) {
			areaRect.y = bounds.y;
		}
		else if (areaRect.y + areaRect.height > bounds.y + bounds.height) {
			areaRect.y = bounds.y + bounds.height - areaRect.height;
		}
		
		g.setPosition(new Point2D.Double(areaRect.x, areaRect.y));
	}
	
	/**
	 * Calculates the rectangle from the top-left corner of the object's sprite
	 * to its bottom-right.
	 * @param g The object
	 * @return The area rectangle
	 */
	private Rectangle calculateAreaRect(GameObject g) {
		Point2D.Double p = g.getPosition();
		BufferedImage s = sprites.get(g.getSprite());
		if (s == null) {
			return new Rectangle((int) p.x, (int)p.y, 0, 0);
		}
		else {
			return new Rectangle((int)p.x, (int) p.y, s.getWidth(), s.getHeight());
		}
	}
	
	/**
	 * Calculates the collision rectangle of the object from its offset
	 * @param g The object
	 * @return The collision rectangle
	 */
	private Rectangle calculateCollRect(GameObject g) {
		Point2D.Double p = g.getPosition();
		Rectangle collRectOffset = g.getCollRectOffset();
		return new Rectangle((int) (p.x + collRectOffset.x), 
				(int) (p.y + collRectOffset.y),
				collRectOffset.width, collRectOffset.height);
	}

	/**
	 * Draws the sprites of all of the GameObjects
	 * @param g Graphics context
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (GameObject h : objects) {
			Point2D.Double p = h.getPosition();
			BufferedImage s;
			if ((s = sprites.get(h.getSprite())) != null) {
				g.drawImage(sprites.get(h.getSprite()), (int) p.x, (int) p.y, null);
			}
		}
	}
	
	private int cpuUsage = 0;
	
	public int getCpuUsage() {
		return cpuUsage;
	}
}