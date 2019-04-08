import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.Color;

/**
 * Please note that 2 graphs will appear, since insert sort took so long i had to standardize it so that other lines in the chart would appear
 * @author i77ki
 *
 */

@SuppressWarnings("unused")
public class SortingComparison {

	public static void main(String[] args) {

		ArrayList<ArrayList<Long>> outPut = new ArrayList<ArrayList<Long>>();
		StringBuilder sb = new StringBuilder();
		
		System.out.println("Sorting.....\n");	

		outPut.add(0,iSOut(randArr(100000)));
		outPut.add(1,mSOut(randArr(100000)));
		outPut.add(2,qSOut(randArr(100000)));
		outPut.add(3,hSOut(randArr(100000)));

		createTable(outPut);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MultipleLinesChart(outPut).setVisible(true);
				outPut.set(0, standardize("InsertSort"));
				new MultipleLinesChart(outPut).setVisible(true);

			}
		});

	}
	public static ArrayList<Long> standardize(String alogrithm){
		ArrayList<Long> blank = new ArrayList<Long>();
		blank.add((long) 100);
		blank.add((long) 100);
		blank.add((long) 100);
		blank.add((long) 100);
		blank.add((long) 100);
		blank.add((long) 100);
		blank.add((long) 100);
		blank.add((long) 100);
		blank.add((long) 100);
		blank.add((long) 100);
		return blank;
	}
	
	public static void createTable(ArrayList<ArrayList<Long>> outPut){
		StringBuilder sb = new StringBuilder();
		sb.append("         |10k|20k|30k|40k|50k|60k|70k|80k|90k|100k|\n");
		sb.append("Insertion|" + outPut.get(0).toString() + "|\n");
		sb.append("Merge    |" + outPut.get(1).toString() + "|\n");
		sb.append("Quik     |" + outPut.get(2).toString() + "|\n");
		sb.append("Heap     |" + outPut.get(3).toString() + "|\n");
		System.out.print(sb.toString());
	}


	public static ArrayList<Long> iSOut(Integer[] arr){ //handles the insertion sorting algorithm
		ArrayList<Long> iSOut = new ArrayList<Long>();
		MyInsertionSort iS = new MyInsertionSort();
		long startTime = System.currentTimeMillis();

		for (int i=10000;i<=100000;i+=10000){				
			iSOut.add((iS.sort(randArr(i)))-startTime);	
		}
		return iSOut;
	}

	
	public static ArrayList<Long> mSOut(Integer[] arr){ //handles the merge sort algorithm
		ArrayList<Long> mSOut = new ArrayList<Long>();
		MergerSort mS = new MergerSort();
		long startTime = System.currentTimeMillis();

		for (int i=10000;i<=100000;i+=10000){				
			mSOut.add((mS.mergeSort(randArr(i)))-startTime);
		}
		return mSOut;
	}

	public static ArrayList<Long> qSOut(Integer[] arr){ //handle the quick sort algorithm 
		ArrayList<Long> qSOut = new ArrayList<Long>();
		QuickSort qS = new QuickSort();
		long startTime = System.currentTimeMillis();

		for (int i=10000;i<=100000;i+=10000){				
			qSOut.add((qS.sort(randArr(i),0, i-1))-startTime);
		}
		return qSOut;
	}

	
	public static ArrayList<Long> hSOut(Integer[] arr){ //handles the heap sort algorithm
		ArrayList<Long> hSOut = new ArrayList<Long>();
		HeapSort hS = new HeapSort();
		long startTime = System.currentTimeMillis();

		for (int i=10000;i<=100000;i+=10000){				
			hSOut.add((hS.sort(randArr(i)))-startTime);
		}
		return hSOut;
	}

	
	public static Integer[] randArr(int size){
		Random r = new Random();
		Integer[] arr = new Integer[size];
		for (int i = 0; i < size; i++) {
			arr[i] =r.nextInt(size) +1;
		}
		return arr;
	}

	//http://www.java2novice.com/java-sorting-algorithms/insertion-sort/
	public static class MyInsertionSort {


		public long sort(Integer[] input){

			int temp;
			for (int i = 1; i < input.length; i++) {
				for(int j = i ; j > 0 ; j--){
					if(input[j] < input[j-1]){
						temp = input[j];
						input[j] = input[j-1];
						input[j-1] = temp;
					}
				}
			}
			return System.currentTimeMillis();
		}
	}

	
	//https://www.cs.cmu.edu/~adamchik/15-121/lectures/Sorting%20Algorithms/code/MergeSort.java
	public static class MergerSort{


		public long mergeSort(Integer[] arr)
		{
			Integer[] tmp = new Integer[arr.length];
			mergeSort(arr, tmp,  0,  arr.length - 1);

			return System.currentTimeMillis();
		}


		private  void mergeSort(Integer[] arr, Integer[] tmp, int left, int right)
		{
			if( left < right )
			{
				int center = (left + right) / 2;
				mergeSort(arr, tmp, left, center);
				mergeSort(arr, tmp, center + 1, right);
				merge(arr, tmp, left, center + 1, right);
			}
		}


		private  void merge(Integer[] arr, Integer[] tmp, int left, int right, int rightEnd )
		{
			int leftEnd = right - 1;
			int k = left;
			int num = rightEnd - left + 1;

			while(left <= leftEnd && right <= rightEnd)
				if(arr[left].compareTo(arr[right]) <= 0)
					tmp[k++] = arr[left++];
				else
					tmp[k++] = arr[right++];

			while(left <= leftEnd)    // Copy rest of first half
			tmp[k++] = arr[left++];

			while(right <= rightEnd)  // Copy rest of right half
				tmp[k++] = arr[right++];

			// Copy tmp back
			for(int i = 0; i < num; i++, rightEnd--)
				arr[rightEnd] = tmp[rightEnd];
		}
	}

	// Java program for implementation of Heap Sort
	
	//https://www.geeksforgeeks.org/heap-sort/
	public static class HeapSort

	{
		public long sort(Integer[] arr)
		{
			int n = arr.length;

			// Build heap (rearrange array)
			for (int i = n / 2 - 1; i >= 0; i--)
				heapify(arr, n, i);

			// One by one extract an element from heap
			for (int i=n-1; i>=0; i--)
			{
				// Move current root to end
				int temp = arr[0];
				arr[0] = arr[i];
				arr[i] = temp;

				// call max heapify on the reduced heap
				heapify(arr, i, 0);
			}
			return System.currentTimeMillis();
		}

		// To heapify a subtree rooted with node i which is
		// an index in arr[]. n is size of heap
		void heapify(Integer[] arr, int n, int i)
		{
			int largest = i;  // Initialize largest as root
			int l = 2*i + 1;  // left = 2*i + 1
			int r = 2*i + 2;  // right = 2*i + 2

			// If left child is larger than root
			if (l < n && arr[l] > arr[largest])
				largest = l;

			// If right child is larger than largest so far
			if (r < n && arr[r] > arr[largest])
				largest = r;

			// If largest is not root
			if (largest != i)
			{
				int swap = arr[i];
				arr[i] = arr[largest];
				arr[largest] = swap;

				// Recursively heapify the affected sub-tree
				heapify(arr, n, largest);
			}
		}

		/* A utility function to print array of size n */
		static void printArray(int arr[])
		{
			int n = arr.length;
			for (int i=0; i<n; ++i)
				System.out.print(arr[i]+" ");
			System.out.println();
		}

	}
	
	//https://www.geeksforgeeks.org/quick-sort/

	// Java program for implementation of QuickSort
	//https://www.geeksforgeeks.org/quick-sort/
	public static class QuickSort
	{
		/* This function takes last element as pivot,
	       places the pivot element at its correct
	       position in sorted array, and places all
	       smaller (smaller than pivot) to left of
	       pivot and all greater elements to right
	       of pivot */
		int partition(Integer[] arr, int low, int high)
		{
			int pivot = arr[high]; 
			int i = (low-1); // index of smaller element
			for (int j=low; j<high; j++)
			{
				// If current element is smaller than or
				// equal to pivot
				if (arr[j] <= pivot)
				{
					i++;

					// swap arr[i] and arr[j]
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}

			// swap arr[i+1] and arr[high] (or pivot)
			int temp = arr[i+1];
			arr[i+1] = arr[high];
			arr[high] = temp;

			return i+1;
		}


		/* The main function that implements QuickSort()
	      arr[] --> Array to be sorted,
	      low  --> Starting index,
	      high  --> Ending index */
		public long sort(Integer[] arr, int low, int high)
		{
			if (low < high)
			{
				/* pi is partitioning index, arr[pi] is 
	              now at right place */
				int pi = partition(arr, low, high);

				// Recursively sort elements before
				// partition and after partition
				sort(arr, low, pi-1);
				sort(arr, pi+1, high);
			}

			return System.currentTimeMillis();
		}

		/* A utility function to print array of size n */
		public void printArray(int arr[])
		{
			int n = arr.length;
			for (int i=0; i<n; ++i)
				System.out.print(arr[i]+" ");
			System.out.println();
		}
	}
	/*This code is contributed by Rajat Mishra */

	

	

	//https://steemit.com/visualization/@datatreemap/visualize-a-multiple-lines-graph-with-jfreechart-in-java
	public static class MultipleLinesChart extends JFrame { // the class extends the JFrame class
		ArrayList<ArrayList<Long>> data = new ArrayList<ArrayList<Long>>(); //pulls in the data when called

		public MultipleLinesChart(ArrayList<ArrayList<Long>> data) {   // the constructor will contain the panel of a certain size and the close operations 
			super("Scott Blair Assignment 6"); // calls the super class constructor

			this.data=data;
			JPanel chartPanel = createChartPanel();    

			add(chartPanel, BorderLayout.CENTER);

			setSize(640, 440);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
		}

		private JPanel createChartPanel() { // this method will create the chart panel containin the graph 
			String chartTitle = "Sorting Algorithm RunTimes";
			String xAxisLabel = "Input Size";
			String yAxisLabel = "Sort RunTime (ms)";

			XYDataset dataset = createDataset();

			JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

			customizeChart(chart);

			return new ChartPanel(chart);
		}
		
		ArrayList<ArrayList<Long>> getData(){//getter for the d
			return this.data;
		}

		private XYDataset createDataset() {    // this method creates the data as time seris 
			XYSeriesCollection dataset = new XYSeriesCollection();
			XYSeries insertion = new XYSeries("Insertion");
			XYSeries merge = new XYSeries("Merge");
			XYSeries quick = new XYSeries("Quick");
			XYSeries heap = new XYSeries("Heap");		

			for(int i=0;i<10; i++){		
				insertion.add(i*10000, getData().get(0).get(i));
				merge.add(i*10000, getData().get(1).get(i));
				quick.add(i*10000, getData().get(2).get(i));
				heap.add(i*10000, getData().get(3).get(i));
			}
	
			dataset.addSeries(insertion);
			dataset.addSeries(merge);
			dataset.addSeries(quick);
			dataset.addSeries(heap);

			return dataset;
		}

		private void customizeChart(JFreeChart chart) {   // here we make some customization
			XYPlot plot = chart.getXYPlot();
			XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

			// sets paint color for each series
			renderer.setSeriesPaint(0, Color.RED);
			renderer.setSeriesPaint(1, Color.GREEN);
			renderer.setSeriesPaint(2, Color.YELLOW);
			renderer.setSeriesPaint(3, Color.BLUE);


			// sets thickness for series (using strokes)
			renderer.setSeriesStroke(0, new BasicStroke(4.0f));
			renderer.setSeriesStroke(1, new BasicStroke(3.0f));
			renderer.setSeriesStroke(2, new BasicStroke(2.0f));
			renderer.setSeriesStroke(3, new BasicStroke(2.0f));


			// sets paint color for plot outlines
			plot.setOutlinePaint(Color.BLUE);
			plot.setOutlineStroke(new BasicStroke(2.0f));

			// sets renderer for lines
			plot.setRenderer(renderer);

			// sets plot background
			plot.setBackgroundPaint(Color.DARK_GRAY);

			// sets paint color for the grid lines
			plot.setRangeGridlinesVisible(true);
			plot.setRangeGridlinePaint(Color.BLACK);

			plot.setDomainGridlinesVisible(true);
			plot.setDomainGridlinePaint(Color.BLACK);

		}
	}
}