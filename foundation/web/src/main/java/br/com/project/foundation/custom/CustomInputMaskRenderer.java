package br.com.project.foundation.custom;

import java.io.IOException;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.inputmask.InputMaskRenderer;
import org.primefaces.context.RequestContext;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.HTML;

import br.com.project.commons.util.StringUtil;

/**
 * @author anderson.nascimento
 *
 */
public class CustomInputMaskRenderer extends InputMaskRenderer {

	@Override
	@SuppressWarnings("unchecked")
    protected void encodeMarkup(FacesContext context, InputMask inputMask) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = inputMask.getClientId(context);
        String styleClass = inputMask.getStyleClass();
        String defaultClass = "form-control " + InputMask.STYLE_CLASS;
        defaultClass = !inputMask.isValid() ? defaultClass + " ui-state-error" : defaultClass;
        defaultClass = inputMask.isDisabled() ? defaultClass + " ui-state-disabled" : defaultClass;
        styleClass = styleClass == null ? defaultClass : defaultClass + " " + styleClass;
    
        String icon = (String) inputMask.getAttributes().get("icon");
        
        if(StringUtil.isNotEmpty(icon)) {
        	writer.startElement("div", inputMask);
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
		writer.writeAttribute("type", "text", null);
		
		String valueToRender = ComponentUtils.getValueToRender(context, inputMask);
		
		if(valueToRender != null) {
			writer.writeAttribute("value", valueToRender , null);
		}
		
        renderPassThruAttributes(context, inputMask, HTML.INPUT_TEXT_ATTRS_WITHOUT_EVENTS);
        renderDomEvents(context, inputMask, HTML.INPUT_TEXT_EVENTS);

        if(inputMask.isDisabled()) writer.writeAttribute("disabled", "disabled", "disabled");
        if(inputMask.isReadonly()) writer.writeAttribute("readonly", "readonly", "readonly");
        if(inputMask.getStyle() != null) writer.writeAttribute("style", inputMask.getStyle(), "style");
		
        writer.writeAttribute("class", styleClass, "styleClass");
        
        if(RequestContext.getCurrentInstance().getApplicationContext().getConfig().isClientSideValidationEnabled()) {
            renderValidationMetadata(context, inputMask);
        }

        writer.endElement("input");        

        if(StringUtil.isNotEmpty(icon)) {
        	writer.endElement("div");
        }
        
        HashMap<String, Boolean> closeDiv = ((HashMap<String, Boolean>) (inputMask.getAttributes().get("closeDiv") != null ? inputMask.getAttributes().get("closeDiv") : null));

        if(closeDiv != null && closeDiv.get(inputMask.getId())) {
        	writer.endElement("div");   
        }
	}
}