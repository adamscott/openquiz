package currentGame;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.IconManager;

public class TeamPanel extends JPanel{
	private static final long serialVersionUID = -8842003795490312585L;
	private JLabel lblTeamName;
	private JLabel lblTeamLogo;
	private JLabel lblTeamScore;
	private CustomButton btnIncreasePoints; 
	private CustomButton btnDecreasePoints;
	private String side;
	
	public TeamPanel(String side){
		this.side = side;
		lblTeamName = new JLabel("");
		btnDecreasePoints = new CustomButton("", IconManager.getRemoveIcon(), ButtonManager.getIconButtonDimensionSmall());
		lblTeamScore = new JLabel("0");
		btnIncreasePoints = new CustomButton("", IconManager.getAddIcon(), ButtonManager.getIconButtonDimensionSmall());
		lblTeamLogo = new JLabel(getIcon(IconManager.getSherbrookeLogo(), 75, 75));
		initGui();
	}
	
	public void initGui(){
		
		lblTeamScore.setFont(new Font("Arial", Font.PLAIN, 32));
		lblTeamName.setFont(new Font("Arial", Font.PLAIN, 18));
		
		if(side == "Left"){
			setLayout(new MigLayout("", "[grow][][][][10px:10px:10px]", "[]0[grow]"));
			add(lblTeamName, "cell 0 0 3 1,alignx center");
			add(lblTeamLogo,"flowx,cell 0 1 1 1,alignx center,aligny center");
			add(btnIncreasePoints, "cell 3 1");
			add(btnDecreasePoints, "cell 1 1");
			add(lblTeamScore,"cell 2 1,alignx center,aligny center");
		}
		else if(side == "Right"){
			setLayout(new MigLayout("", "[10px:10px:10px][][][][grow]", "[]0[grow]"));
			add(btnIncreasePoints, "cell 3 1");
			add(btnDecreasePoints, "cell 1 1");
			add(lblTeamScore,"cell 2 1,alignx center,aligny center");
			add(lblTeamLogo,"flowx,cell 4 1,alignx center,aligny center");
			add(lblTeamName, "cell 0 0 5 1,alignx center");
		}
		
		setOpaque(false);
	}
	
	public void setTeamName(String teamName){
		lblTeamName.setText(teamName);
	}
	
	public void setTeamScore(String teamScore){
		lblTeamScore.setText(teamScore);
	}
	
	public void disableScoreLabel(){
		lblTeamScore.setVisible(false);
	}
	
	public void enableScoreLabel(){
		lblTeamScore.setVisible(true);
	}
	
	public CustomButton getBtnIncreasePoints(){
		return this.btnIncreasePoints;
	}
	
	public CustomButton getBtnDecreasePoints(){
		return this.btnDecreasePoints;
	}

	public void disableTeamScoreEditButton() {
		btnDecreasePoints.setVisible(false);
		btnIncreasePoints.setVisible(false);
	}
	
	private ImageIcon getIcon(ImageIcon imageIcon, int width, int height){
		ImageIcon imgIcon = imageIcon;
		Image image = null;
		image = imgIcon.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH);
		imgIcon.setImage(image);
		return imgIcon;
	}
}
