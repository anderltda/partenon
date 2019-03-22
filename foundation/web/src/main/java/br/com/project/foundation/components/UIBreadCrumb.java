package br.com.project.foundation.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.StateHolder;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlForm;

import org.primefaces.component.api.AjaxSource;

/**
 * @author anderson.nascimento
 *
 */
@FacesComponent("br.com.project.foundation.components.BreadCrumb")
@ResourceDependencies( { 
        @ResourceDependency(library="primefaces", name="primefaces.css"),
        @ResourceDependency(library="primefaces", name="jquery/jquery.js"),
        @ResourceDependency(library="primefaces", name="primefaces.js")
	})
public class UIBreadCrumb extends HtmlForm implements AjaxSource, ClientBehaviorHolder, StateHolder {

	public static final String COMPONENT_TYPE = "br.com.project.foundation.components.BreadCrumb";
	public static final String COMPONENT_FAMILY = "br.com.project.foundation.components";
	private static final String DEFAULT_RENDERER = "br.com.project.foundation.components.BreadCrumbRenderer";
	private static final String OPTIMIZED_PACKAGE = "br.com.project.foundation.components";

	private List<String> eventNames = Arrays.asList("click");

	protected enum PropertyKeys {

		style, view, totalColumns, showOnlyFields, async, process, update, onstart, oncomplete, onerror, onsuccess, global;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {
		}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
		}
	}
	
	public boolean isPartialSubmit() {
		return false;
	}

	public UIBreadCrumb() {
		setRendererType(DEFAULT_RENDERER);
	}

	@Override
	public Collection<String> getEventNames() {
		return eventNames;
	}

	@Override
	public String getDefaultEventName() {
		return "click";
	}

	public Object getView() {
		return (Object) getStateHelper().eval(PropertyKeys.view, null);
	}

	public void setView(Object _bean) {
		getStateHelper().put(PropertyKeys.view, _bean);
		handleAttribute("bean", _bean);
	}

	public int getTotalColumns() {
		return (Integer) getStateHelper().eval(PropertyKeys.totalColumns, 0);
	}

	public void setTotalColumns(int _bean) {
		getStateHelper().put(PropertyKeys.totalColumns, _bean);
		handleAttribute("totalColumns", _bean);
	}

	public String getShowOnlyFields() {
		return (String) getStateHelper().eval(PropertyKeys.showOnlyFields, null);
	}

	public void setShowOnlyFields(String _bean) {
		getStateHelper().put(PropertyKeys.showOnlyFields, _bean);
		handleAttribute("showOnlyFields", _bean);
	}
	
	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public boolean isAsync() {
		return (Boolean) getStateHelper().eval(PropertyKeys.async, false);
	}

	public void setAsync(boolean _async) {
		getStateHelper().put(PropertyKeys.async, _async);
		handleAttribute("async", _async);
	}

	public String getProcess() {
		return (String) getStateHelper().eval(PropertyKeys.process, null);
	}

	public void setProcess(String _process) {
		getStateHelper().put(PropertyKeys.process, _process);
		handleAttribute("process", _process);
	}
	
	public String getStyle() {
		return (String) getStateHelper().eval(PropertyKeys.style, "width: auto");
	}

	public void setStyle(String _style) {
		getStateHelper().put(PropertyKeys.style, _style);
		handleAttribute("style", _style);
	}

	public String getUpdate() {
		return (String) getStateHelper().eval(PropertyKeys.update, null);
	}

	public void setUpdate(String _update) {
		getStateHelper().put(PropertyKeys.update, _update);
		handleAttribute("update", _update);
	}

	public String getOnstart() {
		return (String) getStateHelper().eval(PropertyKeys.onstart, null);
	}

	public void setOnstart(String _onstart) {
		getStateHelper().put(PropertyKeys.onstart, _onstart);
		handleAttribute("onstart", _onstart);
	}

	public String getOncomplete() {
		return (String) getStateHelper().eval(PropertyKeys.oncomplete, null);
	}

	public void setOncomplete(String _oncomplete) {
		getStateHelper().put(PropertyKeys.oncomplete, _oncomplete);
		handleAttribute("oncomplete", _oncomplete);
	}

	public String getOnerror() {
		return (String) getStateHelper().eval(PropertyKeys.onerror, null);
	}

	public void setOnerror(String _onerror) {
		getStateHelper().put(PropertyKeys.onerror, _onerror);
		handleAttribute("onerror", _onerror);
	}

	public String getOnsuccess() {
		return (String) getStateHelper().eval(PropertyKeys.onsuccess, null);
	}

	public void setOnsuccess(String _onsuccess) {
		getStateHelper().put(PropertyKeys.onsuccess, _onsuccess);
		handleAttribute("onsuccess", _onsuccess);
	}

	public boolean isGlobal() {
		return (Boolean) getStateHelper().eval(PropertyKeys.global, true);
	}

	public void setGlobal(boolean _global) {
		getStateHelper().put(PropertyKeys.global, _global);
		handleAttribute("global", _global);
	}

	@SuppressWarnings("unchecked")
	public void handleAttribute(String name, Object value) {
		List<String> setAttributes = (List<String>) this.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
		if (setAttributes == null) {
			String cname = this.getClass().getName();
			if (cname != null && cname.startsWith(OPTIMIZED_PACKAGE)) {
				setAttributes = new ArrayList<String>(6);
				this.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
			}
		}
		if (setAttributes != null) {
			if (value == null) {
				ValueExpression ve = getValueExpression(name);
				if (ve == null) {
					setAttributes.remove(name);
				} else if (!setAttributes.contains(name)) {
					setAttributes.add(name);
				}
			}
		}
	}

	public boolean isPartialSubmitSet() {
		return false;
	}

	@Override
	public boolean isResetValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isResetValuesSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIgnoreAutoUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAjaxified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDelay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public String getPartialSubmitFilter() {
		// TODO Auto-generated method stub
		return null;
	}
}
