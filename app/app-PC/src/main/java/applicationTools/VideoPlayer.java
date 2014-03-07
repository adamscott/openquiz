package applicationTools;

import java.awt.BorderLayout;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import com.sun.jna.NativeLibrary;

public class VideoPlayer extends JPanel{

	private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	private File vlcInstallPath = new File("data/vlc");
    private EmbeddedMediaPlayer player;
    private List<String> filePaths = new ArrayList<String>();
    private int currentIdx;
    private AtomicBoolean isPaused = new AtomicBoolean(false);
    
	public VideoPlayer(){
		currentIdx = 0;
		if(StatusManager.getInstance().isVideoPlayerAvailable()){
			try{
				NativeLibrary.addSearchPath("libvlc", vlcInstallPath.getAbsolutePath());
				System.setProperty("jna.library.path", vlcInstallPath.getAbsolutePath());
				//NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcInstallPath.getPath());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
	        EmbeddedMediaPlayerComponent videoCanvas = null;
	        try{
	        	videoCanvas = new EmbeddedMediaPlayerComponent();
	        }catch(Exception e){
				StatusManager.getInstance().setVideoPlayerAvailable(false);
				System.out.println(e.getMessage());
	        	System.out.println("Could not load VLC libraries. See JS for this.");
	        }
	        this.setLayout(new BorderLayout());
	        if(videoCanvas != null){
	            this.add(videoCanvas, BorderLayout.CENTER);
	            this.player = videoCanvas.getMediaPlayer();
	        }
		}
	}
	
	public void setPlayer(EmbeddedMediaPlayer player){
		this.player = player;
	}
	
	public EmbeddedMediaPlayer getPlayer(){
		return this.player;
	}
	
	public void loadFile(String filepath){
		if (!FileManager.getInstance().checkIfFileExist(filepath)){
			System.out.println("Could not load media " + filepath);
		}
		if (player != null){
	        player.prepareMedia(filepath);
	        player.parseMedia();
		}
	}
	
	public void loadNextVideo(){
		if (currentIdx + 1 < filePaths.size()) {
			currentIdx++;
			loadFile(filePaths.get(currentIdx));
		}
	}
	
	public void loadPreviousVideo(){
		if (currentIdx - 1 >= 0) {
			currentIdx--;
			loadFile(filePaths.get(currentIdx));
		}
	}
	
	public void setFilePaths(List<String> filePaths){
		this.filePaths = filePaths;
	}
	
    public void play() {
    	if (player != null){
    		if(isPaused.get()){player.play();}
    		else{player.playMedia(filePaths.get(0));}
    		isPaused.set(false);
    	}
    }
    
    public void stop(){
    	if (player != null){
    		player.stop();
    	}
    }
    
    public void pause(){
    	if (player != null){
    		player.pause();
    		isPaused.set(true);
    	}
    }
    
    public long getTotalVideoTimeInMilliseconds(){
    	if (player != null){
    		return player.getMediaMeta().getLength();
    	}
    	return 0;
    }
    
    public long getCurrentVideoTimeInMilliseconds(){
    	if (player != null){
    		return player.getTime();
    	}
    	return 0;
    }
    
    public boolean isFirstVideo(){
    	if(currentIdx == 0){
    		return true;
    	}
    	return false;
    }
    
    public boolean isLastVideo(){
    	if(currentIdx == filePaths.size()-1){
    		return true;
    	}
    	return false;
    }

	public String getCurrentVideoName() {
		return filePaths.get(currentIdx);
	}
	
	public void loadFirstVideo(){
		currentIdx = 0;
		loadFile(filePaths.get(0));
	}
	
	public void setVolume(int volume){
		if (player != null){
			player.setVolume(volume);
		}
	}
	
	public void killPlayer(){
		if (player != null){
			player.stop();
			player.release();
		}
	}
}
