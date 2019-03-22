package br.com.project.foundation.components;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.servlet.http.HttpServletRequest;

import br.com.project.commons.util.SetUtil;

import com.sun.faces.renderkit.html_basic.FormRenderer;

/**
 * @author anderson.nascimento
 *
 */
@FacesRenderer(componentFamily = "br.com.project.foundation.components", rendererType = "br.com.project.foundation.components.BreadCrumbRenderer")
public class UIBreadCrumbRenderer extends FormRenderer {
	
	private Set<String> urls = new LinkedHashSet<String>();

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

		UIBreadCrumb breadCrumb = (UIBreadCrumb) component;

		ResponseWriter writer = context.getResponseWriter();
	
		String name = null;

		String name_ = null;

		String clientId = breadCrumb.getClientId(context);

		HttpServletRequest request = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
		
		if(SetUtil.nonEmpty(urls) && urls.size()>3) {
			urls.remove(urls.iterator().next());
		}
		
		writer.startElement("ol", breadCrumb);
		writer.writeAttribute("class", "breadcrumb", null);
		
		for (String url : urls) {
			
			name_ = url.substring(url.lastIndexOf("/") + 1);
			name = (name_.replace("dashboard", "Home").replace("filter", "Filtro ").replace("form", "").replace(".jsf", ""));
			
			writer.startElement("li", breadCrumb);		
			writer.startElement("a", breadCrumb);
			writer.writeAttribute("id", clientId, "id");
			writer.writeAttribute("href", url, null);
			writer.startElement("i", breadCrumb);
			writer.writeAttribute("class", "fa fa-bars", null);    		
			writer.endElement("i");
			writer.write(name);
			writer.endElement("a");
			writer.endElement("li");			
		}

		writer.endElement("ol");
		urls.add(request.getRequestURI());
	}	

}
