package startup;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import mainWindow.MainWindow;
import thread.ThreadBluetoothDiscovery;
import applicationTools.BluecoveBluetoothManager;
import applicationTools.Defines;
import applicationTools.GetInstanceBluetoothManager;
import applicationTools.WindowManager;

public class Main
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		final ThreadBluetoothDiscovery bluetoothDiscovery = new ThreadBluetoothDiscovery();
		final Thread threadBluetoothDiscovery = new Thread(bluetoothDiscovery);
		
		//On application close
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	if(BluecoveBluetoothManager.getInstance().isConnectionToBluetoothEnabled()){
		    		BluecoveBluetoothManager.getInstance().closeBluetoothConnection();
		    	}
		    	
		    	if(threadBluetoothDiscovery.isAlive()){
		    		bluetoothDiscovery.setClose(true);
		    	}
		    }
		});
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
		        try 
		        {	
			    	System.out.println("Architecture: " + System.getProperty("sun.arch.data.model") + "bits");
		        	//Set device target.
		        	Defines.PC_BUILD = true;
		        	
		        	UIManager.put("nimbusBase", Color.lightGray);
		        	UIManager.put("background", Color.WHITE);
		        	UIManager.put("control", Color.WHITE);
		        	
		        	UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		        	
		        	//Set bluetooth instance.
		        	BluecoveBluetoothManager.getInstance();
		        	GetInstanceBluetoothManager.setInstance(BluecoveBluetoothManager.getInstance());
		        	if(BluecoveBluetoothManager.getInstance().isConnectionToBluetoothEnabled()){
		        		threadBluetoothDiscovery.start();
		        	}
		        	
		        	MainWindow mainWindow = new MainWindow();
					WindowManager.getInstance().setMainWindow(mainWindow);
					
					if (!BluecoveBluetoothManager.getInstance().isBluetoothStackFound()){
						mainWindow.setConsoleInfoText("Bluetooth Désactivé");
						mainWindow.setConsoleInfoTextColor(new Color(255,0,0));
					}
					else{
						mainWindow.setConsoleInfoText("Bluetooth Activé");
						mainWindow.setConsoleInfoTextColor(new Color(0,0,0));
					}
		        	
					//Comment for debug - set frame to full screen mode
			 		mainWindow.getFrameWindow().setExtendedState(JFrame.MAXIMIZED_BOTH);
			 		mainWindow.getFrameWindow().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} 
		        catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}	
}
