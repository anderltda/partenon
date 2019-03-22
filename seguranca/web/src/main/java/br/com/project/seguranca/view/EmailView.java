package br.com.project.seguranca.view;


import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.commons.util.CommonsMail;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.util.MessageHelper;
import br.com.project.seguranca.ProvidedSegurancaView;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class EmailView extends ProvidedSegurancaView<Long, Usuario> {

	private static final long serialVersionUID = 1L;
	
	private String emailTo;
	private String emailCc;
	private String emailBcc;
	private String message;

	@Override
	public String getEditPage() {
		return null;
	}

	@Override
	public String getCreatePage() {
		return null;
	}
	
	@Override
	public String getSearchPage() {
		return null;
	}
	
	@Override
	protected String getSavePage() {
		return getEditPage();
	}

	@Override
	public String getViewPage() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public void send() {
		try {
			
			List<String> emailTos = null;
			List<String> ccs = null;
			List<String> bccs = null;

			if(StringUtil.isNotEmpty(emailTo)) {
				emailTos = Arrays.asList(emailTo.split(";"));  				
			}
			
			if(StringUtil.isNotEmpty(emailCc)) {
				ccs = Arrays.asList(emailCc.split(";"));  				
			}
			
			if(StringUtil.isNotEmpty(emailBcc)) {
				bccs = Arrays.asList(emailBcc.split(";"));
			}
		
			CommonsMail.enviaEmailFormatoHtml(emailTos, "Messagem enviada via Sistema", message, ccs, bccs);
			
			MessageHelper.addInfoMessage("message_email_sucess");

		} catch (Exception ex) {
			ex.printStackTrace();
			MessageHelper.addErrorMessage(ex.getMessage());
		}
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailCc() {
		return emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public String getEmailBcc() {
		return emailBcc;
	}

	public void setEmailBcc(String emailBcc) {
		this.emailBcc = emailBcc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}

