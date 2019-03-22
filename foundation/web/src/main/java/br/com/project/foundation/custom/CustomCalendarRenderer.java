package br.com.project.foundation.custom;

import java.io.IOException;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.calendar.CalendarRenderer;
import org.primefaces.context.RequestContext;
import org.primefaces.util.HTML;

/**
 * @author anderson.nascimento
 *
 */
public class CustomCalendarRenderer extends CalendarRenderer {

	@Override
	@SuppressWarnings("unchecked")
    protected void encodeInput(FacesContext context, Calendar calendar, String id, String value, boolean popup) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String type = popup ? "text" : "hidden";
        boolean disabled = calendar.isDisabled();
        
        boolean select = (Boolean) (calendar.getAttributes().get("select") != null ? calendar.getAttributes().get("select") : false);
        
        if(select) {
        	writer.startElement("div", calendar);
        	writer.writeAttribute("class", "input-group input-group-sm", null);   
        }

        writer.startElement("input", null);
        writer.writeAttribute("id", id, null);
        writer.writeAttribute("name", id, null);
        writer.writeAttribute("type", type, null);

        if(!isValueBlank(value)) {
            writer.writeAttribute("value", value, null);
        }

        if(popup) {
            String inputStyleClass = Calendar.INPUT_STYLE_CLASS;
            if(disabled) inputStyleClass = inputStyleClass + " ui-state-disabled";
            if(!calendar.isValid()) inputStyleClass = inputStyleClass + " ui-state-error";
            
            writer.writeAttribute("class", inputStyleClass + " form-control", null);
              
            if(calendar.isReadonly()||calendar.isReadonlyInput()) writer.writeAttribute("readonly", "readonly", null);
            if(calendar.isDisabled()) writer.writeAttribute("disabled", "disabled", null);
        
            renderPassThruAttributes(context, calendar, HTML.INPUT_TEXT_ATTRS_WITHOUT_EVENTS);
            renderDomEvents(context, calendar, HTML.INPUT_TEXT_EVENTS);
        }
        
        if(RequestContext.getCurrentInstance().getApplicationContext().getConfig().isClientSideValidationEnabled()) {
            renderValidationMetadata(context, calendar);
        }
        
        writer.endElement("input");
 
		HashMap<String, Boolean> closeDiv = ((HashMap<String, Boolean>) (calendar.getAttributes().get("closeDiv") != null ? calendar.getAttributes().get("closeDiv") : null));

		if(closeDiv != null && closeDiv.get(calendar.getId())) {
			writer.endElement("div");   
		}
	}


}