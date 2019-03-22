package br.com.project.foundation.custom;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.dialog.DialogRenderer;
import org.primefaces.util.ComponentUtils;

/**
 * @author anderson.nascimento
 *
 */
public class CustomDialogRenderer extends DialogRenderer {
	
	
	@Override
    protected void encodeMarkup(FacesContext context, Dialog dialog) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String clientId = dialog.getClientId(context);
        String style = dialog.getStyle();
        String styleClass = dialog.getStyleClass();
        styleClass = styleClass == null ? Dialog.CONTAINER_CLASS : Dialog.CONTAINER_CLASS + " " + styleClass;
        
        if(ComponentUtils.isRTL(context, dialog)) {
            styleClass += " ui-dialog-rtl";
        }

        writer.startElement("div", null);
        writer.writeAttribute("id", clientId, null);
        writer.writeAttribute("class", styleClass + "modal-dialog", null);
        
        if(style != null) {
            writer.writeAttribute("style", style, null);
        }

        if(dialog.isShowHeader()) {
            encodeHeader(context, dialog);
        }
        
        encodeContent(context, dialog);

        encodeFooter(context, dialog);

        writer.endElement("div");
    }
	
	
	@Override
    protected void encodeContent(FacesContext context, Dialog dialog) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        
        writer.startElement("div", null);
        writer.writeAttribute("class", Dialog.CONTENT_CLASS /*+ " modal-content"*/, null);
        
        if(!dialog.isDynamic()) {
            renderChildren(context, dialog);
        }
        
        writer.endElement("div");
    }
}