package br.com.project.foundation.custom;

import java.io.IOException;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtext.InputTextRenderer;
import org.primefaces.context.RequestContext;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.HTML;

import br.com.project.commons.util.StringUtil;

/**
 * @author anderson.nascimento
 *
 */
public class CustomInputTextRenderer extends InputTextRenderer {
	
	@Override
	@SuppressWarnings("unchecked")
	protected void encodeMarkup(FacesContext context, InputText inputText) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = inputText.getClientId(context);		
		
        boolean dialog = (Boolean) (inputText.getAttributes().get("dialog") != null ? inputText.getAttributes().get("dialog") : false);
       
        if(dialog) {
        	writer.startElement("div", inputText);
        	writer.writeAttribute("class", "input-group input-group-sm", null);   
        } 
        
        String icon = (String) inputText.getAttributes().get("icon");
        
        if(StringUtil.isNotEmpty(icon)) {
        	writer.startElement("div", inputText);
        	writer.writeAttribute("class", "input-group", null); 
        	writer.startElement("div", null);
        	writer.writeAttribute("class", "input-group-addon", null);
        	writer.startElement("i", null);
        	writer.writeAttribute("class", icon, null);
        	writer.endElement("i");
        	writer.endElement("div");
        }
                
		writer.startElement("input", null);
		writer.writeAttribute("id", clientId, null);
		writer.writeAttribute("name", clientId, null);
		writer.writeAttribute("type", inputText.getType(), null);

		String valueToRender = ComponentUtils.getValueToRender(context, inputText);
		if(valueToRender != null) {
			writer.writeAttribute("value", valueToRender , null);
		}

		renderPassThruAttributes(context, inputText, HTML.INPUT_TEXT_ATTRS_WITHOUT_EVENTS);
        renderDomEvents(context, inputText, HTML.INPUT_TEXT_EVENTS);

        if(inputText.isDisabled()) writer.writeAttribute("disabled", "disabled", null);
        if(inputText.isReadonly()) writer.writeAttribute("readonly", "readonly", null);
        if(inputText.getStyle() != null) writer.writeAttribute("style", inputText.getStyle(), null);

        writer.writeAttribute("class", createStyleClass(inputText), "styleClass");
        
        if(RequestContext.getCurrentInstance().getApplicationContext().getConfig().isClientSideValidationEnabled()) {
            renderValidationMetadata(context, inputText);
        }

        writer.endElement("input");
        
        if(StringUtil.isNotEmpty(icon)) {
        	writer.endElement("div");        	
        }
        
        HashMap<String, Boolean> closeDiv = ((HashMap<String, Boolean>) (inputText.getAttributes().get("closeDiv") != null ? inputText.getAttributes().get("closeDiv") : null));
        
        if(closeDiv != null && closeDiv.get(inputText.getId())) {
        	writer.endElement("div");   
        } 
	}
	
	@Override
    protected String createStyleClass(InputText inputText) {
        String defaultClass = "form-control " + InputText.STYLE_CLASS;
        defaultClass = inputText.isValid() ? defaultClass : defaultClass + " ui-state-error";
        defaultClass = !inputText.isDisabled() ? defaultClass : defaultClass + " ui-state-disabled";
        
        String styleClass = inputText.getStyleClass();
        styleClass = styleClass == null ? defaultClass : defaultClass + " " + styleClass;
        
        return styleClass;
    }
}