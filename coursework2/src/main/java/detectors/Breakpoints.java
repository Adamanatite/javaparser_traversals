/*
 * Name: Adam Fairlie
 * Registration Number: 2461352F
 * */
package detectors;

/**
 * Collector for observed behaviour from visitors to be printed
 * 
 * Student solution to OOSE2 Assessed Exercise 2
 * 
 * @author Adam Fairlie <2461352f@student.gla.ac.uk>
 */
public class Breakpoints {
	
	/** The Name of the class */
	private String className;
	/** The Name of the method */
	private String methodName;
	/** The first line */
	private int startLine;
	/** The last line */
	private int endLine;
	
	/**
	 * Creates a new Breakpoint with the given information
	 * 
	 * @param className The name of the class
	 * @param methodName The name of the method
	 * @param startLine The start line of the node
	 * @param endLine The end line of the node
	 */
	public Breakpoints(String className, String methodName, int startLine, int endLine) {
		this.className = className;
		this.methodName = methodName;
		this.startLine = startLine;
		this.endLine = endLine;
	}

	/**
	 * Returns a string representation of the breakpoint
	 * 
	 * @return The string representation
	 */
	@Override
	public String toString() {
		return "className=" + className + ",methodName=" + methodName + ",startline=" + startLine + ",endline=" + endLine;
	}
	
}