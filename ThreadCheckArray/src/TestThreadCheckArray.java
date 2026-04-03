import java.util.Scanner;
import java.util.ArrayList;

/**
 * The {@code TestThreadCheckArray} class serves as the driver for a multi-threaded 
 * application that checks for specific sum solutions within an array.
 * <p>
 * This class handles user input, initializes the shared data structure, 
 * manages the lifecycle of two worker threads, and outputs the resulting 
 * solution set (the "win array") if found.
 * </p>
 * * @author [Your Name/ID]
 * @version 1.0
 */
public class TestThreadCheckArray {

    /**
     * The main entry point of the application.
     * <p>
     * It performs the following steps:
     * <ol>
     * <li>Collects array size and elements from the user.</li>
     * <li>Collects a target sum (constant b).</li>
     * <li>Initializes {@link SharedData} and two worker threads.</li>
     * <li>Starts and joins the threads to ensure synchronization.</li>
     * <li>Prints the solution array (C) indicating which elements contribute to the target sum.</li>
     * </ol>
     * </p>
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Thread thread1, thread2;
            System.out.println("Enter array size");
            int num = input.nextInt();
            ArrayList<Integer> array = new ArrayList<>();
            System.out.println("Enter numbers for array");

            for (int index = 0; index < num; index++)
                array.add(input.nextInt());

            System.out.println("Enter number");
            num = input.nextInt();

            SharedData sd = new SharedData(array, num);

            thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
            thread2 = new Thread(new ThreadCheckArray(sd), "thread2");
            thread1.start();
            thread2.start();
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!sd.getFlag()) {
                System.out.println("Sorry");
                return;
            }
            System.out.println("Solution for b : " + sd.getB() + ",n = " + sd.getArray().size());
            System.out.print("I:    ");
            for (int index = 0; index < sd.getArray().size(); index++)
                System.out.print(index + "    ");
            System.out.println();
            System.out.print("A:    ");
            for (int index : sd.getArray()) {
                System.out.print(index);
                int counter = 5;
                while (true) {
                    index = index / 10;
                    counter--;
                    if (index == 0)
                        break;
                }
                for (int i = 0; i < counter; i++)
                    System.out.print(" ");
            }

            System.out.println();
            System.out.print("C:    ");
            for (boolean index : sd.getWinArray()) {
                if (index)
                    System.out.print("1    ");
                else
                    System.out.print("0    ");
            }
        }
    }
}