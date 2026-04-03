import java.util.ArrayList;

/**
 * The {@code ThreadCheckArray} class implements the {@link Runnable} interface 
 * to perform a subset sum search using a recursive approach.
 * <p>
 * Each thread works on a specific branch of the search tree to determine if a 
 * combination of integers from the shared list can sum up to the target value {@code b}.
 * It communicates with other threads via a {@link SharedData} object to stop 
 * early if a solution is found elsewhere.
 * </p>
 * * @author [Your Name/ID]
 * @version 1.0
 */
public class ThreadCheckArray implements Runnable 
{
	/** Local flag indicating if this specific thread found a solution. */
	private boolean flag;
	
	/** Local boolean array representing the subset elements that form the sum. */
	private boolean [] winArray;
	
	/** Reference to the shared data object for inter-thread communication. */
	SharedData sd;
	
	/** Local reference to the list of integers. */
	ArrayList<Integer> array;
	
	/** The target sum to search for. */
	int b;
	
	/**
	 * Constructs a new {@code ThreadCheckArray} runner.
	 * <p>
	 * This constructor initializes local references to the array and target sum 
	 * from the {@code SharedData} object using a synchronized block to ensure data integrity.
	 * </p>
	 * * @param sd The shared data object containing the input array and target sum.
	 */
	public ThreadCheckArray(SharedData sd) 
	{
		this.sd = sd;	
		synchronized (sd) 
		{
			array = sd.getArray();
			b = sd.getB();
		}		
		winArray = new boolean[array.size()];
	}
	
	/**
	 * A recursive method that explores subsets of the array to find a match for the target sum.
	 * <p>
	 * The method checks the global flag in {@code SharedData} at each step to see if 
	 * another thread has already found a solution, allowing for early termination.
	 * </p>
	 * * @param n The current index in the array being processed (moving backwards).
	 * @param b The remaining sum needed to reach the target.
	 */
	void rec(int n, int b)
	{
		synchronized (sd) 
		{
			if (sd.getFlag())
				return;
		}	
		if (n == 1)
		{
			if(b == 0 || b == array.get(n-1))
			{
				flag = true;
				synchronized (sd) 
				{
					sd.setFlag(true);
				}			
			}
			if (b == array.get(n-1))
				winArray[n-1] = true;
			return;
		}
		
		rec(n-1, b - array.get(n-1));
		if (flag)
			winArray[n-1] = true;
		synchronized (sd) 
		{
			if (sd.getFlag())
				return;
		}	
		rec(n-1, b);
	}

	/**
	 * The execution logic for the thread.
	 * <p>
	 * It splits the workload based on the thread name:
	 * <ul>
	 * <li><b>thread1:</b> Starts the recursion assuming the last element is included in the sum.</li>
	 * <li><b>thread2:</b> Starts the recursion assuming the last element is excluded from the sum.</li>
	 * </ul>
	 * If a solution is found, it updates the {@code winArray} in the shared data object.
	 * </p>
	 */
	public void run() {
		if (array.size() != 1)
			if (Thread.currentThread().getName().equals("thread1"))
				rec(array.size()-1, b - array.get(array.size()-1));
			else 
				rec(array.size()-1, b);
		if (array.size() == 1)
			if (b == array.get(0) && !flag)
			{
				winArray[0] = true;
				flag = true;  
				synchronized (sd) 
				{
					sd.setFlag(true);
				}
			}
		if (flag)
		{
			if (Thread.currentThread().getName().equals("thread1"))
				winArray[array.size() - 1] = true;
			synchronized (sd) 
			{
				sd.setWinArray(winArray); 
			}	
		}
	}
}