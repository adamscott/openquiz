package applicationTools;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.ImageIcon;

public class BackgroundManager extends Observable {
	private static BackgroundManager INSTANCE = null;
	
	//private final String BACKGROUNDS_PATH = System.getProperty("user.dir") + "/img/background/";
	private final String BACKGROUNDS_PATH = "data/background/";
	
	private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
	//private ImageIcon b1 = new ImageIcon(BACKGROUNDS_PATH + "Abstract Waves Background_pale.png");
	/*private ImageIcon b1 = new ImageIcon(classLoader.getResource(BACKGROUNDS_PATH + "Abstract Waves Background_pale.png"));
	private ImageIcon b2 = new ImageIcon(classLoader.getResource(BACKGROUNDS_PATH + "Abstract-Background-Vector-Graphic_pale.png"));
	private ImageIcon b3 = new ImageIcon(classLoader.getResource(BACKGROUNDS_PATH + "bg_pale.png"));
	private ImageIcon b4 = new ImageIcon(classLoader.getResource(BACKGROUNDS_PATH + "Blue-Wallpaper-colors-34503023-1920-1200_pale.png"));
	private ImageIcon b5 = new ImageIcon(classLoader.getResource(BACKGROUNDS_PATH + "Free Vector Colorful Mosaic Pattern Background_pale.png"));
	private ImageIcon b6 = new ImageIcon(classLoader.getResource(BACKGROUNDS_PATH + "FreeGreatPicture.com-19820-blue-vintage-wallpaper-background_pale.png"));
	private ImageIcon b7 = new ImageIcon(classLoader.getResource(BACKGROUNDS_PATH + "FreeVector-Colorful-Squares-Graphics_pale.png"));
	private ImageIcon b8 = new ImageIcon(classLoader.getResource(BACKGROUNDS_PATH + "iappsofts.com uploads freeppt.net background Abstract-Vector-Line-block-backgrounds_pale.png"));*/
	
	private ImageIcon b1 = new ImageIcon(BACKGROUNDS_PATH + "Background1.png");
	private ImageIcon b2 = new ImageIcon(BACKGROUNDS_PATH + "BackgroundDefault.png");
	private ImageIcon b3 = new ImageIcon(BACKGROUNDS_PATH + "Background2.png");
	private ImageIcon b4 = new ImageIcon(BACKGROUNDS_PATH + "Background3.png");
	private ImageIcon b5 = new ImageIcon(BACKGROUNDS_PATH + "Background4.png");
	private ImageIcon b6 = new ImageIcon(BACKGROUNDS_PATH + "Background5.png");
	private ImageIcon b7 = new ImageIcon(BACKGROUNDS_PATH + "Background6.png");
	private ImageIcon b8 = new ImageIcon(BACKGROUNDS_PATH + "Background7.png");

	private ImageIcon background = b2;
	
	public static BackgroundManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new BackgroundManager();
		}
			return INSTANCE;
	}
	
	public BackgroundManager(){
		
	}
	
	public ArrayList<ImageIcon> getBackgroundList(){
		ArrayList<ImageIcon> listIcons = new ArrayList<ImageIcon>();
		
		listIcons.add(b1);
		listIcons.add(b2);
		listIcons.add(b3);
		listIcons.add(b4);
		listIcons.add(b5);
		listIcons.add(b6);
		listIcons.add(b7);
		listIcons.add(b8);
		
		return listIcons;
	}
	
	public void setBackground(ImageIcon newBackground){
		background = newBackground;
		
		setChanged();
		notifyObservers();
	}
	
	public ImageIcon getBackground() {
		return background;
	}
}
