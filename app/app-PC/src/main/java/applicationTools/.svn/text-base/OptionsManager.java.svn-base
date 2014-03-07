package applicationTools;

import mainWindow.MainWindow;

public class OptionsManager {
	private static OptionsManager INSTANCE = null;
	private boolean isGameWindowPlayersInNormalPosition;
	private boolean isProjWindowPlayersInNormalPosition;
	
	public static OptionsManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new OptionsManager();
		}
		return INSTANCE;
	}
	
	private OptionsManager(){
		isGameWindowPlayersInNormalPosition = true;
		isProjWindowPlayersInNormalPosition = true;
	}
	
	public boolean isGameWindowPlayersInNormalPosition() {
		return isGameWindowPlayersInNormalPosition;
	}

	public void setGameWindowPlayersInNormalPosition(
			boolean isGameWindowPlayersInNormalPosition) {
		this.isGameWindowPlayersInNormalPosition = isGameWindowPlayersInNormalPosition;
	}

	public boolean isProjWindowPlayersInNormalPosition() {
		return isProjWindowPlayersInNormalPosition;
	}

	public void setProjWindowPlayersInNormalPosition(
			boolean isProjWindowPlayersInNormalPosition) {
		this.isProjWindowPlayersInNormalPosition = isProjWindowPlayersInNormalPosition;
	}
}
