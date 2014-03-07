package ca.openquiz.mobile.currentGameController;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import structures.PlayerInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import applicationTools.FileManager;
import applicationTools.MediaMode;
import applicationTools.QuestionManager;
import applicationTools.ScoreManager;
import applicationTools.TeamManager;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.model.Choice;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionMedia;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.currentGame.EndOfGameAct;
import ca.openquiz.mobile.util.Defines;
import currentGame.CurrentGameModel;
import currentGame.ICurrentGameView;

public class CurrentGameView implements ICurrentGameView {

	private Activity activity;
	private CurrentGameModel model;
	private final static int nbPlayers = 4;
	private HashMap<String, TextView> leftPlayerNameTextViewMap = new HashMap<String, TextView>(nbPlayers);
	private HashMap<String, TextView> rightPlayerNameTextViewMap = new HashMap<String, TextView>(nbPlayers);
	private int currentShownHint = 1;
	private int currentHintId = 20000;
	private boolean isSwappedPlayers = false;
	
	public void setActivity(Activity act){
		activity = act;
	}
	
	@Override
	public void setModel(CurrentGameModel model) {
		this.model = model;
	}

	@Override
	public void triggerEndOfGame() {
		model.setIsEndOfGameReached(true);
		
		Intent intent = new Intent(activity.getBaseContext(), EndOfGameAct.class);
		activity.startActivity(intent);
	}

	@Override
	public void unselectEveryPlayer() {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	ViewFlipper flipper = (ViewFlipper) activity.findViewById(R.id.team1Layout).findViewById(R.id.layoutPlayer1);
            	flipper.setBackgroundResource(R.drawable.currentgame_player_border);
            	flipper.setDisplayedChild(0);
            	flipper = (ViewFlipper) activity.findViewById(R.id.team1Layout).findViewById(R.id.layoutPlayer2);
            	flipper.setBackgroundResource(R.drawable.currentgame_player_border);
            	flipper.setDisplayedChild(0);
            	flipper = (ViewFlipper) activity.findViewById(R.id.team1Layout).findViewById(R.id.layoutPlayer3);
            	flipper.setBackgroundResource(R.drawable.currentgame_player_border);
            	flipper.setDisplayedChild(0);
            	flipper = (ViewFlipper) activity.findViewById(R.id.team1Layout).findViewById(R.id.layoutPlayer4);
            	flipper.setBackgroundResource(R.drawable.currentgame_player_border);
            	flipper.setDisplayedChild(0);
            	flipper = (ViewFlipper) activity.findViewById(R.id.team2Layout).findViewById(R.id.layoutPlayer1);
            	flipper.setBackgroundResource(R.drawable.currentgame_player_border);
            	flipper.setDisplayedChild(0);
            	flipper = (ViewFlipper) activity.findViewById(R.id.team2Layout).findViewById(R.id.layoutPlayer2);
            	flipper.setBackgroundResource(R.drawable.currentgame_player_border);
            	flipper.setDisplayedChild(0);
            	flipper = (ViewFlipper) activity.findViewById(R.id.team2Layout).findViewById(R.id.layoutPlayer3);
            	flipper.setBackgroundResource(R.drawable.currentgame_player_border);
            	flipper.setDisplayedChild(0);
            	flipper = (ViewFlipper) activity.findViewById(R.id.team2Layout).findViewById(R.id.layoutPlayer4);
            	flipper.setBackgroundResource(R.drawable.currentgame_player_border);
            	flipper.setDisplayedChild(0);
            	flipper = (ViewFlipper) activity.findViewById(R.id.team1Layout).findViewById(R.id.layoutTeam);
            	flipper.setDisplayedChild(0);
            	flipper = (ViewFlipper) activity.findViewById(R.id.team2Layout).findViewById(R.id.layoutTeam);
            	flipper.setDisplayedChild(0);
            }});
	}

	@Override
	public void loadTeamInfo() {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	
            	View teamLayout = (View)activity.findViewById(R.id.team1Layout);
            	((TextView)teamLayout.findViewById(R.id.layoutTeam).findViewById(R.id.teamInfos).findViewById(R.id.teamName)).setText(TeamManager.getInstance().getLeftTeam().getName());
            	for(int n=0; n<nbPlayers; n++)
            	{
            		View playerLayout = (View)teamLayout.findViewById(
            				activity.getResources().getIdentifier("layoutPlayer" + (n+1), "id", activity.getPackageName()));
            		
            		TextView teamTextView = ((TextView)playerLayout.findViewById(R.id.playerName));
            		
            		if(n < TeamManager.getInstance().getLeftTeamNames().size())
            		{
	            		String playerName = TeamManager.getInstance().getLeftTeamNames().get(n);
	            		teamTextView.setText(playerName);
	            		leftPlayerNameTextViewMap.put(playerName, teamTextView);
            		}
            		else
            			playerLayout.setVisibility(View.GONE);
            	}
            	
        		teamLayout = (View)activity.findViewById(R.id.team2Layout);
            	((TextView)teamLayout.findViewById(R.id.layoutTeam).findViewById(R.id.teamInfos).findViewById(R.id.teamName)).setText(TeamManager.getInstance().getRightTeam().getName());
            	for(int n=0; n<nbPlayers; n++)
            	{
            		View playerLayout = (View)teamLayout.findViewById(
            				activity.getResources().getIdentifier("layoutPlayer" + (n+1), "id", activity.getPackageName()));
            		
            		TextView teamTextView = ((TextView)playerLayout.findViewById(R.id.playerName));
            		
            		if(n < TeamManager.getInstance().getRightTeamNames().size())
            		{
	            		String playerName = TeamManager.getInstance().getRightTeamNames().get(n);
	            		teamTextView.setText(playerName);
	            		rightPlayerNameTextViewMap.put(playerName, teamTextView);
            		}
            		else
            			playerLayout.setVisibility(View.GONE);
            	}
            }});		
	}

	@Override
	public void repaintPanelContent() {
	}

	@Override
	public void updateLeftTeamScoreLabel() {
		
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				for (String key : leftPlayerNameTextViewMap.keySet())
				{
					int score = ScoreManager.getInstance().getPlayerScore(key, "Left");	

					TextView playerNameTextView = leftPlayerNameTextViewMap.get(key);
					View playerView = (View)playerNameTextView.getParent();
					TextView playerScoreTextView = (TextView)playerView.findViewById(R.id.playerScore);

					playerScoreTextView.setText(Integer.toString(score));
				}

				View teamView = activity.findViewById(R.id.team1Layout);
				TextView teamTextView = (TextView)teamView.findViewById(R.id.teamScore);
				TextView teamManualTextView = (TextView)teamView.findViewById(R.id.teamPoints);
				int teamScore = ScoreManager.getInstance().getLeftTeamScore();

				teamTextView.setText(Integer.toString(teamScore));
				teamManualTextView.setText(Integer.toString(teamScore));
			}});
	}

	@Override
	public void updateRightTeamScoreLabel() {
    	
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				for (String key : rightPlayerNameTextViewMap.keySet())
				{
					int score = ScoreManager.getInstance().getPlayerScore(key, "Right");	
					TextView playerNameTextView = rightPlayerNameTextViewMap.get(key);
					View playerView = (View)playerNameTextView.getParent();
					TextView playerScoreTextView = (TextView)playerView.findViewById(R.id.playerScore);

					playerScoreTextView.setText(Integer.toString(score));
				}

				View teamView = activity.findViewById(R.id.team2Layout);
				TextView teamTextView = (TextView)teamView.findViewById(R.id.teamScore);
				TextView teamManualTextView = (TextView)teamView.findViewById(R.id.teamPoints);
				int teamScore = ScoreManager.getInstance().getRightTeamScore();

				teamTextView.setText(Integer.toString(teamScore));
				teamManualTextView.setText(Integer.toString(teamScore));
			}});
	}

	@Override
	public void setSelectedPlayer(final String playerName, final String playerTeamSide) {
		unselectEveryPlayer();
		View view = null;
		
		view = getSelectedPlayerView(playerName, playerTeamSide);
		
		if(view != null)
		{
			final View finalView = (View)view.getParent().getParent();
			new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
				public void run() {
					if(QuestionManager.getInstance().getCurrentQuestionTarget() == QuestionTarget.Collectif)
						finalView.setBackgroundResource(R.drawable.currentgame_player_border_selected);
					else
						((ViewFlipper)finalView).setDisplayedChild(1);
				}});
		}
	}
	private View getSelectedPlayerView(String playerName, String playerTeamSide) {
		View view = null;
		if(playerTeamSide.equals("Left"))
			view = leftPlayerNameTextViewMap.get(playerName);
		else if(playerTeamSide.equals("Right"))
			view = rightPlayerNameTextViewMap.get(playerName);
		
		return view;
	}

	@Override
	public void showIntruQuestion(final QuestionIntru question) {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
    			ViewFlipper flipper = (ViewFlipper)activity.findViewById(R.id.questionLayout);
    			flipper.setDisplayedChild(Defines.INTRU);
    			
    			TextView questionText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_question);
            	TextView words = (TextView)flipper.getCurrentView().findViewById(R.id.intru_choices);
            	TextView answerText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_answer);
            	TextView pointsText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionPoints);
            	TextView categoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionCategory);
            	TextView subCategoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionSubCategory);
            	TextView sectionDescription = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_sectionDescription);
            	
            	questionText.setText(question.getQuestion());
            	answerText.setText(question.getAnswer());
            	words.setText(buildIntruChoices(question.getChoices()));
            	pointsText.setText(QuestionManager.getInstance().getQuestionValue(question).toString());
            	categoryText.setText(CategoryType.toString(question.getCategory().getType()));
            	subCategoryText.setText(question.getCategory().getName().toString());
            	sectionDescription.setText(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());

            	TextView progress = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_progress);
            	progress.setText(String.format("%d/%d", QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber()));
            }});
	}
	private String buildIntruChoices(List<String> choices){
		String answer = "";
    	for (String choice : choices) {
			answer += choice + ", ";
		}
    	return answer.substring(0, answer.lastIndexOf(','));
	}

	@Override
	public void showAssociationQuestion(final QuestionAssociation question) {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
    			ViewFlipper flipper = (ViewFlipper)activity.findViewById(R.id.questionLayout);
    			flipper.setDisplayedChild(Defines.ASSOCIATION);
    			
            	TextView questionText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_question);
            	TextView answerLabel = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_answerLabel);
            	TextView answerText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_answer);
            	TextView pointsText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionPoints);
            	TextView categoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionCategory);
            	TextView subCategoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionSubCategory);
            	TextView sectionDescription = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_sectionDescription);
            	
            	questionText.setText(question.getQuestion());
            	int lastChoiceId = addAssociationItems(question.getChoices());
            	
            	pointsText.setText(QuestionManager.getInstance().getQuestionValue(question).toString());
            	categoryText.setText(CategoryType.toString(question.getCategory().getType()));
            	subCategoryText.setText(question.getCategory().getName().toString());
            	sectionDescription.setText(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
            	
            	RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) answerLabel.getLayoutParams();
            	params.addRule(RelativeLayout.BELOW, lastChoiceId-1);
            	params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            	answerLabel.setLayoutParams(params);
            	
            	answerText.setText(buildAssociationAnswer(question.getChoices()));
    	        answerLabel.requestLayout();
    	        answerText.requestLayout();
    	        
            	TextView progress = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_progress);
            	progress.setText(String.format("%d/%d", QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber()));
            }});
	}	
	private int addAssociationItems(List<Choice> choices){
		RelativeLayout ll = (RelativeLayout) activity.findViewById(R.id.AssociationRelativeLayout);
		RelativeLayout.LayoutParams params = null;
		int count = 1;
		int idCount = 10000;
		Set<Integer> randomIndexChoices = generateRandomChoice(choices.size());   
		Set<Integer> randomIndexAnswers = generateRandomChoice(choices.size());   
		for(@SuppressWarnings("unused") Choice choice : choices){
			TextView choiceLabel = new TextView(activity);
			choiceLabel.setId(idCount);
	        choiceLabel.setText(count + ". ");
	        choiceLabel.setTextAppearance(activity, android.R.style.TextAppearance_Large_Inverse);
	        choiceLabel.setPadding(dpToInt(10), 0, 0, dpToInt(5));
	        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	        params.addRule(RelativeLayout.BELOW, count==1?R.id.currentGame_question:idCount-4);
	        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        choiceLabel.setLayoutParams(params);
	        ll.removeView(activity.findViewById(idCount));
	        ll.addView(choiceLabel);
	        idCount++;
	        
	        TextView choiceText = new TextView(activity);
			choiceText.setId(idCount);
	        choiceText.setText(choices.get((Integer)randomIndexChoices.toArray()[count-1]).getChoice());
	        choiceText.setTextAppearance(activity, android.R.style.TextAppearance_Large_Inverse);
	        choiceText.setPadding(0, 0, 0, dpToInt(5));
	        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	        params.addRule(RelativeLayout.BELOW, count==1?R.id.currentGame_question:idCount-4);
	        params.addRule(RelativeLayout.RIGHT_OF, idCount-1);
	        choiceText.setLayoutParams(params);
	        ll.removeView(activity.findViewById(idCount));
	        ll.addView(choiceText);
	        idCount++;
	        
	        TextView answerLabel = new TextView(activity);
			answerLabel.setId(idCount);
			char asciiChar = (char)(count+64);
	        answerLabel.setText(asciiChar + ". ");
	        answerLabel.setTextAppearance(activity, android.R.style.TextAppearance_Large_Inverse);
	        answerLabel.setPadding(0, 0, 0, dpToInt(5));
	        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	        params.addRule(RelativeLayout.BELOW, count==1?R.id.currentGame_question:idCount-4);
	        params.addRule(RelativeLayout.RIGHT_OF, R.id.screenCenter);
	        answerLabel.setLayoutParams(params);
	        ll.removeView(activity.findViewById(idCount));
	        ll.addView(answerLabel);
	        idCount++;
	        
	        TextView answerText = new TextView(activity);
			answerText.setId(idCount);
	        answerText.setText(choices.get((Integer)randomIndexAnswers.toArray()[count-1]).getAnswer());
	        answerText.setTextAppearance(activity, android.R.style.TextAppearance_Large_Inverse);
	        answerText.setPadding(0, 0, 0, dpToInt(5));
	        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	        params.addRule(RelativeLayout.BELOW, count==1?R.id.currentGame_question:idCount-4);
	        params.addRule(RelativeLayout.RIGHT_OF, idCount-1);
	        answerText.setLayoutParams(params);
	        ll.removeView(activity.findViewById(idCount));
	        ll.addView(answerText);
	        idCount++;
	        
	        count++;
		}
    	
		return idCount;
	}
	private int dpToInt(int value){
		float scale = activity.getResources().getDisplayMetrics().density;
		return (int) (value*scale + 0.5f);
	}
	private String buildAssociationAnswer(List<Choice> choices){
		String answer = "";
    	for (Choice choice : choices) {
			answer += choice.getChoice() + " - ";
			answer += choice.getAnswer() + ", ";
		}
    	return answer.substring(0, answer.lastIndexOf(','));
	}
	private Set<Integer> generateRandomChoice(int count){
		Random rng = new Random(); // Ideally just create one instance globally
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < count)
		{
		    Integer next = rng.nextInt(count);
		    generated.add(next);
		}
		return generated;
	}

	@Override
	public void showAnagramQuestion(final QuestionAnagram question) {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
    			ViewFlipper flipper = (ViewFlipper)activity.findViewById(R.id.questionLayout);
    			flipper.setDisplayedChild(Defines.ANAGRAM);
    			
            	TextView questionText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_question);
            	TextView answerText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_answer);
            	TextView hintText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_indice);
            	TextView pointsText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionPoints);
            	TextView categoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionCategory);
            	TextView subCategoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionSubCategory);
            	TextView sectionDescription = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_sectionDescription);
            	
            	
            	questionText.setText(question.getAnagram());
            	answerText.setText(question.getAnswer());
            	hintText.setText(question.getHint());
            	pointsText.setText(QuestionManager.getInstance().getQuestionValue(question).toString());
            	categoryText.setText(CategoryType.toString(question.getCategory().getType()));
            	subCategoryText.setText(question.getCategory().getName().toString());
            	sectionDescription.setText(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
            	
            	TextView progress = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_progress);
            	progress.setText(String.format("%d/%d", QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber()));
              	
            }});
	}

	@Override
	public void showMultipleClueQuestion(final QuestionIdentification question) {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {            	
            	clearHints();
            	
            	ViewFlipper flipper = (ViewFlipper)activity.findViewById(R.id.questionLayout);
    			flipper.setDisplayedChild(Defines.IDENTIFICATION);    			
    			
            	TextView answerText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_answer);
            	TextView pointsText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionPoints);
            	TextView categoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionCategory);
            	TextView subCategoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionSubCategory);
            	TextView firstHint = (TextView)flipper.getCurrentView().findViewById(R.id.firstHint);
            	TextView sectionDescription = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_sectionDescription);

		    	updateAnswerPosition(R.id.firstHint);
            	
            	pointsText.setText(QuestionManager.getInstance().getQuestionValue(question).toString());
            	categoryText.setText(CategoryType.toString(question.getCategory().getType()));
            	subCategoryText.setText(question.getCategory().getName().toString());
            	firstHint.setText(question.getStatements().get(0));            	
            	answerText.setText(question.getAnswer());
            	sectionDescription.setText(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
    	        
            	TextView progress = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_progress);
            	progress.setText(String.format("%d/%d", QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber()));
            }});
	}
	public void addIdentificationItem(final List<String> hints){
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				if(currentShownHint+1 <= hints.size()) {
					RelativeLayout ll = (RelativeLayout) activity.findViewById(R.id.IdentificationRelativeLayout);
					RelativeLayout.LayoutParams params = null;
					currentShownHint++;
				
					TextView hintLabel = new TextView(activity);
					hintLabel.setId(currentHintId);
			        hintLabel.setText(currentShownHint + ". ");
			        hintLabel.setTextAppearance(activity, android.R.style.TextAppearance_Large_Inverse);
			        hintLabel.setPadding(dpToInt(10), 0, 0, dpToInt(5));
			        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			        params.addRule(RelativeLayout.BELOW, currentShownHint==2?R.id.firstHintLabel:currentHintId-2);
			        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			        hintLabel.setLayoutParams(params);
			        ll.removeView(activity.findViewById(currentHintId));
			        ll.addView(hintLabel);
			        currentHintId++;
			        
			        TextView hintText = new TextView(activity);
					hintText.setId(currentHintId);
					hintText.setText(hints.get(currentShownHint-1));
			        hintText.setTextAppearance(activity, android.R.style.TextAppearance_Large_Inverse);
			        hintText.setPadding(0, 0, 0, dpToInt(5));
			        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			        params.addRule(RelativeLayout.BELOW, currentShownHint==2?R.id.firstHintLabel:currentHintId-2);
			        params.addRule(RelativeLayout.RIGHT_OF, currentHintId-1);
			        hintText.setLayoutParams(params);
			        ll.removeView(activity.findViewById(currentHintId));
			        ll.addView(hintText);
			        currentHintId++;
			        
			    	updateAnswerPosition(currentHintId-2);
				}
			}});
	}
	private void clearHints(){
		RelativeLayout ll = (RelativeLayout) activity.findViewById(R.id.IdentificationRelativeLayout);

		for(int i=20000; i<currentHintId; i++){
			ll.removeView(activity.findViewById(i));
		}

    	currentShownHint = 1;
    	currentHintId = 20000;
	}
	private void updateAnswerPosition(int viewId){
		ViewFlipper flipper = (ViewFlipper)activity.findViewById(R.id.questionLayout);
		TextView answerLabel = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_answerLabel);
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) answerLabel.getLayoutParams();
    	params.addRule(RelativeLayout.BELOW, viewId);
    	params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    	answerLabel.setLayoutParams(params);    	
        answerLabel.requestLayout();
	}

	@Override
	public void showGeneralQuestion(final QuestionGeneral question) {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
    			ViewFlipper flipper = (ViewFlipper)activity.findViewById(R.id.questionLayout);
    			flipper.setDisplayedChild(Defines.GENERAL);
    			
            	TextView questionText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_question);
            	TextView answerText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_answer);
            	TextView pointsText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionPoints);
            	TextView categoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionCategory);
            	TextView subCategoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionSubCategory);
            	TextView sectionDescription = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_sectionDescription);
            	
            	questionText.setText(question.getQuestion());
            	answerText.setText(question.getAnswer());
            	pointsText.setText(QuestionManager.getInstance().getQuestionValue(question).toString());
            	categoryText.setText(CategoryType.toString(question.getCategory().getType()));
            	subCategoryText.setText(question.getCategory().getName().toString());
            	sectionDescription.setText(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
            	
            	TextView progress = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_progress);
            	progress.setText(String.format("%d/%d", QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber()));
            }});
	}

	@Override
	public void setRightAnswerColorToLeftPlayerNamePanel(String playerName) {

		TextView playerNameTextView = leftPlayerNameTextViewMap.get(playerName);
		
		final View playerView = (View)playerNameTextView.getParent().getParent();
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	playerView.setBackgroundResource(R.drawable.currentgame_player_border);  			
            }});
	}

	@Override
	public void setRightAnswerColorToRightPlayerNamePanel(String playerName) {

		TextView playerNameTextView = rightPlayerNameTextViewMap.get(playerName);
		final View playerView = (View)playerNameTextView.getParent().getParent();
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	playerView.setBackgroundResource(R.drawable.currentgame_player_border);  			
            }});
	}

	@Override
	public Object getPanelContent() {
		return null;
	}

	@Override
	public void highlightSelectedPlayerPanel(String playerName, String playerTeam) {
		
		setSelectedPlayer(playerName, playerTeam);	
	}

	@Override
	public PlayerInfo getPlayerInfoFromPosition(int team, int player) {

		// Correct the mapping from the console
		int correctedPlayer =  player + 1;
		if(team==1)
		{
			switch (correctedPlayer) {
			case 1:
				correctedPlayer = 4;
				break;
			case 2:
				correctedPlayer = 3;
				break;
			case 3:
				correctedPlayer = 2;
				break;
			case 4:
				correctedPlayer = 1;
				break;

			default:
				break;
			}
		}
		
		View teamLayout = (View)activity.findViewById(
				activity.getResources().getIdentifier("team" + team + "Layout", "id", activity.getPackageName()));
		
		View playerLayout = (View)teamLayout.findViewById(
				activity.getResources().getIdentifier("layoutPlayer" + correctedPlayer, "id", activity.getPackageName()));
		
		TextView teamTextView = ((TextView)playerLayout.findViewById(R.id.playerName));
		String PlayerName = (String) teamTextView.getText();
				
		String teamName = team == 1 ? "Left" : "Right";
		
		
		return new PlayerInfo(PlayerName, teamName);
	}

	@Override
	public void showMediaContentInProjectorMode() {
	}

	@Override
	public void nextAudioTrack() {
	}

	@Override
	public void nextVideoTrack() {
	}

	@Override
	public void previousAudioTrack() {
	}

	@Override
	public void previousVideoTrack() {
	}

	@Override
	public void playSound() {
	}

	@Override
	public void playVideo() {
	}

	@Override
	public void pauseSound() {
	}

	@Override
	public void pauseVideo() {
	}

	@Override
	public void stopSound() {
	}

	@Override
	public void stopVideo() {
	}

	@Override
	public void disableSkipButton() {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnNext).setEnabled(false);			
			}});
	}

	@Override
	public void enableSkipButton() {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnNext).setEnabled(true);			
			}});
	}

	@Override
	public boolean isSkipButtonEnabled() {
		return activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnNext).isEnabled();
	}

	@Override
	public void nextImage() {
	}

	@Override
	public void previousImage() {
	}

	@Override
	public void enablePlayerIndividualPointEdition(String playerName,
			String playerTeam) {
	}

	@Override
	public void disableAllPlayerIndividualPointEdition() {
	}

	@Override
	public void resetUI() {
		model.setIsEndOfGameReached(false);
		model.setIsPlayerSelectionEnabled(true);
		model.setAreStatsPosted(false);
	}

	@Override
	public void enableScoresButton() {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnGoodAnswer).setEnabled(true);	
            	activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnWrongAnswer).setEnabled(true);
            	activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnSubstractPoints).setEnabled(true);	
            }});		
	}

	@Override
	public void disableScoresButton() {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnGoodAnswer).setEnabled(false);	
            	activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnWrongAnswer).setEnabled(false);	
            	activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnSubstractPoints).setEnabled(false);
            }});	
	}
	
	@Override
	public void enablePrevButton() {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnBack).setEnabled(true);			
			}});
	}
	
	@Override
	public void disablePrevButton() {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				activity.findViewById(R.id.buttonsLayout).findViewById(R.id.currentGame_btnBack).setEnabled(false);			
			}});
	}

	@Override
	public void adjustSelectedPlayerScoreLabel(final String playerSide, final String playerName, final int value) {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				View view = (View)getSelectedPlayerView(playerName, playerSide).getParent().getParent();
				view = view.findViewById(R.id.playerManagePoints).findViewById(R.id.playerPoints);
				int oldValue = Integer.parseInt((String)((TextView)view).getText());
				((TextView)view).setText(String.format("%d", oldValue + value));
			}});	
	}

	@Override
	public void setPlayerIndividualScore(final String playerSide, final String playerName, final int value) {
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				View view = (View)getSelectedPlayerView(playerName, playerSide).getParent().getParent();
				view = view.findViewById(R.id.playerManagePoints).findViewById(R.id.playerPoints);
				((TextView)view).setText(String.format("%d", value));
			}});
	}
	
	@Override
	public void showMediaQuestion(final QuestionMedia question) {
		final String contentType = FileManager.getInstance().getQuestionMediaContentType(question.getMediaUrl());
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
    			ViewFlipper flipper = (ViewFlipper)activity.findViewById(R.id.questionLayout);
    			flipper.setDisplayedChild(Defines.MULTI_MEDIA);
    			ViewFlipper mediaFlipper = (ViewFlipper)flipper.findViewById(R.id.currentGame_MediaButtons_Flipper);
    			
        		if (contentType.contains("video")){
        			mediaFlipper.setDisplayedChild(0);
        			model.setMediaMode(MediaMode.VIDEO);
        		}
        		else if (contentType.contains("audio")){
        			mediaFlipper.setDisplayedChild(0);
        			model.setMediaMode(MediaMode.AUDIO);
        		}
        		else if (contentType.contains("image")){
        			mediaFlipper.setDisplayedChild(1);
        			model.setMediaMode(MediaMode.PICTURE);
        		}
        		
            	TextView questionText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_question);
            	TextView answerText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_answer);
            	TextView pointsText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionPoints);
            	TextView categoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionCategory);
            	TextView subCategoryText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_questionSubCategory);
            	TextView sectionDescription = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_sectionDescription);
            	
            	questionText.setText(question.getStatement());
            	answerText.setText(question.getAnswer());
            	pointsText.setText(QuestionManager.getInstance().getQuestionValue(question).toString());
            	categoryText.setText(CategoryType.toString(question.getCategory().getType()));
            	subCategoryText.setText(question.getCategory().getName().toString());
            	sectionDescription.setText(QuestionManager.getInstance().getCurrentQuestionSetSection().getDescription());
            	
            	TextView progress = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_progress);
            	progress.setText(String.format("%d/%d", QuestionManager.getInstance().getCurrentQuestionNumber(), QuestionManager.getInstance().getTotalQuestionNumber()));
            }});
	}

	@Override
	public void updateViewOnPageChanged() {
	}

	@Override
	public void playBuzzerSound() {
	}

	@Override
	public boolean isProjectorModeEnabled() {
		return false;
	}

	@Override
	public void initPanelAudioPlayer(int maxValue) {
	}

	@Override
	public void updatePanelAudioPlayer(int newValue, int currentSoundTime) {
	}

	@Override
	public void resetPanelAudioPlayer(int totalTrackTimeInMilliseconds) {
	}

	@Override
	public void showImage() {
	}

	@Override
	public void resetEventProcessedCounter() {
	}

	@Override
	public void hideCurrentQuestion() {

		ViewFlipper flipper = (ViewFlipper)activity.findViewById(R.id.questionLayout);
		final TextView questionText = (TextView)flipper.getCurrentView().findViewById(R.id.currentGame_question);

		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				questionText.setVisibility(View.INVISIBLE);
			}});

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				questionText.setVisibility(View.VISIBLE);
			}});
	}
	
	@Override
	public void swapPlayers() {
		
		if(isSwappedPlayers)
			isSwappedPlayers = false;
		else
			isSwappedPlayers = true;
		
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				
				LinearLayout gameLayout = (LinearLayout)activity.findViewById(R.id.gameLayout);
				
				LinearLayout leftTeamLayout = (LinearLayout)gameLayout.findViewById(R.id.team1Layout);
				LinearLayout rightTeamLayout = (LinearLayout)gameLayout.findViewById(R.id.team2Layout);
				View questionLayout = (View)gameLayout.findViewById(R.id.questionLayout);
				
				View team1Layout = (View)leftTeamLayout.findViewById(R.id.layoutTeam);
				View team2Layout = (View)rightTeamLayout.findViewById(R.id.layoutTeam);
				
				View player1LeftLayout = (View)leftTeamLayout.findViewById(R.id.layoutPlayer1);
				View player2LeftLayout = (View)leftTeamLayout.findViewById(R.id.layoutPlayer2);
				View player3LeftLayout = (View)leftTeamLayout.findViewById(R.id.layoutPlayer3);
				View player4LeftLayout = (View)leftTeamLayout.findViewById(R.id.layoutPlayer4);
				
				View player1RightLayout = (View)rightTeamLayout.findViewById(R.id.layoutPlayer1);
				View player2RightLayout = (View)rightTeamLayout.findViewById(R.id.layoutPlayer2);
				View player3RightLayout = (View)rightTeamLayout.findViewById(R.id.layoutPlayer3);
				View player4RightLayout = (View)rightTeamLayout.findViewById(R.id.layoutPlayer4);
				
				gameLayout.removeAllViews();
				rightTeamLayout.removeAllViews();
				leftTeamLayout.removeAllViews();
				
				if(isSwappedPlayers)
				{
					gameLayout.addView(rightTeamLayout);
					gameLayout.addView(questionLayout);
					gameLayout.addView(leftTeamLayout);
					
					leftTeamLayout.addView(team1Layout);
					leftTeamLayout.addView(player1LeftLayout);
					leftTeamLayout.addView(player2LeftLayout);
					leftTeamLayout.addView(player3LeftLayout);
					leftTeamLayout.addView(player4LeftLayout);
					
					rightTeamLayout.addView(team2Layout);
					rightTeamLayout.addView(player1RightLayout);
					rightTeamLayout.addView(player2RightLayout);
					rightTeamLayout.addView(player3RightLayout);
					rightTeamLayout.addView(player4RightLayout);
					
				}
				else
				{
					gameLayout.addView(leftTeamLayout);
					gameLayout.addView(questionLayout);
					gameLayout.addView(rightTeamLayout);
					
					leftTeamLayout.addView(team1Layout);
					leftTeamLayout.addView(player4LeftLayout);
					leftTeamLayout.addView(player3LeftLayout);
					leftTeamLayout.addView(player2LeftLayout);
					leftTeamLayout.addView(player1LeftLayout);
					
					rightTeamLayout.addView(team2Layout);
					rightTeamLayout.addView(player4RightLayout);
					rightTeamLayout.addView(player3RightLayout);
					rightTeamLayout.addView(player2RightLayout);
					rightTeamLayout.addView(player1RightLayout);
				}
				
			}});	
	}
}
