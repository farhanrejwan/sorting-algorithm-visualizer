package jcomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CustomJLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	
	protected int CUSTOM_JLABEL_WIDTH;
	protected int CUSTOM_JLABEL_HEIGHT;
	
	public CustomJLabel(Rectangle rectangle, ImageIcon icon, String text) {
		this.CUSTOM_JLABEL_WIDTH  = rectangle.width;
		this.CUSTOM_JLABEL_HEIGHT = rectangle.height;
		
		setBounds(rectangle);
		setIcon(icon);
		setText(text);
		
		setOpaque(false);
		setForeground(Color.WHITE);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
	}
	
	public CustomJLabel(Rectangle rectangle, ImageIcon icon) {
		this(rectangle, icon, "");
	}
	
	public CustomJLabel(Rectangle rectangle, String text, Font font) {
		this(rectangle, null, text);
		setFont(font);
	}
	
	public CustomJLabel(Rectangle rectangle, String text) {
		this(rectangle, null, text);
	}
	
	public CustomJLabel(Rectangle rectangle) {
		this(rectangle, null, "");
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(
				this.CUSTOM_JLABEL_WIDTH,
				this.CUSTOM_JLABEL_HEIGHT
		);
	}
}