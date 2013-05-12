
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
	/**
	 * The constructor. 
	 * Sets the sprite to an empty bin, which becomes that of the
	 * full bin once something has been collected.
	 * 
	 * RecycleBin has collision handlers for @link Sysfile and @link Junk.
	 * When the recycle bin collides with a @link Sysfile, the CPU usage
	 * is increased by 6. When @link Junk is collected, CPU usage decreases
	 * by 5. In either case the object is consumed.
	 * @param bounds 
	 */
	public RecycleBin(Rectangle bounds) {
		super(bounds);
		sprite = "emptyBin";
		calculateCollRectFromSprite();

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
				bgg.increaseCpuUsage(6);
			}

			public void to(Junk a) {
				++amountCollected;
				a.kill();
				if (isUsed() && !sprite.equals("fullBin")) {
					sprite = "fullBin";
				}
				bgg.decreaseCpuUsage(5);
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
		return getAmountCollected() != 0;
	}

	/**
	 * Every cycle, decelerates the recycle bin according to how many
	 * items have been collected. The higher the amount collected, the slower
	 * the deceleration. This is construed as "momentum".
	 */
	public void cycle() {


			super.decelerate((-0.1 * Math.log (0.01 * (amountCollected+1))) / 0.6);
		super.applyAccel();
		super.applyVelocity();
	}


	public void collideWith(GameObject g) {
		g.getCollHandler().to(this);
	}
	
	/**
	 * The number of items collected.
	 */
	private long amountCollected = 0;

	/**
	 * @return the number of items collected by the garbage bin. Determines
	 * the difficulty.
	 */
	public long getAmountCollected() {
		return amountCollected;
	}
}