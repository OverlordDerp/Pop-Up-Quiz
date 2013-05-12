
import java.awt.Rectangle;
import java.awt.geom.Point2D.Double;

/**
 * A system file. Comes in three sizes. Increases CPU usage if junked.
 * @author quincy
 */
class Sysfile extends GameObject {
	/**
	 * An enumeration for the three sizes of  Sysfile
	 */
	public enum Size { S, M, L };
	/**
	 * Modifies the bounds to the object despawns off-screeen. This object's
	 * collision handlers are empty.
	 * @param bounds The boundaries of this object's creator
	 * @param s A size for this object
	 */
	public Sysfile(Rectangle bounds, Size s) {
		super(bounds);
		// Some distance for (de)spawning offscreen
		bounds.height += 64*3;
			
		switch(s) {
			case S:
				sprite = "sysfileSmall";
				accel = new Double(0, 0.35);
				break;
			case M:
				sprite = "sysfileMedium";
				accel = new Double(0, 0.32);
				break;
			case L:
				sprite = "sysfileLarge";
				accel = new Double(0, 0.3);
				break;	
		}
		
		calculateCollRectFromSprite();
		
		collHandler = new CollHandler() {
			public void to(Sysfile a) {}
			public void to(Junk a) {}
			public void to(RecycleBin a) {
			}
		};
	}
	
	public void collideWith(GameObject g) {
		g.getCollHandler().to(this);
	}
	
	/**
	 * Destroys the sysfile once it leaves the boundaries of the screen.
	 */
	public void onOutOfBounds() {
		kill();
	}
}
