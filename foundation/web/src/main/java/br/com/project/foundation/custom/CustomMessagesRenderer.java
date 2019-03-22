package br.com.project.foundation.custom;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.messages.Messages;
import org.primefaces.component.messages.MessagesRenderer;
import org.primefaces.context.RequestContext;
import org.primefaces.expression.SearchExpressionFacade;

/**
 * @author anderson.nascimento
 *
 */
public class CustomMessagesRenderer extends MessagesRenderer {

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException{
		Messages uiMessages = (Messages) component;
		ResponseWriter writer = context.getResponseWriter();
		String clientId = uiMessages.getClientId(context);	
		Map<String, List<FacesMessage>> messagesMap = new HashMap<String, List<FacesMessage>>();
		boolean globalOnly = uiMessages.isGlobalOnly();
		String containerClass = null; //uiMessages.isShowIcon() ? Messages.CONTAINER_CLASS: Messages.ICONLESS_CONTAINER_CLASS;
		String style = uiMessages.getStyle();
		String styleClass = uiMessages.getStyleClass();
		styleClass = (styleClass == null) ? containerClass: containerClass + " " + styleClass;

		String _for = uiMessages.getFor();
		Iterator<FacesMessage> messages;
		if(_for != null) {
			// key case
			messages = context.getMessages(_for);

			// clientId / SearchExpression case
            UIComponent forComponent = SearchExpressionFacade.resolveComponent(context, uiMessages, _for, SearchExpressionFacade.Options.IGNORE_NO_RESULT);
            //UIComponent forComponent = SearchExpressionFacade.resolveComponent(context, uiMessages, _for, SearchExpressionFacade.IGNORE_NO_RESULT);
			if (forComponent != null) {
				messages = context.getMessages(forComponent.getClientId(context));
			}
		}
		else {
			messages = uiMessages.isGlobalOnly() ? context.getMessages(null) : context.getMessages();
		}

		while(messages.hasNext()) {
			FacesMessage message = messages.next();
			FacesMessage.Severity severity = message.getSeverity();

			if(severity.equals(FacesMessage.SEVERITY_INFO)) 
				addMessage(uiMessages, message, messagesMap, "info");
			else if(severity.equals(FacesMessage.SEVERITY_WARN))
				addMessage(uiMessages, message, messagesMap, "warn");
			else if(severity.equals(FacesMessage.SEVERITY_ERROR)) 
				addMessage(uiMessages, message, messagesMap, "error");
			else if(severity.equals(FacesMessage.SEVERITY_FATAL)) 
				addMessage(uiMessages, message, messagesMap, "fatal");	
		}

		writer.startElement("div", uiMessages);
		writer.writeAttribute("id", clientId, "id");
		writer.writeAttribute("class", styleClass, null);
		if(style != null) {
			writer.writeAttribute("style", style, null);
		}
		writer.writeAttribute("aria-live", "polite", null);

		if(RequestContext.getCurrentInstance().getApplicationContext().getConfig().isClientSideValidationEnabled()) {
			writer.writeAttribute("data-global", String.valueOf(globalOnly), null);
			writer.writeAttribute("data-summary", uiMessages.isShowSummary(), null);
			writer.writeAttribute("data-detail", uiMessages.isShowDetail(), null);
			writer.writeAttribute("data-severity", getClientSideSeverity(uiMessages.getSeverity()), null);
			writer.writeAttribute("data-redisplay", String.valueOf(uiMessages.isRedisplay()), null);
		}

		for(String severity : messagesMap.keySet()) {
			List<FacesMessage> severityMessages = messagesMap.get(severity);

			if(severityMessages.size() > 0) {
				encodeSeverityMessages(context, uiMessages, severity, severityMessages);
			}
		}

		writer.endElement("div");
	}


	@Override
	protected void encodeSeverityMessages(FacesContext context, Messages uiMessages, String severity, List<FacesMessage> messages) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String styleClassPrefix = Messages.SEVERITY_PREFIX_CLASS + severity;
		boolean escape = uiMessages.isEscape();

		writer.startElement("div", null);
		writer.writeAttribute("class", "box-body-message", null);

		message(writer, severity);

		if(uiMessages.isClosable()) {
			encodeCloseIcon(context, uiMessages);
		}

		if(uiMessages.isShowIcon()) {
			writer.startElement("h4", null);
			writer.startElement("i", null);
			writer.writeAttribute("class", "fa fa-" + transformeIcon(severity), null);
			writer.endElement("i");
			writer.write("      ");
			writer.write("Alert! ");
			writer.endElement("h4");
		}

		
		for(FacesMessage msg : messages) {

			String summary = msg.getSummary() != null ? msg.getSummary() : "";
			String detail = msg.getDetail() != null ? msg.getDetail() : summary;

			if(uiMessages.isShowSummary()) {

				if(escape)
					writer.writeText(summary, null);
				else
					writer.write(summary);

			}

			if(uiMessages.isShowDetail()) {
				writer.startElement("span", null);
				writer.writeAttribute("class", styleClassPrefix + "-detail", null);

				if(escape)
					writer.writeText(detail, null);
				else
					writer.write(detail);

				writer.endElement("span");
			}

			writer.write("<br/>");

			msg.rendered();
		}

		writer.endElement("div");
		writer.endElement("div");
	}

	protected void encodeCloseIcon(FacesContext context, Messages uiMessages) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		writer.startElement("button", null);
		writer.writeAttribute("class", "close", null);
		writer.writeAttribute("data-dismiss", "alert", null);
		writer.writeAttribute("aria-hidden", "true", null);
		writer.writeAttribute("type", "button", null);
		//writer.writeAttribute("onclick", "$(this).parent().slideUp();return false;", null);
		writer.write("x");
		writer.endElement("button");

	}

	private void message(ResponseWriter writer, String severity) throws IOException {
		writer.startElement("div", null);
		writer.writeAttribute("class", "alert alert-" + transformeMessage(severity) + " alert-dismissable", null);
	}

	private String transformeIcon(String severity) {

		if(severity.equals("warn")) {
			severity = "warning";
		} else if(severity.equals("error")) {
			severity = "ban";
		} else if(severity.equals("info")) {
			severity = "check";
		}

		return severity;
	}
	
	private String transformeMessage(String severity) {

		if(severity.equals("info")) {
			severity = "success";
		}

		return severity;
	}
}