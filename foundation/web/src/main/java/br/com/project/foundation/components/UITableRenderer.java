package br.com.project.foundation.components;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;

import br.com.project.commons.annotation.FilterProperty;
import br.com.project.commons.util.AnnotationUtil;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.custom.CustomLazyDataModel;
import br.com.project.foundation.util.BeanManager;
import br.com.project.foundation.util.JSFHelper;
import br.com.project.foundation.util.MessageHelper;
import br.com.project.foundation.util.RenderHelper;
import br.com.project.foundation.view.ProvidedView;

import com.sun.faces.renderkit.html_basic.GridRenderer;

/**
 * @author anderson.nascimento
 *
 */
@FacesRenderer(componentFamily = "br.com.project.foundation.components", rendererType = "br.com.project.foundation.components.TableRenderer")
public class UITableRenderer extends GridRenderer {

	private String view_;

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {}

	@Override
	@SuppressWarnings("unchecked")
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {

		int seq = 0;

		component.getChildren().clear();

		UITable table = (UITable) component;

		ProvidedView<BaseObject> view = (ProvidedView<BaseObject>) table.getView();	

		view_ = StringUtil.firstLower(ReflectionUtil.getBeanClassName(view));

		BeanManager<?> beanManager = (BeanManager<?>) view.getBeanManager();

		List<FilterProperty> filters = AnnotationUtil.table(beanManager.getBeanClass());

		MethodExpression me = JSFHelper.createMethodExpression(executeMethod( view_ +"." + table.getValue()), List.class, new Class<?>[] {});			

		CustomLazyDataModel<?> list = (CustomLazyDataModel<?>)me.invoke(context.getELContext(), new Object[] {});

		DataTable dataTable = (DataTable) context.getApplication().createComponent(DataTable.COMPONENT_TYPE);

		if(list != null) {

			dataTable.setVar("target");
			dataTable.setWidgetVar("list");
			dataTable.setResizableColumns(true);			
			dataTable.setInView(false);
			dataTable.setPaginator(true);
			dataTable.setLazy(true);
			dataTable.setPaginatorPosition("bottom");
			dataTable.setScrollable(true);
			dataTable.setSelectionMode("single");
			dataTable.setSelection(JSFHelper.createValueExpression("#{".concat(view_).concat(".bean").concat("}"), Object.class));
			dataTable.setRows(10);
			dataTable.setRowsPerPageTemplate("10,20,30");
			dataTable.setEmptyMessage(MessageHelper.getMessage("message_nao_encontrado"));		

			Column column = null;

			for (FilterProperty fp : filters) {

				Annotation annotation = fp.getAnnotation();

				if (annotation instanceof Column) {						

					column = (Column) context.getApplication().createComponent(Column.COMPONENT_TYPE);					

					br.com.project.commons.annotation.Column columnTable = (br.com.project.commons.annotation.Column) annotation;

					column.setHeaderText(MessageHelper.getMessage(columnTable.label()));

					column.setStyle(columnTable.style());

					RenderHelper.renderOutputText(context, column, "field_" + seq, JSFHelper.createValueExpression("#{target.".concat(fp.getName()).concat("}"), Object.class));			

					seq++;
					
					dataTable.setRowKey(seq);				
					
					dataTable.getChildren().add(column);
				}

			}				

			dataTable.setValueExpression("value", JSFHelper.createValueExpression("#{"+ view_ + "." + table.getValue() + "}", Object.class));
			
			table.getChildren().add(dataTable);
		}

		super.encodeChildren(context, table);
	}


	@Override
	public void encodeEnd(FacesContext ctx, UIComponent component) throws IOException {}


	/**
	 * @param value
	 * @return
	 */
	private String executeMethod(String value) {		
		String clazz = value.substring(0, value.indexOf("."));
		String method = value.substring(value.indexOf(".") + 1);
		String expression = "get" + method.substring(0, 1).toUpperCase() + method.substring(1);
		return "#{" + clazz +"."+ expression + "}";		
	}
}
