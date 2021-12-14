package algorithms;

public class BubbleSort extends IndexAccess {

	@Override
	public void sort(double[] array, long initial_speed) {
		super.sort(array, initial_speed);
		
		boolean swapped;
		for (
				currentIndex = array.length - 1;
				currentIndex >= 0;
				currentIndex--
		) {
			swapped = false;
			for (
					traversingIndex = 0,
					selectedIndex = traversingIndex + 1;
					selectedIndex <= currentIndex;
					traversingIndex++, selectedIndex++
			) {
				if (isPaused()) {
					actionWhenPaused();
				}
				
				try {
					arrayAccess++;
					sortingPanel.repaint();
					Thread.sleep(speed);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				
				if (array[traversingIndex] > array[selectedIndex]) {
					swapped = true;
					swap(traversingIndex, selectedIndex);
				}
			}
			
			if (swapped == false) {
				break;
			}
		}
	}	
}