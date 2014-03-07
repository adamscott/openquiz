package currentGame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.ColorManager;
import applicationTools.CustomButton;
import applicationTools.FontManager;
import applicationTools.IconManager;
import applicationTools.MultilineLabel;

public class PlayerInfoZonePanel extends JPanel {
	private static final long serialVersionUID = 4104964483290183558L;
	private JLabel lblPlayerIcon;
	private JLabel lblPlayerScore;
	private JPanel panelPlayerName;
	private String zonePosition;
	private String playerName;
	private JLabel lblPlayerQuestionScores;
	private JSpinner spinPlayerScore;
	private CustomButton btnIncreasePoints;
	private CustomButton btnDecreasePoints;
	
	public PlayerInfoZonePanel(String _position) {
		zonePosition = _position;
		panelPlayerName = new JPanel();
		spinPlayerScore = new JSpinner();
		lblPlayerQuestionScores = new JLabel("0");
		lblPlayerScore = new JLabel("0");
		lblPlayerIcon = new JLabel();
		btnIncreasePoints = new CustomButton("", IconManager.getAddIcon(), ButtonManager.getIconButtonDimensionSmall());
		btnDecreasePoints = new CustomButton("", IconManager.getRemoveIcon(), ButtonManager.getIconButtonDimensionSmall());
		initGUI();
	}
	
	private void initGUI() {
		/*setLayout(new MigLayout("", "[grow]0[grow]", "[50%]0[50%]"));
		setBackground(ColorManager.getPlayerDefaultColor());
		setBorder(new LineBorder(Color.black));
		
		if(zonePosition == "Left") {
			lblPlayerIcon.setIcon(IconManager.getHeadLeftIcon());
			add(lblPlayerIcon, "cell 0 0,alignx center");
			add(lblPlayerScore, "cell 1 0,alignx center");
		}
		else {
			lblPlayerIcon.setIcon(IconManager.getHeadRightIcon());
			lblPlayerScore.setAlignmentX(CENTER_ALIGNMENT);
			add(lblPlayerIcon, "cell 1 0,alignx center");
			add(lblPlayerScore, "cell 0 0,alignx center");
		}
		lblPlayerScore.setHorizontalAlignment(JLabel.CENTER);
		lblPlayerScore.setFont(FontManager.getQuestionZoneMainLabel());
		//lblPlayerScore.setMinimumSize(new Dimension(60,30));
		
		panelPlayerName.setOpaque(false);
		panelPlayerName.setBackground(ColorManager.getTextPaneColor());

		add(panelPlayerName, "cell 0 1, alignx left, aligny center, dock south");
		
		lblPlayerScore.addMouseListener(mouseListenerScoreLabelClicked);*/

		//setLayout(new MigLayout("", "[50%:n:50%]0[20px:20px:20px]0[20px:20px:20px,grow]0[20px:20px:20px][20px:n:20px]", "[40%]0[30px:30px:30px]0[30%]"));
		setBackground(ColorManager.getPlayerDefaultColor());
		setBorder(new LineBorder(Color.black));
		
		if(zonePosition == "Left") {
			setLayout(new MigLayout("", "[50%:50%:50%]0[10%:10%:10%]0[10%:10%:10%]0[10%:10%:10%][20px:n:20px]", "[50%:50%]0[30px:n:10%]0[30%:30%]"));
			lblPlayerScore.setFont(FontManager.getQuestionZoneMainLabel());
			lblPlayerIcon.setIcon(IconManager.getHeadLeftIcon());
			lblPlayerScore.setAlignmentX(CENTER_ALIGNMENT);
			add(btnIncreasePoints, "cell 3 1,alignx left");
			add(btnDecreasePoints, "cell 1 1,alignx right");
			add(lblPlayerScore, "cell 2 0,alignx center");
			add(lblPlayerIcon, "cell 0 0 1 2,alignx left");
			add(lblPlayerQuestionScores, "flowx,cell 2 1,alignx center");
			add(panelPlayerName, "flowx,cell 0 2 4 1,alignx center,aligny center");
		}
		else {
			setLayout(new MigLayout("", "[20px:n:20px][10%:10%:10%]0[10%:10%:10%]0[10%:10%:10%]0[50%:50%:50%]", "[50%:50%]0[30px:n:10%]0[30%:30%]"));
			lblPlayerScore.setFont(FontManager.getQuestionZoneMainLabel());
			lblPlayerIcon.setIcon(IconManager.getHeadRightIcon());
			lblPlayerScore.setAlignmentX(CENTER_ALIGNMENT);
			add(btnDecreasePoints, "cell 1 1,alignx right");
			add(btnIncreasePoints, "cell 3 1,alignx left");
			add(lblPlayerScore, "cell 2 0,alignx center");
			add(lblPlayerIcon, "cell 4 0,alignx right");
			add(lblPlayerQuestionScores, "flowx,cell 2 1,alignx center");
			add(panelPlayerName, "flowx,cell 1 2 4 1,alignx center,aligny center");
		}
		
		lblPlayerQuestionScores.setFont(FontManager.getPlayerZoneQuestionScore());
		
		panelPlayerName.setOpaque(false);
		panelPlayerName.setBackground(ColorManager.getTextPaneColor());
		
		hidePlayerPointsEdition();
	}
	
	public void setPanelSide(String side){
		zonePosition = side;
		remove(btnDecreasePoints);
		remove(btnIncreasePoints);
		remove(lblPlayerScore);
		remove(lblPlayerIcon);
		remove(lblPlayerQuestionScores);
		remove(panelPlayerName);
		initGUI();
	}
	
	private MouseListener mouseListenerScoreLabelClicked = new MouseListener(){

		public void mouseClicked(MouseEvent arg0) {
			if(zonePosition == "Left") {
				remove(lblPlayerScore);
				add(spinPlayerScore, "cell 1 0,alignx center");
			}
			else {
				remove(lblPlayerScore);
				add(spinPlayerScore, "cell 0 0,alignx center");
			}
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public void setPlayerIcon(Icon icon) {
		lblPlayerIcon.setIcon(icon);
	}
	
	public void setPlayerScore(String score) {
		lblPlayerScore.setText(score);
	}
	
	public void setPlayerName(String name) {
		playerName = name;
		
		panelPlayerName.removeAll();
		panelPlayerName.add(MultilineLabel.getComponentListInPanel(MultilineLabel.multiLabelText(name, getWidth(), FontManager.getPlayerZonePlayerName()), getWidth(), (int)(0.45*getHeight()), false));
		revalidate();
	}
	
	public String getPlayerName(){
		return playerName;
	}
	
	public void setScoreVisible(boolean isVisible) {
		lblPlayerScore.setVisible(isVisible);
	}
	
	public JButton getBtnIncreasePoints(){
		return this.btnIncreasePoints;
	}
	
	public JButton getBtnDecreasePoints(){
		return this.btnDecreasePoints;
	}
	
	public void showPlayerPointsEdition(){
		lblPlayerQuestionScores.setVisible(true);
		btnDecreasePoints.setVisible(true);
		btnIncreasePoints.setVisible(true);
	}
	
	public void hidePlayerPointsEdition(){
		lblPlayerQuestionScores.setVisible(false);
		btnDecreasePoints.setVisible(false);
		btnIncreasePoints.setVisible(false);
	}

	public void adjutPlayerIndividualScore(int value) {
		int oldValue = Integer.parseInt(lblPlayerQuestionScores.getText());
		oldValue += value;
		lblPlayerQuestionScores.setText(Integer.toString(oldValue));	
	}
	
	public void setPlayerIndividualScore(int value) {
		lblPlayerQuestionScores.setText(Integer.toString(value));
	}
}
