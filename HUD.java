import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
/**
 * Draws the taskbar, the score, the CPU usage..... the heads-up display.
 * @author quincy
 */
public class HUD extends JPanel {
	private JButton startButton;
	private JLabel cpuUsageLabel;
	
	public static final int startButtonHeight = 20;
	public static final int startButtonWidth = 80;
	public static final int taskbarHeight = startButtonHeight + 4;
	public static final int startButtonPadding = (taskbarHeight - startButtonHeight)/2;
	/**
	 * Base constructor. Creates all the components.
	 * @param d Size of the parent
	 */
	public HUD(Dimension d) {
		super();
		setSize((int) d.getWidth(), taskbarHeight);
		// Prevents automatic layout
		setLayout(null);
		
		startButton = new JButton("Start");
		add(startButton);
		startButton.setBounds(startButtonPadding,
				getHeight() - startButtonHeight - startButtonPadding,
				startButtonWidth, startButtonHeight);
		startButton.setFocusable(false);
		startButton.setBackground(new Color(0,0x99,0));
		startButton.setForeground(Color.white);
		
		cpuUsageLabel = new JLabel("CPU:     ");
		add(cpuUsageLabel);
		cpuUsageLabel.setLocation((int) (getWidth() * 0.6), 0);
		cpuUsageLabel.setFont(new Font("Courier New", Font.BOLD, 18));
		cpuUsageLabel.setForeground(Color.red);
		cpuUsageLabel.setSize(cpuUsageLabel.getMinimumSize());
		
		validate();
	}
	
	/**
	 * Draws the taskbar, the CPU usage, the score.
	 * @param g The Graphics object on which to draw
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.fillRect(0, getHeight() - taskbarHeight, getWidth(), taskbarHeight);
		//g.drawRect(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * Gets the height of the taskbar
	 * @return The height of the taskbar
	 */
	public int getTaskbarHeight() {
		return taskbarHeight;
	}
	
	public void setCpuUsage(int cpuUsage) {
		cpuUsageLabel.setText("CPU: " + cpuUsage + "%");
	}
}