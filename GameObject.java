/**
 * The base class of all in-game objects that interact with each other.
 */
import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.HashMap;

abstract class GameObject {
	public GameObject(Rectangle bounds) {
		position = new Point2D.Double(0,0);
		accel = new Point2D.Double(0,0);
		velocity = new Point2D.Double(0,0);
		this.bounds = (Rectangle) bounds.clone();
		
		lastKinematicsVars = new HashMap<>();
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
	private Point2D.Double velocity;
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
	 * All classes should override this method like so:
	 * 
	 * g.getCollHandler().to(this);
	 * 
	 * This code takes the CollHandler of the other object, and calls the 
	 * handler appropriate for this object. This way, handling collisions with
	 * various objects can be handled through polymorphism rather than e.g.
	 * object identifier properties.
	 * 
	 * In other words, the CollHandler is the visitor
	 * in the <em>visitor design pattern</em>.
	 * 
	 * Note that onCollide(g) calls g's handlers, not this object's.
	 * @param g The other GameObject.
	 */
	public abstract void onCollide(GameObject g);
	
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
		position.x += getVelocity().x;
		position.y += getVelocity().y;
	}
	
	/**
	 * Decelerates the object by some multiplier of the object
	 * @param multiplier A number by which to multiply the acceleration
	 * and velocity. Should be in (0,1).
	 */
	protected void decelerate(double multiplier) {
		double epsilon = 0;
		/*
		System.out.println("Acceleration: " + accel);
		System.out.println("vx:" + velocity.x);
		System.out.println("d ax: " + multiplier * -velocity.x);
		*/
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
	
	public void calculateCollRectFromSprite(String s) {
		BufferedImage theSprite = BackgroundGame.sprites.get(s);
		collRectOffset = new Rectangle(0,0, 
				theSprite.getWidth(), theSprite.getHeight());
	}
	
	/**
	 * Holds values for position, velocity, and acceleration stored
	 * through a call to @link stashKinematicsVars.
	 */
	HashMap<String, Point2D.Double> lastKinematicsVars;
	
	/**
	 * Has the object store its current kinematics variables (s-v-a) 
	 * in case they have to be restored after e.g. a collision
	 */
	public void stashKinematicsVars() {
		lastKinematicsVars.put("position", (Point2D.Double) position.clone());
		lastKinematicsVars.put("velocity", (Point2D.Double) getVelocity().clone());
		lastKinematicsVars.put("accel", (Point2D.Double) accel.clone());
	}
	
	public void popKinematicsVars() {
		position = lastKinematicsVars.get("position");
		setVelocity(lastKinematicsVars.get("velocity"));
		accel = lastKinematicsVars.get("accel");
	}

	/**
	 * @return the velocity
	 */
	public Point2D.Double getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Point2D.Double velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the collHandler
	 */
	public CollHandler getCollHandler() {
		return collHandler;
	}
	
	/**
	 * Child classes will implement this interface, overriding the various 
	 * methods to be called on collision with various objects. Handlers are 
	 * then linked to the object using @link addCollHandler. This allows
	 * the compiler to pick which method to be called
	 */
	protected interface CollHandler {
		void to(RecycleBin a);
		void to(Junk a);
		void to(Sysfile a);
	}
	
	/**
	 * Sets this object's collision handler object.
	 * @param c Object that defines functions to be called on collision with
	 * other GameObjects
	 */
	public void setCollHandler(CollHandler c) {
		collHandler = c;
	}
	
	/**
	 * The CollHandler that serves this object.
	 */
	protected CollHandler collHandler;
}
