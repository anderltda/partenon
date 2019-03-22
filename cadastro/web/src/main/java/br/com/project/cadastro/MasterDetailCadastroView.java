package br.com.project.cadastro;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import br.com.project.cadastro.service.CadastroService;
import br.com.project.commons.cadastro.service.CadastroCommonService;
import br.com.project.commons.monitoramento.MonitoramentoCommonService;
import br.com.project.commons.util.BaseObject;
import br.com.project.foundation.view.DetailView;

/**
 * @author anderson.nascimento
 *
 * @param <T>
 */
public abstract class MasterDetailCadastroView<ID extends Serializable, T extends BaseObject, M extends BaseObject, D extends BaseObject> extends DetailView<ID, T, M, D> {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{" + CadastroService.SERVICE + "}")
	protected CadastroService cadastroService;
	
	@ManagedProperty(value = "#{" + CadastroCommonService.SERVICE + "}")
	protected CadastroCommonService cadastroCommonService;
	
	@ManagedProperty(value = "#{" + MonitoramentoCommonService.SERVICE + "}" )
	protected MonitoramentoCommonService monitoramentoCommonService;
	
	public CadastroService getService() {
		return cadastroService;
	}

	public void setCadastroService(CadastroService cadastroService) {
		this.cadastroService = cadastroService;
	}

	public MonitoramentoCommonService getMonitoramentoCommonService() {
		return monitoramentoCommonService;
	}

	public void setMonitoramentoCommonService(MonitoramentoCommonService monitoramentoCommonService) {
		this.monitoramentoCommonService = monitoramentoCommonService;
	}

	public CadastroCommonService getCadastroCommonService() {
		return cadastroCommonService;
	}

	public void setCadastroCommonService(CadastroCommonService cadastroCommonService) {
		this.cadastroCommonService = cadastroCommonService;
	}	
}