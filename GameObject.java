
import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * The base class of all in-game objects that interact with each other.
 * @author quincy
 */
abstract class GameObject {
	/**
	 * Constructs a GameObject at rest.
	 * @param bounds The boundaries of the GameObject's movement
	 */
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
	 * The index of the sprite for the array of Images in  
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
	 * various objects can be handled using overloading rather than e.g.
	 * object-identifying properties. The advantage is that the decision of
	 * which handler to call can be decided at compile-time.
	 * 
	 * More technically, collision handlers have been implemented through
	 * the <em>visitor design pattern</em>, where implementations of
	 *  CollHandler are the visitors.
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
	 * @param b The new boundary of the object's position
	 */
	public void setBounds(Rectangle b) {
		bounds = b;
	}

	/**
	 * Returns the position of the object
	 * @return the position of the object
	 */
	public Point2D.Double getPosition() {
		return new Point2D.Double(position.x, position.y);
	}

	/**
	 * The new position of the object.
	 * @param position This object's new position
	 */
	public void setPosition(Point2D.Double position) {
		this.position = position;
	}

	/**
	 * Returns the String identifier of the object's sprite
	 * @return the sprite identifier
	 */
	public String getSprite() {
		return sprite;
	}

	/**
	 * Sets the identifier to tihs object's new sprite.
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
	 * Returns the collision rectangle offset
	 * @return A rectangle containing an offset from the top-left corner 
	 * of the object's sprite, and a length and a width, to represent the
	 * collision rectangle of the object
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
	
	/**
	 * Computes the object's collision rectangle from  collRectOffset
	 * @return The collision rectangle of the object
	 */
	public Rectangle getCollRect() {
		return new Rectangle((int) (position.x + collRectOffset.x),
				(int) (position.y + collRectOffset.y),
				collRectOffset.width, collRectOffset.height);
	}
	
	/**
	 * Adds the components of the object's acceleration to its velocity
	 */
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
	 * Accesses the acceleration.
	 * @return the acceleration of the object.
	 */
	public Point2D.Double getAccel() {
		return accel;
	}

	/**
	 * Sets this object's acceleration.
	 * @param accel The new acceleration.
	 */
	public void setAccel(Point2D.Double accel) {
		this.accel = accel;
	}
	
	/**
	 * Sets this object's collision rectangle offset to begin at
	 * corner (0,0) and be the size of the given sprite
	 */
	protected void calculateCollRectFromSprite() {
		BufferedImage theSprite = bgg.getSprites().get(sprite);
		collRectOffset = new Rectangle(0,0, 
				theSprite.getWidth(), theSprite.getHeight());
	}
	
	/**
	 * Holds values for position, velocity, and acceleration stored
	 * through a call to  stashKinematicsVars.
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
	
	/**
	 * Restores the kinematics variables stored by  stashKinematicVars.
	 */
	public void popKinematicsVars() {
		position = lastKinematicsVars.get("position");
		setVelocity(lastKinematicsVars.get("velocity"));
		accel = lastKinematicsVars.get("accel");
	}

	/**
	 * Returns the velocity of the object
	 * @return the velocity
	 */
	public Point2D.Double getVelocity() {
		return velocity;
	}

	/**
	 * Sets this object's velocity.
	 * @param velocity The object's new velocity
	 */
	public void setVelocity(Point2D.Double velocity) {
		this.velocity = velocity;
	}

	/**
	 * Returns the CollHandler object associated with this object. Called
	 * exclusively by other GameObjects' collideWith methods.
	 * @return the CollHandler object associated with this object.
	 */
	public CollHandler getCollHandler() {
		return collHandler;
	}
	
	/**
	 * Child classes will implement this interface, overriding the various 
	 * methods to be called on collision with various kinds of
	 * GameObjects. Handlers are 
	 * then linked to the object using  addCollHandler. This allows
	 * the compiler to pick which handler to call since it is overloaded
	 * for every type of GameObject
	 */
	protected interface CollHandler {
		/**
		 * What to do on collision with a RecycleBin instance
		 * @param a The RecycleBin collided into
		 */
		void to(RecycleBin a);
		/**
		 * What to do on collision with a Junk instance
		 * @param a The Junk collided into
		 */
		void to(Junk a);
		/**
		 * What to do on collision with a Sysfile instance
		 * @param a The Sysfile collided into
		 */
		void to(Sysfile a);
	}
	
	/**
	 * Sets this object's collision handler object.
	 * @param c Object that defines handlers to be called on collision with
	 * other types of GameObjects
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
	 * Moves g until it is within the rectangle specified by  bounds.
	 */
	public void confine() {
		confine(bounds);
	}
	
		
	/**
	 * Calculates the rectangle from the top-left corner of the object's sprite
	 * to its bottom-right.
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
