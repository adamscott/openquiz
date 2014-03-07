package creationGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.FontManager;
import applicationTools.IconManager;
import customWindow.CustomDialog;

public class TemplateCreationPopUpView extends CustomDialog{
	private CustomButton btnAccept;
	private CustomButton btnCancel;
	private JLabel lblTitle;
	private Box verticalBox;
	private JLabel lblAcceptTemplate;
	
	public TemplateCreationPopUpView() {
		lblTitle = new JLabel("Des erreurs sont survenues lors de la création du modèle:");
		lblTitle.setFont(FontManager.getPopUpWindowTitle());
		
		lblAcceptTemplate = new JLabel("Êtes vous certain de vouloir créer le modèle ?");
		lblAcceptTemplate.setFont(FontManager.getPopUpWindowSubTitle());
		
		verticalBox = Box.createVerticalBox();
		btnAccept = new CustomButton("", IconManager.getGreenCheckIcon(),ButtonManager.getIconButtonDimension());
		btnCancel = new CustomButton("", IconManager.getCloseXIcon(),ButtonManager.getIconButtonDimension());
		
		initGui();
	}
	
	private void initGui(){
		panelContent.setLayout(new MigLayout("", "[][][grow]", "[][grow][][]"));
		
		panelContent.add(lblTitle, "cell 0 0 3 1, alignx center");
		panelContent.add(verticalBox, "cell 0 1 3 1,aligny top");
		panelContent.add(lblAcceptTemplate, "cell 0 2");
		panelContent.add(btnAccept, "flowx,cell 0 3");
		panelContent.add(btnCancel, "cell 0 3");
		
		btnCancel.addActionListener(actionListenerClosePopUp);
	}
	
	public CustomButton getBtnAcceptTemplate(){
		return this.btnAccept;
	}
	
	public void showWindow(){
		getDialogWindow().setLocationRelativeTo(null);
		getDialogWindow().revalidate();
		getDialogWindow().setVisible(true);
	}
	
	public void clearErrorList(){
		verticalBox.removeAll();
	}

	public void setErrorList(List<String> errorList) {
		clearErrorList();
		for (String error : errorList){
			verticalBox.add(new JLabel(error));
		}
	}
	
	private ActionListener actionListenerClosePopUp = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			closeWindow();
		}
	};
	
	public void closeWindow(){
		dialogWindow.dispose();
	}

}
