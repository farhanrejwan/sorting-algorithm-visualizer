package algorithms;

public class QuickSort extends IndexAccess {

	@Override
	public void sort(double[] array, long initialSpeed) {
		super.sort(array, initialSpeed);
		
		int left = 0;
		int right = array.length - 1;
		int middle = (left + right) / 2;
		int pivot = medianOfThree(left, middle, right);
		quickSort(pivot, left, right);
		
		traversingIndex = -1;
		currentIndex = -1;
	}
	
	private void quickSort(int pivot, int start, int end) {
		if (start >= end) {
			return;
		}
		
		int selectedIndex = start - 1;
		swap(pivot, end);
		pivot = end;
		
		currentIndex = pivot;
		for (
				traversingIndex = start;
				traversingIndex < pivot;
				traversingIndex++
		) {
			if (isPaused()) {
				actionWhenPaused();
			}
				
			if (array[traversingIndex] < array[pivot]) {
				swap(++selectedIndex, traversingIndex);
			}
			
			try {
				arrayAccess++;
				sortingPanel.repaint();
				Thread.sleep(speed);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		int newPivotPosition = selectedIndex + 1;
		swap(newPivotPosition, pivot);
		
		int lessMiddle = (start + selectedIndex) / 2;
		int lessPivot = medianOfThree(start, lessMiddle, selectedIndex);
		quickSort(lessPivot, start, selectedIndex);
		
		int greaterStart = newPivotPosition + 1;
		int greaterMiddle = (greaterStart + end) / 2;
		int greaterPivot = medianOfThree(greaterStart, greaterMiddle, end);
		quickSort(greaterPivot, greaterStart, end);
	}
	
	private int medianOfThree(int a, int b, int c) {
		double left		= array[a];
		double middle	= array[b];
		double right	= array[c];
		
		if ((left > middle) != (left > right)) {
			return a;
		}
		else if ((middle > left) != (middle > right)) {	
			return b;
		}
		else {
			return c;
		}
	}
}