package reportQuestion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.ReportReason;
import ca.openquiz.comms.model.Question;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.FontManager;
import applicationTools.LoginManager;
import applicationTools.PageManager;
import applicationTools.QuestionManager;
import customWindow.CustomDialog;

public class ReportQuestionView extends CustomDialog implements IReportQuestionView {
	
	private JLabel lblReportQuestionTitle;
	private JComboBox<String> comboBoxProblemEncountered;
	private JLabel lblMoreInfo;
	private JTextPane txtpnMoreInfo;
	private CustomButton btnSaveReport;
	private HashMap<ReportReason, String> reportReasonMap;

	public ReportQuestionView() 
	{
		reportReasonMap = new HashMap<ReportReason, String>();
		initGUI();
	}
	
	private void initGUI() 
	{
		reportReasonMap.put(ReportReason.IncorrectAnswer, "Réponse incorrecte");
		reportReasonMap.put(ReportReason.TooEasy, "Question trop facile");
		reportReasonMap.put(ReportReason.TooHard, "Question trop difficile");
		reportReasonMap.put(ReportReason.Typo, "Orthographe incorrecte");
		
		panelContent.setLayout(new MigLayout("", "[grow]", "[][20px:n:20px][][][20px:n:20px][][grow][]"));
		
		lblReportQuestionTitle = new JLabel("Signaler une erreur");
		lblReportQuestionTitle.setFont(FontManager.getPopUpWindowTitle());
		panelContent.add(lblReportQuestionTitle, "cell 0 0,alignx center");
		
		JLabel lblProblemEncountered = new JLabel("Problème rencontré");
		lblProblemEncountered.setFont(FontManager.getPopUpWindowSubTitle());
		panelContent.add(lblProblemEncountered, "cell 0 2");
		
		comboBoxProblemEncountered = new JComboBox<String>();
		comboBoxProblemEncountered.setModel(new DefaultComboBoxModel<String>(new String[] 
										   {reportReasonMap.get(ReportReason.IncorrectAnswer),
											reportReasonMap.get(ReportReason.TooEasy),
											reportReasonMap.get(ReportReason.TooHard),
											reportReasonMap.get(ReportReason.Typo),}));
		
		panelContent.add(comboBoxProblemEncountered, "cell 0 3,growx");
		
		lblMoreInfo = new JLabel("Information additionnelle");
		lblMoreInfo.setFont(FontManager.getPopUpWindowSubTitle());
		panelContent.add(lblMoreInfo, "cell 0 5");
		
		txtpnMoreInfo = new JTextPane();
		panelContent.add(txtpnMoreInfo, "flowy,cell 0 6,grow");
		
		btnSaveReport = new CustomButton("Signaler", null, ButtonManager.getTextButtonDimension());
		btnSaveReport.setToolTipText("Alt+Enter");
		btnSaveReport.setMnemonic(KeyEvent.VK_ENTER);
		panelContent.add(btnSaveReport, "cell 0 7,alignx right");
		
		btnSaveReport.addActionListener(reportActionListener);
	}
	
	public CustomButton getBtnSaveReport() {
		return btnSaveReport;
	}

	public void showWindow() {
		getDialogWindow().setLocationRelativeTo(null);
		getDialogWindow().revalidate();
		getDialogWindow().setVisible(true);
	}

	public void closeWindow() {
		getDialogWindow().dispose();
	}

	private ActionListener reportActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Question currentQuestion = QuestionManager.getInstance().getCurrentQuestion();
			currentQuestion.setIsReported(true);
			currentQuestion.setreportComment(txtpnMoreInfo.getText());
			currentQuestion.setReportReason((ReportReason)reportReasonMap.keySet().toArray()[comboBoxProblemEncountered.getSelectedIndex()]);
			RequestsWebService.editQuestion(currentQuestion, LoginManager.getInstance().getAuthorization());
			closeWindow();
		}
	};
}
