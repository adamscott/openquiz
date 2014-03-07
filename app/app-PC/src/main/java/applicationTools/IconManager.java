package applicationTools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class IconManager 
{		
	private static final String IMG_PATH = "img/";
	private static final String ICONS_PATH = IMG_PATH + "icons/";
	
	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
	private static ImageIcon pencilIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "pencil.png"));
	private static ImageIcon redCrossIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "redcross.png"));
	private static ImageIcon greenCheckIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "greencheck.png"));
	private static ImageIcon backArrowIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "back.png"));
	private static ImageIcon nextArrowIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "next.png"));
	private static ImageIcon removeIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "remove.png"));
	private static ImageIcon warningYellowIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "signalwarning.png"));
	private static ImageIcon gearIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "gear.png"));
	private static ImageIcon jjbIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "jjblogo.png"));
	private static ImageIcon headLeftIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "headiconleft.png"));
	private static ImageIcon headRightIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "headiconright.png"));
	private static ImageIcon homeIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "home.png"));
	private static ImageIcon openQuizLogoWithName = new ImageIcon(classLoader.getResource(IMG_PATH + "OpenQuizLogoWithName_small.png"));
	private static ImageIcon openQuizLogoWithNameBig = new ImageIcon(classLoader.getResource(IMG_PATH + "OpenQuizLogoWithName.png"));
	private static ImageIcon minimizeIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "remove.png"));
	private static ImageIcon addIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "add.png"));
	private static ImageIcon questionIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "question.png"));
	private static ImageIcon starIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "fullstar.png"));
	private static ImageIcon playIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "play.png"));
	private static ImageIcon pauseIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "pause.png"));
	private static ImageIcon stopIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "stop.png"));
	private static ImageIcon prevSongIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "prevSong.png"));
	private static ImageIcon nextSongIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "nextSong.png"));
	private static ImageIcon lightIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "light.png"));
	private static ImageIcon refreshIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "refresh.png"));
	private static ImageIcon logoutIcon = new ImageIcon(classLoader.getResource(ICONS_PATH + "logout.png"));
	private static ImageIcon sherbrookeLogo = new ImageIcon(classLoader.getResource(ICONS_PATH + "sherbrooke.jpg"));

	/**
	 * 
	 * @param _img: ImageIcon to resize
	 * @param x: New x position
	 * @param y: New y position
	 * @param height: New height
	 * @param width: New width
	 * @return Redized ImageIcon
	 */
	public static ImageIcon resizeIcon(ImageIcon _img, int x, int y, int height, int width)
	{
		Image img = _img.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, x, y, width, height, null);
		ImageIcon newIcon = new ImageIcon(bi);
	  
		return newIcon;
	}
	
	public static ImageIcon getPencilIcon()
	{
		return pencilIcon;
	}
	
	public static ImageIcon getRedCrossIcon() {
		return redCrossIcon;
	}

	public static ImageIcon getHomeIcon() {
		return homeIcon;
	}
	
	public static ImageIcon getCloseXIcon()
	{
		return redCrossIcon;
	}
	public static ImageIcon getMinimizeIcon()
	{
		return minimizeIcon;
	}
	
	public static ImageIcon getGreenCheckIcon()
	{
		return greenCheckIcon;
	}
	
	public static ImageIcon getBackArrowIcon()
	{
		return backArrowIcon;
	}
	
	public static ImageIcon getNextArrowIcon()
	{
		return nextArrowIcon;
	}
	
	public static ImageIcon getRemoveIcon()
	{
		return removeIcon;
	}
	
	public static ImageIcon getWarningYellowIcon()
	{
		return warningYellowIcon;
	}
	
	public static ImageIcon getGearIcon()
	{
		return gearIcon;
	}
	
	public static ImageIcon getJjbIcon()
	{
		return jjbIcon;
	}
	
	public static ImageIcon getHeadRightIcon()
	{
		return headRightIcon;
	}
	
	public static ImageIcon getHeadLeftIcon()
	{
		return headLeftIcon;
	}

	public static ImageIcon getOpenQuizLogoWithNameIcon()
	{
		return openQuizLogoWithName;
	}
	
	public static ImageIcon getOpenQuizLogoWithName() {
		return openQuizLogoWithName;
	}
	
	public static ImageIcon getOpenQuizLogoWithNameBig() {
		return openQuizLogoWithNameBig;
	}
	
	public static ImageIcon getAddIcon()
	{
		return addIcon;
	}
	
	public static ImageIcon getQuestionIcon()
	{
		return questionIcon;
	}	
	
	public static ImageIcon getStarIcon(){
		return starIcon;
	}
	
	public static ImageIcon getPlayIcon(){
		return playIcon;
	}
	
	public static ImageIcon getPauseIcon(){
		return pauseIcon;
	}
	
	public static ImageIcon getStopIcon(){
		return stopIcon;
	}
	
	public static ImageIcon getNextSongIcon(){
		return nextSongIcon;
	}

	public static ImageIcon getPrevSongIcon(){
		return prevSongIcon;
	}

	public static ImageIcon getLightIcon(){
		return lightIcon;
	}

	public static ImageIcon getRefreshIcon(){
		return refreshIcon;
	}
	
	public static ImageIcon getLogoutIcon(){
		return logoutIcon;
	}
	
	public static ImageIcon getSherbrookeLogo(){
		return sherbrookeLogo;
	}

}
