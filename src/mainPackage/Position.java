package mainPackage;

/**
 * Position class represents the coordinates of a cell on the maze board. The
 * coordinates are described by x and y, where the point (0, 0) is the top-left
 * cell on the maze. Two positions are equals iff they have same x and same y.
 * 
 * @author razvan
 *
 */
public class Position {

	/**
	 * Represents the x coordinate on horizontal direction.
	 */
	private int x;

	/**
	 * Represents the y coordinate on vertical direction.
	 */
	private int y;

	/**
	 * Create a new Position with x and y uninitialized.
	 */
	public Position() {
	}

	/**
	 * Create a new Position with a given x and y.
	 * 
	 * @param x
	 *            the x coordinate on horizontal
	 * @param y
	 *            the y coordinate on vertical
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Setter for both x and y.
	 * 
	 * @param x
	 *            the x coordinate on horizontal
	 * @param y
	 *            the y coordinate on vertical
	 */
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x.
	 * 
	 * @return the x coordinate on horizontal
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter for x.
	 * 
	 * @param x
	 *            the x coordinate on horizontal
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter for y.
	 * 
	 * @return the y coordinate on vertical
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter for y.
	 * 
	 * @param y
	 *            the y coordinate on vertical
	 */
	public void setY(int y) {
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof Position))
			return false;

		Position pos = (Position) obj;
		return (this.x == pos.x && this.y == pos.y) ? true : false;
	}

}