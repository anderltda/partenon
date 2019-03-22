package br.com.project.cadastro.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.MasterDetailCadastroView;
import br.com.project.commons.annotation.Combo;
import br.com.project.commons.annotation.PosEdit;
import br.com.project.commons.annotation.PosView;
import br.com.project.commons.cadastro.entity.Cliente;
import br.com.project.commons.cadastro.entity.ClienteCobranca;
import br.com.project.commons.cadastro.entity.ContaBancaria;
import br.com.project.commons.cadastro.entity.Endereco;
import br.com.project.commons.cadastro.entity.TipoCobranca;
import br.com.project.commons.cadastro.entity.TipoFaturamento;
import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 * 
 */
@ManagedBean
@SessionScoped
public class ClienteCobrancaView extends MasterDetailCadastroView<Long, ClienteCobranca, Cliente, ClienteCobranca> { 

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{enderecoView}")
	protected EnderecoView enderecoView;	
	
	@ManagedProperty(value = "#{agenciaView}")
	protected AgenciaView agenciaView;
	
	@Combo(bean = TipoCobranca.class)
	private List<TipoCobranca> tiposCobrancas; 
	
	@Combo(bean = TipoFaturamento.class)
	private List<TipoFaturamento> tiposFaturamentos;
		
	@Override
	public String getEditPage() {
		return "/cadastro/formClienteCobranca.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/cadastro/formClienteCobranca.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/cadastro/formClienteCobranca.xhml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/cadastro/formCliente.xhml?faces-redirect=true";
	}
	
	/**
	 * 
	 */
	@PosView
	public void posView() {
		findAgenciaBancaria();		
	}
	
	/**
	 * 
	 */
	@PosEdit
	public void posEdit() {
		findAgenciaBancaria();	
	}
	
	/**
	 * 
	 */
	private void findAgenciaBancaria() {
		try {
			ContaBancaria contaBancaria = getCadastroCommonService().buscarContaBancaria(bean.getContaBancaria().getId());			
			bean.setContaBancaria(contaBancaria);
		} catch (ServiceException ex) {
			ex.printStackTrace();
		}
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

	public List<TipoCobranca> getTiposCobrancas() {
		return tiposCobrancas;
	}

	public void setTiposCobrancas(List<TipoCobranca> tiposCobrancas) {
		this.tiposCobrancas = tiposCobrancas;
	}

	public List<TipoFaturamento> getTiposFaturamentos() {
		return tiposFaturamentos;
	}

	public void setTiposFaturamentos(List<TipoFaturamento> tiposFaturamentos) {
		this.tiposFaturamentos = tiposFaturamentos;
	}
}
