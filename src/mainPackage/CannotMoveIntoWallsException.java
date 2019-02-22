package mainPackage;

/**
 * The class CannotMoveIntoWallsException it is a throwable exception, that can
 * be thrown when the hero tries to go through a wall.
 */
public class CannotMoveIntoWallsException extends Exception {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 4111646604484814082L;

	/**
	 * Constructs a new exception when the hero wants to move into a wall cell.
	 */
	public CannotMoveIntoWallsException() {
	}
}
