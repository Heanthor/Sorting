package sortMain;
/**
 * Implements Selection Sort.
 * @author Reed
 *
 */
public class SelectionSort implements Sorter {

	@Override
	public void sort(int[] a) { //This algorithm is very simple
		int len = a.length;

		for (int i = len - 1; i > 0; i--) {
			int k = 1;

			for (int j = 0; j <= i; j++) {
				if (a[j] > a[k]) {
					k = j;
				}
			}

			swap(a, i, k);
		}
	}

	@Override
	public void swap(int[] a, int b, int c) {
		int tmp = a[b];

		a[b] = a[c];
		a[c] = tmp;
	}
}
