package ca.openquiz.mobile.currentGame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ViewFlipper;
import applicationTools.QuestionManager;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.ThreadManager;
import ca.openquiz.mobile.currentGameController.CurrentGameTask;

public class CurrentGameAct extends Activity
{

    private CurrentGameTask currentGameTask = new CurrentGameTask();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		overridePendingTransition(R.anim.anim_toright_in,R.anim.anim_toright_out);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
				
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.initCurrentGameAct(CurrentGameAct.this));

		setContentView(R.layout.currentgame_screen);
		
		registerListeners();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.initCurrentGameAct());
	}

	private void registerListeners()
	{
		findViewById(R.id.currentGame_btnGoodAnswer).setOnClickListener(goodAnswerButtonListener);
		findViewById(R.id.currentGame_btnSubstractPoints).setOnClickListener(substractPointsButtonListener);
		findViewById(R.id.currentGame_btnWrongAnswer).setOnClickListener(wrongAnswerButtonListener);
		findViewById(R.id.currentGame_btnNext).setOnClickListener(nextButtonListener);
		findViewById(R.id.currentGame_btnBack).setOnClickListener(prevButtonListener);
		findViewById(R.id.currentGame_btnSwapPlayers).setOnClickListener(swapButtonListener);
		findViewById(R.id.nextHintButton).setOnClickListener(nextHintButtonListener);
		findViewById(R.id.currentGame_MediaButtons_Video_Play).setOnClickListener(playMediaButtonListener);
		findViewById(R.id.currentGame_MediaButtons_Video_Stop).setOnClickListener(stopMediaButtonListener);
		findViewById(R.id.currentGame_MediaButtons_Video_Pause).setOnClickListener(pauseMediaButtonListener);
		findViewById(R.id.currentGame_MediaButtons_ShowImage).setOnClickListener(playMediaButtonListener);
		
		for(int i=1; i<=2; i++){
			View teamView = findViewById(getResources().getIdentifier("team" + i + "Layout", "id", getPackageName()));
			teamView.findViewById(R.id.layoutTeam).setOnClickListener(teamClickListener);
			teamView.findViewById(R.id.addPoints).setOnClickListener(addTeamPointsButtonListener);
			teamView.findViewById(R.id.removePoints).setOnClickListener(removeTeamPointsButtonListener);
			teamView.findViewById(R.id.confirmPoints).setOnClickListener(confirmTeamPointsButtonListener);
			for(int j=1; j<=4; j++){
				View playerView = teamView.findViewById(getResources().getIdentifier("layoutPlayer" + j, "id", getPackageName()));
				playerView.setOnClickListener(playerClickListener);
				playerView.findViewById(R.id.addPoints).setOnClickListener(addIndividualPointsButtonListener);
				playerView.findViewById(R.id.removePoints).setOnClickListener(removeIndividualPointsButtonListener);
				playerView.findViewById(R.id.confirmPoints).setOnClickListener(confirmIndividualPointsButtonListener);
			}
		}
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.anim_toleft_in, R.anim.anim_toleft_out);
	    QuestionManager.getInstance().resetGame();
	}

	//	/******************************************************************************************************************
	//	 ********************************** Listeners implementation ******************************************************
	//	 ******************************************************************************************************************/
		
	View.OnClickListener goodAnswerButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.goodAnswer());
		}
	};
	
	View.OnClickListener substractPointsButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.substractPoints());
		}
	};
	
	View.OnClickListener wrongAnswerButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.wrongAnswer());
		}
	};
	
	View.OnClickListener nextButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.skipQuestion());
		}
	};
	
	View.OnClickListener prevButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.prevQuestion());
		}
	};
	
	View.OnClickListener swapButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.swapPlayers());
		}
	};
	
	View.OnClickListener playerClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			TextView playerTextView = (TextView)v.findViewById(R.id.playerName);
			TextView teamTextView = (TextView) ((View)v.getParent()).findViewById(R.id.layoutTeam).findViewById(R.id.teamInfos).findViewById(R.id.teamName);
			View sideView = (View) ((View)teamTextView.getParent().getParent().getParent());

			String teamSide = null;
			if(sideView.getId() == R.id.team1Layout)
				teamSide = "Left";
			else if(sideView.getId() == R.id.team2Layout)
				teamSide = "Right";
			
			if(teamSide != null)
				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(
						currentGameTask.setSelectedPlayer(playerTextView.getText().toString(), teamSide));
		}
	};
	
	View.OnClickListener nextHintButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.nextHint());
		}
	};
	
	View.OnClickListener playMediaButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.playMedia());
		}
	};
	
	View.OnClickListener pauseMediaButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.pauseMedia());
		}
	};
	
	View.OnClickListener stopMediaButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.stopMedia());
		}
	};
	
	View.OnClickListener addIndividualPointsButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			System.out.println("addPoints");
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.adjustPlayerScoreForIndividualQuestion(1));
		}
	};
	
	View.OnClickListener removeIndividualPointsButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			System.out.println("removePoints");
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.adjustPlayerScoreForIndividualQuestion(-1));
		}
	};
	
	View.OnClickListener confirmIndividualPointsButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			System.out.println("confirmPoints");
			((ViewFlipper)v.getParent().getParent()).setDisplayedChild(0);
		}
	};
	
	View.OnClickListener teamClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String currentScore = (String)((TextView)v.findViewById(R.id.teamInfos).findViewById(R.id.teamScore)).getText();
			((TextView)v.findViewById(R.id.teamManagePoints).findViewById(R.id.teamPoints)).setText(currentScore);
			((ViewFlipper)v).setDisplayedChild(1);
		}
	};
	
	View.OnClickListener addTeamPointsButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {			
			View sideView = (View)v.getParent().getParent().getParent();

			String teamSide = null;
			if(sideView.getId() == R.id.team1Layout)
				teamSide = "Left";
			else if(sideView.getId() == R.id.team2Layout)
				teamSide = "Right";
			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.adjustTeamScore(teamSide, 1));
		}
	};
	
	View.OnClickListener removeTeamPointsButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			View sideView = (View)v.getParent().getParent().getParent();

			String teamSide = null;
			if(sideView.getId() == R.id.team1Layout)
				teamSide = "Left";
			else if(sideView.getId() == R.id.team2Layout)
				teamSide = "Right";
			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.adjustTeamScore(teamSide, -1));
		}
	};

	View.OnClickListener confirmTeamPointsButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			((ViewFlipper)v.getParent().getParent()).setDisplayedChild(0);
		}
	};
}