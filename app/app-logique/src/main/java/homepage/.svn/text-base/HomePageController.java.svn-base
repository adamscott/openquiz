package homepage;

import login.LoginController;
import serialization.PreferencesImportExport;
import applicationTools.LoginManager;
import applicationTools.PageManager;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Game;

public class HomePageController {
	private IHomePageView view;
	
	public HomePageController() {
		
	}

	public void actionPlay(){
//		PageManager.getInstance().setPageDisplayed(PageManager.CREATENEWGAME);
	}
	
	public Object getView(){
		return view;
	}
	
	//TODO something with this.
	public void actionTutorial(){
		//Game game = RequestsWebService.getGame("ahFzfm9wZW5xdWl6cHJvamVjdHIRCxIER2FtZRiAgICAwLSmCQw");
		//game.setActive(false);
//		RequestsWebService.editGame(game);
	}
	
	//TODO Ya surement un moyen plus efficace de faire sa. Popper la fenetre de login la premiere fois
	//que l'application démarre
	//TODO Ask JS for this shitty shit.
	public void init(){
		if (LoginManager.getInstance().getLoggedUsername().equals("")){
			PreferencesImportExport.getInstance().loadPreferencesFromFile();
			int delay = 100;
			
			LoginController loginController = new LoginController();
			//Timer timer = new Timer(delay, taskPerformer);
			//timer.start();
			//timer.setRepeats(false);
		}
	}
	
	public void actionShowLogin(){
		view.showLogin();
	}
	
	public void setView(IHomePageView view){
		this.view = view;
	}
}
