
import java.awt.Rectangle;
class Junk extends GameObject {
	public Junk(Rectangle bounds) {
		super(bounds);
		// Some distance for the stuff to fall so it doesn't just
		// appear or vanish on-screen
		bounds.y -= 64;
		bounds.height += 128;
		
		
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
		bgg.increaseCpuUsage(0.05);
	}
}
