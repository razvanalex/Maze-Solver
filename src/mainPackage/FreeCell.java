package mainPackage;

/**
 * FreeCell represents a cell that is't marked with '.' character in input file.
 * It represents a pathway cell from maze.
 */
public class FreeCell extends PathCell {

	/**
	 * Constructor for a new FreeCell. It initializes the character to '.'.
	 */
	public FreeCell() {
		super('.');
	}
}
