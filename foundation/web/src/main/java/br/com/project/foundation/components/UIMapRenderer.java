package br.com.project.foundation.components;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import org.primefaces.component.gmap.GMap;

import com.sun.faces.renderkit.html_basic.GridRenderer;

/**
 * @author anderson.nascimento
 *
 */
@FacesRenderer(componentFamily = "br.com.project.foundation.components", rendererType = "br.com.project.foundation.components.MapRenderer")
public class UIMapRenderer extends GridRenderer {

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		
		UIMap maps = (UIMap) component;

		maps.getChildren().clear();
		maps.setColumns(1);

		super.encodeBegin(context, maps);
	}

	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {

		rendererParamsNotNull(context, component);

		if (!shouldEncodeChildren(component)) {
			return;
		}

		UIMap map = (UIMap) component;
		
		HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) context.getApplication().createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		htmlPanelGrid.setColumns(1);
		component.getChildren().add(htmlPanelGrid);
		
		GMap gMap =  (GMap) context.getApplication().createComponent(GMap.COMPONENT_TYPE);
		gMap.setType("ROADMAP");
		
		htmlPanelGrid.getChildren().add(gMap);
		
		super.encodeChildren(context, component);
	}

	@Override
	public void encodeEnd(FacesContext ctx, UIComponent component) throws IOException {
		super.encodeEnd(ctx, component);
	}

	
}
