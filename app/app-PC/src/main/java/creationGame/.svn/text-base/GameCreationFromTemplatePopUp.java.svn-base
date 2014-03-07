package creationGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.IconManager;
import customWindow.CustomDialog;

public class GameCreationFromTemplatePopUp extends CustomDialog {
	private CustomButton btnAccept;
	private CustomButton btnClose;
	private JLabel lblTitle;
	private Box verticalBox;
	
	public GameCreationFromTemplatePopUp(){
		lblTitle = new JLabel("Erreur pendant la création de partie.");
		verticalBox = Box.createVerticalBox();
		btnAccept = new CustomButton("", IconManager.getGreenCheckIcon(),ButtonManager.getIconButtonDimension());
		btnClose = new CustomButton("", IconManager.getCloseXIcon(),ButtonManager.getIconButtonDimension());
		
		initGui();
	}
	
	private void initGui(){
		panelContent.setLayout(new MigLayout("", "[][][grow]", "[][grow][]"));
		
		panelContent.add(lblTitle, "cell 0 0 3 1");
		panelContent.add(verticalBox, "cell 0 1 3 1,aligny top");
		panelContent.add(btnAccept, "cell 0 2");
		panelContent.add(btnClose, "cell 1 2");
		
		btnClose.addActionListener(actionListenerClose);
	}
	
	public void closeWindow(){
		getDialogWindow().dispose();
	}
	
	private ActionListener actionListenerClose = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			closeWindow();
		}
	};
	
	public void showWindow(){
		getDialogWindow().setLocationRelativeTo(null);
		getDialogWindow().revalidate();
		getDialogWindow().setVisible(true);
	}

	public void removeErrors(){
		verticalBox.removeAll();
	}
	public void setErrors(List<String> errors) {
		removeErrors();
		
		for (String error : errors){
			verticalBox.add(new JLabel(error));
		}
		
	}
	
	public CustomButton getBtnAccept(){
		return this.btnAccept;
	}

}
