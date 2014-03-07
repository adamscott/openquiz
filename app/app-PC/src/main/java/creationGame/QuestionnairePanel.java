package creationGame;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ColorManager;
import applicationTools.TableHeaderRenderer;

public class QuestionnairePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPaneQuestionnaireList;
	private JTable tableQuestionnaireList;
	private DefaultTableModel dm;
	
	public QuestionnairePanel(){
		//Disable cell editing
		dm = new DefaultTableModel() {

			private static final long serialVersionUID = -4565425264857772188L;

			@Override
			   public boolean isCellEditable(int row, int column) {
			       return false;
			   }
			};
	    dm.setDataVector(new Object[][] { { "Nom du questionnaire", "Date de création" } }, 
	    				 new Object[] { "Nom du questionnaire", "Date de création" });
	    
		scrollPaneQuestionnaireList = new JScrollPane();
		tableQuestionnaireList = new JTable(dm);
		
		initGui();
	}
	
	private void initGui(){
		tableQuestionnaireList.setTableHeader(null);
		tableQuestionnaireList.setRowHeight(30);
		tableQuestionnaireList.setOpaque(false);
		tableQuestionnaireList.setSelectionBackground(ColorManager.getMousePressedColor());
		tableQuestionnaireList.setGridColor(ColorManager.getTextPaneColor());
		tableQuestionnaireList.setShowGrid(true);
		tableQuestionnaireList.setShowVerticalLines(false);
		tableQuestionnaireList.setBackground(ColorManager.getTextPaneColor());
		tableQuestionnaireList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tableQuestionnaireList.getColumnModel().getColumn(0).setCellRenderer(new TableHeaderRenderer());
		tableQuestionnaireList.getColumnModel().getColumn(1).setCellRenderer(new TableHeaderRenderer());

		scrollPaneQuestionnaireList.setOpaque(false);
		scrollPaneQuestionnaireList.getViewport().setOpaque(false);
		scrollPaneQuestionnaireList.setViewportBorder(null);
		scrollPaneQuestionnaireList.setViewportView(tableQuestionnaireList);
		
		this.setOpaque(false);
		
		this.setLayout(new MigLayout("", "[grow]", "[23px,grow]"));
		add(scrollPaneQuestionnaireList, "cell 0 0,grow");
	}
	
	public JTable getTableQuestionnaireList() {
		return tableQuestionnaireList;
	}

	public void setTableQuestionnaireList(JTable tableQuestionnaireList) {
		this.tableQuestionnaireList = tableQuestionnaireList;
	}
	
	public int getTableSelectedRow(){
		if (this.tableQuestionnaireList.getSelectedRow() != -1){
			return this.tableQuestionnaireList.getSelectedRow()-1;
		}
		else {
			return -1;
		}
	}
	
	public void addQuestionSet(String questionSetName, Date availableDate){
		Locale localeFrCa = new Locale("fr", "ca");
		DateFormat availableDateFormatFrCa =  DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, localeFrCa);
		Object[] questionSet = new Object[]{ questionSetName, availableDateFormatFrCa.format(availableDate), 0 };
		dm.addRow(questionSet);
		tableQuestionnaireList.setModel(dm);
	}
	
	public void clearQuestionSetList(){
		while (dm.getRowCount() > 1){
			dm.removeRow(1);
		}
		tableQuestionnaireList.setModel(dm);
	}
}
