package algorithms;

public class MergeSort extends IndexAccess {

	@Override
	public void sort(double[] array, long initialSpeed) {
		super.sort(array, initialSpeed);
		
		mergeSort(0, array.length - 1);
		
		traversingIndex= -1;
		selectedIndex	= -1;
		sortingPanel.repaint();		
	}
	
	private void mergeSort(int start, int end) {
		if(start == end) {
			return;
		}
		
		int lookupLength = end - start + 1;
		int middle = ((lookupLength) / 2) + start;
		
		mergeSort(start, middle - 1);
		mergeSort(middle, end);
		
		currentIndex = start;
		merge(start, middle, end);
	}
	
	private void merge(int start, int middle, int end) {
		int lookupLength = end - start + 1;
		double[] temp = new double[lookupLength];
		
		for (int i = 0; i < lookupLength; i++) {
			temp[i] = array[start + i];
		}
		
		int temp_middle = middle - start;
		int a_ptr = start;
		int l_ptr = 0;
		int r_ptr = temp_middle; 
		
		while(l_ptr < temp_middle && r_ptr < temp.length) {
			if (isPaused()) {
				actionWhenPaused();
			}
				
			arrayAccess++;
			traversingIndex= l_ptr + start;
			selectedIndex	= r_ptr + start;
			
			if (temp[l_ptr] < temp[r_ptr]) {
				array[a_ptr] = temp[l_ptr++];
			}
			else {
				array[a_ptr] = temp[r_ptr++];
			}
			
			try {
				a_ptr++;
				sortingPanel.repaint();
				Thread.sleep(speed);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		while (l_ptr < temp_middle) {
			if (isPaused()) {
				actionWhenPaused();
			}
			
			try {
				traversingIndex= l_ptr + start;
				sortingPanel.repaint();
				Thread.sleep(speed);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			array[a_ptr++] = temp[l_ptr++];
		}
		
		while (r_ptr < temp.length) {
			if (isPaused()) {
				actionWhenPaused();
			}
			
			try {
				selectedIndex	= r_ptr + start;
				sortingPanel.repaint();
				Thread.sleep(speed);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			array[a_ptr++] = temp[r_ptr++];
		}		
	}
}