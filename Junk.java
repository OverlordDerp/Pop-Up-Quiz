
import java.awt.geom.Point2D.Double;
import java.awt.Rectangle;
class Junk extends GameObject {
	public Junk(Rectangle bounds) {
		super(bounds);
		// Some distance for the stuff to fall so it doesn't just
		// appear on-screen
		bounds.y -= 64;
		bounds.height += 64;
		
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
	
	public void cycle() {
		super.cycle();
		bgg.increaseCpuUsage(0.01);
	}
	
	//Stay on-screen until collected.
	public void onOutOfBounds() {
		confine();
	}
}
