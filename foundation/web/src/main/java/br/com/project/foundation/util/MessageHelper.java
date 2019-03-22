package br.com.project.foundation.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageHelper {

	private static final String MESSAGES_APPLICATION_RESOURCES = "msgs";

	public static String getMessage(String messageId) {
		String message = null;
		try {
			message = getBundle().getString(messageId);
		} catch (Exception e) {
			message = messageId;
		}
		return message;
	}

	public static String getMessage(String messageId, String... params) {
		ResourceBundle resource = getBundle();
		String message = null;
		Object[] objs = null;
		if (params != null) {
			objs = new Object[params.length];
			for (int i = 0; i < params.length; i++) {
				try {
					objs[i] = resource.getString(params[i]);
				} catch (Exception e) {
					objs[i] = params[i];
				}
			}
		} else {
			objs = new Object[0];
		}

		try {
			message = formatMessage(resource.getString(messageId), objs);
		} catch (Exception e) {
			message = messageId;
		}

		return message;
	}

	public static String formatMessage(String mensagem, Object... params) {
		return MessageFormat.format(mensagem, params);
	}

	public static void addInfoMessage(String messageId) {
		addMessage(FacesMessage.SEVERITY_INFO, messageId);
	}
	
	public static void addInfoMessage(String messageId, String... params) {
		addMessage(FacesMessage.SEVERITY_INFO, messageId, params);
	}
	
	public static void addWarnMessage(String messageId) {
		addMessage(FacesMessage.SEVERITY_WARN, messageId);
	}
	
	public static void addWarnMessage(String messageId, String... params) {
		addMessage(FacesMessage.SEVERITY_WARN, messageId, params);
	}

	public static void addErrorMessage(String messageId) {
		addMessage(FacesMessage.SEVERITY_ERROR, messageId);
	}

	public static void addErrorMessage(String messageId, String... params) {
		addMessage(FacesMessage.SEVERITY_ERROR, messageId, params);
	}

	public static void addFatalMessage(String messageId, String... params) {
		addMessage(FacesMessage.SEVERITY_FATAL, messageId, params);
	}

	public static void addMessage(FacesMessage.Severity severity, String messageId) {
		addMessage(severity, messageId, new String[] {});
	}

	public static void addMessage(FacesMessage.Severity severity, String messageId, String... params) {
		addMessage(getFacesMessage(severity, messageId, params));
	}

	public static void addMessage(FacesMessage msg) {
		String clientId = msg.getSeverity().equals(FacesMessage.SEVERITY_FATAL) ? "faltalMensagens" : null;

		addMessage(msg, clientId);
	}

	public static void addMessage(FacesMessage msg, String clientId) {
		FacesContext.getCurrentInstance().addMessage(clientId, msg);
	}

	public static FacesMessage getInfoFacesMessage(String messageId, String... params) {
		return getFacesMessage(FacesMessage.SEVERITY_INFO, messageId, params);
	}

	public static FacesMessage getErrorFacesMessage(String messageId, String... params) {
		return getFacesMessage(FacesMessage.SEVERITY_ERROR, messageId, params);
	}

	public static FacesMessage getWarnFacesMessage(String messageId, String... params) {
		return getFacesMessage(FacesMessage.SEVERITY_WARN, messageId, params);
	}

	public static FacesMessage getFatalFacesMessage(String messageId, String... params) {
		return getFacesMessage(FacesMessage.SEVERITY_FATAL, messageId, params);
	}

	public static FacesMessage getFacesMessage(FacesMessage.Severity severity, String messageId, String... params) {
		return new FacesMessage(severity, getMessage(messageId, params), null);
	}

	private static ResourceBundle getBundle() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, MESSAGES_APPLICATION_RESOURCES);
	}

}
