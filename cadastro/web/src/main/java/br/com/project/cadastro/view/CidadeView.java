package br.com.project.cadastro.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.ProvidedCadastroView;
import br.com.project.commons.annotation.PreSave;
import br.com.project.commons.cadastro.entity.Cidade;
import br.com.project.foundation.Constants;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class CidadeView extends ProvidedCadastroView<Long, Cidade> {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{filialView}")
	protected FilialView filialView;
	
	@Override
	public String getEditPage() {
		return "/cadastro/formCidade.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/cadastro/formCidade.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/cadastro/formCidade.xhml?faces-redirect=true";
	}
	
	@Override
	public String getSearchPage() {
		return "/cadastro/filterCidade.xhml?faces-redirect=true";
	}
	
	/**
	 * 
	 */
	@Override
	public void onSelectDialog() {
		setSelectDialog("filial", bean, filialView.getSelected());
		setSelectDialog("filial", beanFilter, filialView.getSelected());
	}

	public FilialView getFilialView() {
		return filialView;
	}

	public void setFilialView(FilialView filialView) {
		this.filialView = filialView;
	}
	
	@PreSave
	public void preSave() {
		bean.setAtendRedespachante(bean.getAutentica() ? Constants.SIM : Constants.NAO);
	}
	
	
	
}
