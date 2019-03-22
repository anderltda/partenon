package br.com.project.cadastro.view;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.ProvidedCadastroView;
import br.com.project.commons.annotation.PreSearch;
import br.com.project.commons.cadastro.entity.Endereco;
import br.com.project.commons.util.StringUtil;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class EnderecoView extends ProvidedCadastroView<Long, Endereco> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getEditPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCreatePage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getViewPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 */
	@PreSearch
	public void preSearch() {		
		try {				
			if(StringUtil.isNotEmpty(bean.getCep())) {
				getService().buscaEndereco(bean.getCep());
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}	
}  
