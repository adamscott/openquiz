package creationGame;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonEditor;
import applicationTools.ButtonManager;
import applicationTools.ButtonRenderer;
import applicationTools.CustomButton;
import applicationTools.IconManager;

public class GameListView {
	private JPanel panelContent;
	private JTable tableAvailableGameList;
	private CustomButton btnNewGame;
	private CustomButton btnPlayGame;
	private JScrollPane scrollPane;
	
	private DefaultTableModel dm;

	public GameListView() {
		panelContent = new JPanel();
		btnNewGame = new CustomButton("Nouvelle partie", null, ButtonManager.getTextButtonDimension() );
		
		//Table need to be in a scrollpane for header to display
		scrollPane = new JScrollPane();
		
		//TODO - Table data model - exemple only. Should be in Controller/Model?
	    dm = new DefaultTableModel();
	    dm.setDataVector(new Object[][] { { null, null, null, null, null, null, "button1_1", "button1_2" },
	    								  { null, null, null, null, null, null, "button2_1", "button2_2" } }, 
	    				 new Object[] { "Nom de partie", "Date cr�ation", "Auteur", "�quipe 1", 
	    								"�quipe 2", "En cours", "" , "" });

	    tableAvailableGameList = new JTable(dm);
		btnPlayGame = new CustomButton("Jouer", null, ButtonManager.getTextButtonDimension());
		
		initGUI();
	}
	
	private void initGUI()
	{
		
		panelContent.setLayout(new MigLayout("", "[grow][][][]", "[][grow][]"));
		
		panelContent.add(btnNewGame, "flowx,cell 0 0");
		
		//Table need to be in a scrollpane for header to display
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelContent.add(scrollPane, "cell 0 1 2 1,grow");
	    
	    //Add button and action listener to button column
		ButtonRenderer buttonPencil = new ButtonRenderer("", new Color(255,255,255,150), new Color(255,255,255,150), IconManager.getPencilIcon(), ButtonManager.getIconButtonDimension());
		ButtonRenderer buttonRemove = new ButtonRenderer("", new Color(255,255,255,150), new Color(255,255,255,150), IconManager.getCloseXIcon(), ButtonManager.getIconButtonDimension());
		
		tableAvailableGameList.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("", new Color(255,255,255,150), new Color(255,255,255,150), IconManager.getPencilIcon(), ButtonManager.getIconButtonDimension()));
		tableAvailableGameList.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(),IconManager.getPencilIcon(),buttonPencil));
		
		tableAvailableGameList.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("", new Color(255,255,255,150), new Color(255,255,255,150), IconManager.getCloseXIcon(), ButtonManager.getIconButtonDimension()));
		tableAvailableGameList.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(),IconManager.getCloseXIcon(),buttonRemove));
		
		//Resize button column
		tableAvailableGameList.getColumnModel().getColumn(6).setMinWidth(50);
		tableAvailableGameList.getColumnModel().getColumn(7).setMinWidth(50);
		tableAvailableGameList.getColumnModel().getColumn(6).setMaxWidth(50);
		tableAvailableGameList.getColumnModel().getColumn(7).setMaxWidth(50);
		
		tableAvailableGameList.setRowHeight(30);

		scrollPane.setViewportView(tableAvailableGameList);
	    
		panelContent.add(btnPlayGame, "cell 1 2");	
	}
	
	public JPanel getPanelContent() {
		return panelContent;
	}
}
