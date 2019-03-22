package br.com.project.seguranca.view;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.commons.util.dto.SelectItemDTO;
import br.com.project.foundation.exception.ServiceException;
import br.com.project.foundation.util.MessageHelper;
import br.com.project.seguranca.ProvidedSegurancaView;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class UsuarioView extends ProvidedSegurancaView<Long, Usuario> {

	private static final long serialVersionUID = 1L;

	@Override
	public String getEditPage() {
		return "/seguranca/formUsuario.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/seguranca/formUsuario.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/seguranca/formUsuario.xhml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/seguranca/filterUsuario.xhml?faces-redirect=true";
	}

	@Override
	protected void executeSave() throws ServiceException {
		getService().salvarUsuario(bean);
	}

	public List<SelectItemDTO> comboSelectAtivo() {
		List<SelectItemDTO> selectAtivo = new ArrayList<SelectItemDTO>();
		selectAtivo.add(new SelectItemDTO("S", MessageHelper.getMessage("lbl_sim")));
		selectAtivo.add(new SelectItemDTO("N", MessageHelper.getMessage("lbl_nao")));
		return selectAtivo;
	}
}

