package applicationTools;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class ButtonEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -9151724893454751983L;
	private ButtonRenderer button;
	private ImageIcon imageIconQuit = new ImageIcon("img/icons/gear.png");
	private String label;
	private boolean isPushed;
	private ImageIcon btnImageIcon;

	public ButtonEditor(JCheckBox _checkBox, ImageIcon _imageIcon, ButtonRenderer _button) 
	{
		super(_checkBox);
		btnImageIcon = _imageIcon;
	    button = _button;
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		fireEditingStopped();
	    	}
	    });
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
	{
	    label = (value == null) ? "" : value.toString();
	    //button.setText(label);
	    button.setIcon(new ImageIcon(btnImageIcon.getImage().getScaledInstance(20, -1,java.awt.Image.SCALE_SMOOTH)));
	    isPushed = true;
	    return button;
	  }

	  //Action handler for button pushed
	  public Object getCellEditorValue() 
	  {
		  if (isPushed) 
		  {

		  }
		  isPushed = false;
		  return new String(label);
	  }

	  public boolean stopCellEditing() 
	  {
		  isPushed = false;
		  return super.stopCellEditing();
	  }

	  protected void fireEditingStopped() 
	  {
		  super.fireEditingStopped();
	  }
}