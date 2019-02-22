package mainPackage;

/**
 * InPortalCell class represents the entry point in maze (starting cell of the
 * hero). After that point, the cell of type InPortalCell will act as a FreeCell
 * cell.
 */
public class InPortalCell extends PathCell {

	/**
	 * Create a new InPortalCell and set character to 'I' and it is visited
	 * once.
	 */
	public InPortalCell() {
		super('I');
		this.setNumVisited(1);
	}
}
