package br.com.project.cadastro;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import br.com.project.cadastro.service.CadastroService;
import br.com.project.commons.monitoramento.MonitoramentoCommonService;
import br.com.project.commons.util.BaseObject;
import br.com.project.foundation.view.CrudView;

/**
 * @author anderson.nascimento
 *
 * @param <T>
 */
public abstract class ProvidedCadastroView<ID extends Serializable, T extends BaseObject> extends CrudView<ID, T> {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{" + CadastroService.SERVICE + "}")
	protected CadastroService cadastroService;
	
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
}