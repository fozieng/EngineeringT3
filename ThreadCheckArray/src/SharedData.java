import java.util.ArrayList;

/**
 * The {@code SharedData} class represents a shared resource used in a multi-threaded
 * environment to store an array of integers, a target sum, and the results of a 
 * subset search.
 * <p>
 * It provides synchronized-style access (via getters and setters) to the input data 
 * and the state flags that indicate whether a solution has been found.
 * </p>
 * * @author [Your Name/ID]
 * @version 1.0
 */
public class SharedData 
{
	/** The list of integers to be processed. */
	private ArrayList<Integer> array;
	
	/** A boolean array where each index corresponds to whether the element in {@code array} is part of the solution. */
	private boolean [] winArray;
	
	/** A flag indicating if a valid subset sum solution has been found by any thread. */
	private boolean flag;
	
	/** The target sum constant that the threads are searching for. */
	private final int b;
	
	/**
	 * Constructs a new {@code SharedData} object with the specified array and target sum.
	 * * @param array The {@code ArrayList} of integers to search through.
	 * @param b     The target sum to identify.
	 */
	public SharedData(ArrayList<Integer> array, int b) {
		
		this.array = array;
		this.b = b;
	}

	/**
	 * Returns the boolean array representing the solution set.
	 * * @return A boolean array where {@code true} indicates an included element.
	 */
	public boolean[] getWinArray() 
	{
		return winArray;
	}

	/**
	 * Sets the solution array once a successful subset is found.
	 * * @param winArray The boolean array identifying the solution elements.
	 */
	public void setWinArray(boolean [] winArray) 
	{
		this.winArray = winArray;
	}

	/**
	 * Returns the original list of integers.
	 * * @return The {@code ArrayList} of integers.
	 */
	public ArrayList<Integer> getArray()  
	{
		return array;
	}

	/**
	 * Returns the target sum constant.
	 * * @return The value of {@code b}.
	 */
	public int getB() 
	{
		return b; 
	}

	/**
	 * Returns the status of the search.
	 * * @return {@code true} if a solution was found; {@code false} otherwise.
	 */
	public boolean getFlag() 
	{
		return flag;
	}

	/**
	 * Sets the search status flag.
	 * * @param flag {@code true} if a solution is found, {@code false} to reset.
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}