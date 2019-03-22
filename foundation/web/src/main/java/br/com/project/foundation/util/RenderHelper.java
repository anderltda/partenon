package br.com.project.foundation.util;

import java.io.IOException;
import java.util.Map;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.remotecommand.RemoteCommand;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.spacer.Spacer;

import br.com.project.commons.util.StringUtil;


/**
 * @author anderson.nascimento
 *
 */
public class RenderHelper {
	
	private static final String INPUT_CLASS = "ui-inputfield ui-widget ui-state-default ui-corner-all";

	
	public static HtmlOutputLabel renderLabel(FacesContext context, UIComponent component, String id, String label) throws IOException {
		
		HtmlOutputLabel htmlOutputLabel = (HtmlOutputLabel) context.getApplication().createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		
		htmlOutputLabel.setFor(id);
		
		htmlOutputLabel.setValue(MessageHelper.getMessage(label));		
		
		component.getChildren().add(htmlOutputLabel);
		
		return htmlOutputLabel;
	}
	
	public static OutputLabel renderOutputLabel(FacesContext context, UIComponent component, String id, String label) throws IOException {
		
		OutputLabel htmlOutputLabel = (OutputLabel) context.getApplication().createComponent(OutputLabel.COMPONENT_TYPE);
		
		htmlOutputLabel.setId(id + "_label");
		
		htmlOutputLabel.setFor(id + "_label");
		
		htmlOutputLabel.setValue(MessageHelper.getMessage(label));		
		
		component.getChildren().add(htmlOutputLabel);
		
		return htmlOutputLabel;
	}
	
	public static InputText renderInputText(FacesContext context, UIComponent component, String id, String label, Object value) throws IOException {
		
		InputText inputText = (InputText) context.getApplication().createComponent(InputText.COMPONENT_TYPE);
		
		inputText.setId(id);

		inputText.setLabel(MessageHelper.getMessage(label));

		if (value != null && ValueExpression.class.isAssignableFrom(value.getClass())) {
			inputText.setValueExpression("value", (ValueExpression) value);
		} else {
			inputText.setValue(value);
		}

		
		component.getChildren().add(inputText);
		
		return inputText;
	}
	
	
	public static InputMask renderInputMask(FacesContext context, UIComponent component, String id, String label, Object value) throws IOException {
		
		InputMask inputMask = (InputMask) context.getApplication().createComponent(InputMask.COMPONENT_TYPE);
		
		inputMask.setId(id);
		
		inputMask.setLabel(MessageHelper.getMessage(label));
		
		if (value != null && ValueExpression.class.isAssignableFrom(value.getClass())) {
			inputMask.setValueExpression("value", (ValueExpression) value);
		} else {
			inputMask.setValue(value);
		}

		component.getChildren().add(inputMask);
		
		return inputMask;
	}
	
	
	public static SelectOneMenu renderSelect(FacesContext context, UIComponent component, Map<String, String> map, Object value) {
					
		SelectOneMenu select = (SelectOneMenu) context.getApplication().createComponent(SelectOneMenu.COMPONENT_TYPE);
		
		if (value != null && ValueExpression.class.isAssignableFrom(value.getClass())) {
			select.setValueExpression("value", (ValueExpression) value);
		} else {
			select.setValue(value);
		}

		for (Map.Entry<String,String> entry : map.entrySet()) {				
			UISelectItem selectItem = (UISelectItem) context.getApplication().createComponent(UISelectItem.COMPONENT_TYPE);
			selectItem.setItemLabel(entry.getValue());
			selectItem.setItemValue(entry.getKey());
			select.getChildren().add(selectItem);
		}			

		component.getChildren().add(select);

		return select;
	}
	
	

	public static HtmlOutputText renderOutputText(FacesContext context, UIComponent component, String id, Object value) throws IOException {
		HtmlOutputText outputText = (HtmlOutputText) context.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);

		outputText.setId(id);
		
		if (value != null && ValueExpression.class.isAssignableFrom(value.getClass())) {
			outputText.setValueExpression("value", (ValueExpression) value);
		} else {
			outputText.setValue(value);
		}

		component.getChildren().add(outputText);
		return outputText;
	}	
	
	public static Calendar renderCalendar(FacesContext context, UIComponent component, String id, String label, Object value, boolean required) {
		Calendar calendar =  (Calendar) context.getApplication().createComponent(Calendar.COMPONENT_TYPE);
		calendar.setId(id);
		calendar.setPattern("dd/MM/yyyy");
		//calendar.setShowOn("button");
		calendar.setEffect("drop");
		calendar.setReadonlyInput(true);
		calendar.setRequired(required);
		calendar.setLabel(label);
		
		if (value != null && ValueExpression.class.isAssignableFrom(value.getClass())) {
			calendar.setValueExpression("value", (ValueExpression) value);
		} else {
			calendar.setValue(value);
		}
		
		component.getChildren().add(calendar);
		return calendar;
	}
	
	public static HtmlInputHidden renderInputHidden(FacesContext context, UIComponent component, String id, Object value) throws IOException {
		HtmlInputHidden hidden = (HtmlInputHidden) context.getApplication().createComponent(HtmlInputHidden.COMPONENT_TYPE);

		hidden.setId(id);
		
		if (value != null && ValueExpression.class.isAssignableFrom(value.getClass())) {
			hidden.setValueExpression("value", (ValueExpression) value);
		} else {
			hidden.setValue(value);
		}
		
		component.getChildren().add(hidden);
		
		return hidden;
	}
	
	public static HtmlSelectOneMenu renderCombo(FacesContext context, UIComponent component, String id, String[] list, Object value) throws IOException {
		HtmlSelectOneMenu select = (HtmlSelectOneMenu) context.getApplication().createComponent(HtmlSelectOneMenu.COMPONENT_TYPE);

		select.setId(id);
		select.setStyleClass(INPUT_CLASS);
		select.setValue(value);

		int i = 0;
		
		for (String item : list) {
			String[] itemLine = item.split("\\s*\\:\\s*");
			UISelectItem selectItem = (UISelectItem) context.getApplication().createComponent(UISelectItem.COMPONENT_TYPE);
			selectItem.setId(select.getId() + "Select" + i);
			if (itemLine[0].equals("null") && itemLine[1].equals("null")) {
				itemLine[0] = "";
				itemLine[1] = "Todos";
			}

			selectItem.setItemValue(itemLine[0]);
			selectItem.setItemLabel(MessageHelper.getMessage(itemLine[1]));
			select.getChildren().add(selectItem);
			i ++;
		}

		component.getChildren().add(select);
		return select;
	}
	
	public static HtmlSelectOneRadio renderRadio(FacesContext context, UIComponent component, String id, String[] list) throws IOException {
		
		HtmlSelectOneRadio radio = (HtmlSelectOneRadio) context.getApplication().createComponent(HtmlSelectOneRadio.COMPONENT_TYPE);

		radio.setId(id);
		
		radio.setStyleClass(INPUT_CLASS);
		
		int i = 0;
		
		for (String item : list) {
			String[] itemLine = item.split("\\s*\\:\\s*");
			UISelectItem selectItem = (UISelectItem) context.getApplication().createComponent(UISelectItem.COMPONENT_TYPE);
			selectItem.setId(radio.getId() + "Select" + i);
			if (itemLine[0].equals("null") && itemLine[1].equals("null")) {
				itemLine[0] = "";
				itemLine[1] = "Todos";
			}

			selectItem.setItemValue(itemLine[0]);
			selectItem.setItemLabel(MessageHelper.getMessage(itemLine[1]));
			radio.getChildren().add(selectItem);
			i ++;
		}

		component.getChildren().add(radio);
		
		return radio;
	}
	
	public static RemoteCommand renderRemoteCommand(FacesContext context, UIComponent component, String id, String name, String update, String actionListener) throws IOException {
		
		RemoteCommand remoteCommand = (RemoteCommand) context.getApplication().createComponent(RemoteCommand.COMPONENT_TYPE);
		remoteCommand.setId(id);
		remoteCommand.setName(name);
		remoteCommand.setUpdate(update);		

		if (StringUtil.isNotEmpty(actionListener)) {
			MethodExpression me = JSFHelper.createMethodExpression(actionListener, Object.class, new Class<?>[] { ActionEvent.class });
			MethodExpressionActionListener al = new MethodExpressionActionListener(me);
			remoteCommand.addActionListener(al);
		}

		component.getChildren().add(remoteCommand);

		return remoteCommand;
	}

	public static CommandButton renderButton(FacesContext ctx, UIComponent component, String id, String label, String actionListener) throws IOException {
		CommandButton button = (CommandButton) ctx.getApplication().createComponent(CommandButton.COMPONENT_TYPE);

		button.setId(id);
		button.setValue(MessageHelper.getMessage(label));
				
		if (actionListener != null) {
			MethodExpression me = JSFHelper.createMethodExpression(actionListener, Object.class, new Class<?>[] { ActionEvent.class });
			MethodExpressionActionListener al = new MethodExpressionActionListener(me);
			button.addActionListener(al);
		}

		component.getChildren().add(button);
		
		return button;
	}
	
	public static CommandLink renderCommandLink(FacesContext ctx, UIComponent component, String id, String label, String actionListener) throws IOException {

		CommandLink commandLink = (CommandLink) ctx.getApplication().createComponent(CommandLink.COMPONENT_TYPE);
		commandLink.setId(id);
		commandLink.setTitle(label);
	
		if (StringUtil.isNotEmpty(actionListener)) {
			MethodExpression me = JSFHelper.createMethodExpression(actionListener, Object.class, new Class<?>[] { ActionEvent.class });
			MethodExpressionActionListener al = new MethodExpressionActionListener(me);
			commandLink.addActionListener(al);
		}
		
		component.getChildren().add(commandLink);
		
		return commandLink;
	}

	public static Spacer renderSpacer(FacesContext context, UIComponent component, int width) throws IOException {
		Spacer spacer = (Spacer) context.getApplication().createComponent(Spacer.COMPONENT_TYPE);
		spacer.setWidth(width + "px");
		component.getChildren().add(spacer);
		return spacer;
	}


	public static Panel renderPanel(FacesContext context, String id, String name) {
		Panel panel =  (Panel)context.getApplication().createComponent(Panel.COMPONENT_TYPE);
		panel.setId(id);
		panel.setStyle("ui-datatable-complex");
		panel.setHeader(name);

		return panel;
	}

	public static HtmlPanelGrid renderPanelGrid(FacesContext context, UIComponent component, int columns) {
		HtmlPanelGrid panelGrid = (HtmlPanelGrid)context.getApplication().createComponent(HtmlPanelGrid.COMPONENT_TYPE);

		panelGrid.setColumns(columns);
		panelGrid.setColumnClasses("form-label,form-field");

		component.getChildren().add(panelGrid);

		return panelGrid;
	}


}
