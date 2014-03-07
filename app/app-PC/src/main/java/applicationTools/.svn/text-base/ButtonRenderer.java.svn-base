package applicationTools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends CustomButton implements TableCellRenderer 
{
	private static final long serialVersionUID = -1557073148854687239L;
	private Color defaultButtonColor;
	private Color mouseOverButtonColor;
	
	public ButtonRenderer(String strLabel, Color defaultColor, Color mouseOverColor, ImageIcon _imageIcon, Dimension _btnDimension) {
		super(strLabel, defaultColor, mouseOverColor,_imageIcon, _btnDimension);
		defaultButtonColor = defaultColor;
		mouseOverButtonColor = mouseOverColor;
	}

	public ButtonRenderer(String strLabel, ImageIcon _imageIcon, Dimension _btnDimension) {
		super(strLabel, _imageIcon, _btnDimension);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	{
		if (isSelected) 
		{
			//setForeground(table.getSelectionForeground());
			//setBackground(defaultButtonColor);
		} 
		else 
		{
			//setForeground(table.getSelectionForeground());
			//setBackground(mouseOverButtonColor);
		}	
		//setText((value == null) ? "" : value.toString());
		if (imageIcon != null) {
			//setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(20, -1,java.awt.Image.SCALE_SMOOTH)));
		}
		//setIcon(IconMgr.resizeIcon(btnImageIcon, 10, 10, 30, 30));
		return this;
	}
}