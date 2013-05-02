
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

	public RecycleBin(Rectangle bounds) {
		super(bounds);
		sprite = "emptyBin";
		calculateCollRectFromSprite(sprite);
		
		/**
		 * The recycle bin should destroy every file it comes into contact with.
		 * Upon recycling something, the icon should change from the empty
		 * icon to the full one. 
		 */

		
		collHandler = new CollHandler() {
			public void to(Sysfile a) {
				++amountCollected;
				a.kill();
				if (isUsed() && !sprite.equals("fullBin")) {
					sprite = "fullBin";
				}
			}
			public void to(Junk a) {
				++amountCollected;
				a.kill();
				if (isUsed()  && !sprite.equals("fullBin")) {
					sprite = "fullBin";
				}
			}
			public void to(RecycleBin a) {
				// Should never happen
			}
		};
	}


	/**
	 * 
	 * @return whether the bin has collected any items
	 */
	public boolean isUsed() {
		return amountCollected != 0;
	}

	
	/**
	 * Every cycle, decelerates the recycle bin.
	 */
	public void cycle() {
		super.decelerate(0.2);
		super.applyAccel();
		super.applyVelocity();
	}
	

	public void onCollide(GameObject g) {
		g.getCollHandler().to(this);
	}
	
	private long amountCollected = 0;
}