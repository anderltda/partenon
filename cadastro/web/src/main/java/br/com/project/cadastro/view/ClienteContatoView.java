package br.com.project.cadastro.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.MasterDetailCadastroView;
import br.com.project.commons.cadastro.entity.Cliente;
import br.com.project.commons.cadastro.entity.ClienteContato;

/**
 * @author anderson.nascimento
 * 
 */
@ManagedBean
@SessionScoped
public class ClienteContatoView extends MasterDetailCadastroView<Long, ClienteContato, Cliente, ClienteContato> { 

	private static final long serialVersionUID = 1L;
		
	@Override
	public String getEditPage() {
		return "/cadastro/formClienteContato.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/cadastro/formClienteContato.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/cadastro/formClienteContato.xhml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/cadastro/formCliente.xhml?faces-redirect=true";
	}

}
