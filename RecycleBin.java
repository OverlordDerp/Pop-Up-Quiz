
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;
import java.awt.Color;

class RecycleBin extends JPanel {

	public RecycleBin() {
		super();

	}

	public void increaseVelocity() {
		++velocity;
	}

	public void decreaseVelocity() {
		--velocity;
	}

	/**
	 * Returns whether the bin has collected any items
	 * @return 
	 */
	public boolean isUsed() {
		return false;
	}
	private double movementAccel = 0;
	private double velocity = 0;
	public static BufferedImage emptyBin, fullBin;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.drawRect(0, 0, 10, 10);
		if (isUsed()) {
			g.drawImage(fullBin, getBounds().x, getBounds().y, null);
		} else {
			g.drawImage(emptyBin, getBounds().x, getBounds().y, null);
		}
	}

	// Stuff to be looped
	public void cycle() {
		setLocation((int) (getX() + velocity), getY());
		if (getX() < 0) {
			setLocation(0, getY());
			velocity = 0;
		} else if (getX() > getWidth() - getWidth()) {
			setLocation(getWidth() - getWidth(), getY());
			velocity = 0;
		}
		velocity -= 0.1 * Math.signum(velocity);
	}
}