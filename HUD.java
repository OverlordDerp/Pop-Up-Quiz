import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Draws the taskbar, the score, the CPU usage..... the heads-up display.
 * @author quincy
 */
public class HUD extends JPanel {
	private JButton startButton;
	
	private final int startButtonHeight = 20;
	private final int startButtonWidth = 80;
	private final int taskbarHeight = startButtonHeight + 4;
	private final int startButtonPadding = (taskbarHeight - startButtonHeight)/2;
	/**
	 * Base constructor. Creates all the components.
	 * @param d Size of the parent
	 */
	public HUD(Dimension d) {
		super();
		setSize(d);
		// Prevents automatic layout
		setLayout(null);
		System.out.println(d);
				
		startButton = new JButton("Start");
		add(startButton);
		
		startButton.setBounds(startButtonPadding,
				getHeight() - startButtonHeight - startButtonPadding,
				startButtonWidth, startButtonHeight);
		startButton.setFocusable(false);
		validate();
	}
	
	/**
	 * Draws the taskbar, the CPU usage, the score.
	 * @param g The Graphics object on which to draw
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(0, getHeight() - taskbarHeight, getWidth(), taskbarHeight);
		
		//g.drawRect(0, 0, getWidth(), getHeight());
	}
	

}