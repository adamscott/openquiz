package applicationTools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.model.Choice;
import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionCorrectWord;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionMedia;
import ca.openquiz.comms.model.QuestionSet;
import ca.openquiz.comms.model.QuestionSetSection;
import ca.openquiz.comms.model.QuestionWrapper;

public class QuestionManager {
	
	private List<QuestionSet> questionSetList; // Keep the list of questionSet of the user
	
	private List<QuestionSetSection> questionSectionList;
	
	private static int currentShownQuestionSetSection;
	private static int currentShownQuestionInSection;
	
	private static int currentQuestionNumber;
	private static int totalQuestionNumber;
	
	private static Boolean isLastQuestionShown;
	private static Boolean isFirstQuestionShown;	
	
	private static QuestionManager INSTANCE = null;
	private static String currentGameKey;
	
	private final String PATH_MEDIA_FILES = "data/media/";

	public static QuestionManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new QuestionManager();
		}
			return INSTANCE;
	}
	
	private QuestionManager(){
		//questionList = new ArrayList<Question>();
		//questionValue = new HashMap<Question, Integer>();
		questionSetList = new ArrayList<QuestionSet>();
		currentShownQuestionInSection = 0;
		isLastQuestionShown = false;
		setIsFirstQuestionShown(true);
		currentQuestionNumber = 1;
		totalQuestionNumber = 0;
	}
	
	public Question getFirstQuestion(){
		if(questionSectionList != null){
			//Get first question of first section
			if(questionSectionList.size() > 0){
				if(questionSectionList.get(0).getQuestionList().getAllQuestions().size() > 0){
					return questionSectionList.get(0).getQuestionList().getAllQuestions().get(0);
				}
			}
		}
		return null;
	}
	
	public Question getNextQuestion(){
		if(questionSectionList != null){
			setIsFirstQuestionShown(false);
			//Get next question in same question section
			if(questionSectionList.get(currentShownQuestionSetSection).getQuestionList().getAllQuestions().size() > currentShownQuestionInSection + 1){
				currentShownQuestionInSection++;
				currentQuestionNumber++;
				return questionSectionList.get(currentShownQuestionSetSection).getQuestionList().getAllQuestions().get(currentShownQuestionInSection);
			}
			//Get first question of next question section
			else if(questionSectionList.get(currentShownQuestionSetSection).getQuestionList().getAllQuestions().size() == currentShownQuestionInSection + 1 &&
					questionSectionList.size() > currentShownQuestionSetSection+1){
				currentShownQuestionSetSection++;
				currentQuestionNumber++;
				currentShownQuestionInSection = 0;
				return questionSectionList.get(currentShownQuestionSetSection).getQuestionList().getAllQuestions().get(currentShownQuestionInSection);
			}
			else{
				isLastQuestionShown = true;
				return null;
			}
		}
		return null;
	}
	
	public Question getCurrentQuestion(){
		if(questionSectionList != null){
			return questionSectionList.get(currentShownQuestionSetSection).getQuestionList().getAllQuestions().get(currentShownQuestionInSection);
		}
		return null;
	}
	
	public Integer getCurrentQuestionNbOfChoices(){
		Question question = getCurrentQuestion();
		
		if(question.getClass() == QuestionGeneral.class){
			return 1;
		}
		else if(question.getClass() == QuestionIntru.class){
			return 1;
		}
		else if(question.getClass() == QuestionAssociation.class){
			List<Choice> choices = ((QuestionAssociation)question).getChoices();
			if (choices != null){
				return ((QuestionAssociation)question).getChoices().size();
			}
		}
		else if(question.getClass() == QuestionAnagram.class){
			return 1;
		}
		else if(question.getClass() == QuestionIdentification.class){
			return 1;
		}
		return 1;
	}
	
	public QuestionSetSection getCurrentQuestionSetSection(){
		if (currentShownQuestionSetSection < questionSectionList.size()){
			return questionSectionList.get(currentShownQuestionSetSection);
		}
		return null;
	}
	
	public QuestionTarget getCurrentQuestionTarget(){
		QuestionSetSection qsSection = getCurrentQuestionSetSection();
		if(qsSection != null){
			return qsSection.getQuestionTarget();
		}
		return QuestionTarget.Collectif;
	}
	public int getCurrentQuestionNumber(){
		return currentQuestionNumber;
	}
	
	public void resetGame(){
		QuestionManager.currentQuestionNumber = 1;
		QuestionManager.currentShownQuestionInSection = 0;
		QuestionManager.currentShownQuestionSetSection = 0;
	}
	
	public void setCurrentQuestionNumber(int currentQuestionNumber){
		QuestionManager.currentQuestionNumber = currentQuestionNumber;
	}
	
	public int getTotalQuestionNumber(){
		if(questionSectionList != null){
			totalQuestionNumber = 0;
	
			for(Iterator<QuestionSetSection> it = questionSectionList.iterator(); it.hasNext();){
				QuestionSetSection currentSection = it.next();
				
				if (currentSection.getQuestionList() != null){
					for(Iterator<Question> it2 = currentSection.getQuestionList().getAllQuestions().iterator(); it2.hasNext();){
						it2.next();
						totalQuestionNumber++;
					}
				}
			}
			
			return totalQuestionNumber;
		}
		return 0;
	}
	
	public Question getPreviousQuestion(){
		if(questionSectionList != null){
			if(currentShownQuestionInSection == 0 && currentShownQuestionSetSection == 0){
				setIsFirstQuestionShown(true);
				return null;
			}
			else{
				setIsFirstQuestionShown(false);
			}
	
			if(isLastQuestionShown){
				isLastQuestionShown = false;
				QuestionSetSection currentSection = questionSectionList.get(questionSectionList.size()-1);
				return currentSection.getQuestionList().getAllQuestions().get(currentSection.getQuestionList().getAllQuestions().size()-1);
			}
			//Get last question in previous section
			if(currentShownQuestionInSection == 0){
				currentShownQuestionSetSection--;
				currentQuestionNumber--;
				currentShownQuestionInSection = questionSectionList.get(currentShownQuestionSetSection).getQuestionList().getAllQuestions().size()-1;
				return questionSectionList.get(currentShownQuestionSetSection).getQuestionList().getAllQuestions().get(currentShownQuestionInSection);
			}
			//Get prev question in same section
			else{
				currentQuestionNumber--;
				currentShownQuestionInSection--;
				return questionSectionList.get(currentShownQuestionSetSection).getQuestionList().getAllQuestions().get(currentShownQuestionInSection);
			}
		}
		return null;
	}
	
	public Integer getCurrentQuestionValue(){
		Integer questionValue = 0;
		questionValue = getQuestionValue(getCurrentQuestion());
		
		return questionValue;
	}
	
	public Integer getQuestionValue(Question question){
		for (QuestionSetSection qs : questionSectionList){
			for (Question q : qs.getQuestionList().getAllQuestions()){
				if (question.getKey().equals(q.getKey())){
					return qs.getPoints();
				}
			}
		}
		return 0;
	}
	
	public void setQuestionSetList(List<QuestionSet> questionSetList){
		this.questionSetList = questionSetList;
	}
	
	public List<QuestionSet> getQuestionSetList(){
		return questionSetList;
	}
	
	public void addQuestionSet(QuestionSet questionSet) {
		this.questionSetList.add(questionSet);
	}
	
	public void setQuestionSectionList(List<QuestionSetSection> questionSectionList){
		List<QuestionSetSection> verifiedQuestionSection = new ArrayList<QuestionSetSection>();
		for (QuestionSetSection qs : questionSectionList){
			if (qs.getQuestionList() != null){
				verifiedQuestionSection.add(qs);
			}
		}
		this.questionSectionList = verifiedQuestionSection;
	}
	
	public List<String[]> getAllMediaPathQuestions(){
		List<String[]> filesPath = new ArrayList<String[]>();
		for (QuestionSetSection qss : questionSectionList){
			for (QuestionMedia question : qss.getQuestionList().getQuestionMedia()){
				String fileContentType = FileManager.getInstance().getQuestionMediaContentType(question.getMediaUrl());
				if (!fileContentType.contains("image")){
					String[] path = {question.getMediaUrl(), PATH_MEDIA_FILES+question.getMediaId()};
					filesPath.add(path);
				}
			}
		}
		return filesPath;
	}
	
	public List<QuestionSetSection> getQuestionSectionList(){
		return questionSectionList;
	}
	
	public int getCurrentShownQuestionSetSection(){
		return currentShownQuestionSetSection;
	}
	
	public void setCurrentShownQuestionSetSection(int currentShownQuestionSetSection){
		QuestionManager.currentShownQuestionSetSection = currentShownQuestionSetSection;
	}
	
	public int getCurrentShownQuestionInSection(){
		return currentShownQuestionInSection;
	}
	
	public void setCurrentShownQuestionInSection(int currentShownQuestionInSection){
		QuestionManager.currentShownQuestionInSection = currentShownQuestionInSection;
	}

	public Boolean getIsFirstQuestionShown() {
		if (currentShownQuestionSetSection == 0 && currentShownQuestionInSection == 0){
			return true;
		}
		return false;
	}

	public static void setIsFirstQuestionShown(Boolean isFirstQuestionShown) {
		QuestionManager.isFirstQuestionShown = isFirstQuestionShown;
	}
	
	public String getCurrentGameKey() {
		return currentGameKey;
	}

	public void setCurrentGameKey(String currentGameKey) {
		QuestionManager.currentGameKey = currentGameKey;
	}


	
}
