package sortMain;
/**
 * Sorting algorithm interface.
 * @author Reed
 *
 */
public interface Sorter {
	/**
	 * Sorting method as in class
	 * @param a - Array to be sorted
	 * @return The sorted array
	 */
	public void sort(int[] a);
	/**
	 * Swaps two elements in array a.
	 * @param a - Array to use.
	 * @param b - First index
	 * @param c - Second index.
	 */
	public void swap(int[] a, int b, int c);
}
