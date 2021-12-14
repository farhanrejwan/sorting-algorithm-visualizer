package visuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingWorker;

import algorithms.SortingAlgorithms;
import jcomponents.CustomJPanel;
import jcomponents.CustomSwingWorker;

public class SortingPanel extends CustomJPanel {
	private static final long serialVersionUID = 1L;
	
	private MainScreen mainScreen;
	
	private final float BAR_STROKE_THICKNESS = 2f;
	private final int FONT_SIZE = 15;
	private final Font ALGORITHM_FONT = new Font(
			"Arial", Font.PLAIN, FONT_SIZE
	);
	
	private int size = Menu.INIT_SLIDER_SIZE;
	private long speed = Menu.INIT_SLIDER_SPEED;
	
	private SortingAlgorithms currentAlgorithm = SortingAlgorithms.NO_ALGORITHM;
	private double bar_width;
	private double bar_height[];
	
	private CustomSwingWorker<Void, Void> sortingWorker;
	
	private int currentBarIndex = -1;
	private int traversingBarIndex = -1;
	private int selectedBarIndex = -1;
	
	public SortingPanel(MainScreen mainScreen, Rectangle rectangle) {
		super(rectangle);
		this.mainScreen = mainScreen;
		
		initBarsDimension();
	}
	
	private void initBarsDimension() {
		bar_width = (double) CUSTOM_JPANEL_WIDTH / size;
		bar_height = new double[size];
		
		double height_interval = (double) CUSTOM_JPANEL_HEIGHT / size;

		for (int i = 0; i < size; i++) {
			bar_height[i] = height_interval * (i + 1.0) - BAR_STROKE_THICKNESS;
		}
		
		repaint();
	}
	
	public void randomizeBarHeight() {
		SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				final long randomTickSpeed = 10; 
				int left = 0, right = size - 1;
				
				for (;  left < size / 2;  left++, right--) {
					int rand_index;
					double temp;
					
					rand_index = (int) (Math.random() * size);
					temp = bar_height[left];
					bar_height[left] = bar_height[rand_index];
					bar_height[rand_index] = temp;
					
					rand_index = (int) (Math.random() * size);
					temp = bar_height[right];
					bar_height[right] = bar_height[rand_index];
					bar_height[rand_index] = temp;
					
					repaint();
					Thread.sleep(randomTickSpeed);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
				super.done();
				mainScreen.doneProcess(MainScreen.PROCESS.SHUFFLE);
			}
		}; 
		swingWorker.execute();
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		super.paintComponents(graphics);
		Graphics2D graphics2D = (Graphics2D) graphics;
		
		Stroke stroke = graphics2D.getStroke();
		graphics2D.setStroke(new BasicStroke(BAR_STROKE_THICKNESS));

		graphics2D.setColor(Color.GREEN);
		
		double x, y;
		for (int i = 0; i < size; i++) {
			x = i * bar_width;
			y = CUSTOM_JPANEL_HEIGHT - (BAR_STROKE_THICKNESS + bar_height[i]);
			
			Rectangle2D.Double rectangle2D = new Rectangle2D.Double(
					x,
					y,
					bar_width,
					bar_height[i]
			);
			
			graphics2D.fill(rectangle2D);
		}
		
		if (currentAlgorithm != SortingAlgorithms.NO_ALGORITHM) {
			drawAlgorithm(graphics2D);
		}
		
		graphics2D.setStroke(stroke);
		graphics2D.dispose();
	}
	
	private void drawAlgorithm(Graphics2D graphics2D) {
		double x, y;
		
		if ((currentBarIndex = currentAlgorithm.getCurrentIndex()) != -1) {
			graphics2D.setColor(Color.YELLOW);
			 
			x = currentBarIndex * bar_width;
			y = CUSTOM_JPANEL_HEIGHT
					- (BAR_STROKE_THICKNESS + bar_height[currentBarIndex]);
			
			Rectangle2D.Double rectangle2D = new Rectangle2D.Double(
					x,
					y,
					bar_width,
					bar_height[currentBarIndex]
			);
			
			graphics2D.fill(rectangle2D);
		}
		
		if ((traversingBarIndex = currentAlgorithm.getTraversingIndex()) != -1) {	
			graphics2D.setColor(Color.RED);
			
			x = traversingBarIndex * bar_width;
			y = CUSTOM_JPANEL_HEIGHT
					- (BAR_STROKE_THICKNESS + bar_height[traversingBarIndex]);
			
			Rectangle2D.Double rectangle2D = new Rectangle2D.Double(
					x,
					y,
					bar_width,
					bar_height[traversingBarIndex]
			);
			
			graphics2D.fill(rectangle2D);
		}
		
		if ((selectedBarIndex = currentAlgorithm.getSelectedIndex()) != -1) {
			graphics2D.setColor(Color.BLUE);
			 
			x = selectedBarIndex * bar_width;
			y = CUSTOM_JPANEL_HEIGHT
					- (BAR_STROKE_THICKNESS + bar_height[selectedBarIndex]);
			
			Rectangle2D.Double rectangle2D = new Rectangle2D.Double(
					x,
					y,
					bar_width, bar_height[selectedBarIndex]
			);
			
			graphics2D.fill(rectangle2D);
		}
		
		graphics2D.setColor(Color.WHITE);
		graphics2D.setFont(ALGORITHM_FONT);
		
		int string_y_padding = 20,
			string_x_padding = 200;
		
		x = 20;
		y = 20;
		
		graphics2D.drawString("Current Algorithm : ", (int)x, (int)y);
		
		x += string_x_padding;
		
		String algorithm = currentAlgorithm.toString().replace("_", " ");
		
		graphics2D.drawString(algorithm, (int)x, (int)y);
		
		x -= string_x_padding;
		y += string_y_padding;
		
		graphics2D.drawString("Array Access : ", (int)x, (int)y);
		
		x += string_x_padding;
		
		String array_access = String.valueOf(
				currentAlgorithm.getArrayAccess()
		);
		
		graphics2D.drawString(array_access, (int)x, (int)y);
	}
	
	public void sort() {
		sortingWorker = new CustomSwingWorker<>() {
			@Override
			protected Void doInBackground() {
				currentAlgorithm.performAlgorithm(bar_height, speed);
				
				return null;
			}

			@Override
			protected void done() {
				if (isCancelled()) {
					return;
				}
				
				super.done();
				mainScreen.doneProcess(MainScreen.PROCESS.SORT);
			}
		};
		sortingWorker.execute();
	}
	
	public void pause() {
		System.out.println("Process is paused...");
		currentAlgorithm.pause();
	}
	
	public synchronized void resume() {
		System.out.println("Continue...");
		currentAlgorithm.resume();
	}
	
	public void setBarSize(int size) {
		this.size = size;	
		initBarsDimension();
	}
	
	public void setSpeed(long speed) {
		this.speed = speed;
		
		if (currentAlgorithm != SortingAlgorithms.NO_ALGORITHM) {
			currentAlgorithm.setSpeed(speed);
		}
	}
	
	public void setAlgorithm(SortingAlgorithms algorithm) {
		this.currentAlgorithm = algorithm;
		
		if (algorithm != SortingAlgorithms.NO_ALGORITHM) {
			currentAlgorithm.setSortingPanel(this);
		}
		
		resetScreen();
	}
	
	public synchronized void resetScreen() {
		if (currentAlgorithm != SortingAlgorithms.NO_ALGORITHM) {
			currentAlgorithm.reset();
		}
		
		currentBarIndex	= -1;
		traversingBarIndex = -1;
		selectedBarIndex = -1;
		
		if (sortingWorker != null) {
			sortingWorker.cancel(true);
		}			
	}
}