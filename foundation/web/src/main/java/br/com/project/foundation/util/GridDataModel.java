package br.com.project.foundation.util;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.SetUtil;

/**
 * @author anderson.nascimento
 *
 * @param <E>
 */
public class GridDataModel<E extends BaseObject> extends ListDataModel<E> implements SelectableDataModel<E> {	
	
	protected E model;
	protected List<E> listGrid;

	public GridDataModel(List<E> list) {
		super(list);	
		listGrid = list;
		if(SetUtil.nonEmpty(list)) {
			model = list.get(0);
		}
	}

	public E getModel() {
		return model;
	}

	public List<E> getListGrid() {
		return listGrid;
	}

	@Override
	public Object getRowKey(E object) {
		return object;
	}

	@Override
	public E getRowData(String rowKey) {
		return null;
	}		
}
