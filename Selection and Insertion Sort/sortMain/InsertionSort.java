package sortMain;
/**
 * Implements Insertion Sort.
 * @author Reed
 *
 */
public class InsertionSort implements Sorter {

	@Override
	public void sort(int[] a) {
		int n =  a.length;
		int[] arr = new int[n + 1];
		arr[0] = Integer.MIN_VALUE; //Sentinel

		//Copy elements over
		for (int i = 0; i < n; i++) {
			arr[i + 1] = a[i];
		}
		//Now, ready for insertion sort.
		n = arr.length;

		for (int i = 1; i < n; i++) {
			int temp = arr[i];
			int j = i - 1;

			while (temp < arr[j]) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = temp;
		}

		//Copy back into a
		for (int i = 0; i < a.length; i++) {
			a[i] = arr[i + 1];
		}
	}
	
	@Override
	public void swap(int[] a, int b, int c){}
}
