package currentGame;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import applicationTools.VideoPlayer;

public class VideoPlayerPanel extends MediaPlayerBasePanel{
	private static final long serialVersionUID = 1L;
	
	private VideoPlayer videoPlayer = null;
	private JLabel lblCurrentTime;
	private JProgressBar videoProgressBar;
	private JLabel lblTotalTime;
	
	public VideoPlayerPanel(){
		
		lblCurrentTime = new JLabel("--:--");
		lblCurrentTime.setHorizontalAlignment(SwingConstants.RIGHT);
		videoProgressBar = new JProgressBar();
		lblTotalTime = new JLabel("--:--");
		initGui();
	}
	
	private void initGui(){
		//JPanel mediaContentPanel = this;
		videoProgressBar.setMinimum(0);
		Dimension dim = new Dimension(400,25);
		videoProgressBar.setMinimumSize(dim);
		videoProgressBar.setMaximumSize(dim);
		videoProgressBar.setSize(dim);
		
		lblTotalTime.setOpaque(false);
		lblCurrentTime.setOpaque(false);
		
		Dimension lblDim = new Dimension(50,25);
		lblTotalTime.setMinimumSize(lblDim);
		lblTotalTime.setMaximumSize(lblDim);
		lblTotalTime.setPreferredSize(lblDim);
		lblCurrentTime.setMinimumSize(lblDim);
		lblCurrentTime.setMaximumSize(lblDim);
		lblCurrentTime.setPreferredSize(lblDim);
		
		lblCurrentTime.setAlignmentX(RIGHT_ALIGNMENT);
		
		mediaContentPanel.setLayout(new MigLayout("", "[grow][][grow]", "[grow]"));

		mediaContentPanel.add(lblTotalTime, "cell 2 1,alignx left");
		mediaContentPanel.add(videoProgressBar, "cell 1 1,alignx center");
		mediaContentPanel.add(lblCurrentTime, "cell 0 1,alignx right");
		
		mediaContentPanel.validate();
		mediaContentPanel.setVisible(true);
	}
	
	public void setVideoPlayer(VideoPlayer videoPlayer){
		this.videoPlayer = videoPlayer;
	}
	
	public void showVideoPlayer(int width, int height){
		Dimension dim = new Dimension(width-100, height-110);
		System.out.println(width + " " + height);
		videoPlayer.setMinimumSize(dim);
		videoPlayer.setMaximumSize(dim);
		mediaContentPanel.add(videoPlayer, "cell 0 0 3 1,alignx center,aligny center");
		
		mediaContentPanel.validate();
		mediaContentPanel.repaint();
	}
	
	public VideoPlayer getVideoPlayer(){
		return this.videoPlayer;
	}
	
	public JProgressBar getVideoProgressBar(){
		return this.videoProgressBar;
	}
	
	public void setCurrentVideoTime(long seconds){
		long hours = seconds / 3600;
		long min = (seconds / 60) % 60;
		long sec = seconds % 60;
		
		String time = "";
		if (hours > 0){
			if (hours < 10){
				time += "0";
			}
			time = time + hours + ":";
		}
		if (min < 10){
			time += "0";
		}
		time = time + min + ":";
		if (sec < 10){
			time += "0";
		}
		time = time + sec;
		lblCurrentTime.setText(time);
	}
	
	public void setTotalVideoTime(long seconds){
		long hours = seconds / 3600;
		long min = (seconds / 60) % 60;
		long sec = seconds % 60;
		
		String time = "";
		if (hours > 0){
			if (hours < 10){
				time += "0";
			}
			time = time + hours + ":";
		}
		if (min < 10){
			time += "0";
		}
		time = time + min + ":";
		if (sec < 10){
			time += "0";
		}
		time = time + sec;
		lblTotalTime.setText(time);
	}

	public void setProjectorMode(){
		super.setProjectorMode();
	}
}
