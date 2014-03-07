package creationGame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ColorManager;
import applicationTools.TableHeaderRenderer;

public class GameListPanel extends JPanel{
	private static final long serialVersionUID = -4856738836776242346L;
	private JPanel panelContent;
	private JTable tableAvailableGameList;
	private JScrollPane scrollPaneGameList;
	
	private DefaultTableModel dm;

	public GameListPanel() {
		//Disable cell editing
		dm = new DefaultTableModel() {

			private static final long serialVersionUID = -4565425264857772188L;

			@Override
			   public boolean isCellEditable(int row, int column) {
			       return false;
			   }
			};
	    dm.setDataVector(new Object[][] { { "Nom de la partie", "Date de création", "Tournoi", "Équipe 1", "Équipe 2" } }, 
	    				 new Object[] {     "Nom de la partie", "Date de création", "Tournoi", "Équipe 1", "Équipe 2" });
	    
	    scrollPaneGameList = new JScrollPane();
	    tableAvailableGameList = new JTable(dm);
		
		initGui();
	}
	
	private void initGui()
	{
		tableAvailableGameList.setTableHeader(null);
		tableAvailableGameList.setRowHeight(30);
		tableAvailableGameList.setOpaque(false);
		tableAvailableGameList.setSelectionBackground(ColorManager.getMousePressedColor());
		tableAvailableGameList.setGridColor(ColorManager.getTextPaneColor());
		tableAvailableGameList.setShowGrid(true);
		tableAvailableGameList.setShowVerticalLines(false);
		tableAvailableGameList.setBackground(ColorManager.getTextPaneColor());
		tableAvailableGameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tableAvailableGameList.getColumnModel().getColumn(0).setCellRenderer(new TableHeaderRenderer());
		tableAvailableGameList.getColumnModel().getColumn(1).setCellRenderer(new TableHeaderRenderer());
		tableAvailableGameList.getColumnModel().getColumn(2).setCellRenderer(new TableHeaderRenderer());
		tableAvailableGameList.getColumnModel().getColumn(3).setCellRenderer(new TableHeaderRenderer());
		tableAvailableGameList.getColumnModel().getColumn(4).setCellRenderer(new TableHeaderRenderer());

		scrollPaneGameList.setOpaque(false);
		scrollPaneGameList.getViewport().setOpaque(false);
		scrollPaneGameList.setViewportBorder(null);
		scrollPaneGameList.setViewportView(tableAvailableGameList);
		
		this.setOpaque(false);
		
		this.setLayout(new MigLayout("", "[grow]", "[23px,grow]"));
		add(scrollPaneGameList, "cell 0 0,grow");
	}
	
	public JPanel getPanelContent() {
		return panelContent;
	}
	
	public int getTableSelectedRow(){
		if (this.tableAvailableGameList.getSelectedRow() != -1){
			return this.tableAvailableGameList.getSelectedRow()-1;
		}
		else {
			return -1;
		}
	}

	public JTable getGameTable() {
		return this.tableAvailableGameList;
	}
	
	public void clearGameList(){
		while (dm.getRowCount() > 1){
			dm.removeRow(1);
		}
		tableAvailableGameList.setModel(dm);
	}
}
