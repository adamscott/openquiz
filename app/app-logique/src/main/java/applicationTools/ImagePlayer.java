package applicationTools;

import java.util.ArrayList;
import java.util.List;

public class ImagePlayer {
	private List<String> imagePaths;
	private int idx;
	
	public ImagePlayer(){
		imagePaths = new ArrayList<String>();
		idx = 0;
	}
	
	public List<String> getImagePaths(){
		return this.imagePaths;
	}
	
	public void setImagePaths(List<String> imagePaths){
		this.imagePaths = imagePaths;
	}
	
	public String getNextImage(){
		if (idx+1 < imagePaths.size()){
			return imagePaths.get(++idx);
		}
		return null;
	}
	
	public String getPreviousImage(){
		if (idx-1 >= 0){
			return imagePaths.get(--idx);
		}
		return null;
	}
	
	public boolean isLastImage(){
		if (idx == imagePaths.size()-1){
			return true;
		}
		return false;
	}
	
	public boolean isFirstImage(){
		if (idx == 0){
			return true;
		}
		return false;
	}

	public String getCurrentImageName() {
		if (idx >= 0 && idx < imagePaths.size()){
			return imagePaths.get(idx);
		}
		return "";
	}

}
