
import java.awt.Rectangle;
class Junk extends GameObject {
	public Junk(Rectangle bounds) {
		super(bounds);
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
}
