package br.com.project.seguranca.view;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class LocaleView {
	private String locale;

	public String changeLocale(String newLocale) {
		
		locale = newLocale;
		
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(newLocale));

		return null;
	}

	public String getLocale() {
		return locale != null ? locale : "pt";
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

}
