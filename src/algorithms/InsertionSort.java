package algorithms;

public class InsertionSort extends IndexAccess {
	@Override
	public void sort(double[] array, long initial_speed) {
		super.sort(array, initial_speed);
		
		for (currentIndex = 1;  currentIndex < array.length;  currentIndex++) {
			for (
					traversingIndex = currentIndex,
					selectedIndex = traversingIndex - 1;
					selectedIndex >= 0
					&& array[selectedIndex] > array[traversingIndex];
					traversingIndex--, selectedIndex--
			) {
				if (isPaused()) {
					actionWhenPaused();
				}
				
				try {
					arrayAccess++;
					sortingPanel.repaint();
					Thread.sleep(speed);
					swap(traversingIndex, selectedIndex);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		currentIndex = -1;
	}
}