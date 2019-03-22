package br.com.project.cadastro.view;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.MasterDetailCadastroView;
import br.com.project.commons.cadastro.entity.Agencia;
import br.com.project.commons.cadastro.entity.Banco;
import br.com.project.commons.util.dto.SelectItemDTO;
import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class AgenciaView extends MasterDetailCadastroView<Long, Agencia, Banco, Agencia> { 

	private static final long serialVersionUID = 1L;
	
	private List<SelectItemDTO> bancos;

	@Override
	public String getEditPage() {
		return "/cadastro/formAgencia.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/cadastro/formAgencia.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/cadastro/formAgencia.xhml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/cadastro/formBanco.xhml?faces-redirect=true";
	}
	
	public List<SelectItemDTO> getBancos() {
		try {
			
			bancos = new ArrayList<SelectItemDTO>();
			
			List<Banco> banks = getService().getByAll(Banco.class);			
			
			for (Banco banco : banks) {
				bancos.add(new SelectItemDTO(banco.getId(), banco.getCodigoNome()));
			}
			
		} catch (ServiceException ex) {
			ex.printStackTrace();
		}
		return bancos;
	}
}  
