package ca.openquiz.mobile.currentGameController;
import java.util.List;

import currentGame.CurrentGameViewWrapper;
import android.app.Activity;
import applicationTools.QuestionManager;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.mobile.currentGameController.CurrentGameController;
import ca.openquiz.mobile.currentGameController.CurrentGameView;

public class CurrentGameTask {

	private CurrentGameController currentGameController = CurrentGameController.getInstance();  

	public Runnable initCurrentGameAct(final Activity act)
	{
		Runnable init = new Runnable() {

			public void run() {
				CurrentGameViewWrapper views = new CurrentGameViewWrapper();
				CurrentGameView view = new CurrentGameView();
				views.registerView(view);
				currentGameController.registerViews(views);
				currentGameController.registerMainView(view);
				((CurrentGameView)currentGameController.getView()).setActivity(act);

            	view.setModel(currentGameController.getModel());
			}
		};

		return init;
	}
	
	public Runnable initCurrentGameAct()
	{
		Runnable init = new Runnable() {

			public void run() {

            	currentGameController.initData();
			}
		};

		return init;
	}
	
	public Runnable skipQuestion()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.skipQuestionApp();
			}
		};
		
		return init;
	}
	
	public Runnable prevQuestion()
	{
		Runnable init = new Runnable() {

			public void run() {
            	currentGameController.previousQuestionApp();
			}
		};

		return init;
	}
	
	public Runnable setSelectedPlayer(final String playerName, final String teamSide)
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.highlightSelectedPlayerPanelApp(playerName, teamSide);
			}
		};
		
		return init;
	}

	public Runnable goodAnswer()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.goodAnswerApp();
			}
		};
		
		return init;
	}
	
	public Runnable substractPoints()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.substractPointsApp();
			}
		};
		
		return init;
	}
	
	public Runnable wrongAnswer()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.wrongAnswerApp();
			}
		};
		
		return init;
	}
	
	public Runnable nextHint()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				List<String> hints = ((QuestionIdentification)QuestionManager.getInstance().getCurrentQuestion()).getStatements();
				((CurrentGameView)currentGameController.getView()).addIdentificationItem(hints);
			}
		};
		
		return init;
	}
	
	public Runnable adjustPlayerScoreForIndividualQuestion(final int multiplicator)
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.adjustPlayerScoreForIndividualQuestionApp(multiplicator);
			}
		};
		
		return init;
	}
	
	public Runnable adjustTeamScore(final String side, final int multiplicator)
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.adjustTeamScoreApp(side, multiplicator);
			}
		};
		
		return init;
	}
	
	public Runnable playMedia()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.playMediaApp();
			}
		};
		
		return init;
	}
	
	public Runnable pauseMedia()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.pauseMediaApp();
			}
		};
		
		return init;
	}
	
	public Runnable stopMedia()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.stopMediaApp();
			}
		};
		
		return init;
	}
	
	public Runnable swapPlayers()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				((CurrentGameView)currentGameController.getView()).swapPlayers();
			}
		};
		
		return init;
	}
	
	public Runnable closeGame()
	{
		Runnable init = new Runnable() {
			
			public void run() {
				currentGameController.setGameAsEnded();
			}
		};
		
		return init;
	}
}
