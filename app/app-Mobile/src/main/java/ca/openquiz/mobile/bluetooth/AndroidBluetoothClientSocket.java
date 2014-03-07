package ca.openquiz.mobile.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import Console.bluetooth.event.BluetoothConnectionClosedListener;
import Console.bluetooth.event.BluetoothDiscoveryDoneEvent;
import Console.bluetooth.event.BluetoothDiscoveryDoneListener;
import Console.bluetooth.event.ConsoleBaseEvent;
import Console.bluetooth.event.ConsoleEventClassListener;
import Console.bluetooth.event.ConsoleEventListener;
import Console.bluetooth.event.ConsoleEventPressed;
import Console.bluetooth.event.ConsoleEventScoreUpdate;
import Console.bluetooth.event.ConsoleEventSerializedMessage;
import Console.bluetooth.event.ConsoleEventSettings;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import applicationTools.IBluetoothManager;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.util.AlertBuilder;
import ca.openquiz.mobile.util.Defines;
import ca.openquiz.mobile.wizardQuestionSet.PlayGameAsyncTask;

public class AndroidBluetoothClientSocket implements IBluetoothManager{

	private Thread listeningThread = null;
	private Thread writingThread = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothDevice consoleDevice = null;
	private BluetoothSocket mmSocket = null;
	private InputStream mmInStream = null;
	private OutputStream mmOutStream = null;
	private Activity activity = null;
	private Semaphore connectedLock = new Semaphore(1);
	private boolean isClosed = false;
	private String multiMediaEvent = "";
	
	private List<ConsoleEventClassListener> eventListeners = new ArrayList<ConsoleEventClassListener>();

	private List<BluetoothDiscoveryDoneListener> discoveryListeners = new ArrayList<BluetoothDiscoveryDoneListener>();
	
	private List<BluetoothDevice> consolesFound = new ArrayList<BluetoothDevice>();
	
	private boolean isConnected = false;
	
	private BluetoothStatus status = BluetoothStatus.NOT_CONNECTED;
	
	private BluetoothConnectionClosedListener bluetoothListener;

	public enum BluetoothStatus{
		NOT_CONNECTED,
		DISCOVERING,
		CONSOLE_NOT_FOUND,
		CONNECTING,
		CONNECTED,
		INITIALIZING,
		INIT_FAILED_BLUETOOTH_NOT_AVAILABLE,
		BLUETOOTH_NOT_ENABLED, 
		DISCOVERY_DONE
	}
	
	// Singleton stuff
	private static AndroidBluetoothClientSocket instance = null;
	
	private AndroidBluetoothClientSocket()
	{
		
	}
	
	public static AndroidBluetoothClientSocket getInstance()
	{
		if(instance == null)
			instance = new AndroidBluetoothClientSocket();
		
		return instance;
	}
	
	// Create a BroadcastReceiver for ACTION_FOUND
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				printToScreen("Found device: " + device.getName() + " - " + device.getAddress());

				if (device.getName() != null && device.getName().startsWith("openquiz")) {
					consolesFound.add(device);
				}
			}else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
				if (consoleDevice == null) {
					// The discovery is finished and we didnt find the openquiz
					// console.
					setStatus(BluetoothStatus.DISCOVERY_DONE);
					printToScreen("Discovery finished, openquiz console not found");
					ArrayList<String> consoleNames = new ArrayList<String>();
					for( BluetoothDevice d : consolesFound){
						consoleNames.add(d.getName());
					}
					for (BluetoothDiscoveryDoneListener item : discoveryListeners) {
						
						item.handleDiscoveryDone(new BluetoothDiscoveryDoneEvent(consoleNames));
					}
					
					waitingScreenVisible(false);
				}
			}
		}

	};

	private void connectToDevice(String deviceName) throws Exception {
		
		for(BluetoothDevice d : this.consolesFound){
			if(d.getName().equals(deviceName)){
				consoleDevice = d;
				if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] consoleDevice=" + consoleDevice.getName());
				break;
			}
		}
		
		if(consoleDevice == null){
			throw new Exception("No device discovered with this name");
		}
	
		setStatus(BluetoothStatus.CONNECTING);
		printToScreen("Trying to connect to device");
		// Get the input and output streams, using temp objects because
		// member streams are final
		int retryLeft = 5;
		while (retryLeft > 0) {
			try {
				BluetoothSocket tmp = null;

				// Get a BluetoothSocket to connect with the given
				// BluetoothDevice
				try {
					// MY_UUID is the app's UUID string, also used by the
					// server code
					tmp = consoleDevice
							.createInsecureRfcommSocketToServiceRecord(UUID
									.fromString("00001101-0000-1000-8000-00805f9c34ff"));

				} catch (IOException e) {
					if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] connectToDevice---");
					e.printStackTrace();
					break;
				}
				mmSocket = tmp;
				
				mmSocket.connect();
				break;
			} catch (IOException e) {
				e.printStackTrace();
				
				//Like it hardcore like johnny blaze
				closeSocketStream();
				
				printToScreen("Connection failed, " + retryLeft + " retry left");
				retryLeft--;
			}
		}
		
		if (mmSocket.isConnected()) {
			setConnected(true);
			try {
				mmInStream = mmSocket.getInputStream();
				mmOutStream = mmSocket.getOutputStream();
				
			} catch (IOException e) {
				printToScreen("Cannot get input/output streams");
			}
			
			setStatus(BluetoothStatus.CONNECTED);
			if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] BluetoothStatus=" + getStatus());
			
			destroyAllThreads();
			
			startListeningThread();
			
			if(activity != null)
			{
				new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			        public void run() {
			        	new PlayGameAsyncTask(activity).execute();
			        }});
			}
			
			connectedLock.release();
		}
		else if(!BluetoothAdapter.getDefaultAdapter().isEnabled())
		{
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			activity.startActivityForResult(enableBtIntent, -1);
			setStatus(BluetoothStatus.BLUETOOTH_NOT_ENABLED);
		}
		else if(activity != null)
		{
			new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
				public void run() {
					String title = activity.getResources().getString(R.string.bluetooth_connection_title);
					String message = activity.getResources().getString(R.string.bluetooth_connection_error);
					
					AlertBuilder.showPopUp(activity, title, message);
				}});
		}
		
		if(activity != null)
			waitingScreenVisible(false);

	}
	
	public void startConnectDeviceThread(final String deviceName)
	{
		if(Defines.PRINT_DEBUG_INFO) printToScreen("[DEBUG] startConnectDeviceThread");
		Thread connectThread = (new Thread() {
			public void run() {
					try {
						connectToDevice(deviceName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		connectThread.start();
	}
	
	public void run() {
		//Read first message that should be the welcome message
		readData();
		
		// Keep listening to the InputStream until an exception occurs
		while (!Thread.currentThread().isInterrupted()) {
			if(Defines.PRINT_DEBUG_THREAD) printToScreen("[DEBUG] run() run run run");
			// Read from the InputStream
			String message = readData();
			// Send the obtained bytes to the UI activity

			if (message == null) {
				if (bluetoothListener != null) {
					// TODO Check if there could be problem with different
					// thread handling UI or something...
					bluetoothListener.handleClosedConnection();
				}
				return;
			}
			ConsoleBaseEvent event = new ConsoleBaseEvent(this);
			try {
				if(!message.isEmpty())
				{
					event.parse(message);
					if (event.Type == ConsoleBaseEvent.EVENT_PRESSED) {
						event = new ConsoleEventPressed(this);
						((ConsoleEventPressed) event).parse(message);
						
					} else if (event.Type == ConsoleBaseEvent.EVENT_SCORE) {
						event = new ConsoleEventScoreUpdate(this);
						((ConsoleEventScoreUpdate) event).parse(message);
					}else if (event.Type == ConsoleBaseEvent.EVENT_SERIALIZED_MESSAGE) {
						event = new ConsoleEventSerializedMessage(this);
						((ConsoleEventSerializedMessage) event).parse(message);
					}else if (event.Type == ConsoleBaseEvent.EVENT_SETTINGS) {
						event = new ConsoleEventSettings(this);
						((ConsoleEventSettings) event).parse(message);
					}
					fireEvent(event);
				} 
			} catch (Exception e) {
				System.out.println("Cannot parse event received: " + message);
			}
			
			synchronized(this)
			{
				try {
					wait(50);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			
		}
	}

	/*
	 * Important: This must be called after the first init of the singleton.
	 * This is bad like jf at badminton.
	 */
	public void setup(BluetoothActivity activity)
	{
		this.activity = activity;
	}

	private void startListeningThread() {
		if(Defines.PRINT_DEBUG_THREAD) printToScreen("[DEBUG] startListeningThread()");
		
		Runnable listeningRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					printToScreen("Aquireing lock");
					connectedLock.acquire();
					printToScreen("Lock aquired");
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
				
				AndroidBluetoothClientSocket.this.run();
				
				if(!isClosed)
					closeDown();
				setConnected(false);
			}
			
		};
		
		listeningThread = new Thread(listeningRunnable);
		listeningThread.start();
	}
	
	public void stopListeningThread() {
		if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] stopListeningThread()");
		listeningThread.interrupt();
	}

	public void stopWrittingThread() {
		if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] stopWrittingThread()");
		writingThread.interrupt();
	}

	public void init() throws Exception {
		try{
			if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] socket init()");
			consolesFound.clear();
			setStatus(BluetoothStatus.INITIALIZING);
			printToScreen("Initializing socket");
			connectedLock.acquire();
			
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (mBluetoothAdapter == null) {
				setStatus(BluetoothStatus.INIT_FAILED_BLUETOOTH_NOT_AVAILABLE);
				throw new Exception("This device doesnt have bluetooth");
			}
			printToScreen("Bluetooth is present on this device");
			
			// If not enabled, pop up a window askign to turn it on!
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				activity.startActivityForResult(enableBtIntent, -1);
			}
			
			if(!mBluetoothAdapter.isEnabled()){
				setStatus(BluetoothStatus.BLUETOOTH_NOT_ENABLED);
				throw new Exception("Bluetooth is not enabled on this device");
			}
	
			// Register the BroadcastReceiver
			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
			
			activity.registerReceiver(mReceiver, filter); // Don't forget to
			// unregister during
			// onDestroy
	
			printToScreen("Starting discovery");
			setStatus(BluetoothStatus.DISCOVERING);
			mBluetoothAdapter.startDiscovery();
		} finally {
		}
	}

	public synchronized void addEventListener(ConsoleEventClassListener listener) {
		eventListeners.add(listener);
	}
	
	public synchronized void addDiscoveryListeners(BluetoothDiscoveryDoneListener listener) {
		discoveryListeners.add(listener);
	}

	public void setBluetoothConnectionClosedListener(
			BluetoothConnectionClosedListener listener) {
		bluetoothListener = listener;
	}

	public void sendMessage(String msg) {
		try {
			mmOutStream.write(msg.length());
			mmOutStream.write(msg.getBytes());
			mmOutStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void closeDown() {
		try {
			if (mmSocket != null && !isClosed)
			{
				if(mmInStream != null)
					mmInStream.close();
				
				if(mmOutStream != null)
					mmOutStream.close();
				
				mmSocket.close();
			}
		} catch (IOException e) {
			// We don't care if it was already closed
		}
		isClosed = true;
		this.connectedLock.release();
		consoleDevice = null;
		setStatus(BluetoothStatus.NOT_CONNECTED);
	}

	public String readData() {
		byte[] buffer = new byte[1024]; // buffer store for the stream
		int bytes; // bytes returned from read()

		try {
			bytes = mmInStream.read(buffer);
		} catch (IOException e) {
			return null;
		} catch (NullPointerException e){
			return null;
		}
		// Send the obtained bytes to the UI activity
		return (new String(buffer, 0, bytes)).trim();
	}

	private synchronized void fireEvent(ConsoleBaseEvent event) {
		Iterator<ConsoleEventClassListener> i = eventListeners.iterator();
		while (i.hasNext()) {
			((ConsoleEventClassListener) i.next()).handleConsoleEvent(event);
		}
	}
	
	public synchronized boolean isConnected(){
		return isConnected;
	}
	
	private synchronized void setConnected(boolean state)
	{
		isConnected = state;
	}

	public synchronized BluetoothStatus getStatus() {
		return status;
	}
	
	public synchronized void setStatus(BluetoothStatus status)
	{
		this.status = status; 
	}
	
	public synchronized String getMultiMediaEvent() {
		return multiMediaEvent;
	}
	
	public synchronized void setMultiMediaEvent(String event)
	{
		this.multiMediaEvent = event; 
	}
	
	private void printToScreen(String str){
		if(activity instanceof BluetoothActivity){
			((BluetoothActivity)activity).writeToLog(str);
		}
	}

	public BroadcastReceiver getmReceiver() {
		return mReceiver;
	}

	@Override
	public void initBluetoothConnection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerActionListener(ConsoleEventListener event) {
		// TODO Auto-generated method stub
		this.addEventListener(event);
	}

	@Override
	public void registerConnectionClosedListener(
			BluetoothConnectionClosedListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerDiscoveryCompleted(
			BluetoothDiscoveryDoneListener listener) {
		// TODO Auto-generated method stub
		if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] registerDiscoveryCompleted()");
		
	}

	@Override
	public void startCaptureThread() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isConnectionToBluetoothEnabled() {

		return isConnected();
	}

	@Override
	public void closeBluetoothConnection() {
		// TODO Auto-generated method stub
		
	}
	
	private void waitingScreenVisible(final Boolean state)
	{
		new Handler(activity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				if(state)
					activity.findViewById(R.id.loading).setVisibility(View.VISIBLE);
				else
					activity.findViewById(R.id.loading).setVisibility(View.INVISIBLE);
			}});
	}
	
	private void closeSocketStream() throws IOException
	{
		if(mmInStream != null)
			mmInStream.close();
		
		if(mmOutStream != null)
			mmOutStream.close();
		
		if(mmSocket != null)
			mmSocket.close();
	}
	
	public void destroyAllThreads()
	{
		if(Defines.PRINT_DEBUG_THREAD) printToScreen("[DEBUG] destroyAllThreads()");
		if(listeningThread != null)
		{
			// Stop listening thread
			stopListeningThread();
			listeningThread = null;
		}
		
		closeDown();
	}
}
