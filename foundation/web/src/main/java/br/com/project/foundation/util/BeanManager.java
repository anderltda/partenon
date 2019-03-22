package br.com.project.foundation.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.project.commons.annotation.Configuration;
import br.com.project.commons.util.AnnotationUtil;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;

/**
 * @author anderson.nascimento
 *
 * @param <T>
 */
public class BeanManager<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String beanPackage;
	private String name;
	private String className;
	private String module;
	private String fieldValue;
	private String fieldLabel;

	private int rows;

	private Class<?> beanClass;
	private Class<?> beanFilterClass;
	private Class<?> entityClass;

	public BeanManager(String module, Class<?> beanFilterClass, Class<?> beanClass) {
		this.module = module;
		this.beanFilterClass = beanFilterClass;
		this.beanClass =  beanClass;
		init();
	}

	private void init() {
		Pattern p = Pattern.compile(ConfigWebHelper.getBasicPackage() + "\\.(\\w+)\\.\\w+\\.(\\w+)");

		String beanName = beanClass.getName();
		Matcher packageRegExp = p.matcher(beanName);
		if (packageRegExp.find()) {
			beanPackage = packageRegExp.group(1);
			className = packageRegExp.group(2);
			name = StringUtil.firstLower(className);
		}
		this.rows = ConfigWebHelper.getDefaultRowsPerPage();

		Configuration cfg = (Configuration) AnnotationUtil.getAnnotation(beanClass, Configuration.class);
		if (cfg != null) {
			fieldValue = cfg.fieldValue();
			fieldLabel = cfg.fieldLabel();
			if (!cfg.entityClass().getName().equals(Object.class.getName())) {
				entityClass = cfg.entityClass();
			} else {
				entityClass = beanClass;
			}
		} else {
			fieldValue = "id";
			fieldLabel = "nome";
			entityClass = beanClass;
		}
	}

	public T newInstance() {
		return (T) ReflectionUtil.newInstance(getBeanClass());
	}
	
	public T newFilterInstance() {
		return (T) ReflectionUtil.newInstance(getBeanFilterClass());
	}

	public boolean isIdNull(T bean) {
		return ReflectionUtil.isIdNull(bean);
	}

	@SuppressWarnings("unchecked")
	public Class<T> getBeanClass() {
		return (Class<T>) beanClass;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getBeanFilterClass() {
		return (Class<T>) beanFilterClass;
	}

	public String getClassName() {
		return className;
	}

	public String getName() {
		return name;
	}

	public String getBeanPackage() {
		return beanPackage;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getModule() {
		return module;
	}	


	public String getFieldValue() {
		return fieldValue;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}
}
