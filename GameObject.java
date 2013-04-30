/**
 * The base class of all in-game objects that interact with each other.
 */
import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.util.ArrayList;

abstract class GameObject {
	public GameObject() {
		position = new Point2D.Double(0,0);
		accel = new Point2D.Double(0,0);
		velocity = new Point2D.Double(0,0);
	}
	
	/**
	 * The index of the sprite for the array of Images in @link 
	 */
	protected String sprite = null;
	/**
	 * Acceleration in x and y directions.
	 */
	protected Point2D.Double accel;
	/**
	 * Velocity in x and y direcitons.
	 */
	protected Point2D.Double velocity;
	/**
	 * Position.
	 */
	protected Point2D.Double position;
	
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
		applyAccel();
		applyVelocity();
		decelerate();
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
	public Point2D.Double getPosition() {
		return new Point2D.Double(position.x, position.y);
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D.Double position) {
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
	
	protected void applyAccel() {
		velocity.x += accel.x;
		velocity.y += accel.y;
	}
	
	/**
	 * Offsets the position by the velocity
	 */
	protected void applyVelocity() {
		position.x += velocity.x;
		position.y += velocity.y;
	}
	
	/**
	 * Decelerates the object by some multiplier of the object
	 * @param multiplier A number by which to multiply the acceleration
	 * and velocity. Should be in (0,1).
	 */
	protected void decelerate(double multiplier) {
		double epsilon = 0;
		System.out.println("Acceleration: " + accel);
		System.out.println("vx:" + velocity.x);
		System.out.println("d ax: " + multiplier * -velocity.x);
		velocity.x *= multiplier;
		velocity.y *= multiplier;
	}
	
	/**
	 * Calls decelerate(double) with multiplier 0.1
	 */
	protected void decelerate() {
		decelerate(0.1);
	}

	/**
	 * @return the accel
	 */
	public Point2D.Double getAccel() {
		return accel;
	}

	/**
	 * @param accel the accel to set
	 */
	public void setAccel(Point2D.Double accel) {
		this.accel = accel;
	}
}
