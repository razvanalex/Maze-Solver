package mainPackage;

/**
 * WallCell represents a cell that is not marked with '#' character in input
 * file. It represents a cell where hero cannot stay, being a wall.
 */
public class WallCell extends Cell {

	/**
	 * Constructor for a new WallCell. It initializes the character to '#'.
	 */
	public WallCell() {
		super('#');
	}
}
