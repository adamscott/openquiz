package currentGame;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.ColorManager;
import applicationTools.CustomButton;

public class AnswerHidePanel extends JPanel{
	private static final long serialVersionUID = -8279662349368548648L;
	private CustomButton btnShowAnswer;
	
	public AnswerHidePanel() {
		
		btnShowAnswer = new CustomButton("Afficher la r�ponse", new Color(255,255,255), new Color(200,200,200),  null, ButtonManager.getTextButtonDimension());
		initGUI();
	}
	
	private void initGUI(){
		setLayout(new MigLayout("", "[grow,center]", "[grow,center]"));
		setBackground(ColorManager.getTextPaneColor());
		setOpaque(false);
		add(btnShowAnswer, "cell 0 0");
	}
	
	public void registerActionsListenerToShowAnswerButton(ActionListener actionListener){
		btnShowAnswer.addActionListener(actionListener);
	}

}
