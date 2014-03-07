package creationGame;

import javax.swing.Box;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import applicationTools.FontManager;
import customWindow.CustomDialog;

public class GameCreationCheckPopUp extends CustomDialog{
	private JLabel lblTitle;
	private Box verticalBox;
	
	public GameCreationCheckPopUp(){
		lblTitle = new JLabel("Une erreur s'est produite lors de la création de la partie:");
		lblTitle.setFont(FontManager.getPopUpWindowTitle());
		
		verticalBox = Box.createVerticalBox();
		
		initGui();
	}
	
	private void initGui(){
		panelContent.setLayout(new MigLayout("", "[][]", "[][grow]"));
		
		panelContent.add(lblTitle, "cell 0 0 2 1, alignx center");
		panelContent.add(verticalBox, "cell 0 1 2 1,aligny top");
	}
	
	public void showWindow(){
		getDialogWindow().setLocationRelativeTo(null);
		getDialogWindow().revalidate();
		getDialogWindow().setVisible(true);
	}
	
	public void clearError(){
		verticalBox.removeAll();
	}
	
	public void setError(String error){
		clearError();
		verticalBox.add(new JLabel(error));
	}

}
