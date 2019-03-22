package br.com.project.foundation.custom;

import java.io.IOException;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.commandbutton.CommandButtonRenderer;
import org.primefaces.util.HTML;

import br.com.project.commons.util.BooleanUtil;
import br.com.project.commons.util.StringUtil;

/**
 * @author anderson.nascimento
 *
 */
public class CustomCommandButtonRenderer extends CommandButtonRenderer {
	
	@Override
	@SuppressWarnings("unchecked")
	protected void encodeMarkup(FacesContext context, CommandButton button) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = button.getClientId(context);
		String type = button.getType();
        boolean pushButton = (type.equals("reset")||type.equals("button"));
        Object value = button.getValue();
        String icon = button.resolveIcon();
        String request = pushButton ? null: buildRequest(context, button, clientId);        
        String onclick = buildDomEvent(context, button, "onclick", "click", "action", request);
        
        boolean dialog = (Boolean) (button.getAttributes().get("dialog") != null ? BooleanUtil.booleanValue(button.getAttributes().get("dialog")) : false);
        String idCloseModalDialog = (String)button.getAttributes().get("data-dismiss");
        String idOpenModalDialog = (String)button.getAttributes().get("modal");
        String iconButtonModal = (String)button.getAttributes().get("button-icon");

        // New         
        if(dialog) {
        	writer.startElement("span", button);
        	writer.writeAttribute("class", "input-group-btn", null);        	
        }
		// New 
        
		writer.startElement("button", button);
		writer.writeAttribute("id", clientId, "id");
		writer.writeAttribute("name", clientId, "name");
        writer.writeAttribute("class", button.resolveStyleClass(), "styleClass");
        
        if(idCloseModalDialog != null) {
        	writer.writeAttribute("data-dismiss", idCloseModalDialog, null);	
        }
        
        if(idOpenModalDialog != null) {
        	writer.writeAttribute("data-toggle", "modal", null);
        	writer.writeAttribute("data-target", button.getAttributes().get("modal"), null);
        }

		if(onclick != null) {
            if(button.requiresConfirmation()) {
                writer.writeAttribute("data-pfconfirmcommand", onclick, null);
                writer.writeAttribute("onclick", button.getConfirmationScript(), "onclick");
            }
            else
                writer.writeAttribute("onclick", onclick, "onclick");
		}
		
		renderPassThruAttributes(context, button, HTML.BUTTON_ATTRS, HTML.CLICK_EVENT);

        if(button.isDisabled()) writer.writeAttribute("disabled", "disabled", "disabled");
        if(button.isReadonly()) writer.writeAttribute("readonly", "readonly", "readonly");
		
        //icon
        if(icon != null && !icon.trim().equals("")) {
            String defaultIconClass = button.getIconPos().equals("left") ? HTML.BUTTON_LEFT_ICON_CLASS : HTML.BUTTON_RIGHT_ICON_CLASS; 
            String iconClass = defaultIconClass + " " + icon;
            
            writer.startElement("span", null);
            writer.writeAttribute("class", iconClass, null);
            writer.endElement("span");
        }
        
        //text
        writer.startElement("span", null);
        //writer.writeAttribute("class", HTML.BUTTON_TEXT_CLASS, null);
        
        if(value == null) {
            writer.write("ui-button");
        } else {
            if(button.isEscape())
              writer.writeText(" " + value, "value");
            else
                writer.write(value.toString());
        }
        
        writer.endElement("span");
	
        if(StringUtil.isNotEmpty(iconButtonModal)) {
        	writer.startElement("i", null);
        	writer.writeAttribute("class", iconButtonModal, null);
        	writer.endElement("i");
        }
    	
		writer.endElement("button");
		
		// New
        if(dialog) {
        	writer.endElement("span");
        	writer.endElement("div");        	
        }
        
        writer.endElement("input");
        
        HashMap<String, Boolean> closeDiv = ((HashMap<String, Boolean>) (button.getAttributes().get("closeDiv") != null ? button.getAttributes().get("closeDiv") : null));
        
        if(closeDiv != null && closeDiv.get(button.getId())) {
        	writer.endElement("div");   
        }
	}
}