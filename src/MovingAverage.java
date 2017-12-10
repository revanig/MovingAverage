
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
/**
 * Can be run on the command line with arguments or user will be prompted for arguments
 * java MovingAverage.java 200 20
 * 
 * @author Revani Govender
 *
 */

public class MovingAverage {
	private static double min = -10.00;
	private static double max = 10.00;
	private static int number = 0;
	private static int windowSize = 0;
	
	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		try {
			number = Integer.parseInt(args[0]);
			windowSize = Integer.parseInt(args[1]); 
		} catch (Exception e) {
			while (number < 2) {
				System.out.print("Array Size (value over 2): ");
				number = reader.nextInt();
			}
			while (windowSize > number | windowSize < 1) {
				System.out.print("Window Size: (positive int less than array size): ");
				windowSize = reader.nextInt();
			}
		}
		reader.close();
		
		double [] array = makeRandomArray(number);
		long startTime = System.nanoTime();
		
		double [] averages = computeMovingAverage(windowSize, array );
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Total computation time: " + totalTime);
		for (double average: averages){
			System.out.printf("%.2f", average);
			System.out.printf("  ");
		}

	}
	
	
	/**
	 * Creates an array of int size and inserts random values
	 * @param size
	 * @return array of random double values
	 */
	public static double[] makeRandomArray(int size) {
		double [] array = new double[size];
		Random r = new Random();
		for (int i = 0; i < size-1; i++) {
			array[i] = min + (max - min) * r.nextDouble();
		}
		return  array;
	}
	
	/**
	 * takes a window size and an array of double values
	 * returns the averages of each successive number, once the window size is reached
	 * then the average is only of the numbers contained in the window
	 * @param windowSize
	 * @param values
	 * @return double array of moving averages
	 */
	public static double[] computeMovingAverage(int windowSize, double[] values) {
		//useing a queue here to remove items from the head and add to the tail easily
		Queue<Double> window = new LinkedList<Double>();
		
		double[] averages = new double[values.length]; 
		double sum  = 0;
		/*
		 * For a greater window size there will be less remove/adding to the queue which will increase the running time
		 * 
		 * For bigger array size, the function will be slower because it will need to iterate through all the values
		 * 
		 * Iteration through the array = O(n)
		 * Simple computations like adding and removing items to the queue (moving window) should be O(1)
		 * 
		 * Overall the function should be O(n) where the biggest time will be spent iterating through the array
		 */
		for (int i = 0; i < values.length; i++) {
			//if we are greater than the window size, then remove the value and subtract from the sum
			if (window.size() >= windowSize) {
				sum -= window.remove();
			} 
			//otherwise always adding to the sum
			window.add(values[i]);
			sum += values[i];
			
			averages[i] = getAverage(sum, window);
		}
		return averages;
	}
	
	/**
	 * Takes a double sum and the window size and returns the average in the window
	 * @param sum
	 * @param window
	 * @return double average
	 */
	public static double getAverage(Double sum, Queue<Double> window) {
		if (window.isEmpty()) {
			return 0; //would be undef otherwise
		}
		
		return sum/window.size();
	}

}
