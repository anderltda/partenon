package br.com.project.foundation.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.project.commons.annotation.PosCreate;
import br.com.project.commons.annotation.PosEdit;
import br.com.project.commons.annotation.PosSave;
import br.com.project.commons.annotation.PosView;
import br.com.project.commons.annotation.PreSearch;
import br.com.project.commons.util.BaseObject;
import br.com.project.foundation.persistence.BasicEntityObject;

/**
 * @author anderson.nascimento
 *
 * @param <ID>
 * @param <T>
 */
public abstract class MasterView<ID extends Serializable, T extends BaseObject> extends CrudView<ID, T> {
	
	private static final long serialVersionUID = 1L;

	@PreSearch
	@PosCreate
	@PosSave
	@PosView
	@PosEdit
	public abstract void updateChildren();
	
	/**
	 * @param name
	 * @param bean
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void setChildrenMasterBean(String name, String aba, BasicEntityObject<?> bean) {
		Map<String, String> abaView = new HashMap<String, String>();
		DetailView detailView = findBean(name);
		detailView.setBeanMaster(bean);
		abaView.put(name, aba);
		addAbas(abaView);
	}
}
