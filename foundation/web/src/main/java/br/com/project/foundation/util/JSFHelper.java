package br.com.project.foundation.util;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.view.BaseBeanView;

public class JSFHelper {

	public static MethodExpression createMethodExpression(String el, Class<?> retType, Class<?>[] argsType) {
		FacesContext facesCtx = getContext();
		ELContext elCtx = facesCtx.getELContext();
		ExpressionFactory expFact = facesCtx.getApplication().getExpressionFactory();

		return expFact.createMethodExpression(elCtx, el, retType, argsType);
	}

	public static ValueExpression createValueExpression(String el, Class<?> type) {
		FacesContext facesCtx = getContext();
		ELContext elCtx = facesCtx.getELContext();
		ExpressionFactory expFact = facesCtx.getApplication().getExpressionFactory();

		return expFact.createValueExpression(elCtx, el, type);
	}

	public static List<SelectItem> convertListToSelectItem(List<?> list, String valueField, String labelField, boolean addEmptyOption) {
		List<SelectItem> itens = new ArrayList<SelectItem>();
		if (addEmptyOption) {
        		SelectItem empty = new SelectItem("", "");
        		itens.add(empty);
		}
		for (Object baseEntity : list) {
			SelectItem item = new SelectItem(ReflectionUtil.executeGetterMethod(baseEntity, valueField), (String) ReflectionUtil.executeGetterMethod(baseEntity, labelField));
			itens.add(item);
		}
		return itens;
	}
	
	public static BaseBeanView<? extends BaseObject> getController(Class<?> beanClass) {
		String controllerName = StringUtil.firstLower(beanClass.getSimpleName()) + "Controller";
		return getController(controllerName);
	}
	
	public static BaseBeanView<? extends BaseObject> getController(String controllerName) {
		return (BaseBeanView<?>) getContext().getExternalContext().getSessionMap().get(controllerName);
	}
	
	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public static ELContext getELContext() {
		return getContext().getELContext();
	}

	public static ExternalContext getExternalContext() {
		return getContext().getExternalContext();
	}
}
