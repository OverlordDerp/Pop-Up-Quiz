
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
	 * 
	 * @return whether the bin has collected any items
	 */
	public boolean isUsed() {
		return false;
	}

	
	/**
	 * Every cycle, decelerates the recycle bin.
	 */
	public void cycle() {
		super.decelerate(0.2);
		super.applyAccel();
		super.applyVelocity();
	}
	
	private double constAccel = 0.00001;	
}