package br.com.project.commons.view;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import br.com.project.commons.service.CommonsService;
import br.com.project.commons.util.BaseObject;
import br.com.project.foundation.view.CrudView;


/**
 * @author anderson.nascimento
 *
 * @param <ID>
 * @param <T>
 */
public abstract class ProvidedCommonsView <ID extends Serializable, T extends BaseObject> extends CrudView<ID, T> {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{" + CommonsService.SERVICE + "}" )
	protected CommonsService commonsService; 

	public CommonsService getService() {
		return commonsService;
	}

	public void setCommonsService(CommonsService service) {
		this.commonsService = service;
	}
}
