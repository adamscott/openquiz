package applicationTools;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableHeaderRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 250580439126781918L;

	@Override
	  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, false, row, col);
		
		l.setForeground(Color.black);
		
		if(row == 0) {
			l.setBackground(ColorManager.getMousePressedColor());
		} 
		else if(isSelected){
			l.setBackground(ColorManager.getMouseOverColor());
		}
		else {
			l.setBackground(ColorManager.getTextPaneColor());
		}
		
		return l;
	};	
}
