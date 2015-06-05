package sortMain;

import java.util.Arrays;

/**
 * Container class for testing data. Helps to associate a sorting method with
 * its respective data.
 * @author Reed
 *
 */
public class Data {
	/**
	 * The sorter used to generate the current object.
	 */
	public Sorter sorter;
	/**
	 * The associated data. Size, nanoseconds
	 */
	public double[] data;
	/**
	 * The title of the graph being created with this Data.
	 */
	public String title;

	public Data(Sorter sorter, double[] data, String title) {
		super();
		this.title = title;
		this.sorter = sorter;
		this.data = data;
	}

	@Override
	public String toString() {
		return "Data [sorter=" + sorter + ", data=" + Arrays.toString(data)
				+ "]";
	}
}
