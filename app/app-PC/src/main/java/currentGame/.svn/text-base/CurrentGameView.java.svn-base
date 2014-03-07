package currentGame;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import reportQuestion.ReportQuestionController;
import reportQuestion.ReportQuestionView;
import structures.PlayerInfo;
import applicationTools.ButtonManager;
import applicationTools.ColorManager;
import applicationTools.Command;
import applicationTools.CustomButton;
import applicationTools.DownloadManager;
import applicationTools.FileManager;
import applicationTools.IconManager;
import applicationTools.ImagePlayer;
import applicationTools.MediaMode;
import applicationTools.OptionsManager;
import applicationTools.PageManager;
import applicationTools.QuestionManager;
import applicationTools.RepaintWindowManager;
import applicationTools.ScoreManager;
import applicationTools.SoundPlayer;
import applicationTools.TeamManager;
import applicationTools.VideoPlayer;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.model.Choice;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionCorrectWord;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionMedia;
import java.awt.Component;
import java.beans.PropertyChangeListener;

public class CurrentGameView implements ICurrentGameView{

	// UI Variable
	private JPanel panelContent;

	private TeamPanel panelLeftTeam;
	private PlayerInfoZonePanel panelLeftPlayer1;
	private PlayerInfoZonePanel panelLeftPlayer2;
	private PlayerInfoZonePanel panelLeftPlayer3;
	private PlayerInfoZonePanel panelLeftPlayer4;
	private TeamPanel panelRightTeam;
	private PlayerInfoZonePanel panelRightPlayer1;
	private PlayerInfoZonePanel panelRightPlayer2;
	private PlayerInfoZonePanel panelRightPlayer3;
	private PlayerInfoZonePanel panelRightPlayer4;
	private CustomButton btnPrevQuestion;
	private CustomButton btnGoodAnswer;
	private CustomButton btnWrongAnswer;
	private CustomButton btnSubstractPoints;
	private CustomButton btnSkipQuestion;
	private JPanel panelQuestion;

	// QuestionPanelZone
	private IdentificationQuestionPanel panelMultipleClueQuestion = new IdentificationQuestionPanel();
	private AnagramQuestionPanel panelAnagramQuestion = new AnagramQuestionPanel();
	private AssociationQuestionPanel panelAssociationQuestion = new AssociationQuestionPanel();
	private GeneralQuestionPanel panelGeneralQuestion = new GeneralQuestionPanel();
	private IntruQuestionPanel panelIntruQuestion = new IntruQuestionPanel();
	private MotJusteQuestionPanel panelMotJusteQuestion = new MotJusteQuestionPanel();
	private EndOfGamePanel endOfGamePanel = new EndOfGamePanel();
	private AudioPlayerPanel panelAudioPlayer = new AudioPlayerPanel();
	private VideoPlayerPanel panelVideoPlayer = new VideoPlayerPanel();
	private ImagePlayerPanel panelImagePlayer = new ImagePlayerPanel();
	private JPanel boutonPanel;

	private HashMap<String, PlayerInfoZonePanel> leftPlayerNamePanelMap;
	private HashMap<String, PlayerInfoZonePanel> rightPlayerNamePanelMap;

	private CurrentGameModel model = null;
	private CurrentGameController controller = null;

	private SoundPlayer soundPlayer;
	private VideoPlayer videoPlayer;
	private ImagePlayer imagePlayer;
	private Timer playbackTimer;
	private Timer repaintTimer;
	
	private boolean isProjectorModeEnabled = false;
	private AtomicInteger eventProcessedCounter = new AtomicInteger(0);
	
	private JLabel lblLogo = new JLabel("");
	private Dimension oldWindowDimension = new Dimension(0,0);

	public CurrentGameView(boolean projectorMode) {
		isProjectorModeEnabled = projectorMode;
		panelContent = new JPanel();
		panelLeftTeam = new TeamPanel("Left");
		panelLeftPlayer1 = new PlayerInfoZonePanel("Left");
		panelLeftPlayer2 = new PlayerInfoZonePanel("Left");
		panelLeftPlayer3 = new PlayerInfoZonePanel("Left");
		panelLeftPlayer4 = new PlayerInfoZonePanel("Left");
		panelRightTeam = new TeamPanel("Right");
		panelRightPlayer1 = new PlayerInfoZonePanel("Right");
		panelRightPlayer2 = new PlayerInfoZonePanel("Right");
		panelRightPlayer3 = new PlayerInfoZonePanel("Right");
		panelRightPlayer4 = new PlayerInfoZonePanel("Right");
		
		btnGoodAnswer = new CustomButton("", IconManager.getGreenCheckIcon(),ButtonManager.getIconButtonDimension());
		btnWrongAnswer = new CustomButton("", IconManager.getCloseXIcon(),ButtonManager.getIconButtonDimension());
		btnSkipQuestion = new CustomButton("", IconManager.getNextArrowIcon(),ButtonManager.getIconButtonDimension());
		btnPrevQuestion = new CustomButton("", IconManager.getBackArrowIcon(), ButtonManager.getIconButtonDimension());
		btnSubstractPoints = new CustomButton("", IconManager.getRemoveIcon(),ButtonManager.getIconButtonDimension());
		
		btnPrevQuestion.setToolTipText("Question précédente (Alt+Flèche gauche)");
		btnPrevQuestion.setMnemonic(KeyEvent.VK_LEFT);

		panelQuestion = new JPanel();
		boutonPanel = new JPanel();

		playbackTimer = new Timer(100, actionListenerTimerTick);
		repaintTimer = new Timer(75, actionListenerRepaintTimerTick);
		
		if (isProjectorModeEnabled){
			setProjectorMode();
		}else{
			repaintTimer.start();
			initGUI();
		}
	}

	private void initGUI() {
		double heightRatio = 0.7;
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		if(isProjectorModeEnabled){
			heightRatio = 0.9;
		}

		Dimension panelTeamDimension = new Dimension((int) (0.16 * screenDimension.getWidth()), (int) (heightRatio * screenDimension.getHeight()));
		panelLeftTeam.setMinimumSize(panelTeamDimension);
		//panelLeftTeam.setMaximumSize(panelTeamDimension);
		//panelLeftTeam.setSize(panelTeamDimension);
		//panelLeftTeam.setPreferredSize(panelTeamDimension);
		
		panelRightTeam.setMinimumSize(panelTeamDimension);
		//panelRightTeam.setMaximumSize(panelTeamDimension);
		//panelRightTeam.setSize(panelTeamDimension);
		//panelRightTeam.setPreferredSize(panelTeamDimension);

		if (!isProjectorModeEnabled){
			Dimension panelPlayerDimension = new Dimension((int) (0.16 * screenDimension.getWidth()), (int) (heightRatio * screenDimension.getHeight()/4.5));
			panelLeftPlayer1.setMaximumSize(panelPlayerDimension);
			panelLeftPlayer2.setMaximumSize(panelPlayerDimension);
			panelLeftPlayer3.setMaximumSize(panelPlayerDimension);
			panelLeftPlayer4.setMaximumSize(panelPlayerDimension);
	
			panelRightPlayer1.setMaximumSize(panelPlayerDimension);
			panelRightPlayer2.setMaximumSize(panelPlayerDimension);
			panelRightPlayer3.setMaximumSize(panelPlayerDimension);
			panelRightPlayer4.setMaximumSize(panelPlayerDimension);
		}
		
		if (!isProjectorModeEnabled){
			Dimension panelQuestionDimension = new Dimension((int) (0.6 * screenDimension.getWidth()),(int) (heightRatio * screenDimension.getHeight()));
			panelQuestion.setMinimumSize(panelQuestionDimension);
		}
		//panelQuestion.setMaximumSize(panelQuestionDimension);
		//panelQuestion.setSize(panelQuestionDimension);
		//panelQuestion.setPreferredSize(panelQuestionDimension);
		//MigLayout panelContentLayout = new MigLayout("fill", "[grow][grow][grow]");

		panelContent.setOpaque(false);
		panelContent.setLayout(new MigLayout("", "[][50px][grow][50px][]", "[grow][]"));

		panelContent.add(panelLeftTeam, "cell 0 0,growy");
		panelContent.add(panelQuestion, "cell 2 0,grow");
		panelContent.add(panelRightTeam, "cell 4 0,growy");
		
		boutonPanel.setOpaque(false);
		boutonPanel.setLayout(new MigLayout("", "[][grow][][grow 75][][grow 75][][grow][]", "[]"));
		
		if (!isProjectorModeEnabled){
			panelContent.add(boutonPanel, "cell 2 1,growx");
		}
		
		boutonPanel.add(btnPrevQuestion, "cell 0 0");
		btnGoodAnswer.setToolTipText("Bonne réponse reçue (Alt+Enter)");
		btnGoodAnswer.setMnemonic(KeyEvent.VK_ENTER);
		boutonPanel.add(btnGoodAnswer, "cell 2 0");
		btnGoodAnswer.addActionListener(actionListenerGoodAnswer);
						
		btnWrongAnswer.setToolTipText("Mauvaise réponse reçue (Alt+X)");
		btnWrongAnswer.setMnemonic(KeyEvent.VK_X);
		boutonPanel.add(btnWrongAnswer, "cell 4 0");
		btnWrongAnswer.addActionListener(actionListenerWrongAnswer);
				
		btnSubstractPoints.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnSubstractPoints.setToolTipText("Soustraire 10 points (Alt+S)");
		btnSubstractPoints.setMnemonic(KeyEvent.VK_S);
		boutonPanel.add(btnSubstractPoints, "cell 6 0");
		btnSubstractPoints.addActionListener(actionListenerSubstractPoint);
		
		btnSkipQuestion.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnSkipQuestion.setToolTipText("Question suivante (Alt+Flèche droite)");
		btnSkipQuestion.setMnemonic(KeyEvent.VK_RIGHT);
		boutonPanel.add(btnSkipQuestion, "cell 8 0");
		btnSkipQuestion.addActionListener(actionListenerSkipQuestion);
		//panelContent.add(panelLeftTeam, "cell 0 0,grow");

		panelQuestion.setLayout(new CardLayout());
		
		//panelContent.add(panelQuestion, "cell 1 0,grow");

		panelQuestion.add(panelGeneralQuestion, panelGeneralQuestion.getClass().toString());
		panelQuestion.add(panelMultipleClueQuestion, panelMultipleClueQuestion.getClass().toString());
		panelQuestion.add(panelAnagramQuestion, panelAnagramQuestion.getClass().toString());
		panelQuestion.add(panelAssociationQuestion, panelAssociationQuestion.getClass().toString());
		panelQuestion.add(panelIntruQuestion, panelIntruQuestion.getClass().toString());
		panelQuestion.add(panelMotJusteQuestion, panelMotJusteQuestion.getClass().toString());
		panelQuestion.add(endOfGamePanel, endOfGamePanel.getClass().toString());
		panelQuestion.add(panelAudioPlayer, panelAudioPlayer.getClass().toString());
		panelQuestion.add(panelVideoPlayer, panelVideoPlayer.getClass().toString());
		panelQuestion.add(panelImagePlayer, panelImagePlayer.getClass().toString());

		panelQuestion.setBackground(ColorManager.getMouseOverColor());
		panelQuestion.setBorder(new LineBorder(ColorManager.getMousePressedColor()));

		//panelContent.add(panelRightTeam, "cell 2 0,grow");

		if (isProjectorModeEnabled){
			panelLeftTeam.add(panelRightPlayer4, "cell 0 2 5 1,grow");
			panelLeftTeam.add(panelRightPlayer3, "cell 0 3 5 1,grow");
			panelLeftTeam.add(panelRightPlayer2, "cell 0 4 5 1,grow");
			panelLeftTeam.add(panelRightPlayer1, "cell 0 5 5 1,grow");
			panelRightTeam.add(panelLeftPlayer4, "cell 0 2 5 1,grow");
			panelRightTeam.add(panelLeftPlayer3, "cell 0 3 5 1,grow");
			panelRightTeam.add(panelLeftPlayer2, "cell 0 4 5 1,grow");
			panelRightTeam.add(panelLeftPlayer1, "cell 0 5 5 1,grow");
		}
		else{
			panelLeftTeam.add(panelLeftPlayer1, "cell 0 2 5 1,grow");
			panelLeftTeam.add(panelLeftPlayer2, "cell 0 3 5 1,grow");
			panelLeftTeam.add(panelLeftPlayer3, "cell 0 4 5 1,grow");
			panelLeftTeam.add(panelLeftPlayer4, "cell 0 5 5 1,grow");
			panelRightTeam.add(panelRightPlayer1, "cell 0 2 5 1,grow");
			panelRightTeam.add(panelRightPlayer2, "cell 0 3 5 1,grow");
			panelRightTeam.add(panelRightPlayer3, "cell 0 4 5 1,grow");
			panelRightTeam.add(panelRightPlayer4, "cell 0 5 5 1,grow");
		}

		
		/*panelContent.add(btnPrevQuestion, "cell 1 2,grow");
		panelContent.add(btnGoodAnswer, "cell 3 2,grow");
		panelContent.add(btnWrongAnswer, "cell 5 2,grow");
		panelContent.add(btnSubstractPoints, "cell 7 2,grow");
		panelContent.add(btnSkipQuestion, "cell 9 2,grow");*/

		RepaintWindowManager.getInstance().registerCurrentGameViewContent(panelContent);
		btnPrevQuestion.addActionListener(actionListenerPreviousQuestion);

		panelLeftPlayer1.addMouseListener(mouseListenerPlayerSelected);
		panelLeftPlayer2.addMouseListener(mouseListenerPlayerSelected);
		panelLeftPlayer3.addMouseListener(mouseListenerPlayerSelected);
		panelLeftPlayer4.addMouseListener(mouseListenerPlayerSelected);

		panelRightPlayer1.addMouseListener(mouseListenerPlayerSelected);
		panelRightPlayer2.addMouseListener(mouseListenerPlayerSelected);
		panelRightPlayer3.addMouseListener(mouseListenerPlayerSelected);
		panelRightPlayer4.addMouseListener(mouseListenerPlayerSelected);
		
		panelLeftTeam.addMouseListener(mouseListenerTeamSelected);
		panelRightTeam.addMouseListener(mouseListenerTeamSelected);

		panelGeneralQuestion.getBtnReportQuestion().addActionListener(actionListenerReportQuestion);
		panelAssociationQuestion.getBtnReportQuestion().addActionListener(actionListenerReportQuestion);
		panelAnagramQuestion.getBtnReportQuestion().addActionListener(actionListenerReportQuestion);
		panelMultipleClueQuestion.getBtnReportQuestion().addActionListener(actionListenerReportQuestion);
		panelMotJusteQuestion.getBtnReportQuestion().addActionListener(actionListenerReportQuestion);
		panelIntruQuestion.getBtnReportQuestion().addActionListener(actionListenerReportQuestion);
		
		panelAudioPlayer.getBtnPause().addActionListener(actionListenerPause);
		panelAudioPlayer.getBtnPlay().addActionListener(actionListenerPlay);
		panelAudioPlayer.getBtnStop().addActionListener(actionListenerStop);
		panelAudioPlayer.getBtnNext().addActionListener(actionListenerSkipMedia);
		panelAudioPlayer.getBtnPrevious().addActionListener(actionListenerPreviousMedia);
		
		panelVideoPlayer.getBtnPause().addActionListener(actionListenerPause);
		panelVideoPlayer.getBtnPlay().addActionListener(actionListenerPlay);
		panelVideoPlayer.getBtnStop().addActionListener(actionListenerStop);
		panelVideoPlayer.getBtnNext().addActionListener(actionListenerSkipMedia);
		panelVideoPlayer.getBtnPrevious().addActionListener(actionListenerPreviousMedia);
		panelImagePlayer.getBtnPlay().addActionListener(actionListenerPlay);
		panelImagePlayer.getBtnNext().addActionListener(actionListenerSkipMedia);
		panelImagePlayer.getBtnPrevious().addActionListener(actionListenerPreviousMedia);
		
		panelLeftPlayer1.getBtnDecreasePoints().addActionListener(actionListenerDecreasePlayerAnswer);
		panelLeftPlayer2.getBtnDecreasePoints().addActionListener(actionListenerDecreasePlayerAnswer);
		panelLeftPlayer3.getBtnDecreasePoints().addActionListener(actionListenerDecreasePlayerAnswer);
		panelLeftPlayer4.getBtnDecreasePoints().addActionListener(actionListenerDecreasePlayerAnswer);
		panelLeftPlayer1.getBtnIncreasePoints().addActionListener(actionListenerIncreasePlayerAnswer);
		panelLeftPlayer2.getBtnIncreasePoints().addActionListener(actionListenerIncreasePlayerAnswer);
		panelLeftPlayer3.getBtnIncreasePoints().addActionListener(actionListenerIncreasePlayerAnswer);
		panelLeftPlayer4.getBtnIncreasePoints().addActionListener(actionListenerIncreasePlayerAnswer);
		
		panelRightPlayer1.getBtnDecreasePoints().addActionListener(actionListenerDecreasePlayerAnswer);
		panelRightPlayer2.getBtnDecreasePoints().addActionListener(actionListenerDecreasePlayerAnswer);
		panelRightPlayer3.getBtnDecreasePoints().addActionListener(actionListenerDecreasePlayerAnswer);
		panelRightPlayer4.getBtnDecreasePoints().addActionListener(actionListenerDecreasePlayerAnswer);
		panelRightPlayer1.getBtnIncreasePoints().addActionListener(actionListenerIncreasePlayerAnswer);
		panelRightPlayer2.getBtnIncreasePoints().addActionListener(actionListenerIncreasePlayerAnswer);
		panelRightPlayer3.getBtnIncreasePoints().addActionListener(actionListenerIncreasePlayerAnswer);
		panelRightPlayer4.getBtnIncreasePoints().addActionListener(actionListenerIncreasePlayerAnswer);
		
		panelLeftTeam.getBtnIncreasePoints().addActionListener(actionListenerIncreaseLeftTeamScore);
		panelRightTeam.getBtnIncreasePoints().addActionListener(actionListenerIncreaseRightTeamScore);
		panelLeftTeam.getBtnDecreasePoints().addActionListener(actionListenerDecreaseLeftTeamScore);
		panelRightTeam.getBtnDecreasePoints().addActionListener(actionListenerDecreaseRightTeamScore);

		leftPlayerNamePanelMap = new HashMap<String, PlayerInfoZonePanel>(4);
		rightPlayerNamePanelMap = new HashMap<String, PlayerInfoZonePanel>(4);
		
		endOfGamePanel.getBtnCloseGame().addActionListener(actionListenerCloseGame);
	}
	
	private ActionListener actionListenerCloseGame = (new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.postGameLogs();
			controller.setGameAsEnded();
		}
	});
	
	private ActionListener actionListenerIncreaseLeftTeamScore = (new ActionListener() {
		public void actionPerformed(ActionEvent e){
			controller.adjustTeamScoreApp("Left", 1);
		}
	});
	
	private ActionListener actionListenerIncreaseRightTeamScore = (new ActionListener() {
		public void actionPerformed(ActionEvent e){
			controller.adjustTeamScoreApp("Right", 1);
		}
	});
	
	private ActionListener actionListenerDecreaseLeftTeamScore = (new ActionListener() {
		public void actionPerformed(ActionEvent e){
			controller.adjustTeamScoreApp("Left", -1);
		}
	});
	
	private ActionListener actionListenerDecreaseRightTeamScore = (new ActionListener() {
		public void actionPerformed(ActionEvent e){
			controller.adjustTeamScoreApp("Right", -1);
		}
	});
	
	private ActionListener actionListenerDecreasePlayerAnswer = (new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.adjustPlayerScoreForIndividualQuestionApp(-1);
		}
	});
	
	private ActionListener actionListenerIncreasePlayerAnswer = (new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.adjustPlayerScoreForIndividualQuestionApp(1);	
		}
	});
	
	private ActionListener actionListenerSkipMedia = (new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			panelImagePlayer.resetImage();
			controller.skipMedia();
		}
	});

	private ActionListener actionListenerPreviousMedia = (new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			controller.previousMedia();
		}
	});

	private ActionListener actionListenerPlay = (new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			controller.playMediaApp();
		}
	});

	private ActionListener actionListenerPause = (new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			controller.pauseMediaApp();
		}
	});

	private ActionListener actionListenerStop = (new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			controller.stopMediaApp();
		}
	});

	public void playSound() {
		if (isProjectorModeEnabled){
			soundPlayer.setCommand(Command.PLAY);
			playbackTimer.start();
		}
	}

	public void pauseSound() {
		if (isProjectorModeEnabled){
			soundPlayer.setCommand(Command.PAUSE);
			playbackTimer.stop();
		}
	}

	public void stopSound() {
		if (isProjectorModeEnabled){
			soundPlayer.setCommand(Command.STOP);
			playbackTimer.stop();
			resetPlaybackUI();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void nextAudioTrack() {
		stopSound();
		playbackTimer.stop();
		
		if (!isProjectorModeEnabled){
			soundPlayer.loadNextFile();
		}
		resetPlaybackUI();
			
		playSound();
		playbackTimer.start();

		if (soundPlayer.isLastAudioTrack()) {
			panelAudioPlayer.btnNext.setEnabled(false);
		}
		panelAudioPlayer.btnPrevious.setEnabled(true);
	}

	public void previousAudioTrack() {
		stopSound();
		playbackTimer.stop();

		if (!isProjectorModeEnabled){
			soundPlayer.loadPreviousFile();
		}
		resetPlaybackUI();

		playSound();
		playbackTimer.start();

		if (soundPlayer.isFirstAudioTrack()) {
			panelAudioPlayer.btnPrevious.setEnabled(false);
		}
		panelAudioPlayer.btnNext.setEnabled(true);
	}

	public void playVideo() {
		if (isProjectorModeEnabled){
			videoPlayer.play();
		}
		playbackTimer.start();
	}

	public void pauseVideo() {
		if (isProjectorModeEnabled){
			videoPlayer.pause();
		}
		playbackTimer.stop();
	}

	public void stopVideo() {
		if (isProjectorModeEnabled){
			videoPlayer.stop();
		}
		playbackTimer.stop();
		resetPlaybackUI();
	}

	public void previousVideoTrack() {
		if (isProjectorModeEnabled){
			videoPlayer.stop();
		}
		playbackTimer.stop();

		if (isProjectorModeEnabled){
			videoPlayer.loadPreviousVideo();
		}
		resetPlaybackUI();

		if (isProjectorModeEnabled){
			videoPlayer.play();
		}
		playbackTimer.start();

		if (videoPlayer.isFirstVideo()) {
			panelVideoPlayer.btnPrevious.setEnabled(false);
		}
		panelVideoPlayer.btnNext.setEnabled(true);
	}

	public void nextVideoTrack() {
		if (isProjectorModeEnabled){
			videoPlayer.stop();
		}
		playbackTimer.stop();

		if (isProjectorModeEnabled){
			videoPlayer.loadNextVideo();
		}
		resetPlaybackUI();
		
		if (isProjectorModeEnabled){
			videoPlayer.play();
		}
		playbackTimer.start();

		if (videoPlayer.isLastVideo()) {
			panelVideoPlayer.btnNext.setEnabled(false);
		}
		panelVideoPlayer.btnPrevious.setEnabled(true);
		panelContent.repaint();
	}
	
	public void nextImage() {
		if (isProjectorModeEnabled){
			String imagePath = imagePlayer.getNextImage();
	
			if (imagePath != null){
					panelImagePlayer.setImage(imagePath);
			}
		}
		resetPlaybackUI();
		if (imagePlayer.isLastImage()){
			panelImagePlayer.btnNext.setEnabled(false);
		}
		panelImagePlayer.btnPrevious.setEnabled(true);
		panelContent.repaint();
	}
	
	public void showImage(){
		if (isProjectorModeEnabled){
			Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
			panelImagePlayer.setImageWithSize(imagePlayer.getImagePaths().get(0), screenDimension.width, screenDimension.height);
			panelContent.repaint();
		}
	}

	public void previousImage() {
		/*if (isProjectorModeEnabled){
			String imagePath = imagePlayer.getPreviousImage();
			
			if (imagePath != null){
				panelImagePlayer.setImage(imagePath);
			}
		}
		resetPlaybackUI();
		if (imagePlayer.isFirstImage()){
			panelImagePlayer.btnPrevious.setEnabled(false);
		}
		panelImagePlayer.btnNext.setEnabled(true);
		panelContent.repaint();*/
	}

	private void resetPlaybackUI() {
			switch (model.getMediaMode()) {
			case NONE:
				break;
			case PICTURE:
				//panelImagePlayer.setCurrentImageName(imagePlayer.getCurrentImageName());
				break;
			case AUDIO:
				if (isProjectorModeEnabled){
					controller.resetPanelAudioPlayer(soundPlayer.getTotalTrackTimeInMilliseconds());
				}
				break;
			case VIDEO:
				panelVideoPlayer.setTotalVideoTime(videoPlayer.getTotalVideoTimeInMilliseconds() / 1000);
				panelVideoPlayer.setCurrentVideoTime(0);
				panelVideoPlayer.getVideoProgressBar().setValue(0);
				panelVideoPlayer.getVideoProgressBar().setMaximum((int) (videoPlayer.getTotalVideoTimeInMilliseconds() / 100));
			break;
		}
		
		panelContent.repaint();
	}

	private ActionListener actionListenerTimerTick = (new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			float ratio = 0.0f;
			int newValue = 0;
			switch (model.getMediaMode()) {
			case NONE:
				break;
			case PICTURE:
				break;
			case AUDIO:
				// Increase progress bar audio
				if(isProjectorModeEnabled){
					ratio = (float) soundPlayer.getCurrentTrackTimeInMilliseconds() / (float) soundPlayer.getTotalTrackTimeInMilliseconds();
					newValue = (int) (ratio * (soundPlayer.getTotalTrackTimeInMilliseconds() / 100));
					System.out.println(soundPlayer.getCurrentTrackTimeInMilliseconds());
					controller.updatePanelAudioPlayer(newValue, soundPlayer.getCurrentTrackTimeInMilliseconds() / 1000);
				}
				break;
			case VIDEO:
				ratio = (float) videoPlayer.getCurrentVideoTimeInMilliseconds() / (float) videoPlayer.getTotalVideoTimeInMilliseconds();
				newValue = (int) (ratio * (videoPlayer.getTotalVideoTimeInMilliseconds() / 100));
				panelVideoPlayer.getVideoProgressBar().setValue(newValue);
				panelVideoPlayer.setCurrentVideoTime(videoPlayer.getCurrentVideoTimeInMilliseconds() / 1000);
				break;
			}

			panelContent.repaint();
		}
	});
	
	private ActionListener actionListenerRepaintTimerTick = (new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if (eventProcessedCounter.getAndAdd(1) < 3){
				panelContent.repaint();
				System.out.println("Repaint");
			}
		}
	});

	public void highlightSelectedPlayerPanel(String playerName, String playerTeam) {
		model.setHasPlayerPanelBeenClicked(true);
		unselectEveryPlayer();

		if(playerTeam.equals("Left")){
			leftPlayerNamePanelMap.get(playerName).setBackground(ColorManager
					.getPlayerHasAnswerRightDefaultColor());
		}
		else if(playerTeam.equals("Right")){
			rightPlayerNamePanelMap.get(playerName).setBackground(ColorManager
					.getPlayerHasAnswerRightDefaultColor());
		}

		if (QuestionManager.getInstance().getCurrentQuestionTarget() == QuestionTarget.Collectif){
			enableScoresButton();
		}
		panelContent.repaint();
		model.setCurrentlySelectedPlayer(playerName);
		model.setTeamSelected(playerTeam);
	}

	public void repaintPanelContent() {
		panelContent.repaint();
	}
	
	private MouseListener mouseListenerTeamSelected = new MouseListener() {
		public void mouseClicked(MouseEvent e) {
			TeamPanel teamPanel = (TeamPanel)(e.getComponent());
			teamPanel.setBackground(ColorManager.getPlayerMouseOverColor());
			if (model.getIsPlayerSelectionEnabled()) {
				
			} else {
				e.consume();
			}
		}

		public void mouseEntered(MouseEvent e) {
			TeamPanel teamPanel = (TeamPanel)(e.getComponent());
			teamPanel.setBackground(ColorManager.getPlayerMouseOverColor());
			if (model.getIsPlayerSelectionEnabled()) {
				
				panelContent.repaint();
			} else {
				e.consume();
			}
		}

		public void mouseExited(MouseEvent e) {
			TeamPanel teamPanel = (TeamPanel)(e.getComponent());
			teamPanel.setBackground(ColorManager.getPlayerMouseOverColor());
			if (model.getIsPlayerSelectionEnabled()) {
				
				panelContent.repaint();
			} else {
				e.consume();
			}
		}

		public void mousePressed(MouseEvent arg0) {

		}

		public void mouseReleased(MouseEvent arg0) {

		}
	};

	private MouseListener mouseListenerPlayerSelected = new MouseListener() {
		public void mouseClicked(MouseEvent e) {
			if (model.getIsPlayerSelectionEnabled() && !model.getHasPlayerPanelBeenClicked()) {
				JPanel panel = (JPanel)(e.getComponent());
				controller.highlightSelectedPlayerPanelApp((String)panel.getClientProperty("playerName"), (String)panel.getClientProperty("playerTeam"));
			} else {
				e.consume();
			}
		}

		public void mouseEntered(MouseEvent e) {
			if (model.getIsPlayerSelectionEnabled()) {
				JPanel selectedPlayer = (JPanel) (e.getComponent());
				if (selectedPlayer.getBackground() != ColorManager.getPlayerHasAnswerRightDefaultColor()) {
					selectedPlayer.setBackground(ColorManager.getPlayerMouseOverColor());
				}
				panelContent.repaint();
			} else {
				e.consume();
			}
		}

		public void mouseExited(MouseEvent e) {
			if (model.getIsPlayerSelectionEnabled()) {
				JPanel selectedPlayer = (JPanel) (e.getComponent());
				if (!model.getHasPlayerPanelBeenClicked()) {
					if (selectedPlayer.getBackground() != ColorManager.getPlayerHasAnswerRightDefaultColor()) {
						selectedPlayer.setBackground(ColorManager.getPlayerDefaultColor());
					}
				}

				model.setHasPlayerPanelBeenClicked(false);
				panelContent.repaint();
			} else {
				e.consume();
			}
		}

		public void mousePressed(MouseEvent arg0) {

		}

		public void mouseReleased(MouseEvent arg0) {

		}
	};

	public void updateSelectedPlayerZone() {
		if (panelLeftPlayer2.getBackground() == ColorManager.getPlayerHasAnswerRightDefaultColor()) {
			panelLeftPlayer2.setBackground(ColorManager.getPlayerHasAnswerRightTickColor());
		} else {
			panelLeftPlayer2.setBackground(ColorManager.getPlayerHasAnswerRightDefaultColor());
		}
	}

	public JPanel getPlayerPanelFromSideAndName(String side, String playerName) {
		if (side == "Left") {
			if (leftPlayerNamePanelMap.containsKey(playerName)) {
				return leftPlayerNamePanelMap.get(playerName);
			}
		} else if (side == "Right") {
			if (rightPlayerNamePanelMap.containsKey(playerName)) {
				return rightPlayerNamePanelMap.get(playerName);
			}
		}
		return null;
	}
	
	public PlayerInfo getPlayerInfoFromPosition(int TeamNumber, int PlayerNumber) {
		String playerName = "";
		String playerTeam = "";
		
		if (!isProjectorModeEnabled){
			if (TeamNumber == 1) {
				if (PlayerNumber == 3) {
					playerName = (String)panelLeftPlayer1.getClientProperty("playerName");
					playerTeam = (String)panelLeftPlayer1.getClientProperty("playerTeam");
				} else if (PlayerNumber == 2) {
					playerName = (String)panelLeftPlayer2.getClientProperty("playerName");
					playerTeam = (String)panelLeftPlayer2.getClientProperty("playerTeam");
				} else if (PlayerNumber == 1) {
					playerName = (String)panelLeftPlayer3.getClientProperty("playerName");
					playerTeam = (String)panelLeftPlayer3.getClientProperty("playerTeam");
				} else {
					playerName = (String)panelLeftPlayer4.getClientProperty("playerName");
					playerTeam = (String)panelLeftPlayer4.getClientProperty("playerTeam");
				}
			} 
			else {
				if (PlayerNumber == 0) {
					playerName = (String)panelRightPlayer1.getClientProperty("playerName");
					playerTeam = (String)panelRightPlayer1.getClientProperty("playerTeam");
				} else if (PlayerNumber == 1) {
					playerName = (String)panelRightPlayer2.getClientProperty("playerName");
					playerTeam = (String)panelRightPlayer2.getClientProperty("playerTeam");
				} else if (PlayerNumber == 2) {
					playerName = (String)panelRightPlayer3.getClientProperty("playerName");
					playerTeam = (String)panelRightPlayer3.getClientProperty("playerTeam");
				} else {
					playerName = (String)panelRightPlayer4.getClientProperty("playerName");
					playerTeam = (String)panelRightPlayer4.getClientProperty("playerTeam");
				}
			}
		}
		else{
			if (TeamNumber == 1) {
				if (PlayerNumber == 3) {
					playerName = (String)panelRightPlayer4.getClientProperty("playerName");
					playerTeam = (String)panelRightPlayer4.getClientProperty("playerTeam");
				} else if (PlayerNumber == 2) {
					playerName = (String)panelRightPlayer3.getClientProperty("playerName");
					playerTeam = (String)panelRightPlayer3.getClientProperty("playerTeam");
				} else if (PlayerNumber == 1) {
					playerName = (String)panelRightPlayer2.getClientProperty("playerName");
					playerTeam = (String)panelRightPlayer2.getClientProperty("playerTeam");
				} else {
					playerName = (String)panelRightPlayer1.getClientProperty("playerName");
					playerTeam = (String)panelRightPlayer1.getClientProperty("playerTeam");
				}
			} 
			else {
				if (PlayerNumber == 0) {
					playerName = (String)panelLeftPlayer4.getClientProperty("playerName");
					playerTeam = (String)panelLeftPlayer4.getClientProperty("playerTeam");
				} else if (PlayerNumber == 1) {
					playerName = (String)panelLeftPlayer3.getClientProperty("playerName");
					playerTeam = (String)panelLeftPlayer3.getClientProperty("playerTeam");
				} else if (PlayerNumber == 2) {
					playerName = (String)panelLeftPlayer2.getClientProperty("playerName");
					playerTeam = (String)panelLeftPlayer2.getClientProperty("playerTeam");
				} else {
					playerName = (String)panelLeftPlayer1.getClientProperty("playerName");
					playerTeam = (String)panelLeftPlayer1.getClientProperty("playerTeam");
				}
			}
		}
			
		return new PlayerInfo(playerName, playerTeam);
	}

	public void triggerEndOfGame() {
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion, EndOfGamePanel.class.toString());
		
		if (isProjectorModeEnabled){
			showMediaContentInProjectorMode();
		}
		
		model.setIsEndOfGameReached(true);
		model.setIsPlayerSelectionEnabled(false);
		
		unselectEveryPlayer();
		
		panelContent.repaint();

		List<Integer> leftTeamGoodAnswer = new ArrayList<Integer>();
		List<Integer> leftTeamBadAnswer = new ArrayList<Integer>();
		List<Integer> leftTeamScores = new ArrayList<Integer>();
		List<Integer> rightTeamGoodAnswer = new ArrayList<Integer>();
		List<Integer> rightTeamBadAnswer = new ArrayList<Integer>();
		List<Integer> rightTeamScores = new ArrayList<Integer>();
		Integer leftTeamScore = ScoreManager.getInstance().getLeftTeamScore();
		Integer rightTeamScore = ScoreManager.getInstance().getRightTeamScore();
		String leftTeamName = TeamManager.getInstance().getLeftTeam().getName();
		String rightTeamName = TeamManager.getInstance().getRightTeam().getName();

		for (String player : TeamManager.getInstance().getLeftTeamNames()) {
			int nbGoodQuestions = ScoreManager.getInstance().getNbOfGoodAnswerForPlayer(player, "Left");
			int nbBadQuestions = ScoreManager.getInstance().getNbOfBadAnswerForPlayer(player, "Left");
			leftTeamGoodAnswer.add(nbGoodQuestions);
			leftTeamBadAnswer.add(nbBadQuestions);
			leftTeamScores.add(ScoreManager.getInstance().getPlayerScore(player, "Left"));
		}

		for (String player : TeamManager.getInstance().getRightTeamNames()) {
			int nbGoodQuestions = ScoreManager.getInstance().getNbOfGoodAnswerForPlayer(player, "Right");
			int nbBadQuestions = ScoreManager.getInstance().getNbOfBadAnswerForPlayer(player, "Right");
			rightTeamGoodAnswer.add(nbGoodQuestions);
			rightTeamBadAnswer.add(nbBadQuestions);
			rightTeamScores.add(ScoreManager.getInstance().getPlayerScore(player, "Right"));
			rightTeamScore = ScoreManager.getInstance().getRightTeamScore();
		}

		if (ScoreManager.getInstance().getLeftTeamScore() >= ScoreManager.getInstance().getRightTeamScore()) {
			endOfGamePanel.setWinningTeam(TeamManager.getInstance().getLeftTeamNames(), leftTeamGoodAnswer, leftTeamBadAnswer, leftTeamScores, leftTeamScore, leftTeamName);
			endOfGamePanel.setLosingTeam(TeamManager.getInstance().getRightTeamNames(), rightTeamGoodAnswer, rightTeamBadAnswer, rightTeamScores, rightTeamScore, rightTeamName);
		} 
		else if (ScoreManager.getInstance().getLeftTeamScore() < ScoreManager.getInstance().getRightTeamScore()) {
			endOfGamePanel.setLosingTeam(TeamManager.getInstance().getLeftTeamNames(), leftTeamGoodAnswer, leftTeamBadAnswer, leftTeamScores, leftTeamScore, leftTeamName);
			endOfGamePanel.setWinningTeam(TeamManager.getInstance().getRightTeamNames(), rightTeamGoodAnswer, rightTeamBadAnswer, rightTeamScores, rightTeamScore, rightTeamName);
		}
		panelContent.repaint();
	}
	
	public void updateViewOnPageChanged() {
		
		int size = TeamManager.getInstance().getLeftTeamNames().size();
		if (size < 1){panelLeftPlayer1.setVisible(false);}else{panelLeftPlayer1.setVisible(true);}
		if (size < 2){panelLeftPlayer2.setVisible(false);}else{panelLeftPlayer2.setVisible(true);}
		if (size < 3){panelLeftPlayer3.setVisible(false);}else{panelLeftPlayer3.setVisible(true);}
		if (size < 4){panelLeftPlayer4.setVisible(false);}else{panelLeftPlayer4.setVisible(true);}
		
		size = TeamManager.getInstance().getRightTeamNames().size();
		if (size < 1){panelRightPlayer1.setVisible(false);}else{panelRightPlayer1.setVisible(true);}
		if (size < 2){panelRightPlayer2.setVisible(false);}else{panelRightPlayer2.setVisible(true);}
		if (size < 3){panelRightPlayer3.setVisible(false);}else{panelRightPlayer3.setVisible(true);}
		if (size < 4){panelRightPlayer4.setVisible(false);}else{panelRightPlayer4.setVisible(true);}
	}

	public void loadTeamInfo() {
		if (isProjectorModeEnabled){
			panelLeftTeam.setTeamName(TeamManager.getInstance().getLeftTeam().getName());
			panelRightTeam.setTeamName(TeamManager.getInstance().getRightTeam().getName());
		}
		else{
			panelLeftTeam.setTeamName(TeamManager.getInstance().getLeftTeam().getName());
			panelRightTeam.setTeamName(TeamManager.getInstance().getRightTeam().getName());
		}
		List<String> leftTeamName = TeamManager.getInstance().getLeftTeamNames();
		Iterator<String> it = leftTeamName.iterator();
		
		try {
			String playerName = "";
			if (it.hasNext()){
				playerName = (String) it.next();
				panelLeftPlayer1.setPlayerName(playerName);
				panelLeftPlayer1.putClientProperty("playerName", playerName);
				panelLeftPlayer1.putClientProperty("playerTeam", "Left");
			}
			if (it.hasNext()){
				playerName = (String) it.next();
				panelLeftPlayer2.setPlayerName(playerName);
				panelLeftPlayer2.putClientProperty("playerName", playerName);
				panelLeftPlayer2.putClientProperty("playerTeam", "Left");
			}

			if (it.hasNext()){
				playerName = (String) it.next();
				panelLeftPlayer3.setPlayerName(playerName);
				panelLeftPlayer3.putClientProperty("playerName", playerName);
				panelLeftPlayer3.putClientProperty("playerTeam", "Left");
			}

			if (it.hasNext()){
				playerName = (String) it.next();
				panelLeftPlayer4.setPlayerName(playerName);
				panelLeftPlayer4.putClientProperty("playerName", playerName);
				panelLeftPlayer4.putClientProperty("playerTeam", "Left");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> rightTeamName = TeamManager.getInstance().getRightTeamNames();
		it = rightTeamName.iterator();
		
		try {
			String playerName = "";
			if (it.hasNext()){
				playerName = (String) it.next();
				panelRightPlayer1.setPlayerName(playerName);
				panelRightPlayer1.putClientProperty("playerName", playerName);
				panelRightPlayer1.putClientProperty("playerTeam", "Right");
			}

			if (it.hasNext()){
				playerName = (String) it.next();
				panelRightPlayer2.setPlayerName(playerName);
				panelRightPlayer2.putClientProperty("playerName", playerName);
				panelRightPlayer2.putClientProperty("playerTeam", "Right");
			}

			if (it.hasNext()){
				playerName = (String) it.next();
				panelRightPlayer3.setPlayerName(playerName);
				panelRightPlayer3.putClientProperty("playerName", playerName);
				panelRightPlayer3.putClientProperty("playerTeam", "Right");
			}

			if (it.hasNext()){
				playerName = (String) it.next();
				panelRightPlayer4.setPlayerName(playerName);
				panelRightPlayer4.putClientProperty("playerName", playerName);
				panelRightPlayer4.putClientProperty("playerTeam", "Right");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Register player name with is panel
		leftPlayerNamePanelMap.put(panelLeftPlayer1.getPlayerName(),panelLeftPlayer1);
		leftPlayerNamePanelMap.put(panelLeftPlayer2.getPlayerName(),panelLeftPlayer2);
		leftPlayerNamePanelMap.put(panelLeftPlayer3.getPlayerName(),panelLeftPlayer3);
		leftPlayerNamePanelMap.put(panelLeftPlayer4.getPlayerName(),panelLeftPlayer4);

		rightPlayerNamePanelMap.put(panelRightPlayer1.getPlayerName(),panelRightPlayer1);
		rightPlayerNamePanelMap.put(panelRightPlayer2.getPlayerName(),panelRightPlayer2);
		rightPlayerNamePanelMap.put(panelRightPlayer3.getPlayerName(),panelRightPlayer3);
		rightPlayerNamePanelMap.put(panelRightPlayer4.getPlayerName(),panelRightPlayer4);
	}

	public void unselectEveryPlayer() {
		panelLeftPlayer1.setBackground(ColorManager.getPlayerDefaultColor());
		panelLeftPlayer2.setBackground(ColorManager.getPlayerDefaultColor());
		panelLeftPlayer3.setBackground(ColorManager.getPlayerDefaultColor());
		panelLeftPlayer4.setBackground(ColorManager.getPlayerDefaultColor());

		panelRightPlayer1.setBackground(ColorManager.getPlayerDefaultColor());
		panelRightPlayer2.setBackground(ColorManager.getPlayerDefaultColor());
		panelRightPlayer3.setBackground(ColorManager.getPlayerDefaultColor());
		panelRightPlayer4.setBackground(ColorManager.getPlayerDefaultColor());

		if (!isProjectorModeEnabled) {
			model.setHasPlayerPanelBeenClicked(false);
			model.setCurrentlySelectedPlayer(null);
			model.setTeamSelected(null);
		}
		
		model.setCurrentlySelectedPlayer(null);
		model.setTeamSelected(null);
		disableScoresButton();
		panelContent.repaint();

	}

	public void updateLeftTeamScoreLabel() {
		try {
			panelLeftPlayer1.setPlayerScore(Integer.toString(ScoreManager.getInstance().getPlayerScore(panelLeftPlayer1.getPlayerName(), "Left")));
			panelLeftPlayer2.setPlayerScore(Integer.toString(ScoreManager.getInstance().getPlayerScore(panelLeftPlayer2.getPlayerName(), "Left")));
			panelLeftPlayer3.setPlayerScore(Integer.toString(ScoreManager.getInstance().getPlayerScore(panelLeftPlayer3.getPlayerName(), "Left")));
			panelLeftPlayer4.setPlayerScore(Integer.toString(ScoreManager.getInstance().getPlayerScore(panelLeftPlayer4.getPlayerName(), "Left")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		panelLeftTeam.setTeamScore(Integer.toString(ScoreManager.getInstance().getLeftTeamScore()));
	}

	public void updateRightTeamScoreLabel() {
		try {
			panelRightPlayer1.setPlayerScore(Integer.toString(ScoreManager.getInstance().getPlayerScore(panelRightPlayer1.getPlayerName(), "Right")));
			panelRightPlayer2.setPlayerScore(Integer.toString(ScoreManager.getInstance().getPlayerScore(panelRightPlayer2.getPlayerName(), "Right")));
			panelRightPlayer3.setPlayerScore(Integer.toString(ScoreManager.getInstance().getPlayerScore(panelRightPlayer3.getPlayerName(), "Right")));
			panelRightPlayer4.setPlayerScore(Integer.toString(ScoreManager.getInstance().getPlayerScore(panelRightPlayer4.getPlayerName(), "Right")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		panelRightTeam.setTeamScore(Integer.toString(ScoreManager.getInstance().getRightTeamScore()));
	}

	public void showIntruQuestion(QuestionIntru question) {
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion,panelIntruQuestion.getClass().toString());

		if (isProjectorModeEnabled){showQuestionContentInProjectorMode((QuestionAnswerTopPanel)panelIntruQuestion);}
		
		panelIntruQuestion.setQuestionValue(QuestionManager.getInstance().getQuestionValue(question));
		panelIntruQuestion.setCategory(question.getCategory());

		panelIntruQuestion.setQuestionIDAndTotal(QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber());
		panelIntruQuestion.setQuestion(question.getQuestion());
		panelIntruQuestion.setQuestionList(question.getChoices(),question.getQuestion());
		panelIntruQuestion.setAnswer(question.getAnswer());
		panelIntruQuestion.setDescription(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
		
		panelContent.repaint();
	}

	public void showCorrectWordQuestion(QuestionCorrectWord question) {
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion,panelMotJusteQuestion.getClass().toString());

		if (isProjectorModeEnabled){hideMediaContentInProjectorMode();}
		
		panelMotJusteQuestion.setQuestionValue(QuestionManager.getInstance().getQuestionValue(question));
		panelMotJusteQuestion.setCategory(question.getCategory());

		panelMotJusteQuestion.setQuestionIDAndTotal(QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber());
		panelMotJusteQuestion.setQuestion(question.getQuestion());
		panelMotJusteQuestion.setHint(question.getHint());
		panelMotJusteQuestion.setAnswer(question.getAnswer());
		panelMotJusteQuestion.setDescription(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
		
		panelContent.repaint();
	}

	public void showAssociationQuestion(QuestionAssociation question) {
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion,panelAssociationQuestion.getClass().toString());

		if (isProjectorModeEnabled){
			showQuestionContentInProjectorMode((QuestionAnswerTopPanel) panelAssociationQuestion);
			panelAssociationQuestion.hideAnswerPanel();
		}
		
		panelAssociationQuestion.setQuestionValue(QuestionManager.getInstance().getQuestionValue(question));
		panelAssociationQuestion.setCategory(question.getCategory());
		
		ArrayList<String> choice = new ArrayList<String>();
		ArrayList<String> tempAnswer = new ArrayList<String>();
		ArrayList<String> answer = new ArrayList<String>();
		HashMap<String, String> answerMap = new HashMap<String, String>();
		
		for (Choice c : question.getChoices()) {
			tempAnswer.add(c.getAnswer());
		}
		tempAnswer = shuffleList(tempAnswer);
		
		int i = 1;
		for (Choice c : question.getChoices()) {
			choice.add(i + ". " + c.getChoice());
			answer.add(Character.toString((char)(i+64)) + ". " + tempAnswer.get(i-1));
			answerMap.put(tempAnswer.get(i-1), Character.toString((char)(i+64)));
			i++;
		}
		
		ArrayList<String> mergedList = mergeLists(choice, answer);

		panelAssociationQuestion.setQuestion(question.getQuestion());
		panelAssociationQuestion.setQuestionList(mergedList,question.getQuestion());

		String realAnswer = "";
		i = 1;
		for (Choice c : question.getChoices()) {
			realAnswer += i + " " + answerMap.get(c.getAnswer()) + ",   ";
			i++;
		}

		panelAssociationQuestion.setQuestionIDAndTotal(QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber());
		panelAssociationQuestion.setAnswer(realAnswer);
		panelAssociationQuestion.setDescription(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
		
		panelContent.repaint();
	}
	
	private ArrayList<String> shuffleList(ArrayList<String> list){
		long seed = System.nanoTime();
		Collections.shuffle(list, new Random(seed));
		return list;
	}
	
	private ArrayList<String> mergeLists(ArrayList<String> list1, ArrayList<String> list2){
		ArrayList<String> mergedList = new ArrayList<String>();
		if (list1.size() != list2.size()){
			return mergedList;
		}
		for (int i = 0; i < list1.size(); i++){
			mergedList.add(list1.get(i) + "   " + list2.get(i));
		}
		return mergedList;
	}
	
	public void showMediaQuestion(QuestionMedia question){
		//Get the media content type to show the good panel.
		String contentType = FileManager.getInstance().getQuestionMediaContentType(question.getMediaUrl());
		System.out.println(question.getMediaUrl());
		if (contentType.contains("video")){
			showVideoQuestion(question);
		}
		else if (contentType.contains("audio")){
			showAudioQuestion(question);
		}
		else if (contentType.contains("image")){
			showImageQuestion(question);
		}
	}
	
	public void showVideoQuestion(QuestionMedia question) {		
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion,panelVideoPlayer.getClass().toString());
		
		if (isProjectorModeEnabled){
			showMediaContentInProjectorMode();
			panelVideoPlayer.showVideoPlayer(panelQuestion.getWidth(),panelQuestion.getHeight());
			panelImagePlayer.resetImage();
		}

		//DownloadManager.getInstance().downloadURLToLocalFile(question.getMediaUrl(), question.getMediaId());
		model.setMediaMode(MediaMode.VIDEO);
		List<String> videoFiles = new ArrayList<String>();
		videoFiles.add("data/media/"+question.getMediaId());

		videoPlayer.setFilePaths(videoFiles);
		videoPlayer.loadFirstVideo();
		
		long maxValue = videoPlayer.getTotalVideoTimeInMilliseconds();
		panelVideoPlayer.setTotalVideoTime(maxValue/1000);
		panelVideoPlayer.getVideoProgressBar().setMaximum((int)maxValue/100);

		panelVideoPlayer.btnPrevious.setVisible(false);
		panelVideoPlayer.btnNext.setVisible(false);
		
		panelVideoPlayer.setCategory(question.getCategory());
		panelVideoPlayer.setQuestionValue(QuestionManager.getInstance().getQuestionValue(question));
		panelVideoPlayer.setQuestionIDAndTotal(QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber());
		panelVideoPlayer.setQuestion(question.getStatement());
		panelVideoPlayer.setAnswer(question.getAnswer());
		panelVideoPlayer.setDescription(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
		
		panelContent.repaint();
	}

	public void showAudioQuestion(QuestionMedia question) {		
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion,panelAudioPlayer.getClass().toString());

		if (isProjectorModeEnabled){
			showMediaContentInProjectorMode();
		}
		
		//DownloadManager.getInstance().downloadURLToLocalFile(question.getMediaUrl(), question.getMediaId());
		model.setMediaMode(MediaMode.AUDIO);
		List<String> audioFiles = new ArrayList<String>();
		audioFiles.add("data/media/"+question.getMediaId());

		if(isProjectorModeEnabled){
			panelImagePlayer.resetImage();
			soundPlayer.setFilePaths(audioFiles);
			soundPlayer.loadFirstFile();
			int maxValue = soundPlayer.getTotalTrackTimeInMilliseconds() / 100;
			controller.initPanelAudioPlayer(maxValue);
			hideMediaContentInProjectorMode();
		}
		
		panelAudioPlayer.btnPrevious.setVisible(false);
		panelAudioPlayer.btnNext.setVisible(false);
		
		panelAudioPlayer.setCategory(question.getCategory());
		panelAudioPlayer.setQuestionValue(QuestionManager.getInstance().getQuestionValue(question));
		panelAudioPlayer.setQuestionIDAndTotal(QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber());
		panelAudioPlayer.setQuestion(question.getStatement());
		panelAudioPlayer.setAnswer(question.getAnswer());
		panelAudioPlayer.setDescription(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
		
		panelContent.repaint();
	}
	
	public void showImageQuestion(QuestionMedia question){
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion,panelImagePlayer.getClass().toString());
		panelImagePlayer.resetImage();
		model.setMediaMode(MediaMode.PICTURE);
		List<String> imageFiles = new ArrayList<String>();
		imageFiles.add(question.getMediaUrl());
		
		imagePlayer.setImagePaths(imageFiles);
		
		if (isProjectorModeEnabled){
			panelImagePlayer.btnPlay.setVisible(false);
			showMediaContentInProjectorMode();
			//System.out.println(panelQuestion.getWidth() + " " + panelQuestion.getHeight());
			//panelImagePlayer.setImage(imagePlayer.getImagePaths().get(0), panelQuestion.getWidth(), panelQuestion.getHeight());
		}else{
			panelImagePlayer.btnPlay.setVisible(true);
			//panelImagePlayer.hideMediaContent();
		}

		//panelImagePlayer.setCurrentImageName(imagePlayer.getImagePaths().get(0));
		//No usage for these buttons.
		panelImagePlayer.btnNext.setVisible(false);
		panelImagePlayer.btnPause.setVisible(false);
		panelImagePlayer.btnStop.setVisible(false);
		panelImagePlayer.btnPrevious.setVisible(false);
		
		panelImagePlayer.setCategory(question.getCategory());
		panelImagePlayer.setQuestionValue(QuestionManager.getInstance().getQuestionValue(question));
		panelImagePlayer.setQuestionIDAndTotal(QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber());
		panelImagePlayer.setQuestion(question.getStatement());
		panelImagePlayer.setAnswer(question.getAnswer());
		panelImagePlayer.setDescription(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
		
		panelContent.repaint();
	}

	public void showAnagramQuestion(QuestionAnagram question) {
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion,panelAnagramQuestion.getClass().toString());

		if (isProjectorModeEnabled){showQuestionContentInProjectorMode((QuestionAnswerTopPanel)panelAnagramQuestion);}
		
		panelAnagramQuestion.setQuestionValue(QuestionManager.getInstance().getQuestionValue(question));
		panelAnagramQuestion.setCategory(question.getCategory());

		panelAnagramQuestion.setQuestionIDAndTotal(QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber());
		panelAnagramQuestion.setQuestion(question.getAnagram());
		panelAnagramQuestion.setHint(question.getHint());
		panelAnagramQuestion.setAnswer(question.getAnswer());
		panelAnagramQuestion.setDescription(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
		
		panelContent.repaint();
	}

	public void showMultipleClueQuestion(QuestionIdentification question) {
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion,panelMultipleClueQuestion.getClass().toString());

		if (isProjectorModeEnabled){hideMediaContentInProjectorMode();}
		
		panelMultipleClueQuestion.setQuestionValue(QuestionManager.getInstance().getQuestionValue(question));
		panelMultipleClueQuestion.setCategory(question.getCategory());
		
		panelMultipleClueQuestion.setQuestionIDAndTotal(QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber());
		panelMultipleClueQuestion.setQuestionList(question.getStatements(),"");
		panelMultipleClueQuestion.setAnswer(question.getAnswer());
		panelMultipleClueQuestion.setDescription(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
		
		panelContent.repaint();
	}

	public void showGeneralQuestion(QuestionGeneral question) {
		((CardLayout) panelQuestion.getLayout()).show(panelQuestion,panelGeneralQuestion.getClass().toString());
		
		if (isProjectorModeEnabled){hideMediaContentInProjectorMode();}
		
		panelGeneralQuestion.setQuestionValue(QuestionManager.getInstance().getQuestionValue(question));
		panelGeneralQuestion.setCategory(question.getCategory());

		panelGeneralQuestion.setQuestionIDAndTotal(QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber());
		panelGeneralQuestion.setQuestion(question.getQuestion());
		panelGeneralQuestion.setAnswer(question.getAnswer());
		panelGeneralQuestion.setDescription(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
		
		panelContent.repaint();
	}
	
	public HashMap<String, PlayerInfoZonePanel> getLeftPlayerNamePanelMap() {
		return leftPlayerNamePanelMap;
	}

	public HashMap<String, PlayerInfoZonePanel> getRightPlayerNamePanelMap() {
		return rightPlayerNamePanelMap;
	}

	public void setModel(CurrentGameModel model) {
		this.model = model;
	}

	public void setController(CurrentGameController controller) {
		this.controller = controller;
	}

	private ActionListener actionListenerReportQuestion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ReportQuestionController reportQuestionController = new ReportQuestionController(new ReportQuestionView());
			PageManager.getInstance().setPageDisplayedNoHistory(PageManager.REPORTQUESTION);
		}
	};

	private ActionListener actionListenerSkipQuestion = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			controller.skipQuestionApp();
		}
	};

	private ActionListener actionListenerGoodAnswer = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			//TODO : modifier la valeur de la question
			controller.goodAnswerApp();
		}
	};

	private ActionListener actionListenerWrongAnswer = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			controller.wrongAnswerApp();
		}
	};

	private ActionListener actionListenerSubstractPoint = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			controller.substractPointsApp();
		}
	};

	private ActionListener actionListenerPreviousQuestion = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			panelImagePlayer.resetImage();
			panelContent.repaint();
			controller.previousQuestionApp();
		}
	};

	public JPanel getPanelContent() {
		return panelContent;
	}

	public void disableSkipButton() {
		btnSkipQuestion.setEnabled(false);
	}

	public void enableSkipButton() {
		btnSkipQuestion.setEnabled(true);
	}
	
	public void disablePrevButton(){
		btnPrevQuestion.setEnabled(false);
	}
	
	public void enablePrevButton(){
		btnPrevQuestion.setEnabled(true);
	}

	public boolean isSkipButtonEnabled() {
		if (btnSkipQuestion.isEnabled()) {
			return true;
		}
		return false;
	}

	public void setRightAnswerColorToLeftPlayerNamePanel(String playerName) {
		leftPlayerNamePanelMap.get(playerName).setBackground(ColorManager.getPlayerHasAnswerRightDefaultColor());
	}

	public void setRightAnswerColorToRightPlayerNamePanel(String playerName) {
		rightPlayerNamePanelMap.get(playerName).setBackground(ColorManager.getPlayerHasAnswerRightDefaultColor());
	}
	
	public void setSelectedPlayer(String playerName, String playerTeamSide){
		if(playerTeamSide == "Left"){
			getLeftPlayerNamePanelMap().get(playerName).setBackground(ColorManager.getPlayerHasAnswerRightDefaultColor());
		}
		else if(playerTeamSide == "Right"){
			getRightPlayerNamePanelMap().get(playerName).setBackground(ColorManager.getPlayerHasAnswerRightDefaultColor());
		}
		
		/*model.setCurrentlySelectedPlayer(playerName);
		model.setTeamSelected(playerTeamSide);*/
	}

	public void setProjectorMode() {
		panelLeftTeam.disableScoreLabel();
		panelRightTeam.disableScoreLabel();
		panelLeftTeam.disableTeamScoreEditButton();
		panelRightTeam.disableTeamScoreEditButton();

		panelLeftPlayer1.setScoreVisible(false);
		panelLeftPlayer2.setScoreVisible(false);
		panelLeftPlayer3.setScoreVisible(false);
		panelLeftPlayer4.setScoreVisible(false);

		panelRightPlayer1.setScoreVisible(false);
		panelRightPlayer2.setScoreVisible(false);
		panelRightPlayer3.setScoreVisible(false);
		panelRightPlayer4.setScoreVisible(false);

		panelQuestion.setVisible(false);

		btnGoodAnswer.setVisible(false);
		btnPrevQuestion.setVisible(false);
		btnSkipQuestion.setVisible(false);
		btnSubstractPoints.setVisible(false);
		btnWrongAnswer.setVisible(false);
		
		panelAudioPlayer.btnNext.setVisible(false);
		panelAudioPlayer.btnPause.setVisible(false);
		panelAudioPlayer.btnPlay.setVisible(false);
		panelAudioPlayer.btnPrevious.setVisible(false);
		panelAudioPlayer.btnStop.setVisible(false);
		
		panelVideoPlayer.btnNext.setVisible(false);
		panelVideoPlayer.btnPause.setVisible(false);
		panelVideoPlayer.btnPlay.setVisible(false);
		panelVideoPlayer.btnPrevious.setVisible(false);
		panelVideoPlayer.btnStop.setVisible(false);
		
		panelImagePlayer.btnNext.setVisible(false);
		panelImagePlayer.btnPrevious.setVisible(false);

		panelAudioPlayer.setProjectorMode();
		panelVideoPlayer.setProjectorMode();
		panelImagePlayer.setProjectorMode();
		
		isProjectorModeEnabled = true;
		
		initGUI();
	}
	
	public void showMediaContentInProjectorMode(){
		panelContent.remove(lblLogo);
		panelContent.add(panelQuestion, "cell 2 0,grow");
		panelQuestion.setVisible(true);
	}
	
	public void showQuestionContentInProjectorMode(QuestionAnswerTopPanel questionPanel){
		questionPanel.hideAnswerPanel();
		panelContent.remove(lblLogo);
		panelContent.add(panelQuestion, "cell 2 0,grow");
		panelQuestion.setVisible(true);
	}
	
	public void hideMediaContentInProjectorMode(){
		panelContent.remove(panelQuestion);
		panelContent.remove(lblLogo);
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenDimension.width == 0){
			screenDimension = new Dimension(100,100);
		}
		if (oldWindowDimension.getHeight() != screenDimension.getHeight() && 
			oldWindowDimension.getWidth()  != screenDimension.getHeight()){
			lblLogo.setIcon(getIcon(IconManager.getOpenQuizLogoWithNameBig(),screenDimension.width/2,screenDimension.height/4));
			oldWindowDimension = screenDimension;
			System.out.println("Update Window Dimension");
		}
		panelContent.add(lblLogo, "cell 2 0,grow");
	}
	
	public void setVideoPlayer(VideoPlayer videoPlayer){
		this.videoPlayer = videoPlayer;
		this.panelVideoPlayer.setVideoPlayer(videoPlayer);
	}
	
	public void setAudioPlayer(SoundPlayer soundPlayer){
		this.soundPlayer = soundPlayer;
	}

	public void setImagePlayer(ImagePlayer imagePlayer) {
		this.imagePlayer = imagePlayer;	
	}

	public void enablePlayerIndividualPointEdition(String playerName, String playerTeam) {
		System.out.println(QuestionManager.getInstance().getCurrentQuestionTarget().toString());
		if (QuestionManager.getInstance().getCurrentQuestionTarget() == QuestionTarget.Individual){
			PlayerInfoZonePanel playerPanel = (PlayerInfoZonePanel)getPlayerPanelFromSideAndName(playerTeam, playerName);
			
			if (playerPanel != null){
				playerPanel.showPlayerPointsEdition();
			}
		}
		panelContent.repaint();
	}

	public void disableAllPlayerIndividualPointEdition() {
		for (PlayerInfoZonePanel panel : leftPlayerNamePanelMap.values()){
			panel.hidePlayerPointsEdition();
		}
		for (PlayerInfoZonePanel panel : rightPlayerNamePanelMap.values()){
			panel.hidePlayerPointsEdition();
		}
	}

	public void resetUI() {
		model.setIsEndOfGameReached(false);
		model.setIsPlayerSelectionEnabled(true);
		model.setAreStatsPosted(false);
		btnSkipQuestion.setEnabled(true);
	}
	
	public void disableScoresButton(){
		btnGoodAnswer.setEnabled(false);
		btnWrongAnswer.setEnabled(false);
		btnSubstractPoints.setEnabled(false);
	}
	
	public void enableScoresButton(){
		btnGoodAnswer.setEnabled(true);
		btnWrongAnswer.setEnabled(true);
		btnSubstractPoints.setEnabled(true);
	}
	
	public void setPlayerIndividualScore(String selectedTeam, String selectedPlayer, int value){
		PlayerInfoZonePanel panel = (PlayerInfoZonePanel)getPlayerPanelFromSideAndName(selectedTeam, selectedPlayer);
		if (panel != null){
			panel.setPlayerIndividualScore(value);
		}
		panelContent.repaint();
	}
	
	public void adjustSelectedPlayerScoreLabel(String selectedTeam, String selectedPlayer, int value){
		PlayerInfoZonePanel panel = (PlayerInfoZonePanel)getPlayerPanelFromSideAndName(selectedTeam, selectedPlayer);
		if (panel != null){
			panel.adjutPlayerIndividualScore(value);
		}
		panelContent.repaint();
	}
	
	public void playBuzzerSound(){
		if (!isProjectorModeEnabled){
			if(model.getTeamSelected().equals("Left")){soundPlayer.loadBuzzerLeftSoundFile();}
			else if(model.getTeamSelected().equals("Right")){soundPlayer.loadBuzzerRightSoundFile();}
			soundPlayer.setCommand(Command.PLAY);
			playbackTimer.start();
		}
	}
	
	public synchronized void resetEventProcessedCounter(){
		eventProcessedCounter.set(0);
	}

	public boolean isProjectorModeEnabled() {
		return this.isProjectorModeEnabled;
	}

	public void initPanelAudioPlayer(int maxValue) {
		panelAudioPlayer.setTotalTrackTime(maxValue / 10);
		panelAudioPlayer.getProgressBarAudio().setMaximum(maxValue);
		System.out.println("maxValue: " + maxValue/10);
		panelContent.repaint();
	}
	
	public void updatePanelAudioPlayer(int newValue, int currentSoundTime){
		panelAudioPlayer.getProgressBarAudio().setValue(newValue);
		panelAudioPlayer.setCurrentSoundTime(currentSoundTime);
		System.out.println("currentSoundTime: " + currentSoundTime);
		panelContent.repaint();
	}

	public void resetPanelAudioPlayer(int totalTrackTimeInMilliseconds) {
		panelAudioPlayer.setTotalTrackTime(totalTrackTimeInMilliseconds / 1000);
		panelAudioPlayer.setCurrentSoundTime(0);
		panelAudioPlayer.getProgressBarAudio().setValue(0);
		panelAudioPlayer.getProgressBarAudio().setMaximum(totalTrackTimeInMilliseconds /100);
		
		panelContent.repaint();
	}
	
	private ImageIcon getIcon(ImageIcon imageIcon, int width, int height){
		ImageIcon imgIcon = imageIcon;
		Image image = null;
		image = imgIcon.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH);
		imgIcon.setImage(image);
		return imgIcon;
	}

	public void swapPlayers() {
		if (!isProjectorModeEnabled){
			changePlayersPanelPosition(OptionsManager.getInstance().isGameWindowPlayersInNormalPosition());
		}
		else{
			changePlayersPanelPosition(OptionsManager.getInstance().isProjWindowPlayersInNormalPosition());
		}
		panelContent.repaint();
	}
	
	private void changePlayersPanelPosition(boolean isNormalPosition){
		removeAllPlayerPanel();
		if (!isNormalPosition){
			setLeftPlayersPanelSide("Right");
			setRightPlayersPanelSide("Left");
			panelLeftTeam.add(panelRightPlayer4, "cell 0 2 5 1,grow");
			panelLeftTeam.add(panelRightPlayer3, "cell 0 3 5 1,grow");
			panelLeftTeam.add(panelRightPlayer2, "cell 0 4 5 1,grow");
			panelLeftTeam.add(panelRightPlayer1, "cell 0 5 5 1,grow");
			panelRightTeam.add(panelLeftPlayer4, "cell 0 2 5 1,grow");
			panelRightTeam.add(panelLeftPlayer3, "cell 0 3 5 1,grow");
			panelRightTeam.add(panelLeftPlayer2, "cell 0 4 5 1,grow");
			panelRightTeam.add(panelLeftPlayer1, "cell 0 5 5 1,grow");
		}
		else{
			setLeftPlayersPanelSide("Left");
			setRightPlayersPanelSide("Right");
			panelLeftTeam.add(panelLeftPlayer1, "cell 0 2 5 1,grow");
			panelLeftTeam.add(panelLeftPlayer2, "cell 0 3 5 1,grow");
			panelLeftTeam.add(panelLeftPlayer3, "cell 0 4 5 1,grow");
			panelLeftTeam.add(panelLeftPlayer4, "cell 0 5 5 1,grow");
			panelRightTeam.add(panelRightPlayer1, "cell 0 2 5 1,grow");
			panelRightTeam.add(panelRightPlayer2, "cell 0 3 5 1,grow");
			panelRightTeam.add(panelRightPlayer3, "cell 0 4 5 1,grow");
			panelRightTeam.add(panelRightPlayer4, "cell 0 5 5 1,grow");
		}
	}
	
	private void setLeftPlayersPanelSide(String side){
		panelLeftPlayer1.setPanelSide(side);
		panelLeftPlayer2.setPanelSide(side);
		panelLeftPlayer3.setPanelSide(side);
		panelLeftPlayer4.setPanelSide(side);
	}
	
	private void setRightPlayersPanelSide(String side){
		panelRightPlayer1.setPanelSide(side);
		panelRightPlayer2.setPanelSide(side);
		panelRightPlayer3.setPanelSide(side);
		panelRightPlayer4.setPanelSide(side);
	}
	
	private void removeAllPlayerPanel(){
		panelLeftTeam.remove(panelLeftPlayer1);
		panelLeftTeam.remove(panelLeftPlayer2);
		panelLeftTeam.remove(panelLeftPlayer3);
		panelLeftTeam.remove(panelLeftPlayer4);
		panelLeftTeam.remove(panelRightPlayer1);
		panelLeftTeam.remove(panelRightPlayer2);
		panelLeftTeam.remove(panelRightPlayer3);
		panelLeftTeam.remove(panelRightPlayer4);
		panelRightTeam.remove(panelLeftPlayer1);
		panelRightTeam.remove(panelLeftPlayer2);
		panelRightTeam.remove(panelLeftPlayer3);
		panelRightTeam.remove(panelLeftPlayer4);
		panelRightTeam.remove(panelRightPlayer1);
		panelRightTeam.remove(panelRightPlayer2);
		panelRightTeam.remove(panelRightPlayer3);
		panelRightTeam.remove(panelRightPlayer4);
	}

	public void hideCurrentQuestion() {
		// TODO Auto-generated method stub
		
	}

}
