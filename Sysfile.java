
import java.awt.Rectangle;
class Sysfile extends GameObject {
	
	public enum Size { S, M, L };
	public Sysfile(Rectangle bounds, Size s) {
		super(bounds);
			
		switch(s) {
			case S:
				sprite = "sysfileSmall";
				break;
			case M:
				sprite = "sysfileMedium";
				break;
			case L:
				sprite = "sysfileLarge";
				break;	
		}
		
		calculateCollRectFromSprite(sprite);
		
		collHandler = new CollHandler() {
			public void to(Sysfile a) {}
			public void to(Junk a) {}
			public void to(RecycleBin a) {}
		};
	}
	
	public void onCollide(GameObject g) {
		g.getCollHandler().to(this);
	}
}
