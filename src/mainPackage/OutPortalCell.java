package mainPackage;

/**
 * OutPortalCell class represents the exit point in maze. When hero goes on that
 * cell, the maze is solved.
 */
public class OutPortalCell extends PathCell {

	/**
	 * Create a new OutPortalCell and set character to '#'.
	 */
	public OutPortalCell() {
		super('O');
	}
}
