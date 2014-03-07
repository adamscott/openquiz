package applicationTools;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;

public class DownloadManager  implements Runnable{
	private AtomicInteger maxLoop = new AtomicInteger(200);
	private AtomicInteger loop = new AtomicInteger(0);
	private AtomicBoolean isClose = new AtomicBoolean(false);
	private List<String[]> filesToDownload = new ArrayList<String[]>();
	
	public void run() {
		while(true){
			//Get next file to downlaod
			String[] file = getNextFileToDownload();
			if(file[0] != "" && file[1] != ""){
				downloadURLToLocalFile(file[0], file[1]);	
			}
        	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public DownloadManager(){
		
	}
	
	private synchronized boolean getClose(){
		return (isClose.get() || loop.incrementAndGet() > maxLoop.get());
	}
	
	public synchronized void setClose(boolean isClose){
		this.isClose.set(isClose);
	}
	
	public synchronized void setFilesToDownload(List<String[]> filesToDownload){
		this.filesToDownload = filesToDownload;
		isClose.set(false);
		loop.set(0);
	}
	
	public synchronized String[] getNextFileToDownload(){
		String path[] = {"",""};
		if (filesToDownload.size() != 0){
			path = filesToDownload.get(0);
			filesToDownload.remove(0);
		}
		else if (filesToDownload.size() == 0){
			isClose.set(true);
		}
		return path;
	}

	private synchronized void downloadURLToLocalFile(String url, String filePath){
		if (!FileManager.getInstance().checkIfFileExist(filePath)){
			FileManager.getInstance().createFile(filePath);
			try {
				FileUtils.copyURLToFile(new URL(url), new File(filePath));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
