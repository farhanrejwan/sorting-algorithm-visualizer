package visuals;

import java.awt.Dimension;
import java.awt.Rectangle;

import algorithms.SortingAlgorithms;
import jcomponents.CustomJPanel;

public class MainScreen extends CustomJPanel {
	private static final long serialVersionUID = 1L;
	
	private SortingAlgorithms selectedAlgorithm = SortingAlgorithms.NO_ALGORITHM;
	private Menu menu;
	private SortingPanel sortingPanel;
	private SidePanel sidePanel;
	
	public static enum PROCESS {
		SORT, PAUSE, RESUME, SHUFFLE, RESET, SIDEPANEL_ANIMATION;
	}
	
	public MainScreen(Dimension dimension) {
		super(dimension);
		createAndDisplayGUI();
	}
	
	private void createAndDisplayGUI() {
		menu = new Menu(this, new Rectangle(
				0,
				0, 
				this.getBounds().width, 
				this.getBounds().height / 10
		));
		
		sortingPanel = new SortingPanel(this, new Rectangle(
				0, 
				menu.getY() + menu.getHeight(), 
				this.getBounds().width, 
				this.getBounds().height - menu.getHeight()
		));
		
		sidePanel = new SidePanel(this, new Rectangle(
				-1 * this.getBounds().width / 4, 
				sortingPanel.getY(), 
				this.getBounds().width / 4, 
				sortingPanel.getHeight() * 19 / 20
		));
		
		add(menu);
		add(sidePanel);
		add(sortingPanel);
	}
	
	public void startProcess(PROCESS process) {
		switch(process) {
		case SORT:
			sortingPanel.sort();
			break;
		case PAUSE:
			sortingPanel.pause();
			break;
		case RESUME:
			sortingPanel.resume();
			break;
		case SHUFFLE:
			sortingPanel.randomizeBarHeight();
			sortingPanel.setAlgorithm(selectedAlgorithm);
			break;
		case RESET:
			sortingPanel.resetScreen();
			break;
		case SIDEPANEL_ANIMATION:
			sidePanel.startSidePanelAnimation();
			break;
		default:
			System.out.println("Process Start Request Error : " + 
								process + " Undefined Process");
			break;
		}
	}
	
	public void doneProcess(PROCESS process) {
		switch(process) {
		case SORT:
			menu.doneSorting();
			break;
		case SHUFFLE:
			menu.doneShuffling();
			break;
		default:
			System.out.println("Process Done Flag Error : " + 
								process + " Undefined Process");
			break;
		}
	}

	public void changeValue(String name, int value) {
		switch(name) {
		case "SIZE":
			sortingPanel.setBarSize(value);
			break;
		case "SPEED":
			sortingPanel.setSpeed((long)value);
			break;
		default:
			System.out.println("Change Value Error : "
							+ name
							+ " Undefined variable"
			);
			break;
		}
	}
	
	public void setAlgorithm(SortingAlgorithms selectedAlgorithm) {
		this.selectedAlgorithm = selectedAlgorithm;
		sortingPanel.setAlgorithm(selectedAlgorithm);
		menu.donePicking();
	}
	
	public boolean isSidePanelShown() {
		return sidePanel.isShown();
	}
	
	public boolean hasChosenAnAlgorithm() {
		return selectedAlgorithm != SortingAlgorithms.NO_ALGORITHM;
	}
}