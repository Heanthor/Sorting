package sortMain;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 * 
 * Performs the sorting and timing calculations used in testing. Launches the thread
 * responsible for creating graphs, and launches the GUI.
 * @author Reed
 * 
 */
public class Sort implements Runnable {
	private final boolean DEBUG_UI = false; //Display UI or not?
	private final boolean DEBUG_THREAD = false; //Create graphs in new thread?
	private final boolean DEBUG_GRAPHS = false; //Use smaller data sizes to debug graphs?

	private static final Object o = new Object(); //For synchronization
	private boolean ready = false; //Notifier variable
	private Window currentWindow; //Window the UI is drawn in
	private JProgressBar progress; //Progress bar object from Window

	/**
	 * Holds experiment data for exporting to grapher. Is consumed by helper threads.
	 */
	private Data[] experimentData = new Data[5];

	/**
	 * Program start point. Creates sorting experiment and thread to collect data.
	 * @param args
	 */
	public static void main(String[] args) {
		Sort s = new Sort();
		Thread t = new Thread(s, "Grapher");
		t.start();
		s.init();
	}

	/**
	 * Executes the program. No threading involved here.
	 */
	public void init() {
		if (!DEBUG_UI) {
			/* Start the UI. It makes no performance difference since 
			 * all timing is done outside of the UI classes. */
			currentWindow = new Window();
			progress = currentWindow.getProgressBar();
			System.setOut(new PrintStream(new TextAreaOutputStream(currentWindow.getTextArea())));
		}

		System.out.println("Hello! Example output can be found in readme.txt.\n"
				+ "Program has started. Generating dataset of size 10.");

		long start = System.nanoTime();
		generateDataset(10);
		long end = System.nanoTime();
		long total = end - start;

		System.out.println("Array generated in time " + total / 1000 + " microseconds.\n");
		System.out.println("That is pretty fast. Let's generate 10 arrays of size\n1,000,000.\n");

		long avgTime = 0, totalTime = 0;

		start = System.nanoTime();

		int[][] data = new int[10][];
		for (int i = 0; i < 10; i++) {
			data[i] = generateDataset(1000000);
		}

		totalTime = System.nanoTime() - start;

		avgTime = totalTime / 100;

		System.out.println("Arrays created in total time " + totalTime / 1000000 + " miliseconds,"
				+ " average time\n" + avgTime / 1000000 + " ms per array.\n");

		System.out.println("Now testing Insertion Sort. We will test 10 iterations of\neach array size.\n");

		/*
		 * Test size 1,000, 10,000, 100,000, 1m, set size 4.
		 */
		int _1, _2, _3, _4;

		if (DEBUG_GRAPHS) { //Smaller datasets
			_1 = 100;
			_2 = 10000;
			_3 = 100000;
			_4 = 200000;
		} else {
			_1 = 100;
			_2 = 10000;
			_3 = 100000;
			_4 = 1000000;
		}

		/* Insertion sort average */
		
		int[][] set100 = generateDatasetSet(10, _1, false);
		updateProgressBar("Set 10,000", 25);
		int[][] set10k = generateDatasetSet(10, _2, false);
		updateProgressBar("Set 100,000", 50);
		int[][] set100k = generateDatasetSet(10, _3, false);
		updateProgressBar("Set 1,000,000", 75);
		int[][] set1m = generateDatasetSet(10, _4, false);
		updateProgressBar(100);

		int[][][] sets = {set100, set10k, set100k, set1m};

		System.out.println("Note: times printed will be for individual sorts, averaged\n"
				+ "over all iterations.\n");

		String header = "Array Size       Time(ms)       Time(sec)";
		System.out.println(header);

		sortLoop(new InsertionSort(), sets, 10, "Insertion Sort Average Case");

		/* Selection Sort average */

		System.out.println("\nNow we will test Selection Sort using the same methods.\n");

		set100 = generateDatasetSet(10, _1, false);
		updateProgressBar("Set 10,000", 25);
		set10k = generateDatasetSet(10, _2, false);
		updateProgressBar("Set 100,000", 50);
		set100k = generateDatasetSet(10, _3, false);
		updateProgressBar("Set 1,000,000", 75);
		set1m = generateDatasetSet(10, _4, false);
		updateProgressBar(100);

		int[][][] sets3 = {set100, set10k, set100k, set1m};
		System.out.println(header);

		sortLoop(new SelectionSort(), sets3, 10, "Selection Sort Average Case");

		/* Insertion Sort worst */
		
		System.out.println("\nNow testing insertion sort in the worst case, in "
				+ "which all\nelements are in reverse order.\n");
		System.out.println(header);

		//Since worst case is reverse order, only one permutation is necessary.
		set100 = generateDatasetSet(1, _1, true);
		updateProgressBar("Set 10,000", 25);
		set10k = generateDatasetSet(1, _2, true);
		updateProgressBar("Set 100,000", 50);
		set100k = generateDatasetSet(1, _3, true);
		updateProgressBar("Set 1,000,000", 75);
		set1m = generateDatasetSet(1, _4, true);
		updateProgressBar(100);


		int[][][] sets2 = {set100, set10k, set100k, set1m};

		sortLoop(new InsertionSort(), sets2, 1, "Insertion Sort Worst Case");

		/* Selection Sort worst */
		
		System.out.println("\nSelection sort worst case, despite the fact that selection sort\n"
				+ "should run in approximately the same time for any input.\n");
		System.out.println(header);

		set100 = generateDatasetSet(1, _1, true);
		updateProgressBar("Set 10,000", 25);
		set10k = generateDatasetSet(1, _2, true);
		updateProgressBar("Set 100,000", 50);
		set100k = generateDatasetSet(1, _3, true);
		updateProgressBar("Set 1,000,000", 75);
		set1m = generateDatasetSet(1, _4, true);
		updateProgressBar(100);

		int[][][] sets4 = {set100, set10k, set100k, set1m};

		sortLoop(new SelectionSort(), sets4, 1, "Selection Sort Reversed Datasets");

		System.out.println("\nDone!\n");
		currentWindow.getTextArea2().setText("As you can see by either the graphs or the data itself, \n"
				+ "both Selection and Insertion sort demonstrate exponential time sorting.\n"
				+ "However, while the two algorithms were very similar on small datasets, \n"
				+ "Insertion sort pulled ahead dramatically in large datasets.");
	}

	/**
	 * Updates the progress of the main progress bar object.
	 * @param percent The progress, as a percent of 100.
	 */
	private void updateProgressBar(final int percent) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				progress.setString(percent + "%");
				progress.setValue(percent);
			}	
		});
	}
	/**
	 * Updates the progress of the main progress bar object.
	 * @param text - The text to be displayed on the progress bar, prior to the percentage.
	 * @param percent - The progress, as a percent of 100.
	 * 
	 */
	private void updateProgressBar(final String text, final int percent) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				progress.setString(text + " - " + percent + "%");
				progress.setValue(percent);
			}	
		});
	}

	/**
	 * Sorting loop for testing. Prints times in appropriate format.
	 * @param s - The Sorter object used to perform the sorting.
	 * @param sets - Sets to sort
	 * @param numIterations - Number of permutations to perform, and average
	 */
	private void sortLoop(Sorter s, int[][][] sets, int numIterations, String title) {
		synchronized(o) {
			Data[] d = new Data[4]; //Hold results

			for (int i = 0; i < 4; i++) { //Size loop
				long total2 = 0;

				for (int j = 0; j < numIterations; j++) { //Sorting loop
					long start2 = System.nanoTime();
					s.sort(sets[i][j]);

					total2 += (System.nanoTime() - start2);
					/* Converts progress into a percentage
					 * Note this should have no effect on the time calculations, since
					 * time is taken before this method is called.
					 */
					updateProgressBar(j * 100 / numIterations); 
				}

				updateProgressBar(0);
				total2 /= numIterations; //Average
				int size = -1;

				//Helps to print the correct size in the for loop
				switch (i) {
				case 0:
					size = 100;
					break;
				case 1:
					size = 10000;
					break;
				case 2:
					size = 100000;
					break;
				case 3:
					size = 1000000;
					break;
				}

				String format ="%9s  %14s %15s\n"; //Format string for number output
				double totalms = (double)total2 / 1000000;
				double totals = totalms / 1000;

				System.out.format(format, NumberFormat.getNumberInstance(
						Locale.US).format(size), NumberFormat.getNumberInstance(
								Locale.US).format(totalms), NumberFormat.getNumberInstance(Locale.US).format(totals));

				//Store data
				double[] numbers = {size, totalms};
				d[i] = new Data(s, numbers, title);
			}
			experimentData = d;
			ready = true;
			o.notifyAll(); //Indicate value is ready to consume
		}
	}

	/**
	 * Generates a set of random datasets for use in sorting.
	 * @param numSets - The number of random sets to generate
	 * @param size - The size of each set.
	 * @param reversed - Generate random data if false, or a reversed list if true
	 * @return The double array of sets.
	 */
	private int[][] generateDatasetSet(int numSets, int size, boolean reversed) {
		int[][] set = new int[numSets][];
		if (!reversed) {
			for (int i = 0; i < numSets; i++) {
				set[i] = generateDataset(size);
			}

		} else {
			for (int i = 0; i < numSets; i++) {
				set[i] = generateReversedList(size);
			}
		}
		return set;
	}

	/**
	 * Generates a random dataset of numbers 1 to range, inclusive. Runs in 
	 * O(n) time, due to Collections.shuffle.
	 * @param range - Size of the dataset (and the range of the numbers).
	 * @return The generated array.
	 */
	private int[] generateDataset(int range) {
		int[] tmp = new int[range];
		ArrayList<Integer> lst = new ArrayList<Integer>();

		//Generate range of numbers
		for(int i = 0; i < range; i++) {
			lst.add(i + 1);
		}

		Collections.shuffle(lst); //Uses standard PRNG

		//Convert back into an int[] since it doesn't like Integer
		for(int i = 0; i < range; i++) {
			tmp[i] = lst.get(i);
		}

		return tmp;
	}

	/**
	 * Generates a list whose elements are in reverse order. Useful for worst case.
	 * @param size - Size of the list to create
	 * @return The generated list.
	 */
	private int[] generateReversedList(int size) {
		int[] tmp = new int[size];

		for(int i = size; i > 0; i--) {
			tmp[size - i] = i;
		}

		return tmp;
	}
	/*
	 * Fields for graphmaking.
	 */
	int count = 0;
	MyGraph g = new MyGraph(2);

	@Override
	public void run() {
		if (!DEBUG_THREAD) {
			while (true) {
				/* Returns current experiment data for graph creation.
				 * Also blocks the Grapher thread on this line. */
				/*MyGraph g = new MyGraph(consume()); 
				currentWindow.getGraphsPanel().add(g.getChart()); */
				int dataPerChart = 2;

				g.addData(consume(), count++);

				if (count == dataPerChart) {
					currentWindow.getGraphsPanel().add(g.getChart());
					count = 0;
				}
			}
		}
	}

	/**
	 * Called by Grapher thread. Consumes data currently stored in experimentData, setting
	 * ready flag to false and returning the Data[] generated by sortLoop.
	 * @return Most recently generated Data[]
	 */
	public Data[] consume() {
		synchronized(o) {
			while (!ready) {
				//System.out.println("wait");
				try {
					o.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//System.out.println("Consumed");
			Data[] d = experimentData;
			ready = false;
			return d;
		}
	}

	/**
	 * Prints an int array. For debugging mostly
	 * @param a - Array to be printed.
	 */
	@SuppressWarnings("unused")
	private static void printArray(Object[] a) {
		String s = "[";

		for (int i = 0; i < a.length; i++) {
			s+= "" + a[i];

			if (i != a.length - 1) {
				s+= ", ";
			}
		}

		s += "]";

		System.out.println(s);
	}
}