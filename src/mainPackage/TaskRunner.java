package mainPackage;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * TaskRunner class is a singleton class that can run tasks.
 * 
 * @author razvan
 *
 */
public class TaskRunner {

	/**
	 * Instance of TaskRunner, hence it is a singleton
	 */
	private static TaskRunner instance = null;

	/**
	 * Arguments of the program
	 */
	private String[] args;

	/**
	 * Create a new instance of TaskRunner and set args.
	 * 
	 * @param args
	 *            arguments from main method.
	 */
	private TaskRunner(String[] args) {
		this.args = args;
	}

	/**
	 * Get an existent instance or create one if needed.
	 * 
	 * @param args
	 *            arguments of main function
	 * @return an instance of TaskRunner
	 */
	public static TaskRunner getInstance(String[] args) {
		if (instance == null) {
			instance = new TaskRunner(args);
		}

		return instance;
	}

	/**
	 * Move hero on a certain direction and put the new coordinates in a file.
	 * 
	 * @param hero
	 *            the hero, here Dubluve
	 * @param maze
	 *            the maze generated
	 * @param direction
	 *            next direction where hero should go
	 * 
	 * @return if we have a win or keep finding the exit portal
	 * 
	 * @throws IOException
	 *             if there are problems while writing on file
	 */
	private boolean moveHeroToDirection(Hero hero, Maze maze,
			Direction direction) throws IOException {
		hero.move(direction);

		if (checkWin(maze, hero)) {
			return true;
		}

		return false;
	}

	/**
	 * Check if the hero has reached the exit portal.
	 * 
	 * @param m
	 *            the maze
	 * @param h
	 *            the hero
	 * @return whether hero has reached the exit portal or not
	 */
	private boolean checkWin(Maze m, Hero h) {
		if (m.getCellAt(h.getPosition()) instanceof OutPortalCell) {
			return true;
		}

		return false;
	}

	/**
	 * Run a task given as parameter. It move the hero using AI or computing the
	 * shortest path to the finish. If the hero cannot reach the finish, a
	 * message will be printed. It returns a list of cells visited by the hero.
	 * 
	 * @param task
	 *            the number of task
	 * @param m
	 *            the maze
	 * @param h
	 *            the hero
	 * 
	 * @return a list of cells visited by the hero
	 * 
	 * @throws IOException
	 *             if there are problems while writing to file
	 */
	private ArrayList<String> runTask(int task, Maze m, Hero h)
			throws IOException {

		Direction dir;
		ArrayList<String> result = new ArrayList<String>();

		switch (task) {
			case 1:
				while ((dir = h.aiMove()) != null) {
					result.add(h.getPosition().getY() + " "
							+ h.getPosition().getX() + "\n");
					if (moveHeroToDirection(h, m, dir)) {
						break;
					}
				}
				break;

			case 2:
				h.computeShortestPath();

				while ((dir = h.moveShortestPath()) != null) {
					result.add(h.getPosition().getY() + " "
							+ h.getPosition().getX() + "\n");
					if (moveHeroToDirection(h, m, dir)) {
						break;
					}
				}
				break;

			default:
				System.out.println("Invalid task!\n");
				return null;
		}

		if (!checkWin(m, h)) {
			System.out.println("Fail to win!\n");
			return null;
		}

		result.add(
				h.getPosition().getY() + " " + h.getPosition().getX() + "\n");

		return result;
	}

	/**
	 * Prints the output given as an array of strings, each string per line, in
	 * a given file.
	 * 
	 * @param string
	 *            an array of strings
	 * @param fileWriter
	 *            file buffer
	 * @throws IOException
	 *             when there are problems while writing in file
	 */
	private void printArrayToFile(ArrayList<String> string,
			BufferedWriter fileWriter) throws IOException {

		fileWriter.write(string.size() + "\n");
		for (String s : string) {
			fileWriter.write(s);
		}
	}

	/**
	 * This method opens the output file stream, creates a hero and a maze from
	 * a given file from arguments list and prints the path of the hero up to
	 * exit portal.
	 */
	public void run() {
		FileOutputStream fileStream = null;
		int task = 0;

		try {
			fileStream = new FileOutputStream(args[2]);
			task = Integer.parseInt(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println("Error while opening output file!");
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Arguments are missing!");
			return;
		} catch (NumberFormatException e) {
			System.out.println("Invalid number format for task number!");
			return;
		}

		OutputStreamWriter streamReader = new OutputStreamWriter(fileStream);
		BufferedWriter fileWriter = new BufferedWriter(streamReader);

		try {
			Maze m = new Maze(args[1]);
			Hero h = new Hero(m);

			printArrayToFile(runTask(task, m, h), fileWriter);

			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Error while writing to file!");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Maze couldn't be generated. Check your files!");
			return;
		}

	}
}