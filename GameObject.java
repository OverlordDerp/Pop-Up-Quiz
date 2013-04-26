/**
 * The base class of all in-game objects that interact with each other.
 */
import java.awt.Point;
import java.awt.Rectangle;

abstract class GameObject {
	public GameObject() {
		position = new Point(0,0);
		accel = new Point(0,0);
		velocity = new Point(0,0);
	}
	
	/**
	 * The index of the sprite for the array of Images in @link 
	 */
	protected String sprite = null;
	/**
	 * Acceleration in x and y directions.
	 */
	protected Point accel;
	/**
	 * Velocity in x and y direcitons.
	 */
	protected Point velocity;
	/**
	 * Position.
	 */
	protected Point position;
	
	/**
	 * The rectangle on which collision calculations are based. Relative to
	 * the top left corner of the object's sprite.
	 */
	protected Rectangle collRectOffset;
	
	/**
	 * Marks this object for deletion
	 */
	public boolean isDead = false;
	
	/**
	 * Code to run over and over again.
	 */
	public void cycle() {
		
	}
	
	/**
	 * Code to run on overlapping another GameObject.
	 * Override this in child classes for polymorphism.
	 * @param g The other GameObject.
	 */
	public void onCollide(GameObject g) {
		
	}
	
	/**
	 * Boundary within which to confine the object
	 */
	protected Rectangle bounds;
	
	/**
	 * Returns the boundary of the object's position
	 * @return The boundary of the object's position
	 */
	public final Rectangle getBounds() {
		return new Rectangle(bounds);
	}
	
	/**
	 * Sets the boundary of the object's position
	 * @param The new boundary of the object's position
	 */
	public void setBounds(Rectangle b) {
		bounds = b;
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return new Point(position);
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * @return the sprite identifier
	 */
	public String getSprite() {
		return sprite;
	}

	/**
	 * @param sprite the new sprite identifier
	 */
	public void setSprite(String sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Marks the object for deletion
	 */
	public void kill() {
		isDead = true;
	}

	/**
	 * @return the collRectOffset
	 */
	public Rectangle getCollRectOffset() {
		return new Rectangle(collRectOffset);
	}

	/**
	 * @param collRectOffset the collRectOffset to set
	 */
	public void setCollRectOffset(Rectangle collRectOffset) {
		this.collRectOffset = collRectOffset;
	}
}
