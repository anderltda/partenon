package br.com.project.foundation.custom;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.datatable.DataTableRenderer;
import org.primefaces.component.subtable.SubTable;
import org.primefaces.model.SortMeta;
import org.primefaces.util.HTML;

import br.com.project.foundation.util.DataTableState;

/**
 * @author anderson.nascimento
 *
 */
public class CustomDataTableRenderer extends DataTableRenderer {

	@Override
	public void decode(FacesContext context, UIComponent component) {
		DataTable dataTable = (DataTable) component;
		
		super.decode(context, dataTable);

		CustomDataHelper.decodeFilters(context, dataTable);
		
		saveState(context, dataTable);
	}

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		DataTable dataTable = (DataTable) component;

		restoreState(context, dataTable);

		super.encodeBegin(context, component);
	}

	protected void saveState(FacesContext context, DataTable dataTable) {
		//Object value = dataTable.getValue();
		//if (value instanceof CustomLazyDataModel<?>) {}
		getDataTableState(context, dataTable).saveState(dataTable);
	}

	protected void restoreState(FacesContext context, DataTable dataTable) {
		//Object value = dataTable.getValue();
		//if (value instanceof CustomLazyDataModel<?>) {}
		getDataTableState(context, dataTable).restoreState(dataTable);		
	}
	
	protected DataTableState getDataTableState(FacesContext context, DataTable dataTable) {
		Map<String, Object> session = context.getExternalContext().getSessionMap();
		
		String clientId = dataTable.getClientId(context);
		DataTableState dts = (DataTableState) session.get(clientId + "_STATE");
		if (dts == null) {
			dts = new DataTableState(dataTable);
			session.put(clientId + "_STATE", dts);
		}
		
		return dts;
	}
	
	@Override
    protected void encodeRegularTable(FacesContext context, DataTable table) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        
        writer.startElement("div", null);
        writer.writeAttribute("class", "box-body table-responsive", null);
        
        writer.startElement("table", null);
        writer.writeAttribute("class", "table table-bordered table-striped", null);
        
        writer.writeAttribute("role", "grid", null);
        if(table.getTableStyle() != null) writer.writeAttribute("style", table.getTableStyle(), null);
        if(table.getTableStyleClass() != null) writer.writeAttribute("class", table.getTableStyleClass(), null);
        if(table.getSummary() != null) writer.writeAttribute("summary", table.getSummary(), null);
        
        encodeThead(context, table);
        encodeTFoot(context, table);
        encodeTbody(context, table, false);
        
        writer.endElement("table");
        writer.endElement("div");
    }
	
	@Override
	public void encodeTbody(FacesContext context, DataTable table, boolean dataOnly, int columnStart, int columnEnd, String tbodyId) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String rowIndexVar = table.getRowIndexVar();
        String clientId = table.getClientId(context);
        String emptyMessage = table.getEmptyMessage();
        UIComponent emptyFacet = table.getFacet("emptyMessage");
        SubTable subTable = table.getSubTable();
        String tbodyClientId = (tbodyId == null) ? clientId + "_data" : tbodyId;
        
        if(table.isSelectionEnabled()) {
            table.findSelectedRowKeys();
        }
               
        int rows = table.getRows();
		int first = table.getFirst();
        int rowCount = table.getRowCount();
        int rowCountToRender = rows == 0 ? (table.isLiveScroll() ? (table.getScrollRows() + table.getScrollOffset()) : rowCount) : rows;
        int frozenRows = table.getFrozenRows();
        boolean hasData = rowCount > 0;
        
        if(first == 0 && frozenRows > 0) {
            first += frozenRows;
        }
              
        if(!dataOnly) {
            writer.startElement("tbody", null);
            writer.writeAttribute("id", tbodyClientId, null);
            //Retirado
            writer.writeAttribute("class", DataTable.DATA_CLASS, null);
        }

        if(hasData) {
            if(subTable != null)
                encodeSubTable(context, table, subTable, first, (first + rowCountToRender));
            else
                encodeRows(context, table, first, (first + rowCountToRender), columnStart, columnEnd);
        }
        else {
            //Empty message
            writer.startElement("tr", null);
            writer.writeAttribute("class", DataTable.EMPTY_MESSAGE_ROW_CLASS, null);

            writer.startElement("td", null);
            writer.writeAttribute("colspan", table.getColumnsCount(), null);
            
            if(emptyFacet != null)
                emptyFacet.encodeAll(context);
            else
                writer.write(emptyMessage);

            writer.endElement("td");
            
            writer.endElement("tr");
        }
		
        if(!dataOnly) {
            writer.endElement("tbody");
        }

		//Cleanup
		table.setRowIndex(-1);
		if(rowIndexVar != null) {
			context.getExternalContext().getRequestMap().remove(rowIndexVar);
		}
    }
	
	@Override
	public void encodeColumnHeader(FacesContext context, DataTable table, UIColumn column) throws IOException {
        if(!column.isRendered()) {
            return;
        }
        
        ResponseWriter writer = context.getResponseWriter();
        String clientId = column.getContainerClientId(context);

        ValueExpression columnSortByVE = column.getValueExpression("sortBy");
        boolean sortable = (columnSortByVE != null);
        boolean filterable = (column.getValueExpression("filterBy") != null);
        String selectionMode = column.getSelectionMode();
        String sortIcon = null;
        boolean resizable = table.isResizableColumns() && column.isResizable();
                
        String columnClass = sortable ? DataTable.COLUMN_HEADER_CLASS + " " + DataTable.SORTABLE_COLUMN_CLASS : DataTable.COLUMN_HEADER_CLASS;
        columnClass = filterable ? columnClass + " " + DataTable.FILTER_COLUMN_CLASS : columnClass;
        columnClass = selectionMode != null ? columnClass + " " + DataTable.SELECTION_COLUMN_CLASS : columnClass;
        columnClass = resizable ? columnClass + " " + DataTable.RESIZABLE_COLUMN_CLASS : columnClass;
        columnClass = !column.isToggleable() ? columnClass + " " + DataTable.STATIC_COLUMN_CLASS : columnClass;
        columnClass = column.getStyleClass() != null ? columnClass + " " + column.getStyleClass() : columnClass;
        
        if(sortable) {
            ValueExpression tableSortByVE = table.getValueExpression("sortBy");
            Object tableSortBy = table.getSortBy();
            boolean defaultSorted = (tableSortByVE != null || tableSortBy != null);
                    
            if(defaultSorted) {
                if(table.isMultiSort()) {
                    List<SortMeta> sortMeta = table.getMultiSortMeta();
                    
                    if(sortMeta != null) {
                        for(SortMeta meta : sortMeta) {
                            sortIcon = resolveDefaultSortIcon(column, meta);

                            if(sortIcon != null) {
                                break;
                            }
                        }
                    }
                }
                else {
                    sortIcon = resolveDefaultSortIcon(table, column, table.getSortOrder());
                }
            }
            
            if(sortIcon == null)
                sortIcon = DataTable.SORTABLE_COLUMN_ICON_CLASS;
            else
                columnClass += " ui-state-active";
        }
        
        String style = column.getStyle();
        String width = column.getWidth();
        if(width != null) {
            String unit = width.endsWith("%") ? "" : "px";
            if(style != null)
                style = style + ";width:" + width + unit;
            else
                style = "width:" + width + unit;
        }
        
        writer.startElement("th", null);
        writer.writeAttribute("id", clientId, null);
        //Retirado
        writer.writeAttribute("class", columnClass, null);
        writer.writeAttribute("role", "columnheader", null);
        
        if(style != null) writer.writeAttribute("style", style, null);
        if(column.getRowspan() != 1) writer.writeAttribute("rowspan", column.getRowspan(), null);
        if(column.getColspan() != 1) writer.writeAttribute("colspan", column.getColspan(), null);
        
        if(filterable) {
            table.enableFiltering();

            String filterPosition = column.getFilterPosition();

            if(filterPosition.equals("bottom")) {
                encodeColumnHeaderContent(context, column, sortIcon);
                encodeFilter(context, table, column);
            }
            else if(filterPosition.equals("top")) {
                encodeFilter(context, table, column);
                encodeColumnHeaderContent(context, column, sortIcon);
            } 
            else {
                throw new FacesException(filterPosition + " is an invalid option for filterPosition, valid values are 'bottom' or 'top'.");
            }
        }
        else {
            encodeColumnHeaderContent(context, column, sortIcon);
        }
        
        if(selectionMode != null && selectionMode.equalsIgnoreCase("multiple")) {
            encodeCheckbox(context, table, false, false, HTML.CHECKBOX_ALL_CLASS);
        }
        
        writer.endElement("th");
    }
}
