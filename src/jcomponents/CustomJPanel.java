package jcomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class CustomJPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	protected int CUSTOM_JPANEL_WIDTH;
	protected int CUSTOM_JPANEL_HEIGHT;
	
	public CustomJPanel(Rectangle rectangle) {
		CUSTOM_JPANEL_WIDTH  = rectangle.width;
		CUSTOM_JPANEL_HEIGHT = rectangle.height;
		
		setBounds(rectangle);
		setLayout(null);
		setBackground(Color.BLACK);
	}
	
	public CustomJPanel(Dimension dimension) {
		this(new Rectangle(0, 0, dimension.width, dimension.height));
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(CUSTOM_JPANEL_WIDTH, CUSTOM_JPANEL_HEIGHT);
	}
}