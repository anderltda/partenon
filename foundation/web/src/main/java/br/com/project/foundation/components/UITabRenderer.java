package br.com.project.foundation.components;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.servlet.http.HttpServletRequest;

import br.com.project.commons.annotation.FilterProperty;
import br.com.project.commons.util.AnnotationUtil;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.util.BeanManager;
import br.com.project.foundation.view.ProvidedView;

import com.sun.faces.renderkit.html_basic.FormRenderer;

/**
 * @author anderson.nascimento
 *
 */
@FacesRenderer(componentFamily = "br.com.project.foundation.components", rendererType = "br.com.project.foundation.components.TabRenderer")
public class UITabRenderer extends FormRenderer {

	@SuppressWarnings("unchecked")
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

		UITab tab = (UITab) component;

		ResponseWriter writer = context.getResponseWriter();
		
		ProvidedView<BaseObject> view = (ProvidedView<BaseObject>) tab.getView();
		
		String view_ = StringUtil.firstLower(ReflectionUtil.getBeanClassName(view));
		
		Object id = view.getBean().getId();
		
		Set<Map<String, String>> abas = view.getAbas();

		writer.startElement("ul", tab);
		writer.writeAttribute("class", "nav nav-tabs pull-right", null);
		
		if(id != null) {		
			for (Map<String, String> abaView : abas) {
				for (String key : abaView.keySet()) {
					writer.startElement("li", tab);		
					writer.startElement("a", tab);
					writer.writeAttribute("id", "aba" + StringUtil.firstUpper(key), null);
					writer.writeAttribute("href", "#dado" + StringUtil.firstUpper(key), null);
					writer.writeAttribute("data-toggle", "tab", null);
					writer.write(abaView.get(key));
					writer.endElement("a");
					writer.endElement("li");					
				}
			}			
		}
	
		writer.startElement("li", tab);		
		writer.writeAttribute("class", "active", null);
		writer.startElement("a", tab);
		writer.writeAttribute("id", "aba" + StringUtil.firstUpper(view_), null);
		writer.writeAttribute("href", "#dado" + StringUtil.firstUpper(view_), null);
		writer.writeAttribute("data-toggle", "tab", null);
		writer.write(tab.getName());
		writer.endElement("a");
		writer.endElement("li");			

		writer.endElement("ul");
	}	

}
