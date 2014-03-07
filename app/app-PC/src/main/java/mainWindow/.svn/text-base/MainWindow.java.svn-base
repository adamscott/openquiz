package mainWindow;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.response.BaseResponse;

import net.miginfocom.swing.MigLayout;

import login.LoginController;
import login.LoginView;
import options.OptionsController;
import options.OptionsView;
import serialization.SerializationManager;
import thread.InfiniteProgressPanel;
import applicationTools.BackgroundManager;
import applicationTools.BackgroundPanel;
import applicationTools.BluecoveBluetoothManager;
import applicationTools.ButtonManager;
import applicationTools.Command;
import applicationTools.CustomButton;
import applicationTools.EventGameChanges;
import applicationTools.GetInstanceBluetoothManager;
import applicationTools.GlassPanel;
import applicationTools.IconManager;
import applicationTools.ImagePlayer;
import applicationTools.LoginManager;
import applicationTools.PageManager;
import applicationTools.QuestionManager;
import applicationTools.RepaintWindowManager;
import applicationTools.SoundPlayer;
import applicationTools.VideoPlayer;
import applicationTools.WindowManager;
import creationGame.CreateNewGameController;
import creationGame.CreateNewGameView;
import currentGame.CurrentGameController;
import currentGame.CurrentGameModel;
import currentGame.CurrentGameView;
import currentGame.CurrentGameViewWrapper;
import customWindow.CustomWindow;
import enumPack.GameAction;

public class MainWindow extends CustomWindow implements Observer, WindowFocusListener
{	
	private CustomButton btnOptions;
	private CustomButton btnLogo;
	private CustomButton btnHome;
	private CustomButton btnBack;
	private CustomButton btnMinimize;
	private CustomButton btnTutorial;
	private CustomButton btnRefresh;
	private CustomButton btnLogout;
	
	private JLabel lblUsername;
	private JLabel lblConsoleInfo;
	private Box verticalBox;
	private Box horizontalBox;
	
	private CreateNewGameController createNewGameController;
	private CurrentGameController currentGameController;
	private OptionsController optionsController = null;
	private LoginController loginController;
	
	private CreateNewGameView createNewGameView;
	private CurrentGameView currentGameView;
	private CurrentGameView currentGameView2;
	private OptionsView optionsView = null;
	private LoginView loginView;
	private CurrentGameViewWrapper currentGameViewWrapper;
	
	private JFrame projectorFrame;

	private GlassPanel glassPanel;
	
	private SoundPlayer soundPlayerMedia = new SoundPlayer();
	private SoundPlayer soundPlayerBuzzer = new SoundPlayer();
	private VideoPlayer videoPlayer = new VideoPlayer();
	private ImagePlayer imagePlayer = new ImagePlayer();
	
	private boolean showHome = false;
	
	private Timer loginTimer;
	private Timer repackTimer;
	
	public MainWindow() {		
		PageManager.getInstance().addObserver(this);
		BackgroundManager.getInstance().addObserver(this);
		
		//projectorFrame = new JFrame();
		
		createNewGameController = new CreateNewGameController();
		createNewGameView = new CreateNewGameView();
		currentGameView = new CurrentGameView(false);
		currentGameView2 = new CurrentGameView(true);
		currentGameViewWrapper = new CurrentGameViewWrapper();
		
		WindowManager.getInstance().setGameView(currentGameView);
		WindowManager.getInstance().setProjView(currentGameView2);
		
		soundPlayerMedia = new SoundPlayer();
		soundPlayerBuzzer = new SoundPlayer();
		videoPlayer = new VideoPlayer();
		
		currentGameView.setAudioPlayer(soundPlayerBuzzer);
		currentGameView.setVideoPlayer(videoPlayer);
		currentGameView2.setAudioPlayer(soundPlayerMedia);
		currentGameView2.setVideoPlayer(videoPlayer);
		currentGameView.setImagePlayer(imagePlayer);
		currentGameView2.setImagePlayer(imagePlayer);
		
		soundPlayerMedia.start();
		soundPlayerBuzzer.start();
		
		currentGameView2.setProjectorMode();
		
		currentGameController = new CurrentGameController();
		
		CurrentGameModel model = currentGameController.getModel();
		createNewGameView.setModel(createNewGameController.getModel());
		currentGameView.setModel(model);
		currentGameView2.setModel(model);
		
		createNewGameView.setController(createNewGameController);
		currentGameView.setController(currentGameController);
		currentGameView2.setController(currentGameController);
		
		createNewGameController.setView(createNewGameView);
		
		currentGameViewWrapper.registerView(currentGameView);
		currentGameViewWrapper.registerView(currentGameView2);
		
		currentGameController.registerViews(currentGameViewWrapper);
		currentGameController.registerMainView(currentGameView);
		
		loginController = new LoginController();
		loginView = new LoginView();
		loginView.setController(loginController);
		loginController.setView(loginView);
		
		repackTimer = new Timer(100, actionListenerrepack);
		
		initGUI();	
		
		addActionListeners();
		
		PageManager.getInstance().setPageDisplayed(PageManager.CREATENEWGAME);//HOMEPAGE);
		frameWindow.addWindowFocusListener(this);
		
	}
	
	private void initGUI() {
		btnLogo = new CustomButton("", IconManager.getOpenQuizLogoWithNameIcon(), ButtonManager.getLogoWithTextDimension());
		btnLogo.setContentAreaFilled(false);
		btnLogo.setBorderPainted(false);
		panelWindow.add(btnLogo, "cell 0 0 2 2,alignx left,aligny center");
		
		btnRefresh = new CustomButton("", IconManager.getRefreshIcon(), ButtonManager.getIconButtonDimension());
		btnRefresh.setToolTipText("Rafraîchir (Alt+R)");
		btnRefresh.setMnemonic(KeyEvent.VK_R);
		panelWindow.add(btnRefresh, "cell 13 0,alignx right,aligny top");
		
		btnTutorial = new CustomButton("", IconManager.getLightIcon(), ButtonManager.getIconButtonDimension());
		btnTutorial.setToolTipText("Tutoriel (Alt+T)");
		btnTutorial.setMnemonic(KeyEvent.VK_T);
		panelWindow.add(btnTutorial, "cell 13 0,alignx right,aligny top");
		
		btnOptions = new CustomButton("", IconManager.getGearIcon(), ButtonManager.getIconButtonDimension());
		btnOptions.setToolTipText("Options (Alt+O)");
		btnOptions.setMnemonic(KeyEvent.VK_O);
		panelWindow.add(btnOptions, "cell 13 0,alignx right,aligny top");
		
		btnMinimize = new CustomButton("", IconManager.getMinimizeIcon(), ButtonManager.getIconButtonDimension());
		btnMinimize.setToolTipText("Minimiser (Alt+M)");
		btnMinimize.setMnemonic(KeyEvent.VK_M);
		panelWindow.add(btnMinimize, "cell 13 0,alignx right,aligny top");
		
		btnQuit = new CustomButton("", IconManager.getCloseXIcon(), ButtonManager.getIconButtonDimension());
		btnQuit.setToolTipText("Fermer (Alt+F4)");
		btnQuit.addActionListener(actionListenerQuit);
		panelWindow.add(btnQuit, "cell 13 0,alignx right,aligny top");
		
		JPanel infoPanel = new JPanel();
		btnLogout = new CustomButton("", IconManager.getLogoutIcon(), ButtonManager.getIconButtonDimensionSmall());
		lblUsername = new JLabel("");
		lblConsoleInfo = new JLabel("");
		infoPanel.setLayout(new MigLayout("", "[grow][grow]", "[grow][grow]"));
		infoPanel.add(lblUsername, "cell 0 0,alignx left");
		infoPanel.add(btnLogout, "cell 1 0,alignx right");
		infoPanel.add(lblConsoleInfo, "cell 0 1 2 1,alignx left");
		
		panelWindow.add(infoPanel, "cell 13 1,alignx right,aligny top");

		btnBack = new CustomButton("", IconManager.getBackArrowIcon(), ButtonManager.getIconButtonDimension());
		btnBack.setToolTipText("Page précédente (Alt+P)");
		btnBack.setMnemonic(KeyEvent.VK_P);
		
		btnHome = new CustomButton("", IconManager.getHomeIcon(), ButtonManager.getIconButtonDimension());
		btnHome.setToolTipText("Accueil (Alt+A)");
		btnHome.setMnemonic(KeyEvent.VK_A);
		panelWindow.add(btnHome, "cell 0 10,alignx left,aligny center");
					
		panelContent.setLayout(new CardLayout());

		panelContent.add((JComponent)(((CreateNewGameView)(createNewGameController.getView())).getPanelContent()), PageManager.CREATENEWGAME);
		panelContent.add((JComponent)(((CurrentGameView)(currentGameController.getView())).getPanelContent()), PageManager.CURRENTGAME);
		
		glassPanel = new GlassPanel(panelWindow);
		frameWindow.setGlassPane(InfiniteProgressPanel.getInstance());
		
		loginTimer = new Timer(5000, actionListenerLoginTimerTick);
		loginTimer.setInitialDelay(500);
		loginTimer.start();
	}
	
	public void update(Observable arg0, Object arg1) {
		((CardLayout)panelContent.getLayout()).show(panelContent, (String)arg1);

		if((String)arg1 == PageManager.CURRENTGAME){
			currentGameController.initData();

			if(RepaintWindowManager.getInstance().getIsProjectorModeEnabled()){
				if(projectorFrame == null){
					projectorFrame = new JFrame();
				}
				if(!projectorFrame.isVisible()){
					projectorFrame.setVisible(true);
					
					ImageIcon logoImageIcon = new ImageIcon("img/OpenQuizLogoWithoutName_large.png");
					projectorFrame.setIconImage(logoImageIcon.getImage().getScaledInstance(48, -1,java.awt.Image.SCALE_SMOOTH));
					
					BackgroundPanel panelProjectorFrame = new BackgroundPanel(BackgroundManager.getInstance().getBackground().getImage(), BackgroundPanel.SCALED, 0f, 0f);
					panelProjectorFrame.add(((CurrentGameView)(currentGameView2)).getPanelContent());
					
					projectorFrame.revalidate();
					projectorFrame.setContentPane(panelProjectorFrame);
					projectorFrame.getContentPane().revalidate();
					currentGameView2.loadTeamInfo();
					projectorFrame.revalidate();
					projectorFrame.pack();
				}
			}
		}
		
		if((String)arg1 == PageManager.CREATENEWGAME){
			btnHome.setEnabled(false);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
		            Thread action = new Thread(new Runnable() {
		                public void run() {
		                	InfiniteProgressPanel.getInstance().start();
		        			createNewGameController.refreshData();
		                	InfiniteProgressPanel.getInstance().stop();
		                }
		            }, "Action");
		            action.start();
                }
            });
		}
		else{
			btnHome.setEnabled(true);
		}
				
		panelWindow.setImage(BackgroundManager.getInstance().getBackground().getImage());
		
		panelWindow.repaint();
	}
	
	public void addActionListeners() {
		btnOptions.addActionListener(actionListenerOptions);
		btnTutorial.addActionListener(actionTutorial);
		btnMinimize.addActionListener(actionListenerMinimize);
		btnBack.addActionListener(actionListenerBack);
		btnHome.addActionListener(actionListenerHome);
		btnLogo.addActionListener(actionListenerHome);
		btnRefresh.addActionListener(actionListenerRefresh);
		btnLogout.addActionListener(actionListenerLogout);
		
		for (int i=0 ; i < btnQuit.getActionListeners().length; i++){
			btnQuit.removeActionListener(btnQuit.getActionListeners()[i]);
		}
		
		btnQuit.addActionListener(actionListenerQuit);
	}
	
	//Logout user
	private ActionListener actionListenerLogout = (new ActionListener(){
		public void actionPerformed(ActionEvent event){
			LoginManager.getInstance().logout();
			lblUsername.setText("");
			panelWindow.repaint();
			loginView.showWindow();
		}
	});
	
	//Quit application
	private ActionListener actionListenerQuit = (new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			Object[] options = {"Oui", "Non"};
			int answer = JOptionPane.showOptionDialog(frameWindow,
												"Êtes-vous certain de vouloir fermer la fenêtre ?",
												"Fermeture de la fenêtre",
												JOptionPane.YES_NO_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												IconManager.getQuestionIcon(),     //do not use a custom Icon
												options,  //the titles of buttons
												options[0]); //default button title
			if(answer == 0) {
				soundPlayerMedia.setCommand(Command.EXIT);
				soundPlayerBuzzer.setCommand(Command.EXIT);
				frameWindow.dispose();
				System.exit(0);
			}
		}
	});
	
	//Display option window
	private ActionListener actionListenerOptions = (new ActionListener() {
		public void actionPerformed(ActionEvent event) {			
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
		            Thread action = new Thread(new Runnable() {
		                public void run() {
		                	if (optionsController == null || optionsView == null){
			                	InfiniteProgressPanel.getInstance().start();
			        			optionsController = new OptionsController();
			        			optionsView = new OptionsView();
			                	InfiniteProgressPanel.getInstance().stop();
			        			optionsController.setView(optionsView);
		                	}
		                	else{
		                		optionsView.showWindow();
		                	}
		                }
		            }, "Action");
		            action.start();
                }
            });

		}
	});
	
	//Display tutorial window
	private ActionListener actionTutorial = (new ActionListener() {
		public void actionPerformed(ActionEvent eventa) {			
			TutorialPopUp tp = new TutorialPopUp();
			tp.showWindow();
		}
	});
	
	//Minimize application
	private ActionListener actionListenerMinimize = (new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			frameWindow.setState(Frame.ICONIFIED);
		}
	});
	
	//Go to previous page
	private ActionListener actionListenerBack = (new ActionListener() {
		public void actionPerformed(ActionEvent event) {	
			String lastPageVisited = PageManager.getInstance().getLastPageVisited();
			if(lastPageVisited != "")
			{
				PageManager.getInstance().setPageDisplayedNoHistory(lastPageVisited);
			}
		}
	});
	
	//Refresh data
	private ActionListener actionListenerRefresh = (new ActionListener() {
		public void actionPerformed(ActionEvent event) {

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
		            Thread action = new Thread(new Runnable() {
		                public void run() {
		                	InfiniteProgressPanel.getInstance().start();
		                	createNewGameController.refreshData();
		                	InfiniteProgressPanel.getInstance().stop();
		                }
		            }, "Action");
		            action.start();
                }
            });
		}
	});
	
	//Go to home page
	private ActionListener actionListenerHome = (new ActionListener() {
		public void actionPerformed(ActionEvent event) {	
			String test = QuestionManager.getInstance().getCurrentGameKey();
			if (QuestionManager.getInstance().getCurrentGameKey() != null && 
				QuestionManager.getInstance().getCurrentGameKey() != ""){
				Game game = RequestsWebService.getGame(QuestionManager.getInstance().getCurrentGameKey());
				if (game != null){
					game.setActive(false);
					BaseResponse baseResponse = RequestsWebService.editGame(game);
				}
			}
			currentGameController.disableEventProcess();
			PageManager.getInstance().setPageDisplayed(PageManager.CREATENEWGAME);//HOMEPAGE);
			showHome = true;
		}
	});
	
	//Check for login
	private ActionListener actionListenerLoginTimerTick = (new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//Show the login wizard if no user logged, not even as a guest.
			if (!LoginManager.getInstance().isAnyUserLogged()){
				loginView.showWindow();
				lblUsername.setText(LoginManager.getInstance().getLoggedUsernameOrGuest());
				loginTimer.stop();
			}
		}
	});
	
	private ActionListener actionListenerrepack = (new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//projectorFrame.pack();
		}
	});

	public void windowGainedFocus(WindowEvent arg0) {
		glassPanel.setVisible(false);
	}

	public void windowLostFocus(WindowEvent arg0) {
		glassPanel.setVisible(true);
	}
	
	public void closeMediaPlayer(){
		videoPlayer.killPlayer();
		soundPlayerMedia.killPlayer();
		soundPlayerBuzzer.killPlayer();
	}
	
	public void setConsoleInfoText(String info){
		if (info != null){
			lblConsoleInfo.setText(info);
		}
	}
	
	public void setConsoleInfoTextColor(Color newColor){
		if (newColor != null){
			lblConsoleInfo.setForeground(newColor);
		}
	}
	
	public void setLoggedUsername(String username){
		lblUsername.setText(username);
	}
}