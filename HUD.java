import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;

public class HUD extends JPanel {
	public HUD() {
		super();
		
	}
	
	protected void paintComponent(Graphics g) {
		final int startButtonHeight = 20;
		final int taskbarHeight = startButtonHeight + 4;
		
		g.setColor(Color.RED);
		g.fillRect(0, getHeight() - taskbarHeight, getWidth(), taskbarHeight);
		
		final int startButtonWidth = 80;
		g.setColor(Color.BLUE);
		g.fillRect(1, getHeight() - startButtonHeight + 1, startButtonWidth,
				startButtonHeight);		
	}
}