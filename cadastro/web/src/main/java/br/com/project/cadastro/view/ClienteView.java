package br.com.project.cadastro.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.MasterCadastroView;
import br.com.project.commons.annotation.Combo;
import br.com.project.commons.cadastro.entity.Cliente;
import br.com.project.commons.cadastro.entity.Endereco;
import br.com.project.commons.cadastro.entity.TipoContribuinte;

/**
 * @author anderson.nascimento
 * 
 */
@ManagedBean
@SessionScoped
public class ClienteView extends MasterCadastroView<Long, Cliente> {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{enderecoView}")
	protected EnderecoView enderecoView;
	
	@ManagedProperty(value = "#{filialView}")
	protected FilialView filialView;
	
	@Combo(bean = TipoContribuinte.class)
	private List<TipoContribuinte> tiposContribuintes; 
		
	@Override
	public String getEditPage() {
		return "/cadastro/formCliente.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/cadastro/formCliente.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/cadastro/formCliente.xhml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/cadastro/filterCliente.xhml?faces-redirect=true";
	}

	@Override
	public String getSavePage() {
		return getEditPage();
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
			setSelectDialog(bean, filialView.getSelected());			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void updateChildren() {
		setChildrenMasterBean("clienteContatoView", "Dados Contatos", bean);	
		setChildrenMasterBean("clienteCobrancaView", "Dados Cobrança", bean);
	}
	
	/**
	 * Adicionar filial na filialCobranca do bean
	 */
	public void openFilialCobrancaDialog() {
		addSelect = "filialCobranca";
	}

	/**
	 * Adicionar filial na filialResponsavel do bean
	 */
	public void openFilialResponsavelDialog() {
		addSelect = "filialResponsavel";
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

	public EnderecoView getEnderecoView() {
		return enderecoView;
	}

	public void setEnderecoView(EnderecoView enderecoView) {
		this.enderecoView = enderecoView;
	}

	public List<TipoContribuinte> getTiposContribuintes() {
		return tiposContribuintes;
	}

	public void setTiposContribuintes(List<TipoContribuinte> tiposContribuintes) {
		this.tiposContribuintes = tiposContribuintes;
	}

	public FilialView getFilialView() {
		return filialView;
	}

	public void setFilialView(FilialView filialView) {
		this.filialView = filialView;
	}	
}
