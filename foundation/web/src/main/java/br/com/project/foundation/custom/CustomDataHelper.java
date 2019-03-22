package br.com.project.foundation.custom;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import br.com.project.commons.util.StringUtil;

public class CustomDataHelper {

	public static Map<String, Object> decodeFilters(FacesContext context, DataTable table) {
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		Map<String, Object> filters = table.getFilters();

		String id = table.getClientId();
		for (String key : params.keySet()) {
			if (key.matches("^" + id + "\\:\\w+$") && key.indexOf("_operator") < 0 && key.indexOf("_filter") < 0 && key.indexOf("_between") < 0) {
        			String value = null;
        			String operator = params.get(key +  "_operator");
        			if((StringUtil.isNotEmpty(operator))) {
        				value = "null";
        			} else if (StringUtil.isNotEmpty(operator) && operator.equals("BETWEEN")){
        				if (StringUtil.isNotEmpty(params.get(key + "_between"))){
                				value = params.get(key) + "," + params.get(key + "_between");
        				} else {
        					operator = "=";
                				value = params.get(key);
        				}
        			} else {
        				value = params.get(key);
        			}
        			
        			if (value.matches("[0-9,\\.]+")) {
        				value = value.replaceAll(",", ".");
        			}
        			
				String prop = key.substring(key.lastIndexOf(':') + 1);
        			if (StringUtil.isNotEmpty(value)) {
        				if (operator != null) {
        					value += "___" + operator;
        				}
        				filters.put(prop, value);
        			} else {
        				filters.remove(prop);
        			}
			}
		}
		table.setFilters(filters);
		return filters;
		
	}
}
