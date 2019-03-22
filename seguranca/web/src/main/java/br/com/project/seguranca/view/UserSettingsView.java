package br.com.project.seguranca.view;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.project.commons.annotation.PreEdit;
import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.foundation.exception.ServiceException;
import br.com.project.seguranca.ProvidedSegurancaView;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class UserSettingsView extends ProvidedSegurancaView<Long, Usuario> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getEditPage() {
		return "/seguranca/formUserSettings.xhtml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return null;
	}
	
	@Override
	public String getSearchPage() {
		return "/home.xhml?faces-redirect=true";
	}
	
	@Override
	protected String getSavePage() {
		return getEditPage();
	}

	@Override
	public String getViewPage() {
		return null;
	}
	
	@PreEdit
	public void preEdit() {
		bean = getLoggedUser();		
	}
	
	@Override
	protected void executeSave() throws ServiceException {
		getService().salvarUsuario(bean);
	}

}

