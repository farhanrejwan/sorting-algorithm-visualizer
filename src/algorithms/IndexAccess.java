package algorithms;

import visuals.SortingPanel;

abstract class IndexAccess {
	protected SortingPanel sortingPanel;
	
	protected int currentIndex = -1;
	protected int traversingIndex = -1;
	protected int selectedIndex = -1;
	protected int arrayAccess;
	protected volatile boolean isPaused = false;
	protected final long PAUSED_INTERVAL = 1000;
	protected double[] array;
	protected long speed = 0;
	
	protected final synchronized void actionWhenPaused() {
		while(isPaused()) {
			System.out.println("waiting...");
			
			try {
				Thread.sleep(PAUSED_INTERVAL);
			} catch (InterruptedException e) {
				System.out.println("Thread Closed");
			}
		}
	}
	
	protected final void swap(int a_index, int b_index) {
		double temp = array[a_index];
		array[a_index] = array[b_index];
		array[b_index] = temp;
		
		sortingPanel.repaint();
	}
	
	public final void pause() {	
		if (!isPaused()) {
			isPaused = true;
		}
	}
	
	public final void resume() {	
		if (isPaused()) {
			isPaused = false;
		}
	}
	
	public final void reset() {
		isPaused = false;
		currentIndex = -1;
		traversingIndex = -1;
		selectedIndex = -1;
		speed = 0;
		arrayAccess = 0;
	}
	
	public void sort(double[] array, long initialSpeed) {
		this.array = array;
		speed = initialSpeed;
	}
	
	public final void setSortingPanel(SortingPanel sortingPanel) {
		this.sortingPanel = sortingPanel;
	}
	public final void setSpeed(long speed) {
		this.speed = speed;
	}
	
	public final boolean isPaused() {
		return isPaused;
	}
	public final int getArrayAccess() {
		return arrayAccess;
	}
	public final int getCurrentIndex() {
		return currentIndex;
	}
	public final int getTraversingIndex() {
		return traversingIndex;
	}
	public final int getSelectedIndex() {
		return selectedIndex;
	}
}