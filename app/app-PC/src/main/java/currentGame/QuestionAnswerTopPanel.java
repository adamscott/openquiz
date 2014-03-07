package currentGame;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.ColorManager;
import applicationTools.CustomButton;
import applicationTools.FontManager;
import applicationTools.IconManager;
import applicationTools.MultilineLabel;
import applicationTools.RepaintWindowManager;
import applicationTools.ScoreManager;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.model.Category;

public abstract class QuestionAnswerTopPanel extends JPanel {
	private static final long serialVersionUID = -123151953043024605L;
	protected JPanel panelQuestion;
	protected JPanel panelHint;
	protected JPanel panelAnswerCardLayout;
	protected JPanel panelAnswer;
	protected AnswerHidePanel panelHideAnswer;
	protected JPanel panelMedia;
	protected JPanel topPanel;
	protected JPanel bottomPanel;
	
	protected JLabel lblDescription;
	protected JLabel lblAnswerLabel;
	protected JLabel lblFieldQuestionValue;
	protected JLabel lblQuestionType;
	protected JLabel lblQuestionCategory;
	protected CustomButton btnReportQuestion;
	protected JSeparator separator;
	protected JLabel lblQuestionIdAndTotal;
	
	public QuestionAnswerTopPanel() 
	{	
		panelAnswerCardLayout = new JPanel();
		panelQuestion = new JPanel();
		panelHint = new JPanel();
		panelAnswer = new JPanel();
		panelHideAnswer = new AnswerHidePanel();
		panelMedia = new JPanel();
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		
		initGUI();
	}
	
	private void initGUI()
	{
		setBackground(ColorManager.getTextPaneColor());
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		
		topPanel.setBackground(ColorManager.getTextPaneColor());
		topPanel.setLayout(new MigLayout("", "[33%][33%][33%]", "[][]"));
		
		lblFieldQuestionValue = new JLabel("20");
		lblFieldQuestionValue.setFont(FontManager.getQuestionZoneSubItem());
		topPanel.add(lblFieldQuestionValue, "cell 0 1, align left");
		
		JLabel lblQuestionValue = new JLabel("points");
		lblQuestionValue.setFont(FontManager.getQuestionZoneSubItem());
		topPanel.add(lblQuestionValue, "cell 0 1, align left");
		
		lblQuestionType = new JLabel("Type de question");
		lblQuestionType.setFont(FontManager.getQuestionZoneMainLabel());
		topPanel.add(lblQuestionType, "cell 0 0 3 1,alignx center");
		
		lblQuestionCategory = new JLabel("Catégorie");
		lblQuestionCategory.setFont(FontManager.getQuestionZoneSubItem());
		topPanel.add(lblQuestionCategory, "cell 2 1, align right");
		
		lblDescription = new JLabel("Description");
		lblDescription.setFont(FontManager.getQuestionZoneSubItem());
		topPanel.add(lblDescription, "cell 1 1, align center");
		
		btnReportQuestion = new CustomButton("", IconManager.getWarningYellowIcon(), ButtonManager.getIconButtonDimension());
		btnReportQuestion.setToolTipText("Signaler une erreur (Alt+E)");
		btnReportQuestion.setMnemonic(KeyEvent.VK_E);
		//topPanel.add(btnReportQuestion, "cell 3 0, alignx right");

		c.gridy = 0;
		add(topPanel, c);
		
		panelQuestion.setBackground(ColorManager.getTextPaneColor());
		panelQuestion.setLayout(new MigLayout("", "[grow]", "[]"));
		
		c.weighty = 5.0;
		c.gridy = 1;
		add(panelQuestion, c);
		
		c.weighty = 2.0;
		c.gridy = 2;
		panelHint.setBackground(ColorManager.getTextPaneColor());
		panelHint.setLayout(new MigLayout("", "[grow]", "[]"));
		add(panelHint, c);
						
		separator = new JSeparator();
		separator.setBackground(ColorManager.getMouseOverColor());
		
		c.weighty = 0.0;
		c.gridy = 4;
		add(separator, c);

		panelAnswerCardLayout.setLayout(new CardLayout());
		panelAnswerCardLayout.add(panelAnswer, panelAnswer.getClass().toString());
		panelAnswerCardLayout.add(panelHideAnswer, panelHideAnswer.getClass().toString());
				
		panelAnswerCardLayout.setBackground(ColorManager.getTextPaneColor());
		
		panelAnswer.setBackground(ColorManager.getTextPaneColor());
		panelAnswer.setLayout(new MigLayout("", "[grow]", "[]"));
		
		c.weighty = 2.0;
		c.gridy = 4;
		add(panelAnswerCardLayout, c);
		
		panelMedia.setBackground(ColorManager.getTextPaneColor());
		
		c.weighty = 0.5;
		c.gridy = 5;
		add(panelMedia, c);
		
		bottomPanel.setBackground(ColorManager.getTextPaneColor());
		bottomPanel.setLayout(new MigLayout("", "[grow]0[grow]","[]"));
		
		lblQuestionIdAndTotal = new JLabel("0/0");
		bottomPanel.add(lblQuestionIdAndTotal, "cell 0 0, alignx left, aligny bottom");
		bottomPanel.add(btnReportQuestion, "cell 1 0, alignx right, aligny bottom");
		
		c.weighty = 0.0;
		c.gridy = 6;
		add(bottomPanel, c);
		
		panelHideAnswer.registerActionsListenerToShowAnswerButton(actionListenerShowAnswer);
		
		panelQuestion.setMinimumSize(panelQuestion.getSize());
		panelQuestion.setPreferredSize(panelQuestion.getSize());
		panelQuestion.setMaximumSize(panelQuestion.getSize());
		
		panelHint.setMinimumSize(panelHint.getSize());
		panelHint.setPreferredSize(panelHint.getSize());
		panelHint.setMaximumSize(panelHint.getSize());

		panelAnswer.setMinimumSize(panelAnswer.getSize());
		panelAnswer.setPreferredSize(panelAnswer.getSize());
		panelAnswer.setMaximumSize(panelAnswer.getSize());
	}
	
	private ActionListener actionListenerShowAnswer = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			showAnswer();
		}
	};
	
	public CustomButton getBtnReportQuestion() {
		return btnReportQuestion;
	}
	
	public void setQuestionValue(int newScore){
		lblFieldQuestionValue.setText(Integer.toString(newScore));
	}
	
	public void setCategory(Category category){
		if(category != null){
			lblQuestionCategory.setText(CategoryType.toString(category.getType()) + " - " + category.getName());
		}
	}
	
	public void setCategory(String mainCategory, String subCategory){
		lblQuestionCategory.setText(mainCategory + " - " + subCategory);
	}
	
	public int getQuestionValue(){
		return Integer.parseInt(lblFieldQuestionValue.getText());
	}
	
	public void setQuestion(String question){
		String pos = "";
		
		panelQuestion.removeAll();
		
		JLabel lblQuestion = new JLabel("Question");
		lblQuestion.setFont(FontManager.getQuestionZoneSubTitle());
		pos = "cell 0 0";
		panelQuestion.add(lblQuestion, pos);

		pos = "cell 0 1";	
		panelQuestion.add(MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText(question, panelQuestion.getWidth(), FontManager.getQuestionZoneSubItem()), panelQuestion.getWidth(), panelQuestion.getHeight()-lblQuestion.getSize().height, true), pos);
	}
	
	public void setQuestionIDAndTotal(int currentQuestionID, int totalQuestions){
		lblQuestionIdAndTotal.setText(currentQuestionID + "/" + totalQuestions);
	}
	
	public void setHint(String hint){
		panelHint.removeAll();
		
		if(!hint.equals("")){
			String pos = "";
			
			JLabel lblHint = new JLabel("Indice");
			lblHint.setFont(FontManager.getQuestionZoneSubTitle());
			pos = "cell 0 0";
			panelHint.add(lblHint, pos);

			pos = "cell 0 1";
			panelHint.add(MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText(hint, panelHint.getWidth(), FontManager.getQuestionZoneSubItem()), panelHint.getWidth(), panelHint.getHeight()-lblHint.getSize().height, true), pos);
		}
	}
	
	public void setQuestionList(List<String> list, String question){
		ArrayList<JComponent> statementsList = new ArrayList<JComponent>();
		String pos = "";
		
		JPanel panelQuestionTemp = new JPanel();
		panelQuestionTemp.setLayout(new MigLayout("", "[grow]", "[]"));
		
		panelQuestion.removeAll();
		statementsList.clear();
		
		JLabel lblQuestion = new JLabel("Question");
		lblQuestion.setFont(FontManager.getQuestionZoneSubTitle());
		pos = "cell 0 0";
		panelQuestion.add(lblQuestion, pos);
		
		/*pos = "cell 0 1";	
		panelQuestion.add(MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText(question, panelQuestion.getWidth(), FontManager.getQuestionZoneSubItem()), panelQuestion.getWidth(), panelQuestion.getHeight(), true), pos);
		*/
		JPanel statementPanelTemp = MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText(question, panelQuestion.getWidth(), FontManager.getQuestionZoneSubItem()), 0, 0, false);
		statementsList.add(statementPanelTemp);
		
		pos = "cell 0 " + panelQuestionTemp.getComponentCount();
		panelQuestionTemp.add(statementPanelTemp, pos);	
		
		for(String s : list){
			statementPanelTemp = MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText(s, panelQuestion.getWidth(), FontManager.getQuestionZoneSubItem()), 0, 0, false);
			statementsList.add(statementPanelTemp);
			
			pos = "cell 0 " + panelQuestionTemp.getComponentCount();
			panelQuestionTemp.add(statementPanelTemp, pos);	
		}
		
		pos = "cell 0 1";// + panelQuestion.getComponentCount();

		panelQuestion.add(MultilineLabel.getComponentListInPanel(statementsList, panelQuestion.getWidth(), panelQuestion.getHeight()-lblQuestion.getHeight(), true), pos);
	}
	
	public void setAnswer(String answer){
		String pos = "";
		
		panelAnswer.removeAll();
		
		JLabel lblAnswer = new JLabel("Réponse");
		lblAnswer.setFont(FontManager.getQuestionZoneSubTitle());
		pos = "cell 0 0";
		panelAnswer.add(lblAnswer, pos);
		pos = "cell 0 1";
				
		panelAnswer.add(MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText(answer, panelAnswer.getWidth(), FontManager.getQuestionZoneSubItem()), panelAnswer.getWidth(), panelAnswer.getHeight()-lblAnswer.getHeight(), true), pos);

		showAnswerForTrainingMode();
	}
	
	public void setAnswerList(List<String> list){		
		ArrayList<JComponent> statementsList = new ArrayList<JComponent>();
		String pos = "";
		
		JPanel panelAnswerTemp = new JPanel();
		panelAnswerTemp.setLayout(new MigLayout("", "[grow]", "[]"));
		
		panelAnswer.removeAll();
		statementsList.clear();
		
		JLabel lblAnswer = new JLabel("Réponse");
		lblAnswer.setFont(FontManager.getQuestionZoneSubTitle());
		pos = "cell 0 0";
		panelAnswer.add(lblAnswer, pos);
		
		for(String s : list){
			JPanel statementPanelTemp = MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText(s, panelAnswer.getWidth(), FontManager.getQuestionZoneSubItem()), 0, 0, false);
			statementsList.add(statementPanelTemp);
			
			pos = "cell 0 " + panelAnswerTemp.getComponentCount();
			panelAnswerTemp.add(statementPanelTemp, pos);			
		}

		pos = "cell 0 " + panelAnswer.getComponentCount();
		panelAnswer.add(MultilineLabel.getComponentListInPanel(statementsList, panelAnswer.getWidth(), panelAnswer.getHeight()-lblAnswer.getHeight(), true), pos);
		
		showAnswerForTrainingMode();
	}
	
	private void hideAnswer(){
		((CardLayout)panelAnswerCardLayout.getLayout()).show(panelAnswerCardLayout, panelHideAnswer.getClass().toString());
		RepaintWindowManager.getInstance().repaintCurrentGameViewContent();
	}
	
	private void showAnswer(){
		((CardLayout)panelAnswerCardLayout.getLayout()).show(panelAnswerCardLayout, panelAnswer.getClass().toString());
		RepaintWindowManager.getInstance().repaintCurrentGameViewContent();
	}
	
	protected void hideAnswerPanel(){
		panelAnswerCardLayout.setVisible(false);
		panelAnswerCardLayout.setEnabled(false);
		panelAnswer.setVisible(false);
		panelAnswer.setEnabled(false);
	}
	
	protected void showAnswerPanel(){
		panelAnswerCardLayout.setVisible(true);
		panelAnswerCardLayout.setEnabled(true);
		panelAnswer.setVisible(true);
		panelAnswer.setEnabled(true);
	}
	
	protected void hideHintPanel(){
		panelHint.setVisible(false);
		panelHint.setEnabled(false);
	}
	
	protected void setDescription(String desc){
		this.lblDescription.setText(desc);
	}
	
	/**
	 * Show the answer does not depend if the training mode is set or not.
	 */
	private void showAnswerForTrainingMode(){
		if(!ScoreManager.getInstance().isTrainingModeEnabled()){
			showAnswer();
		}
		else{
			hideAnswer();
		}
	}
}
