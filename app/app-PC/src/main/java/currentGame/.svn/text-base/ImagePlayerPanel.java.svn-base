package currentGame;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class ImagePlayerPanel extends MediaPlayerBasePanel{
	private static final long serialVersionUID = 1L;
	private JLabel lblCurrentImage;
	private int defaultWidth;
	private int defaultHeight;
	
	public ImagePlayerPanel(){
		lblCurrentImage = new JLabel("");
		defaultWidth = 200;
		defaultHeight = 200;
		initGui();
	}
	
	private void initGui(){
		//JPanel mediaContentPanel = this;		
		mediaContentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		mediaContentPanel.add(lblCurrentImage, "cell 0 0,alignx center,aligny center");
	}
	
	public void setImageWithSize(String path, int defaultWidth, int defaultHeight){
		this.defaultHeight = defaultHeight;
		this.defaultWidth = defaultWidth;
		setImage(path);
	}
	
	public void resetImage(){
		lblCurrentImage.setIcon(new ImageIcon());
	}
	
	public void hideMediaContent(){
		mediaContentPanel.setVisible(false);
		getPanelMedia().setVisible(false);
	}
	
	public void setImage(String path){
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new URL(path));
			ImageIcon imgIcon = new ImageIcon(myPicture);
			Image image = null;
			int imgHeight = imgIcon.getIconHeight();
			int imgWidth =  imgIcon.getIconWidth();
			//System.out.println(defaultWidth + " " + defaultHeight);
			if (imgWidth-100 >= imgHeight){
				image = imgIcon.getImage().getScaledInstance(defaultWidth/2, -1, Image.SCALE_SMOOTH);
			}
			else{
				image = imgIcon.getImage().getScaledInstance(-1, defaultHeight-100, Image.SCALE_SMOOTH);
			}
			//image = imgIcon.getImage().getScaledInstance(-1, defaultHeight-100, Image.SCALE_SMOOTH);
			//image = imgIcon.getImage().getScaledInstance(defaultWidth-100, -1, Image.SCALE_SMOOTH);
			imgIcon.setImage(image);
			lblCurrentImage.setIcon(imgIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setProjectorMode(){
		super.setProjectorMode();
	}
}
