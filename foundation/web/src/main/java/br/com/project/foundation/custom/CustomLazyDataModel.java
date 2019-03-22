package br.com.project.foundation.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.project.commons.service.CommonsService;
import br.com.project.commons.util.NumberUtil;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.SetUtil;
import br.com.project.commons.util.StringUtil;

/**
 * @author anderson.nascimento
 *
 * @param <T>
 */
public class CustomLazyDataModel<T> extends LazyDataModel<T> {
	
	private static final long serialVersionUID = 1L;

	private Map<String, Object> conditions;
	private String query;
	private Class<T> beanClass;
	private CommonsService service;
	private List<T> data;

	public CustomLazyDataModel(CommonsService service, Map<String, Object> filters, String query) {
		this.service = service;
		this.conditions = filters;
		this.query = query;
	}
	
	public CustomLazyDataModel(CommonsService service, Map<String, Object> filters, Class<T> beanClass) {
		this.service = service;
		this.conditions = filters;
		this.beanClass = beanClass;
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {

		data = new ArrayList<T>();
		
		try {
			
			int firstResult = first;
			
			int maxResult = (first + pageSize);		

			if(StringUtil.isNotEmpty(query)) {

				setRowCount(service.countQuery(query, conditions));
				
				data = service.selectQuery(query, conditions, firstResult, maxResult);

			} else {
				
				setRowCount(service.countEntity(beanClass, conditions));
				
				data = service.getByCondition(beanClass, conditions, firstResult, maxResult);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
	
	@Override
    public T getRowData(String rowKey) {
		Long id = null;
		Long key = NumberUtil.getLong(rowKey);
		
		if(SetUtil.nonEmpty(data)) {
			for (T object : data) {
				id = (Long)ReflectionUtil.executeGetterMethod(object, "id");				
				if(id.equals(key)) {
					return object;
				}
			}
		}		
		return null;
    }

	@Override
    public Object getRowKey(T object) {
       return object;
    }
}
