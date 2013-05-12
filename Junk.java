
import java.awt.geom.Point2D.Double;
import java.awt.Rectangle;

/**
 * A junk file. Increases the CPU usage as it stays on the screen.
 * @author quincy
 */
class Junk extends GameObject {
	/**
	 * Creates the junk and gives it a bit of downwards
	 * acceleration. 
	 * @param bounds The boundary of the game that created it. 
	 */
	public Junk(Rectangle bounds) {
		super(bounds);
		
		accel = new Double(0, 1.5);
		
		sprite = "junk";
		
		calculateCollRectFromSprite();
		
		collHandler = new CollHandler() {
			public void to(Sysfile a) {}
			public void to(Junk a) {}
			public void to(RecycleBin a) {}
		};
	}
	
	public void collideWith(GameObject g) {
		g.getCollHandler().to(this);
	}
	
	/**
	 * Increase CPU usage by 0.01 per iteration.
	 */
	public void cycle() {
		super.cycle();
		bgg.increaseCpuUsage(0.01);
	}
	
	/**
	 * Keep the file on-screen once it has hit the bottom of its boundary.
	 */
	public void onOutOfBounds() {
		confine();
	}
}
