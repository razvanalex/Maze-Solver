package mainPackage;

/**
 * CellFactory class implements a singleton and a factory design pattern, used
 * to create new cells for maze, using a string as identifier.
 */
public class CellFactory {
	/**
	 * The instance of CellFactory, hence it is a singleton
	 */
	private static CellFactory instance = null;

	/**
	 * Create a new instance of CellFactory.
	 */
	private CellFactory() {
	}

	/**
	 * Get the existing instance or create a new one otherwise.
	 * 
	 * @return instance of CellFactory
	 */
	public static CellFactory getInstance() {
		if (instance == null) {
			instance = new CellFactory();
		}

		return instance;
	}

	/**
	 * The factory function. It creates a new cell of type given as parameter.
	 * 
	 * @param type
	 *            of cell. It can be: I, O, # or . all given as String.
	 * @return a new Cell instance of a type given as parameter
	 */
	public Cell createCell(String type) {
		if (type.equals("I")) {
			return new InPortalCell();
		} else if (type.equals("O")) {
			return new OutPortalCell();
		} else if (type.equals("#")) {
			return new WallCell();
		} else if (type.equals(".")) {
			return new FreeCell();
		}

		return null;
	}
}
