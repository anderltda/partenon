package br.com.project.foundation.util;

import java.io.Serializable;
import java.util.Map;

import javax.el.ValueExpression;

import org.primefaces.component.datatable.DataTable;

public class DataTableState implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> filters;
	private Object sortField;
	private String sortOrder;
	private int first;

	public DataTableState(DataTable dataTable) {
		saveState(dataTable);
	}
	
	public void saveState(DataTable dataTable) {
		filters = dataTable.getFilters();
		sortField = dataTable.getValueExpression("sortBy");
		sortOrder = dataTable.getSortOrder();
		first = dataTable.getFirst();
	}
	
	public void restoreState(DataTable dataTable) {
		dataTable.setFilters(filters);
		dataTable.setValueExpression("sortBy", (ValueExpression) sortField);
		dataTable.setSortOrder(sortOrder);
		dataTable.setFirst(first);
	}
	
	public void resetState() {
		filters = null;
		sortField = null;
		sortOrder = null;
		first = 0;
	}
}
