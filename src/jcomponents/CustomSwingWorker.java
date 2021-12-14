package jcomponents;

import javax.swing.SwingWorker;

public abstract class CustomSwingWorker<K, V> extends SwingWorker<K, V> {
	private volatile boolean isPaused;
	
	public final void pause() {
		if (!isPaused() && !isDone()) {
			isPaused = true;
			firePropertyChange("paused", false, true);
		}
	}
	
	public final void resume() {
		if (isPaused() && !isDone()) {
			isPaused = false;
			firePropertyChange("paused", true, false);
		}
	}
	
	public final boolean isPaused() {
		return isPaused;
	}
}