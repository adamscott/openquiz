package applicationTools;

import javax.swing.JPanel;

public class RepaintWindowManager {
	
	private static RepaintWindowManager INSTANCE = null;
	private static JPanel currentGameViewContent = null;
	private static boolean isProjectorModeEnabled = true;
	
	public static RepaintWindowManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new RepaintWindowManager();
		}
			return INSTANCE;
	}
	
	public RepaintWindowManager(){
		
	}
	
	public void registerCurrentGameViewContent(JPanel content){
		currentGameViewContent = content;
	}
	
	public void repaintCurrentGameViewContent(){
		if(currentGameViewContent != null){
			currentGameViewContent.repaint();
		}
	}
	
	public void setIsProjectorModeEnabled(boolean isProjectorModeEnabled){
		this.isProjectorModeEnabled = isProjectorModeEnabled;
	}
	
	public boolean getIsProjectorModeEnabled(){
		return this.isProjectorModeEnabled;
	}

}
