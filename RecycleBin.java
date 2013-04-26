
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;
import java.awt.Color;
import java.awt.Point;
/**
 * The recycling bin that collects stuff.
 * @author quincy
 */
class RecycleBin extends GameObject {

	public RecycleBin() {
		super();
		sprite = "emptyBin";
		BufferedImage emptyBin = BackgroundGame.sprites.get(sprite);
		collRectOffset = new Rectangle(0,0, 
				emptyBin.getWidth(), emptyBin.getHeight());
	}
	/**
	 * Increments the bin's velocity (does not necessarily speed it up)
	 */
	public void increaseXVelocity() {
		++velocity.x;
	}
	/**
	 * Decrements the bin's velocity (does not necessarily speed it up)
	 */
	public void decreaseXVelocity() {
		--velocity.x;
	}

	/**
	 * Returns whether the bin has collected any items
	 * @return 
	 */
	public boolean isUsed() {
		return false;
	}

	/**
	 * Every cycle, decelerates the recycle bin.
	 */
	public void cycle() {
		velocity.x -= 0.1 * Math.signum(velocity.x);
		velocity.y -= 0.1 * Math.signum(velocity.y);
	}
}