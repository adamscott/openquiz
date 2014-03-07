package ca.openquiz.mobile.currentGameController;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CurrentGameController extends currentGame.CurrentGameController
{
	private static CurrentGameController instance = null;
	private static final int[] sleepMsTimeArray = {500,1000,2000,4000,6000};
	private int sleepArrayIndex = 0;
	
	public static CurrentGameController getInstance()
	{
		if(instance == null)
			instance = new CurrentGameController();

		return instance;
	}
	
	public boolean handleRetry(Activity currentActivity, String method)
	{
		sleepArrayIndex = 0;

		while(!isInternetConnectionAvailable(currentActivity))
		{
			try {
				Thread.sleep(sleepMsTimeArray[sleepArrayIndex]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			sleepArrayIndex++;

			if(sleepArrayIndex == sleepMsTimeArray.length)
			{
				return true;
			}
		}

		return false;
	}

	private boolean isInternetConnectionAvailable(Activity currentActivity)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	@Override
	public void initData(){
		boolean isNextActOnEndGame = false;
		if (model.getIsEndOfGameReached()){
			isNextActOnEndGame = true;
			view.resetUI();
		}
		
		showCurrentQuestion();
		views.loadTeamInfo();
		view.disableScoresButton();
		
		if (isNextActOnEndGame){
			previousQuestion();
		}
	}
}
