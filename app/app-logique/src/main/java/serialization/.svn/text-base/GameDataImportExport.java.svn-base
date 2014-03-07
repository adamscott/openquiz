package serialization;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.bind.JAXBException;

import structures.QuestionTest;

import applicationTools.QuestionManager;
import applicationTools.ScoreManager;
import applicationTools.TeamManager;

import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionSetSection;
import ca.openquiz.comms.model.QuestionWrapper;

public class GameDataImportExport {

	private static String WriteFilePath = "gameData.opq";
	private static GameDataImportExport INSTANCE = null;
	
	private static GameDataSerializationObject data;
	
	public static GameDataImportExport getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new GameDataImportExport();
		}
			return INSTANCE;
	}
	
	private GameDataImportExport(){
		data = new GameDataSerializationObject();
	}
	
	public void saveCurrentGameDataInFile(String filename){
		//Create the serialization object first
		data.setLeftTeam(TeamManager.getInstance().getLeftTeam());
		data.setRightTeam(TeamManager.getInstance().getRightTeam());
		data.setLeftTeamPlayers(TeamManager.getInstance().getLeftTeamPlayers());
		data.setRightTeamPlayers(TeamManager.getInstance().getRightTeamPlayers());
		data.setLeftTeamScore(ScoreManager.getInstance().getLeftTeamScore());
		data.setRightTeamScore(ScoreManager.getInstance().getRightTeamScore());
		
		data.setLeftPlayerQuestionsAnswered(ScoreManager.getInstance().getLeftPlayerQuestionsAnswered());
		data.setRightPlayerQuestionsAnswered(ScoreManager.getInstance().getRightPlayerQuestionsAnswered());
		
		//data.setLeftPlayerQuestionsAnswered(wrapPlayerQuestionsAnsweredToSerializableList(ScoreManager.getInstance().getLeftPlayerQuestionsAnswered()));
		//data.setRightPlayerQuestionsAnswered(wrapPlayerQuestionsAnsweredToSerializableList(ScoreManager.getInstance().getRightPlayerQuestionsAnswered()));
		
		data.setQuestionSectionList(QuestionManager.getInstance().getQuestionSectionList());
		data.setCurrentShownQuestionInSection(QuestionManager.getInstance().getCurrentShownQuestionInSection());
		data.setCurrentShownQuestionSetSection(QuestionManager.getInstance().getCurrentShownQuestionSetSection());
		data.setCurrentQuestionNumber(QuestionManager.getInstance().getCurrentQuestionNumber());
		
		data.setCurrentGame(QuestionManager.getInstance().getCurrentGameKey());
		
		//Serialize the object
		try {
			SerializationManager.getInstance().serializeObject(data, filename);
		} catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void loadCurrentGameDataFromFile(String filename){
		//Deserialize data
		try {
			data = (GameDataSerializationObject) SerializationManager.getInstance().deserializeObject(GameDataSerializationObject.class, filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if(data != null){
			//Put the data back where it belongs
			TeamManager.getInstance().setLeftTeam(data.getLeftTeam());
			TeamManager.getInstance().setRightTeam(data.getRightTeam());
			
			TeamManager.getInstance().setLeftTeamPlayers(data.getLeftTeamPlayers());
			TeamManager.getInstance().setRightTeamPlayers(data.getRightTeamPlayers());
			
			ScoreManager.getInstance().setLeftTeamScore(data.getLeftTeamScore());
			ScoreManager.getInstance().setRightTeamScore(data.getRightTeamScore());

			ScoreManager.getInstance().setLeftPlayerQuestionsAnswered(data.getLeftPlayerQuestionsAnswered());
			ScoreManager.getInstance().setRightPlayerQuestionsAnswered(data.getRightPlayerQuestionsAnswered());
			
			//ScoreManager.getInstance().setLeftPlayerQuestionsAnswered(unwrapSerializedPlayerQuestionsList(data.getLeftPlayerQuestionsAnswered()));
			//ScoreManager.getInstance().setRightPlayerQuestionsAnswered(unwrapSerializedPlayerQuestionsList(data.getRightPlayerQuestionsAnswered()));
			
			QuestionManager.getInstance().setQuestionSectionList(data.getQuestionSectionList());
			QuestionManager.getInstance().setCurrentShownQuestionSetSection(data.getCurrentShownQuestionSetSection());
			QuestionManager.getInstance().setCurrentShownQuestionInSection(data.getCurrentShownQuestionInSection());
			QuestionManager.getInstance().setCurrentQuestionNumber(data.getCurrentQuestionNumber());
			
			QuestionManager.getInstance().setCurrentGameKey(data.getCurrentGame());
		}
		
	}
	
	private HashMap<QuestionTest, String> wrapPlayerQuestionsAnsweredToSerializableList(HashMap<String, ArrayList<QuestionTest>> playerQuestions){
		HashMap<QuestionTest, String> questionPlayerAnsweredMap = new HashMap<QuestionTest, String>();
		
		for(Iterator<String> it = playerQuestions.keySet().iterator(); it.hasNext();){
			//Get the list of questions for this user
			String player = it.next();
			ArrayList<QuestionTest> questionList = playerQuestions.get(player);
			
			for(Iterator<QuestionTest> it2 = questionList.iterator(); it2.hasNext();){
				questionPlayerAnsweredMap.put(it2.next(), player);
			}
		}
		return questionPlayerAnsweredMap;
	}
	
	private HashMap<String, ArrayList<QuestionTest>> unwrapSerializedPlayerQuestionsList(HashMap<QuestionTest, String> mapToUnwrap){
		HashMap<String, ArrayList<QuestionTest>> playerQuestionsMap = new HashMap<String, ArrayList<QuestionTest>>();
		
		for(Iterator<QuestionTest> it = mapToUnwrap.keySet().iterator(); it.hasNext();){
			QuestionTest qt = it.next();
			Question currentQuestion = qt.getQuestion();
			boolean answer = qt.answer;
			int value = qt.value;
			String currentPlayer = mapToUnwrap.get(qt);
			if(!playerQuestionsMap.containsKey(currentPlayer)){
				playerQuestionsMap.put(currentPlayer, new ArrayList<QuestionTest>());
			}
			
			//Get the good question object from QuestionSetSection
			Question goodQuestion = getQuestionInQuestionSetFromKey(currentQuestion.getKey());
			QuestionTest qt2 = new QuestionTest();
			QuestionWrapper qw = new QuestionWrapper();
			qw.addQuestion(goodQuestion);
			qt.setAnswer(answer);
			qt.setValue(value);
			qt.setQuestionList(qw);
			playerQuestionsMap.get(currentPlayer).add(qt2);
		}
		
		return playerQuestionsMap;
	}
	
	private Question getQuestionInQuestionSetFromKey(String key){
		for(Iterator<QuestionSetSection> it = data.getQuestionSectionList().iterator(); it.hasNext();){
			QuestionSetSection section = it.next();
			
			for(Iterator<Question> it2 = section.getQuestionList().getAllQuestions().iterator(); it2.hasNext();){
				Question question = it2.next();
				if(key.equals(question.getKey())){
					return question;
				}
			}
		}
		return null;
	}
}