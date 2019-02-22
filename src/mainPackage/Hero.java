package mainPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Hero class represent a hero that wants to solve a maze. For the first task,
 * he has access only to neighbor cells and the size of the maze, to check if
 * our hero is still on the board, or is not staying on a wall. He only go to
 * where he can, according to the priority of directions and the number of
 * pebbles (number of times the cell was visited) on that cell. For the second
 * task, hero has access to whole map, hence breadth first search algorithm it
 * is used to determine the shortest path.
 * 
 * @author razvan
 *
 */
public class Hero {

	/**
	 * A reference to the maze map. For the first task it is used only the size
	 * of the map and neighbor cells, and for the second task it is used the
	 * whole map.
	 */
	private Maze maze;

	/**
	 * The position of the hero on the map.
	 */
	private Position position;

	/**
	 * The orientation of the hero. Orientation is relative to board.
	 */
	private Orientation orientation;

	/**
	 * The shortest path from enter portal to exit portal described using
	 * directions (e.g. LEFT, RIGHT, UP, DOWN)
	 */
	private ArrayList<Direction> shortestPath;

	/**
	 * Create a new Hero.
	 */
	public Hero() {
	}

	/**
	 * Create a new Hero initializing the maze, the starting position of the
	 * hero and the orientation towards North.
	 * 
	 * @param maze
	 *            the maze
	 */
	public Hero(Maze maze) {
		this.maze = maze;
		this.position = maze.getStartPosition();
		this.orientation = Orientation.NORTH;
	}

	/**
	 * Move the hero according to a direction given as parameter. If the hero
	 * cannot move to that direction, a message will be shown. Also, if the maze
	 * is not initialized, a message will be also shown. As implementation the
	 * hero first will turn left, right or back if necessary and then he will go
	 * forward. Finally, the cell where the hero stays will be marked as being
	 * visited.
	 * 
	 * @param direction
	 *            the direction where the hero will go.
	 * 
	 * @see mainPackage.Hero#moveFront()
	 * @see mainPackage.Hero#moveLeft()
	 * @see mainPackage.Hero#moveRight()
	 * @see mainPackage.Hero#moveBack()
	 */
	public void move(Direction direction) {
		if (maze == null) {
			System.out.println("Maze not initialized!");
			return;
		}

		switch (direction) {
			case RIGHT:
				moveRight();
				break;
			case FRONT:
				moveFront();
				break;
			case LEFT:
				moveLeft();
				break;
			case BACK:
				moveBack();
				break;
			default:
		}

		if ((maze.getCellAt(position)) instanceof FreeCell) {
			FreeCell cell = (FreeCell) maze.getCellAt(position);
			cell.setNumVisited(cell.getNumVisited() + 1);
		} else if ((maze.getCellAt(position)) instanceof InPortalCell) {
			InPortalCell cell = (InPortalCell) maze.getCellAt(position);
			cell.setNumVisited(cell.getNumVisited() + 1);
		} else if ((maze.getCellAt(position)) instanceof OutPortalCell) {
			OutPortalCell cell = (OutPortalCell) maze.getCellAt(position);
			cell.setNumVisited(cell.getNumVisited() + 1);
		}
	}

	/**
	 * Move the hero to hero's right cell. First, the hero will turn right:
	 * <ul>
	 * <li>when our hero is looking to North, right cell will be to East</li>
	 * <li>when our hero is looking to East, right cell will be to South</li>
	 * <li>when our hero is looking to South, right cell will be to West</li>
	 * <li>when our hero is looking to West, right cell will be to North</li>
	 * </ul>
	 * 
	 * Then, the hero will move forward.
	 * 
	 * @see mainPackage.Hero#moveFront()
	 */
	private void moveRight() {
		switch (this.orientation) {
			case NORTH:
				this.orientation = Orientation.EAST;
				break;
			case EAST:
				this.orientation = Orientation.SOUTH;
				break;
			case SOUTH:
				this.orientation = Orientation.WEST;
				break;
			case WEST:
				this.orientation = Orientation.NORTH;
				break;
			default:
		}

		moveFront();
	}

	/**
	 * Move the hero to hero's left cell. First, the hero will turn left:
	 * <ul>
	 * <li>when our hero is looking to North, left cell will be to WEST</li>
	 * <li>when our hero is looking to East, left cell will be to NORTH</li>
	 * <li>when our hero is looking to South, left cell will be to EAST</li>
	 * <li>when our hero is looking to West, left cell will be to SOUTH</li>
	 * </ul>
	 * 
	 * Then, the hero will move forward.
	 * 
	 * @see mainPackage.Hero#moveFront()
	 */
	private void moveLeft() {
		switch (this.orientation) {
			case NORTH:
				this.orientation = Orientation.WEST;
				break;
			case EAST:
				this.orientation = Orientation.NORTH;
				break;
			case SOUTH:
				this.orientation = Orientation.EAST;
				break;
			case WEST:
				this.orientation = Orientation.SOUTH;
				break;
			default:
		}

		moveFront();
	}

	/**
	 * Move the hero to hero's back cell. First, the hero will turn back:
	 * <ul>
	 * <li>when our hero is looking to North, back cell will be to SOUTH</li>
	 * <li>when our hero is looking to East, back cell will be to WEST</li>
	 * <li>when our hero is looking to South, back cell will be to NORTH</li>
	 * <li>when our hero is looking to West, back cell will be to EAST</li>
	 * </ul>
	 * 
	 * Then, the hero will move forward.
	 * 
	 * @see mainPackage.Hero#moveFront()
	 */
	private void moveBack() {
		switch (this.orientation) {
			case NORTH:
				this.orientation = Orientation.SOUTH;
				break;
			case EAST:
				this.orientation = Orientation.WEST;
				break;
			case SOUTH:
				this.orientation = Orientation.NORTH;
				break;
			case WEST:
				this.orientation = Orientation.EAST;
				break;
			default:
		}

		moveFront();
	}

	/**
	 * Move the hero to the cell next to him. If the orientation of the hero is:
	 * <ul>
	 * <li>North - the position on Y will decrease</li>
	 * <li>East - the position on X will increase</li>
	 * <li>South - the position on Y will increase</li>
	 * <li>West - the position on X will decrease</li>
	 * </ul>
	 * 
	 * If the hero tries to leave the board or tries to walk on a wall then an
	 * exception will be thrown and treated by showing a message.
	 * 
	 * @see mainPackage.Hero#moveNorth()
	 * @see mainPackage.Hero#moveEast()
	 * @see mainPackage.Hero#moveSouth()
	 * @see mainPackage.Hero#moveWest()
	 */
	private void moveFront() {
		try {
			switch (this.orientation) {
				case NORTH:
					moveNorth();
					break;
				case EAST:
					moveEast();
					break;
				case SOUTH:
					moveSouth();
					break;
				case WEST:
					moveWest();
					break;
				default:
			}
		} catch (HeroOutOfGroundException e) {
			System.out.println("Hero is out of bounds!");
		} catch (CannotMoveIntoWallsException e) {
			System.out.println("Hero tries to move into a wall!");
		}
	}

	/**
	 * The hero will move to North, this means that the Y coordinate of hero's
	 * position will decrease.
	 * 
	 * @throws HeroOutOfGroundException
	 *             when the hero tries to leave the ground
	 * @throws CannotMoveIntoWallsException
	 *             when the hero tries to move into a wall
	 */
	private void moveNorth()
			throws HeroOutOfGroundException, CannotMoveIntoWallsException {
		if (position.getY() <= 0) {
			throw new HeroOutOfGroundException();

		} else if (maze.getCellAt(position.getX(),
				position.getY() - 1) instanceof WallCell) {
			throw new CannotMoveIntoWallsException();

		} else {
			position.setY(position.getY() - 1);
		}
	}

	/**
	 * The hero will move to South, this means that the Y coordinate of hero's
	 * position will increased.
	 * 
	 * @throws HeroOutOfGroundException
	 *             when the hero tries to leave the ground
	 * @throws CannotMoveIntoWallsException
	 *             when the hero tries to move into a wall
	 */
	private void moveSouth()
			throws HeroOutOfGroundException, CannotMoveIntoWallsException {
		if (position.getY() >= maze.getHeight() - 1) {
			throw new HeroOutOfGroundException();

		} else if (maze.getCellAt(position.getX(),
				position.getY() + 1) instanceof WallCell) {
			throw new CannotMoveIntoWallsException();

		} else {
			position.setY(position.getY() + 1);
		}
	}

	/**
	 * The hero will move to East, this means that the X coordinate of hero's
	 * position will increase.
	 * 
	 * @throws HeroOutOfGroundException
	 *             when the hero tries to leave the ground
	 * @throws CannotMoveIntoWallsException
	 *             when the hero tries to move into a wall
	 */
	private void moveEast()
			throws HeroOutOfGroundException, CannotMoveIntoWallsException {
		if (position.getX() >= maze.getWidth() - 1) {
			throw new HeroOutOfGroundException();

		} else if (maze.getCellAt(position.getX() + 1,
				position.getY()) instanceof WallCell) {
			throw new CannotMoveIntoWallsException();

		} else {
			position.setX(position.getX() + 1);
		}
	}

	/**
	 * The hero will move to West, this means that the X coordinate of hero's
	 * position will decrease.
	 * 
	 * @throws HeroOutOfGroundException
	 *             when the hero tries to leave the ground
	 * @throws CannotMoveIntoWallsException
	 *             when the hero tries to move into a wall
	 */
	private void moveWest()
			throws HeroOutOfGroundException, CannotMoveIntoWallsException {
		if (position.getX() <= 0) {
			throw new HeroOutOfGroundException();

		} else if (maze.getCellAt(position.getX() - 1,
				position.getY()) instanceof WallCell) {
			throw new CannotMoveIntoWallsException();

		} else {
			position.setX(position.getX() - 1);
		}
	}

	/**
	 * Getter for position of the hero.
	 * 
	 * @return current position of the hero on board
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Setter for position of the hero.
	 * 
	 * @param position
	 *            new position of the hero on board
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Getter for orientation of the hero.
	 * 
	 * @return the orientation of the hero
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Setter for orientation of the hero.
	 * 
	 * @param orientation
	 *            new orientation of the hero
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Getter for maze.
	 * 
	 * @return the reference to maze
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * Setter for maze.
	 * 
	 * @param maze
	 *            a new maze
	 */
	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	/**
	 * Compute the next direction where the hero will go. The algorithm is the
	 * following:
	 * <ol>
	 * <li>Generate an array of neighbor cells that are not wall cells</li>
	 * <li>If a neighbor cell is out portal, then return the direction to
	 * it</li>
	 * <li>Sort the array according to the priority (number of times the cell
	 * was visited and direction priority)</li>
	 * <li>Return first element of the array</li>
	 * </ol>
	 * 
	 * @return the direction the hero will follow
	 * 
	 * @see mainPackage.Hero#arrayOfNeighbors()
	 * @see mainPackage.Hero.NeighborCell
	 */
	public Direction aiMove() {
		ArrayList<NeighborCell> array = arrayOfNeighbors();
		for (NeighborCell e : array) {
			if (e.getCell() instanceof OutPortalCell) {
				return getCellDirection(e.getCell());
			}
		}

		Collections.sort(array, new NeighborCell());

		return array.get(0).direction;
	}

	/**
	 * Create an array of cells that are PathCell (InPortalCell, OutPortalCell,
	 * FreeCell) in neighborhood of the cell where hero stays. Each element from
	 * the array is a NeighborCell instance, having a reference to that cell and
	 * here using the direction field (the direction where the cell is
	 * positioned according to the position of the hero).
	 * 
	 * @return an array of NeighborCell elements.
	 * 
	 * @see mainPackage.Hero#getCellDirection(Cell)
	 * @see mainPackage.Maze#getCellAt(int, int)
	 */
	private ArrayList<NeighborCell> arrayOfNeighbors() {
		ArrayList<NeighborCell> neighbors = new ArrayList<>();
		Cell cell;

		cell = maze.getCellAt(position.getX() + 1, position.getY());
		if (cell instanceof PathCell) {
			neighbors.add(
					new NeighborCell((PathCell) cell, getCellDirection(cell)));
		}

		cell = maze.getCellAt(position.getX(), position.getY() - 1);
		if (cell instanceof PathCell) {
			neighbors.add(
					new NeighborCell((PathCell) cell, getCellDirection(cell)));
		}

		cell = maze.getCellAt(position.getX() - 1, position.getY());
		if (cell instanceof PathCell) {
			neighbors.add(
					new NeighborCell((PathCell) cell, getCellDirection(cell)));
		}

		cell = maze.getCellAt(position.getX(), position.getY() + 1);
		if (cell instanceof PathCell) {
			neighbors.add(
					new NeighborCell((PathCell) cell, getCellDirection(cell)));
		}

		return neighbors;
	}

	/**
	 * Create an array of cells that are PathCell (InPortalCell, OutPortalCell,
	 * FreeCell) in neighborhood of the cell dictated by a given position. Each
	 * element from the array is a NeighborCell instance, having a reference to
	 * that cell and here using the direction field (the direction where the
	 * cell is positioned according to the position given as argument).
	 * 
	 * @param pos
	 *            a position where to find neighbor cell
	 * @return an array of NeighborCell elements.
	 * 
	 * @see mainPackage.Hero#getCellDirection(Cell)
	 * @see mainPackage.Maze#getCellAt(int, int)
	 */
	private ArrayList<NeighborCell> arrayOfNeighbors(Position pos) {
		ArrayList<NeighborCell> neighbors = new ArrayList<>();
		Cell cell;

		cell = maze.getCellAt(pos.getX() + 1, pos.getY());
		if (cell instanceof PathCell) {
			neighbors.add(new NeighborCell((PathCell) cell, Orientation.WEST));
		}

		cell = maze.getCellAt(pos.getX(), pos.getY() - 1);
		if (cell instanceof PathCell) {
			neighbors.add(new NeighborCell((PathCell) cell, Orientation.NORTH));
		}

		cell = maze.getCellAt(pos.getX() - 1, pos.getY());
		if (cell instanceof PathCell) {
			neighbors.add(new NeighborCell((PathCell) cell, Orientation.EAST));
		}

		cell = maze.getCellAt(pos.getX(), pos.getY() + 1);
		if (cell instanceof PathCell) {
			neighbors.add(new NeighborCell((PathCell) cell, Orientation.SOUTH));
		}

		return neighbors;
	}

	/**
	 * Get the direction of a cell according to the orientation of the hero.
	 * 
	 * @param cell
	 *            a cell
	 * @return the direction of the cell according to he orientation of the hero
	 * 
	 * @see mainPackage.Hero#getCellDirection(Orientation, Orientation)
	 */
	private Direction getCellDirection(Cell cell) {
		Orientation cellOrientation = getCellOrientation(cell);
		return getCellDirection(cellOrientation, this.orientation);
	}

	/**
	 * Get the direction of a cell according to the orientation of both cell,
	 * the reference and the other one, by calculating the difference of the
	 * indices of orientations in enumeration.
	 * 
	 * @param cellOrientation
	 *            orientation of a cell
	 * @param baseOrientation
	 *            orientation of base cell
	 * @return a direction where to find the cell, according the a base cell.
	 */
	private Direction getCellDirection(Orientation cellOrientation,
			Orientation baseOrientation) {
		int difference = cellOrientation.ordinal() - baseOrientation.ordinal();

		switch (difference) {
			case 0:
				return Direction.FRONT;
			case 1:
			case -3:
				return Direction.LEFT;
			case 2:
			case -2:
				return Direction.BACK;
			case 3:
			case -1:
				return Direction.RIGHT;
		}

		return null;
	}

	/**
	 * Get the orientation of a cell according to the position of the hero.
	 * 
	 * @param cell
	 *            any neighbor cell
	 * @return the orientation of the cell according to the position of the hero
	 */
	private Orientation getCellOrientation(Cell cell) {
		if (cell == maze.getCellAt(position.getX(), position.getY() - 1)) {
			return Orientation.NORTH;

		} else if (cell == maze.getCellAt(position.getX() - 1,
				position.getY())) {
			return Orientation.WEST;

		} else if (cell == maze.getCellAt(position.getX(),
				position.getY() + 1)) {
			return Orientation.SOUTH;

		} else if (cell == maze.getCellAt(position.getX() + 1,
				position.getY())) {
			return Orientation.EAST;

		}

		return null;
	}

	/**
	 * Compute the shortest path between InPortalCell and OutPortalCell using
	 * breadth first search algorithm. The maze is considered being a graph, or
	 * more precisely a tree of possibilities of getting the exit point. So,
	 * going through the tree in depth, any level represents current position of
	 * the hero. The path described by this algorithm is one chosen to be the
	 * shortest path from all paths generated by this algorithm. The main idea
	 * of following the path is simple:
	 * 
	 * <ol>
	 * <li>compute the path using breadth first search algorithm</li>
	 * <li>for each orientation compute the direction to be followed</li>
	 * <li>update the orientation</li>
	 * <li>add the direction to the shortest path stored outside of the
	 * function</li>
	 * </ol>
	 * 
	 * @see mainPackage.Hero#breadthFirstSearch()
	 * @see mainPackage.Hero#getCellDirection(Orientation, Orientation)
	 */
	public void computeShortestPath() {
		ArrayList<Orientation> path = breadthFirstSearch();
		shortestPath = new ArrayList<>();
		Orientation crtOrientation = this.getOrientation();

		for (Orientation o : path) {
			Direction dir = getCellDirection(crtOrientation, o);
			crtOrientation = o;

			shortestPath.add(dir);
		}

	}

	/**
	 * Get next move from shortest path. If there don't exist any path, or the
	 * path is empty, null will be returned.
	 * 
	 * @return return next move
	 */
	public Direction moveShortestPath() {
		try {
			return shortestPath.remove(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * It uses the breadth first search algorithm to determine the shortest path
	 * to the exit portal. It uses a queue of possible cells to be visited (all
	 * possibilities in a current state), a set of visited cells and a hash map
	 * for describing the way of getting to that cell. The algorithm is
	 * presented as follows:
	 * <ul>
	 * <li>Add first cell (from starting point) to the queue. There are no ways
	 * to get to the first cell, so data are null.</li>
	 * <li>While we have cell inside the queue:
	 * <ul>
	 * <li>if last cell is OutPortalCell then construct the path and return the
	 * reverse.</li>
	 * <li>for each neighbor of that cell:
	 * <ul>
	 * <li>if the cell was visited, go to next one</li>
	 * <li>if that cell is not in the queue, add it and generate data to hash
	 * map</li>
	 * </ul>
	 * </li>
	 * <li>mark last cell as being visited</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * In other words, this algorithm generates all possible paths and while
	 * generating, when the first path gets to the final point, then that is the
	 * shortest one.
	 * 
	 * @return the shortest path to the exit portal
	 */
	private ArrayList<Orientation> breadthFirstSearch() {
		Queue<PathCell> lastCells = new LinkedList<>();
		Set<PathCell> visitedCells = new HashSet<>();
		HashMap<PathCell, NeighborCell> cellData = new HashMap<>();

		PathCell start = ((PathCell) maze.getCellAt(position));
		cellData.put(null, null);
		lastCells.add(start);

		while (!lastCells.isEmpty()) {
			PathCell parent = lastCells.remove();

			if (parent instanceof OutPortalCell) {
				ArrayList<Orientation> path = constructPath(parent, cellData);
				Collections.reverse(path);
				return path;
			}

			for (NeighborCell nc : arrayOfNeighbors(
					maze.getCellPosition(parent))) {
				if (visitedCells.contains(nc.cell)) {
					continue;
				}

				if (!lastCells.contains(nc.cell)) {
					cellData.put(nc.cell,
							new NeighborCell(parent, nc.orientation));
					lastCells.add(nc.cell);
				}
			}

			visitedCells.add(parent);
		}

		return null;
	}

	/**
	 * Construct the path from exit portal to enter portal, using the data
	 * stored in a hash map.
	 * 
	 * @param cell
	 *            last cell in the path
	 * @param cellData
	 *            data about getting to each cells form the path
	 * @return an array of orientations
	 */
	private ArrayList<Orientation> constructPath(PathCell cell,
			HashMap<PathCell, NeighborCell> cellData) {
		ArrayList<Orientation> path = new ArrayList<>();

		while (true) {
			NeighborCell nc = cellData.get(cell);
			if (nc != null) {
				cell = nc.cell;
				path.add(nc.getOrientation());
			} else
				break;
		}

		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		switch (orientation) {
			case NORTH:
				return new String("^");
			case WEST:
				return new String("<");
			case SOUTH:
				return new String("v");
			case EAST:
				return new String(">");
			default:
				return null;
		}
	}

	/**
	 * NeighborCell class has data about a cell like a reference to that cell
	 * and the direction/orientation where the cell may be found. It also
	 * implements a comparator to sort the cells by priority and number of times
	 * the cell was visited.
	 * 
	 * @author razvan
	 *
	 */
	class NeighborCell implements Comparator<NeighborCell> {

		/**
		 * The reference to the cell.
		 */
		private PathCell cell;

		/**
		 * The direction where cell may be found, used in generating a path (not
		 * the shortest one).
		 */
		private Direction direction;

		/**
		 * The orientation where the cell may be found, used in generating the
		 * shortest path.
		 */
		private Orientation orientation;

		/**
		 * Create a new NeighborCell.
		 */
		public NeighborCell() {
		}

		/**
		 * Create a new NeighborCell and set the cell and direction.
		 * 
		 * @param cell
		 *            the current cell
		 * @param direction
		 *            the direction where the cell may be found
		 */
		public NeighborCell(PathCell cell, Direction direction) {
			this.cell = cell;
			this.direction = direction;
		}

		/**
		 * Create a new NeighborCell and set the cell and orientation.
		 * 
		 * @param cell
		 *            the current cell
		 * @param orientation
		 *            the orientation where the cell may be found
		 */
		public NeighborCell(PathCell cell, Orientation orientation) {
			this.cell = cell;
			this.orientation = orientation;
		}

		/**
		 * Getter for reference to cell.
		 * 
		 * @return the reference to cell
		 */
		public PathCell getCell() {
			return cell;
		}

		/**
		 * Setter for reference to cell.
		 * 
		 * @param cell
		 *            a reference to a new cell
		 */
		public void setCell(PathCell cell) {
			this.cell = cell;
		}

		/**
		 * Getter for direction.
		 * 
		 * @return the direction where the cell may be found
		 */
		public Direction getDirection() {
			return direction;
		}

		/**
		 * Setter for direction.
		 * 
		 * @param direction
		 *            a new direction where the cell may be found
		 */
		public void setDirection(Direction direction) {
			this.direction = direction;
		}

		/**
		 * Getter for orientation.
		 * 
		 * @return the orientation where the cell may be found
		 */
		public Orientation getOrientation() {
			return orientation;
		}

		/**
		 * Setter for orientation.
		 * 
		 * @param orientation
		 *            a new orientation where the cell may be found
		 */
		public void setOrientation(Orientation orientation) {
			this.orientation = orientation;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(NeighborCell o1, NeighborCell o2) {
			if (o1.cell.getNumVisited() < o2.cell.getNumVisited()) {
				return -1;
			} else if (o1.cell.getNumVisited() > o2.cell.getNumVisited()) {
				return 1;
			} else {
				int compareDirections = o1.direction.compareTo(o2.direction);
				return (compareDirections > 0) ? 1
						: ((compareDirections < 0) ? -1 : 0);
			}
		}

	}
}
