package serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import structures.QuestionTest;

import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionSetSection;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.User;


@XmlRootElement(name = "GameData")
public class GameDataSerializationObject {
	
	//Team and player data
	private Team leftTeam;
	private Team rightTeam;
	private int leftTeamScore;
	private int rightTeamScore;
	private List<User> leftTeamPlayers = new ArrayList<User>();
	private List<User> rightTeamPlayers = new ArrayList<User>();
	
	//Player who answered a specific question
	private HashMap<String, ArrayList<QuestionTest>> leftPlayerQuestionsAnswered = new HashMap<String, ArrayList<QuestionTest>>();
	private HashMap<String, ArrayList<QuestionTest>> rightPlayerQuestionsAnswered = new HashMap<String, ArrayList<QuestionTest>>();

	//private HashMap<QuestionTest, String> leftPlayerQuestionsAnswered = new HashMap<QuestionTest, String>();
	//private HashMap<QuestionTest, String> rightPlayerQuestionsAnswered = new HashMap<QuestionTest, String>();
	
	//All game questions
	private List<QuestionSetSection> questionSectionList;
	private int currentShownQuestionSetSection;
	private int currentShownQuestionInSection;
	private int currentQuestionNumber;
	
	//Game object
	private String currentGameKey;
	
	public int getLeftTeamScore(){
		return leftTeamScore;
	}
	
	public void setLeftTeamScore(int score){
		leftTeamScore = score;
	}
	
	public int getRightTeamScore(){
		return rightTeamScore;
	}
	
	public void setRightTeamScore(int score){
		rightTeamScore = score;
	}
	
	public int getCurrentQuestionNumber(){
		return currentQuestionNumber;
	}
	
	public void setCurrentQuestionNumber(int currentQuestionNumber){
		this.currentQuestionNumber = currentQuestionNumber;
	}
	
	public Team getLeftTeam(){
		return leftTeam;
	}
	
	public void setLeftTeam(Team team){
		this.leftTeam = team;
	}
	
	public Team getRightTeam(){
		return rightTeam;
	}
	
	public void setRightTeam(Team team){
		this.rightTeam = team;
	}
	
	@XmlElementWrapper(name = "leftPlayerQuestionsAnswered")
	public HashMap<String, ArrayList<QuestionTest>> getLeftPlayerQuestionsAnswered(){
		return leftPlayerQuestionsAnswered;
	}
	
	public void setLeftPlayerQuestionsAnswered(HashMap<String, ArrayList<QuestionTest>> leftPlayerQuestionsAnswered){
		this.leftPlayerQuestionsAnswered = leftPlayerQuestionsAnswered;
	}
	
	@XmlElementWrapper(name = "rightPlayerQuestionsAnswered")
	public HashMap<String, ArrayList<QuestionTest>> getRightPlayerQuestionsAnswered(){
		return rightPlayerQuestionsAnswered;
	}
	
	public void setRightPlayerQuestionsAnswered(HashMap<String, ArrayList<QuestionTest>> rightPlayerQuestionsAnswered){
		this.rightPlayerQuestionsAnswered = rightPlayerQuestionsAnswered;
	}
	
	/*@XmlElementWrapper(name = "leftPlayerQuestionsAnswered")
	public HashMap<QuestionTest, String> getLeftPlayerQuestionsAnswered(){
		return leftPlayerQuestionsAnswered;
	}
	
	public void setLeftPlayerQuestionsAnswered(HashMap<QuestionTest, String> leftPlayerQuestionsAnswered){
		this.leftPlayerQuestionsAnswered = leftPlayerQuestionsAnswered;
	}
	
	@XmlElementWrapper(name = "rightPlayerQuestionsAnswered")
	public HashMap<QuestionTest, String> getRightPlayerQuestionsAnswered(){
		return rightPlayerQuestionsAnswered;
	}
	
	public void setRightPlayerQuestionsAnswered(HashMap<QuestionTest, String> rightPlayerQuestionsAnswered){
		this.rightPlayerQuestionsAnswered = rightPlayerQuestionsAnswered;
	}*/
	
	@XmlElementWrapper(name = "leftTeamPlayers")
	@XmlElement(name = "user")
	public List<User> getLeftTeamPlayers(){
		return leftTeamPlayers;
	}
	
	public void setLeftTeamPlayers(List<User> leftTeamPlayers){
		this.leftTeamPlayers = leftTeamPlayers;
	}
	
	@XmlElementWrapper(name = "rightTeamPlayers")
	@XmlElement(name = "user")
	public List<User> getRightTeamPlayers(){
		return rightTeamPlayers;
	}
	
	public void setRightTeamPlayers(List<User> rightTeamPlayers){
		this.rightTeamPlayers = rightTeamPlayers;
	}
	
	@XmlElementWrapper(name = "questionSectionList")
	@XmlElement(name = "questionSection")
	public List<QuestionSetSection> getQuestionSectionList(){
		return questionSectionList;
	}
	
	public void setQuestionSectionList(List<QuestionSetSection> questionSectionList){
		this.questionSectionList = questionSectionList;
	}
	
	public int getCurrentShownQuestionSetSection(){
		return currentShownQuestionSetSection;
	}
	
	public void setCurrentShownQuestionSetSection(int currentShownQuestionSetSection){
		this.currentShownQuestionSetSection = currentShownQuestionSetSection;
	}
	
	public int getCurrentShownQuestionInSection(){
		return currentShownQuestionInSection;
	}
	
	public void setCurrentShownQuestionInSection(int currentShownQuestionInSection){
		this.currentShownQuestionInSection = currentShownQuestionInSection;
	}
	
	public void setCurrentGame(String gameKey){
		this.currentGameKey = gameKey;
	}
	
	public String getCurrentGame(){
		return this.currentGameKey;
	}

}
