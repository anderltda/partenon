package br.com.project.cadastro.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.MasterCadastroView;
import br.com.project.commons.cadastro.entity.Banco;

/**
 * @author anderson.nascimento
 * 
 */
@ManagedBean
@SessionScoped
public class BancoView extends MasterCadastroView<Long, Banco> {

	private static final long serialVersionUID = 1L;
		
	@Override
	public String getEditPage() {
		return "/cadastro/formBanco.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/cadastro/formBanco.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/cadastro/formBanco.xhml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/cadastro/filterBanco.xhml?faces-redirect=true";
	}

	@Override
	public String getSavePage() {
		return getEditPage();
	}
	
	/**
	 * 
	 */
	@Override
	public void updateChildren() {
		setChildrenMasterBean("agenciaView", "Agências", bean);
	}
}
