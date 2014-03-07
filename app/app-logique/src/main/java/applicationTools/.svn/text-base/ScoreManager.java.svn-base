package applicationTools;

import java.util.ArrayList;
import java.util.HashMap;

import structures.PlayerInfo;
import structures.QuestionTest;
import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionWrapper;

public class ScoreManager {
	
	private static int LeftTeamScore = 0;
	private static int RightTeamScore = 0;
	
	//Register all question answer by a player
	private static HashMap<String, ArrayList<QuestionTest>> LeftPlayerQuestionsAnswered;
	private static HashMap<String, ArrayList<QuestionTest>> RightPlayerQuestionsAnswered;
	
	//Register a point substraction for a player
	private static HashMap<String, Integer> LeftPlayerSubstractionPoint;
	private static HashMap<String, Integer> RightPlayerSubstractionPoint;
	
	//Training mode
	private static Boolean isTrainingModeEnabled = false;
	
	private static ScoreManager INSTANCE = null;
	
	public static ScoreManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new ScoreManager();
		}
			return INSTANCE;
	}
	
	private ScoreManager(){
		//questionsScoreStats = new HashMap<Question, PlayerInfo>();
		LeftPlayerQuestionsAnswered = new HashMap<String, ArrayList<QuestionTest>>();
		RightPlayerQuestionsAnswered = new HashMap<String, ArrayList<QuestionTest>>();
		LeftPlayerSubstractionPoint = new HashMap<String, Integer>();
		RightPlayerSubstractionPoint = new HashMap<String, Integer>();
	}
	
	public QuestionTest getPlayerIndividualQuestion(Question question, String playerName, String playerTeamSide){
		if (playerTeamSide.equals("Left")){
			if (LeftPlayerQuestionsAnswered.containsKey(playerName)){
				for (QuestionTest qt : LeftPlayerQuestionsAnswered.get(playerName)){
					if (qt.getQuestion() != null && qt.getQuestion().getKey().equals(question.getKey())){
						return qt;
					}
				}
			}
		}
		else if (playerTeamSide.equals("Right")){
			if (RightPlayerQuestionsAnswered.containsKey(playerName)){
				for (QuestionTest qt : RightPlayerQuestionsAnswered.get(playerName)){
					if (qt.getQuestion() != null && qt.getQuestion().getKey().equals(question.getKey())){
						return qt;
					}
				}
			}
		}
		
		return null;
	}
	
	public void adjustPlayerScoreForIndividualQuestion(Question question, String playerName, String playerTeamSide, int value){
		//Get the QuestionTest if the player already has an entry
		QuestionTest qt = getPlayerIndividualQuestion(question, playerName, playerTeamSide);
		
		//Player did not already answer this question
		if (qt == null){
			qt = new QuestionTest();
			QuestionWrapper qw = new QuestionWrapper();
			qw.addQuestion(question);
			qt.setAnswer(true);
			qt.setValue(value);
			qt.setQuestionList(qw);
			
			if(playerTeamSide.equals("Left")){
				if(!LeftPlayerQuestionsAnswered.containsKey(playerName)){
					LeftPlayerQuestionsAnswered.put(playerName, new ArrayList<QuestionTest>());
				}
				LeftPlayerQuestionsAnswered.get(playerName).add(qt);
				LeftTeamScore += value;
			}
			else if(playerTeamSide.equals("Right")){
				if(!RightPlayerQuestionsAnswered.containsKey(playerName)){
					RightPlayerQuestionsAnswered.put(playerName, new ArrayList<QuestionTest>());
				}
				RightPlayerQuestionsAnswered.get(playerName).add(qt);
				RightTeamScore += value;
			}
		}
		else{
			if (playerTeamSide.equals("Left")){
				int idx = LeftPlayerQuestionsAnswered.get(playerName).indexOf(qt);
				qt.setValue(qt.getValue() + value);
				LeftPlayerQuestionsAnswered.get(playerName).set(idx, qt);
				LeftTeamScore += value;
			}
			else if (playerTeamSide.equals("Right")){
				int idx = RightPlayerQuestionsAnswered.get(playerName).indexOf(qt);
				qt.setValue(qt.getValue() + value);
				RightPlayerQuestionsAnswered.get(playerName).set(idx, qt);
				RightTeamScore += value;
			}
		}
	}
	
	public void addQuestionToPlayerAnswerList(Question question, String playerName, String playerTeamSide, int questionValue){
		//Check if someone has already answer this question, then remove it
		PlayerInfo oldPlayer = getPlayerWhoAnsweredQuestionCorrectly(question);
		if(oldPlayer != null){
			removeAnsweredQuestionFromPlayer(question, oldPlayer.playerName, oldPlayer.playerTeamSide, oldPlayer.idx);
		}
		
		QuestionTest qt = new QuestionTest();
		QuestionWrapper qw = new QuestionWrapper();
		qw.addQuestion(question);
		qt.setAnswer(true);
		qt.setValue(questionValue);
		qt.setQuestionList(qw);
		
		//Register info about the answered question
		if(playerTeamSide.equals("Left")){
			if(!LeftPlayerQuestionsAnswered.containsKey(playerName)){
				LeftPlayerQuestionsAnswered.put(playerName, new ArrayList<QuestionTest>());
			}
			LeftPlayerQuestionsAnswered.get(playerName).add(qt);
			LeftTeamScore += QuestionManager.getInstance().getQuestionValue(question);
		}
		else if(playerTeamSide.equals("Right")){
			if(!RightPlayerQuestionsAnswered.containsKey(playerName)){
				RightPlayerQuestionsAnswered.put(playerName, new ArrayList<QuestionTest>());
			}
			RightPlayerQuestionsAnswered.get(playerName).add(qt);
			RightTeamScore += QuestionManager.getInstance().getQuestionValue(question);
		}
	}
	
	public void addWrongAnswerQuestionToPlayerList(Question question, String playerName, String playerTeamSide, int questionValue){
		QuestionTest qt = new QuestionTest();
		QuestionWrapper qw = new QuestionWrapper();
		qw.addQuestion(question);
		qt.setAnswer(false);
		qt.setValue(questionValue);
		qt.setQuestionList(qw);
		
		PlayerInfo oldPlayer = getPlayerWhoAnsweredQuestionCorrectly(question);
		if(oldPlayer != null){
			if (oldPlayer.playerName.equals(playerName) && oldPlayer.playerTeamSide.equals(playerTeamSide)){
				removeAnsweredQuestionFromPlayer(question, oldPlayer.playerName, oldPlayer.playerTeamSide, oldPlayer.idx);
			}
		}
		
		if(playerTeamSide.equals("Left")){
			if(!LeftPlayerQuestionsAnswered.containsKey(playerName)){
				LeftPlayerQuestionsAnswered.put(playerName, new ArrayList<QuestionTest>());
			}
			LeftPlayerQuestionsAnswered.get(playerName).add(qt);
			//LeftTeamScore += questionValue;
		}
		else if(playerTeamSide.equals("Right")){
			if(!RightPlayerQuestionsAnswered.containsKey(playerName)){
				RightPlayerQuestionsAnswered.put(playerName, new ArrayList<QuestionTest>());
			}
			RightPlayerQuestionsAnswered.get(playerName).add(qt);
			//RightTeamScore += questionValue;
		}
	}
	
	public void substractPointFromPlayer(String playerName, String playerTeamSide, int negativeValue){
		if(playerTeamSide.equals("Left")){
			int oldValue = 0;
			if(LeftPlayerSubstractionPoint.containsKey(playerName)){
				oldValue += LeftPlayerSubstractionPoint.get(playerName);
			}
			LeftPlayerSubstractionPoint.put(playerName, negativeValue + oldValue);
			LeftTeamScore += negativeValue;
		}
		else if(playerTeamSide.equals("Right")){
			int oldValue = 0;
			if(RightPlayerSubstractionPoint.containsKey(playerName)){
				oldValue += RightPlayerSubstractionPoint.get(playerName);
			}
			RightPlayerSubstractionPoint.put(playerName, negativeValue + oldValue);
			RightTeamScore += negativeValue;
		}
	}
		
	public PlayerInfo getPlayerWhoAnsweredQuestionCorrectly(Question question){
		//Go throught all player to find which one has answered the question
		for(String player : LeftPlayerQuestionsAnswered.keySet()){
			int i = 0;
			for(QuestionTest qt : LeftPlayerQuestionsAnswered.get(player)){
				if(qt.getQuestion().getKey().equals(question.getKey()) && qt.answer == true){
					return new PlayerInfo(player, "Left", i);
				}
				i++;
			}
		}
		for(String player : RightPlayerQuestionsAnswered.keySet()){
			int i = 0;
			for(QuestionTest qt : RightPlayerQuestionsAnswered.get(player)){
				if(qt.getQuestion().getKey().equals(question.getKey()) && qt.answer == true){
					return new PlayerInfo(player, "Right", i);
				}
				i++;
			}
		}
		return null;
	}
	
	public void removeAnsweredQuestionFromPlayer(Question question, String player, String playerTeamSide, int idx){
		if(playerTeamSide.equals("Left")){
			LeftPlayerQuestionsAnswered.get(player).remove(idx);
			LeftTeamScore -= QuestionManager.getInstance().getQuestionValue(question);
		}
		else if(playerTeamSide.equals("Right")){
			RightPlayerQuestionsAnswered.get(player).remove(idx);
			RightTeamScore -= QuestionManager.getInstance().getQuestionValue(question);
		}
	}
	
	public int getPlayerScore(String player, String playerTeamSide){
		int playerScore = 0;
		if(playerTeamSide.equals("Left"))
		{
			if(LeftPlayerQuestionsAnswered.containsKey(player)){
				for(QuestionTest qt : LeftPlayerQuestionsAnswered.get(player)){
					playerScore += qt.getValue();
					
				}
			}
			/*if(LeftPlayerSubstractionPoint.containsKey(player)){
				int pointsToRemove = LeftPlayerSubstractionPoint.get(player);
				playerScore += pointsToRemove;
			}*/
		}
		else if(playerTeamSide.equals("Right")){
			if(RightPlayerQuestionsAnswered.containsKey(player)){
				for(QuestionTest qt : RightPlayerQuestionsAnswered.get(player)){
					playerScore += qt.getValue();
					
				}
			}
			/*if(RightPlayerSubstractionPoint.containsKey(player)){
				int pointsToRemove = RightPlayerSubstractionPoint.get(player);
				playerScore += pointsToRemove;
			}*/
		}
		return playerScore;
	}
	
	public void adjustLeftTeamScore(int delta){
		LeftTeamScore += delta;
	}
	
	public void adjustRightTeamScore(int delta){
		RightTeamScore += delta;
	}
	
	public int getLeftTeamScore(){
		return LeftTeamScore;
	}
	
	public int getRightTeamScore(){
		return RightTeamScore;
	}
	
	public void setLeftTeamScore(int score){
		LeftTeamScore = score;
	}
	
	public void setRightTeamScore(int score){
		RightTeamScore = score;
	}
	
	public HashMap<String, ArrayList<QuestionTest>> getLeftPlayerQuestionsAnswered(){
		return LeftPlayerQuestionsAnswered;
	}
	
	public HashMap<String, ArrayList<QuestionTest>> getRightPlayerQuestionsAnswered(){
		return RightPlayerQuestionsAnswered;
	}
	
	public void setLeftPlayerQuestionsAnswered(HashMap<String, ArrayList<QuestionTest>> LeftPlayerQuestionsAnswered){
		ScoreManager.LeftPlayerQuestionsAnswered = LeftPlayerQuestionsAnswered;
	}
	
	public void setRightPlayerQuestionsAnswered(HashMap<String, ArrayList<QuestionTest>> RightPlayerQuestionsAnswered){
		ScoreManager.RightPlayerQuestionsAnswered = RightPlayerQuestionsAnswered;
	}
	
	public void setTrainingMode(Boolean mode){
		ScoreManager.isTrainingModeEnabled = mode;
	}
	
	public Boolean isTrainingModeEnabled(){
		return isTrainingModeEnabled;
	}
	
	public void resetScores(){
		LeftTeamScore = 0;
		RightTeamScore = 0;
		LeftPlayerQuestionsAnswered.clear();
		RightPlayerQuestionsAnswered.clear();
		LeftPlayerSubstractionPoint.clear();
		RightPlayerSubstractionPoint.clear();
	}
	
	public int getNbOfGoodAnswerForPlayer(String playerName, String side){
		int nbQuestions = 0;
		if (side.equals("Left")){
			if (LeftPlayerQuestionsAnswered.containsKey(playerName)){
				for(QuestionTest qt : LeftPlayerQuestionsAnswered.get(playerName)){
					if(qt.answer == true){
						nbQuestions++;
					}
				}
			}
		}
		else if(side.equals("Right")){
			if (RightPlayerQuestionsAnswered.containsKey(playerName)){
				for(QuestionTest qt : RightPlayerQuestionsAnswered.get(playerName)){
					if(qt.answer == true){
						nbQuestions++;
					}
				}
			}
		}
		return nbQuestions;
	}
	
	public int getNbOfBadAnswerForPlayer(String playerName, String side){
		int nbQuestions = 0;
		if (side.equals("Left")){
			if (LeftPlayerQuestionsAnswered.containsKey(playerName)){
				for(QuestionTest qt : LeftPlayerQuestionsAnswered.get(playerName)){
					if(qt.answer == false){
						nbQuestions++;
					}
				}
			}
		}
		else if(side.equals("Right")){
			if (RightPlayerQuestionsAnswered.containsKey(playerName)){
				for(QuestionTest qt : RightPlayerQuestionsAnswered.get(playerName)){
					if(qt.answer == false){
						nbQuestions++;
					}
				}
			}
		}
		return nbQuestions;
	}
}
