package br.com.project.monitoramento;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import br.com.project.commons.monitoramento.MonitoramentoCommonService;
import br.com.project.commons.util.BaseObject;
import br.com.project.foundation.view.CrudView;
import br.com.project.monitoramento.service.MonitoramentoService;


/**
 * @author anderson.nascimento
 *
 * @param <ID>
 * @param <T>
 */
public abstract class ProvidedMonitoramentoView <ID extends Serializable, T extends BaseObject> extends CrudView<ID, T> {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{" + MonitoramentoService.SERVICE + "}" )
	protected MonitoramentoService monitoramentoService; 
	
	@ManagedProperty(value = "#{" + MonitoramentoCommonService.SERVICE + "}" )
	protected MonitoramentoCommonService monitoramentoCommonService; 

	public MonitoramentoService getService() {
		return monitoramentoService;
	}

	public void setMonitoramentoService(MonitoramentoService monitoramentoService) {
		this.monitoramentoService = monitoramentoService;
	}

	public MonitoramentoCommonService getMonitoramentoCommonService() {
		return monitoramentoCommonService;
	}

	public void setMonitoramentoCommonService(MonitoramentoCommonService monitoramentoCommonService) {
		this.monitoramentoCommonService = monitoramentoCommonService;
	}	
}
