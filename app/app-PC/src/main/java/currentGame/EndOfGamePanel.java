package currentGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.ColorManager;
import applicationTools.CustomButton;
import applicationTools.FileManager;
import applicationTools.FontManager;
import applicationTools.IconManager;
import applicationTools.PageManager;
import applicationTools.QuestionManager;
import applicationTools.ScoreManager;
import applicationTools.TeamManager;

import javax.swing.JButton;

import structures.QuestionTest;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.TeamPosition;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.GameLog;
import ca.openquiz.comms.response.BaseResponse;

public class EndOfGamePanel extends JPanel {
	private static final long serialVersionUID = -5622725248671052430L;
	private JLabel lblTitle;
	private JLabel lblWinningTeamPlayer1;
	private JLabel lblWinningTeamPlayer2;
	private JLabel lblWinningTeamPlayer3;
	private JLabel lblWinningTeamPlayer4;
	private JLabel lblWinningPlayer1Efficiency;
	private JLabel lblWinningPlayer2Efficiency;
	private JLabel lblWinningPlayer3Efficiency;
	private JLabel lblWinningPlayer4Efficiency;
	private JLabel lblLosingTeamPlayer1;
	private JLabel lblLosingTeamPlayer2;
	private JLabel lblLosingTeamPlayer3;
	private JLabel lblLosingTeamPlayer4;
	private JLabel lblLosingPlayer1Efficiency;
	private JLabel lblLosingPlayer2Efficiency;
	private JLabel lblLosingPlayer3Efficiency;
	private JLabel lblLosingPlayer4Efficiency;
	private JButton btnCloseGame;

	public EndOfGamePanel() {

		lblTitle  = new JLabel("Partie terminée");
		lblWinningTeamName = new JLabel("");
		lblWinningTeamScore = new JLabel("");
		lblWinningPlayer1Score = new JLabel("");
		lblWinningTeamPlayer1 = new JLabel("");
		lblWinningPlayer2Score = new JLabel("");
		lblWinningPlayer3Score = new JLabel("");
		lblWinningPlayer4Score = new JLabel("");
		lblLosingTeamName = new JLabel("");
		lblLosingTeamScore = new JLabel("");
		lblLosingPlayer1Score = new JLabel("");
		lblLosingPlayer2Score = new JLabel("");
		lblLosingPlayer3Score = new JLabel("");
		lblLosingPlayer4Score = new JLabel("");
		lblWinningPlayer1Efficiency = new JLabel("");
		lblWinningTeamPlayer2 = new JLabel("");
		lblWinningPlayer2Efficiency = new JLabel("");
		lblWinningTeamPlayer3 = new JLabel("");
		lblWinningPlayer3Efficiency = new JLabel("");
		lblWinningTeamPlayer4 = new JLabel("");
		lblWinningPlayer4Efficiency = new JLabel("");
		lblLosingTeamPlayer1 = new JLabel("");
		lblLosingPlayer1Efficiency = new JLabel("");
		lblLosingTeamPlayer2 = new JLabel("");
		lblLosingPlayer2Efficiency = new JLabel("");
		lblLosingTeamPlayer3 = new JLabel("");
		lblLosingPlayer3Efficiency = new JLabel("");
		lblLosingTeamPlayer4 = new JLabel("");
		lblLosingPlayer4Efficiency = new JLabel("");
		btnCloseGame = new CustomButton("Terminé la partie", new Color(220,220,220), new Color(200,200,200), null, ButtonManager.getTextButtonDimension());
		
		initGui();
	}
	
	private void initGui(){
		lblWinningTeamName.setFont(FontManager.getEndOfGameItem());
		lblLosingTeamName.setFont(FontManager.getEndOfGameItem());
		
		lblWinningTeamScore.setFont(FontManager.getEndOfGameItem());
		lblLosingTeamScore.setFont(FontManager.getEndOfGameItem());
		
		lblWinningPlayer1Efficiency.setFont(FontManager.getEndOfGameItem());
		lblWinningPlayer2Efficiency.setFont(FontManager.getEndOfGameItem());
		lblWinningPlayer3Efficiency.setFont(FontManager.getEndOfGameItem());
		lblWinningPlayer4Efficiency.setFont(FontManager.getEndOfGameItem());
		
		lblLosingPlayer1Efficiency.setFont(FontManager.getEndOfGameItem());
		lblLosingPlayer2Efficiency.setFont(FontManager.getEndOfGameItem());
		lblLosingPlayer3Efficiency.setFont(FontManager.getEndOfGameItem());
		lblLosingPlayer4Efficiency.setFont(FontManager.getEndOfGameItem());
		
		lblWinningTeamPlayer1.setFont(FontManager.getEndOfGameItem());
		lblWinningTeamPlayer2.setFont(FontManager.getEndOfGameItem());
		lblWinningTeamPlayer3.setFont(FontManager.getEndOfGameItem());
		lblWinningTeamPlayer4.setFont(FontManager.getEndOfGameItem());
		
		lblLosingTeamPlayer1.setFont(FontManager.getEndOfGameItem());
		lblLosingTeamPlayer2.setFont(FontManager.getEndOfGameItem());
		lblLosingTeamPlayer3.setFont(FontManager.getEndOfGameItem());
		lblLosingTeamPlayer4.setFont(FontManager.getEndOfGameItem());
		
		lblWinningPlayer1Score.setFont(FontManager.getEndOfGameItem());
		lblWinningPlayer2Score.setFont(FontManager.getEndOfGameItem());
		lblWinningPlayer3Score.setFont(FontManager.getEndOfGameItem());
		lblWinningPlayer4Score.setFont(FontManager.getEndOfGameItem());
		
		lblLosingPlayer1Score.setFont(FontManager.getEndOfGameItem());
		lblLosingPlayer2Score.setFont(FontManager.getEndOfGameItem());
		lblLosingPlayer3Score.setFont(FontManager.getEndOfGameItem());
		lblLosingPlayer4Score.setFont(FontManager.getEndOfGameItem());
		
		lblTitle.setFont(FontManager.getQuestionZoneMainLabel());
		
		setBackground(ColorManager.getTextPaneColor());
		setBorder(new LineBorder(ColorManager.getMousePressedColor()));
		setLayout(new MigLayout("", "[grow 50][grow,center][grow,center][grow]", "[][10px:n:10px][][5px:n:5px][][][][][20px:n:20px][][5px:n:5px][][][][][20px:n:20px][]"));
		
		add(lblTitle, "cell 0 0 4 1,alignx center");
		
		
		add(lblWinningTeamName, "cell 0 2");
		add(lblWinningTeamScore, "cell 1 2");
		add(lblWinningTeamPlayer1, "cell 0 4,alignx left");
		
		add(lblWinningPlayer1Score, "cell 1 4");
		add(lblWinningPlayer1Efficiency, "cell 2 4,alignx center");
		add(lblWinningTeamPlayer2, "cell 0 5,alignx left");
		
		add(lblWinningPlayer2Score, "cell 1 5");
		add(lblWinningPlayer2Efficiency, "cell 2 5,alignx center");
		add(lblWinningTeamPlayer3, "cell 0 6,alignx left");
		
		add(lblWinningPlayer3Score, "cell 1 6");
		add(lblWinningPlayer3Efficiency, "cell 2 6,alignx center");
		add(lblWinningTeamPlayer4, "cell 0 7,alignx left");
		
		add(lblWinningPlayer4Score, "cell 1 7");
		add(lblWinningPlayer4Efficiency, "cell 2 7,alignx center");
		
		add(lblLosingTeamName, "cell 0 9");
		add(lblLosingTeamScore, "cell 1 9");
		add(lblLosingTeamPlayer1, "cell 0 11,alignx left");
		
		add(lblLosingPlayer1Score, "cell 1 11");
		add(lblLosingPlayer1Efficiency, "cell 2 11,alignx center");
		add(lblLosingTeamPlayer2, "cell 0 12,alignx left");
	
		add(lblLosingPlayer2Score, "cell 1 12");
		add(lblLosingPlayer2Efficiency, "cell 2 12,alignx center");
		add(lblLosingTeamPlayer3, "cell 0 13,alignx left");
		
		add(lblLosingPlayer3Score, "cell 1 13");
		add(lblLosingPlayer3Efficiency, "cell 2 13,alignx center");
		
		add(lblLosingPlayer4Score, "cell 1 14");
		add(lblLosingTeamPlayer4, "cell 0 14,alignx left");
		add(lblLosingPlayer4Efficiency, "cell 2 14,alignx center");
		add(btnCloseGame, "cell 0 16 4 1,alignx center");
	}
	
	public void setWinningTeam(List<String> playerNames, List<Integer> goodAnswer, 
			List<Integer> badAnswer, List<Integer> playerScores, Integer TeamScore, String teamName){
		List<JLabel> teamPlayer = new ArrayList<JLabel>();
		List<JLabel> playerScore = new ArrayList<JLabel>();
		List<JLabel> playerEfficiency = new ArrayList<JLabel>();
		
		lblWinningTeamScore.setText(TeamScore.toString());
		lblWinningTeamName.setText(teamName);
		
		teamPlayer.add(lblWinningTeamPlayer1);
		teamPlayer.add(lblWinningTeamPlayer2);
		teamPlayer.add(lblWinningTeamPlayer3);
		teamPlayer.add(lblWinningTeamPlayer4);
		
		playerEfficiency.add(lblWinningPlayer1Efficiency);
		playerEfficiency.add(lblWinningPlayer2Efficiency);
		playerEfficiency.add(lblWinningPlayer3Efficiency);
		playerEfficiency.add(lblWinningPlayer4Efficiency);
		
		playerScore.add(lblWinningPlayer1Score);
		playerScore.add(lblWinningPlayer2Score);
		playerScore.add(lblWinningPlayer3Score);
		playerScore.add(lblWinningPlayer4Score);
		
		for (int i = 0; i < playerNames.size(); i++){
			teamPlayer.get(i).setText(playerNames.get(i));
			playerScore.get(i).setText(playerScores.get(i).toString());
			
			String effPlayer = "0/0 - 0.0%";
			if (goodAnswer.get(i)+badAnswer.get(i) != 0){
				int total = goodAnswer.get(i)+badAnswer.get(i);
				float ratio = (float)goodAnswer.get(i)/total;
				effPlayer = Integer.toString(goodAnswer.get(i))+ "/" + Integer.toString(total) + " - " + Integer.toString((int)(100*ratio)) + "%";
			}
			playerEfficiency.get(i).setText(effPlayer);
		}
	}
	
	public void setLosingTeam(List<String> playerNames, List<Integer> goodAnswer, 
			List<Integer> badAnswer, List<Integer> playerScores, Integer TeamScore, String teamName){
		List<JLabel> teamPlayer = new ArrayList<JLabel>();
		List<JLabel> playerScore = new ArrayList<JLabel>();
		List<JLabel> playerEfficiency = new ArrayList<JLabel>();
		
		lblLosingTeamScore.setText(TeamScore.toString());
		lblLosingTeamName.setText(teamName);
		
		teamPlayer.add(lblLosingTeamPlayer1);
		teamPlayer.add(lblLosingTeamPlayer2);
		teamPlayer.add(lblLosingTeamPlayer3);
		teamPlayer.add(lblLosingTeamPlayer4);
		
		playerEfficiency.add(lblLosingPlayer1Efficiency);
		playerEfficiency.add(lblLosingPlayer2Efficiency);
		playerEfficiency.add(lblLosingPlayer3Efficiency);
		playerEfficiency.add(lblLosingPlayer4Efficiency);
		
		playerScore.add(lblLosingPlayer1Score);
		playerScore.add(lblLosingPlayer2Score);
		playerScore.add(lblLosingPlayer3Score);
		playerScore.add(lblLosingPlayer4Score);
		
		for (int i = 0; i < playerNames.size(); i++){
			teamPlayer.get(i).setText(playerNames.get(i));
			playerScore.get(i).setText(playerScores.get(i).toString());
			
			String effPlayer = "0/0 - 0.0%";
			if (goodAnswer.get(i)+badAnswer.get(i) != 0){
				int total = goodAnswer.get(i)+badAnswer.get(i);
				float ratio = (float)goodAnswer.get(i)/total;
				effPlayer = Integer.toString(goodAnswer.get(i))+ "/" + Integer.toString(total) + " - " + Integer.toString((int)(100*ratio)) + "%";
			}
			playerEfficiency.get(i).setText(effPlayer);
		}
	}
	
	public JButton getBtnCloseGame(){
		return this.btnCloseGame;
	}

	private JLabel lblWinningPlayer1Score;
	private JLabel lblWinningPlayer2Score;
	private JLabel lblWinningPlayer3Score;
	private JLabel lblWinningPlayer4Score;
	private JLabel lblLosingPlayer1Score;
	private JLabel lblLosingPlayer2Score;
	private JLabel lblLosingPlayer3Score;
	private JLabel lblLosingPlayer4Score;
	private JLabel lblWinningTeamName;
	private JLabel lblWinningTeamScore;
	private JLabel lblLosingTeamName;
	private JLabel lblLosingTeamScore;
	
	
}
