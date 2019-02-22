package mainPackage;

/**
 * The abstract class PathCell it is used to identify cells where hero can stay.
 * PathCell cells are FreeCell, InPortalCell and OutPortalCell.
 * 
 * @author razvan
 *
 */
public abstract class PathCell extends Cell {

	/**
	 * Counts the number of times hero passes the current cell.
	 */
	private int numVisited;

	/**
	 * Create a new PathCell, set numVisited to 0 and initialize the character.
	 * 
	 * @param character
	 *            the character of the cell
	 */
	public PathCell(char character) {
		super(character);
		numVisited = 0;
	}

	/**
	 * Getter for numVisited
	 * 
	 * @return numVisited
	 */
	public int getNumVisited() {
		return numVisited;
	}

	/**
	 * Setter for numVisited.
	 * 
	 * @param numVisited
	 *            updated numVisited
	 */
	public void setNumVisited(int numVisited) {
		this.numVisited = numVisited;
	}

}
