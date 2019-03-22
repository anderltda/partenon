package br.com.project.commons.util.dto;

import br.com.project.commons.util.BaseObject;


/**
 * @author anderson.nascimento
 *
 */
public class SelectItemDTO extends BaseObject {
	
	private static final long serialVersionUID = 1L;
	
	private Object value;
	private Object label;
	
	public SelectItemDTO() {
		super();
	}
			
	public SelectItemDTO(Object value, Object label) {
		super();
		this.value = value;
		this.label = label;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getLabel() {
		return label;
	}
	
	public void setLabel(Object label) {
		this.label = label;
	}	
}