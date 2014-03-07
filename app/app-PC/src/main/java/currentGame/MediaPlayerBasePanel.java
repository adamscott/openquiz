package currentGame;

import java.awt.Dimension;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.IconManager;

public class MediaPlayerBasePanel extends QuestionAnswerTopPanel{
	private static final long serialVersionUID = 1L;
	
	protected JPanel mediaContentPanel;
	protected CustomButton btnPlay;
	protected CustomButton btnStop;
	protected CustomButton btnPause;
	protected CustomButton btnNext;
	protected CustomButton btnPrevious;
	
	public MediaPlayerBasePanel() {
		mediaContentPanel = new JPanel();
		btnPlay = new CustomButton("", IconManager.getPlayIcon(), ButtonManager.getIconButtonDimension());
		btnPause = new CustomButton("", IconManager.getPauseIcon(), ButtonManager.getIconButtonDimension());
		btnStop = new CustomButton("", IconManager.getStopIcon(), ButtonManager.getIconButtonDimension());
		btnNext = new CustomButton("", IconManager.getNextSongIcon(), ButtonManager.getIconButtonDimension());
		btnPrevious = new CustomButton("", IconManager.getPrevSongIcon(), ButtonManager.getIconButtonDimension());
		
		initGui();
	}
	
	private void initGui(){
		setOpaque(true);
		mediaContentPanel.setOpaque(true);
		panelMedia.setOpaque(true);
		panelMedia.setLayout(new MigLayout("", "[grow][][20px:n:20px][][][][20px:n:20px][][grow]", "[grow][]"));
		panelMedia.add(mediaContentPanel, "cell 0 0 9 1,grow");
		
		panelMedia.add(btnPrevious, "cell 1 1");
		panelMedia.add(btnPlay, "cell 4 1");
		panelMedia.add(btnPause, "cell 3 1");
		panelMedia.add(btnStop, "cell 5 1");
		panelMedia.add(btnNext, "cell 7 1");
	}
	
	public void setMaximumSize(Dimension dim){
		mediaContentPanel.setMaximumSize(dim);
	}
	
	public CustomButton getBtnPlay(){
		return this.btnPlay;
	}
	
	public CustomButton getBtnPause(){
		return this.btnPause;
	}
	
	public CustomButton getBtnStop(){
		return this.btnStop;
	}
	
	public CustomButton getBtnNext(){
		return this.btnNext;
	}
	
	public CustomButton getBtnPrevious(){
		return this.btnPrevious;
	}
	
	public void setProjectorMode(){
		/*panelAnswer.setVisible(false);
		panelQuestion.setVisible(false);
		panelAnswerCardLayout.setVisible(false);
		panelHideAnswer.setVisible(false);
		panelHint.setVisible(false);
		btnReportQuestion.setVisible(false);
		lblQuestionType.setVisible(false);
		lblAnswerLabel.setVisible(false);
		lblQuestionIdAndTotal.setVisible(false);
		lblQuestionValue.setVisible(false);
		separator.setVisible(false);
		lblQuestionCategory.setVisible(false);*/
		this.remove(panelQuestion);
		this.remove(panelAnswer);
		this.remove(panelHideAnswer);
		this.remove(panelAnswerCardLayout);
		this.remove(panelHint);
		this.remove(topPanel);
		this.remove(bottomPanel);
		
		this.remove(lblQuestionType);
		this.remove(lblQuestionIdAndTotal);
		this.remove(lblFieldQuestionValue);
		this.remove(separator);
		this.remove(lblQuestionCategory);
		this.remove(btnReportQuestion);
		panelMedia.remove(btnPrevious);
		panelMedia.remove(btnNext);
		panelMedia.remove(btnPlay);
		panelMedia.remove(btnPause);
		panelMedia.remove(btnStop);
	}
	
	public JPanel getPanelMedia(){
		return this.panelMedia;
	}
	
	/*public JPanel getContentPanel(){
		return this.mediaContentPanel;
	}*/
}
