package br.com.project.cadastro.view;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.ProvidedCadastroView;
import br.com.project.commons.annotation.PosEdit;
import br.com.project.commons.annotation.PosView;
import br.com.project.commons.annotation.PreSave;
import br.com.project.commons.cadastro.entity.Agencia;
import br.com.project.commons.cadastro.entity.Banco;
import br.com.project.commons.cadastro.entity.Endereco;
import br.com.project.commons.cadastro.entity.Motorista;
import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class MotoristaView extends ProvidedCadastroView<String, Motorista> {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{enderecoView}")
	protected EnderecoView enderecoView;	
	
	@ManagedProperty(value = "#{agenciaView}")
	protected AgenciaView agenciaView;
	
	@Override
	public String getEditPage() {
		return "/cadastro/formMotorista.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/cadastro/formMotorista.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/cadastro/formMotorista.xhml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/cadastro/filterMotorista.xhml?faces-redirect=true";
	}
	
	@PosView
	public void posView() {
		findAgenciaBancaria();
		this.viewContentsFile(bean.getImagem(), "JPG", "foto.jpg");
	}
	
	@PosEdit
	public void posEdit() {
		findAgenciaBancaria();
		this.viewContentsFile(bean.getImagem(), "JPG", "foto.jpg");		
	}
	
	private void findAgenciaBancaria() {
		try {
			Agencia agencia = getService().getByID(Agencia.class, bean.getContaBancaria().getId());
			Banco banco = getService().getByID(Banco.class, agencia.getBanco().getId());
			agencia.setBanco(banco);			
			bean.getContaBancaria().setAgencia(agencia);
		} catch (ServiceException ex) {
			ex.printStackTrace();
		}
	}
	
	@PreSave
	public void preSave() {
		upload();
	}
	
	/**
	 * 
	 */
	protected void viewContentsFile(byte[] contents, String type, String nameFile) {
		super.viewContentsFile(bean.getImagem(), "JPG", "foto.jpg", 120, 120);
	}

	/**
	 * 
	 */
	public void upload() {
		if(getFile() != null) {
			bean.setImagem(getFile().getContents());
			this.viewContentsFile(bean.getImagem(), "JPG", "foto.jpg");
		}
	}
	
	
	@Override
	protected void executeSave() throws ServiceException {		
		getService().salvarMotorista(bean);		
	}
	
	/**
	 * 
	 */
	@Override
	public void onSelectDialog() {
		try {			
			bean.getContaBancaria().setAgencia(agenciaView.getSelected());
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

	public EnderecoView getEnderecoView() {
		return enderecoView;
	}

	public void setEnderecoView(EnderecoView enderecoView) {
		this.enderecoView = enderecoView;
	}

	public AgenciaView getAgenciaView() {
		return agenciaView;
	}

	public void setAgenciaView(AgenciaView agenciaView) {
		this.agenciaView = agenciaView;
	}
}  
