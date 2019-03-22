package br.com.project.foundation.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.primefaces.component.api.ClientBehaviorRenderingMode;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.commandlink.CommandLinkRenderer;
import org.primefaces.context.RequestContext;
import org.primefaces.util.CSVBuilder;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.Constants;
import org.primefaces.util.HTML;
import org.primefaces.util.SharedStringBuilder;

import br.com.project.commons.util.StringUtil;

/**
 * @author anderson.nascimento
 *
 */
public class CustomCommandLinkRenderer extends CommandLinkRenderer {

private static final String SB_BUILD_ONCLICK = CommandLinkRenderer.class.getName() + "#buildOnclick";
    
    @Override
	public void decode(FacesContext context, UIComponent component) {
        CommandLink link = (CommandLink) component;
        if(link.isDisabled()) {
            return;
        }
        
		String param = component.getClientId();

		if(context.getExternalContext().getRequestParameterMap().containsKey(param)) {
			component.queueEvent(new ActionEvent(component));
		}
        
        decodeBehaviors(context, component);
	}

    @Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		CommandLink link = (CommandLink) component;
		String clientId = link.getClientId(context);
        Object label = link.getValue();

		if(!link.isDisabled()) {
            String request;
            boolean ajax = link.isAjax();
            String styleClass = link.getStyleClass();
            styleClass = styleClass == null ? CommandLink.STYLE_CLASS : CommandLink.STYLE_CLASS + " " + styleClass;
            RequestContext requestContext = RequestContext.getCurrentInstance();
            boolean csvEnabled = requestContext.getApplicationContext().getConfig().isClientSideValidationEnabled()&&link.isValidateClient();
        
            StringBuilder onclick = SharedStringBuilder.get(context, SB_BUILD_ONCLICK);
            if(link.getOnclick() != null) {
                onclick.append(link.getOnclick()).append(";");
            }
            
            
            String onclickBehaviors = getEventBehaviors(context, link, "click", null);
            if(onclickBehaviors != null) {
                onclick.append(onclickBehaviors);
            }
            
            if(StringUtil.isNotEmpty(link.getDir()) && link.getDir().equals("anderson")) {
            	writer.startElement("span", null);
            	writer.writeAttribute("class", "input-group-addon", null);            	
            }
            
			writer.startElement("a", link);
			writer.writeAttribute("id", clientId, "id");
			writer.writeAttribute("href", "#", null);
			writer.writeAttribute("class", styleClass, null);
            if(link.getTitle() != null) {
                writer.writeAttribute("aria-label", link.getTitle(), null);
            }
            
            if(ajax) {
                request = buildAjaxRequest(context, link, null);
            }
            else {
                UIComponent form = ComponentUtils.findParentForm(context, link);
                if(form == null) {
                    throw new FacesException("Commandlink \"" + clientId + "\" must be inside a form component");
                }
                
                request = buildNonAjaxRequest(context, link, form, clientId, true);
            }
            
            if(csvEnabled) {
                CSVBuilder csvb = requestContext.getCSVBuilder();
                request = csvb.init().source("this").ajax(ajax).process(link, link.getProcess()).update(link, link.getUpdate()).command(request).build();
            }
            
            onclick.append(request);

            if(onclick.length() > 0) {
                if(link.requiresConfirmation()) {
                    writer.writeAttribute("data-pfconfirmcommand", onclick.toString(), null);
                    writer.writeAttribute("onclick", link.getConfirmationScript(), "onclick");
                }
                else
                    writer.writeAttribute("onclick", onclick.toString(), "onclick");
            }
            
            List<ClientBehaviorContext.Parameter> behaviorParams = new ArrayList<ClientBehaviorContext.Parameter>();
            behaviorParams.add(new ClientBehaviorContext.Parameter(Constants.CLIENT_BEHAVIOR_RENDERING_MODE, ClientBehaviorRenderingMode.UNOBSTRUSIVE));
            String dialogReturnBehavior = getEventBehaviors(context, link, "dialogReturn", behaviorParams);
            if(dialogReturnBehavior != null) {
                writer.writeAttribute("data-dialogreturn", dialogReturnBehavior, null);
            }
			
			renderPassThruAttributes(context, link, HTML.LINK_ATTRS, HTML.CLICK_EVENT);

			if(label != null)
				writer.writeText(label, "value");
			else
				renderChildren(context, link);
			
			 if(StringUtil.isNotEmpty(link.getDir()) && link.getDir().equals("anderson")) {
				 writer.startElement("i", null);
				 writer.writeAttribute("class", "fa fa-search", null);    		
				 writer.endElement("i");				 
			 }
    		
    		writer.endElement("a");
    		
    		 if(StringUtil.isNotEmpty(link.getDir()) && link.getDir().equals("anderson")) {
    			 writer.endElement("span");    			 
    		 }
		}
        else {
            String styleClass = link.getStyleClass();
            styleClass = styleClass == null ? CommandLink.DISABLED_STYLE_CLASS : CommandLink.DISABLED_STYLE_CLASS + " " + styleClass;

			writer.startElement("span", link);
			writer.writeAttribute("id", clientId, "id");
            writer.writeAttribute("class", styleClass, "styleclass");

            if(link.getStyle() != null)
                writer.writeAttribute("style", link.getStyle(), "style");
			
			if(label != null)
				writer.writeText(label, "value");
			else
				renderChildren(context, link);
			
			writer.endElement("span");
		}
	}

    @Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		//Do Nothing
	}

    @Override
	public boolean getRendersChildren() {
		return true;
	}
}