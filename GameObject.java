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
	 * This variable allows access to the BackgroundGame object
	 */
	public static BackgroundGame bgg;
	
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
	 * All classes should override this method like so:
	 * 
	 * g.getCollHandler().to(this);
	 * 
	 * This code takes the CollHandler of the other object, and calls the 
	 * handler appropriate for this object. This way, handling collisions with
	 * various objects can be handled through polymorphism rather than e.g.
	 * object-identifying properties.
	 * 
	 * In other words, the CollHandler is the visitor
	 * in the <em>visitor design pattern</em>.
	 * 
	 * Note that collideWith(g) calls g's handlers, not this object's.
	 * @param g The other GameObject.
	 */
	public abstract void collideWith(GameObject g);
	
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
	 * @param collRectOffset the new offset from the area rectangle
	 * from which to calculate the collision rectangle
	 */
	public void setCollRectOffset(Rectangle collRectOffset) {
		this.collRectOffset = collRectOffset;
	}
	
	public Rectangle getCollRect() {
		return new Rectangle((int) (position.x + collRectOffset.x),
				(int) (position.y + collRectOffset.y),
				collRectOffset.width, collRectOffset.height);
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
		velocity.x *= (1 - multiplier);
		velocity.y *= (1 - multiplier);
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
	
	/**
	 * Sets this object's collision rectangle offset to begin at
	 * corner (0,0) and be the size of the given sprite
	 * @param s A String identifying a sprite
	 */
	protected void calculateCollRectFromSprite() {
		BufferedImage theSprite = bgg.getSprites().get(sprite);
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

	
	/**
	 * Moves g until it is within the given rectangle
	 * @param r The rectangle in which to confine this object
	 */
	public void confine(Rectangle r) {
		Rectangle areaRect = getAreaRect();
		
		if (bounds.contains(areaRect)) {
			return;
		}
		
		if (areaRect.x < bounds.x) {
			areaRect.x = bounds.x;
		}
		else if (areaRect.x + areaRect.width > bounds.x + bounds.width) {
			areaRect.x = bounds.x + bounds.width - areaRect.width;
		}
		
		if (areaRect.y < bounds.y) {
			areaRect.y = bounds.y;
		}
		else if (areaRect.y + areaRect.height > bounds.y + bounds.height) {
			areaRect.y = bounds.y + bounds.height - areaRect.height;
		}
		
		position = (new Point2D.Double(areaRect.x, areaRect.y));
	}
	
	/**
	 * Moves g until it is within the rectangle specified by @link bounds.
	 */
	public void confine() {
		confine(bounds);
	}
	
		
	/**
	 * Calculates the rectangle from the top-left corner of the object's sprite
	 * to its bottom-right.
	 * @param g The object
	 * @return The area rectangle
	 */
	public Rectangle getAreaRect() {
		BufferedImage s = bgg.getSprites().get(sprite);
		if (s == null) {
			return new Rectangle((int) position.x, (int)position.y, 0, 0);
		}
		else {
			return new Rectangle((int)position.x, (int) position.y, s.getWidth(), s.getHeight());
		}
	}
	
	/**
	 * Called when this object's area rectangle does not overlap this area's
	 * bounding rectangle
	 */
	public void onOutOfBounds() {
		confine();
	}

}
