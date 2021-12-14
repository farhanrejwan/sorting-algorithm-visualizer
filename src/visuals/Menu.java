package visuals;

import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jcomponents.CustomJButton;
import jcomponents.CustomJLabel;
import jcomponents.CustomJPanel;
import jcomponents.CustomJSlider;

public class Menu extends CustomJPanel {
	private static final long serialVersionUID = 1L;
	private MainScreen mainScreen;
	private CustomJButton burger, sort, stop, shuffle;
	private CustomJLabel title, sliderName, sliderValue;
	private CustomJSlider sizeSlider, speedSlider;
	
	private Timer sliderAnimationTimer;
	
	private final String size_slider = "SIZE", speed_slider = "SPEED";
	private String current_slider;
	
	public static final int MIN_SLIDER_SIZE = 0,
				MAX_SLIDER_SIZE = 200, 
				INIT_SLIDER_SIZE = 100,
				SLIDER_MINOR_TICK_SIZE = 10,
				SLIDER_MAJOR_TICK_SIZE = 100;

	public static final int MIN_SLIDER_SPEED = 0, 
				MAX_SLIDER_SPEED = 200, 
				INIT_SLIDER_SPEED = 100,
				SLIDER_MINOR_TICK_SPEED = 10,
				SLIDER_MAJOR_TICK_SPEED = 100;
	
	private enum COMPONENT  {
		BURGER, SORT, SHUFFLE, SLIDER,
		SORT_VISIBILITY, STOP_VISIBILITY, SLIDER_VISIBILITY_SIZE; 	
	}
	
	private boolean paused = false;
	
	public Menu(MainScreen mainScreen, Rectangle rectangle) {
		super(rectangle);
		this.mainScreen = mainScreen;
		
		initComponent();
	}
	
	private void initComponent() {
		final int PADDING = CUSTOM_JPANEL_HEIGHT * 1 / 6;
		final int FIXED_HEIGHT = CUSTOM_JPANEL_HEIGHT - (2 * PADDING); 
		final int FIXED_WIDTH = FIXED_HEIGHT;
		final int FIXED_Y = CUSTOM_JPANEL_HEIGHT / 2 - FIXED_HEIGHT / 2;
		final Font SLIDER_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);
		
		ImageIcon icons[] = setUpIcon(FIXED_HEIGHT);
		final int BURGER_ENABLED_INDEX = 0;
		final int BURGER_DISABLED_INDEX = 1;
		final int SORT_ENABLED_INDEX = 2;
		final int SORT_DISABLED_INDEX = 3;
		final int SHUFFLE_ENABLED_INDEX = 4;
		final int SHUFFLE_DISABLED_INDEX = 5;
		final int STOP_ENABLED_INDEX = 6;
		
		final String burger_tooltip = "MENU";
		final String no_algorithm_tooltip = "PICK AN ALGORITHM FIRST";
		final String shuffle_tooltip = "SHUFFLE";
		final String stop_tooltip = "STOP";				
		
		AbstractAction burger_action = new AbstractAction("burger") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				mainScreen.startProcess(
						MainScreen.PROCESS.SIDEPANEL_ANIMATION
				);
				
				if (paused) {
					resetSorting();
				}
				
				mainScreen.repaint();
			}
		};
		
		burger = new CustomJButton(
				new Rectangle(
						PADDING, FIXED_Y, FIXED_WIDTH, FIXED_HEIGHT
				),
				burger_action,
				icons[BURGER_ENABLED_INDEX],
				icons[BURGER_DISABLED_INDEX],
				burger_tooltip
		);
		
		AbstractAction sort_action = new AbstractAction("sort") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!mainScreen.hasChosenAnAlgorithm())	{
					sort.setToolTipText("Please choose an algorithm first");
					sort.createToolTip().setVisible(true);
					return;
				}
				else {
					sort.setToolTipText("");
				}
				
				if (paused) {
					mainScreen.startProcess(MainScreen.PROCESS.RESUME);
				}
				else {
					resetSorting();
					mainScreen.startProcess(MainScreen.PROCESS.SORT);
				}
				
				if (mainScreen.isSidePanelShown()) {
					mainScreen.startProcess(
							MainScreen.PROCESS.SIDEPANEL_ANIMATION
					);
				}
				
				changeComponentState(
						COMPONENT.BURGER,
						COMPONENT.SHUFFLE,
						COMPONENT.SLIDER, 
						COMPONENT.SORT_VISIBILITY,
						COMPONENT.STOP_VISIBILITY
				);
			}
		};
		
		sort = new CustomJButton(
				new Rectangle(
						burger.getX() + burger.getWidth() + PADDING, 
						FIXED_Y, 
						FIXED_WIDTH, 
						FIXED_HEIGHT
				),
				sort_action,
				icons[SORT_ENABLED_INDEX],
				icons[SORT_DISABLED_INDEX],
				no_algorithm_tooltip
		);
		
		AbstractAction stop_action = new AbstractAction("stop") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				paused = true;
				
				changeComponentState(
						COMPONENT.BURGER,
						COMPONENT.SHUFFLE,
						COMPONENT.SLIDER, 
						COMPONENT.SORT_VISIBILITY,
						COMPONENT.STOP_VISIBILITY
				);
				
				mainScreen.startProcess(MainScreen.PROCESS.PAUSE);
			}
		};
		
		stop = new CustomJButton(
				new Rectangle(burger.getX() + burger.getWidth() + PADDING, 
						FIXED_Y, 
						FIXED_WIDTH, 
						FIXED_HEIGHT
				),
				stop_action,
				icons[STOP_ENABLED_INDEX],
				stop_tooltip
		);
		
		AbstractAction shuffle_action = new AbstractAction("shuffle") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mainScreen.isSidePanelShown()) {
					mainScreen.startProcess(
							MainScreen.PROCESS.SIDEPANEL_ANIMATION
					);
				}
				
				changeComponentState(
						COMPONENT.BURGER,
						COMPONENT.SORT,
						COMPONENT.SHUFFLE, 
						COMPONENT.SLIDER_VISIBILITY_SIZE
				);
				
				mainScreen.startProcess(MainScreen.PROCESS.SHUFFLE);
			}
		};
		
		shuffle = new CustomJButton(
				new Rectangle(
						sort.getX() + sort.getWidth() + PADDING, 
						FIXED_Y, 
						FIXED_WIDTH, 
						FIXED_HEIGHT
				),
				shuffle_action,
				icons[SHUFFLE_ENABLED_INDEX],
				icons[SHUFFLE_DISABLED_INDEX],
				shuffle_tooltip);

		current_slider = size_slider;
		
		sliderName	=	new CustomJLabel(
				new Rectangle(
						shuffle.getX() + shuffle.getWidth() + PADDING, 
						FIXED_Y,
						FIXED_WIDTH * 5 / 4,
						FIXED_HEIGHT
				),
				current_slider, SLIDER_FONT
		);
		
		UIManager.put("Slider.paintValue", false);

		sizeSlider = new CustomJSlider(
				new Rectangle(
						sliderName.getX() + sliderName.getWidth() + PADDING, 
						FIXED_Y, 
						FIXED_WIDTH * 2,
						FIXED_HEIGHT
				),
				new DefaultBoundedRangeModel(
						INIT_SLIDER_SIZE,
						0,
						MIN_SLIDER_SIZE,
						MAX_SLIDER_SIZE
				),
				SLIDER_MINOR_TICK_SIZE,
				SLIDER_MAJOR_TICK_SIZE
		);
		
		sizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (mainScreen.isSidePanelShown()) {
					mainScreen.startProcess(
							MainScreen.PROCESS.SIDEPANEL_ANIMATION
					);
				}
				
				resetSorting();
				
				int size_value = ((CustomJSlider)e.getSource()).getValue();
				
				mainScreen.changeValue(size_slider, size_value);
				sliderValue.setText(String.valueOf(size_value));
			}
		});
		
		sizeSlider.addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sliderAnimationTimer.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sliderAnimationTimer.start();
			}

			@Override
			public void mouseClicked(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		speedSlider	= new CustomJSlider(
				sizeSlider.getBounds(),
				new DefaultBoundedRangeModel(
						INIT_SLIDER_SPEED,
						0,
						MIN_SLIDER_SPEED,
						MAX_SLIDER_SPEED
				),
				SLIDER_MINOR_TICK_SPEED,
				SLIDER_MAJOR_TICK_SPEED
		);
		
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int speed_value = ((CustomJSlider)e.getSource()).getValue();
				mainScreen.changeValue(speed_slider, speed_value);
				sliderValue.setText(String.valueOf(speed_value) + "ms");
			}
		});
		
		speedSlider.addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sliderAnimationTimer.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sliderAnimationTimer.start();
			}

			@Override
			public void mouseClicked(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		sliderValue	= new CustomJLabel(
				new Rectangle(
						sizeSlider.getX(),
						FIXED_Y,
						FIXED_WIDTH * 3 / 2,
						FIXED_HEIGHT
				), 
				String.valueOf(sizeSlider.getValue()),
				SLIDER_FONT
		);
		
		String titleAndDev = "<html>"
				+ "<span style = \"font-weight: bold; color: #FFFFFF; font-size: 25px;\">"
				+ "Sorting Algoritm Visualizer"
				+ "</span>"
				+ "</html>";
		
		title = new CustomJLabel(
				new Rectangle(
						sizeSlider.getX()
						+ sizeSlider.getStartOfCoveredRegion(),
						FIXED_Y, 
						CUSTOM_JPANEL_WIDTH - (
								sizeSlider.getX()
								+ sizeSlider.getWidth()
								+ PADDING
						),
						FIXED_HEIGHT
				),
				titleAndDev
		);
		
		sort.setEnabled(false);
		stop.setVisible(false);
		speedSlider.setVisible(false);
		
		initAnimationTimer();
		
		add(burger);
		add(sort);
		add(shuffle);
		add(stop);
		add(sliderName);
		add(sliderValue);
		add(sizeSlider);
		add(speedSlider);
		add(title);
	}
	
	private void initAnimationTimer() {
		sliderAnimationTimer = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int startOfCoveredRegion = sizeSlider.isVisible()
						? sizeSlider.getStartOfCoveredRegion()
						: speedSlider.getStartOfCoveredRegion();
				
				startOfCoveredRegion += sizeSlider.getX();
				
				boolean animationRunning = sizeSlider.isVisible()
						? sizeSlider.isAnimationRunning()
						: speedSlider.isAnimationRunning();
				
				if (animationRunning) {
					sliderValue.setLocation(
							startOfCoveredRegion,
							sliderValue.getY()
					);
					title.setLocation(
							sliderValue.getX() + sliderValue.getWidth(),
							title.getY()
					);
				}
				else {
					sliderAnimationTimer.stop();
				}
			}
		});
	}
	
	private ImageIcon[] setUpIcon(int dim) {
		String paths[] = {
				"/burger-icon-enabled.png",
				"/burger-icon-disabled.png",
				"/sort-icon-enabled.png",
				"/sort-icon-disabled.png",
				"/shuffle-icon-enabled.png",
				"/shuffle-icon-disabled.png",
				"/stop-icon-enabled.png"
		};
		
		ImageIcon icons[] = new ImageIcon[paths.length];
		
		final int ICON_WIDTH = dim * 2 / 3, 
				  ICON_HEIGHT = ICON_WIDTH;
		
		for (int i = 0;  i < icons.length;  i++) {
			try {
				Image image = ImageIO.read(
							getClass().getResource(paths[i])
						).getScaledInstance(
								ICON_WIDTH,
								ICON_HEIGHT,
								Image.SCALE_SMOOTH
						);
				
				icons[i] = new ImageIcon(image);
			} catch (IOException e) {
				System.out.println("Failed to get Image File");
				e.printStackTrace();
			}
		}
		
		return icons;
	}
	
	private void changeComponentState(COMPONENT...components) {
		for(COMPONENT component : components) {
			switch(component) {
			case BURGER:
				burger.setEnabled(!burger.isEnabled());
				break;
			case SORT:
				if (!mainScreen.hasChosenAnAlgorithm()) {
					sort.setEnabled(false);
				}
				else {
					sort.setEnabled(!sort.isEnabled());
				}
				break;
			case SORT_VISIBILITY:
				sort.setVisible(!sort.isVisible());
				break;
			case STOP_VISIBILITY:
				stop.setVisible(!stop.isVisible());
				break;
			case SHUFFLE:
				shuffle.setEnabled(!shuffle.isEnabled());
				break;
			case SLIDER:
				sizeSlider.setVisible(!sizeSlider.isVisible());
				speedSlider.setVisible(!speedSlider.isVisible());
				
				current_slider = current_slider == size_slider
						? speed_slider
						: size_slider;
				sliderName.setText(current_slider);
				
				int value = current_slider == size_slider
						? sizeSlider.getValue()
						: speedSlider.getValue();
				
				String strValue = current_slider == size_slider
						? String.valueOf(value)
						: String.valueOf(value) + "ms";						
				
				sliderValue.setText(strValue);
				break;
			case SLIDER_VISIBILITY_SIZE:
				sizeSlider.setVisible(!sizeSlider.isVisible());
				break;
			default:
				System.out.println("Undefined Component");
				break;
			}
		}
	}
	
	public void donePicking() {
		sort.setEnabled(true);
		sort.setToolTipText("SORT");
	}
	
	public void doneShuffling() {
		changeComponentState(
				COMPONENT.BURGER,
				COMPONENT.SORT,
				COMPONENT.SHUFFLE, 
				COMPONENT.SLIDER_VISIBILITY_SIZE
		);
		resetSorting();
	}
	
	public void doneSorting() {
		changeComponentState(
				COMPONENT.BURGER,
				COMPONENT.SHUFFLE,
				COMPONENT.SLIDER, 
				COMPONENT.SORT_VISIBILITY,
				COMPONENT.STOP_VISIBILITY);
		resetSorting();
	}
	
	public void resetSorting() {
		paused = false;
		mainScreen.startProcess(MainScreen.PROCESS.RESET);
	}
}