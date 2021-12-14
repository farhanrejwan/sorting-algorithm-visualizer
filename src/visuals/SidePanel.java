package visuals;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.SwingWorker;

import algorithms.SortingAlgorithms;
import jcomponents.CustomJButton;
import jcomponents.CustomJLabel;
import jcomponents.CustomJPanel;

public class SidePanel extends CustomJPanel{
	private static final long serialVersionUID = 1L;
	
	private MainScreen mainScreen;
	private SwingWorker<Void, Void> sidePanelAnimation;
	private boolean isShowing = false;
	
	public SidePanel(MainScreen mainScreen, Rectangle rectangle) {
		super(rectangle);
		setBackground(Color.DARK_GRAY);
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		
		this.mainScreen = mainScreen;
		initComponent(rectangle.width, rectangle.height);
	}
	
	private void initComponent(int width, int height) {
		final int fixed_x = 0, title_height = height / 5;
		int y = 15, font_size = 30;

		String title_text = "<html><center>"
				+ "SORTING<br/>ALGORITHM<br/>VISUALIZER"
				+ "</center></html>";
		add(new CustomJLabel(
				new Rectangle(fixed_x, y, width, title_height), 
				title_text,
				new Font("Arial", Font.PLAIN, font_size)
		));
		
		font_size = 20;
		y = title_height;
		int selected_algo_height = title_height;
		String selected_algorithm_text = "NONE";
		CustomJLabel selected_algorithm = new CustomJLabel(
				new Rectangle(fixed_x, y, width, selected_algo_height), 
				selected_algorithm_text,
				new Font("Arial", Font.PLAIN, font_size)) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void setText(String text) {
				String selected_algorithm = "<html><center>"
						+ "SELECTED SORTING : <br/>"
						+ text
						+ "</center></html>";
				super.setText(selected_algorithm);
			}
		};
		add(selected_algorithm);
		
		AbstractAction action = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				String text = ((CustomJButton)event.getSource()).getText();
				selected_algorithm.setText(text);
				
				mainScreen.setAlgorithm(
						SortingAlgorithms.getAlgorithmViaText(text)
				);				
			}
		};
		
		int button_height = (height - (title_height + selected_algo_height))
							/ (SortingAlgorithms.values().length - 1); 
		y = selected_algorithm.getY() + selected_algorithm.getHeight();
		
		for (SortingAlgorithms algo : SortingAlgorithms.values()) {
			if ("NO_ALGORITHM".equals(algo.toString())) {
				continue;
			}
			
			add(new CustomJButton(
					new Rectangle(fixed_x, y, width, button_height), 
					action,
					algo.toString().replace("_", " ")
			));
			
			y += button_height;
		}
	}
	
	public void startSidePanelAnimation() {
		isShowing = !isShowing;
		
		if (sidePanelAnimation != null && !sidePanelAnimation.isDone()) {
			sidePanelAnimation.cancel(true);
		}
		
		sidePanelAnimation = new SwingWorker<>(){
			@Override
			protected Void doInBackground() throws InterruptedException {
				while (!isCancelled()) {
					if (isShowing && getX() <= 0) {
						setLocation(getX() + 1, getY());
					}
					else if (!isShowing && getX() >= -1 * CUSTOM_JPANEL_WIDTH) {
						setLocation(getX() - 1, getY());
					}
					else {
						break;
					}
				}
				
				return null;
			}
			
			@Override
			protected void done() {
				super.done();
				sidePanelAnimation.cancel(true);
			}
		};
		
		sidePanelAnimation.execute();
	}
	
	public boolean isShown() {
		return getX() > -getWidth();
	}
}