package mainPackage;


/**
 * MainClass represents the class where entry point of the program (main
 * function) can be found.
 * 
 * @author razvan
 *
 */
public class MainClass {
	/**
	 * The entry point of the program. It runs the task.
	 * 
	 * @param args
	 *            list of arguments, first argument is task number, second one
	 *            is input file name and third one is output file name.
	 */
	public static void main(String[] args) {
		TaskRunner taskRunner = TaskRunner.getInstance(args);
		taskRunner.run();
	}

}
