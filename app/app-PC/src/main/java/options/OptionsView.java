package options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import thread.InfiniteProgressPanel;

import net.miginfocom.swing.MigLayout;
import Console.bluetooth.event.BluetoothDiscoveryDoneEvent;
import Console.bluetooth.event.BluetoothDiscoveryDoneListener;
import Console.bluetooth.event.ConsoleBaseEvent;
import Console.bluetooth.event.ConsoleEventListener;
import Console.bluetooth.event.ConsoleEventPressed;
import Console.bluetooth.event.ConsoleEventSerializedMessage;
import Console.bluetooth.event.ConsoleEventSettings;
import applicationTools.BackgroundManager;
import applicationTools.BluecoveBluetoothManager;
import applicationTools.EventGameChanges;
import applicationTools.FontManager;
import applicationTools.OptionsManager;
import applicationTools.ScoreManager;
import applicationTools.WindowManager;
import customWindow.CustomDialog;
import javax.swing.JSpinner;

public class OptionsView extends CustomDialog implements IOptionsView{
//public class OptionsView extends JPanel{
	
	private JCheckBox chckBoxTrainingMode;
	private JComboBox<String> cmbBoxBluetoothDevice;
	private JButton btnConnectToBluetoothDevice;
	private JButton btnRefreshBluetoothDevice;
	private JLabel lblBluetoothTitle;
	private ButtonGroup rdbtnGroup;
	private ButtonGroup rdbtnGroupResetMode;
	private ButtonGroup rdbtnGroupGameWindow;
	private ButtonGroup rdbtnGroupProjWindow;
	private JButton btnSaveChanges;
	private JLabel lblResetMode;
	private JRadioButton rdbtnResetAutomatic;
	private JRadioButton rdbtnResetManual;
	private JLabel lblResetTime;
	private JSpinner spinnerResetTime;
	private JButton btnSaveConsoleChanges;
	private JPanel backgroundPanel;
	private JLabel lblPlayerPanel;
	private JRadioButton rdbtnGameWindowNormal;
	private JRadioButton rdbtnGameWindowInverse;
	private JRadioButton rdbtnProjWindowNormal;
	private JRadioButton rdbtnProjWindowInverse;
	private JLabel lblGameWindow;
	private JLabel lblProjWindow;
	private ArrayList<JRadioButton> listRadioButtonBackgrounds;
	
	public OptionsView() {		
		rdbtnResetAutomatic = new JRadioButton("Automatique");
		rdbtnResetManual = new JRadioButton("Manuel");
		spinnerResetTime = new JSpinner();
		lblBluetoothTitle = new JLabel("Bluetooth");
		btnRefreshBluetoothDevice = new JButton("Refresh");
		lblResetMode = new JLabel("Mode remise à zéro");
		lblResetTime = new JLabel("Délai remise à zéro (sec)");
		chckBoxTrainingMode = new JCheckBox("Mode entraînement");
		btnConnectToBluetoothDevice = new JButton("Connexion");
		btnSaveChanges = new JButton("Sauvegarder Options");
		backgroundPanel = new JPanel();
		rdbtnProjWindowNormal = new JRadioButton("Normal");
		rdbtnGameWindowNormal = new JRadioButton("Normal");
		rdbtnGameWindowInverse = new JRadioButton("Inversé");
		rdbtnProjWindowInverse = new JRadioButton("Inversé");
		super.setDimension(550, 750);
		initGUI();
	}
	
	public void initGUI() {		
		//JPanel panelContent = this;
		panelContent.setLayout(new MigLayout("", "[grow]", "[]10px[][grow][][][][][][5px:n:5px][][][][][][][]"));
		backgroundPanel.setLayout(new MigLayout("","[]","[]"));
		rdbtnGroup = new ButtonGroup();
		rdbtnGroupResetMode = new ButtonGroup();
		rdbtnGroupGameWindow = new ButtonGroup();
		rdbtnGroupProjWindow = new ButtonGroup();
				
		rdbtnGroupResetMode.add(rdbtnResetAutomatic);
		rdbtnGroupResetMode.add(rdbtnResetManual);
		rdbtnGroupGameWindow.add(rdbtnGameWindowNormal);
		rdbtnGroupGameWindow.add(rdbtnGameWindowInverse);
		rdbtnGroupProjWindow.add(rdbtnProjWindowNormal);
		rdbtnGroupProjWindow.add(rdbtnProjWindowInverse);

		JLabel lblTitle = new JLabel("Panneau d'options");
		lblTitle.setFont(FontManager.getPopUpWindowTitle());
		panelContent.add(lblTitle, "cell 0 0,alignx center,aligny top");
		
		JLabel lblBackgroundSelection = new JLabel("Veuillez choisir le fond d'écran");
		lblBackgroundSelection.setFont(FontManager.getPopUpWindowSubTitle());
		panelContent.add(lblBackgroundSelection, "cell 0 1,alignx center");
		
		Dimension dimBackground = new Dimension(90,70);
		
		cmbBoxBluetoothDevice = new JComboBox<String>();
		
		ArrayList<ImageIcon> listBackgrounds = BackgroundManager.getInstance().getBackgroundList();
		listRadioButtonBackgrounds = new ArrayList<JRadioButton>();
		
		for(int i = 0 ; i < listBackgrounds.size() ; i++){
			JRadioButton rbtn = new JRadioButton();
			rdbtnGroup.add(rbtn);
			listRadioButtonBackgrounds.add(rbtn);
			backgroundPanel.add(rbtn, "cell " + (Integer)i/(listBackgrounds.size()/2) + " " + (i % (listBackgrounds.size()/2) + 2) + ", aligny center");

			JLabel lblBackground = new JLabel(new ImageIcon(listBackgrounds.get(i).getImage().getScaledInstance((int)dimBackground.getWidth(), (int)dimBackground.getHeight(),java.awt.Image.SCALE_SMOOTH)));
			lblBackground.setText("");
			backgroundPanel.add(lblBackground, "cell " + (Integer)i/(listBackgrounds.size()/2) + " " + (i % (listBackgrounds.size()/2) + 2) + ",alignx left, aligny center");
		}		
		
		backgroundPanel.setOpaque(false);
		backgroundPanel.setSize(new Dimension(300,300));
		panelContent.add(backgroundPanel, "cell 0 2,grow");
				
		panelContent.add(chckBoxTrainingMode, "cell 0 3");
		
		lblPlayerPanel = new JLabel("Disposition des panneaux de joueurs");
		panelContent.add(lblPlayerPanel, "cell 0 4,alignx center");
		
		lblGameWindow = new JLabel("Fenêtre de jeu");
		panelContent.add(lblGameWindow, "flowx,cell 0 5");
		panelContent.add(rdbtnGameWindowNormal, "cell 0 5");
		
		lblProjWindow = new JLabel("Fenêtre de projecteur");
		panelContent.add(lblProjWindow, "flowx,cell 0 6");
		panelContent.add(rdbtnProjWindowNormal, "cell 0 6");
		
		panelContent.add(btnSaveChanges, "cell 0 7,alignx center");
		btnSaveChanges.addActionListener(actionSaveChanges);
		
		panelContent.add(lblBluetoothTitle, "cell 0 9,alignx center");
		panelContent.add(cmbBoxBluetoothDevice, "cell 0 10");
		
		panelContent.add(btnRefreshBluetoothDevice, "flowx,cell 0 11");
		
		btnRefreshBluetoothDevice.addActionListener(actionRefreshBluetooth);
		
		panelContent.add(lblResetMode, "flowx,cell 0 12");
		
		panelContent.add(lblResetTime, "flowx,cell 0 13");
		panelContent.add(btnConnectToBluetoothDevice, "cell 0 11");
		
		panelContent.add(rdbtnResetAutomatic, "cell 0 12");
		panelContent.add(rdbtnResetManual, "cell 0 12");
		
		spinnerResetTime.setToolTipText("");
		panelContent.add(spinnerResetTime, "cell 0 13");
		
		btnSaveConsoleChanges = new JButton("Sauvegarder Options Console");
		panelContent.add(btnSaveConsoleChanges, "cell 0 14,alignx center");
		
		panelContent.add(rdbtnGameWindowInverse, "cell 0 5");
		panelContent.add(rdbtnProjWindowInverse, "cell 0 6");
		
		btnConnectToBluetoothDevice.addActionListener(actionConnectBluetoth);
		
		chckBoxTrainingMode.addActionListener(actionSetTrainingMode);
		
		rdbtnResetAutomatic.addActionListener(actionListenerResetAutomatic);
		rdbtnResetManual.addActionListener(actionListenerResetManual);
		btnSaveConsoleChanges.addActionListener(actionListenerSaveConsoleSettings);
		
		showConsoleSettingsBasedOnConsoleState();
	}
	
	private ActionListener actionListenerSaveConsoleSettings = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			ConsoleEventSettings event = new ConsoleEventSettings("");
			if (rdbtnResetAutomatic.isSelected()){
				event.setDelay(((Integer)spinnerResetTime.getValue())*1000);
			}
			else if (rdbtnResetManual.isSelected()){
				event.setDelay(0);
			}
			BluecoveBluetoothManager.getInstance().sendMessage(event.toString());
		}
	};
	
	private ActionListener actionListenerResetAutomatic = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			spinnerResetTime.setEnabled(true);
		}
	};
	
	private ActionListener actionListenerResetManual = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			spinnerResetTime.setEnabled(false);
		}
	};
	
	public void showWindow() {
		setChckBoxTrainingModeStatus();
		
		for(String consoleName : BluecoveBluetoothManager.getInstance().getAvailableConsoleList()){
			cmbBoxBluetoothDevice.addItem(consoleName);
		}

		showConsoleSettingsBasedOnConsoleState();
		
		getDialogWindow().setLocationRelativeTo(null);
		getDialogWindow().revalidate();
		getDialogWindow().setVisible(true);
	}
	
	public void setChckBoxTrainingModeStatus(){
		if(ScoreManager.getInstance().isTrainingModeEnabled()){
			chckBoxTrainingMode.setSelected(true);
		}
		else{
			chckBoxTrainingMode.setSelected(false);
		}
	}
	
	private ActionListener actionSetTrainingMode = new ActionListener() {
		public void actionPerformed(ActionEvent e) {		
			if(chckBoxTrainingMode.isSelected()){
				ScoreManager.getInstance().setTrainingMode(true);
			}
			else{
				ScoreManager.getInstance().setTrainingMode(false);
			}
		}
	};
	
	private ActionListener actionSaveChanges = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			for(int i = 0 ; i < listRadioButtonBackgrounds.size() ; i++){
				if(listRadioButtonBackgrounds.get(i).isSelected()){
					BackgroundManager.getInstance().setBackground(BackgroundManager.getInstance().getBackgroundList().get(i));
				}
			}
			if (rdbtnGameWindowNormal.isSelected()){
				OptionsManager.getInstance().setGameWindowPlayersInNormalPosition(true);
			}
			else{
				OptionsManager.getInstance().setGameWindowPlayersInNormalPosition(false);
			}
			if (rdbtnProjWindowNormal.isSelected()){
				OptionsManager.getInstance().setProjWindowPlayersInNormalPosition(true);
			}
			else{
				OptionsManager.getInstance().setProjWindowPlayersInNormalPosition(false);
			}
			WindowManager.getInstance().getGameView().swapPlayers();
			WindowManager.getInstance().getProjView().swapPlayers();
		}
	};
	
	private ActionListener actionRefreshBluetooth = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
        	//Init bluetooth
        	try{
        		if(BluecoveBluetoothManager.getInstance().isConnectionToBluetoothEnabled()){
        			BluecoveBluetoothManager.getInstance().registerDiscoveryCompleted(actionDiscoveryDone);
        			BluecoveBluetoothManager.getInstance().initBluetoothConnection();
        		}
        	}catch(Exception exc){exc.printStackTrace();}
		}
	};
	
	private ActionListener actionConnectBluetoth = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(BluecoveBluetoothManager.getInstance().isConnectionToBluetoothEnabled()){
				
					SwingUtilities.invokeLater(new Runnable() {
		                public void run() {
				            Thread action = new Thread(new Runnable() {
				                public void run() {
				                	String deviceName = getSelectedDeviceName();
				                	if (deviceName != null && deviceName != ""){
				                		getDialogWindow().setVisible(false);
					                	InfiniteProgressPanel.getInstance().start();
										BluecoveBluetoothManager.getInstance().connect(deviceName);
										if (WindowManager.getInstance().getMainWindow() != null){
											WindowManager.getInstance().getMainWindow().setConsoleInfoTextColor(new Color(0,0,0));
											WindowManager.getInstance().getMainWindow().setConsoleInfoText("Console: " + deviceName);
										}
										BluecoveBluetoothManager.getInstance().registerActionListener(consoleEventListenerCaptureConsoleInput);
										BluecoveBluetoothManager.getInstance().startCaptureThread();
										showConsoleSettingsBasedOnConsoleState();
					                	InfiniteProgressPanel.getInstance().stop();
					                	getDialogWindow().setVisible(true);
				                	}
				                }
				            }, "Action");
				            action.start();
		                }
		            });
				}
			}
		};
	
	private BluetoothDiscoveryDoneListener actionDiscoveryDone = new BluetoothDiscoveryDoneListener() {
		public void handleDiscoveryDone(BluetoothDiscoveryDoneEvent e) {
			for (String deviceName : e.getConsolesFound()){
				if (deviceName.contains("openquiz")){
					if (!isDeviceInComboBox(deviceName)){
						cmbBoxBluetoothDevice.addItem(deviceName);
					}
				}
			}
		}
	};
	
	private ConsoleEventListener consoleEventListenerCaptureConsoleInput = new ConsoleEventListener(){
		@Override
		public void handleConsoleEvent(ConsoleBaseEvent e) {
			if(e.Type == ConsoleBaseEvent.EVENT_SETTINGS){
				ConsoleEventSettings event = (ConsoleEventSettings) e;
				rdbtnResetAutomatic.setEnabled(true);
				rdbtnResetManual.setEnabled(true);
				if (event.getDelay() == 0){
					spinnerResetTime.setValue(0);
					spinnerResetTime.setEnabled(false);
					rdbtnResetManual.setSelected(true);
					rdbtnResetAutomatic.setSelected(false);
				}
				else{
					spinnerResetTime.setValue(event.getDelay()/1000);
					spinnerResetTime.setEnabled(true);
					rdbtnResetManual.setSelected(false);
					rdbtnResetAutomatic.setSelected(true);
				}
			}
			panelContent.repaint();
		}
	};

	
	private boolean isDeviceInComboBox(String deviceName){
		for (int i = 0; i < cmbBoxBluetoothDevice.getItemCount(); i++){
			if (cmbBoxBluetoothDevice.getItemAt(i).equals(deviceName)){
				return true;
			}
		}
		return false;
	}
	
	public String getSelectedDeviceName(){
		return (String) cmbBoxBluetoothDevice.getSelectedItem();
	}
	
	private void showConsoleSettingsBasedOnConsoleState(){
		if (BluecoveBluetoothManager.getInstance().isConnectionToBluetoothEnabled() && 
			BluecoveBluetoothManager.getInstance().isConnectionEstablished()){
			
			ConsoleEventSettings event = new ConsoleEventSettings("");
			event.setDelay(-1);
			BluecoveBluetoothManager.getInstance().sendMessage(event.toString());
		}
		else{
			rdbtnResetAutomatic.setEnabled(false);
			rdbtnResetManual.setEnabled(false);
			spinnerResetTime.setEnabled(false);
		}
	}
}