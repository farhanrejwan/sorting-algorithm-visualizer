package jcomponents;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.Timer;

public class CustomJSlider extends JSlider {
	private static final long serialVersionUID = 1L;
	
	protected int CUSTOM_JSLIDER_WIDTH;
	protected int CUSTOM_JSLIDER_HEIGHT;
	
	private Timer sliderAnimation;
	private volatile boolean isCovered = true;
	private volatile int startOfCoveredRegion;
	
	public CustomJSlider(
			Rectangle rectangle,
			BoundedRangeModel boundedRangeModel, 
			int minor_tick,
			int major_tick
	) {
		CUSTOM_JSLIDER_WIDTH = rectangle.width;
		CUSTOM_JSLIDER_HEIGHT = rectangle.height;
		
		setOrientation(HORIZONTAL);
		setBounds(rectangle);
		setModel(boundedRangeModel);
		setMinorTickSpacing(minor_tick);
		setMajorTickSpacing(major_tick);
		
		setOpaque(false);
		setFocusable(false);
		setSnapToTicks(true);
		
		initAnimationTimer();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!isCovered) {
					return;
				}
				
				isCovered = false;
				setEnabled(false);
				sliderAnimation.start();				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				Rectangle bounds = new Rectangle(
						0,
						0,
						CUSTOM_JSLIDER_WIDTH,
						CUSTOM_JSLIDER_HEIGHT
				);
				
				if (bounds.contains(e.getPoint())) {
					return;
				}
				
				isCovered = true;
				setEnabled(false);
				sliderAnimation.start();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setColor(Color.BLACK);
		graphics.fillRect(
				startOfCoveredRegion,
				0,
				this.getWidth() - startOfCoveredRegion,
				this.getHeight()
		);
	}
	
	public void initAnimationTimer() {
		sliderAnimation = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (startOfCoveredRegion <= getWidth() && !isCovered()) {
					startOfCoveredRegion++;
				}
				else if (startOfCoveredRegion >= 0 && isCovered()) {
					startOfCoveredRegion--;
				}
				else {
					sliderAnimation.stop();
					setEnabled(true);
				}
				
				repaint();
			}
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(CUSTOM_JSLIDER_WIDTH, CUSTOM_JSLIDER_HEIGHT);
	}
	
	public final int getStartOfCoveredRegion() {
		return startOfCoveredRegion;
	}
	
	public final boolean isCovered() {
		return isCovered;
	}
	
	public final boolean isAnimationRunning() {
		return sliderAnimation.isRunning();
	}
}