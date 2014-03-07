package applicationTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.tritonus.share.sampled.file.TAudioFileFormat;


//TODO - Do that thread again it sucks.
public class SoundPlayer extends Thread{
	AudioInputStream din;
	AudioInputStream in;
	SourceDataLine line;
	AudioFormat decodedFormat;
	AudioFormat baseFormat;
	AudioFileFormat fileFormat;
	int nBytesRead = 0, nBytesWritten = 0;
	byte[] data = new byte[4096];
	String filename = "";
	List<String> filePaths = new ArrayList<String>();
	String buzzerLeftSoundPath = SoundManager.getBuzzerSoundLeft();
	String buzzerRightSoundPath = SoundManager.getBuzzerSoundRight();
	private int currentIdx = 0;
	
	Command command = Command.NOTHING;
	Command lastCommand = Command.NOTHING;
	
	boolean isFileBeingLoaded = false;
	private boolean isClose = false;
	
	public void run(){
		//Thread main looop
		while(!isClose){
			switch (command){
			case PLAY:
				playTrack();
				break;
				
			case PAUSE:
				pauseTrack();
				break;
				
			case STOP:
				stopTrack();
				break;
				
			case LOADFILE:
				loadSoundFile();
				break;
			case NOTHING:
				
				break;
			case EXIT:
				break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void killPlayer(){
		isClose = true;
	}
	
	private void playTrack()
	{
		if (lastCommand == Command.STOP){
			loadSoundFile();
		}
		if (line != null) {
			lastCommand = Command.PLAY;
			// Start line
			if (!line.isActive()) {
				line.start();
			}
			
			try {
				nBytesRead = din.read(data, 0, data.length);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (nBytesRead != -1)
				nBytesWritten = line.write(data, 0, nBytesRead);
			else {
				// Stop
				command = Command.STOP;
				stopTrack();
			}
		}
	}
	
	private void loadSoundFile()
	{
		isFileBeingLoaded = true;
		//Wait to be sure filename as been changed.
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (filename != "" && lastCommand != Command.LOADFILE)
		{
			try {
				File file = new File(filename);
				in = AudioSystem.getAudioInputStream(file);
				fileFormat = AudioSystem.getAudioFileFormat(file);
				baseFormat = in.getFormat();
				decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
						baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
						baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
						false);
				din = AudioSystem.getAudioInputStream(decodedFormat, in);
				try {
					line = getLine(decodedFormat);
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				// Handle exception.
			}
		}
		lastCommand = Command.LOADFILE;
		isFileBeingLoaded = false;
	}
	
	private void pauseTrack(){
		if (line != null){
			line.stop();
		}
		lastCommand = Command.PAUSE;
	}
	
	private void stopTrack(){
		if (line != null){
			line.stop();
			line.flush();
			line.close();
		}
		lastCommand = Command.STOP;
	}
	
	public void setFilename(String filename){
		this.filename = filename;
	}
	
	public void setCommand(Command newCommand){
		this.command = newCommand;
	}
	
	public void setFilePaths(List<String> filePaths){
		this.filePaths = filePaths;
	}
	
	public void loadNextFile(){
		if (currentIdx + 1 < filePaths.size()) {
			currentIdx++;
			setFilename(filePaths.get(currentIdx));
			setCommand(Command.LOADFILE);
			// TODO - Find a better way to wait for a file to be loaded.
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void playLeftBuzzerSound(){
		loadBuzzerLeftSoundFile();
		playTrack();
	}
	
	public void playRightBuzzerSound(){
		loadBuzzerRightSoundFile();
		playTrack();
	}
	
	public void loadBuzzerLeftSoundFile(){
		setFilename(buzzerLeftSoundPath);
		setCommand(Command.LOADFILE);
		// TODO - Find a better way to wait for a file to be loaded.
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadBuzzerRightSoundFile(){
		setFilename(buzzerRightSoundPath);
		setCommand(Command.LOADFILE);
		// TODO - Find a better way to wait for a file to be loaded.
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadPreviousFile(){
		if (currentIdx - 1 >= 0) {
			currentIdx--;
			setFilename(filePaths.get(currentIdx));
			setCommand(Command.LOADFILE);
			// TODO - Find a better way to wait for a file to be loaded.
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getAudioTrackName(){
		return filePaths.get(currentIdx);
	}
	
	public void loadFirstFile(){
		currentIdx = 0;
		setFilename(filePaths.get(0));
		setCommand(Command.LOADFILE);
		// TODO - Find a better way to wait for a file to be loaded.
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean isFirstAudioTrack(){
		if (currentIdx == 0){
			return true;
		}
		return false;
	}
	
	public boolean isLastAudioTrack(){
		if(currentIdx == filePaths.size()-1){
			return true;
		}
		return false;
	}
	
	public int getTotalTrackTimeInMilliseconds(){
		int totalDuration = 0;
	    
		//If a file is being loaded, wait for it to finish.
		if (fileFormat != null){
		    if (fileFormat instanceof TAudioFileFormat) {
		        Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
		        String key = "duration";
		        Long microseconds = (Long) properties.get(key);
		        totalDuration = (int) (microseconds / 1000);
		    } 
		}
		return totalDuration;
	}
	
	public int getCurrentTrackTimeInMilliseconds(){
		int currentTime = 0;
		if (line != null){
			currentTime = (int)line.getMicrosecondPosition()/1000;
		}
		return currentTime;
	}
	
	private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
	{
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
	}
}
