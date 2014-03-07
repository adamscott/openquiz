package currentGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.FontManager;
import applicationTools.MultilineLabel;

public class IdentificationQuestionPanel extends QuestionAnswerTopPanel {
	private static final long serialVersionUID = -1510981925327421768L;
	private CustomButton btnNextClue;
	private ArrayList<JComponent> statementsList = new ArrayList<JComponent>();
	private int currentClue = 1;
	
	public IdentificationQuestionPanel() 
	{
		btnNextClue = new CustomButton("Prochain indice", new Color(220,220,220), new Color(200,200,200), null, ButtonManager.getTextButtonDimension());
		btnNextClue.addActionListener(actionListenerNextClue);
		
		lblQuestionType.setText("Question à indices multiples");
		hideHintPanel();
	}
	
	private ActionListener actionListenerNextClue = (new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			// TODO : lower questionvalue
			
			if(currentClue < statementsList.size()){
				currentClue++;
				statementsList.get(currentClue).setVisible(true);
			}
			
			panelQuestion.getParent().getParent().getParent().repaint(); // repaints view.panelContent
		}
	});

	@Override
	public void setQuestionList(List<String> list, String question) {
		String pos = "";
		currentClue = 0;
		
		panelQuestion.removeAll();
		statementsList.clear();
		
		JLabel lblQuestion = new JLabel("Question");
		lblQuestion.setFont(FontManager.getQuestionZoneSubTitle());
		pos = "cell 0 0";
		panelQuestion.add(lblQuestion, pos);

		pos = "cell 0 1";
		panelQuestion.add(btnNextClue, pos);
		
		for(String s : list){
			JPanel statementPanelTemp = MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText(s, panelQuestion.getWidth(), FontManager.getQuestionZoneSubItem()), 0, 0, false);
			statementsList.add(statementPanelTemp);
		}
		
		panelQuestion.add(MultilineLabel.getComponentListInPanel(statementsList, panelQuestion.getWidth(), panelQuestion.getHeight()-lblQuestion.getHeight(), true), "cell 0 2");
		
		//TODO - Sketchy as fuck
		for(JComponent statement : statementsList){		
			if(statementsList.indexOf(statement) > 0){
				statement.setVisible(false);
			}
		}	
	}
}
