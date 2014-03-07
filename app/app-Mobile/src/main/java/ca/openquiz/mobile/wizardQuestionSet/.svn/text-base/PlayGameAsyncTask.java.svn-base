package ca.openquiz.mobile.wizardQuestionSet;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.bluetooth.AndroidBluetoothClientSocket;
import ca.openquiz.mobile.createNewGame.CreateNewGameController;
import ca.openquiz.mobile.currentGame.CurrentGameAct;
import ca.openquiz.mobile.currentGameController.CurrentGameController;
import ca.openquiz.mobile.util.AlertBuilder;

public class PlayGameAsyncTask extends AsyncTask<Void, Void, Boolean>{
	
	private Activity activity;
	
	public PlayGameAsyncTask(Activity activity)
	{
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {

		CurrentGameController.getInstance().setProcessEvent(true);	// hack sketchy, to bypass PC.
		return CreateNewGameController.getInstance().playGame();
	}

	@Override
    protected void onPostExecute(Boolean result) {
		
		if(result)
		{
			Intent intent = new Intent(activity.getBaseContext(), CurrentGameAct.class);
			activity.startActivity(intent);
		}
		else
		{
			AndroidBluetoothClientSocket.getInstance().destroyAllThreads();
			
			String title = activity.getResources().getString(R.string.message_play_game_title);
			String message = activity.getResources().getString(R.string.message_play_game_message);
			AlertBuilder.showPopUp(activity, title, message);
		}
    }
}
