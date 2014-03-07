package applicationTools;

import Console.bluetooth.event.BluetoothConnectionClosedListener;
import Console.bluetooth.event.BluetoothDiscoveryDoneListener;
import Console.bluetooth.event.ConsoleEventListener;

public interface IBluetoothManager {
	
	void initBluetoothConnection();
	
	public void registerActionListener(ConsoleEventListener event);
	
	public void registerConnectionClosedListener(BluetoothConnectionClosedListener listener);
	
	public void registerDiscoveryCompleted(BluetoothDiscoveryDoneListener listener);

	public void startCaptureThread();
	
	public Boolean isConnectionToBluetoothEnabled();
	
	public void closeBluetoothConnection();
	
	public void sendMessage(String msg);
}
