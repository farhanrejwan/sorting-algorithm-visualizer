package algorithms;

import visuals.SortingPanel;

public enum SortingAlgorithms {
	NO_ALGORITHM(),
	INSERTION_SORT(
			new InsertionSort(),
			new String[] {
					"\u03a9(n)",
					"\u0398(n^2)",
					"\u039f(n^2)"
			},
			"\u039f(1)"
	),
	BUBBLE_SORT(
			new BubbleSort(),
			new String[] {
					"\u03a9(n)",
					"\u0398(n^2)",
					"\u039f(n^2)"
			},
			"\u039f(1)"
	),
	MERGE_SORT(
			new MergeSort(),
			new String[] {
					"\u03a9(n log(n))",
					"\u0398(n log(n))",
					"\u039f(n log(n))"
			},
			"\u039f(n)"
	),
	QUICK_SORT(
			new QuickSort(),
			new String[] {
					"\u03a9(n log(n))",
					"\u0398(n log(n))",
					"\u039f(n^2)"
			},
			"\u039f(log(n))"
	);
	
	private final int NUM_OF_COMPLEXITY = 3;
	private final int BEST = 0, AVERAGE = 1, WORST = 2;
	
	private String[] timeComplexity = new String[NUM_OF_COMPLEXITY]; 
	private String worstSpaceComplexity;
	private IndexAccess algorithm;
	
	SortingAlgorithms() {
	}
	
	SortingAlgorithms(
			IndexAccess algorithm,
			String[] timeComplexity,
			String worstSpaceComplexity
	) {
		this.algorithm = algorithm;
		
		for (int i = 0;  i < timeComplexity.length;  i++) {
			this.timeComplexity[i] = timeComplexity[i];
		}
		
		this.worstSpaceComplexity = worstSpaceComplexity;
	}
	
	public void performAlgorithm(double[] array, long initialSpeed) { 
		algorithm.sort(array, initialSpeed);
	}
	
	public String getWorstSpaceComplexity() {
		return worstSpaceComplexity;
	}
	public String getBestTimeComplexity() {
		return timeComplexity[BEST];
	}
	public String getAverageTimeComplexity() {
		return timeComplexity[AVERAGE];
	}
	public String getWorstTimeComplexity() {
		return timeComplexity[WORST];
	}
	
	public int getCurrentIndex() {
		return algorithm.getCurrentIndex();
	}
	public int getTraversingIndex() {
		return algorithm.getTraversingIndex();
	}
	public int getSelectedIndex() {
		return algorithm.getSelectedIndex();
	}
	public int getArrayAccess() {
		return algorithm.getArrayAccess();
	}
	public boolean isPaused() {
		return algorithm.isPaused();
	}
	
	public void pause() {
		algorithm.pause();
	}
	public void resume() {
		algorithm.resume();
	}
	public void reset() {
		algorithm.reset();
	}
	public void setSpeed(long speed) {
		algorithm.setSpeed(speed);
	}
	public void setSortingPanel(SortingPanel sortingPanel) {
		algorithm.setSortingPanel(sortingPanel);
	}
	
	public static SortingAlgorithms getAlgorithmViaText(String text) {
		switch(text) {
		case "INSERTION SORT":
			return INSERTION_SORT;
		case "BUBBLE SORT":
			return BUBBLE_SORT;
		case "MERGE SORT":
			return MERGE_SORT;
		case "QUICK SORT":
			return QUICK_SORT;
		default:
			return NO_ALGORITHM;
		}
	}
}