package applicationTools;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIDefaults;

public class CustomButton extends JButton {
	private static final long serialVersionUID = 3984028032731958393L;
	protected ImageIcon imageIcon = null;
	protected Dimension btnDimension = ButtonManager.getIconButtonDimension();
	
	public CustomButton(String strLabel, Color defaultColor, Color mouseOverColor, ImageIcon _imageIcon, Dimension _btnDimension){
		setText(strLabel);
		imageIcon = _imageIcon;
		btnDimension = _btnDimension;
		setStyle(defaultColor, mouseOverColor, mouseOverColor);
	}
	
	public CustomButton(String strLabel, ImageIcon _imageIcon, Dimension _btnDimension){
		setText(strLabel);
		imageIcon = _imageIcon;
		btnDimension = _btnDimension;
		setStyle(ColorManager.getMouseDefaultColor(), ColorManager.getMouseOverColor(), ColorManager.getMousePressedColor());
	}
	
	private void setStyle(Color defaultColor, Color mouseOverColor, Color mousePressedColor){		
		ButtonPainter butPainter = new ButtonPainter(defaultColor, mouseOverColor, mousePressedColor);
		butPainter.generatePainter();
		
		UIDefaults def = new UIDefaults();
		def.put("Button[Default].backgroundPainter", butPainter.getDefaultPainter());
		def.put("Button[Enabled].backgroundPainter", butPainter.getDefaultPainter());
		def.put("Button[Disabled].backgroundPainter", butPainter.getMousePressedPainter());
		def.put("Button[Focused].backgroundPainter", butPainter.getDefaultPainter());
		def.put("Button[MouseOver].backgroundPainter", butPainter.getMouseOverPainter());
		def.put("Button[Focused+Pressed].backgroundPainter", butPainter.getMousePressedPainter());
		def.put("Button[Focused+MouseOver].backgroundPainter", butPainter.getMousePressedPainter());
		this.putClientProperty("Nimbus.Overrides", def);
		
		setOpaque(false);
		setMaximumSize(btnDimension);
		setMinimumSize(btnDimension);
		if (imageIcon != null) {
			setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance((int)btnDimension.getWidth()-10, -1,java.awt.Image.SCALE_SMOOTH)));
		}
	}
}
