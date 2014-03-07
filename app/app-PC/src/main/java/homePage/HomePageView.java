package homePage;

import homepage.IHomePageView;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.PageManager;
import applicationTools.SoundPlayer;
import ca.openquiz.comms.RequestsWebService;

public class HomePageView implements IHomePageView{
	private JPanel panelContent;
	private CustomButton btnPlay;
	private CustomButton btnTutorial;
	SoundPlayer sp = new SoundPlayer();
	
	public HomePageView() {		

		panelContent = new JPanel();
		
		btnPlay = new CustomButton("Jouer", null, ButtonManager.getTextButtonDimension());
		btnTutorial = new CustomButton("Tutoriel", null, ButtonManager.getTextButtonDimension());

		initWindow();
	}
	
	public void initWindow() {
		Dimension btnDim = new Dimension(200, 75);
		Box verticalBoxButtons = Box.createVerticalBox();
		
		panelContent.setOpaque(false);
		
		btnPlay.setMinimumSize(btnDim);
		btnPlay.setMaximumSize(btnDim);
		btnPlay.setMnemonic(KeyEvent.VK_J);
		
		btnTutorial.setMinimumSize(btnDim);
		btnTutorial.setMaximumSize(btnDim);
		btnTutorial.setMnemonic(KeyEvent.VK_T);
		
		verticalBoxButtons.add(btnPlay);
		verticalBoxButtons.add(Box.createVerticalStrut(50));
		verticalBoxButtons.add(btnTutorial);

		panelContent.setLayout(new MigLayout("fill"));
		panelContent.add(verticalBoxButtons, "alignx center");
		
		btnPlay.addActionListener(actionListenerPlay);
		btnTutorial.addActionListener(actionListenerTutorial);
	}
	
	private ActionListener actionListenerPlay = new ActionListener() {
		public void actionPerformed(ActionEvent event) {		
			PageManager.getInstance().setPageDisplayed(PageManager.CREATENEWGAME);
		}
	};
	
	private ActionListener actionListenerTutorial = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			//String authorization = RequestsWebService.getAuthorization("test1@gmail.com", "test");
			//Session session = RequestsWebService.login(authorization);
			//RequestsWebService.setCurrentSessionId(session.getSessionKey());
			RequestsWebService.closeAllGames();
		}
	};

	public void showLogin() {
		// TODO Auto-generated method stub
		
	}

	public void registerActionOnBtnPlay() {
		// TODO Auto-generated method stub
		
	}

	public void registerActionOnBtnJoinOnlineGame() {
		// TODO Auto-generated method stub
		
	}

	public void registerActionOnBtnTutorial() {
		// TODO Auto-generated method stub
		
	}

	public void registerActionOnTimerForLogin() {
		// TODO Auto-generated method stub
		
	}
	
	public Object getPanelContent(){
		return panelContent;
	}
}
