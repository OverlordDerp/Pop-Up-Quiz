
import java.awt.Rectangle;
import java.awt.geom.Point2D.Double;
class Sysfile extends GameObject {
	
	public enum Size { S, M, L };
	public Sysfile(Rectangle bounds, Size s) {
		super(bounds);
		// Some distance for (de)spawning offscreen
		bounds.y -= 64;
		bounds.height += 128;
			
		switch(s) {
			case S:
				sprite = "sysfileSmall";
				accel = new Double(0, 2.5);
				break;
			case M:
				sprite = "sysfileMedium";
				accel = new Double(0, 2.2);
				break;
			case L:
				sprite = "sysfileLarge";
				accel = new Double(0, 2);
				break;	
		}
		
		calculateCollRectFromSprite();
		
		collHandler = new CollHandler() {
			public void to(Sysfile a) {}
			public void to(Junk a) {}
			public void to(RecycleBin a) {
				bgg.increaseCpuUsage(5);
			}
		};
	}
	
	public void collideWith(GameObject g) {
		g.getCollHandler().to(this);
	}
	
	public void onOutOfBounds() {
		kill();
	}
}
