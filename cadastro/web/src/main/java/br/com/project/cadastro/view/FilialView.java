package br.com.project.cadastro.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.ProvidedCadastroView;
import br.com.project.commons.annotation.PosEdit;
import br.com.project.commons.annotation.PosView;
import br.com.project.commons.annotation.PreSave;
import br.com.project.commons.cadastro.entity.Endereco;
import br.com.project.commons.cadastro.entity.Filial;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.Constants;
import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 * 
 */
@ManagedBean
@SessionScoped
// @Query(value = "cadastro.buscarFiliais")
public class FilialView extends ProvidedCadastroView<String, Filial> {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{enderecoView}")
	protected EnderecoView enderecoView;	
	
	@Override
	public String getEditPage() {
		return "/cadastro/formFilial.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/cadastro/formFilial.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/cadastro/formFilial.xhml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/cadastro/filterFilial.xhml?faces-redirect=true";
	}

	/**
	 * 
	 */
	public void preencherNomeFantasia() {
		bean.setNomeFantasia(bean.getRazaoSocial());
	}

	/**
	 * 
	 */
	@Override
	public void onSelectDialog() {
		try {
			getCompositeObjectHelper().createEnderecoDominio(bean.getEndereco(), getEnderecoView().getSelected());	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void handleBlurEventSearchCep() {
		try {

			Endereco endereco = getService().buscaEndereco(bean.getEndereco().getCep());
			getCompositeObjectHelper().createEnderecoDominio(bean.getEndereco(), endereco);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
		
	/**
	 * 
	 */
	public void handleEventMask() {
		maskInsEstadual = INSCRICOES_ESTADUAIS.get(bean.getEndereco().getUf());		
	}
	
	@PosView
	public void posView() {
		this.viewContentsFile(bean.getImagem(), "JPG", "logo.jpg");
	}
	
	@PosEdit
	public void posEdit() {
		bean.setAutentica(StringUtil.isNotEmpty(bean.getAutenticacao()) && StringUtil.isEqual(bean.getAutenticacao(), Constants.SIM) ? true : false);
		this.viewContentsFile(bean.getImagem(), "JPG", "logo.jpg");
	}
	
	/**
	 * 
	 */
	protected void viewContentsFile(byte[] contents, String type, String nameFile) {
		super.viewContentsFile(bean.getImagem(), "JPG", "logo.jpg", 200, 50);
	}

	/**
	 * 
	 */
	public void upload() {
		if(getFile() != null) {
			bean.setImagem(getFile().getContents());
			this.viewContentsFile(bean.getImagem(), "JPG", "logo.jpg", 200, 50);
		}
	}
	
	/**
	 * 
	 */
	@PreSave
	public void preSave() {
		upload();
		bean.setAutenticacao(bean.getAutentica() ? Constants.SIM : Constants.NAO);
	}

	/**
	 * 
	 */
	@Override
	protected void executeSave() throws ServiceException {
		getService().salvarFilial(bean);
	}

	public EnderecoView getEnderecoView() {
		return enderecoView;
	}

	public void setEnderecoView(EnderecoView enderecoView) {
		this.enderecoView = enderecoView;
	}

	public String getMaskInsEstadual() {
		return maskInsEstadual;
	}	
}
