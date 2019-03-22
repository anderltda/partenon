package br.com.project.foundation.view;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.Constants;
import br.com.project.foundation.util.MessageHelper;

/**
 * @author anderson.nascimento
 *
 */
public abstract class BaseView implements Serializable {

	private static final long serialVersionUID = 1L;

	public BaseView() {
	}

	public String getMessage(String messageId, String params) {
		return MessageHelper.getMessage(messageId, params.split("\\s*,\\s*"));
	}

	public String formatMessage(String message, String params) {
		Object[] objs = params.split("\\s*,\\s*");
		return MessageHelper.formatMessage(message, objs);
	}

	protected void handleError(String bundle, String... params) {
		if(RequestContext.getCurrentInstance() != null) {
			RequestContext.getCurrentInstance().addCallbackParam("hasError", true);			
		}				
		MessageHelper.addErrorMessage(bundle, params);
	}

	protected String getServletPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();
	}

	protected String getRealPath(String uri) {
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath(uri);
	}
	
	protected String getRequestURL() {		
		String path = getServletPath();
		String url = StringUtil.getString(((HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest()).getRequestURL()).replace(path, "");		
		return url;
	}
	
	protected String getIconMapsURL() {		
		String path = getServletPath();
		String url = StringUtil.getString(((HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest()).getRequestURL()).replace(path, "");	
		String icon = url.concat("/resources/images/icon-tracking.png");
		return icon;
	}
	
	protected String getModuleName() {
		String path = getServletPath();
		int index = path.lastIndexOf('/');
		if (index == 0) {
			return "/";
		}
		return path.substring(1, index);
	}

	protected String getContextPath() {
		return "/" + getModuleName() + "/";
	}

	protected String getCurrentPage() {
		String path = getServletPath();
		return path.substring(path.lastIndexOf('/') + 1);
	}

	protected Map<String, Object> getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	protected Map<String, Object> getRequestMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
	}

	protected Map<String, String> getParameterMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	}
		
	protected Usuario getLoggedUser() {
		return (Usuario) getSessionMap().get(Constants.LOGGED_USER);
	}
	
	protected void setLoggedUser(Usuario loggedUser) {
		getSessionMap().put(Constants.LOGGED_USER, loggedUser); 
	}

	protected void reset() {
		getSessionMap().remove(Constants.LOGGED_USER); 
	}
	
}
