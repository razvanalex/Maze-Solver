package mainPackage;

/**
 * Cell class represents a cell inside of a maze, that can be a wall, a portal
 * such as enter portal or exit portal, or the roads of the maze.
 */
public abstract class Cell {
	/**
	 * The character given from input file. Can be used to print the maze.
	 */
	private char character;

	/**
	 * Create a new cell and store its character.
	 * 
	 * @param character
	 *            the character of the cell
	 */
	public Cell(char character) {
		this.character = character;
	}

	/**
	 * Getter for character.
	 * 
	 * @return the character of the cell
	 */
	public char getCharacter() {
		return character;
	}

	/**
	 * Setter for character.
	 * 
	 * @param character
	 *            the new character of the cell (only for dynamic maze)
	 */
	public void setCharacter(char character) {
		this.character = character;
	}
}
