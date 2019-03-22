package br.com.project.foundation.custom;

import java.io.IOException;
import java.util.HashMap;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.api.InputHolder;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.outputlabel.OutputLabelRenderer;
import org.primefaces.expression.SearchExpressionFacade;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.HTML;

/**
 * @author anderson.nascimento
 * 
 */
public class CustomOutputLabelRenderer extends OutputLabelRenderer {


	@SuppressWarnings("unchecked")
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		OutputLabel label = (OutputLabel) component;
		String clientId = label.getClientId();
		String value = ComponentUtils.getValueToRender(context, label);
		UIComponent target = null;
		String targetClientId = null;
		UIInput input = null;
		String styleClass = label.getStyleClass();
		styleClass = styleClass == null ? OutputLabel.STYLE_CLASS : OutputLabel.STYLE_CLASS + " " + styleClass;

		String _for = label.getFor();

		if(_for != null) {
			target = SearchExpressionFacade.resolveComponent(context, label, _for);
			targetClientId = (target instanceof InputHolder) ? ((InputHolder) target).getInputClientId() : target.getClientId(context);

			if(target instanceof UIInput) {
				input = (UIInput) target;

				if(value != null && (input.getAttributes().get("label") == null || input.getValueExpression("label") == null)) {
					ValueExpression ve = label.getValueExpression("value");

					if(ve != null) {
						input.setValueExpression("label", ve);
					}
					else {
						String labelString = value;
						int colonPos = labelString.lastIndexOf(":");

						if(colonPos != -1) {
							labelString = labelString.substring(0, colonPos);
						}

						input.getAttributes().put("label", labelString);
					}
				}

				if(!input.isValid()) {
					styleClass = styleClass + " ui-state-error";
				}
			}
		}

        HashMap<String, Boolean> openDiv = ((HashMap<String, Boolean>) (component.getAttributes().get("openDiv") != null ? component.getAttributes().get("openDiv") : null));
        String colmd = ((String) (component.getAttributes().get("colmd") != null ? component.getAttributes().get("colmd") : "col-md-4"));
        
        // DIV         
        if(openDiv != null && openDiv.get(component.getId())) {
        	writer.startElement("div", component);
        	writer.writeAttribute("class", colmd, null);   
        }
		
		writer.startElement("label", label);
		writer.writeAttribute("id", clientId, "id");
		writer.writeAttribute("class", styleClass, "id");
		renderPassThruAttributes(context, label, HTML.LABEL_ATTRS);

		if(target != null) {
			writer.writeAttribute("for", targetClientId, "for");
		}

		if(value != null) {            
			if(label.isEscape())
				writer.writeText(value, "value");
			else
				writer.write(value);
		}

		renderChildren(context, label);

		if(input != null && input.isRequired() && label.isIndicateRequired()) {
			writer.startElement("span", label);
			writer.writeAttribute("class", OutputLabel.REQUIRED_FIELD_INDICATOR_CLASS, null);
			writer.write("*");
			writer.endElement("span");   
		}

		writer.endElement("label");        
	}


}