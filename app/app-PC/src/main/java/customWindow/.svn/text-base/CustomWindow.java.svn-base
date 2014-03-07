package customWindow;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import applicationTools.BackgroundManager;
import applicationTools.BackgroundPanel;
import applicationTools.CustomButton;

public class CustomWindow {
	protected JFrame frameWindow;
	protected BackgroundPanel panelWindow;
	protected JPanel panelContent;
	protected CustomButton btnQuit;
	
	public CustomWindow(){
		initFrameWindow();
	}
	
	private void initFrameWindow()
	{
		frameWindow = new JFrame();
		frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameWindow.removeNotify();
		frameWindow.setUndecorated(true);
		frameWindow.setResizable(true);
		frameWindow.getContentPane().add(new JLabel("Appuyez sur Alt+F4 pour quitter le mode plein écran.", SwingConstants.CENTER), BorderLayout.CENTER);
		frameWindow.validate();
		frameWindow.setVisible(true);
		
		ImageIcon logoImageIcon = new ImageIcon("img/OpenQuizLogoWithoutName_large.png");
		frameWindow.setIconImage(logoImageIcon.getImage().getScaledInstance(48, -1,java.awt.Image.SCALE_SMOOTH));
		
		panelWindow = new BackgroundPanel(BackgroundManager.getInstance().getBackground().getImage(), BackgroundPanel.SCALED, 0f, 0f);
		panelWindow.setLayout(new MigLayout("", "[0px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][0px]", "[0px][grow][grow][grow][grow][grow][grow][grow][grow][grow][0px]"));
		
		frameWindow.setContentPane(panelWindow);
		frameWindow.getContentPane().revalidate();
				
		panelContent = new JPanel();
		panelContent.setOpaque(false);
		
		panelWindow.add(panelContent, "cell 0 2 15 10,grow");
	}

	public CustomButton getBtnQuit() {
		return btnQuit;
	}
	
	public JFrame getFrameWindow() {
		return frameWindow;
	}

	public BackgroundPanel getPanelWindow() {
		return panelWindow;
	}

	public JPanel getPanelContent() {
		return panelContent;
	}
}
