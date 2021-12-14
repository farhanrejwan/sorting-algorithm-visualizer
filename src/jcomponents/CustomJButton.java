package jcomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CustomJButton extends JButton{
	private static final long serialVersionUID = 1L;

	protected int CUSTOM_JBUTTON_WIDTH;
	protected int CUSTOM_JBUTTON_HEIGHT;
	
	public CustomJButton(
			Rectangle rectangle,
			AbstractAction action, 
			ImageIcon enabled_icon,
			ImageIcon disabled_icon, 
			String text,
			String tooltip
	) {
		CUSTOM_JBUTTON_WIDTH  = rectangle.width;
		CUSTOM_JBUTTON_HEIGHT = rectangle.height;
		
		setBounds(rectangle);
		setAction(action);
		setIcon(enabled_icon);
		setDisabledIcon(disabled_icon);
		setText(text);
		setToolTipText(tooltip);
		
		setOpaque(true);
		setForeground(Color.WHITE);
		setContentAreaFilled(false);
		setHorizontalAlignment(JButton.CENTER);
		setVerticalAlignment(JButton.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.WHITE));		
	}
	
	public CustomJButton(
			Rectangle rectangle,
			AbstractAction action, 
			ImageIcon enabled_icon,
			ImageIcon disabled_icon,
			String tooltip
	) {
		this(rectangle, action, enabled_icon, disabled_icon, "", tooltip);
	}
	
	public CustomJButton(
			Rectangle rectangle,
			AbstractAction action,
			ImageIcon enabled_icon,
			String tooltip
	) {
		this(rectangle, action, enabled_icon, null, "", tooltip);
	}
	
	public CustomJButton(
			Rectangle rectangle,
			AbstractAction action,
			String text
	) {
		this(rectangle, action, null, null, text, "");
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(CUSTOM_JBUTTON_WIDTH, CUSTOM_JBUTTON_HEIGHT);
	}
}