package mainPackage;

/**
 * Direction enum represents the direction of the next cell where the hero will
 * go, or the direction where it is a cell according to the position of the
 * hero. Possible directions are: RIGHT, FRONT, LEFT and BACK. The main
 * difference between Direction enum end Orientation enum is that Direction is
 * relative to something and Orientation is an absolute value (NORTH represents
 * up direction of the screen).
 */
public enum Direction {
	RIGHT, FRONT, LEFT, BACK;
}
