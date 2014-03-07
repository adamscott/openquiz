package ca.openquiz.mobile.currentGame;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import applicationTools.ScoreManager;
import applicationTools.TeamManager;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.ThreadManager;
import ca.openquiz.mobile.bluetooth.AndroidBluetoothClientSocket;
import ca.openquiz.mobile.currentGameController.CurrentGameTask;
import ca.openquiz.mobile.wizardTeam.WizardTeamAct;

public class EndOfGameAct extends Activity
{
	private CurrentGameTask currentGameTask = new CurrentGameTask();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.anim_toright_in,R.anim.anim_toright_out);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
						
		setContentView(R.layout.endofgame_screen);
		
		setViewData();
		
		registerListeners();
	}
	
	private void setViewData(){
		// Set stats for team 1
		((TextView)findViewById(R.id.team1Label)).setText(TeamManager.getInstance().getLeftTeam().getName());
		List<String> leftPlayersNames = new ArrayList<String>();
		for(String player : TeamManager.getInstance().getLeftTeamNames())
			leftPlayersNames.add(player);
		
		final int team1Size = TeamManager.getInstance().getLeftTeamNames().size();
		View playerTeam1[] = new View[team1Size];
		
		for(int n=0; n<team1Size; n++)
		{
			playerTeam1[n] = (View)findViewById(
					this.getResources().getIdentifier("player" + (n+1) + "Team1", "id", getPackageName()));
			
			((TextView)playerTeam1[n].findViewById(R.id.playerName)).setText(leftPlayersNames.get(n));
			
			((TextView)playerTeam1[n].findViewById(R.id.goodAnswerRatio)).setText(
					buildPlayerAnswerRatio(ScoreManager.getInstance().getNbOfGoodAnswerForPlayer(leftPlayersNames.get(n), "Left"), 
							ScoreManager.getInstance().getNbOfBadAnswerForPlayer(leftPlayersNames.get(n), "Left")));
			
			((TextView)playerTeam1[n].findViewById(R.id.goodAnswerPercentage)).setText(
					buildPlayerAnswerPercentage(ScoreManager.getInstance().getNbOfGoodAnswerForPlayer(leftPlayersNames.get(n), "Left"), 
							ScoreManager.getInstance().getNbOfBadAnswerForPlayer(leftPlayersNames.get(n), "Left")));
		}
		
		// Set stats for team 2		
		((TextView)findViewById(R.id.team2Label)).setText(TeamManager.getInstance().getRightTeam().getName());
		List<String> rightPlayersNames = new ArrayList<String>();
		for(String player : TeamManager.getInstance().getRightTeamNames())
			rightPlayersNames.add(player);
		
		final int team2Size = TeamManager.getInstance().getRightTeamNames().size();
		View playerTeam2[] = new View[team2Size];
		
		for(int n=0; n<team2Size; n++)
		{
			playerTeam2[n] = (View)findViewById(
					this.getResources().getIdentifier("player" + (n+1) + "Team2", "id", getPackageName()));
			
			((TextView)playerTeam2[n].findViewById(R.id.playerName)).setText(rightPlayersNames.get(n));
			
			((TextView)playerTeam2[n].findViewById(R.id.goodAnswerRatio)).setText(
					buildPlayerAnswerRatio(ScoreManager.getInstance().getNbOfGoodAnswerForPlayer(rightPlayersNames.get(n), "Right"), 
							ScoreManager.getInstance().getNbOfBadAnswerForPlayer(rightPlayersNames.get(n), "Right")));
			
			((TextView)playerTeam2[n].findViewById(R.id.goodAnswerPercentage)).setText(
					buildPlayerAnswerPercentage(ScoreManager.getInstance().getNbOfGoodAnswerForPlayer(rightPlayersNames.get(n), "Right"), 
							ScoreManager.getInstance().getNbOfBadAnswerForPlayer(rightPlayersNames.get(n), "Right")));
		}
	}
	
	private String buildPlayerAnswerRatio(int goodAnswers, int badAnswers){
		return (goodAnswers + "/" + (goodAnswers + badAnswers));
	}
	private String buildPlayerAnswerPercentage(int goodAnswers, int badAnswers){
		float percentage = 0.0f;
		if((goodAnswers + badAnswers) != 0){			
			percentage = ((float)goodAnswers / (float)(goodAnswers + badAnswers));
		} 
		percentage *= 100;
		return String.format("%3.0f", percentage)+"%";
	}
	
	private void registerListeners()
	{
		findViewById(R.id.currentGame_btnHome).setOnClickListener(homeButtonListener);
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.anim_toleft_in, R.anim.anim_toleft_out);
	}

	//	/******************************************************************************************************************
	//	 ********************************** Listeners implementation ******************************************************
	//	 ******************************************************************************************************************/
	View.OnClickListener homeButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String title = EndOfGameAct.this.getResources().getString(R.string.message_quit_game_title);
			String message = EndOfGameAct.this.getResources().getString(R.string.message_quit_game_message);
			
			AlertDialog.Builder alert = new AlertDialog.Builder(EndOfGameAct.this);
			
			alert.setTitle(title);
		    alert.setMessage(message);
		    alert.setPositiveButton(R.string.positive_dialog_button, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 

		        	AndroidBluetoothClientSocket.getInstance().destroyAllThreads();
		        	ThreadManager.getInstance().getCreateNewGameThreadPool().execute(currentGameTask.closeGame());
		        	
					Intent intent = new Intent(getBaseContext(), WizardTeamAct.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					EndOfGameAct.this.startActivity(intent);
		        }
		     });
		    alert.setNegativeButton(R.string.negative_dialog_button, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	//Do nothing
		        }
		     });
		    alert.show();
		}
	};
}