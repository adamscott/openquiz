package applicationTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileManager {

	private final String PATH_SAVED_GAME = "data/savedGame/";
	private final String gameDataFileNamePrefix = "GameData_";
	private final String gameDataFileExtension = ".opz";
	private final String lockPath = "gameInProgress.lock";
	private static FileManager INSTANCE = null;
	
	public static FileManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new FileManager();
		}
			return INSTANCE;
	}
	
	public Boolean checkIfFileExist(String filepath){
		File f = new File(filepath);
		
		if(f.exists() && f.isFile()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Boolean checkIfFileExist(File f){
		if(f.exists() && f.isFile()){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public File openFile(String filepath){
		File f = new File(filepath);
		if(checkIfFileExist(f)){
			return f;
		}
		else{
			return null;
		}
	}
	
	public void createFile(String filepath){
		File f = new File(filepath);
		
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteFile(String filepath){
		File f = new File(filepath);
		
		f.delete();
	}
	
	public void write(String filepath, String content){
		 
		File file = new File(filepath);

		// if file doesnt exists, then create it
		if (!file.exists()) {
			createFile(filepath);
		}

		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String read(String filepath){
		
		BufferedReader br = null;
		String fileContent = "";
		
		try {
			String currentLine;
			
			br = new BufferedReader(new FileReader(filepath));
 
			while ((currentLine = br.readLine()) != null) {
				fileContent += currentLine;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
 
		return fileContent;
	}
	
	public void createGameInProgressLock(){
		createFile(lockPath);
	}
	
	public void releaseGameInProgressLock(){
		deleteFile(lockPath);
	}
	
	@Deprecated
	public Boolean isGameInProgressLockExist(){
		return checkIfFileExist(lockPath);
	}
	
	public Boolean isGameInProgressLocally(String gameKey){
		if (gameKey == null || gameKey == ""){
			return false;
		}
		return checkIfFileExist(getGameDataFile(gameKey));
	}
	
	public String getGameDataFile(String gameKey){
		return PATH_SAVED_GAME + gameDataFileNamePrefix + gameKey + gameDataFileExtension;
	}
	
	public void deleteGameDataFile(String gameKey){
		deleteFile(getGameDataFile(gameKey));
	}
	
	public String getQuestionMediaContentType(String mediaUrl){
		String contentType = "";
		URL url;
		try {
			url = new URL(mediaUrl);
			HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
			connection.setRequestMethod("HEAD");
			connection.connect();
			contentType = connection.getContentType();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentType;
	}
}
