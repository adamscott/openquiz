package applicationTools;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.Painter;

public class ButtonPainter {
	
	private Painter<JButton> defaultPainter;
	private Painter<JButton> mouseOverPainter;
	private Painter<JButton> mousePressedPainter;
	private Color defaultColor;
	private Color mouseOverColor;
	private Color mousePressedColor;
	
	public ButtonPainter(Color _defaultColor, Color _mouseOverColor, Color _mousePressedColor){
		defaultColor = _defaultColor;
		mouseOverColor = _mouseOverColor;
		mousePressedColor = _mousePressedColor;
	}
	
	public void generatePainter()
	{
		defaultPainter = new Painter<JButton>()
		{
			public void paint(Graphics2D g, JButton c, int height, int width) {
				g.setColor(defaultColor);
				g.fillRect(0, 0, height, width);
				g.setColor(mousePressedColor);
				g.drawRect(0, 0, height-1, width-1);
			}
		};
		
		mouseOverPainter = new Painter<JButton>()
		{
			public void paint(Graphics2D g, JButton c, int height, int width) {
				g.setColor(mouseOverColor);
				g.fillRect(0, 0, height, width);
				g.setColor(mousePressedColor);
				g.drawRect(0, 0, height-1, width-1);
			}
		};
		
		mousePressedPainter = new Painter<JButton>()
		{
			public void paint(Graphics2D g, JButton c, int height, int width) {
				g.setColor(mousePressedColor);
				g.fillRect(0, 0, height, width);
				g.setColor(mousePressedColor);
				g.drawRect(0, 0, height-1, width-1);
			}
		};
	}

	public Painter<JButton> getDefaultPainter() {
		return defaultPainter;
	}
	
	public Painter<JButton> getMouseOverPainter() {
		return mouseOverPainter;
	}
	
	public Painter<JButton> getMousePressedPainter() {
		return mousePressedPainter;
	}
}
