package br.com.project.seguranca;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import br.com.project.commons.util.BaseObject;
import br.com.project.foundation.view.CrudView;
import br.com.project.seguranca.service.SegurancaService;


/**
 * @author anderson.nascimento
 *
 * @param <ID>
 * @param <T>
 */
public abstract class ProvidedSegurancaView <ID extends Serializable, T extends BaseObject> extends CrudView<ID, T> {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{" + SegurancaService.SERVICE + "}" )
	protected SegurancaService segurancaService; 

	public SegurancaService getService() {
		return segurancaService;
	}

	public void setSegurancaService(SegurancaService segurancaService) {
		this.segurancaService = segurancaService;
	}
}
