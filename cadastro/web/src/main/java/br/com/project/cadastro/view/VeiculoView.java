package br.com.project.cadastro.view;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.project.cadastro.ProvidedCadastroView;
import br.com.project.commons.annotation.PosCreate;
import br.com.project.commons.annotation.PosEdit;
import br.com.project.commons.annotation.PosView;
import br.com.project.commons.annotation.PreSave;
import br.com.project.commons.cadastro.entity.Veiculo;
import br.com.project.foundation.exception.ServiceException;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class VeiculoView extends ProvidedCadastroView<String, Veiculo> {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{motoristaView}")
	protected MotoristaView motoristaView;

	@ManagedProperty(value = "#{filialView}")
	protected FilialView filialView;

	@Override
	public String getEditPage() {
		return "/cadastro/formVeiculo.xhml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/cadastro/formVeiculo.xhml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/cadastro/formVeiculo.xhml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/cadastro/filterVeiculo.xhml?faces-redirect=true";
	}

	@PosCreate
	public void posCreate() {
		bean.setImagem(null);
	}
	
	@PosView
	public void posView() {		
		this.viewContentsFile(bean.getImagem(), "JPG", "veiculo.jpg");				
	}
	
	@PosEdit
	public void posEdit() {
		this.viewContentsFile(bean.getImagem(), "JPG", "veiculo.jpg");	
	}
	
	@PreSave
	public void preSave() {
		upload();
	}

	/**
	 * 
	 */
	@Override
	public void onSelectDialog() {
		setSelectDialog(bean, motoristaView.getSelected());
		setSelectDialog(beanFilter, motoristaView.getSelected());
		setSelectDialog("filial", bean, filialView.getSelected());
		setSelectDialog("filial", beanFilter, filialView.getSelected());
		
		motoristaView.setSelected(null);
		filialView.setSelected(null);
	}

	/**
	 * 
	 */
	protected void viewContentsFile(byte[] contents, String type, String nameFile) {
		super.viewContentsFile(bean.getImagem(), "JPG", "veiculo.jpg", 800, 600);
	}

	/**
	 * 
	 */
	public void upload() {
		if(getFile() != null) {
			bean.setImagem(getFile().getContents());
			this.viewContentsFile(bean.getImagem(), "JPG", "veiculo.jpg", 800, 600);
		}
	}

	/**
	 * Adicionar motorista na propriedade proprietario do bean
	 */
	public void openProprietarioDialog() {
		addSelect = "proprietario";
	}

	/**
	 * Adicionar motorista na propriedade motorista do bean
	 */
	public void openMotoristaDialog() {
		addSelect = "motorista";
	}

	/**
	 * 
	 */
	@Override
	protected void executeSave() throws ServiceException {
		getService().salvarVeiculo(bean);
	}

	public MotoristaView getMotoristaView() {
		return motoristaView;
	}

	public void setMotoristaView(MotoristaView motoristaView) {
		this.motoristaView = motoristaView;
	}

	public FilialView getFilialView() {
		return filialView;
	}

	public void setFilialView(FilialView filialView) {
		this.filialView = filialView;
	}

}  
