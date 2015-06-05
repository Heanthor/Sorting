package sortMain;

import java.util.*;

import javax.swing.JPanel;

import com.xeiam.xchart.*;
import com.xeiam.xchart.StyleManager.LegendPosition;

/**
 * Creates an XChart graph using a Data object. Plots the data on a logarithmic x-axis,
 * and while it is not smoothed, it shows exponential growth in time as n increases.
 * @author Reed
 *
 */
public class MyGraph {
	/**
	 * Number of data sets per chart
	 */
	private int dataPerChart;
	private Data[][] dataSet;
	private Chart chart;

	public MyGraph(int dataPerChart) {
		dataSet = new Data[2][];
		this.dataPerChart = dataPerChart;

	}

	public MyGraph(Data[] consume) {
		dataSet = new Data[2][];
		dataSet[0] = consume;
		generateGraph();
	}

	public void addData(Data[] data, int index) {
		dataSet[index] = data;

		if (index == dataPerChart - 1) {
			generateGraph();
		}
	}

	/**
	 * Creates the Chart object to be graphed
	 */
	public void generateGraph() { 
		// Create Chart
		String title = dataSet[0][0].title;

		title = title.substring(title.indexOf(" ")).trim();
		title = title.substring(title.indexOf(" ")).trim();

		chart = new ChartBuilder().width(800).height(600).title(title).build();

		// Customize Chart
		chart.getStyleManager().setChartTitleVisible(true);
		chart.setXAxisTitle("Data Size");
		chart.setYAxisTitle("Time (miliseconds)");
		chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyleManager().setXAxisLogarithmic(true);

		for (int i = 0; i < dataPerChart; i++) {
			// generates data lists
			List<Double> xData = new ArrayList<Double>();
			List<Double> yData = new ArrayList<Double>();

			for (int j = 0; j < dataSet[0].length; j++) {
				xData.add(dataSet[i][j].data[0]);
				yData.add(dataSet[i][j].data[1]);
			}

			// Series
			chart.addSeries(dataSet[i][0].title, xData, yData);

		}
	}

	/**
	 * Getter for drawn chart.
	 * @return Returns the current Chart object, painted in a JPanel by XChartPanel
	 */
	public JPanel getChart() {
		XChartPanel p = new XChartPanel(chart);
		p.setBounds(0, 0, 300, 200);
		return p;
	}
}
