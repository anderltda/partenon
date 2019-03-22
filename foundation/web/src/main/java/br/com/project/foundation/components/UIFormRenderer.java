package br.com.project.foundation.components;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import org.apache.commons.collections.CollectionUtils;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.inputtext.InputText;

import br.com.project.commons.annotation.FilterProperty;
import br.com.project.commons.annotation.filter.DateFilter;
import br.com.project.commons.annotation.filter.DialogFilter;
import br.com.project.commons.annotation.filter.MaskFilter;
import br.com.project.commons.annotation.filter.SelectFilter;
import br.com.project.commons.annotation.filter.TextFilter;
import br.com.project.commons.util.AnnotationUtil;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.util.BeanManager;
import br.com.project.foundation.util.JSFHelper;
import br.com.project.foundation.util.RenderHelper;
import br.com.project.foundation.view.ProvidedView;

import com.sun.faces.renderkit.html_basic.FormRenderer;

/**
 * @author anderson.nascimento
 *
 */
@FacesRenderer(componentFamily = "br.com.project.foundation.components", rendererType = "br.com.project.foundation.components.FormRenderer")
public class UIFormRenderer extends FormRenderer {

	private String view_;
	private String coluna;

	private Map<String, Boolean> openMaps = new HashMap<String, Boolean>();
	private Map<String, Boolean> closeMaps = new HashMap<String, Boolean>();

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

		UIForm filter = (UIForm) component;

		filter.getChildren().clear();

		super.encodeBegin(context, filter);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {

		rendererParamsNotNull(context, component);

		if (!shouldEncodeChildren(component)) {
			return;
		}

		UIForm form = (UIForm) component;

		ProvidedView<BaseObject> view = (ProvidedView<BaseObject>) form.getView();	

		view_ = StringUtil.firstLower(ReflectionUtil.getBeanClassName(view));

		coluna = form.getColuna();

		BeanManager<?> beanManager = (BeanManager<?>) view.getBeanManager();				

		List<FilterProperty> filters = AnnotationUtil.filter(beanManager.getBeanClass());

		encodeFields(context, component, form, filters, beanManager, null);

		super.encodeChildren(context, component);
	}

	@Override
	public void encodeEnd(FacesContext ctx, UIComponent component) throws IOException {
		super.encodeEnd(ctx, component);
	}

	private void encodeFields(FacesContext context, UIComponent component, UIForm filter, List<FilterProperty> filters, BeanManager<?> beanManager, Map<String, String> filtersValues) throws IOException {

		List<String> showOnlyFields = new ArrayList<String>();

		CollectionUtils.addAll(showOnlyFields, filter.getShowOnlyFields() != null ? filter.getShowOnlyFields().split("\\s*,\\s*") : new String[] {});

		for (FilterProperty fp : filters) {

			if (showOnlyFields.size() > 0 && !showOnlyFields.contains(fp.getName())) {
				continue;
			}

			Annotation annotation = fp.getAnnotation();

			/** Campos do filtro */

			if (annotation instanceof TextFilter) {

				TextFilter text = (TextFilter) annotation;
				
				if((text.exclusiveDialog() && !filter.isDialog()) || (text.exclusiveForm() && filter.isDialog()))
					continue;

				HtmlOutputLabel label = RenderHelper.renderOutputLabel(context, component, fp.getName(), text.label());

				open(label, true);				

				InputText inputText = RenderHelper.renderInputText(context, component, fp.getName(), text.label(), 
						JSFHelper.createValueExpression("#{" + view_ + ".beanFilter." + fp.getName() + "}", Object.class));

				inputText.setRequired(text.required());
				inputText.setStyle(text.style());
				inputText.setStyleClass(text.styleClass());
				inputText.setMaxlength(text.length());
				inputText.setOnkeyup(text.onkeyup());
				inputText.getAttributes().put("icon", text.icon());

				closeInputText(inputText, true);

			} else if (annotation instanceof MaskFilter) {

				MaskFilter mask = (MaskFilter) annotation;
				
				if((mask.exclusiveDialog() && !filter.isDialog()) || (mask.exclusiveForm() && filter.isDialog()))
					continue;

				HtmlOutputLabel label = RenderHelper.renderOutputLabel(context, component, fp.getName(), mask.label());

				open(label, true);


				InputMask inputMask = RenderHelper.renderInputMask(context, component, fp.getName(),
						mask.label(), JSFHelper.createValueExpression("#{" + view_ + ".beanFilter." + fp.getName() + "}", Object.class));

				inputMask.setRequired(mask.required());				
				inputMask.setMask(mask.mask());				
				inputMask.setStyle(mask.style());						
				inputMask.setMaxlength(mask.length());
				inputMask.getAttributes().put("icon", mask.icon());

				closeInputMask(inputMask, true);

			} else 	if (annotation instanceof DateFilter) {

				DateFilter date = (DateFilter) annotation;
				
				if((date.exclusiveDialog() && !filter.isDialog()) || (date.exclusiveForm() && filter.isDialog()))
					continue;

				HtmlOutputLabel label = RenderHelper.renderOutputLabel(context, component, fp.getName(), date.label());

				open(label, true);	

				Calendar calendar = RenderHelper.renderCalendar(context, component, fp.getName(), date.label(), JSFHelper.createValueExpression("#{" + view_ + ".beanFilter."+fp.getName()+"}", Object.class),  date.required());
				calendar.getAttributes().put("select", true);

				Map<String, String> map = new LinkedHashMap<String, String>();
				String method = "#{" + view_ + ".comboOperators()}";
				if (StringUtil.isNotEmpty(method) && method.matches("\\#\\{.*?\\}")) {
					MethodExpression me = JSFHelper.createMethodExpression(generatorMethod(method, view_), List.class, new Class<?>[] {});
					Object object = me.invoke(context.getELContext(), new Object[] {});						
					if (object != null) {
						if (List.class.isAssignableFrom(object.getClass())) {
							List<?> list = (List<?>) object;
							for (int i = 0; i < list.size(); i++) {
								Object obj = list.get(i);
								Object value = ReflectionUtil.executeGetterMethod(obj, "value");
								Object field = ReflectionUtil.executeGetterMethod(obj, "label");
								map.put(StringUtil.getString(value), StringUtil.getString(field));
							}
						}
					}
				}				

				HtmlSelectOneMenu select = RenderHelper.renderSelect(context, component, map, JSFHelper.createValueExpression("#{" + view_ + ".beanFilter." + fp.getName() + "}", Object.class));		
				select.setStyle("font-size: 14px; font-weight: 900;  width: 65px;");
				select.getAttributes().put("filter-data", true);

				closeSelectOneMenu(select, true);

			} else if (annotation instanceof DialogFilter) {

				DialogFilter dialog = (DialogFilter) annotation;	
				
				if((dialog.exclusiveDialog() && !filter.isDialog()) || (dialog.exclusiveForm() && filter.isDialog()))
					continue;

				HtmlOutputLabel label = RenderHelper.renderOutputLabel(context, component, fp.getName(), dialog.label());

				open(label, true);

				InputText inputText = RenderHelper.renderInputText(context, component, fp.getName() + "_text", dialog.label(), 
						JSFHelper.createValueExpression("#{" + view_ + ".beanFilter." + dialog.name() + "}", Object.class));

				inputText.setRequired(dialog.required());
				inputText.setDisabled(true);
				inputText.setStyle(dialog.style());
				inputText.setStyleClass(dialog.styleClass());
				inputText.getAttributes().put("dialog", true);

				String actionListener = "#{" + view_ + "." + dialog.actionListener() + "}";

				if(StringUtil.isEmpty(dialog.actionListener())) {
					actionListener = null;
				}

				CommandButton button = RenderHelper.renderButton(context, component, fp.getName() +"_button", "", actionListener);
				button.setStyleClass("btn btn-default btn-sm");
				button.getAttributes().put("dialog", true);
				button.getAttributes().put("modal", dialog.openDialog());
				button.getAttributes().put("button-icon", "fa fa-binoculars");

				closeButton(button, true);

			} else if (annotation instanceof SelectFilter) {

				SelectFilter selectOneMenu = (SelectFilter) annotation;
				
				if((selectOneMenu.exclusiveDialog() && !filter.isDialog()) || (selectOneMenu.exclusiveForm() && filter.isDialog()))
					continue;

				HtmlOutputLabel label = RenderHelper.renderOutputLabel(context, component, fp.getName(), selectOneMenu.label());

				open(label, true);

				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("", "");

				if(StringUtil.isNotEmpty(selectOneMenu.method())) {

					String method = selectOneMenu.method();
					if (StringUtil.isNotEmpty(method) && method.matches("\\#\\{.*?\\}")) {
						MethodExpression me = JSFHelper.createMethodExpression(generatorMethod(method, view_), List.class, new Class<?>[] {});
						Object object = me.invoke(context.getELContext(), new Object[] {});						
						if (object != null) {
							if (List.class.isAssignableFrom(object.getClass())) {
								List<?> list = (List<?>) object;
								for (int i = 0; i < list.size(); i++) {
									Object obj = list.get(i);
									Object value = ReflectionUtil.executeGetterMethod(obj, "value");
									Object field = ReflectionUtil.executeGetterMethod(obj, "label");
									map.put(StringUtil.getString(value), StringUtil.getString(field));
								}
							}
						}
					}				

				}

				HtmlSelectOneMenu select = RenderHelper.renderSelect(context, component, map, 
						JSFHelper.createValueExpression("#{" + view_ + ".beanFilter." + fp.getName() + "}", Object.class));		
				select.setStyle(selectOneMenu.style());

				closeSelectOneMenu(select, true);
			}

		}		
	}

	private void open(HtmlOutputLabel label, Boolean open) {		
		openMaps.put(label.getId(), open);
		label.getAttributes().put("openDiv", openMaps);	

		if(coluna != null) {
			label.getAttributes().put("colmd", coluna);		
		}
	}

	private void closeInputText(InputText inputText, Boolean close) {
		closeMaps.put(inputText.getId(), close);
		inputText.getAttributes().put("closeDiv", closeMaps);

	}

	private void closeInputMask(InputMask inputMask, Boolean close) {
		closeMaps.put(inputMask.getId(), close);
		inputMask.getAttributes().put("closeDiv", closeMaps);
	}

	private void closeButton(CommandButton button, Boolean close) {
		closeMaps.put(button.getId(), close);
		button.getAttributes().put("closeDiv", closeMaps);
	}

	private void closeSelectOneMenu(HtmlSelectOneMenu select, Boolean close) {
		closeMaps.put(select.getId(), close);
		select.getAttributes().put("closeDiv", closeMaps);
	}		

	/**
	 * @param method
	 * @param controller
	 * @return
	 */
	private String generatorMethod(String method, String controller) {

		if(StringUtil.isNotEmpty(method) && method.contains("{controller}")) {
			method = method.replace("{controller}", controller);
		}		

		return method;		
	}
}
