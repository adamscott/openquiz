package applicationTools;

public class StatusManager {
	
	private boolean isVideoPlayerAvailable = true;

	private static StatusManager INSTANCE = null;
	
	public static StatusManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new StatusManager();
		}
			return INSTANCE;
	}
	
	public boolean isVideoPlayerAvailable() {
		return isVideoPlayerAvailable;
	}

	public void setVideoPlayerAvailable(boolean isVideoPlayerAvailable) {
		this.isVideoPlayerAvailable = isVideoPlayerAvailable;
	}

}
