package mainPackage;

/**
 * The class HeroOutOfGroundException it is a throwable exception, that can be
 * thrown when the hero tries to leave the playground, going to a cell that is
 * outside of the border.
 */
public class HeroOutOfGroundException extends Exception {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = -3627874153022380187L;

	/**
	 * Constructs a new exception when the hero wants to go to a cell outside of
	 * border.
	 */
	public HeroOutOfGroundException() {
	}

}
