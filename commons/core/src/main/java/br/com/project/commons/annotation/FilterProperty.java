package br.com.project.commons.annotation;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class FilterProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Class<?> fieldType;
	private int order;
	private Annotation annotation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
