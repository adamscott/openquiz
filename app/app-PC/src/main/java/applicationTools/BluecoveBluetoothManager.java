package applicationTools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Console.bluetooth.event.BluetoothConnectionClosedListener;
import Console.bluetooth.event.BluetoothDiscoveryDoneListener;
//import Console.bluetooth.event.BluetoothDiscoveryDoneListener;
import Console.bluetooth.event.ConsoleEventListener;
import Console.bluetooth.socket.BluecoveBluetoothClientSocket;

public class BluecoveBluetoothManager implements IBluetoothManager{

	private static BluecoveBluetoothManager INSTANCE = null;
	private static BluecoveBluetoothClientSocket clientsock;
	private static Boolean connectToBluetooth = true;
	private static List<String> consoleList;
	private static Boolean isBluetoothStackFound = false;
	private static Boolean isConnectionEstablished = false;
	
	public static BluecoveBluetoothManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new BluecoveBluetoothManager();
		}
		return INSTANCE;
	}
	
	public BluecoveBluetoothManager(){
		consoleList = new ArrayList<String>();
		try{
			clientsock = new BluecoveBluetoothClientSocket();
			isBluetoothStackFound = true;
		}catch(Exception exp){
			System.out.println(exp.getMessage());
			connectToBluetooth = false;
		}
	}
	
	public void initBluetoothConnection(){
		clientsock.init();
	}
	
	public void connect(String deviceName){
		clientsock.connect(deviceName);
		isConnectionEstablished = true;
	}
	
	public void registerActionListener(ConsoleEventListener event){
		clientsock.addEventListener(event);
	}
	
	public void startCaptureThread(){
		clientsock.startListeningThread();
	}
	
	public Boolean isConnectionToBluetoothEnabled(){
		return connectToBluetooth;
	}
	
	public void closeBluetoothConnection(){
		clientsock.closeDown();
		clientsock.stopListeningThread();
		isConnectionEstablished = false;
	}

	public void registerConnectionClosedListener(
			BluetoothConnectionClosedListener listener) {
		// TODO Auto-generated method stub
		//throw new RuntimeException("UNIMPLEMENTED");
	}

	public void registerDiscoveryCompleted(BluetoothDiscoveryDoneListener listener) {
		clientsock.addDiscoveryDoneListener(listener);
	}

	/*public void registerDiscoveryCompleted(
			BluetoothDiscoveryDoneListener listener) {
		// TODO Auto-generated method stub
		throw new RuntimeException("UNIMPLEMENTED");
	}*/
	
	public void addConsoleToAvailableConsoleList(String consoleName){
		if (consoleList != null && !consoleList.contains(consoleName)){
			consoleList.add(consoleName);
		}
	}
	
	public List<String> getAvailableConsoleList(){
		return this.consoleList;
	}
	
	public boolean isBluetoothStackFound(){
		return this.isBluetoothStackFound;
	}
	
	public void sendMessage(String msg){
		clientsock.sendMessage(msg);
	}
	
	public boolean isConnectionEstablished(){
		return this.isConnectionEstablished;
	}

}
