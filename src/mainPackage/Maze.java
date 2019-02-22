package mainPackage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Maze class represents a board of cells that have some properties like wall
 * cells or free cells. It has a two-dimensional array of cells and some data
 * about the height and width of the maze and also the position of enter portal.
 * 
 * @author razvan
 *
 */
public class Maze {

	/**
	 * The height of the maze.
	 */
	private int height;

	/**
	 * The width of the maze.
	 */
	private int width;

	/**
	 * The maze.
	 */
	private ArrayList<ArrayList<Cell>> maze = null;

	/**
	 * Starting point of the hero. It is the position of InPortalCell.
	 */
	private Position startPosition = null;

	/**
	 * Create a new Maze.
	 */
	public Maze() {
	}

	/**
	 * Create a new maze from input file.
	 * 
	 * @param inputFile
	 *            the name of the input file
	 * 
	 * @throws NullPointerException if maze cannot be generated
	 */
	public Maze(String inputFile) throws NullPointerException {
		getMazeFromFile(inputFile);
		if (maze == null) {
			throw new NullPointerException();
		}
	}

	/**
	 * Get the content of the file and initialize the maze array. It uses a
	 * factory to "guess" the type of the cells when building the maze array.
	 * Starting position is also set to the position of InPortalCell cell.
	 * 
	 * @param inputFile
	 *            the name of the input file
	 * 
	 * @see mainPackage.CellFactory#createCell
	 */
	public void getMazeFromFile(String inputFile) {

		try {
			FileInputStream fileStream = new FileInputStream(inputFile);
			InputStreamReader streamReader = new InputStreamReader(fileStream);
			BufferedReader fileReader = new BufferedReader(streamReader);
			CellFactory cellFactory = CellFactory.getInstance();

			String firstLine = fileReader.readLine();
			String[] mazeGridData = firstLine.trim().split(" ");

			height = Integer.parseInt(mazeGridData[0]);
			width = Integer.parseInt(mazeGridData[1]);

			maze = new ArrayList<>(height);

			for (int i = 0; i < height; i++) {
				ArrayList<Cell> mazeLine = new ArrayList<>(width);

				String fileLine = fileReader.readLine();
				String[] mazeDataPerLine;

				mazeDataPerLine = fileLine.trim().split("");

				for (int j = 0; j < width; j++) {
					if (mazeDataPerLine[j].equals("I")
							&& startPosition == null) {
						startPosition = new Position(j, i);
					}
					mazeLine.add(cellFactory.createCell(mazeDataPerLine[j]));
				}

				maze.add(mazeLine);
			}

			fileReader.close();

		} catch (IOException e) {
			System.out.println(
				"Error while generating the maze: No such file or directory");
		}
	}

	/**
	 * Getter for maze.
	 * 
	 * @return the maze
	 */
	public ArrayList<ArrayList<Cell>> getMaze() {
		return maze;
	}

	/**
	 * Setter for maze.
	 * 
	 * @param maze
	 *            new maze
	 */
	public void setMaze(ArrayList<ArrayList<Cell>> maze) {
		this.maze = maze;
	}

	/**
	 * Getter for height.
	 * 
	 * @return the height of the maze
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Setter for height.
	 * 
	 * @param height
	 *            new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Getter for width.
	 * 
	 * @return the width of the maze
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Setter for width.
	 * 
	 * @param width
	 *            new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Getter for start position.
	 * 
	 * @return starting position of the hero
	 */
	public Position getStartPosition() {
		return startPosition;
	}

	/**
	 * Setter for start position.
	 * 
	 * @param startPosition
	 *            new starting position
	 */
	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * Get cell at position specified by x and y coordinates.
	 * 
	 * @param x
	 *            coordinate x
	 * @param y
	 *            coordinate y
	 * @return a reference to cell located at (x, y), or null if the cell is
	 *         outside of the maze
	 */
	public Cell getCellAt(int x, int y) {
		try {
			return maze.get(y).get(x);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Get cell at position specified by a position.
	 * 
	 * @param pos
	 *            the position of the cell in maze
	 * @return a reference to cell located at position specified, or null if the
	 *         cell is outside of the maze
	 */
	public Cell getCellAt(Position pos) {
		return getCellAt(pos.getX(), pos.getY());
	}

	/**
	 * Get position of a cell in the maze.
	 * 
	 * @param cell
	 *            a reference to a cell
	 * @return position of the cell in maze if exists, or null otherwise.
	 */
	public Position getCellPosition(Cell cell) {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (getCellAt(j, i) == cell) {
					return new Position(j, i);
				}
			}
		}

		return null;
	}
}
