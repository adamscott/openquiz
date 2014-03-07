package ca.openquiz.mobile.wizardTeam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.ThreadManager;
import ca.openquiz.mobile.bluetooth.BluetoothActivity;
import ca.openquiz.mobile.createNewGame.CreateNewGameController;
import ca.openquiz.mobile.createNewGame.CreateNewGameTask;
import ca.openquiz.mobile.createNewGame.CreateNewGameView;
import ca.openquiz.mobile.util.Defines;
import ca.openquiz.mobile.wizardQuestionSet.PlayGameAsyncTask;

public class JoinGameActivity extends Activity {

	private ArrayAdapter<String> gamesAdapter;
	private CreateNewGameTask createNewGameTask = new CreateNewGameTask();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.joingame_screen);

		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.initJoinGameAct(JoinGameActivity.this));
		
		gamesAdapter = new ArrayAdapter<String>(this.getBaseContext(), R.layout.simple_list_item);
		
		final ListView listView = (ListView) findViewById(R.id.games_list);
		listView.setAdapter(gamesAdapter);
		
		registerListeners();		
	}
	
	@Override
	public void onStart()
	{
		super.onStart();	
		
		((CreateNewGameView)CreateNewGameController.getInstance().getView()).setIsLoadGeneratedGame(true);
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.refreshGames());
	}	
	

	/******************************************************************************************************************
	 ********************************** Listeners implementation ******************************************************
	 ******************************************************************************************************************/

	private void registerListeners()
	{
		final ListView listView = (ListView) findViewById(R.id.games_list);
		listView.setOnItemClickListener(gameSelected);

		final Button buttonJoin = (Button) findViewById(R.id.button_join);

		buttonJoin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {				
				if(Defines.ENABLE_BLUETOOTH)
				{
					Intent intent = new Intent(getBaseContext(), BluetoothActivity.class);
					startActivity(intent);
				}
				else
				{
					new PlayGameAsyncTask(JoinGameActivity.this).execute();					
				}
			}
		});
		
		final Button buttonRefresh = (Button) findViewById(R.id.button_refresh);

		buttonRefresh.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {				

				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.refreshGames());
			}
		});
	}

	AdapterView.OnItemClickListener gameSelected = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
			
			((CreateNewGameView)CreateNewGameController.getInstance().getView()).setSelectedGameIndex(position);
		}
	};
}
