
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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

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
		setLayout(null);

		//setBackground(Color.red);

		GameObject.bgg = this;

		sprites = new HashMap<>();
		try {
			loadSprites();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(-1);
		}

		Rectangle boundsRect = new Rectangle(d);

		objects = new ArrayList<GameObject>();
		rb = new RecycleBin(boundsRect);
		rb.setPosition(new Point2D.Double(d.width / 2,
				d.height - getSprites().get(rb.getSprite()).getHeight()));

		objects.add(rb);

		//Create a background loop
		(new Thread(new Runnable() {

			public void run() {
				while (true) {
					if (isPaused) {
						/**
						 * Offsets the time spent whilst pausing
						 */
						++timeGameStarted;
					}
					if (System.nanoTime() - lastLogicCycleTime > 1000000000 / logicFps) {
						lastLogicCycleTime = System.nanoTime();

						synchronized (lock) {

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

								//Execute any actions if the object goes out of bounds
								if (!g.getBounds().contains(g.getAreaRect())) {
									g.onOutOfBounds();
								}


								//Store state of kinematics variables
								g.stashKinematicsVars();

								//Handle collisions
								Rectangle myCollRect = g.getCollRect();
								for (ListIterator<GameObject> j = objects.listIterator();
										j.hasNext();) {
									final GameObject h = j.next();
									if (g == h) {
										continue;
									}

									if (myCollRect.intersects(h.getCollRect())) {
										new Thread(new Runnable() {

											public void run() {
												// Reminder: collideWith calls g's 
												// collision handlers
												h.collideWith(g);
											}
										}).start();
									}
								}


								// Execute cycling methdos
								g.cycle();

							}
							if (isStarted) {
								gameCycle();
							}
						}

						// With actions having concluded,
						// update the state of the object list
						//objects.set(objectsTemp);
					} // FPS condition ends
				} // While loop ends
			}
		})).start();

		// Another loop for painting
		(new Thread(new Runnable() {

			public void run() {
				while (true) {
					repaint();
				}
			}
		})).start();
	}

	/**
	 * @return the sprites
	 */
	public HashMap<String, BufferedImage> getSprites() {
		return sprites;
	}

	/**
	 * @return the timeGameStarted
	 */
	public long getTimeGameStarted() {
		return timeGameStarted;
	}
	private RecycleBin rb;
	private HashMap<String, BufferedImage> sprites;
	private ArrayList<GameObject> objects;

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
				rb.setAccel(new Point2D.Double(-3, 0));
				break;
			case KeyEvent.VK_RIGHT:
				rb.setAccel(new Point2D.Double(+3, 0));
				break;

			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_SPACE:
				if (isStarted) {
					togglePaused();
				}
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
		sprites.put("grass", ImageIO.read(new File("grass.jpg")));
	}

	/**
	 * Draws the sprites of all of the GameObjects
	 * @param g Graphics context
	 */
	public void paintComponent(Graphics g) {

		
		super.paintComponent(g);
		
				
		// The background.
		g.drawImage(sprites.get("grass"),0,0,getWidth(),getHeight(),null);

		if (isOver) {
			drawGameOverScreen(g);
			return;
		}

		if (!isStarted) {
			drawTitleScreen(g);
		}

		synchronized (lock) {
			for (GameObject h : objects) {
				Point2D.Double p = h.getPosition();
				BufferedImage s;
				if ((s = sprites.get(h.getSprite())) != null) {
					g.drawImage(sprites.get(h.getSprite()), (int) p.x, (int) p.y, null);
				}
			}
		}

	}
	private double cpuUsage = 0;

	public double getCpuUsage() {
		return cpuUsage;
	}

	private void makeDialog() {
		final BackgroundGame thisPanel = this;


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


		final JInternalFrame dialog = new JInternalFrame("Question!");
		dialog.addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameClosed(InternalFrameEvent arg0) {
				thisPanel.requestFocusInWindow();
			}

			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {
			}
		});
		JLabel randQuestion = new JLabel(choices.get(0));
		randQuestion.setHorizontalAlignment(JLabel.CENTER);
		Font font = randQuestion.getFont();
		randQuestion.setFont(randQuestion.getFont().deriveFont(font.PLAIN, 14.0f));
		JButton choice1 = new JButton("1986");
		JButton choice2 = new JButton("1987");
		JButton choice3 = new JButton("1984");
		JButton choice4 = new JButton("1973");

		JPanel a = new JPanel();
		a.add(choice1);
		a.add(choice2);
		a.add(choice3);
		a.add(choice4);
		dialog.setContentPane(a);

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


		dialog.pack();
		dialog.setVisible(true);
		add(dialog);

		dialog.setLocation(
				(int) (Math.random() * (getWidth() - dialog.getWidth())),
				(int) (Math.random() * getHeight()));


		for (max = max; max >= 1; max--) {
			test = choices.get((int) (Math.random() * max) + min);
			System.out.println(test);
			System.out.println(choices.indexOf(test));
			choices.remove(test);
			System.out.println(max);
		}
	}

	/**
	 * Draws the instructive title screen.
	 * @param g The graphics context
	 */
	private void drawTitleScreen(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 30));
		g.drawString("POP UP QUIZ!", 50, 100);
		g.setFont(new Font("Courier New", Font.BOLD, 18));
		g.drawString("Where you answer questions whilst cleaning your desktop!",
				50, 120);
		g.drawString("Q LAM, V TONG, A VIJAYARAGAVAN", 50, 140);

		g.drawString("Use the arrow keys to move the recycle bin.", 50, 180);
		g.drawString("<Space> pauses, and <Esc> quits.", 50, 200);

		g.drawImage(sprites.get("junk"), 4, 200, null);
		g.drawString("Polish your computer by trashing junk falling "
				+ "from the sky!", 50, 220);

		g.drawImage(sprites.get("sysfileLarge"), 4, 330, null);
		g.drawImage(sprites.get("sysfileMedium"), 8, 390, null);
		g.drawImage(sprites.get("sysfileSmall"), 16, 440, null);
		g.drawString("You'll mess up your computer if you collect system files.",
				50, 360);
		g.drawString("Note that they fall from the sky at different speeds.",
				50, 390);

		g.drawString("The CPU gauge will tell you how well you're doing!",
				50, 420);
		g.drawString("If your CPU usage goes over 100%, your computer goes kaput"
				+ "and you lose! How long can you clean?", 50, 450);

		g.drawString("By the way, you'll have to answer questions from a barrage"
				+ " of pop-up as you do this.", 50, 500);
		g.drawString("Serves you right for not being clean!!!", 50, 530);

		g.drawString("PUSH START TO BEGIN_", 50, 600);
	}
	/**
	 * How many cycles of game logic to execute per second
	 */
	private final int logicFps = 60;
	/**
	 * Some number of nanoseconds representing a moment in the past 
	 * when the logic loop was run. Used for pacing with @link logicFps.
	 */
	private long lastLogicCycleTime = 0;
	/**
	 * Whether the game has been started. Note that the game goes to the
	 * bitter end (the user's loss).
	 */
	private boolean isStarted = false;
	/**
	 * Whether the game is paused.
	 */
	private boolean isPaused = false;
	/**
	 * Whether the game has been lost;
	 */
	private boolean isOver = false;
	/**
	 * Some number of nanoseconds representing a moment in the past
	 * when the game was started. 
	 */
	private long timeGameStarted;

	/**
	 * Increases the CPU usage by some amount.
	 * @param val The amount by which to increase CPU usage.
	 */
	public void increaseCpuUsage(double val) {
		cpuUsage += val;
	}

	/**
	 * Decreases the CPU usage by some amount.
	 * @param val The amount by which to increase CPU usage.
	 */
	public void decreaseCpuUsage(double val) {
		cpuUsage -= val;
	}

	/**
	 * Toggles the paused state of the game.
	 */
	private void togglePaused() {
		if (!isPaused) {
			timeFirstPaused = System.nanoTime();
		} else {
			// Offset the time spent paused.
			timeGameStarted += (System.nanoTime() - timeFirstPaused);
		}
		isPaused = !isPaused;
	}
	/**
	 * A moment to be used later in offseting time paused from the time
	 * elapsed in-game.
	 */
	private long timeFirstPaused;

	/**
	 * Begins the game proper!
	 */
	public void startGame() {
		timeGameStarted = System.nanoTime();
		isStarted = true;

	}

	/**
	 * What to do whilst the game is running. Called in the background
	 * loop thread
	 */
	private void gameCycle() {
		if (Math.random() < 0.1) {

			Sysfile foo = new Sysfile(getBounds(), Sysfile.Size.L);
			synchronized (lock) {
				objects.add(foo);
			}
			foo.setPosition(new Point2D.Double(
					(int) (Math.random() * (getWidth() - foo.getAreaRect().width)),
					10));
			makeDialog();

		}
	}

	/**
	 * Whether the game is paused.
	 * @return Whether the game is paused.
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * whether the game has started
	 * @return Whether the game has started.
	 */
	public boolean isStarted() {
		return isStarted;
	}

	/**
	 * Whether the game is over
	 * @return Whether the game is over
	 */
	public boolean isOver() {
		return isOver;
	}

	/**
	 * Draws a BSOD with game information, signifying a game over.
	 * @param g Grahpics context
	 */
	private void drawGameOverScreen(Graphics g) {
	}
	/**
	 * A dummy object used for synchronization. Used primarily to isolate
	 * adding GameObjects to @link objects and iterating through 
	 * @link objects.
	 */
	private final Object lock = new Object();
	
}