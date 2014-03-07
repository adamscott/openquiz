package currentGame;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class AudioPlayerPanel extends MediaPlayerBasePanel{
	private static final long serialVersionUID = 1L;
	
	private JProgressBar audioProgressBar;
	private JLabel lblCurrentSoundTime;
	private JLabel lblTotalSoundTime;
	
	public AudioPlayerPanel(){
		audioProgressBar = new JProgressBar();
		lblCurrentSoundTime = new JLabel("--:--");
		lblCurrentSoundTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalSoundTime = new JLabel("--:--");
		
		initGui();
	}
	
	private void initGui(){
		//JPanel mediaContentPanel = this;
		audioProgressBar.setMinimum(0);
		Dimension dim = new Dimension(400,25);
		audioProgressBar.setMinimumSize(dim);
		audioProgressBar.setMaximumSize(dim);
		audioProgressBar.setSize(dim);
		
		lblTotalSoundTime.setOpaque(false);
		lblCurrentSoundTime.setOpaque(false);
		
		Dimension lblDim = new Dimension(50,25);
		lblCurrentSoundTime.setMinimumSize(lblDim);
		lblCurrentSoundTime.setMaximumSize(lblDim);
		lblCurrentSoundTime.setPreferredSize(lblDim);
		lblTotalSoundTime.setMinimumSize(lblDim);
		lblTotalSoundTime.setMaximumSize(lblDim);
		lblTotalSoundTime.setPreferredSize(lblDim);
		
		mediaContentPanel.setLayout(new MigLayout("", "[grow][][grow]", "[grow]"));
		
		mediaContentPanel.add(lblTotalSoundTime, "cell 2 1,alignx left");
		mediaContentPanel.add(audioProgressBar, "cell 1 1,alignx center");
		mediaContentPanel.add(lblCurrentSoundTime, "cell 0 1,alignx right");

		mediaContentPanel.validate();
		mediaContentPanel.setVisible(true);
		
	}
	
	public JProgressBar getProgressBarAudio(){
		return this.audioProgressBar;
	}
	
	public JLabel getLblCurrentSoundTime(){
		return this.lblCurrentSoundTime;
	}
	
	public JLabel getLblTotalSoundTime(){
		return this.lblTotalSoundTime;
	}
	
	public void setCurrentSoundTime(int seconds){
		int min = seconds / 60;
		int sec = seconds % 60;
		if (sec < 10){
			this.lblCurrentSoundTime.setText(min + ":0" + sec);
		}
		else {
			this.lblCurrentSoundTime.setText(min + ":" + sec);
		}
	}
	
	public void setTotalTrackTime(int seconds){
		int min = seconds / 60;
		int sec = seconds % 60;
		if (sec < 10){
			this.lblTotalSoundTime.setText(min + ":0" + sec);
		}
		else {
			this.lblTotalSoundTime.setText(min + ":" + sec);
		}	
	}
	
	public void setProjectorMode(){
		super.setProjectorMode();
	}
}
