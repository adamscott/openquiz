package ca.openquiz.mobile.bluetooth;

import Console.bluetooth.event.BluetoothDiscoveryDoneEvent;
import Console.bluetooth.event.BluetoothDiscoveryDoneListener;
import Console.bluetooth.event.ConsoleBaseEvent;
import Console.bluetooth.event.ConsoleEventClassListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.util.Defines;
import ca.openquiz.mobile.wizardQuestionSet.PlayGameAsyncTask;

public class BluetoothActivity extends Activity {

	private AndroidBluetoothClientSocket btSocket = null;
	private ArrayAdapter<String> bluetoothDevicesAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] onCreate BluetoothActivity");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bluetooth_screen);

		findViewById(R.id.loading).setVisibility(View.VISIBLE);

		bluetoothDevicesAdapter = new ArrayAdapter<String>(this.getBaseContext(), R.layout.bluetooth_list_item);
		
		btSocket = AndroidBluetoothClientSocket.getInstance();
		btSocket.setup(BluetoothActivity.this);

		final ListView listView = (ListView) findViewById(R.id.device_list);
		listView.setAdapter(bluetoothDevicesAdapter);

		registerListeners();
	}

	@Override
	protected void onStop() {
		if(btSocket != null)
		{
			if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] onStop BluetoothActivity");
			btSocket.closeDown();
			unregisterReceiver(btSocket.getmReceiver());
			btSocket = null;
		}

		super.onStop();
	};

	@Override
	public void onStart()
	{
		super.onStart();	
		if(Defines.PRINT_DEBUG_THREAD) System.out.println("[DEBUG] onStart() activity bluetooth");
		AndroidBluetoothClientSocket.getInstance().destroyAllThreads();
		
		bluetoothDevicesAdapter.notifyDataSetInvalidated();
		refreshConnection();
	}

	@Override
	public void onDestroy() {
		if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] onDestroy BluetoothActivity");
		super.onDestroy();
	}

	public void writeToLog(final String message) {
		System.out.println("btooth=" + message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	/******************************************************************************************************************
	 ********************************** Listeners implementation ******************************************************
	 ******************************************************************************************************************/

	private void registerListeners()
	{
		final ListView listView = (ListView) findViewById(R.id.device_list);
		listView.setOnItemClickListener(consoleSelected);

		final Button buttonRefresh = (Button) findViewById(R.id.button_refresh);
		final Button buttonIgnore = (Button) findViewById(R.id.button_ignore);

		buttonRefresh.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				refreshConnection();
			}
		});
		
		buttonIgnore.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				new PlayGameAsyncTask(BluetoothActivity.this).execute();
			}
		});
		
		btSocket.addDiscoveryListeners(new BluetoothDiscoveryDoneListener() {

			@Override
			public void handleDiscoveryDone(BluetoothDiscoveryDoneEvent e) {

				bluetoothDevicesAdapter.clear();
				
				if(e.getConsolesFound().size() > 0){
					writeToLog("Found consoles:");

					for(String item : e.getConsolesFound()){
						writeToLog(item);

						bluetoothDevicesAdapter.add(item);

						findViewById(R.id.loading).setVisibility(View.INVISIBLE);
					}
				}
			}
		});

		btSocket.addEventListener(new ConsoleEventClassListener() {

			@Override
			public void handleConsoleEvent(ConsoleBaseEvent e) {
				writeToLog("Received: "
						+ e.toString());
			}
		});
	}

	AdapterView.OnItemClickListener consoleSelected = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
			if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] AdapterView.OnItemClickListener consoleSelected");
			
			findViewById(R.id.loading).setVisibility(View.VISIBLE);
			String consoleName = bluetoothDevicesAdapter.getItem(position);

			try {
				if(btSocket != null)
				{
					if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] consoleName=" + consoleName);

					btSocket.startConnectDeviceThread(consoleName);
				}
			} catch (Exception e1) {
				writeToLog(e1.getMessage());
			}
			if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] AdapterView.OnItemClickListener consoleSelected -- Ending");
		}
	};
	
	private void refreshConnection()
	{
		try {
			findViewById(R.id.loading).setVisibility(View.VISIBLE);
			
			if(btSocket != null) {
				if(Defines.PRINT_DEBUG_INFO) System.out.println("***** [DEBUG] closeDown socket *****");
				btSocket.closeDown();
			}
			
			btSocket.init();
		} catch (Exception e1) {

			//IMPORTANT!!! IF an exception is thrown here, you should create a new AndroidBluetoothClientSocket and not reuse it!
			writeToLog(e1.getMessage());
			btSocket = null;

			this.finish();	//Finish the activity and go back to previous one.
		}
	}
}
