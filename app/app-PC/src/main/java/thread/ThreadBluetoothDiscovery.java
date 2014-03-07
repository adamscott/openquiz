package thread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import Console.bluetooth.event.BluetoothDiscoveryDoneEvent;
import Console.bluetooth.event.BluetoothDiscoveryDoneListener;
import applicationTools.BluecoveBluetoothManager;

public class ThreadBluetoothDiscovery implements Runnable{
	private AtomicInteger maxLoop = new AtomicInteger(5);
	private AtomicInteger loop = new AtomicInteger(0);
	private AtomicBoolean isClose = new AtomicBoolean(false);
	private AtomicBoolean isDiscoverySuccessfull = new AtomicBoolean(false);
	
	public void run() {
		BluecoveBluetoothManager.getInstance().registerDiscoveryCompleted(actionDiscoveryDone);
		while(!getClose()){
        	try{
        		if(!isDiscoverySuccessfull.get() && BluecoveBluetoothManager.getInstance().isConnectionToBluetoothEnabled()){
        			BluecoveBluetoothManager.getInstance().initBluetoothConnection();
        		}
        	}catch(Exception exc){exc.printStackTrace();}
        	
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private synchronized boolean getClose(){
		return (isClose.get() || loop.incrementAndGet() > maxLoop.get() || isDiscoverySuccessfull.get());
	}
	
	public synchronized void setClose(boolean isClose){
		this.isClose.set(isClose);
	}
	
	private BluetoothDiscoveryDoneListener actionDiscoveryDone = new BluetoothDiscoveryDoneListener() {
		public void handleDiscoveryDone(BluetoothDiscoveryDoneEvent e) {
			for (String deviceName : e.getConsolesFound()){
				if (deviceName.contains("openquiz")){
					BluecoveBluetoothManager.getInstance().addConsoleToAvailableConsoleList(deviceName);
					isDiscoverySuccessfull.set(true);
				}
			}
		}
	};

}
