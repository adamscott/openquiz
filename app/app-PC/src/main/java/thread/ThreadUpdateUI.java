package thread;

public class ThreadUpdateUI {

	private static ThreadUpdateUI INSTANCE = null;
	
	public static ThreadUpdateUI getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new ThreadUpdateUI();
		}
			return INSTANCE;
	}
	
	public void startUIUpdate(){
		
	}

}
