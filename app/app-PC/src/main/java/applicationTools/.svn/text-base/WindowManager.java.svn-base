package applicationTools;

import currentGame.CurrentGameView;
import mainWindow.MainWindow;

public class WindowManager {

	private static WindowManager INSTANCE = null;
	private static MainWindow mainWindow = null;
	private static CurrentGameView gameView = null;
	private static CurrentGameView projView = null;
	
	public static WindowManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new WindowManager();
		}
		return INSTANCE;
	}
	
	public MainWindow getMainWindow(){
		return mainWindow;
	}
	
	public void setMainWindow(MainWindow mainWindow){
		WindowManager.mainWindow = mainWindow;
	}
	
	public CurrentGameView getGameView() {
		return gameView;
	}

	public void setGameView(CurrentGameView gameView) {
		WindowManager.gameView = gameView;
	}

	public CurrentGameView getProjView() {
		return projView;
	}

	public void setProjView(CurrentGameView projView) {
		WindowManager.projView = projView;
	}

}
