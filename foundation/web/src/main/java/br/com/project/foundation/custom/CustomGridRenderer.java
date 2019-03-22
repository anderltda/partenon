package br.com.project.foundation.custom;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.faces.renderkit.html_basic.GridRenderer;

/**
 * @author anderson.nascimento
 *
 */
public class CustomGridRenderer extends GridRenderer {

	@Override
	protected void renderRow(FacesContext context, UIComponent table, UIComponent child, ResponseWriter writer) throws IOException {

		TableMetaInfo info = getMetaInfo(context, table);
		writer.startElement("td", table);
		
		StringBuilder columnClass = new StringBuilder();
		if (info.getCurrentColumnClass() != null) {
			columnClass.append(info.getCurrentColumnClass());
		}
	
		if (child.getAttributes().get("styleClass") != null) {
			columnClass.append((String) child.getAttributes().get("styleClass"));
		}
		
		if (columnClass.length() > 0) {
			writer.writeAttribute("class", columnClass.toString(), "columns");
		}
		
		if (child.getAttributes().get("style") != null) {
			writer.writeAttribute("style", child.getAttributes().get("style"), "columns");
		}
		
		encodeRecursive(context, child);
		writer.endElement("td");
		writer.writeText("\n", table, null);

	}
}
