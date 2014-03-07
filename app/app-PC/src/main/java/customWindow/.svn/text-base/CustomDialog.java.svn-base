package customWindow;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import applicationTools.BackgroundPanel;
import applicationTools.ButtonManager;
import applicationTools.ColorManager;
import applicationTools.CustomButton;
import applicationTools.IconManager;

public class CustomDialog {
	protected JDialog dialogWindow;
	protected BackgroundPanel panelWindow;
	protected JPanel panelContent;
	protected CustomButton btnQuit;
	private Dimension popupDimension = new Dimension(550,600);
	
	public CustomDialog(){
		initFrameWindow();
	}
	
	private void initFrameWindow() {
		dialogWindow = new JDialog();
		dialogWindow.setSize(popupDimension);
		
		dialogWindow.setModalityType(ModalityType.APPLICATION_MODAL);
		
		ImageIcon logoImageIcon = new ImageIcon("img/OpenQuizLogoWithoutName_large.png");
		dialogWindow.setIconImage(logoImageIcon.getImage().getScaledInstance(48, -1,java.awt.Image.SCALE_SMOOTH));

		panelWindow = new BackgroundPanel(null, BackgroundPanel.SCALED, 0f, 0f);
		panelWindow.setBackground(ColorManager.getDialogPanelColor());
		panelWindow.setBorder(new LineBorder(ColorManager.getMousePressedColor()));
		panelWindow.setLayout(new MigLayout("", "[0px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][0px]", "[0px][grow][grow][grow][grow][grow][grow][grow][grow][grow][0px]"));
		
		dialogWindow.getContentPane().add(panelWindow, BorderLayout.CENTER);
		
		btnQuit = new CustomButton("", IconManager.getCloseXIcon(), ButtonManager.getIconButtonDimension());
		btnQuit.setToolTipText("Fermer (Alt+F4)");
		btnQuit.addActionListener(actionListenerQuit);
		panelWindow.add(btnQuit, "cell 14 0,alignx right,aligny top");
				
		panelContent = new JPanel();
		panelWindow.add(panelContent, "cell 0 1 15 10,grow");
		
		dialogWindow.setUndecorated(true);
		dialogWindow.setResizable(true);		
	}
	
	//Quit application
	private ActionListener actionListenerQuit = (new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			dialogWindow.dispose();
		}
	});

	public CustomButton getBtnQuit() {
		return btnQuit;
	}
	
	public JDialog getDialogWindow() {
		return dialogWindow;
	}

	public BackgroundPanel getPanelWindow() {
		return panelWindow;
	}

	public JPanel getPanelContent() {
		return panelContent;
	}
	
	public void disableQuitBtn(){
		btnQuit.setVisible(false);
		btnQuit.setEnabled(false);
	}
	
	public void setDimension(int width, int height){
		popupDimension = new Dimension(width,height);
		dialogWindow.setSize(popupDimension);
	}
}
