package br.com.project.seguranca.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.foundation.Constants;

/**
 * @author anderson.nascimento
 *
 */
public class UsuarioFilter implements Filter {

	private String path = null;
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = getURL(req);
		
		Usuario usuario = authorizeWebService(req, url);
		boolean isAllowed = isURLAllowed(url);
		if (usuario == null && !isAllowed) {
			String params = req.getQueryString();
			resp.sendRedirect(req.getContextPath() + path + (params != null ? "?" + params : ""));
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	private String getURL(HttpServletRequest req) {
		String url = req.getServletPath();

		return url;
	}

	private boolean isURLAllowed(String url) {
		if (url != null && (url.matches("/login\\.jsf") || url.matches("/.*?\\.jar") || url.matches("/.*?\\.class") || (url.matches("\\/javax\\.faces\\.resource\\/.*?\\.jsf")))) {
			return true;
		}
		return false;
	}

	private Usuario authorizeWebService(HttpServletRequest req, String url) {

		Usuario usuario = (Usuario) req.getSession().getAttribute(Constants.LOGGED_USER);

		path = "/login.jsf"; 
		
		return usuario;
	}

}
