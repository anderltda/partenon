package br.com.project.seguranca.view;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.foundation.view.BaseView;
import br.com.project.seguranca.service.SegurancaService;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class LoginView extends BaseView {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{" + SegurancaService.SERVICE + "}" )
	protected SegurancaService segurancaService;	

	private String login;
	private String password;
	private String email;
	private String message;
	private Usuario loggedUser;

	public void login() {

		try {

			/** Usuario teste */
 			loggedUser = new Usuario();
			loggedUser.setAtivo("S");
			loggedUser.setDataUltimoAcesso(new Date());
			loggedUser.setEmail("anderltda@gmail.com");
			loggedUser.setId(1l);
			loggedUser.setNome("Anderson Nascimento");
			loggedUser.setPassword("123456");

			/** Usuario dinamico do banco */
			/*				
			loggedUser = getSegurancaService().login(login, password);

			loggedUser.setDataUltimoAcesso(new Date());

			getSegurancaService().save(loggedUser);
			 */

			setLoggedUser(loggedUser);

			RequestContext.getCurrentInstance().addCallbackParam("logado", FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/dashboard.jsf");

		} catch (Exception ex) {
			message = ex.getMessage() ;
			RequestContext.getCurrentInstance().addCallbackParam("logar", message);
			ex.printStackTrace();
		}

	}

	public void forgot() {

		try {

			getSegurancaService().esqueciSenha(email);

			RequestContext.getCurrentInstance().addCallbackParam("ok", true);

		} catch (Exception ex) {
			message = ex.getMessage() ;
			RequestContext.getCurrentInstance().addCallbackParam("ok", false);
			ex.printStackTrace();
		}
	}

	public String logout() {

		reset();

		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();

		return "/login.jsf?faces-redirect=true";		
	}
	
	public String home() {
		return "/dashboard.jsf?faces-redirect=true";		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SegurancaService getSegurancaService() {
		return segurancaService;
	}

	public void setSegurancaService(SegurancaService segurancaService) {
		this.segurancaService = segurancaService;
	}

	public static void main(String[] args) throws UnknownHostException {
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}	
}
