package org.vaadin.vaadinvisualizations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;

import com.vaadin.ui.AbstractComponent;

import org.vaadin.vaadinvisualizations.widgetset.client.ui.VVisualizationWidget;

public class VisualizationComponent extends AbstractComponent {

	private static final long serialVersionUID = 8773635639061447457L;

	private static final String OPT_NAME_PREFIX = VVisualizationWidget.OPT_NAME_PREFIX;

	private ArrayList<SelectionListener> listeners;

	private List<String[]> table = new ArrayList<String[]>();

	private List<String> colTypes = new ArrayList<String>();

	private List<String> colNames = new ArrayList<String>();

	private List<String> selectedItems = new ArrayList<String>();

	private Map<String, Object> options = new HashMap<String, Object>();

	/**
	 * Set a String option.
	 * 
	 * @param name
	 * @param value
	 */
	public void setOption(String name, String value) {
		this.options.put(name, value);
	}

	/**
	 * Set a Boolean option.
	 * 
	 * @param name
	 * @param value
	 */
	public void setOption(String name, boolean value) {
		this.options.put(name, value);
	}

	/**
	 * Set a Integer option.
	 * 
	 * @param name
	 * @param value
	 */
	public void setOption(String name, int value) {
		this.options.put(name, value);
	}

	/**
	 * Set a Double option.
	 * 
	 * @param name
	 * @param value
	 */
	public void setOption(String name, double value) {
		this.options.put(name, value);
	}
	

	/**
	 * Add a column to data.
	 * 
	 * TODO: Document the available column types.
	 * 
	 * @param type
	 * @param name
	 */
	public void addColumn(String type, String name) {
		this.colTypes.add(type);
		this.colNames.add(name);
	}

	/**
	 * Add a row to data. Number of columns must match the columns added.
	 * 
	 * @param row
	 */
	public void addRow(String[] row) {
		table.add(row);
		requestRepaint();
	}

	/**
	 * Removes row(s) from data matching the name in the given column.
	 * 
	 * @param name
	 * @param columnIndex
	 */
	public void removeMatching(String name, int columnIndex) {

		if (name == null || columnIndex < 0) {
			return;
		}

		List<String[]> toBeRemoved = new ArrayList<String[]>();
		for (String[] row : this.table) {
			if (columnIndex < row.length && name.equals(row[columnIndex])) {
				toBeRemoved.add(row);
			}
		}
		if (!toBeRemoved.isEmpty()) {
			table.removeAll(toBeRemoved);
			requestRepaint();
		}

	}

	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);

		if (variables.containsKey("selectedItems")) {
			this.selectedItems.clear();
			this.selectedItems.addAll(Arrays.asList((String[]) variables
					.get("selectedItems")));

			if (this.listeners != null) {
				ArrayList<SelectionListener> tempList = new ArrayList<SelectionListener>(
						this.listeners);
				for (SelectionListener l : tempList) {
					l.selectionChanged(selectedItems);
				}
			}
		}
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);

		// Paint the column information
		target.addAttribute("colNames", (String[]) colNames
				.toArray(new String[] {}));
		target.addAttribute("colTypes", (String[]) colTypes
				.toArray(new String[] {}));

		// Paint options
		paintOptions(options, target);

		// Paint all data from the string table
		paintTableData(table, target);

		// Variable for selected items
		target.addVariable(this, "selectedItems", (String[]) this.selectedItems
				.toArray(new String[] {}));
		target.addVariable(this, "collapse", 2);
	}

	protected void paintOptions(Map<String, Object> ops, PaintTarget target)
			throws PaintException {
		
		String[] opNames = ops.keySet().toArray(new String[]{});
		String[] opTypes = new String[opNames.length];
		int i = 0;
		
		for (String opName :opNames) {
			
			Object opValue = ops.get(opName);
			
			
			if (opValue instanceof Boolean) {
				opTypes[i++] = "boolean";
				target
						.addAttribute(OPT_NAME_PREFIX + opName,
								(Boolean) opValue);
			} else if (opValue instanceof Integer) {
				opTypes[i++] = "int";
				target
						.addAttribute(OPT_NAME_PREFIX + opName,
								(Integer) opValue);
			} else if (opValue instanceof Double) {
				opTypes[i++] = "double";
				target.addAttribute(OPT_NAME_PREFIX + opName, (Double) opValue);
			}  else if (opValue instanceof String[]){
				opTypes[i++] = "string[]";
				target.addAttribute(OPT_NAME_PREFIX + opName, (String[]) opValue);
				
			}else if (opValue instanceof String) {
				opTypes[i++] = "string";
				target.addAttribute(OPT_NAME_PREFIX + opName, (String) opValue);
			}
		}

		target.addAttribute("optionNames", opNames );
		target.addAttribute("optionTypes", opTypes);

	}

	/** Paint a table of strings to a paint target */
	private void paintTableData(List<String[]> aTable, PaintTarget pt)
			throws PaintException {

		// Paint size
		pt.addAttribute("rowCount", aTable.size());

		// Paint items one by one
		int i = 0;
		for (String[] str : aTable) {
			pt.addAttribute("row-" + (i++), str);
		}
	}

	/** Listener interface for chart click events. */
	public interface SelectionListener {

		public void selectionChanged(List<String> selectedItems);

	}

	public void addListener(SelectionListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<SelectionListener>();
		}
		listeners.add(listener);

	}
	
	public void removeAllData(){
		table.clear();
		while(colTypes.size() > 1){
			colTypes.remove(colTypes.get(colTypes.size()-1));
			colNames.remove(colNames.get(colNames.size()-1));
		}
	}

}
